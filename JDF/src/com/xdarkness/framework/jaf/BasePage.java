package com.xdarkness.framework.jaf;

import java.lang.reflect.ParameterizedType;

import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.controls.grid.ITreeDataGrid;
import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.TwoTuple;

public class BasePage<T> extends Page {

	private Class<T> genericClass;

	/**
	 * 获取泛型Class
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getGenericClass() {
		if (genericClass == null) {
			genericClass = (Class<T>) ((ParameterizedType) this.getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return genericClass;
	}

	/**
	 * 获取泛型类型的简单名称，不包含包名
	 * 
	 * @return
	 */
	public String getGenericClassSimpleName() {
		return getGenericClass().getSimpleName();
	}

	/**
	 * 获取泛型类型POJO对应的Table名称
	 * 
	 * @return
	 */
	public String getGenericClassTableName() {
		return getGenericClass().getSimpleName().replace("Schema", "");
	}

	/**
	 * 默认表格绑定，绑定表格数据前，会自动调用sortDataGridTable(DataTable dt)方法排序表格数据
	 * 
	 * @param dga
	 */
	public void dataGridBind(DataGridAction dga) {
		DataTable dt = new QueryBuilder("SELECT * FROM "
				+ getGenericClassTableName() + " ORDER BY orderFlag")
				.executeDataTable();

		dga.bindData(sortDataGridTable(dt));
	}

	/**
	 * 表格数据排序
	 * 
	 * @param dt
	 * @return
	 */
	public DataTable sortDataGridTable(DataTable dt) {
		return dt;
	}

	// ////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////TreeDataGrid相关////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 初始化树状表格的Expand、TreeLevel
	 */
	public ITreeDataGrid initExpandAndTreeLevel() {
		return new ITreeDataGrid() {

			public String getTreeLevel(DataRow dr) {
				return "0";
			}

			public String getExpand(DataRow dr) {
				return "N";
			}
		};
	}

	/**
	 * get all data and add columns: Expand, TreeLevel with ''
	 * 
	 * @return
	 */
	public DataTable getDataWithExpandAndTreeLevel() {
		String sql = "SELECT t.*,'' AS Expand,'' AS TreeLevel FROM "
				+ getGenericClassTableName() + " t ORDER BY t.OrderFlag, t.id";
		return new QueryBuilder(sql).executeDataTable();
	}

	/**
	 * 绑定数表格控件
	 */
	public void treeDataGridBind(DataGridAction dga) {
		DataTable dt = getDataWithExpandAndTreeLevel();
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.get(i);
			TwoTuple<String, String> expandAndLevel = initExpandAndTreeLevel(
					dr, initExpandAndTreeLevel());
			dt.set(i, "Expand", expandAndLevel.first);
			dt.set(i, "TreeLevel", expandAndLevel.second);
		}
		dga.bindData(dt);
	}

	/**
	 * 初始化树状表格的Expand、TreeLevel
	 * 
	 * @param dr
	 * @param treeDataGrid
	 * @return
	 */
	public TwoTuple<String, String> initExpandAndTreeLevel(DataRow dr,
			ITreeDataGrid treeDataGrid) {
		return new TwoTuple<String, String>(treeDataGrid.getExpand(dr),
				treeDataGrid.getTreeLevel(dr));
	}

	// ////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////////////////TreeDataGrid相关////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////
	
	public void onDataGridEditSuccess(){
	}
	public void onDataGridEditFailure(){
	}
	
	/**
	 * update all the menu into DB
	 */
	public void dataGridEdit() {
		DataTable dt = (DataTable) this.request.get("DT");
		Class<T> schemaClass = getGenericClass();
		String schemaSetName = schemaClass.getName().replace("Schema", "Set");
		Class<?> setClass = null;
		try {
			setClass = Class.forName(schemaSetName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		SchemaSet schemaSet = null;
		try {
			schemaSet = (SchemaSet)setClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < dt.getRowCount(); i++) {
			Schema schema = null;
			try {
				schema = (Schema)schemaClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			schema.setPrimaryKey(Integer.parseInt(dt.getString(i, "ID")));
			schema.fill();
			
			DataRow row = dt.get(i);
			schema.setValue(row);

			processingSchema(row, schema);

			schemaSet.add(schema);
		}
		if (schemaSet.update()) {
			onDataGridEditSuccess();
			this.response.setStatus(1);
		} else {
			onDataGridEditFailure();
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}
	
	/**
	 * schema的特殊处理操作
	 * @param dr
	 * @param schema
	 */
	public void processingSchema(DataRow dr, Schema schema) {
	}
}
