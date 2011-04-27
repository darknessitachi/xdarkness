 package com.zving.member;
 
 import com.zving.cms.dataservice.MemberField;
 import com.zving.framework.Ajax;
 import com.zving.framework.CookieImpl;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZDMemberCompanyInfoSchema;
 import com.zving.schema.ZDMemberPersonInfoSchema;
 import java.util.Date;
 
 public class Register extends Ajax
 {
   public static Mapx init(Mapx params)
   {
     String SiteID = params.getString("SiteID");
     if (StringUtil.isEmpty(SiteID)) {
       SiteID = new QueryBuilder("select ID from ZCSite Order by AddTime desc").executeOneValue();
     }
     params.put("Columns", MemberField.getColumns(SiteID));
     return params;
   }
 
   public void checkUser() {
     String UserName = $V("UserName");
     Member member = new Member(UserName);
     if (member.isExists())
       this.Response.setLogInfo(0, "用户名已存在,请选择另外的用户名");
     else
       this.Response.setLogInfo(1, "用户名未注册,您可以放心使用");
   }
 
   public void register()
   {
     String userName = $V("UserName");
     String passWord = $V("PassWord");
     String eMail = $V("Email");
     String Type = $V("Type");
     String VerifyCode = $V("VerifyCode");
     Object authCode = User.getValue("_ZVING_AUTHKEY");
     if ((authCode != null) && (!authCode.equals(VerifyCode))) {
       this.Response.setLogInfo(0, "验证码错误");
       return;
     }
     Transaction trans = new Transaction();
     Member member = new Member(userName);
     member.setName(userName);
     member = MemberField.setPropValues(member, this.Request);
     member.setEmail(eMail);
     member.setType(Type);
     member.setSiteID($V("SiteID"));
     member.setRegIP(this.Request.getClientIP());
     member.setValue(this.Request);
     member.setPassword(passWord.trim());
     member.setStatus("X");
     member.setRegTime(new Date());
     member.setScore("0");
     member.setMemberLevel(new QueryBuilder("select ID from ZDMemberLevel where Score <= 0 order by Score desc")
       .executeOneValue());
 
     member.setLastLoginIP(this.Request.getClientIP());
     member.setLastLoginTime(new Date());
     if (StringUtil.isEmpty(member.getName())) {
       member.setName("注册用户");
     }
     trans.add(member, 1);
     if (Type.equalsIgnoreCase("Person")) {
       ZDMemberPersonInfoSchema person = new ZDMemberPersonInfoSchema();
       person.setUserName(member.getUserName());
       person.setNickName(member.getName());
       person.setMobile($V("Mobile"));
       person.setAddress($V("Address"));
       person.setZipCode($V("ZipCode"));
       trans.add(person, 1);
     } else {
       ZDMemberCompanyInfoSchema company = new ZDMemberCompanyInfoSchema();
       company.setUserName(member.getUserName());
       company.setCompanyName(member.getName());
       company.setEmail(member.getEmail());
       company.setMobile($V("Mobile"));
       company.setAddress($V("Address"));
       company.setZipCode($V("ZipCode"));
       trans.add(company, 1);
     }
 
     if (trans.commit()) {
       this.Cookie.setCookie("UserName", userName);
       User.setLogin(true);
       User.setUserName(userName);
       User.setMember(true);
       User.setValue("UserName", member.getUserName());
       User.setValue("Type", member.getType());
       User.setValue("SiteID", member.getSiteID());
 
       if (StringUtil.isNotEmpty(member.getName()))
         User.setValue("Name", member.getName());
       else {
         User.setValue("Name", member.getUserName());
       }
       this.Response.setLogInfo(1, "注册成功");
     } else {
       this.Response.setLogInfo(0, "注册失败");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.member.Register
 * JD-Core Version:    0.5.4
 */