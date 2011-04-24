package com.xdarkness.framework;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xdarkness.framework.connection.XConnection;
import com.xdarkness.framework.connection.XConnectionConfig;
import com.xdarkness.framework.connection.XConnectionPool;
import com.xdarkness.framework.connection.XConnectionPoolManager;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

/**
 * 
 * @author Darkness Create on 2010-5-19 下午05:09:38
 * @version 1.0
 */
public class DBUtil {

    public DBUtil() {
    }

    public static DataTable getTableInfo() {
        return getTableInfo(XConnectionPoolManager.getDBConnConfig());
    }

    public static DataTable getTableInfo(XConnectionConfig dcc) {
        Connection conn = null;
        try {
            conn = XConnectionPool.createConnection(dcc, false);
            DatabaseMetaData dbm = conn.getMetaData();
            String currentCatalog = conn.getCatalog();
            ResultSet rs = dbm.getTables(currentCatalog, null, null, null);
            return new DataTable(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static DataTable getColumnInfo(String tableName) {
        
        return getColumnInfo(XConnectionPoolManager.getDBConnConfig(), tableName);
    }

    public static DataTable getColumnInfo(XConnectionConfig dcc,
            String tableName) {
        
        XConnection conn = null;
        try {
            conn = XConnectionPool.createConnection(dcc, false);
            DatabaseMetaData dbm = conn.getMetaData();
            String currentCatalog = conn.getCatalog();
            String schema = null;
            String oldName = tableName;
            int index = tableName.indexOf(".");
            if (index > 0) {
                schema = tableName.substring(0, index);
                tableName = tableName.substring(index + 1);
            }
            ResultSet rs = dbm.getColumns(currentCatalog, schema, tableName,
                    null);
            DataTable dt = new DataTable(rs);

            rs = dbm.getPrimaryKeys(currentCatalog, null, tableName);
            DataTable keyDt = new DataTable(rs);
            Mapx<String, Object> map = keyDt.toMapx("Column_Name", "PK_Name");
            dt.insertColumn("isKey");
            for (int i = 0; i < dt.getRowCount(); ++i) {
                DataRow dr = dt.getDataRow(i);
                if (map.containsKey(dr.getString("Column_Name")))
                    dr.set("isKey", "Y");
                else {
                    dr.set("isKey", "N");
                }
            }
            DataTable data = new QueryBuilder(
                    "select * from " + oldName + " where 1=2").executeDataTable();
            for (int i = 0; i < data.getColCount(); ++i) {
                DataRow dr = dt.getDataRow(i);
                dr.set("Type_Name", data.getDataColumn(i).getColumnType());
            }
            return dt;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static DataTable getSQLTypes() {
        return getSQLTypes(XConnectionPoolManager.getDBConnConfig());
    }

    public static DataTable getSQLTypes(XConnectionConfig dcc) {
        Connection conn = null;
        try {
            conn = XConnectionPool.createConnection(dcc, false);
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet rs = dbm.getTypeInfo();
            DataTable dt = new DataTable(rs);
            return dt;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static int getCount(QueryBuilder qb) {
        return qb.getCount();
    }
}
