 package com.zving.member;
 
 import com.zving.cms.pub.PubFun;
 import com.zving.framework.Ajax;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataListAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZDMemberAddrSchema;
 import java.util.Date;
 
 public class MemberAddress extends Ajax
 {
   public static void dg1DataList(DataListAction dla)
   {
     QueryBuilder qb = new QueryBuilder("select * from zdmemberaddr where UserName = ?  Order by AddTime Desc", User.getUserName());
     dla.setTotal(qb);
     DataTable dt = qb.executeDataTable();
     dt.insertColumn("ProvinceName");
     dt.insertColumn("CityName");
     dt.insertColumn("DistrictName");
     dt.insertColumn("IsDefaultName");
     Mapx DistrictMap = PubFun.getDistrictMap();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (StringUtil.isNotEmpty(dt.getString(i, "Province"))) {
         dt.set(i, "ProvinceName", DistrictMap.getString(dt.getString(i, "Province")));
       }
       if (StringUtil.isNotEmpty(dt.getString(i, "City"))) {
         dt.set(i, "CityName", DistrictMap.getString(dt.getString(i, "City")));
       }
       if (StringUtil.isNotEmpty(dt.getString(i, "District"))) {
         dt.set(i, "DistrictName", DistrictMap.getString(dt.getString(i, "District")));
       }
       if (dt.getString(i, "IsDefault").equals("Y"))
         dt.set(i, "IsDefaultName", "<b>默认</b>");
       else {
         dt.set(i, "IsDefaultName", "<a href='#;' onClick='setDefault(\"" + dt.getString(i, "ID") + "\",\"" + dt.getString(i, "UserName") + "\")'>设为默认</a>");
       }
     }
     dla.bindData(dt);
   }
 
   public void setDefault() {
     String ID = $V("ID");
     String UserName = $V("UserName");
     if ((StringUtil.isEmpty(UserName)) || (StringUtil.isEmpty(ID))) {
       this.Response.setLogInfo(0, "发生错误");
       return;
     }
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
 
   public void getAddress()
   {
     String ID = $V("AddrID");
     if (StringUtil.isEmpty(ID)) {
       this.Response.setLogInfo(0, "发生错误");
       return;
     }
     ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
     addr.setID(ID);
     addr.fill();
     this.Response.putAll(addr.toMapx());
   }
 
   public void doSave() {
     String UserName = User.getUserName();
     String ID = $V("ID");
     if (StringUtil.isEmpty(UserName)) {
       this.Response.setLogInfo(0, "发生错误");
       return;
     }
     ZDMemberAddrSchema memberaddr = new ZDMemberAddrSchema();
     boolean isExists = false;
     if (StringUtil.isNotEmpty(ID)) {
       memberaddr.setID(ID);
       memberaddr.fill();
       memberaddr.setModifyUser(UserName);
       memberaddr.setModifyTime(new Date());
       isExists = true;
     } else {
       memberaddr.setID(NoUtil.getMaxID("MemberAddr"));
       memberaddr.setAddUser(UserName);
       memberaddr.setAddTime(new Date());
     }
     memberaddr.setValue(this.Request);
     memberaddr.setUserName(UserName);
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
 
   public void del()
   {
     String ID = $V("ID");
     if (StringUtil.isNotEmpty(ID)) {
       ZDMemberAddrSchema addr = new ZDMemberAddrSchema();
       addr.setID(ID);
       addr.fill();
       if (addr.deleteAndBackup())
         this.Response.setLogInfo(1, "删除地址成功");
       else
         this.Response.setLogInfo(0, "删除地址失败");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.member.MemberAddress
 * JD-Core Version:    0.5.4
 */