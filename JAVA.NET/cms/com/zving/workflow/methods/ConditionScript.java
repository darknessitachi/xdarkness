 package com.zving.workflow.methods;
 
 import com.zving.framework.script.EvalException;
 import com.zving.framework.script.ScriptEngine;
 import com.zving.framework.utility.StringUtil;
 import com.zving.workflow.Context;
 
 public class ConditionScript extends ConditionMethod
 {
   private String script;
 
   public void setScript(String script)
   {
     this.script = script;
   }
 
   public boolean validate(Context context)
     throws EvalException
   {
     if (StringUtil.isEmpty(this.script)) {
       return true;
     }
     ScriptEngine se = new ScriptEngine(0);
     se.importPackage("com.zving.framework.cache");
     se.importPackage("com.zving.framework.data");
     se.importPackage("com.zving.framework.utility");
     se.compileFunction("_tmp", "return " + this.script);
     se.setVar("context", context);
     Object obj = se.executeFunction("_tmp");
     if (obj instanceof Boolean) {
       return ((Boolean)obj).booleanValue();
     }
     throw new RuntimeException("流程条件脚本返回的不是布尔型!");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.workflow.methods.ConditionScript
 * JD-Core Version:    0.5.4
 */