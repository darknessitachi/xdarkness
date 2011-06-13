package tablemeta;

/// 应用程序使用过程中操作日志统一处理程序。
/// 对各表的各行数据的操作写入日志。
public class Log
{
	/// <summary>
	/// 本类是静态类，故不提供实例化方法
	/// </summary>
	private Log()
	{
	}

	/// <summary>
	/// 确定该 tableName 的 colName 是否需要写入日志。
	/// </summary>
	/// <param name="tableName"></param>
	/// <param name="colName"></param>
	/// <returns>true or false</returns>
//	public static bool ExistsDic(string tableName,string colName)
//	{
//		if ( colName.Equals("0") && TablesremarkViews.Child(tableName).Tracelog > 0 )
//		{
//			//添加数据，此时colName = "0"
//			return true;
//		}
//		else if (tableName.ToLower().Equals("usr") && colName.Length == 1 && MyString.IsInt(colName) )
//		{
//			//登录或初始化密码等安全日志
//			return true;
//		}
//		else
//		{
//			//字段更新操作日志
//			return ColsremarkViews.Child(tableName , colName).Tracelog > 0;
//		}
//	}
//	/// <summary>
//	/// 向日志表插入一行操作日志
//	/// 注意：colName若赋值为0-9为保留信息，一般不是真实的字段
//	/// 其中目前已使用：0-插入该行数据
//	/// </summary>
//	/// <param name="operateUser">操作者ID</param>
//	/// <param name="dutyUser">责任者ID</param>
//	/// <param name="m_OperateGlobalId">操作序列ID</param>
//	/// <param name="tableName">表名</param>
//	/// <param name="rowId">行ID</param>
//	/// <param name="colName">列名</param>
//	/// <param name="oldValue">原值</param>
//	/// <param name="newValue">新值</param>
//	/// <returns></returns>
//	public static bool Add(object operateUser, object dutyUser, string m_OperateGlobalId, string tableName, object rowId, string colName, object oldValue, object newValue)
//	{
//		if (ExistsDic(tableName,colName) == false)
//			return false;
//		string oldValueE = oldValue.ToString().Trim();
//		string newValueE = newValue.ToString().Trim();
//		if (ColInfos.Get(tableName , colName).SimpleType == ColInfo.SimpleTypes.DOUBLE || ColInfos.Get(tableName , colName).SimpleType == ColInfo.SimpleTypes.INT)
//		{
//			if (MyString.IsDouble(newValueE) == false)
//			{
//				newValueE = "0";
//			}
//		}
//
//
//		//细化
//		if (colName.Length>1 && ColInfos.Get(tableName , colName).SqlType == ColInfo.SqlTypes.DATETIME && MyDate.GetString(oldValueE , MyDate.FmtTypes.NO_Convert).Equals(MyDate.GetString(newValueE , MyDate.FmtTypes.NO_Convert)))
//		{
//			//日期型值相等，比如传递过来2005-4-3 与 2005-4-3 0:00:00
//			return false;
//		}
//		else if (colName.Length>1 && ( ColInfos.Get(tableName , colName).SimpleType == ColInfo.SimpleTypes.DOUBLE || ColInfos.Get(tableName , colName).SimpleType == ColInfo.SimpleTypes.INT) && Math.Abs(Double.Parse(oldValueE) - Double.Parse(newValueE)) < 0.0000001)
//		{
//			//数字型值相等，比如传递过来0 与 0.00
//			return false;
//		}
//		else if ( colName.Length>1 && MyString.BooleanChanged(oldValueE , newValueE) == false)
//		{
//			return false;
//		}
//
//		string sql = "insert into operatelog (operateUser,dutyUser,OperateGlobalId,tableName,rowId,colName,oldValue,newValue,logTime) values (" + operateUser + "," + dutyUser + ",'" + m_OperateGlobalId + "','" + tableName + "','" + MyString.SqlEncode(rowId.ToString()) + "','" + colName + "','" + MyString.SqlEncode(oldValueE) + "','" + MyString.SqlEncode(newValueE) + "','" + System.DateTime.Now + "')";
//		SqlHelper.ExecuteNonQuery(sql);
//		return true;
//	}
}
