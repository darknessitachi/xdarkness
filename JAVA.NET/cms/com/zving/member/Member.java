 package com.zving.member;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZDMemberSchema;
 
 public class Member extends ZDMemberSchema
 {
   private static final long serialVersionUID = 1L;
 
   public Member()
   {
   }
 
   public Member(String userName)
   {
     setUserName(userName);
   }
 
   public Member(String userName, String siteID) {
     setUserName(userName);
     setSiteID(siteID);
   }
 
   public void setPassword(String passWord) {
     super.setPassword(StringUtil.md5Hex(passWord));
   }
 
   public void setUnMd5Password(String passWord) {
     super.setPassword(passWord);
   }
 
   public boolean isExists() {
     boolean flag = false;
     if (StringUtil.isNotEmpty(getUserName())) {
       int count = new QueryBuilder("select count(*) from ZDMember where UserName = ?", getUserName()).executeInt();
       if (count > 0) {
         flag = true;
       }
     }
     return flag;
   }
 
   public boolean isExistsCurrentSite() {
     boolean flag = false;
     if (StringUtil.isNotEmpty(getUserName())) {
       int count = new QueryBuilder("select count(*) from ZDMember where UserName = ? and SiteID = ?", getUserName(), getSiteID()).executeInt();
       if (count > 0) {
         flag = true;
       }
     }
     return flag;
   }
 
   public boolean checkPassWord(String passWord) {
     boolean flag = false;
     if (StringUtil.isNotEmpty(passWord)) {
       passWord = StringUtil.md5Hex(passWord.trim());
       if (getPassword().equals(passWord)) {
         flag = true;
       }
     }
     return flag;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.member.Member
 * JD-Core Version:    0.5.4
 */