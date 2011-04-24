 package com.xdarkness.cms.webservice;
 
 public class UserOperationResponse
 {
   private String userCode;
   private int errorFlag;
   private int errorCode;
   private String errorMessage;
 
   public String getUserCode()
   {
     return this.userCode;
   }
   public void setUserCode(String userCode) {
     this.userCode = userCode;
   }
   public int getErrorFlag() {
     return this.errorFlag;
   }
   public void setErrorFlag(int errorFlag) {
     this.errorFlag = errorFlag;
   }
   public int getErrorCode() {
     return this.errorCode;
   }
   public void setErrorCode(int errorCode) {
     this.errorCode = errorCode;
   }
   public String getErrorMessage() {
     return this.errorMessage;
   }
   public void setErrorMessage(String errorMessage) {
     this.errorMessage = errorMessage;
   }
 }

          
/*    com.xdarkness.cms.webservice.UserOperationResponse
 * JD-Core Version:    0.6.0
 */