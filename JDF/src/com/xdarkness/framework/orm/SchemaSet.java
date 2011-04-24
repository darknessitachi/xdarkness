package com.xdarkness.framework.orm;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Filter;
import com.xdarkness.framework.util.LogUtil;

public abstract class SchemaSet extends AbstractSchema implements Serializable,
		Cloneable {

	public void initDeleteQueryBuilder(QueryBuilder queryBuilder) {
		queryBuilder.setBatchMode(true);
		for (int k = 0; k < elementCount; ++k) {
			Schema schema = elementData[k];
			queryBuilder.add(schema.getPrimaryKeyValue("delete"));
			queryBuilder.addBatch();
		}
	}

	public void initInsertQueryBuilder(QueryBuilder queryBuilder) {
		try {
			queryBuilder.setBatchMode(true);

			for (int k = 0; k < this.elementCount; ++k) {
				Schema schema = this.elementData[k];

				setParams(queryBuilder, schema);

				queryBuilder.addBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected SchemaSet(int initialCapacity, int capacityIncrement) {
		bOperateFlag = false;
		if (initialCapacity < 0) {
			throw new RuntimeException("SchemaSet的初始容量不能小于0");
		} else {
			elementData = new Schema[initialCapacity];
			this.capacityIncrement = capacityIncrement;
			elementCount = 0;
			return;
		}
	}

	public void initQueryBuilder(QueryBuilder queryBuilder) {
		queryBuilder.setBatchMode(true);
		for (int k = 0; k < this.elementCount; ++k) {
			Schema schema = this.elementData[k];
			setUpdataParams(queryBuilder, schema);
			queryBuilder.addBatch();
		}
	}

	protected SchemaSet(int initialCapacity) {
		this(initialCapacity, 0);
	}

	protected SchemaSet() {
		this(10);
	}

	public boolean add(Schema s) {
		if (s == null || s.TableCode != TableCode) {
			LogUtil.warn("传入的参数不是一个" + TableCode + "Schema");
			return false;
		}
		ensureCapacityHelper(elementCount + 1);
		elementData[elementCount] = s;
		elementCount++;
		return true;
	}

	public boolean add(SchemaSet aSet) {
		if (aSet == null)
			return false;
		int n = aSet.size();
		ensureCapacityHelper(elementCount + n);
		for (int i = 0; i < n; i++)
			elementData[elementCount + i] = aSet.getObject(i);

		elementCount += n;
		return true;
	}

	public boolean remove(Schema aSchema) {
		if (aSchema == null)
			return false;
		for (int i = 0; i < elementCount; i++)
			if (aSchema.equals(elementData[i])) {
				int j = elementCount - i - 1;
				if (j > 0)
					System.arraycopy(elementData, i + 1, elementData, i, j);
				elementCount--;
				elementData[elementCount] = null;
				return true;
			}

		return false;
	}

	public boolean removeRange(int index, int length) {
		if ((index < 0) || (length < 0) || (index + length > this.elementCount)) {
			return false;
		}
		if (this.elementCount > index + length) {
			System.arraycopy(this.elementData, index + length,
					this.elementData, index, length);
		}
		for (int i = 0; i < length; i++) {
			this.elementData[(this.elementCount - i - 1)] = null;
		}
		this.elementCount -= length;
		return true;
	}

	public void clear() {
		for (int i = 0; i < this.elementCount; i++) {
			this.elementData[i] = null;
		}
		this.elementCount = 0;
	}
	
	public boolean isEmpty() {
		return this.elementCount == 0;
	}

	public Schema getObject(int index) {
		if (index > this.elementCount) {
			throw new RuntimeException("SchemaSet索引过大," + index);
		}
		return this.elementData[index];
	}

	public boolean set(int index, Schema aSchema) {
		if (index > this.elementCount) {
			throw new RuntimeException("SchemaSet索引过大," + index);
		}
		this.elementData[index] = aSchema;
		return true;
	}

	public boolean set(SchemaSet aSet) {
		this.elementData = aSet.elementData;
		this.elementCount = aSet.elementCount;
		this.capacityIncrement = aSet.capacityIncrement;
		return true;
	}

	public int size() {
		return this.elementCount;
	}

	private void ensureCapacityHelper(int minCapacity) {
		int oldCapacity = this.elementData.length;
		if (minCapacity > oldCapacity) {
			Object[] oldData = this.elementData;
			int newCapacity = this.capacityIncrement > 0 ? oldCapacity
					+ this.capacityIncrement : oldCapacity * 2;
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			this.elementData = new Schema[newCapacity];
			System
					.arraycopy(oldData, 0, this.elementData, 0,
							this.elementCount);
		}
	}

	public void setOperateColumns(String colNames[]) {
		if (colNames == null || colNames.length == 0) {
			bOperateFlag = false;
			return;
		}
		operateColumnOrders = new int[colNames.length];
		int i = 0;
		int k = 0;
		for (; i < colNames.length; i++) {
			boolean flag = false;
			for (int j = 0; j < Columns.length; j++) {
				if (!colNames[i].toString().toLowerCase().equals(
						Columns[j].getColumnName().toLowerCase()))
					continue;
				operateColumnOrders[k] = j;
				k++;
				flag = true;
				break;
			}

			if (!flag)
				throw new RuntimeException("指定的列名" + colNames[i] + "不正确");
		}

		bOperateFlag = true;
	}

	public void setOperateColumns(int colOrder[]) {
		if (colOrder == null || colOrder.length == 0) {
			bOperateFlag = false;
			return;
		}
		for (int i = 0; i < elementCount; i++)
			elementData[i].setOperateColumns(colOrder);

		operateColumnOrders = colOrder;
		bOperateFlag = true;
	}

	public DataTable toDataTable() {
		DataColumn dcs[];
		Object values[][];
		DataTable dt;
		if (bOperateFlag) {
			dcs = new DataColumn[operateColumnOrders.length];
			values = new Object[elementCount][Columns.length];
			for (int i = 0; i < operateColumnOrders.length; i++) {
				DataColumn dc = new DataColumn();
				dc.setColumnName(Columns[operateColumnOrders[i]]
						.getColumnName());
				dc.setColumnType(Columns[operateColumnOrders[i]]
						.getColumnType());
				dcs[i] = dc;
			}

			for (int i = 0; i < elementCount; i++) {
				for (int j = 0; j < operateColumnOrders.length; j++)
					values[i][j] = elementData[i].getV(operateColumnOrders[j]);

			}

			dt = new DataTable(dcs, values);
			return dt;
		}
		dcs = new DataColumn[Columns.length];
		values = new Object[elementCount][Columns.length];
		for (int i = 0; i < Columns.length; i++) {
			DataColumn dc = new DataColumn();
			dc.setColumnName(Columns[i].getColumnName());
			dc.setColumnType(Columns[i].getColumnType());
			dcs[i] = dc;
		}

		for (int i = 0; i < elementCount; i++) {
			for (int j = 0; j < Columns.length; j++)
				values[i][j] = elementData[i].getV(j);

		}

		return new DataTable(dcs, values);
	}

	public Object clone() {
		SchemaSet set = newInstance();
		for (int i = 0; i < size(); i++)
			set.add((Schema) elementData[i].clone());

		return set;
	}

	public void sort(Comparator c) {
		Schema newData[] = new Schema[elementCount];
		System.arraycopy(elementData, 0, newData, 0, elementCount);
		Arrays.sort(newData, c);
		elementData = newData;
	}

	public void sort(String columnName) {
		sort(columnName, "desc", false);
	}

	public void sort(String columnName, String order) {
		sort(columnName, order, false);
	}

	public void sort(String columnName, String order, final boolean isNumber) {
		final String cn = columnName;
		final String od = order;
		sort(new Comparator() {

			public int compare(Object obj1, Object obj2) {
				DataRow dr1 = ((Schema) obj1).toDataRow();
				DataRow dr2 = ((Schema) obj2).toDataRow();
				Object v1 = dr1.get(cn);
				Object v2 = dr2.get(cn);
				if ((v1 instanceof Number) && (v2 instanceof Number)) {
					double d1 = ((Number) v1).doubleValue();
					double d2 = ((Number) v2).doubleValue();
					if (d1 == d2)
						return 0;
					if (d1 > d2)
						return "asc".equalsIgnoreCase(od) ? 1 : -1;
					else
						return "asc".equalsIgnoreCase(od) ? -1 : 1;
				}
				if ((v1 instanceof Date) && (v2 instanceof Date)) {
					Date d1 = (Date) v1;
					Date d2 = (Date) v1;
					if ("asc".equalsIgnoreCase(od))
						return d1.compareTo(d2);
					else
						return -d1.compareTo(d2);
				}
				if (isNumber) {
					double d1 = 0.0D;
					double d2 = 0.0D;
					try {
						d1 = Double.parseDouble(String.valueOf(v1));
						d2 = Double.parseDouble(String.valueOf(v2));
					} catch (Exception exception) {
					}
					if (d1 == d2)
						return 0;
					if (d1 > d2)
						return "asc".equalsIgnoreCase(od) ? -1 : 1;
					else
						return "asc".equalsIgnoreCase(od) ? 1 : -1;
				}
				int c = dr1.getString(cn).compareTo(dr2.getString(cn));
				if ("asc".equalsIgnoreCase(od))
					return c;
				else
					return -c;
			}

		});
	}

	public SchemaSet filter(Filter filter) {
		SchemaSet set = newInstance();
		for (int i = 0; i < elementData.length; i++)
			if (filter.filter(elementData[i]))
				set.add((Schema) elementData[i].clone());

		return set;
	}

	public AbstractSchema getBackUpSchema(String backupOperator,
			String backupMemo) {
		Class s = null;
		try {
			s = Class.forName("com.xdarkness.schema.B" + this.TableCode + "Set");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SchemaSet bSet = null;
		try {
			bSet = (SchemaSet) s.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date now = new Date();
		for (int k = 0; k < this.elementCount; ++k) {
			Schema schema = this.elementData[k];
			bSet.add((Schema) schema
					.getBackUpSchema(backupOperator, backupMemo));
		}
		return bSet;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < this.elementCount; i++) {
			sb.append(this.elementData[i] + "\n");
		}
		return sb.toString();
	}

	protected abstract SchemaSet newInstance();

	private static final long serialVersionUID = 1L;
	protected Schema elementData[];
	protected int elementCount;
	private int capacityIncrement;
	protected String NameSpace;
	protected SchemaColumn Columns[];

	protected boolean bOperateFlag;
	protected int operateColumnOrders[];
}
