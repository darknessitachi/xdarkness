package tablemeta;

/**
 * 数据库的一个表的简单结构属性索引
 */
public class TableStruct {
	private String name;// 表名
	private String primary;
	private String[] cols;

	/**
	 * 实例化本类，使成为数据库的一个表的简单属性索引。
	 * @param m_Name 表名
	 * @param m_Primary 主键字段名
	 * @param m_Cols 字段名列表数组
	 */
	public TableStruct(String m_Name, String m_Primary, String[] m_Cols) {
		name = m_Name.toLowerCase();
		primary = m_Primary.toLowerCase();
		cols = m_Cols;
	}

	public String getName() {
		return name;
	}

	/**
	 * 主键字段的ColInfo对象
	 */
	public ColInfo getPrimary() {
		return getCol(primary);
	}

	/**
	 * 字段名列表数组
	 */
	public String[] getCols() {
		return cols;
	}

	/**
	 * 取得本表的一列的ColInfo对象
	 */
	public ColInfo getCol(String m_ColName) {
		return ColInfos.get(name, m_ColName);
	}

	/**
	 * 判断指定的字段名在本表是否存在
	 */
	public boolean existsCol(String m_ColName) {
		return getCol(m_ColName).getName().length() > 0;
	}

	/**
	 * 取得本表的Tablesremark对象的信息
	 */
	public Tablesremark getRemarkInfo() {
		return TablesremarkViews.Child(this.getName());
	}
}
