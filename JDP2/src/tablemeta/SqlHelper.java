package tablemeta;

import java.util.List;

import com.xdarkness.common.data.DataRow;
import com.xdarkness.common.data.DataTable;

/// <summary>
/// SqlHelper,用于数据库存取操作的一些基类封装,静态类,不提供实例化方法。
/// </summary>
/// <remarks>
/// CopyRight：擎天科技 Skyinsoft. Co. Ltd.
/// 王周文 于 2006-3-3
///		初次完成
/// 陈涛 于 2006-4-17
///		添加注释
/// </remarks>
public  class SqlHelper
{
	
	private static String strCn = null;

	/// <summary>
	/// 连接字符串
	/// </summary>
    public static String getGlobalConnectionString() 
	{return "";
//			if (strCn == null)
//			{
//                if (System.Configuration.ConfigurationSettings.AppSettings["GlobalConnectionString"] == null)
//                {
//                    strCn = "";
//                }
//                else
//                {
//                    strCn = System.Configuration.ConfigurationSettings.AppSettings["GlobalConnectionString"];
//                }
//				//throw new Exception("the GlobalConnectionString has not setted! You can't use SqlHelper");
//			}
//			return strCn ;
		}
    public void
        setGlobalConnectionString(String value)
        {
            strCn = value;
        }
	
	

	
	/// <summary>
	/// Execute a Text SqlCommand (that returns no resultset)
	/// 
	/// e.g.:  
	/// int result = ExecuteNonQuery("update a set b=1"); 
	/// </summary>
	/// <param name="commandText"> the stored procedure name or T-SQL command </param>
	/// <returns> returns an int representing the number of rows affected by the command</returns>
	public static int ExecuteNonQuery(String commandText)
	{
		return -1;
		//return -1;//SqlHelperBase.ExecuteNonQuery(GlobalConnectionString , commandText);
	}
	
	
	/// <summary>
	///  Execute a SqlCommand (that returns a resultset)
	/// 
	/// e.g.:  
	///  DataSet ds = ExecuteDataset("select * from a");
	/// </summary>
	/// <param name="commandText">the T-SQL command</param>
	/// <returns>returns a dataset containing the resultset generated by the command</returns>
	//public static DataSet ExecuteDataset(String commandText)
	//{throw new Exception();
		//return SqlHelperBase.ExecuteDataset(GlobalConnectionString , commandText);
	//}
	

	
	/// <summary>
	/// 使用查询语句取得一个DataTable
	/// </summary>
	/// <param name="commandText">查询语名,形如“select * from a where b=1”</param>
	/// <returns>DataTable</returns>
	public static DataTable ExecuteDatatable(String commandText)
	{return null;
		//return ExecuteDataset(commandText).Tables[0];
	}
	

	/// <summary>
	///  Execute a SqlCommand (that returns a resultset)
	/// 
	/// e.g.:  
	///  DataRow dr = ExecuteDatarow("select * from a");
	/// </summary>
	/// <param name="commandText">the T-SQL command</param>
	/// <returns>returns a datarow containing the resultset generated by the command</returns>
	public static DataRow ExecuteDatarow(String commandText)
	{return null;
		//return SqlHelperBase.ExecuteDatarow(GlobalConnectionString , commandText);
	}

	/// <summary>
	///  Execute a SqlCommand (that returns a 1x1 resultset) 
	///  
	/// e.g.:  
	///  int orderCount = (int)ExecuteScalar("select max(b) from a");
	/// </summary>
	/// <param name="commandText">SqlCommand Text</param>
	/// <returns> returns an object containing the value in the 1x1 resultset generated by the command</returns>
	public static Object ExecuteScalar(String commandText)
	{return null;
//		return SqlHelperBase.ExecuteScalar(GlobalConnectionString , commandText);
	}

	/// <summary>
	/// 对ExecuteScalar方法的扩展，将结果转化为String
	/// 本函数对查询结果为空时返回""，以防调用者未加检测而报错（这也是编写本函数的目的）
	/// </summary>
	/// <param name="commandText">sql语句，形如“select usrname from usr where id=3”</param>
	/// <returns>一个String值，若查询未得到一行记录，则返回""</returns>
	public static String ExecuteScalarString(String commandText)
	{return null;
		//return SqlHelperBase.ExecuteScalarString(GlobalConnectionString , commandText);
	}

	/// <summary>
	///  Execute a SqlCommand (that returns an int number for the table's max ID for insert) 
	///  
	/// e.g.:  
	///  int newUserIdForInsert = SqlHelper.GetPK("users","userId")
	///  
	/// </summary>
	/// <param name="tableName">table's name</param>
	/// <param name="columnName">columnName's name,the column must be the PK and not an increment column</param>
	/// <returns> returns an int value</returns>
	public static int GetPK(String tableName, String columnName)
	{return -1;
		//return SqlHelperBase.GetPK(GlobalConnectionString , tableName , columnName);
	}
	/// <summary>
	///  Execute a SqlCommand (that returns an int number for the table's max ID for insert) 
	///  
	/// e.g.:  
	///  int newUserIdForInsert = SqlHelper.GetPK("users","userId",20,30)
	///  取得在20-30之间的一个可插入值
	/// </summary>
	/// <param name="tableName">table's name</param>
	/// <param name="columnName">columnName's name,the column must be the PK and not an increment column</param>
	/// <param name="minValue"></param>
	/// <param name="maxValue"></param>
	/// <returns> returns an int value</returns>
	public static int GetPK(String tableName, String columnName, int minValue, int maxValue)
	{return -1;
		//return SqlHelperBase.GetPK(GlobalConnectionString , tableName , columnName , minValue , maxValue);
	}

	/// <summary>
	/// 取得指定表最后一次插入的ID，若无法取得，则返回int.MinValue
	/// 注意：未测试本函数对“某表同时有一自增长列和一主键列”的情形
	/// 注意：若某表的主键字段不是数字型（例如“用客户名称作为主键”的情形），则调用此函数可能引起运行时错误
	/// </summary>
	/// <param name="tableName">被指定的表</param>
	/// <returns>一个int型的值</returns>
	public static int GetInsertedPK(String tableName)
	{return -1;
		//return SqlHelperBase.GetInsertedPK(GlobalConnectionString , tableName);
	}


	/// <summary>
	/// 取得指定表的indexId,用于插入
	/// 返回一个可插入的String型值
	/// 因为此函数主要用于操作者在操作界面（例如添加新用户界面）上对“排序号”字段的默认值提供
	/// 所以返回String型
	/// </summary>
	/// <param name="tableName">表名，例如“usr”</param>
	/// <param name="columnName">列名，例如“indexId”</param>
	/// <param name="m_where">不带“where”关键字的条件语名，例如“deptId=3”表示“在单位ID为3的所有人员中的排序”</param>
	/// <returns>返回一个可插入的String型值(在已有的最大值上加100，若无对比，则返回100)</returns>
	public static String GetIndexId(String tableName,String columnName,String m_where)
	{return null;
		//return SqlHelperBase.GetIndexId(GlobalConnectionString , tableName , columnName ,m_where);
	}

	/// <summary>
	/// 取得指定表的indexId,用于插入
	/// 返回一个可插入的String型值
	/// 因为此函数主要用于操作者在操作界面（例如添加新用户界面）上对“排序号”字段的默认值提供
	/// 所以返回String型
	/// </summary>
	/// <param name="tableName">表名，例如“usr”</param>
	/// <param name="columnName">列名，例如“indexId”</param>
	/// <param name="m_where">不带“where”关键字的条件语名，例如“deptId=3”表示“在单位ID为3的所有人员中的排序”</param>
	/// <param name="amplitude">在已有的最大值上加该数作为返回值</param>
	/// <returns>返回一个可插入的String型值(在已有的最大值上加amplitude，若无对比，则返回amplitude)</returns>
	public static String GetIndexId(String tableName,String columnName,String m_where,int amplitude)
	{return null;
//		return SqlHelperBase.GetIndexId(GlobalConnectionString , tableName , columnName ,m_where , amplitude);
	}


	/// <summary>
	/// 判断是否在 tableName 表中已存在符合 m_where 条件的记录
	/// </summary>
	/// <param name="tableName">表名</param>
	/// <param name="m_where">where条件，不可包含“where”关键字</param>
	/// <returns>存在则返回true,否则返回false</returns>
	public static boolean DataRowExists(String tableName, String m_where)
	{return false;
		//return SqlHelperBase.DataRowExists(GlobalConnectionString , tableName , m_where);
	}

	/// <summary>
	/// 得到在 tableName 表中符合 m_where 条件的记录行数
	/// </summary>
	/// <param name="tableName">表名</param>
	/// <param name="m_where">where条件，不可包含“where”关键字</param>
	/// <returns>返回在 tableName 表中符合 m_where 条件的记录行数</returns>
	public static int DataRowCount(String tableName, String m_where)
	{return -1;
		//return SqlHelperBase.DataRowCount(GlobalConnectionString , tableName , m_where);
	}

	/// <summary>
	/// 利用数据库取一个GUID，注意每次获得的此值均是不同的
	/// </summary>
	public static String getGUID()
	{return null;
		
//			return SqlHelperBase.GUID(GlobalConnectionString);
	}

    /// <summary>
    /// 取得一单列多行结果，将结果用“,”号分隔返回一个字符串
    /// 例如：“select distinct tablename from tailorformcol”
    /// 返回：“zfw,sbw,affair,commfun,……”
    /// </summary>
    /// <param name="commandText"></param>
    /// <returns></returns>
    public static String GetResultString(String commandText)
    {return null;
//		return SqlHelperBase.GetResultString(GlobalConnectionString, commandText);
    }

	/// <summary>
	/// 拼凑多个DataTable为一个
	/// </summary>
	/// <param name="dataTables"></param>
	/// <returns></returns>
	public static DataTable PatchDataTable(List dataTables)
	{return null;
		//return SqlHelperBase.PatchDataTable(dataTables);
	}
	/// <summary>
	/// 拼凑多个DataTable为一个
	/// </summary>
	/// <param name="dataTables"></param>
	/// <param name="sortExpression">排序字符串</param>
	/// <returns></returns>
	public static DataTable PatchDataTable(List dataTables, String sortExpression)
	{return null;
//		return SqlHelperBase.PatchDataTable(dataTables, sortExpression);
	}
	/// <summary>
	/// 重新排序一个表，返回新表
	/// </summary>
	/// <param name="dt"></param>
	/// <param name="sortExpression"></param>
	/// <returns></returns>
	public static DataTable ReOrderByDataTable(DataTable dt, String sortExpression)
	{return null;
//		ArrayList a = new ArrayList();
//		a.Add(dt);
//		return PatchDataTable(a, sortExpression);
	}
}