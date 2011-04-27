 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZCMessageSet extends SchemaSet
 {
   public ZCMessageSet()
   {
     this(10, 0);
   }
 
   public ZCMessageSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZCMessageSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZCMessage";
     this.Columns = ZCMessageSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZCMessage values(?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZCMessage set ID=?,FromUser=?,ToUser=?,Box=?,ReadFlag=?,PopFlag=?,Subject=?,Content=?,AddTime=? where ID=?";
     this.FillAllSQL = "select * from ZCMessage  where ID=?";
     this.DeleteSQL = "delete from ZCMessage  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZCMessageSet();
   }
 
   public boolean add(ZCMessageSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZCMessageSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZCMessageSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZCMessageSchema get(int index) {
     ZCMessageSchema tSchema = (ZCMessageSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZCMessageSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZCMessageSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZCMessageSet
 * JD-Core Version:    0.5.4
 */