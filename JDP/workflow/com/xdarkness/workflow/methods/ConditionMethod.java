package com.xdarkness.workflow.methods;

import com.xdarkness.workflow.Context;

public abstract class ConditionMethod
{
  public abstract boolean validate(Context paramContext)
    throws Exception;
}

          
/*    com.xdarkness.workflow.methods.ConditionMethod
 * JD-Core Version:    0.6.0
 */