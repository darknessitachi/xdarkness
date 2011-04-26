package com.xdarkness.cms.api;

import com.xdarkness.framework.data.Transaction;
import com.xdarkness.framework.orm.Schema;


public abstract interface APIInterface
{
  public abstract long insert();

  public abstract long insert(Transaction paramTransaction);

  public abstract boolean update();

  public abstract boolean delete();

  public abstract boolean setSchema(Schema paramSchema);
}

          
/*    com.xdarkness.cms.api.APIInterface
 * JD-Core Version:    0.6.0
 */