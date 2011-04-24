package com.xdarkness.cms.site;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import com.xdarkness.schema.ZDCodeSchema;
import com.xdarkness.schema.ZDCodeSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class ReferConfig extends Page {
	public static Mapx init(Mapx params) {
		String CodeName = params.getString("CodeName");
		String CodeType = "ArticleRefer";
		String ParentCode = "ArticleRefer";
		if (XString.isNotEmpty(CodeName)) {
			try {
				CodeName = URLDecoder.decode(params.getString("CodeName"),
						"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			ZDCodeSchema code = new ZDCodeSchema();
			code.setCodeType(CodeType);
			code.setParentCode(ParentCode);
			code.setCodeValue(CodeName);
			code.setCodeName(CodeName);
			code.fill();
			params.putAll(code.toMapx());
			params.put("New", "");
		} else {
			params.put("New", "New");
		}
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String SearchContent = dga.getParam("SearchContent");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZDCode where CodeType = 'ArticleRefer' and ParentCode ='ArticleRefer' ");
		if (XString.isNotEmpty(SearchContent)) {
			qb
					.append(" and CodeName like ? ", "%" + SearchContent.trim()
							+ "%");
		}
		qb.append(" order by AddTime desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dga.bindData(dt);
	}

	public void add() {
		String CodeName = $V("CodeName").trim();
		String New = $V("New");
		String hCodeName = $V("hCodeName");
		if (XString.isNotEmpty(New)) {
			if (new QueryBuilder(
					"select count(*) from ZDCode where CodeName = ? and ParentCode ='ArticleRefer'",
					CodeName).executeInt() == 0) {
				ZDCodeSchema code = new ZDCodeSchema();
				code.setCodeType("ArticleRefer");
				code.setParentCode("ArticleRefer");
				code.setCodeValue(CodeName);
				code.setCodeName(CodeName);
				code.setCodeOrder(System.currentTimeMillis());
				code.setProp4(XString.getChineseFirstAlpha(CodeName));
				code.setAddTime(new Date());
				code.setAddUser(User.getUserName());
				if (code.insert()) {
					this.response.setStatus(1);
					this.response.setMessage("新增成功!");
				} else {
					this.response.setStatus(0);
					this.response.setMessage("发生错误!");
				}
			} else {
				this.response.setStatus(0);
				this.response.setMessage("已经存在的文章来源!");
			}
		} else {
			ZDCodeSchema code = new ZDCodeSchema();
			code.setCodeType("ArticleRefer");
			code.setParentCode("ArticleRefer");
			code.setCodeValue(hCodeName);
			code.setCodeName(hCodeName);
			code.fill();
			code.setCodeValue(CodeName);
			code.setCodeName(CodeName);
			code.setProp4(XString.getChineseFirstAlpha(CodeName));
			if (code.update()) {
				this.response.setStatus(1);
				this.response.setMessage("修改成功!");
			} else {
				this.response.setStatus(0);
				this.response.setMessage("发生错误!");
			}
		}
	}

	public void del() {
		String CodeNames = $V("CodeNames");
		CodeNames = XString.replaceEx(CodeNames, ",", "','");
		Transaction trans = new Transaction();
		ZDCodeSchema code = new ZDCodeSchema();
		ZDCodeSet set = code.query(new QueryBuilder("where CodeName in ('"
				+ CodeNames + "')"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);
		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public static String getReferList() {
		StringBuffer sb = new StringBuffer();
		QueryBuilder qb = new QueryBuilder(
				"select CodeName,Prop4 from ZDCode where CodeType='ArticleRefer' and ParentCode<>'System' order by Prop4");
		DataTable dt = qb.executeDataTable();
		sb.append("var AllNames = [");
		char last = ' ';
		for (int i = 0; i < dt.getRowCount(); i++) {
			String abbr = dt.getString(i, "Prop4");
			if (XString.isEmpty(abbr)) {
				continue;
			}
			char c = abbr.charAt(0);
			if (!Character.isLetter(c)) {
				c = '*';
			}
			if (c != last) {
				if (i != 0) {
					sb.append("],");
				}
				sb.append("[");
				sb.append("\""
						+ XString.javaEncode(dt.getString(i, "CodeName"))
						+ "\",\"" + abbr + "\"");
				last = c;
			} else {
				sb.append(",\""
						+ XString.javaEncode(dt.getString(i, "CodeName"))
						+ "\",\"" + dt.getString(i, "Prop4") + "\"");
			}
		}
		sb.append("]];");
		return sb.toString();
	}
}

/*
 * com.xdarkness.cms.site.ReferConfig JD-Core Version: 0.6.0
 */