package tablemeta;


import java.util.Date;
import java.util.Hashtable;

import com.xdarkness.common.data.DataRow;
import com.xdarkness.common.data.DataTable;
import com.xdarkness.common.util.StringUtil;

/**
 * TablesBasic ，所有Table类的基类
 */
public class TablesBasic {

	// Struct的后置变量
	private TableStruct _TableStruct = null;

	// ErrorInfo的后置变量
	protected String _ErrorInfo = null;

	// 实例化一个已存在的数据行时，此DataRow存储该行信息 L_DataRow的后置变量
	private DataRow _OldDataRow;
	// 存储待赋给数据库的新值
	// Insert 或 Update 时，用此 Hashtable 存储的信息进行语句生成
	protected Hashtable LDataNew = new Hashtable();
	private int _OperateUser = 0;
	private int _DutyUser = 0;
	// 用于识别数据操作的OperateGlobalId
	private String _OperateGlobalId = null;

	// LastUpdateSql 最后一次调用Update方法时所产生的SQL语句
	private String lastUpdateSql = null;

	// LastInsertSQL 的后置变量
	private String lastInsertSQL = null;

	/**
	 * 此类为基类，不提供无表名的实例化方法
	 */
	protected TablesBasic() {
	}

	/**
	 * 以指定的表名和DataRow实例化一个TablesBasic对象
	 * 
	 * @param m_TableName
	 *            表名
	 * @param m_dr
	 *            实例化的DataRow（一行数据），可以为null
	 */
	public TablesBasic(String m_TableName, DataRow m_dr) {
		init(m_TableName, m_dr);
	}

	/**
	 * 以指定的表名和主键实例化一个TablesBasic对象
	 * 
	 * @param m_TableName
	 *            表名
	 * @param m_id
	 *            该行数据的主键ID
	 */
	public TablesBasic(String m_TableName, Object m_id) {
		DataRow m_dr = null;
		if (m_id == null || m_id.toString().length() == 0
				|| m_id.toString().equals(String.valueOf(Integer.MIN_VALUE))) {
			init(m_TableName, m_dr);
			return;
		}
		try {
			String sql = "select * from " + m_TableName + " where "
					+ TableStructList.get(m_TableName).getPrimary() + "='"
					+ m_id.toString() + "'";
			m_dr = SqlHelper.ExecuteDatarow(sql);
		} catch (Exception ex) {
			m_dr = null;
		}
		init(m_TableName, m_dr);
	}

	// 取得当前表的TableStruct实例对象
	public TableStruct getStruct() {
		if (_TableStruct == null) {
			try {
				throw new Exception(
						"未对 TableBasic 类进行明确的Init方法调用，不能调用 TableBasic 类的 Struct 属性！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return _TableStruct;
	}

	// / 此类的实例在被调用、存储、改写时产生的最后一次错误String，若无错误，则返回null。
	// / 存取异常时抛出的错误信息值（无异常时此值应为null）
	// / 您可在调用Insert()、Update()等函数后加检测判断是否函数执行成功
	// / if (ErrorInfo == null) successed;
	// / else failed;
	public String getErrorInfo() {
		return _ErrorInfo;
	}

	// 实例化一个已存在的数据行时，此DataRow用于读取该行信息
	public DataRow getL_DataRow() {
		return _OldDataRow;
	}

	/**
	 * init方法，子类调用本类时需在构造函数中调用本函数
	 * 
	 * @param m_TableName
	 *            子类的表名
	 * @param m_dr
	 *            实例化的DataRow（一行数据），可以为null
	 */
	public boolean init(String m_TableName, DataRow m_dr) {
		if (TableStructList.get(m_TableName.toString().toLowerCase()) == null
				|| TableStructList.get(m_TableName.toString().toLowerCase())
						.getName().length() == 0) {
			try {
				throw new Exception("指定的表名不存在！");
			} catch (Exception e) {
				return false;
			}
		}
		_TableStruct = TableStructList
				.get(m_TableName.toString().toLowerCase());
		_OldDataRow = m_dr;

		return true;
	}

	/**
	 * 取一个Int值（尝试将字段值作为int型取出），无法取得时尝试返回其默认值，默认值也无法取得为int型，则返回int.MinValue
	 * 
	 * @param m_ColInfo
	 *            对应字段的ColInfo对象
	 * @return
	 */
	public int getInt(ColInfo m_ColInfo) {
		try {
			if (_OldDataRow == null
					|| _OldDataRow.get(m_ColInfo.getName()).toString() == null
					|| _OldDataRow.get(m_ColInfo.getName()).toString().length() == 0) {
				return getIntDefault(m_ColInfo);
			}
			return Integer.parseInt(_OldDataRow.get(m_ColInfo.getName())
					.toString().trim());
		} catch (Exception exception) {
			return getIntDefault(m_ColInfo);
		}
	}

	// / <summary>
	// / 取一个Int型字段的默认值
	// / </summary>
	// / <param name="m_ColInfo">对应字段的ColInfo对象</param>
	// / <returns></returns>
	private static int getIntDefault(ColInfo m_ColInfo) {
		try {
			return Integer.parseInt(m_ColInfo.getDefault());
		} catch (Exception ex) {
			return Integer.MIN_VALUE;
		}
	}

	// / <summary>
	// / 取一个String型字段的值，无法取得时则返回其默认值
	// / </summary>
	// / <param name="m_ColInfo">对应字段的ColInfo对象</param>
	// / <returns></returns>
	public String getString(ColInfo m_ColInfo) {
		try {
			if (_OldDataRow == null
					|| _OldDataRow.get(m_ColInfo.getName()).toString() == null)// ||
			// L_DataRow[m_ColInfo.getName()].toString().length()
			// ==
			// 0)
			{
				return getStringDefault(m_ColInfo);
			}
			if (SimpleTypes.getSimpleType(m_ColInfo.getXtype()) == SimpleTypes.DATETIME) {
				if (_OldDataRow == null
						|| _OldDataRow.get(m_ColInfo.getName()).toString() == null
						|| _OldDataRow.get(m_ColInfo.getName()).toString()
								.indexOf("1900") >= 0) {
					return getStringDefault(m_ColInfo);
				}
			}
			return _OldDataRow.get(m_ColInfo.getName()).toString();
		} catch (Exception ex) {
			return getStringDefault(m_ColInfo);
		}
	}

	// / <summary>
	// / 取一个String型字段的默认值
	// / </summary>
	// / <param name="m_ColInfo">对应字段的ColInfo对象</param>
	// / <returns></returns>
	private static String getStringDefault(ColInfo m_ColInfo) {
		if (SimpleTypes.getSimpleType(m_ColInfo.getXtype()) == SimpleTypes.DATETIME
				&& m_ColInfo.getDefault().toLowerCase().equals(
						"system.datetime.now.toString()")) {
			return new Date().toString();
		} else if (SimpleTypes.getSimpleType(m_ColInfo.getXtype()) == SimpleTypes.DATETIME
				&& m_ColInfo.getDefault().indexOf("1900") >= 0) {
			return "";
		} else {
			return m_ColInfo.getDefault();
		}
	}

	// / <summary>
	// / 取得一列的String型值，此方法主要用于取得“对于采用inner join
	// 方法产生的DataRow数据实例化本对象时，无法确定其ColInfo对象的col的值”
	// / 如果发现此字段为此表对象结构中所包含的字段，则返回调用重载的GetString(ColInfo m_ColInfo)方法所获得的String值
	// / 任何一种无法取得值的错误均会返回零长度字符串
	// / 此方法不会返回本列的默认值
	// / </summary>
	// / <param
	// name="m_colName">列名称，在本类的实例化中一般采用DataRow来实例化本数据，此处给出此列的名称</param>
	// / <returns>字符串，本列值.toString()</returns>
	public String getString(String m_colName) {
		if (this.getStruct().existsCol(m_colName)) {
			return getString(this.getStruct().getCol(m_colName));
		}
		try {
			if (_OldDataRow == null
					|| _OldDataRow.get(m_colName).toString() == null
					|| _OldDataRow.get(m_colName).toString().length() == 0) {
				return "";
			}
			return _OldDataRow.get(m_colName).toString();
		} catch (Exception ex) {
			return "";
		}
	}

	// / <summary>
	// / 取得主键的值，将其作为字符串返回
	// / </summary>
	public String getPKValue() {
		return getString(this.getStruct().getPrimary());
	}

	// / <summary>
	// / 尝试将值作为Double型返回，若无法返回，则尝试将其默认值作为Double型返回，若仍无法返回Double，则返回double
	// / </summary>
	// / <param name="m_ColInfo">对应字段的ColInfo对象</param>
	// / <returns></returns>
	public double getDouble(ColInfo m_ColInfo) {
		try {
			if (_OldDataRow == null
					|| _OldDataRow.get(m_ColInfo.getName()).toString() == null
					|| _OldDataRow.get(m_ColInfo.getName()).toString().length() == 0) {
				return getDoubleDefault(m_ColInfo);
			}
			return Double.parseDouble(_OldDataRow.get(m_ColInfo.getName())
					.toString());
		} catch (Exception exception) {
			return getDoubleDefault(m_ColInfo);
		}
	}

	// / <summary>
	// / 取一个Double型字段的默认值
	// / </summary>
	// / <param name="m_ColInfo">对应字段的ColInfo对象</param>
	// / <returns></returns>
	private static double getDoubleDefault(ColInfo m_ColInfo) {
		try {
			return Double.parseDouble(m_ColInfo.getDefault());
		} catch (Exception ex) {
			return Double.MIN_VALUE;
		}
	}

	// / <summary>
	// / 若某个字段是绑定下拉选框的，则可通过此方法取得其下拉选框的表现值
	// / 返回m_ColInfo所对应的字段值所指定的下拉选框选项表现值，多个值采用m_SplitChar字符分隔
	// / 若不是下拉选框，返回该字段的原始值
	// / </summary>
	// / <param name="m_ColInfo"></param>
	// / <param name="m_SplitChar"></param>
	// / <returns></returns>
	private String getCategoryValuesName(ColInfo m_ColInfo, String m_SplitChar) {
		return null;
		// if (m_ColInfo.getObjRemark().getCategoryid().length() < 36)
		// {
		// return this.getString(m_ColInfo);
		// }
		// return
		// CategoryViews.getChild(m_ColInfo.getObjRemark().getCategoryid()).valuesName(this.getString(m_ColInfo)
		// , m_SplitChar);
	}

	// / <summary>
	// / 若某个字段是绑定下拉选框的，则可通过此方法取得其下拉选框的表现值
	// / 返回m_ColName所对应的字段值所指定的下拉选框选项表现值(Chinaname)，多个值采用","字符分隔
	// / 若m_ColName不是本表的字段，返回""，若不是下拉选框，返回该字段的原始值
	// / </summary>
	// / <param name="m_ColInfo"></param>
	// / <returns></returns>
	private String GetCategoryValuesName(ColInfo m_ColInfo) {
		return getCategoryValuesName(m_ColInfo, ",");
	}

	// / <summary>
	// / 取得一个日期型字段的显示文本String
	// / 传递的参数为一个日期型字段的ColInfo对象，任何的错误均将返回零长度字符串
	// / </summary>
	// / <param name="m_ColInfo">日期型字段的ColInfo对象</param>
	// / <returns>日期型字段的显示文本String</returns>
	private String getDateShowString(ColInfo m_ColInfo) {
		return null;
		// return MyDate.getString(this.getString(m_ColInfo) ,
		// (MyDate.FmtTypes)(m_ColInfo.getObjRemark().Fmttypetime));
	}

	// / <summary>
	// / 根据字段的特点，分析并显示其需要显示为打印文本（非填写文本）时的内容
	// / 注意：尽量采用m_ColInfo对象进行值访问而不建议采用字段名进行值访问
	// / 例如：若是日期型，则可能根据其ColRemark的Fmttypetime属性来确定显示“2005年12月3日”还是显示“2005-12-03”
	// / </summary>
	// / <param name="m_ColName">字段名</param>
	// / <returns></returns>
	public String getShowString(String m_ColName) {
		if (this.getStruct().existsCol(m_ColName)) {
			return getShowString(this.getStruct().getCol(m_ColName));
		}
		return getString(m_ColName);
	}

	// / <summary>
	// / 根据字段的特点，分析并显示其需要显示为打印文本（非填写文本）时的内容
	// / 注意：尽量采用m_ColInfo对象进行值访问而不建议采用字段名进行值访问
	// / 例如：若是日期型，则可能根据其ColRemark的Fmttypetime属性来确定显示“2005年12月3日”还是显示“2005-12-03”
	// / </summary>
	// / <param name="m_ColInfo">该字段的m_ColInfo对象</param>
	// / <returns></returns>
	public String getShowString(ColInfo m_ColInfo) {
		if (m_ColInfo.getObjRemark().getCategoryid().length() >= 36) {
			return GetCategoryValuesName(m_ColInfo);
		}

		SimpleTypes simpleType = SimpleTypes
				.getSimpleType(m_ColInfo.getXtype());
		if (simpleType == SimpleTypes.DATETIME) {
			return getDateShowString(m_ColInfo);
		}
		if (simpleType == SimpleTypes.DOUBLE) {
			return String.valueOf(getDouble(m_ColInfo));
		}
		if (simpleType == SimpleTypes.INT) {
			return String.valueOf(getInt(m_ColInfo));
		}
		if (simpleType == SimpleTypes.TEXT || simpleType == SimpleTypes.VARCHAR) {
			return StringUtil.HtmlEncodeBR(getString(m_ColInfo));
		}

		return getString(m_ColInfo.getName());

	}

	// / <summary>
	// / 是否本对象已实例化为真实的数据库中的一行记录并且该记录未标识为逻辑删除
	// / </summary>
	public boolean isAvail() {
		if (this._OldDataRow == null) {
			return false;
		}
		if (this.getStruct().existsCol("delstatus")) {
			return this.getInt(this.getStruct().getCol("delstatus")) == 0;
		}
		return true;
	}

	// / <summary>
	// / 是否是已实例化的一行数据
	// / </summary>
	public boolean isExists() {
		return this._OldDataRow != null;
	}

	// #endregion 供子类调用的取 L_DataRow 的字段数据的方法

	// / <summary>
	// / 已通过AppendColValue方法加入的字段的数量
	// / </summary>
	public int getAppendedColsCount() {
		return LDataNew.size();
	}

	// / <summary>
	// / 插入或更新数据操作时调用此方法先对各字段进行赋值
	// / 参数分别为:字段对应的ColInfo对象,字段值
	// / （此函数还需判断所赋的值是否符合该字段的存储要求，功能未完成，但不影响使用）
	// / </summary>
	// / <param name="m_ColInfo">字段的ColInfo对象</param>
	// / <param name="m_newValue">字段的值</param>
	public void AppendColValue(ColInfo m_ColInfo, Object m_newValue) {
		// String m_colValue = m_newValue.toString();
		// if (this.getStruct().existsCol(m_ColInfo.getName()) == false)
		// {
		// throw new Exception("编码阶段产生的错误：表 " + this.getStruct().getName() + "
		// 中不存在字段 " + m_ColInfo.getName() + " ！");
		// }
		// // 判断所赋的值是否符合该字段的存储要求
		// // 未完成
		// if (m_ColInfo.getName().equals("adder")
		// || m_ColInfo.getName().equals("addtime")
		// || m_ColInfo.getName().equals("moder")
		// || m_ColInfo.getName().equals("modtime")
		// )
		// {
		// throw new
		// Exception("编码阶段或表单绑定阶段产生的错误：adder、addtime、moder、modtime这样的字段不需要进行额外赋值，系统会自动取当前时间及操作人员为其赋值
		// ！");
		// }
		//
		// if (LDataNew.containsKey(m_ColInfo.getName()))
		// {
		// LDataNew.remove(m_ColInfo.getName());
		// }
		// SimpleTypes simpleType =
		// SimpleTypes.getSimpleType(m_ColInfo.getXtype());
		// if (simpleType == SimpleTypes.INT || simpleType ==
		// SimpleTypes.DOUBLE)
		// {
		// m_colValue = m_colValue.trim();
		// }
		// if (simpleType == SimpleTypes.INT
		// && m_colValue.length() > 0
		// && m_ColInfo.getObjRemark().getCategoryid().length() > 0
		// &&
		// CategoryViews.Child(m_ColInfo.getObjRemark().getCategoryid()).IsExists)
		// {// 存放的是字典值
		// if (MyString.IsDouble(m_colValue) == false)
		// {
		// DataRow[] drs =
		// CategoryViews.Child(m_ColInfo.getObjRemark().getCategoryid()).DtValues.Select("chinaname='"
		// + MyString.SqlEncode(m_colValue).Replace(" " , "") + "'");
		// if (drs.length > 0)
		// {
		// m_colValue = drs[0].getString("refid");
		// }
		// else
		// {
		// m_colValue = "0";
		// }
		// }
		// }
		// else if (simpleType == SimpleTypes.DOUBLE &&
		// MyString.IsDouble(m_colValue) == false)
		// {
		// m_colValue = "0";
		// }
		//		
		// SqlType sqlType = SqlType.getSqlType(m_ColInfo.getXtype());
		// if (sqlType == SqlType.TINYINT && (MyString.IsInt(m_colValue) ==
		// false || Integer.parseInt(m_colValue) < 0 ||
		// Integer.parseInt(m_colValue) > 255))
		// {
		// m_colValue = "0";
		// }
		// LDataNew.put(m_ColInfo.getName(), m_colValue);
	}

	// / <summary>
	// / 插入或更新数据操作时可调用此方法清除对各字段已做的赋值操作
	// / </summary>
	public void clear() {
		LDataNew.clear();
		this._OperateGlobalId = null;
	}

	// #endregion 存储待赋给数据库的新值

	// #region 操作者及更新者
	// / <summary>
	// / 插入或更新数据操作时的实际操作者ID
	// / 例如：A以B的名义操作本条数据，则此变量应赋值为A用户的ID
	// / </summary>
	public int getOperateUser() {
		return _OperateUser;
	}

	public void setOperateUser(int value) {
		_OperateUser = value;
	}

	// / <summary>
	// / 代理人
	// / 插入或更新数据操作时的实际责任者ID
	// / 例如：A以B的名义操作本条数据，则此变量应赋值为B用户的ID
	// / 若未给DutyUser赋值，则系统默认DutyUser = OperateUser
	// / </summary>
	public int getDutyUser() {

		return _DutyUser > 0 ? _DutyUser : _OperateUser;
	}

	public void setDutyUser(int value) {
		_DutyUser = value;
	}

	// #endregion 操作者及更新者

	// #region 用于识别数据操作的OperateGlobalId
	// / <summary>
	// / 获取或设置用于识别数据批量操作的OperateGlobalId
	// /
	// 此属性的意义在于：当更新一次数据库时，可能向日志表中插入多行数据，为有效组织这些数据为“同一次保存按钮点按所触发的”，需要用此用于识别数据操作的OperateGlobalId标识为同一值
	// / </summary>
	public String getOperateGlobalId() {
		return null;
		// if (_OperateGlobalId == null)
		// {
		// _OperateGlobalId = SqlHelper.GUID;
		// }
		// return _OperateGlobalId;
	}

	public void setOperateGlobalId(String value) {
		_OperateGlobalId = value;

	}

	// / <summary>
	// / LastUpdateSql 最后一次调用Update方法时所产生的SQL语句
	// / </summary>
	public String getLastUpdateSql() {
		return lastUpdateSql;
	}

	// / <summary>
	// / 最后一次调用Insert方法时所产生的SQL语句
	// / </summary>
	public String getLastInsertSQL() {
		return lastInsertSQL;
	}

	// #endregion LastUpdateSql 最后一次调用Update方法时所产生的SQL语句

	// #region 数据插入操作函数
	// / <summary>
	// / 向数据库插入一行数据，在执行本函数前需调用 AppendColValue(colName,colValue) 方法 ，
	// / 并需对 OperateUser 进行赋值
	// / 若插入时出现异常，则ErrorInfo != null
	// /
	// / 此方法有待进一步加判断：例如，若为数字型，则插入值时无需 insert into table (intvalue) values
	// ('3')，而只要insert into table (intvalue) values(3)
	// / 诸如此类的细化操作，这有助于更多数据类型的支持，例如money类型可能在数据库语句中不能使用 insert into table
	// (moneyvalue) values ('3')，而只能是insert into table (moneyvalue) values (3)
	// /
	// /
	// 此方法还有如下一些自动赋值操作功能需增加：例如，如果表结构中存在adder字段，则赋值为operateusr，如果存在addtime或adddate字段，则赋值为system.datetime.now，对于moder、modtime、moddate等字段同
	// / 并需注意：如果编码者已经appendcolvalue相应的字段，则不应覆盖编码者所赋的值
	// / 并需注意：如果仅存在addtime而不存在adder，则可能此处的addtime另有含义，不能覆盖其值
	// / </summary>
	// / <returns>插入行的主键，返回为String型</returns>
	public String insert() {
		return singleInsert();
	}

	private String singleInsert() {
		return null;
		// lastInsertSQL = null;
		// String m_OperateGlobalId = this.getOperateGlobalId();
		// if (_OldDataRow != null)
		// {
		// _ErrorInfo = "编程阶段错误：不能使用一个已有的数据的Insert方法！";
		// clear();
		// return "";
		// }
		// if (!this.booleanNoConflict())
		// {
		// clear();
		// return "";
		// }
		// if (!this.booleanCorrectAppendValue())
		// {
		// clear();
		// return "";
		// }
		// String[] Cols = getStruct().getCols();
		// if (Cols == null)
		// {
		// _ErrorInfo = "代码错误，需要赋一个正确的 TableName，或者您直接调用了此类，而不是此类的一个子类！";
		// clear();
		// return "";
		// }
		// if (getOperateUser()<=0)
		// {
		// _ErrorInfo = "您需要对 OperateUser 进行赋值，方可进行插入操作！";
		// clear();
		// return "";
		// }
		// if (getDutyUser() <= 0)
		// {
		// setDutyUser(getOperateUser());
		// }
		// _ErrorInfo = null;
		// String m_columns = null;
		// String m_values = null;
		// for(int i=0;i<Cols.length;i++)
		// {
		// if (LDataNew.contains(Cols[i]))
		// {
		// if (m_columns != null)
		// {
		// m_columns += "," + Cols[i];
		// m_values += ",'" + MyString.SqlEncode(LDataNew[Cols[i]].toString()) +
		// "'";
		// }
		// else
		// {
		// m_columns = Cols[i];
		// m_values = "'" + MyString.SqlEncode(LDataNew[Cols[i]].toString()) +
		// "'";
		// }
		// }
		// }
		// if (m_columns == null)
		// {
		// _ErrorInfo = "您需要先调用 AppendColValue 方法，方可进行插入操作！";
		// LDataNew.clear();
		// return "";
		// }
		// if (Struct.existsCol("adder"))
		// {
		// m_columns += ",adder";
		// m_values += ",'" + this.OperateUser.toString() + "'";
		// }
		// if (Struct.existsCol("addtime"))
		// {
		// m_columns += ",addtime";
		// m_values += ",'" + System.DateTime.Now.toString() + "'";
		// }
		// if (Struct.existsCol("moder"))
		// {
		// m_columns += ",moder";
		// m_values += ",'" + this.OperateUser.toString() + "'";
		// }
		// if (Struct.existsCol("modtime"))
		// {
		// m_columns += ",modtime";
		// m_values += ",'" + System.DateTime.Now.toString() + "'";
		// }
		// if (Struct.existsCol("rdeptid") && LDataNew.contains("rdeptid") ==
		// false )
		// {
		// m_columns += ",rdeptid";
		// m_values += ",'" + UsrViews.Child(this.OperateUser).Deptid.toString()
		// + "'";
		// }
		// String sql = "";
		// String m_idtmp = "";
		//
		// if (LDataNew.containsKey(this.getStruct().getPrimary().getName()))
		// {
		// m_idtmp =
		// LDataNew[this.getStruct().getPrimary().getName()].toString();
		// }
		// if
		// (LDataNew.containsKey(this.getStruct().getPrimary().getName())==false
		// && this.getStruct().getPrimary().IsIncrement==false
		// && this.getStruct().getPrimary().SimpleType==ColInfo.SimpleTypes.INT)
		// {
		// // 如果不是自增长主键并且该主键为int型，并且该主键未赋值
		// m_idtmp = SqlHelper.ExecuteScalarString("select isnull(max(" +
		// this.getStruct().getPrimary().getName() + "),0)+1 as " +
		// this.getStruct().getPrimary().getName() + " from " +
		// this.getStruct().getName());
		// sql = "insert into " + Struct.getName()
		// + " (" + this.getStruct().getPrimary().getName() + "," + m_columns +
		// ") values ("
		// + "'" + m_idtmp + "'," + m_values + ")";
		// }
		// else if
		// (LDataNew.containsKey(this.getStruct().getPrimary().getName())==false
		// && this.getStruct().getPrimary().SqlType == ColInfo.SqlTypes.VARCHAR
		// && this.getStruct().getPrimary().length() >= 38 &&
		// this.getStruct().getPrimary().length() <= 50)
		// {
		// m_idtmp = SqlHelper.GUID;
		// // 如果认为该主键varchar型guid值，并且该主键未赋值
		// sql = "insert into " + Struct.getName()
		// + " (" + this.getStruct().getPrimary().getName() + "," + m_columns +
		// ") values ("
		// + "'" + m_idtmp + "'," + m_values + ")";
		// }
		// else
		// {
		// sql = "insert into " + Struct.getName()
		// + " (" + m_columns + ") values (" + m_values + ")";
		// }
		// try
		// {
		// SqlHelper.ExecuteNonQuery(sql);
		// lastInsertSQL = sql;
		// }
		// catch
		// {
		// SqlHelper.ExecuteNonQuery("insert into tablesbasicDebug (tbl,sqls)
		// values ('" + this.getStruct().getName() + "','" +
		// MyString.SqlEncode(sql) + "')");
		// // _ErrorInfo = "试图写入数据时出现错误，如果这个错误不是您预期的，请与开发人员联系！" + e.Source +
		// "\r\n" +
		// // e.Message + "\r\n" + e.toString();
		// _ErrorInfo = "试图写入数据时出现错误，如果这个错误不是您预期的，请与开发人员联系！" ;
		// return "";
		// }
		// finally
		// {
		// clear();
		// }
		//
		// if (m_idtmp.equals(""))
		// {
		// if (this.getStruct().getPrimary().IsIncrement)
		// {
		// // 如果是自增长型主键，不推荐使用
		// m_idtmp = SqlHelper.getInsertedPK(Struct.getName()).toString();
		// }
		// else if
		// (LDataNew.containsKey(this.getStruct().getPrimary().getName())==false
		// && this.getStruct().getPrimary().SimpleType==ColInfo.SimpleTypes.INT)
		// {
		// m_idtmp = SqlHelper.ExecuteScalarString("select max(" +
		// this.getStruct().getPrimary().getName() + ") from " +
		// this.getStruct().getName()).toString();
		// }
		// else if
		// (LDataNew.containsKey(this.getStruct().getPrimary().getName()))
		// {
		// m_idtmp =
		// LDataNew[this.getStruct().getPrimary().getName()].toString();
		// }
		// }
		// if (m_idtmp.equals("") == false)
		// {
		// Log.Add(OperateUser, DutyUser, m_OperateGlobalId, Struct.getName(),
		// m_idtmp, "0", "", "");
		// _OldDataRow = SqlHelper.ExecuteDatarow("select * from " +
		// Struct.getName() + " where " + Struct.getPrimary().getName() + " = '"
		// + m_idtmp.toString() + "'");
		// }
		// return m_idtmp;
	}

	// #endregion

	// #region 数据更新操作函数

	// / <summary>
	// / 更新本行数据，在执行本函数前需调用 AppendColValue(colName,colValue) 方法，
	// / 并需对 OperateUser 进行赋值
	// / 若更新时出现异常，则ErrorInfo != null
	// /
	// / 此方法有待进一步加判断：例如，若为数字型，则更新值时无需 update table set intvalue='3'，而只要update
	// table set intvalue=3
	// / 诸如此类的细化操作，这有助于更多数据类型的支持，例如money类型可能在数据库查询语句中不能使用update table set
	// moneyvalue='3'，而只能是update table set moneyvalue=3
	// / </summary>
	// / <returns>boolean型，若为true表示更新成功，否则更新失败</returns>
	public boolean Update() {
		return SingleUpdate();
	}

	private boolean SingleUpdate() {
		// String m_OperateGlobalId = this.OperateGlobalId;
		// if (L_DataRow == null)
		// {
		// _ErrorInfo = "编程阶段错误：不能使用一个不存在的数据的Update方法！";
		// clear();
		// return false;
		// }
		// if (!this.booleanNoConflict())
		// {
		// clear();
		// return false;
		// }
		// if (!this.booleanCorrectAppendValue())
		// {
		// clear();
		// return false;
		// }
		// String[] Cols = Struct.Cols;
		// if (Cols == null)
		// {
		// _ErrorInfo = "代码错误，需要赋一个正确的 TableName，或者您直接调用了此类，而不是此类的一个子类！";
		// clear();
		// return false;
		// }
		// if (OperateUser<=0)
		// {
		// _ErrorInfo = "您需要对 OperateUser 进行赋值，方可进行更新操作！";
		// clear();
		// return false;
		// }
		// if (DutyUser <= 0)
		// {
		// DutyUser = OperateUser;
		// }
		// _ErrorInfo = null;
		// String m_columns = null;
		// for(int i=0;i<Cols.length();i++)
		// {
		// if (LDataNew.contains(Cols[i]))
		// {
		// if (m_columns != null)
		// {
		// m_columns += "," + Cols[i] + " = '" +
		// MyString.SqlEncode(LDataNew[Cols[i]].toString()) + "'";
		// }
		// else
		// {
		// m_columns = Cols[i] + " = '" +
		// MyString.SqlEncode(LDataNew[Cols[i]].toString()) + "'";
		// }
		// }
		// }
		// if (m_columns == null)
		// {
		// _ErrorInfo = "您需要先调用 AppendColValue 方法，方可进行更新操作！";
		// clear();
		// return false;
		// }
		// if (Struct.existsCol("moder"))
		// {
		// m_columns += ",moder = '" + this.OperateUser.toString() + "'";
		// }
		// if (Struct.existsCol("modtime"))
		// {
		// m_columns += ",modtime = '" + System.DateTime.Now.toString() + "'";
		// }
		// if (Struct.existsCol("adder") &&
		// this.getInt(this.getStruct().Col("adder")) <= 0)
		// {
		// m_columns += ",adder = '" + this.OperateUser.toString() + "'";
		// }
		// if (Struct.existsCol("rdeptid") && LDataNew.contains("rdeptid") ==
		// false && this.getInt(this.getStruct().Col("rdeptid")) <= 0)
		// {
		// m_columns += ",rdeptid = " +
		// UsrViews.Child(this.OperateUser).Deptid.toString();
		// }
		// String sql = "";
		// try
		// {
		// sql = "update " + Struct.getName() + " set " + m_columns + " where "
		// + Struct.getPrimary().getName() + "='" +
		// L_DataRow[Struct.getPrimary().getName()] + "'";
		// SqlHelper.ExecuteNonQuery(sql);
		// }
		// catch
		// {
		// SqlHelper.ExecuteNonQuery("insert into tablesbasicDebug (tbl,sqls)
		// values ('" + this.getStruct().getName() + "','" +
		// MyString.SqlEncode(sql) + "')");
		// _ErrorInfo = "试图更新数据时出现错误，如果这个错误不是您预期的，请与开发人员联系！";
		// clear();
		// return false;
		// }
		//
		// for(int i=0;i<Cols.length();i++)
		// {
		// if (LDataNew.contains(Cols[i]))
		// {
		// Log.Add(OperateUser, DutyUser, m_OperateGlobalId,
		// Struct.getName(),L_DataRow[Struct.getPrimary().getName()],Cols[i],L_DataRow[Cols[i]],LDataNew[Cols[i]]);
		// }
		// }
		// clear();
		// lastUpdateSql = sql;
		// _OldDataRow = SqlHelper.ExecuteDatarow("select * from " +
		// Struct.getName() + " where " + Struct.getPrimary().getName() + "='" +
		// L_DataRow[Struct.getPrimary().getName()] + "'");
		return false;
	}

	// #endregion

	// #region 数据删除操作函数

	// / <summary>
	// / (逻辑)删除本行数据，在执行本函数前请为 int OperateUser 进行赋值，
	// / 此函数调用时将清除 已通过 AppendColValue 方法向本类赋的值
	// / 若出现异常，则ErrorInfo != null
	// / </summary>
	// / <returns>boolean型，若为true表示删除成功，否则删除失败</returns>
	public boolean Delete() {
		// if (this._OldDataRow == null)
		// {
		// _ErrorInfo = "代码错误，当前表 " + Struct.getName() + " 的本数据还没有保存入库，无法删除！";
		// return false;
		// }
		// String m_OperateGlobalId = this.OperateGlobalId;
		// clear();
		// this.OperateGlobalId = m_OperateGlobalId;
		// if (Struct.existsCol("delstatus"))
		// {
		// if (L_DataRow["delstatus"].toString().equals("0"))
		// {
		// this.AppendColValue(Struct.Col("delstatus"), 1);
		// return Update();
		// }
		// return true;
		// }
		// _ErrorInfo = "代码错误，当前表 " + Struct.getName() + " 没有 delstatus
		// 标识字段，这是逻辑删除数据所需要的！";
		return false;
	}

	// / <summary>
	// / (逻辑)恢复本行数据，在执行本函数前请为 int OperateUser 进行赋值，
	// / 此函数调用时将清除 已通过 AppendColValue 方法向本类赋的值
	// / 若出现异常，则ErrorInfo != null
	// / </summary>
	// / <returns>boolean型，若为true表示恢复成功，否则恢复失败</returns>
	public boolean Restore() {
		// if (this._OldDataRow == null)
		// {
		// _ErrorInfo = "代码错误，当前表 " + Struct.getName() + " 的本数据还没有保存入库，无法恢复！";
		// return false;
		// }
		// String m_OperateGlobalId = this.OperateGlobalId;
		// clear();
		// this.OperateGlobalId = m_OperateGlobalId;
		// if (Struct.existsCol("delstatus"))
		// {
		// if (L_DataRow["delstatus"].toString().equals("1"))
		// {
		// this.AppendColValue(Struct.Col("delstatus"), 0);
		// return Update();
		// }
		// return true;
		// }
		// _ErrorInfo = "代码错误，当前表 " + Struct.getName() + " 没有 delstatus
		// 标识字段，这是逻辑恢复数据所需要的！";
		return false;
	}

	// #endregion

	// #region 要添加、修改的数据是否不会发生主键（组合主键）冲突

	// / <summary>
	// / 要添加、修改的数据是否不会发生主键（组合主键）冲突
	// / 如果不与已有的数据发生冲突，即“可以添加”，则返回true，否则返回false
	// / </summary>
	// / <returns></returns>
	public boolean booleanNoConflict() {
		// String sql;
		// // 首先判断主键是否会发生冲突，或是否赋值合理
		// if (LDataNew.containsKey(this.getStruct().getPrimary().getName()))
		// {
		// if (this.getStruct().getPrimary().IsIncrement)
		// {
		// this._ErrorInfo = this.getStruct().RemarkInfo.Alias + " 的 " +
		// this.getStruct().getPrimary().ObjRemark.Alias + "(" +
		// this.getStruct().getPrimary().getName() + ") 是自增长字段，不能对其进行赋值！";
		// return false;
		// }
		// if
		// (!MyString.trimHtml(LDataNew[this.getStruct().getPrimary().getName()].toString()).equals(LDataNew[this.getStruct().getPrimary().getName()].toString()))
		// {
		// this._ErrorInfo = this.getStruct().RemarkInfo.Alias + " 的 " +
		// this.getStruct().getPrimary().ObjRemark.Alias + "(" +
		// this.getStruct().getPrimary().getName() + ") 不应包含特殊字符！";
		// return false;
		// }
		// sql = this.getStruct().getPrimary().getName() + " = '" +
		// LDataNew[this.getStruct().getPrimary().getName()].toString() + "' "
		// + (this._OldDataRow == null ? "" : (" and " +
		// this.getStruct().getPrimary().getName() + " <> '" +
		// L_DataRow[this.getStruct().getPrimary().getName()].toString() +
		// "'"));
		// if (SqlHelper.DataRowExists(this.getStruct().getName(),sql))
		// {
		// this._ErrorInfo = this.getStruct().RemarkInfo.Alias + " 的 " +
		// this.getStruct().getPrimary().ObjRemark.Alias + "(" +
		// this.getStruct().getPrimary().getName() + ") 的值冲突，当前提供的值是“" +
		// LDataNew[this.getStruct().getPrimary().getName()].toString() +
		// "”，该值在系统中已存在！";
		// return false;
		// }
		// }
		// // 没有组合主键，则返回不冲突（true）
		// if (this.getStruct().RemarkInfo.getPrimary()ex1.equals(""))
		// {
		// return true;
		// }
		// if
		// (booleanPrimaryExNoConflict(this.getStruct().RemarkInfo.getPrimary()ex1)
		// == false)
		// {
		// return false;
		// }
		// // 没有第二组合主键，则返回不冲突（true）
		// if (this.getStruct().RemarkInfo.getPrimary()ex2.equals(""))
		// {
		// return true;
		// }
		// if
		// (booleanPrimaryExNoConflict(this.getStruct().RemarkInfo.getPrimary()ex2)
		// == false)
		// {
		// return false;
		// }
		// // 下面其实可再做一些判断，即第一组合主键与第二组合主键之间本身不冲突
		// return true;
		return false;
	}

	private boolean booleanPrimaryExNoConflict(String m_primaryEx) {
		// String[] primaryex = m_primaryEx.toLowerCase().Split('+');
		//
		// // splash 2006-4-6
		// String[] primaryexs = m_primaryEx.toLowerCase().Split('+');
		// String m_primaryExChs = "";
		// for (int i=0;i<primaryexs.length();i++)
		// {
		// primaryexs[i] = this.getStruct().Col(primaryexs[i]).ObjRemark.Alias;
		// m_primaryExChs += (m_primaryExChs.length() > 0 ? "＋" : "") +
		// primaryexs[i];
		// }
		//	
		// // 确信是添加数据的操作，则需判断所有组合主键字段不允许为空
		// if (this._OldDataRow == null)
		// {
		// for (int i=0;i<primaryex.length();i++)
		// {
		// if (!LDataNew.containsKey(primaryex[i]) &&
		// primaryex[i].toLowerCase().equals("adder") == false)
		// {
		// this._ErrorInfo = "向 " + this.getStruct().RemarkInfo.Alias + " 添加数据时
		// " + this.getStruct().Col(primaryex[i]).ObjRemark.Alias + "(" +
		// primaryex[i] + ") 的值需要提供，但现在未能提供！";
		// return false;
		// }
		// }
		// }
		// String sql = "";
		//
		// // splash 2006-4-6
		// String sqls = "";
		//
		// for (int i=0;i<primaryex.length();i++)
		// {
		// if (i > 0)
		// {
		// sql += " and ";
		//
		// // splash 2006-4-6
		// sqls += " 并且 ";
		// }
		// if (LDataNew.containsKey(primaryex[i]))
		// {
		// if (LDataNew[primaryex[i]].toString().length() == 0)
		// {
		// this._ErrorInfo = this.getStruct().RemarkInfo.Alias + " 的 " +
		// this.getStruct().Col(primaryex[i]).ObjRemark.Alias + "(" +
		// primaryex[i] + ") 的值中不允许为空，但您现试图将其清空！";
		// return false;
		// }
		// // if (LDataNew[primaryex[i]].toString().indexOf(" ")>=0)
		// // {
		// // this._ErrorInfo = "组合主键字段 " +
		// // this.getStruct().Col(primaryex[i]).ObjRemark.Alias + "(" +
		// // primaryex[i] + ") 的值中不能有空格，现在提供的值是“" +
		// // LDataNew[primaryex[i]].toString() + "”！";
		// // return false;
		// // }
		// if
		// (!MyString.trimHtml(LDataNew[primaryex[i]].toString()).equals(LDataNew[primaryex[i]].toString()))
		// {
		// this._ErrorInfo = this.getStruct().RemarkInfo.Alias + " 的 " +
		// this.getStruct().Col(primaryex[i]).ObjRemark.Alias + "(" +
		// primaryex[i] + ") 的值不应包含特殊字符！";
		// return false;
		// }
		// sql += primaryex[i] + "='" + LDataNew[primaryex[i]].toString() + "'";
		//				
		// // splash 2006-4-6
		// sqls += primaryexs[i] + " 为 ＂" + LDataNew[primaryex[i]].toString() +
		// "＂";
		// }
		// else
		// {
		// if (this._OldDataRow != null)
		// {
		// sql += primaryex[i] + "='" + L_DataRow[primaryex[i]].toString() +
		// "'";
		//
		// // splash 2006-4-6
		// sqls += primaryexs[i] + " 为 ＂" + L_DataRow[primaryex[i]].toString() +
		// "＂";
		// }
		// else if (primaryex[i].toLowerCase().equals("adder"))
		// {
		// sql += primaryex[i] + "=" + this.OperateUser.toString();
		//
		// // splash 2006-4-6
		// sqls += primaryexs[i] + " 为 ＂" + this.OperateUser.toString() + "＂";
		// }
		// }
		// }
		// String m_where = sql;
		// sql = m_where + (this._OldDataRow == null ? "" : (" and " +
		// this.getStruct().getPrimary().getName() + " <> '" +
		// L_DataRow[this.getStruct().getPrimary().getName()].toString() + "'"))
		// + (this.getStruct().existsCol("delstatus")? " and delstatus=0" : "");
		// if (SqlHelper.DataRowExists(this.getStruct().getName(),sql))
		// {
		// // this._ErrorInfo = this.getStruct().RemarkInfo.Alias + "的组合主键字段 " +
		// // this.getStruct().RemarkInfo.getPrimary()ex1 + "
		// 的值冲突（不允许同时相同），您当前提供的是“" +
		// // m_where +
		// // "”！";
		//			
		// // splash 2006-4-6
		// this._ErrorInfo = this.getStruct().RemarkInfo.Alias + " 的 “" +
		// m_primaryExChs + "” 的值冲突（不允许同时相同），您当前提供的是“" + sqls + "”！";
		//
		// return false;
		// }
		// return true;
		return false;
	}

	// #endregion 要添加、修改的数据是否会发生主键（组合主键）冲突

	// #region 要添加、修改的数据赋值是否合理

	// / <summary>
	// / 要添加、修改的数据赋值是否合理，注意：此方法暂未完成
	// / </summary>
	// / <returns></returns>
	// / @splash 2006-4-8 修改此方法
	// / @wzw 2006-4-22
	// 注释此方法，因为判断过严，现将其错误检测机制放在Sky.Business.PhilePageExcelAspx.cs中完成
	public boolean booleanCorrectAppendValue() {
		// if ("1".equals("1"))
		// {
		// return true;
		// }
		//
		// String[] Cols = Struct.Cols;
		// String Errors = "此表中：\r\n\r\n";
		// String Colname = "";
		//		
		// boolean m_isInsert = (this._OldDataRow == null);
		// int intTableType = Struct.RemarkInfo.Tabletype;
		// boolean flag = true;
		//
		// if(intTableType > 0)
		// {
		// foreach(DictionaryEntry objValue in LDataNew)
		// {
		// if(objValue.Value.toString().trim().equals("") == false &&
		// Struct.Col(objValue.Key.toString()).ObjRemark.Openout > 0)
		// {
		// flag = false;
		// }
		// }
		//
		// if(flag == false)
		// {
		// for(int i=0;i<Cols.length();i++)
		// {
		// Colname = Struct.Cols[i];
		//
		// if(LDataNew.containsKey(Colname))
		// {
		// if(Struct.Col(Colname).IsNullAble == false &&
		// LDataNew[Colname].toString().trim() == String.Empty)
		// {
		// Errors = Errors + "字段“" +
		// this.getStruct().Col(Struct.Cols[i]).ObjRemark.Alias +
		// "”的值没有填写，请填写！\r\n";
		// }
		// else
		// {
		// if(this.getStruct().Col(Colname).ObjRemark.Required == 1 &&
		// LDataNew[Colname].toString().trim() == String.Empty)
		// {
		// Errors = Errors + "字段“" +
		// this.getStruct().Col(Struct.Cols[i]).ObjRemark.Alias +
		// "”的值没有填写，请填写！\r\n";
		// }
		// }
		// }
		// }
		// }
		// else
		// {
		// return false;
		// }
		// }
		//
		// // if(Struct.RemarkInfo.Tabletype > 0)
		// // {
		// // for(int i=0;i<Cols.length();i++)
		// // {
		// // Colname = Struct.Cols[i];
		// //
		// // if(LDataNew.containsKey(Colname))
		// // {
		// // if(m_isInsert)
		// // {
		// // if(Struct.Col(Colname).HaveDefault == false ||
		// Colname.equals("dc"))
		// // {
		// // if(LDataNew.containsKey(Colname))
		// // {
		// // if(Struct.Col(Colname).IsNullAble == false &&
		// LDataNew[Colname].toString() ==
		// // String.Empty)
		// // {
		// // Errors = Errors + "字段“" +
		// this.getStruct().Col(Colname).ObjRemark.Alias +
		// // "”的值没有填写，请填写！\n";
		// // }
		// // else
		// // {
		// // if(this.getStruct().Col(Colname).ObjRemark.Required == 1 &&
		// // LDataNew[Colname].toString() == String.Empty)
		// // {
		// // Errors = Errors + "字段“" +
		// // this.getStruct().Col(Struct.Cols[i]).ObjRemark.Alias +
		// // "”的值没有填写，请填写！\n";
		// // }
		// // }
		// // }
		// // }
		// // }
		// // else
		// // {
		// // if(Struct.Col(Colname).IsNullAble == false &&
		// LDataNew[Colname].toString() ==
		// // String.Empty)
		// // {
		// // Errors = Errors + "字段“" +
		// // this.getStruct().Col(Struct.Cols[i]).ObjRemark.Alias +
		// // "”的值没有填写，请填写！\n";
		// // }
		// // else
		// // {
		// // String s = LDataNew[Colname].toString();
		// // if(this.getStruct().Col(Colname).ObjRemark.Required == 1 &&
		// // LDataNew[Colname].toString() == String.Empty)
		// // {
		// // Errors = Errors + "字段“" +
		// // this.getStruct().Col(Struct.Cols[i]).ObjRemark.Alias +
		// // "”的值没有填写，请填写！\n";
		// // }
		// // }
		// // }
		// // }
		// // }
		// // }
		//			
		// this._ErrorInfo = (Errors.equals("此表中：\r\n\r\n") ? null : Errors);
		// return (Errors.equals("此表中：\r\n\r\n") ? true : false);
		return false;
	}

	// #endregion 要添加、修改的数据赋值是否合理

	// #region 根据指定的条件查找指定表的一个DataTable
	// / <summary>
	// / 根据指定的条件查找指定表的一个DataTable
	// / </summary>
	// / <param name="m_wheres">where 子句，例如"id=1 and
	// lastlogintime>='2001-1-1'"，建议不包含"where"关键字</param>
	// / <param name="m_orders">order 子句，例如"id desc,lastlogintime
	// asc"等，建议不包含"order by"关键字</param>
	// / <returns></returns>
	public DataTable DataTables(String m_wheres, String m_orders) {
		String m_where = (m_wheres == null || m_wheres.length() <= 3) ? "1=1"
				: m_wheres.toLowerCase().trim();
		String m_order = (m_orders == null || m_orders.trim().length() == 0) ? ""
				: m_orders.toLowerCase().trim();
		if (m_where.indexOf("where ") == 0) {
			m_where = m_where.substring(5).trim();
		}
		if (m_order.length() > 0 && m_order.indexOf("order ") != 0) {
			m_order = "order by " + m_order;
		} else {
			m_order = "order by " + this.getStruct().getPrimary();
		}
		String sql = "select * from " + this.getStruct().getName() + "  where "
				+ m_where + " " + m_order;
		return SqlHelper.ExecuteDatatable(sql);
	}

	// #endregion 根据指定的条件查找指定表的一个DataTable

	// #region 对本行数据的扩展属性操作

	// / <summary>
	// / 取得本行数据的一个Config
	// / </summary>
	// / <param name="m_Itemname">配置项的键名</param>
	// / <returns>Configrow对象，若键名不允许注册，则返回的Configrow对象为 new
	// Configrow()</returns>
	// public Configrow Config(Object m_Itemname)
	// {
	// if (this.IsExists == false)
	// {
	// return new Configrow();
	// }
	// String itemname = m_Itemname.toString().toLowerCase();
	// Configrow config = ConfigrowViews.Child(this.getStruct().getName() ,
	// this.PKValue , itemname);
	// if (config.IsExists)
	// {
	// return config;
	// }
	// Categoryvalue categoryvalue = CONSTCategory.DEF_CONFIG.Value(itemname);
	// if (categoryvalue.IsExists == false ||
	// categoryvalue.Extchar2.toLowerCase().equals(this.getStruct().getName())
	// == false)
	// {
	// this._ErrorInfo = "未能为表“" + this.getStruct().getName() + "”创建配置项“" +
	// itemname + "”，因为系统未注册此配置项！";
	// return config;
	// }
	// if (this.OperateUser > 0)
	// {
	// config.OperateUser = this.OperateUser;
	// }
	// else if (this.getStruct().getName().equals("usr"))
	// {
	// config.OperateUser = this.getInt(this.getStruct().getPrimary());
	// }
	// else if (this.getStruct().existsCol("adder"))
	// {
	// config.OperateUser = this.getInt(this.getStruct().Col("adder"));
	// }
	// else if (this.getStruct().existsCol("moder"))
	// {
	// config.OperateUser = this.getInt(this.getStruct().Col("moder"));
	// }
	// else
	// {
	// config.OperateUser = 1;
	// }
	// config.AppendColValue(config.ItemnameColInfo , itemname);
	// config.AppendColValue(config.TablenameColInfo ,
	// this.getStruct().getName());
	// config.AppendColValue(config.RowidColInfo , this.PKValue);
	// config.AppendColValue(config.ItemvalueColInfo , categoryvalue.Extchar1);
	// config.Insert();
	// ConfigrowViews.clear();
	// return ConfigrowViews.Child(this.getStruct().getName() , this.PKValue ,
	// itemname);
	// }

	// / <summary>
	// / 设定一个配置项
	// / </summary>
	// / <param name="m_Itemname">配置项键</param>
	// / <param name="m_Itemvalue">值</param>
	// / <returns>true or false，在为false时可查看ErrorInfo</returns>
	// public boolean ConfigSave(Object m_Itemname, Object m_Itemvalue)
	// {
	// String itemname = m_Itemname.toString().toLowerCase();
	// String itemvalue = m_Itemvalue.toString();
	//
	// if (this.OperateUser <= 0)
	// {
	// if (this.getStruct().getName().equals("usr"))
	// {
	// this.OperateUser = Integer.parseInt(this.getPKValue());
	// }
	// else
	// {
	// this._ErrorInfo = "您需要先对 OperateUser 进行赋值，方可进行更新 Config 的操作！";
	// return false;
	// }
	// }
	// Configrow config = Config(itemname);
	// if (config.IsExists == false)
	// {
	// return false;
	// }
	// if (config.Itemvalue.toLowerCase().equals(itemvalue.toLowerCase()))
	// {
	// return true;
	// }
	// config.OperateUser = this.OperateUser;
	// config.AppendColValue(config.ItemvalueColInfo , itemvalue);
	// config.Update();
	// ConfigrowViews.clear();
	// return true;
	// }

	// #endregion 对本行数据的扩展属性操作

	// #region 静态方法：根据指定的表名、列名、字段实际存储值，取得其ShowString
	// / <summary>
	// / 静态方法：根据指定的表名、列名、字段实际存储值，取得其ShowString
	// / </summary>
	// / <param name="m_tablename">表名</param>
	// / <param name="m_colname">列名</param>
	// / <param name="m_colvalue">字段实际存储值</param>
	// / <returns>String</returns>
	public static String GetShowStringByTableColValue(String m_tablename,
			String m_colname, String m_colvalue) {
		// if (ColInfos.exists(m_tablename , m_colname) == false)
		// {
		// return m_colvalue;
		// }
		// DataTable m_dt = new DataTable(m_tablename.toLowerCase());
		// m_dt.Columns.Add(m_colname);
		// String[] m_array = {m_colvalue};
		// m_dt.Rows.Add(m_array);
		// TablesBasic m_tb = new TablesBasic(m_tablename , m_dt.get(0));
		// return m_tb.getShowString(m_colname);
		return null;
	}

	// #endregion 静态方法：根据指定的表名、列名、字段实际存储值，取得其ShowString

	// / <summary>
	// / 将该已存在的数据行转化为DataTable
	// / </summary>
	// / <returns></returns>
	public DataTable ConvertToDataTable() {
		// if (!this.isExists())
		// {
		// return new DataTable();
		// }
		// DataTable dt = this._OldDataRow.Table;
		// if (dt.Rows.Count == 0)
		// {
		// dt.Rows.Add(this._OldDataRow.ItemArray);
		// }
		// return dt;
		return null;
	}
}
