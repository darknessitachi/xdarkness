package com.zving.cms.api;

import com.zving.framework.data.Transaction;
import com.zving.framework.orm.Schema;

public abstract interface APIInterface
{
  public abstract long insert();

  public abstract long insert(Transaction paramTransaction);

  public abstract boolean update();

  public abstract boolean delete();

  public abstract boolean setSchema(Schema paramSchema);
}

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.api.APIInterface
 * JD-Core Version:    0.5.4
 */