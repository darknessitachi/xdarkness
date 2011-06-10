 package com.abigdreamer.java.net.jaf.extend;
 
 import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.abigdreamer.java.net.util.LogUtil;
 
 public class ExtendTag extends TagSupport
 {
   private static final long serialVersionUID = 1L;
   private String target;
 
   public String getTarget()
   {
     return this.target;
   }
 
   public void setTarget(String target) {
     this.target = target;
   }
 
   public int doStartTag() throws JspException {
     try {
       if (ExtendManager.hasAction(this.target)) {
         IExtendAction[] actions = ExtendManager.find(this.target);
         for (int i = 0; i < actions.length; i++)
           if (!(actions[i] instanceof JSPExtendAction)) {
             LogUtil.getLogger().warn("类" + actions[i].getClass().getName() + "必须继承JSPExtendAction!");
           }
           else
             actions[i].execute(new Object[] { this.pageContext });
       }
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return 0;
   }
 }

          
/*    com.xdarkness.framework.extend.ExtendTag
 * JD-Core Version:    0.6.0
 */