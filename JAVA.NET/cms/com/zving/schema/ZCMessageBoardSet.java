 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZCMessageBoardSet extends SchemaSet
 {
   public ZCMessageBoardSet()
   {
     this(10, 0);
   }
 
   public ZCMessageBoardSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZCMessageBoardSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZCMessageBoard";
     this.Columns = ZCMessageBoardSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZCMessageBoard values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZCMessageBoard set ID=?,SiteID=?,Name=?,IsOpen=?,Description=?,MessageCount=?,RelaCatalogID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.FillAllSQL = "select * from ZCMessageBoard  where ID=?";
     this.DeleteSQL = "delete from ZCMessageBoard  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZCMessageBoardSet();
   }
 
   public boolean add(ZCMessageBoardSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZCMessageBoardSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZCMessageBoardSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZCMessageBoardSchema get(int index) {
     ZCMessageBoardSchema tSchema = (ZCMessageBoardSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZCMessageBoardSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZCMessageBoardSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZCMessageBoardSet
 * JD-Core Version:    0.5.4
 */