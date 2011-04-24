 package com.xdarkness.framework;
 
 import java.util.ArrayList;

import com.xdarkness.framework.jaf.JafRequest;
import com.xdarkness.framework.jaf.JafResponse;
 
 public class VerifyRuleList
 {
   private ArrayList array = new ArrayList();
   private String Message;
   private JafRequest request;
   private JafResponse response;
 
   public void add(String fieldID, String fieldName, String rule)
   {
     this.array.add(new String[] { fieldID, fieldName, rule });
   }
 
   public boolean doVerify()
   {
     VerifyRule rule = new VerifyRule();
     boolean flag = true;
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < this.array.size(); i++) {
       String[] f = (String[])this.array.get(i);
       rule.setRule(f[2]);
       if (!rule.verify(this.request.getString(f[0]))) {
         sb.append(rule.getMessages(f[1]));
         flag = false;
       }
     }
     if (!flag) {
       this.response.setStatus(0);
       this.Message = sb.toString();
       this.response.setMessage(sb.toString());
     }
     return flag;
   }
 
   public String getMessage() {
     return this.Message;
   }
 
   public JafRequest getRequest() {
     return this.request;
   }
 
   public void setRequest(JafRequest request) {
     this.request = request;
   }
 
   public JafResponse getResponse() {
     return this.response;
   }
 
   public void setResponse(JafResponse response) {
     this.response = response;
   }
 }

          
/*    com.xdarkness.framework.VerifyRuleList
 * JD-Core Version:    0.6.0
 */