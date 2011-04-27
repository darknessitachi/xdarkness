package com.zving.workflow.methods;

import com.zving.workflow.Context;

public abstract class ConditionMethod
{
  public abstract boolean validate(Context paramContext)
    throws Exception;
}

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.workflow.methods.ConditionMethod
 * JD-Core Version:    0.5.4
 */