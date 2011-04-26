<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<%
long siteID = Application.getCurrentSiteID();
long goodsID = 0;
if(StringUtil.isNotEmpty(request.getParameter("GoodsID"))) {
	goodsID = Long.parseLong(request.getParameter("GoodsID"));
}
ZSGoodsSchema goods = new ZSGoodsSchema();
goods.setID(goodsID);
if(goods.fill()){
	String path = "";
	if(!goods.getStatus().equals("30")){
		//path="../Services/PreviewDoc.jsp?ArticleID="+aritcleID;
		out.println("id为"+goodsID+"的商品尚未发布");
	}else{
	  	long catalogID = goods.getCatalogID();
		String isSingle = CatalogUtil.getSingleFlag(catalogID);
		if("Y".equals(isSingle)){
		  	path = Config.getValue("Statical.TargetDir")+"/"+SiteUtil.getAlias(siteID)+"/"+CatalogUtil.getPath(catalogID);
		} else {
		  	path = Config.getValue("Statical.TargetDir")+"/"+SiteUtil.getAlias(siteID)+"/"+PubFun.getGoodsURL(String.valueOf(goodsID));
		}
		String realPath = Config.getContextRealPath() + path;
		File f = new File(realPath);
		if(!f.exists()){
			//path="../Services/PreviewDoc.jsp?ArticleID="+aritcleID;
		}else{
			path = Config.getContextPath()+path+"?"+System.currentTimeMillis();
		}
  	}
	response.sendRedirect(path);
}else{
	//out.println("没有找到id为"+goodsID+"的商品");
	int type = Integer.parseInt(request.getParameter("Type"));
	String path = "";
	path = com.xdarkness.framework.Config.getContextPath()+com.xdarkness.framework.Config.getValue("Statical.TargetDir")
			+"/"+SiteUtil.getAlias(siteID)+"/";
	path = path.replaceAll("///","/");
	path = path.replaceAll("//","/");
	if(type==1){
		long catalogID = Long.parseLong(request.getParameter("CatalogID"));
		path += CatalogUtil.getPath(catalogID);
		path += "index.shtml";
	}else if(type==2){
		path += request.getParameter("File");
	}else{
		path += "index.shtml";
	}

	response.sendRedirect(path+"?"+System.currentTimeMillis());
}
%>


