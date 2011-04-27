 package com.zving.workflow.methods;
 
 import com.zving.framework.script.EvalException;
 import com.zving.framework.script.ScriptEngine;
 import com.zving.framework.utility.StringUtil;
 import com.zving.workflow.Context;
 
 public class MethodScript extends NodeMethod
 {
   private String script;
 
   public MethodScript(String script)
   {
     this.script = script;
   }
 
   public void execute(Context context)
     throws EvalException
   {
     if (StringUtil.isEmpty(this.script)) {
       return;
     }
     ScriptEngine se = new ScriptEngine(0);
     se.importPackage("com.zving.framework.cache");
     se.importPackage("com.zving.framework.data");
     se.importPackage("com.zving.framework.utility");
     se.importPackage("com.zving.workflow");
     se.compileFunction("_tmp", "return " + this.script);
     se.setVar("context", context);
     se.executeFunction("_tmp");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.workflow.methods.MethodScript
 * JD-Core Version:    0.5.4
 */