package com.xdarkness.cms.webservice;

public abstract interface UserOperator
{
  public static final String CREATE = "create";
  public static final String SUSPEND = "suspend";
  public static final String RESTORE = "restore";
  public static final String UPDATE = "update";

  public abstract UserOperationResponse doUserOperation(UserOperationRequest paramUserOperationRequest);
}

          
/*    com.xdarkness.cms.webservice.UserOperator
 * JD-Core Version:    0.6.0
 */