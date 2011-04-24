package com.xdarkness.framework.data;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.xdarkness.framework.connection.XConnection;

/**
 * 
 * @author Darkness Create on 2010-5-19 ����04:57:39
 * @version 1.0
 */
public class LobUtil {

    public LobUtil() {
    }

    /**
     * ��Clob�ж�ȡ�ַ���
     * 
     * @param clob
     * @return
     */
    public static String clobToString(Clob clob) {
        if (clob == null)
            return "";//null

        Reader r = null;
        char[] cs = null;
        StringWriter sw = null;

        try {
            r = clob.getCharacterStream();
            cs = new char[(int) clob.length()];
            sw = new StringWriter();

            try {
                r.read(cs);
                sw.write(cs);
                return sw.toString();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ��Blobת����byte����
     * 
     * @param blob
     * @return
     */
    public static byte[] blobToBytes(Blob blob) {

        if (blob == null)
            return null;
        try {
            return blob.getBytes(1L, (int) blob.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ��ȡOracle ��CLOB������
     * 
     * @return
     */
    private static Class<?> getOracleCLOB() {

        try {
            return Class.forName("oracle.sql.CLOB");
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public static void setClob(XConnection conn,String dbType, PreparedStatement ps, int i,
            Object v) throws SQLException {

        if ("ORACLE".equals(dbType)) {
            Class<?> clobClass = getOracleCLOB();
            Object clob = null;
			Object oc = conn.getPhysicalConnection();
            try {
                Method m = clobClass
                        .getMethod("createTemporary", new Class[] {
                                java.sql.Connection.class, Boolean.TYPE,
                                Integer.TYPE });
                clob = m.invoke(null, new Object[] { oc/*conn*/, new Boolean(true),
                        new Integer(1) });
                m = clobClass.getMethod("open", new Class[] { Integer.TYPE });
                m.invoke(clob, new Object[] { new Integer(1) });
                m = clobClass.getMethod("setCharacterStream",
                        new Class[] { Long.TYPE });
                Writer writer = (Writer) m.invoke(clob,
                        new Object[] { new Long(0L) });
                writer.write(String.valueOf(v));
                writer.close();
                clobClass.getMethod("close").invoke(clob);
                ps.setClob(i, (Clob) clob);
            } catch (Exception e) {
                try {
                    if (clob != null) {
                        Method m = clobClass.getMethod("freeTemporary",
                                new Class[] { clobClass });
                        m.invoke(null, new Object[] { clob });
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        } else {
            ps.setObject(i, v);
        }
    }

    /**
     * ��ȡOracle ��CLOB������
     * 
     * @return
     */
    private static Class<?> getOracleBLOB() {

        try {
            return Class.forName("oracle.sql.BLOB");
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    public static void setBlob(XConnection conn,String dbType, PreparedStatement ps, int i,
            byte v[]) throws SQLException {

        if ("ORACLE".equals(dbType)) {





            Class<?> blobClass = getOracleBLOB();
            Object blob = null;
Object oc = conn.getPhysicalConnection();
            try {
                Method m = blobClass
                        .getMethod("createTemporary", new Class[] {
                                java.sql.Connection.class, Boolean.TYPE,
                                Integer.TYPE });
                blob = m.invoke(null, new Object[] { oc, new Boolean(true),
                        new Integer(1) });
                m = blobClass.getMethod("open", new Class[] { Integer.TYPE });
                m.invoke(blob, new Object[] { new Integer(1) });
                m = blobClass.getMethod("getBinaryOutputStream",
                        new Class[] { Long.TYPE });
                OutputStream out = (OutputStream) m.invoke(blob,
                        new Object[] { new Long(0L) });
                out.write(v);
                out.close();
                blobClass.getMethod("close").invoke(blob);
                ps.setBlob(i, (Blob) blob);
            } catch (Exception e) {
                try {
                    if (blob != null) {
                        Method m = blobClass.getMethod("freeTemporary",
                                new Class[] { blobClass });
                        m.invoke(null, new Object[] { blob });
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        } else {
            ps.setObject(i, v);
        }
    }
}
