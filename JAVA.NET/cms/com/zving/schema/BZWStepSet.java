 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZWStepSet extends SchemaSet
 {
   public BZWStepSet()
   {
     this(10, 0);
   }
 
   public BZWStepSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZWStepSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZWStep";
     this.Columns = BZWStepSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZWStep values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZWStep set ID=?,WorkflowID=?,InstanceID=?,DataVersionID=?,NodeID=?,ActionID=?,PreviousStepID=?,Owner=?,StartTime=?,FinishTime=?,State=?,Operators=?,AllowUser=?,AllowOrgan=?,AllowRole=?,Memo=?,AddTime=?,AddUser=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZWStep  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZWStep  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZWStepSet();
   }
 
   public boolean add(BZWStepSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZWStepSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZWStepSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZWStepSchema get(int index) {
     BZWStepSchema tSchema = (BZWStepSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZWStepSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZWStepSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZWStepSet
 * JD-Core Version:    0.5.4
 */