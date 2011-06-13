package tablemeta;

import java.util.Hashtable;

/**
 * ColInfos，一个静态的Hashtable，键名为“表名 字段名”，键值为一个ColInfo类的实例化对象 提供对某表的某字段的属性的读取
 */
public class ColInfos {

	// 一个静态的Hashtable，键名为“表名 字段名”，键值为一个ColInfo类的实例化对象
	private static Hashtable hashtable = new Hashtable();

	// 清除hashtable
	public static void clear() {
		hashtable.clear();
	}

	/**
	 * 根据指定的表名、指定的字段名，返回一个ColInfo类的实例化对象 若该信息未曾存储于本类中，则返回null
	 */
	public static ColInfo get(String m_TableName, String m_ColName) {
		if (!exists(m_TableName, m_ColName)) {
			// /////////////////////////////////////////////
			// 待修改:当第一次加载时,hashtable[" "]没有初始化,所以返回一个未定义的colinfo,导致错误
			// ///////////////////////////////////////////
			// if (!Exists("",""))
			// {
			// hashtable.Add(" ",new ColInfo("","",0,0,0,0,0,0,"",""));
			// }

			return (ColInfo) (hashtable.get(" "));
		}
		return (ColInfo) (hashtable.get(m_TableName.toLowerCase() + " "
				+ m_ColName.toLowerCase()));
	}

	/**
	 * 将指定的表、指定的字段的ColInfo类的实例存入本类中
	 */
	public static void add(ColInfo m_ColInfo) {
		if (hashtable.containsKey(m_ColInfo.getTableName().toLowerCase() + " "
				+ m_ColInfo.getName().toLowerCase())) {
			hashtable.remove(m_ColInfo.getTableName().toLowerCase() + " "
					+ m_ColInfo.getName().toLowerCase());
		}
		hashtable.put(m_ColInfo.getTableName().toLowerCase() + " "
				+ m_ColInfo.getName().toLowerCase(), m_ColInfo);
	}

	/**
	 * 指定的表名、指定的列名是否已在本类中存储其ColInfo类的实例化对象
	 */
	public static boolean exists(String m_TableName, String m_ColName) {
		if (!TableStructList.Inited) {
			TableStructList.init();
		}
		if ((m_TableName.trim() + m_ColName.trim()).length() == 0) {
			return false;
		}
		return hashtable.containsKey(m_TableName.toLowerCase() + " "
				+ m_ColName.toLowerCase());
	}
}
