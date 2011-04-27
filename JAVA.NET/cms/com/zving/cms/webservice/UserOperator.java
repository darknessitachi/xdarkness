package com.zving.cms.webservice;

public abstract interface UserOperator
{
  public static final String CREATE = "create";
  public static final String SUSPEND = "suspend";
  public static final String RESTORE = "restore";
  public static final String UPDATE = "update";

  public abstract UserOperationResponse doUserOperation(UserOperationRequest paramUserOperationRequest);
}

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.webservice.UserOperator
 * JD-Core Version:    0.5.4
 */