package com.xdarkness.framework.orm;

import org.dom4j.Element;

import com.xdarkness.framework.orm.data.DataCollection;
import com.xdarkness.framework.orm.data.DataTable;

public class SchemaDataCollection extends DataCollection {

	public Schema getSchema(String id) {
		Object o = super.get(id);
		if (Schema.class.isInstance(o))
			return (Schema) o;

		return null;
	}

	public SchemaSet getSchemaSet(String id) {
		Object o = super.get(id);
		if (SchemaSet.class.isInstance(o))
			return (SchemaSet) o;
		else
			return null;
	}
	
	public void dealExtraDataToXml(Object value, Element ele){
		if (value instanceof Schema)
			schemaToXML((Schema) value, ele);
		else if (value instanceof SchemaSet)
			schemaSetToXML((SchemaSet) value, ele);
	}
	
	private void schemaToXML(Schema schema, Element ele) {
		try {
			Class<?> c = Class.forName(SchemaUtil.getNameSpace(schema) + "."
					+ SchemaUtil.getTableCode(schema) + "Set");
			SchemaSet set = (SchemaSet) c.newInstance();
			set.add(schema);
			DataTable dt = set.toDataTable();
			ele.addAttribute("TableCode", SchemaUtil.getTableCode(schema));
			ele.addAttribute("NameSpace", SchemaUtil.getNameSpace(schema));
			translatDataTableToXML(dt, ele, "Schema");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void schemaSetToXML(SchemaSet set, Element ele) {
		if (set != null && set.size() != 0) {
			DataTable dt = set.toDataTable();
			ele.addAttribute("TableCode", SchemaUtil.getTableCode(set));
			ele.addAttribute("NameSpace", SchemaUtil.getNameSpace(set));
			translatDataTableToXML(dt, ele, "SchemaSet");
		}
	}
	
	public void paseExtraDataXml(String type, Element ele, int index, String id) {
		 if (type.equals("Schema")) {
			Class c = null;
			try {
				c = Class.forName(ele.attributeValue("NameSpace")
						+ "." + ele.attributeValue("TableCode"));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Schema schema = null;
			try {
				schema = (Schema) c.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			DataTable dt = parseDataTable(ele);
			for (int j = 0; j < SchemaUtil.getColumns(schema).length; j++)
				schema.setV(index, dt.get(0, j));

			put(id, schema);
		} else if (type.equals("SchemaSet")) {
			Class cSchema = null;
			try {
				cSchema = Class.forName(ele
						.attributeValue("NameSpace")
						+ "." + ele.attributeValue("TableCode") + "Schema");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Class cSet = null;
			try {
				cSet = Class.forName(ele.attributeValue("NameSpace")
						+ "." + ele.attributeValue("TableCode") + "Set");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DataTable dt = parseDataTable(ele);
			SchemaSet set = null;
			try {
				set = (SchemaSet) cSet.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int j = 0; j < dt.getRowCount(); j++) {
				Schema schema = null;
				try {
					schema = (Schema) cSchema.newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int k = 0; k < SchemaUtil.getColumns(schema).length; k++)
					schema.setV(index, dt.get(j, k));

				set.add(schema);
			}

			put(id, set);
		}
	}
}
