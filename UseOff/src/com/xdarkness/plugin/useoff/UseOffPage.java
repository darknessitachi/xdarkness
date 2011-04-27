package com.xdarkness.plugin.useoff;

import java.sql.SQLException;

import com.abigdreamer.java.net.jaf.Cookies;
import com.abigdreamer.java.net.jaf.IPage;
import com.abigdreamer.java.net.jaf.JafRequest;
import com.abigdreamer.java.net.jaf.JafResponse;
import com.abigdreamer.java.net.jaf.controls.grid.DataGridAction;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.Mapx;


//@IPlugin @Module(name = "代理人管理", parent = "基础数据")
public class UseOffPage implements IPage {

	//@AccessControl(name = "编辑代理人")
//	@RequestMapping("/agent/editAgent.do")
	public static void dg1DataBind(DataGridAction dga) {
		String sql = "SELECT * FROM UseOff";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		dga.bindData(dt);
	}
	
	public void save() {
		Double money = Double.parseDouble(getValue("money"));
		String moneyType = getValue("moneyType");
		String useFor = getValue("useFor");
		String description = getValue("description");
		String createTime = getValue("createTime");
		
		UseOff useOff = new UseOff();
		useOff.setCreateTime(new java.util.Date());
		useOff.setDiscription(description);
		useOff.setMoney(money);
		useOff.setMoneyType(moneyType);
		useOff.setUseFor(useFor);
		
		
		try {
			new QueryBuilder("INSERT INTO UseOff(money, moneyType, useFor, description, createTime) VALUES("+money+",'"+moneyType+"','"+useFor+"','"+description+"','"+createTime+"')").executeNoQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		new QueryBuilder("INSERT INTO UseOff(money, moneyType, useFor, description, createTime) VALUES(?,?,?,?,now())").add(money, moneyType, useFor, description).executeNoQuery();
//		InitUseOffData.loadConfig();
		this.response.setMessage("操作成功!");
	} 
	
	/**
	 * Page初始化方法
	 */
	public static Mapx<String, String> init(Mapx<String, String> params) {
//		params.put("AppCode", Config.getAppCode());
//		params.put("AppName", Config.getAppName());
		return params;
	}

	/**
	 * IPage接口的实现方法，在该Page类初始化的时候，系统会自动注入所需对象
	 */
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////// IPage接口的实现方法 开始///////////////////////////////
	// /////////////////////////////////////////////////////////////////////////
	private JafResponse response;
	private JafRequest request;
	private Cookies cookie;

	public JafResponse getResponse() {
		return response;
	}

	public void setResponse(JafResponse response) {
		this.response = response;
	}

	public JafRequest getRequest() {
		return request;
	}

	public void setRequest(JafRequest request) {
		this.request = request;
	}

	public Cookies getCookie() {
		return cookie;
	}

	public void setCookie(Cookies cookies) {
		this.cookie = cookies;
	}

	public String getValue(String id) {
		return request.getString(id);
	}

	public void setValue(String id, Object value) {
		response.put(id, value);
	}

	public void redirect(String url) {
		response.put("_SKY_SCRIPT", "window.location=\"" + url + "\";");
	}
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////// IPage接口的实现方法 结束///////////////////////////////
	// /////////////////////////////////////////////////////////////////////////
}
