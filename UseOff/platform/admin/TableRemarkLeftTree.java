package admin;


import com.abigdreamer.java.net.jaf.Page;
import com.abigdreamer.java.net.util.Mapx;

/// 此页面拷贝../inc/biztable_tree_page.aspx.cs中的代码加以链接修改，仅作为开发人员使用，定制一些特殊属性
/// page=../admin/tablesremark_page.aspx
///		表明这是开发管理员（在本系统中是王周文）从本地服务器上点按“系统管理-开发阶段功能-表及字段管理”得到的链接
public class TableRemarkLeftTree extends Page {

	public static Mapx init(Mapx params) {
		params.put("tree", getTeString());
		return params;
	}

	public static String getTeString() {
		return new StringBuffer("<script language=javascript>")
				.append(
						"var admin_tablesremark_left = new dTree('admin_tablesremark_left','../inc/dtreeimg/');")
				.append(
						" admin_tablesremark_left.config.target =  GetMyTargetName();")
				.append(
						"admin_tablesremark_left.add('root','-1','表所处目录(暂未完成)','../inc/welcome.htm');")
				.append("admin_tablesremark_left.add('0','root','系统核心表');")
				.append("admin_tablesremark_left.add('1','root','所有模块公用表');")
				.append("admin_tablesremark_left.add('8','root','项目数据表');")
				.append(
						"admin_tablesremark_left.add('attachcontent','attachfile','附件内容','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%be%c2%8c%c3%95%c2%97%c3%80%c2%9c%c3%86%c2%9d%c3%89%c2%84%c3%8c%c2%80%c3%9f%c3%8a%c3%97%c2%90%c3%86%c2%86%c3%97%c2%89%c2%8e%c3%95%c2%8d');")
				.append(
						"admin_tablesremark_left.add('attachfile','1','附件表','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%be%c2%8c%c3%95%c2%97%c3%80%c2%9c%c3%83%c2%9b%c3%8b%c2%95%c2%8f%c2%94%c3%91%c2%85%c3%83%c2%8c%c3%80%c3%95%c2%80%c3%93');")
				.append(
						"admin_tablesremark_left.add('category','0','下拉选框','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%bc%c2%99%c3%95%c2%93%c3%84%c2%9b%c3%97%c2%8b%c2%81%c2%8a%c3%93%c2%87%c3%85%c2%8a%c3%82%c3%97%c2%9e%c3%9b');")
				.append(
						"admin_tablesremark_left.add('categoryvalue','category','下拉选项','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%bc%c2%99%c3%95%c2%93%c3%84%c2%9b%c3%97%c2%8b%c3%91%c2%91%c3%85%c2%9b%c3%8e%c3%8a%c3%97%c2%90%c3%86%c2%86%c3%97%c2%89%c2%8e%c3%95%c2%8d');")
				.append(
						"admin_tablesremark_left.add('colsremark','tablesremark','字段释意','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%bc%c2%97%c3%8d%c2%85%c3%91%c2%91%c3%88%c2%93%c3%95%c2%9b%c2%8f%c2%94%c3%91%c2%85%c3%83%c2%8c%c3%80%c3%95%c2%80%c3%93');")
				.append(
						"admin_tablesremark_left.add('configrow','0','行扩展属性','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%bc%c2%97%c3%8f%c2%90%c3%8a%c2%93%c3%97%c2%9d%c3%90%c3%96%c3%93%c2%94%c3%82%c2%82%c3%8b%c2%85%c2%92%c3%99%c2%85');")
				.append(
						"admin_tablesremark_left.add('dept','0','单位部门','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%bb%c2%9d%c3%91%c2%82%c2%85%c2%8e%c3%9f%c2%9b%c3%89%c2%96%c3%86%c3%93%c2%92');")
				.append(
						"admin_tablesremark_left.add('operatelog','0','系统操作日志','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%b0%c2%88%c3%84%c2%84%c3%82%c2%80%c3%80%c2%9e%c3%88%c2%97%c2%8f%c2%94%c3%91%c2%85%c3%83%c2%8c%c3%80%c3%95%c2%80%c3%93');")
				.append(
						"admin_tablesremark_left.add('proj','0','项目','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%af%c2%8a%c3%8e%c2%9c%c2%85%c2%8e%c3%9f%c2%9b%c3%89%c2%96%c3%86%c3%93%c2%92');")
				.append(
						"admin_tablesremark_left.add('projusrmatrix','8','项目人员矩阵','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%af%c2%8a%c3%8e%c2%9c%c3%96%c2%87%c3%97%c2%9f%c3%86%c2%84%c3%9b%c2%87%c3%93%c3%8a%c3%97%c2%90%c3%86%c2%86%c3%97%c2%89%c2%8e%c3%95%c2%8d');")
				.append(
						"admin_tablesremark_left.add('rownote','1','注解信息','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%ad%c2%97%c3%96%c2%98%c3%8c%c2%80%c3%80%c3%94%c3%9d%c2%8a%c3%80%c2%80%c3%8d%c2%83%c2%90%c3%9b%c2%9d');")
				.append(
						"admin_tablesremark_left.add('tablesbasicdebug','tablesremark','调试用表','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%ab%c2%99%c3%83%c2%9a%c3%86%c2%87%c3%87%c2%93%c3%94%c2%99%c3%8a%c2%8a%c3%8e%c2%8e%c3%98%c2%8d%c2%89%c2%92%c3%8b%c2%8f%c3%9d%c2%82%c3%9a%c3%9f%c2%85%c3%91');")
				.append(
						"admin_tablesremark_left.add('tablesremark','0','表释意','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%ab%c2%99%c3%83%c2%9a%c3%86%c2%87%c3%97%c2%97%c3%8a%c2%91%c3%9b%c2%85%c2%8d%c2%96%c3%97%c2%83%c3%81%c2%8e%c3%9e%c3%9b%c2%82%c3%93');")
				.append(
						"admin_tablesremark_left.add('usr','dept','用户表','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%aa%c2%8b%c3%93%c3%90%c3%99%c2%8e%c3%8c%c2%9c%c3%81%c2%9f%c2%94%c3%96');")
				.append(
						"admin_tablesremark_left.add('webresource','0','Web页面','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%a8%c2%9d%c3%83%c2%84%c3%86%c2%87%c3%8a%c2%87%c3%95%c2%93%c3%8c%c3%88%c3%91%c2%96%c3%84%c2%84%c3%89%c2%87%c2%8c%c3%97%c2%85');")
				.append(
						"admin_tablesremark_left.add('worklog','8','工作日志','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%a8%c2%97%c3%93%c2%9d%c3%8f%c2%9b%c3%82%c3%94%c3%9d%c2%8a%c3%80%c2%80%c3%8d%c2%83%c2%90%c3%9b%c2%9d');")
				.append(
						"admin_tablesremark_left.add('workloggather','8','工作量汇总','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%a8%c2%97%c3%93%c2%9d%c3%8f%c2%9b%c3%82%c2%95%c3%86%c2%84%c3%81%c2%8b%c3%99%c3%8a%c3%97%c2%90%c3%86%c2%86%c3%97%c2%89%c2%8e%c3%95%c2%8d');")
				.append(
						"admin_tablesremark_left.add('worktask','8','工作任务','tablesremark_page.aspx?q=%c2%90%c3%ba%c2%91%c3%b8%c3%87%c3%a8%c2%97%c3%93%c2%9d%c3%97%c2%95%c3%96%c2%99%c2%81%c2%8a%c3%93%c2%87%c3%85%c2%8a%c3%82%c3%97%c2%9e%c3%9b');")
				.append("admin_tablesremark_left.makeTree();").append(
						"</script>").toString();
	}
	// public String genDtreeNode()
	// {
	// //
	// DtreeNode dtreeNode = new DtreeNode();
	// dtreeNode.D = "admin_tablesremark_left";//this.Pageurlid;
	// dtreeNode.Name = "表所处目录";//CONSTCategory.TABLE_CATEGORYID.Chinaname +
	// "(暂未完成)";
	// dtreeNode.Pid = "-1";
	// dtreeNode.Id = "root";
	// dtreeNode.Url = "../inc/welcome.htm";
	//
	//
	// String outVal = "var " + dtreeNode.D + " = new dTree(\'" + dtreeNode.D +
	// "\',\'../inc/dtreeimg/\');\r\n"
	// + " " + dtreeNode.D + ".config.target = GetMyTargetName();\r\n" +
	// dtreeNode.ToString();
	//
	// dtreeNode.Pid = "root";
	// dtreeNode.Url = "";
	// DataTable dt = CONSTCategory.TABLE_CATEGORYID.DtValues;
	// for (int i = 0; i < dt.Rows.Count; i++)
	// {
	// dtreeNode.Icon = "";
	// dtreeNode.IconOpen = "";
	// dtreeNode.Id = dt.Rows[i]["refid"].ToString();
	// dtreeNode.Name = dt.Rows[i]["chinaname"].ToString();
	// if (dt.Rows[i]["delStatus"].ToString().Equals("1"))
	// {
	// dtreeNode.Icon = "../inc/dtreeimg/icon/deleted.gif";
	// dtreeNode.IconOpen = "../inc/dtreeimg/icon/deleted.gif";
	// }
	// outVal += dtreeNode.ToString();
	// }
	//
	// dt = TablesremarkViews.All;
	// for (int i = 0; i < dt.Rows.Count; i++)
	// {
	// Tablesremark obj =
	// TablesremarkViews.Child(dt.Rows[i]["tablename"].ToString());
	// dtreeNode.Icon = "";
	// dtreeNode.IconOpen = "";
	// dtreeNode.Id = obj.Tablename;
	// if (obj.Summarytable.Length > 0
	// && obj.Summarytable.Equals(obj.Tablename) == false)
	// {
	// dtreeNode.Pid = obj.Summarytable;
	// }
	// else
	// {
	// dtreeNode.Pid = dt.Rows[i]["categoryid"].ToString();
	// }
	// dtreeNode.Name = dt.Rows[i]["alias"].ToString();
	// if (obj.Tablechanged > 0 || obj.Colchanged > 0)
	// {
	// dtreeNode.Name += "<font color=red>*</font>";
	// }
	// dtreeNode.Url = "tablesremark_page.aspx?" +
	// this.EncryptQueryStringValue("name=" +
	// dt.Rows[i]["tablename"].ToString());
	// outVal += dtreeNode.ToString();
	// if (obj.Tabletype == 0 || obj.Tabletype > 3)
	// {
	// continue;
	// }
	// }
	// return outVal + "\r\n" + dtreeNode.D + ".makeTree();\r\n";
	// }
	//
	// /// <summary>
	// /// 添加新建物理表行
	// /// </summary>
	// /// <param name="dtreeNode">Node</param>
	// /// <param name="outVal">返回的字串</param>
	// private void GenNewDirectory(DtreeNode dtreeNode, String outVal)
	// {
	// if (this.IsDeveloper)
	// {
	// dtreeNode.Icon = "../inc/xloadtree/images/xp/addnew.png";
	// dtreeNode.IconOpen = "../inc/xloadtree/images/xp/addnew.png";
	// dtreeNode.Id = "category_0";
	// dtreeNode.Pid = "root";
	// dtreeNode.Name = "新建物理表";
	// // dtreeNode.Url =
	// "../inc/frame.htm?url0=../admin/PhysicalTable_Gen_page.aspx";
	// dtreeNode.Url = "../inc/mdi_biz_frame_page.aspx?" +
	// this.EncryptQueryStringValue("url=../admin/PhysicalTable_Gen_page.aspx&title=新建物理表");
	// outVal += dtreeNode.ToString();
	// }
	// }
}
