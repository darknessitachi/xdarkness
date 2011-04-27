 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZSSendSet extends SchemaSet
 {
   public ZSSendSet()
   {
     this(10, 0);
   }
 
   public ZSSendSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZSSendSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZSSend";
     this.Columns = ZSSendSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZSSend values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSSend set ID=?,SiteID=?,Name=?,SendInfo=?,ArriveInfo=?,Info=?,Price=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.FillAllSQL = "select * from ZSSend  where ID=?";
     this.DeleteSQL = "delete from ZSSend  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZSSendSet();
   }
 
   public boolean add(ZSSendSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZSSendSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZSSendSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZSSendSchema get(int index) {
     ZSSendSchema tSchema = (ZSSendSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZSSendSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZSSendSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZSSendSet
 * JD-Core Version:    0.5.4
 */