package com.xdarkness.platform.page;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.data.Transaction;
import com.abigdreamer.java.net.io.FileUtil;
import com.abigdreamer.java.net.jaf.Page;
import com.abigdreamer.java.net.jaf.controls.grid.DataGridAction;
import com.abigdreamer.java.net.orm.OperateType;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;
import com.xdarkness.platform.Cms;
import com.zving.schema.ZDDistrictSchema;
import com.zving.schema.ZDDistrictSet;

public class DistrictPage extends Page {
	static Mapx codeMap;
	static Mapx nameMap;
	static DataTable table;

	public static void dg1BindDistrict(DataGridAction dga) {
		String sql = " select * from ZDDistrict where TreeLevel = 1 or TreeLevel = 2 order by CodeOrder ";
		dga.bindData(new QueryBuilder(sql));
	}

	public static void dg1BindDistrictList(DataGridAction dga) {
		String code = dga.getParam("Code");
		if (XString.isEmpty(code)) {
			dga.bindData(new DataTable());
			return;
		}
		String parentCode = "";
		ZDDistrictSchema district = new ZDDistrictSchema();
		district.setCode(code);
		district.fill();
		if ("0".equals(district.getType()))
			parentCode = code.substring(0, 2);
		else {
			parentCode = code.substring(0, 4);
		}
		dga
				.bindData(new QueryBuilder(
						" select * from ZDDistrict where TreeLevel = '3' and code like ? order by CodeOrder ",
						parentCode + "%"));
	}

	public static Mapx init(Mapx params) {
		Mapx map = new Mapx();
		String code = (String) params.get("Code");
		map.put("Code", code);
		String Name = new QueryBuilder(
				"select name from ZDDistrict where code = ?", code)
				.executeString();
		map.put("Name", Name);
		return map;
	}

	public static Mapx initDialog(Mapx params) {
		params.put("Name", "");
		return params;
	}

	public void add() {
		ZDDistrictSchema district = new ZDDistrictSchema();
		String code = $V("Code");
		district.setCode(code);
		if (district.fill()) {
			this.response.setLogInfo(0, district.getCode() + "已经存在了!");
			return;
		}
		district.setValue(this.request);
		if (district.insert()) {
			generateDistrictJS();
			Cms.initDistrict();
			this.response.setLogInfo(1, "新建成功!");
		} else {
			this.response.setLogInfo(0, "新建失败!");
		}
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) this.request.get("DT");
		ZDDistrictSet set = new ZDDistrictSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDDistrictSchema district = new ZDDistrictSchema();
			district.setCode(dt.getString(i, "Code"));
			if (!district.fill()) {
				this.response.setLogInfo(0, "您要修改的项" + dt.getString(i, "Code")
						+ "不存在!");
				return;
			}
			district.setValue(dt.getDataRow(i));
			set.add(district);
		}
		if (set.update()) {
			generateDistrictJS();
			Cms.initDistrict();
			this.response.setStatus(1);
			this.response.setMessage("保存成功!");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("保存失败!");
		}
	}

	public void del() {
		String IDs = $V("IDs");
		String[] ids = IDs.split(",");
		String code = "";
		Transaction trans = new Transaction();
		ZDDistrictSchema district = new ZDDistrictSchema();
		for (int i = 0; i < ids.length; i++) {
			district.setCode(ids[i]);
			if (district.fill()) {
				if (district.getTreeLevel() == 1)
					code = ids[i].substring(0, 2);
				else if (district.getTreeLevel() == 2)
					code = ids[i].substring(0, 4);
				else if (district.getTreeLevel() == 3) {
					code = ids[i];
				}
				trans.add(district.query(new QueryBuilder(" where Code like ?",
						code + "%")), OperateType.DELETE);
			}
		}
		if (trans.commit()) {
			generateDistrictJS();
			Cms.initDistrict();
			this.response.setLogInfo(1, "删除成功");
		} else {
			this.response.setLogInfo(0, "删除失败");
		}
	}

	public static void generateDistrictJS() {
		ZDDistrictSet set = new ZDDistrictSchema().query(new QueryBuilder(
				"where code!='000000' order by code"));

		StringBuffer provinceMap = new StringBuffer();
		StringBuffer cityMap = new StringBuffer();
		StringBuffer districtMap = new StringBuffer();
		provinceMap.append("var provinceMap = [");
		cityMap.append("var cityMap = {\n");
		districtMap.append("var districtMap = {\n");

		boolean firstProv = true;
		boolean firstCity = true;
		boolean firstDistrict = true;
		for (int i = 0; i < set.size(); i++) {
			ZDDistrictSchema d = set.get(i);
			if ("1".equals(d.getType())) {
				if (!firstProv) {
					provinceMap.append(",");
				}
				firstProv = false;
				provinceMap.append("'" + d.getCode() + "','" + d.getName()
						+ "'");

				if (!firstCity) {
					cityMap.append(",\n");
				}
				cityMap.append("'" + d.getCode() + "':[");
				for (int j = i + 1; j < set.size(); j++) {
					d = set.get(j);
					if ("2".equals(d.getType())) {
						firstCity = false;
						if (j == i + 1)
							cityMap.append("'" + d.getCode() + "','"
									+ d.getName() + "'");
						else {
							cityMap.append(",'" + d.getCode() + "','"
									+ d.getName() + "'");
						}

						if (!firstDistrict) {
							districtMap.append(",\n");
						}
						districtMap.append("'" + d.getCode() + "':[");
						for (int k = j + 1; k < set.size(); k++) {
							d = set.get(k);
							if (!"3".equals(d.getType()))
								break;
							firstDistrict = false;
							if (k == j + 1)
								districtMap.append("'" + d.getCode() + "','"
										+ d.getName() + "'");
							else {
								districtMap.append(",'" + d.getCode() + "','"
										+ d.getName() + "'");
							}

						}

						districtMap.append("]");
					} else {
						if (("1".equals(d.getType()))
								|| ("0".equals(d.getType())))
							break;
					}
				}
				cityMap.append("]");
			} else {
				if (!"0".equals(d.getType()))
					continue;
				if (!firstProv) {
					provinceMap.append(",");
				}
				firstProv = false;
				provinceMap.append("'" + d.getCode() + "','" + d.getName()
						+ "'");

				if (!firstCity) {
					cityMap.append(",\n");
				}
				cityMap.append("'" + d.getCode() + "':[");
				for (int j = i; j < i + 1; j++) {
					d = set.get(j);
					if ("0".equals(d.getType())) {
						firstCity = false;
						cityMap.append("'" + d.getCode() + "','" + d.getName()
								+ "'");

						if (!firstDistrict) {
							districtMap.append(",\n");
						}
						districtMap.append("'" + d.getCode() + "':[");
						for (int k = j + 1; k < set.size(); k++) {
							d = set.get(k);
							if (!"3".equals(d.getType()))
								break;
							firstDistrict = false;
							if (k == j + 1)
								districtMap.append("'" + d.getCode() + "','"
										+ d.getName() + "'");
							else {
								districtMap.append(",'" + d.getCode() + "','"
										+ d.getName() + "'");
							}

						}

						districtMap.append("]");
					} else {
						if ("1".equals(d.getType()))
							break;
					}
				}
				cityMap.append("]");
			}
		}

		provinceMap.append("];\n");
		cityMap.append("};\n");
		districtMap.append("};\n");

		String path = Config.getContextRealPath()
				+ "WEB-INF/classes/com/sky/Platform/district.template";
		String func = FileUtil.readText(path);
		String JS = Config.getContextRealPath() + "Framework/District.js";
		FileUtil.writeText(JS, provinceMap.toString() + cityMap.toString()
				+ districtMap.toString() + func);
	}

	public static String getProvinceName(String address) {
		if (codeMap == null) {
			table = new QueryBuilder(
					"select Name,Code from ZDDistrict where code like '11%' or code like '12%' or code like '31%' or code like '50%' or treelevel in (1,2,3) order by treelevel,code desc")
					.executeDataTable();
			codeMap = table.toMapx(0, 1);
			nameMap = table.toMapx(1, 0);
		}
		for (int j = 0; j < table.getRowCount(); j++) {
			String name = table.getString(j, "Name");
			if (address.indexOf(name) >= 0) {
				return name;
			}
		}
		for (int j = 0; j < table.getRowCount(); j++) {
			String name = table.getString(j, "Name");
			if (address.startsWith(name.substring(0, 2))) {
				return name;
			}
		}
		for (int j = 0; j < table.getRowCount(); j++) {
			String name = table.getString(j, "Name");
			if (address.indexOf(name.substring(0, 2)) >= 0) {
				return name;
			}
		}
		return null;
	}
}

/*
 * com.xdarkness.platform.District JD-Core Version: 0.6.0
 */