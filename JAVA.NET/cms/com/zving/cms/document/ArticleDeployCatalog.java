 package com.zving.cms.document;
 
 import com.zving.framework.Page;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.controls.HtmlTR;
 import com.zving.framework.controls.HtmlTable;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.DataTableUtil;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.StringUtil;
 import java.util.ArrayList;
 
 public class ArticleDeployCatalog extends Page
 {
   public static void articleDialogDataBind(DataGridAction dga)
   {
     String innerCode = dga.getParam("CatalogInnerCode");
     String data = new QueryBuilder("select targetCatalog from ZCInnerDeploy where CatalogInnerCode=?", innerCode)
       .executeString();
     DataTable dt = null;
     if (StringUtil.isEmpty(data)) {
       dt = new DataTable();
       dt.insertColumn("ServerAddr");
       dt.insertColumn("SiteID");
       dt.insertColumn("SiteName");
       dt.insertColumn("CatalogID");
       dt.insertColumn("CatalogName");
       dt.insertColumn("Password");
     } else {
       dt = DataTableUtil.txtToDataTable(data, null, "\t", "\n");
     }
     if (dt.getDataColumn("MD5") == null) {
       dt.insertColumn("MD5");
     }
     for (int i = 0; i < dt.getRowCount(); ++i) {
       dt.set(i, "MD5", StringUtil.md5Hex(dt.getString(i, "ServerAddr") + "," + dt.getString(i, "SiteID") + "," + 
         dt.getString(i, "CatalogID")));
     }
     dga.bindData(dt);
     HtmlTable table = dga.getTable();
     for (int i = 1; i < table.getChildren().size(); ++i)
       table.getTR(i).removeAttribute("onclick");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.document.ArticleDeployCatalog
 * JD-Core Version:    0.5.4
 */