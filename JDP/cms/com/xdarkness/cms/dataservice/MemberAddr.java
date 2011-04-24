package com.xdarkness.cms.dataservice;

import java.sql.SQLException;
import java.util.Date;

import com.xdarkness.cms.pub.PubFun;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZDMemberAddrSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class MemberAddr extends Page {
	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ID");
		if (XString.isNotEmpty(ID)) {
			ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
			addr.setID(ID);
			addr.fill();
			if (addr.getIsDefault().equals("Y")) {
				params.put("checkDefault", "checked");
			}
			Mapx DistrictMap = PubFun.getDistrictMap();
			params.put("ProvinceName", DistrictMap
					.getString(addr.getProvince()));
			params.put("CityName", DistrictMap.getString(addr.getCity()));
			params.put("DistrictName", DistrictMap
					.getString(addr.getDistrict()));
			params.putAll(addr.toMapx());
		}
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String UserName = dga.getParam("UserName");
		QueryBuilder qb = new QueryBuilder(
				"select * from zdmemberaddr where UserName = ?", UserName);
		qb.append(" order by addTime desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.insertColumn("CityName");
		dt.insertColumn("IsDefaultName");
		Mapx DistrictMap = PubFun.getDistrictMap();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (XString.isNotEmpty(dt.getString(i, "City"))) {
				dt.set(i, "CityName", DistrictMap.getString(dt.getString(i,
						"City")));
			}
			if (dt.getString(i, "IsDefault").equals("Y"))
				dt.set(i, "IsDefaultName", "<b>默认</b>");
			else {
				dt
						.set(i, "IsDefaultName",
								"<a href='#;' onClick='setDefault(\""
										+ dt.getString(i, "ID") + "\",\""
										+ dt.getString(i, "UserName")
										+ "\")'>设为默认</a>");
			}
		}
		dga.bindData(dt);
	}

	public void add() {
		String ID = $V("ID");
		String UserName = $V("UserName");
		ZDMemberAddrSchema memberaddr = new ZDMemberAddrSchema();
		boolean isExists = false;
		if (XString.isNotEmpty(ID)) {
			memberaddr.setID(ID);
			memberaddr.fill();
			memberaddr.setModifyUser(User.getUserName());
			memberaddr.setModifyTime(new Date());
			isExists = true;
		} else {
			memberaddr.setID(NoUtil.getMaxID("MemberAddr"));
			memberaddr.setAddUser(User.getUserName());
			memberaddr.setAddTime(new Date());
		}
		memberaddr.setUserName(UserName);
		memberaddr.setValue(this.request);
		if (memberaddr.getIsDefault().equals("Y")) {
			try {
				new QueryBuilder(
						"update zdmemberaddr set IsDefault = 'N' where UserName = ?",
						UserName).executeNoQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (isExists) {
			if (memberaddr.update())
				this.response.setLogInfo(1, "修改成功");
			else {
				this.response.setLogInfo(0, "修改失败!");
			}
		} else if (memberaddr.insert())
			this.response.setLogInfo(1, "新增成功");
		else
			this.response.setLogInfo(0, "新增失败!");
	}

	public void setDefault() {
		String ID = $V("ID");
		String UserName = $V("UserName");
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder(
				"update zdmemberaddr set IsDefault = 'N' where UserName = ?",
				UserName));
		ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
		addr.setID(ID);
		addr.fill();
		addr.setIsDefault("Y");
		trans.add(addr, OperateType.UPDATE);
		if (trans.commit())
			this.response.setLogInfo(1, "设置成功");
		else
			this.response.setLogInfo(0, "发生错误");
	}

	public void del() {
		String IDs = $V("IDs");
		String[] ids = IDs.split(",");
		Transaction trans = new Transaction();
		ZDMemberAddrSchema memberaddr = new ZDMemberAddrSchema();
		for (int i = 0; i < ids.length; i++) {
			trans.add(memberaddr
					.query(new QueryBuilder(" where ID = ?", ids[i])), OperateType.DELETE_AND_BACKUP);
		}
		if (trans.commit())
			this.response.setLogInfo(1, "删除成功");
		else
			this.response.setLogInfo(0, "删除失败");
	}
}

/*
 * com.xdarkness.cms.dataservice.MemberAddr JD-Core Version: 0.6.0
 */