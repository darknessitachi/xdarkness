package tablemeta;

/**
 * SqlTypes，在数据库当中以何种类型存储
 */
public enum SqlType {
	TEXT, TINYINT, INT, REAL, MONEY, DATETIME, FLOAT, BIT, DECIMAL, NUMERIC, BIGINT, VARCHAR, CHAR;

	/**
	 * 依Xtype取得该列表现在数据库中时，其以何种类型来存储
	 */
	public static SqlType getSqlType(int m_xtype) {
		switch (m_xtype) {
		case 35:
			return SqlType.TEXT;
		case 48:
			return SqlType.TINYINT;
		case 56:
			return SqlType.INT;
		case 59:
			return SqlType.REAL;
		case 60:
			return SqlType.MONEY;
		case 61:
			return SqlType.DATETIME;
		case 62:
			return SqlType.FLOAT;
		case 104:
			return SqlType.BIT;
		case 106:
			return SqlType.DECIMAL;
		case 108:
			return SqlType.NUMERIC;
		case 127:
			return SqlType.BIGINT;
		case 167:
			return SqlType.VARCHAR;
		case 175:
			return SqlType.CHAR;
		default:
			return SqlType.VARCHAR;
		}
	}
}
