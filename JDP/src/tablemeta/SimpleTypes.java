package tablemeta;


/**
 * SimpleTypes，提供对数据库中的某列表现在类当中以何种类型变量来体现
 * */ 
public enum SimpleTypes
{
	VARCHAR,// char 型 或 varchar 型
	TEXT,// text 型
	DATETIME,// datetime 型
	INT,// tinyint,int,bit,bigint
	DOUBLE;// real,money,float,decimal,numeric
	
	/**
	 * 根据传入的xtype，得到SimpleType
	 * 依Xtype取得该列表现在类中时，一般以何种类型来体现
	 * */
	public static SimpleTypes getSimpleType(int m_xtype)
	{
		switch(m_xtype)
		{
			case 167:
			case 175:
				return SimpleTypes.VARCHAR;
			case 35:
				return SimpleTypes.TEXT;
			case 61:
				return SimpleTypes.DATETIME;
			case 48:
			case 56:
			case 104:
			case 127:
				return SimpleTypes.INT;
			case 59:
			case 60:
			case 62:
			case 106:
			case 108:
				return SimpleTypes.DOUBLE;
			default:
				return SimpleTypes.VARCHAR;
		}
	}
}


