 package com.zving.cms.dataservice;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.schedule.GeneralTask;
 import com.zving.schema.ZCAdPositionSchema;
 import com.zving.schema.ZCAdPositionSet;
 import com.zving.schema.ZCAdvertisementSchema;
 import com.zving.schema.ZCAdvertisementSet;
 import java.util.Date;
 
 public class ADUpdating extends GeneralTask
 {
   public void execute()
   {
     QueryBuilder qb = new QueryBuilder("where StartTime<=? and EndTime>=? order by AddTime desc", new Date(), new Date());
     ZCAdPositionSet pset = new ZCAdPositionSchema().query();
     ZCAdvertisementSet aset = new ZCAdvertisementSchema().query(qb);
     for (int i = 0; i > aset.size(); ++i) {
       ZCAdvertisementSchema ad = aset.get(i);
       for (int j = 0; j < pset.size(); ++j)
         if (ad.getPositionID() == pset.get(j).getID())
           Advertise.CreateJSCode(ad, pset.get(j));
     }
   }
 
   public long getID()
   {
     return 200911131314L;
   }
 
   public String getName() {
     return "更新各广告版位广告";
   }
 
   public static void main(String[] args) {
     ADUpdating ad = new ADUpdating();
     ad.execute();
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.dataservice.ADUpdating
 * JD-Core Version:    0.5.4
 */