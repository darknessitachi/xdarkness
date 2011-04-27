 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZWStepSet extends SchemaSet
 {
   public ZWStepSet()
   {
     this(10, 0);
   }
 
   public ZWStepSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZWStepSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZWStep";
     this.Columns = ZWStepSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZWStep values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZWStep set ID=?,WorkflowID=?,InstanceID=?,DataVersionID=?,NodeID=?,ActionID=?,PreviousStepID=?,Owner=?,StartTime=?,FinishTime=?,State=?,Operators=?,AllowUser=?,AllowOrgan=?,AllowRole=?,Memo=?,AddTime=?,AddUser=? where ID=?";
     this.FillAllSQL = "select * from ZWStep  where ID=?";
     this.DeleteSQL = "delete from ZWStep  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZWStepSet();
   }
 
   public boolean add(ZWStepSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZWStepSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZWStepSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZWStepSchema get(int index) {
     ZWStepSchema tSchema = (ZWStepSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZWStepSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZWStepSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZWStepSet
 * JD-Core Version:    0.5.4
 */