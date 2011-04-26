package tablemeta;


import java.util.Date;
import java.util.Hashtable;

import com.xdarkness.common.data.DataTable;
import com.xdarkness.common.util.StringUtil;

/// TableStructList，提供对所有数据库的非系统表的基本属性访问。
///		修改对sysobjects及syscolumns的查询条件，使适应sql svr 2005
///		tablestructlist 类的 init 方法修改以防出现注释无法加载的情形
public class TableStructList {

	// / 此类为静态类，不提供实例化方法。

	private TableStructList() {
	}

	// / 本类中的静态hashtable是否已初始化

	public static boolean Inited = false;

	// / 一个静态的Hashtable，键名为“表名”，键值为 一个TableStruct对象

	private static Hashtable hashtable = new Hashtable();

	// / 根据指定的表名，取得其TableStruct对象
	// / 若该表名未曾存储于本类中，则返回null

	// / <param name="m_TableName">指定的表名</param>
	// / <returns>TableStruct对象</returns>
	public static TableStruct get(String m_TableName) {
		init();
		if (!Exists(m_TableName.toLowerCase())) {
			return null;
		}
		return (TableStruct) (hashtable.get(m_TableName.toLowerCase()));
	}

	// / 将指定的表的TableStruct实例对象存入本类中

	// / <param name="m_Table">TableStruct实例</param>
	private static void set(TableStruct m_Table) {
		init();
		if (Exists(m_Table.getName())) {
			hashtable.remove(m_Table.getName());
		}
		hashtable.put(m_Table.getName(), m_Table);
	}

	// / 指定的表名是否已在本类中存储其信息

	// / <param name="m_TableName">表名</param>
	// / <returns>true or false</returns>
	public static boolean Exists(String m_TableName) {
		init();
		return hashtable.containsKey(m_TableName.toLowerCase());
	}

	// / 初始化本类的HashTable以及ColInfos类

	static void init() {
		if (Inited) {
			return;
		}
		TestSpeed("tablestructlist init begin at " + new Date());
		hashtable.clear();
		ColInfos.clear();

		// 更新Tablesremark表及Colsremark表的数据
		ColsremarkViews.clear();
		TablesremarkViews.clear();

		String dbString = "mssql";
		if("mssql".equals(dbString)){// 清除Tablesremark中已经被删除的表的数据
			SqlHelper.ExecuteNonQuery("DELETE FROM Tablesremark WHERE tablename NOT IN (SELECT name AS tablename FROM sysobjects WHERE xtype = 'U' AND name <> 'dtproperties')");
		}else if("mysql".equals(dbString)){
			SqlHelper.ExecuteNonQuery("DELETE FROM Tablesremark WHERE tablename NOT IN (SELECT t.table_name AS tablename FROM information_schema.tables t WHERE t.table_schema = 'zcms')");
		}
		// 将Tablesremark中不存在的表添加进Tablesremark
		SqlHelper.ExecuteNonQuery("INSERT INTO Tablesremark (tablename) SELECT {fn LCASE([name])} AS tablename FROM sysobjects WHERE xtype = 'U' AND name <> 'dtproperties' AND name NOT IN (SELECT tablename FROM tablesremark)");
//		INSERT INTO Tablesremark (tablename) SELECT t.table_name as tablename FROM information_schema.tables t WHERE t.table_schema = 'zcms' AND t.table_name  NOT IN (SELECT tablename FROM tablesremark)
		// 将ColsRemark中脏数据清除
		SqlHelper.ExecuteNonQuery("SELECT (sysobjects.name + char(13) + syscolumns.name) AS tablecolname into #temp FROM sysobjects INNER JOIN syscolumns ON sysobjects.id = syscolumns.id WHERE (sysobjects.xtype = 'U') AND (sysobjects.name <> 'dtproperties') delete FROM ColsRemark WHERE id not IN (SELECT * from #temp) drop table #temp");
		// 将ColsRemark中不存在的列添加进ColsRemark
		SqlHelper.ExecuteNonQuery("INSERT INTO ColsRemark (id , tablename, colname, colorder) SELECT newid() as id,lower(sysobjects.name) AS tablename, lower(syscolumns.name) AS colname, syscolumns.colorder FROM sysobjects INNER JOIN syscolumns ON sysobjects.id = syscolumns.id WHERE (sysobjects.xtype = 'U') AND (sysobjects.name <> 'dtproperties') AND ( not exists (SELECT id FROM colsremark c where c.tablename = sysobjects.name and c.colname = syscolumns.name))");
		// 如果表设计发生改变，则更新tablesremark中的colchanged为1(改变)
		SqlHelper.ExecuteNonQuery("update tablesremark set colchanged = 1 where tablename in (select tablename from colsremark where colchanged=1)");
		// 更新colsremark表的id为tablename + char(13) + colname
		SqlHelper.ExecuteNonQuery("update colsremark set id = tablename + char(13) + colname");
		// 更新colsremark中表的列顺序，与真实数据库中表的列顺序一致
		SqlHelper.ExecuteNonQuery("update colsremark set colorder = (select syscolumns.colorder from syscolumns inner join sysobjects on syscolumns.id=sysobjects.id where syscolumns.name=colsremark.colname and sysobjects.name=colsremark.tablename)");
		// 方便开发人员维护，直接将adder、moder、delstatus进行下拉选框绑定
		// 更新adder,moder列为"系统用户"字典
		SqlHelper.ExecuteNonQuery("update colsremark set categoryid = '{612F4576-4BF6-424A-BB92-04E836CAB4FF}' where colname in ('adder' , 'moder')");
		// 更新delstatus列为"开发级YESNO"字典
		SqlHelper.ExecuteNonQuery("update colsremark set categoryid = '{0ECC56D4-9F55-4633-8A41-172CB220ABF9}' where colname in ('delstatus')");
		// 更新rdeptid列为"使用单位部门"字典
		SqlHelper.ExecuteNonQuery("update colsremark set categoryid = '{57460F9A-7F41-4733-84F5-AB1DDEAFBB0F}' where colname in ('rdeptid')");

		DataTable dt=null;
		DataTable dtcols=null;
		try {
			// 查询出所有表，并附上表的主键m_PkCol
			dt = SqlHelper
					.ExecuteDatatable("SELECT (SELECT isnull(syscolumns.name, '') AS colname FROM syscolumns INNER JOIN "
							+ "sysindexkeys ON syscolumns.colid = sysindexkeys.colid AND sysindexkeys.id = syscolumns.id "
							+ "WHERE syscolumns.id = sysobjects.id AND sysindexkeys.indid = 1) AS m_PkCol, * "
							+ "FROM sysobjects WHERE (xtype = 'U')  AND name <> 'dtproperties' ORDER BY sysobjects.id");

			// 查询出所有列
			dtcols = SqlHelper
					.ExecuteDatatable("SELECT id, name, xtype,length,isnullable,cdefault,colstat,colid,isnull((select text from syscomments where id=syscolumns.cdefault) , '') as defvalue FROM syscolumns WHERE id in (select id from sysobjects WHERE (xtype = 'U') AND sysobjects.name <> 'dtproperties' ) ORDER BY id,syscolumns.colorder,xtype");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		ColInfos.add(new ColInfo("", "", 0, 0, 0, 0, 0, 0, "", ""));

		if (hashtable.containsKey("") == false) {
			hashtable.put("", new TableStruct("", "", "".split(",")));
		}
		
		DataTable dtcolremark;
		String m_Name;
		String m_Primary;
		String m_ColNameList = "";

		int xtype,colType,length,isNullAble,haveDefault,isPK,isIncrement;
		String remark,colname,colDefault = "";
		// 遍历所有表
		for (int i = 0; i < dt.getRowCount(); i++) {
			// int remindex = 0;
			m_Name = dt.get(i, "name").toString().toLowerCase();
			m_ColNameList = "";
			m_Primary = dt.getString(i, "m_PkCol");
			if (StringUtil.isEmpty(m_Primary)) {
//				throw new Exception("表 " + m_Name + " 不存在主键，请认真设计该表！");
				return;
			}
			m_Primary = m_Primary.toLowerCase();
			// 获取表中所有字段的说明，如：objname：moder，remark：最后修改者
			dtcolremark = SqlHelper
					.ExecuteDatatable("SELECT objname,cast(value as varchar(8000)) as remark FROM ::fn_listextendedproperty('MS_Description', 'USER', 'dbo', 'TABLE', '"
							+ m_Name
							+ "', 'COLUMN', NULL) [::fn_listextendedproperty_1] order by objname");
			
			// 遍历所有列
			for (int j = 0; j < dtcols.getRowCount(); j++) {
				if (!dtcols.getString(j, "id").equals(
						dt.getString(i, "id"))) {//该列不属于该表则返回
					break;
				}
				colname = dtcols.getString(j, "name").toLowerCase();
				xtype = dtcols.getInt(j, "xtype");
				length = dtcols.getInt(j, "length");
				isNullAble = dtcols.getInt(j, "isnullable");
				haveDefault = dtcols.getInt(j, "cdefault") > 0 ? 1 : 0;
				isPK = (colname.equals(m_Primary) ? 1 : 0);
				colType = xtype;//SimpleTypes.getSimpleType(xtype);

				remark = "";
				// 遍历该表的所有字段备注
				for (int n = 0; n < dtcolremark.getRowCount(); n++) {
					if (dtcolremark.getString(n, "objname").toLowerCase()
							.equals(colname)) {// 该备注字段与该表字段一致
						remark = dtcolremark.getString(n, "remark").trim();
						break;
					}
				}

				/**
				 * char 型 或 varchar 型 VARCHAR = 0,
				 * text 型 TEXT = 1,
				 * datetime 型 DATETIME = 2,
				 * tinyint,int,bit,bigint INT = 3,
				 * real,money,float,decimal,numeric DOUBLE = 4
				 */
				if (colType < 3) {// 小于3的肯定不为自增
					isIncrement = 0;
				} else {// 否则查询是否是自增字段
					isIncrement = (dtcols.getString(j, "colstat").equals(
							"1") ? 1 : 0);
				}

				if (haveDefault == 1) {// 如果有默认值
					colDefault = dtcols.getString(j, "defvalue").trim();
					while (colDefault.indexOf("(") == 0) {// 以(开头，将开头与结尾的()去除
						colDefault = colDefault.substring(1, colDefault
								.length() - 2);
					}
					while (colDefault.indexOf("'") == 0) {// 以'开头，将开头结尾的‘’去除
						colDefault = colDefault.substring(1, colDefault
								.length() - 2);
					}

					if (colType == 2/*为日期类型*/
							&& colDefault.toLowerCase().equals("getdate()")) {
						colDefault = "new Date().toString()";
					}
				} 

				m_ColNameList += (m_ColNameList.length() > 0 ? "," : "")
						+ colname;
				ColInfos.add(new ColInfo(m_Name, colname, xtype, length,
						isNullAble, haveDefault, isPK, isIncrement, remark,
						colDefault));
			}
			hashtable.put(m_Name, new TableStruct(m_Name, m_Primary,
					m_ColNameList.split(",")));
		}

		Inited = true;
	}

	// / 测试速度

	// / <param name="sql"></param>
	private static void TestSpeed(String sql) {
		// SqlHelper.ExecuteNonQuery("insert into TablesBasicDebug (tbl , sqls)
		// values ('tablestructlist TestSpeed' , '" + MyString.SqlEncode(sql) +
		// "')");
	}
}