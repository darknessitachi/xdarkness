package tablemeta;

/**
 * 存储Sql Server数据库中的表的一列的信息(每一列作为一个实例)
 */
public class ColInfo {

	public static final String EnumShowType_DROPDOWNLIST = "DROPDOWNLIST";
	public static final String EnumShowType_FILEFIELD = "FILEFIELD";
	public static final String EnumShowType_MULTITEXTEDIT = "MULTITEXTEDIT";
	public static final String EnumShowType_NUMERICTEXTEDIT = "NUMERICTEXTEDIT";
	public static final String EnumShowType_DATETEXTEDIT = "DATETEXTEDIT";
	public static final String EnumShowType_TEXTEDIT = "TEXTEDIT";

	private String tableName;
	private String name;
	private int xtype;
	private int length;
	private boolean isNullAble;
	private boolean isPK;
	private boolean haveDefault;
	private String remark;
	private String _default;
	private boolean isIncrement;
	private String _ShowType;

	/**
	 * 初始化一个本类的实例 参数为Sql Server 的 syscolumns 中该行的信息.
	 */
	public ColInfo(String m_tablename, String m_name, int m_xtype,
			int m_length, int m_isnullable, int m_havedefault, int m_ispk,
			int m_isincrement, String m_remark, String m_default) {
		tableName = m_tablename.toLowerCase();
		name = m_name.toLowerCase();
		xtype = m_xtype;
		length = m_length;
		isNullAble = (m_isnullable == 1);
		haveDefault = (m_havedefault == 1);
		isPK = (m_ispk == 1);
		isIncrement = (m_isincrement == 1);
		remark = m_remark;
		_default = m_default;
	}

	/**
	 * 本列所从属的表的名称
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 本列名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 本列类型代码，存储于syscolumns表中的xtype列值
	 */
	public int getXtype() {
		return xtype;
	}

	/**
	 * 条件筛选器用到
	 * 可能返回的类型为：DROPDOWNLIST，FILEFIELD，MULTITEXTEDIT，NUMERICTEXTEDIT，DATETEXTEDIT，TEXTEDIT
	 */
	public String getShowType() {
		if (_ShowType != null) {
			return _ShowType;
		}
		if (this.getObjRemark().getCategoryid().trim().length() > 0) {
			_ShowType = EnumShowType_DROPDOWNLIST;
			return _ShowType;
		}
		if (this.name.indexOf("upload") == 0) {
			_ShowType = EnumShowType_FILEFIELD;
			return _ShowType;
		}
		SqlType sqlType = SqlType.getSqlType(this.xtype);
		if (sqlType == SqlType.TEXT) {
			_ShowType = EnumShowType_MULTITEXTEDIT;
			return _ShowType;
		} else if (sqlType == SqlType.TINYINT || sqlType == SqlType.INT
				|| sqlType == SqlType.REAL || sqlType == SqlType.FLOAT
				|| sqlType == SqlType.BIT || sqlType == SqlType.DECIMAL
				|| sqlType == SqlType.NUMERIC || sqlType == SqlType.BIGINT) {
			_ShowType = EnumShowType_NUMERICTEXTEDIT;
			return _ShowType;
		} else if (sqlType == SqlType.DATETIME) {
			_ShowType = EnumShowType_DATETEXTEDIT;
			return _ShowType;
		} else {
			if (this.getObjRemark().getMultilines() > 0)
				_ShowType = EnumShowType_MULTITEXTEDIT;
			else
				_ShowType = EnumShowType_TEXTEDIT;
			return _ShowType;
		}

	}

	/**
	 * 本列数据长度,一般情况下仅当本列的Xtype值为167或175时您需要关注此值
	 */
	public int getLength() {
		return length;
	}

	/**
	 * 允许为空？true：false
	 */
	public boolean isNullAble() {
		return isNullAble;
	}

	/**
	 * 有默认值？true：false
	 */
	public boolean isHaveDefault() {
		return haveDefault;
	}

	/**
	 * 是本表的主键？true：false
	 */
	public boolean isPK() {
		return isPK;
	}

	/**
	 * 是自增长字段？true：false
	 */
	public boolean isIncrement() {
		return isIncrement;
	}

	/**
	 * 本列的备注信息
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 本列的默认值信息
	 */
	public String getDefault() {
		return _default;
	}

	/**
	 * 重载的ToString()方法,返回本Columns的Name
	 */
	public String toString() {
		return name;
	}

	/**
	 * 本字段的Colsremark对象
	 */
	public Colsremark getObjRemark() {
		return ColsremarkViews.getChild(tableName, name);
	}
}
