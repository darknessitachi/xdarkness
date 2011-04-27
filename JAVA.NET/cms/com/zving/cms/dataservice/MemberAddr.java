 package com.zving.cms.dataservice;
 
 import com.zving.cms.pub.PubFun;
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZDMemberAddrSchema;
 import java.util.Date;
 
 public class MemberAddr extends Page
 {
   public static Mapx initDialog(Mapx params)
   {
     String ID = params.getString("ID");
     if (StringUtil.isNotEmpty(ID)) {
       ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
       addr.setID(ID);
       addr.fill();
       if (addr.getIsDefault().equals("Y")) {
         params.put("checkDefault", "checked");
       }
       Mapx DistrictMap = PubFun.getDistrictMap();
       params.put("ProvinceName", DistrictMap.getString(addr.getProvince()));
       params.put("CityName", DistrictMap.getString(addr.getCity()));
       params.put("DistrictName", DistrictMap.getString(addr.getDistrict()));
       params.putAll(addr.toMapx());
     }
     return params;
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String UserName = dga.getParam("UserName");
     QueryBuilder qb = new QueryBuilder("select * from zdmemberaddr where UserName = ?", UserName);
     qb.append(" order by addTime desc");
     dga.setTotal(qb);
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     dt.insertColumn("CityName");
     dt.insertColumn("IsDefaultName");
     Mapx DistrictMap = PubFun.getDistrictMap();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (StringUtil.isNotEmpty(dt.getString(i, "City"))) {
         dt.set(i, "CityName", DistrictMap.getString(dt.getString(i, "City")));
       }
       if (dt.getString(i, "IsDefault").equals("Y"))
         dt.set(i, "IsDefaultName", "<b>默认</b>");
       else {
         dt.set(i, "IsDefaultName", "<a href='#;' onClick='setDefault(\"" + dt.getString(i, "ID") + "\",\"" + dt.getString(i, "UserName") + "\")'>设为默认</a>");
       }
     }
     dga.bindData(dt);
   }
 
   public void add() {
     String ID = $V("ID");
     String UserName = $V("UserName");
     ZDMemberAddrSchema memberaddr = new ZDMemberAddrSchema();
     boolean isExists = false;
     if (StringUtil.isNotEmpty(ID)) {
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
     memberaddr.setValue(this.Request);
     if (memberaddr.getIsDefault().equals("Y")) {
       new QueryBuilder("update zdmemberaddr set IsDefault = 'N' where UserName = ?", UserName).executeNoQuery();
     }
     if (isExists) {
       if (memberaddr.update())
         this.Response.setLogInfo(1, "修改成功");
       else {
         this.Response.setLogInfo(0, "修改失败!");
       }
     }
     else if (memberaddr.insert())
       this.Response.setLogInfo(1, "新增成功");
     else
       this.Response.setLogInfo(0, "新增失败!");
   }
 
   public void setDefault()
   {
     String ID = $V("ID");
     String UserName = $V("UserName");
     Transaction trans = new Transaction();
     trans.add(new QueryBuilder("update zdmemberaddr set IsDefault = 'N' where UserName = ?", UserName));
     ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
     addr.setID(ID);
     addr.fill();
     addr.setIsDefault("Y");
     trans.add(addr, 2);
     if (trans.commit())
       this.Response.setLogInfo(1, "设置成功");
     else
       this.Response.setLogInfo(0, "发生错误");
   }
 
   public void del()
   {
     String IDs = $V("IDs");
     String[] ids = IDs.split(",");
     Transaction trans = new Transaction();
     ZDMemberAddrSchema memberaddr = new ZDMemberAddrSchema();
     for (int i = 0; i < ids.length; ++i) {
       trans.add(memberaddr.query(new QueryBuilder(" where ID = ?", ids[i])), 5);
     }
     if (trans.commit())
       this.Response.setLogInfo(1, "删除成功");
     else
       this.Response.setLogInfo(0, "删除失败");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.dataservice.MemberAddr
 * JD-Core Version:    0.5.4
 */