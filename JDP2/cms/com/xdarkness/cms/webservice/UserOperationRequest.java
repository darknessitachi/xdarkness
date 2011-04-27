 package com.xdarkness.cms.webservice;
 
 public class UserOperationRequest
 {
   private String operationType;
   private String userCode;
   private String userName;
   private String orgCode;
   private String orgName;
 
   public void setOperationType(String operationType)
   {
     this.operationType = operationType;
   }
   public String getOperationType() {
     return this.operationType;
   }
   public void setUserCode(String userCode) {
     this.userCode = userCode;
   }
   public String getUserCode() {
     return this.userCode;
   }
   public void setUserName(String userName) {
     this.userName = userName;
   }
   public String getUserName() {
     return this.userName;
   }
   public void setOrgCode(String orgCode) {
     this.orgCode = orgCode;
   }
   public String getOrgCode() {
     return this.orgCode;
   }
   public void setOrgName(String orgName) {
     this.orgName = orgName;
   }
   public String getOrgName() {
     return this.orgName;
   }
 }

          
/*    com.xdarkness.cms.webservice.UserOperationRequest
 * JD-Core Version:    0.6.0
 */