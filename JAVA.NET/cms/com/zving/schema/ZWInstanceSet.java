 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZWInstanceSet extends SchemaSet
 {
   public ZWInstanceSet()
   {
     this(10, 0);
   }
 
   public ZWInstanceSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZWInstanceSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZWInstance";
     this.Columns = ZWInstanceSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZWInstance values(?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZWInstance set ID=?,WorkflowID=?,Name=?,DataID=?,State=?,Memo=?,AddTime=?,AddUser=? where ID=?";
     this.FillAllSQL = "select * from ZWInstance  where ID=?";
     this.DeleteSQL = "delete from ZWInstance  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZWInstanceSet();
   }
 
   public boolean add(ZWInstanceSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZWInstanceSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZWInstanceSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZWInstanceSchema get(int index) {
     ZWInstanceSchema tSchema = (ZWInstanceSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZWInstanceSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZWInstanceSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZWInstanceSet
 * JD-Core Version:    0.5.4
 */