 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZWWorkflowSet extends SchemaSet
 {
   public BZWWorkflowSet()
   {
     this(10, 0);
   }
 
   public BZWWorkflowSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZWWorkflowSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZWWorkflow";
     this.Columns = BZWWorkflowSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZWWorkflow values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZWWorkflow set ID=?,Name=?,ConfigXML=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Memo=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZWWorkflow  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZWWorkflow  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZWWorkflowSet();
   }
 
   public boolean add(BZWWorkflowSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZWWorkflowSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZWWorkflowSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZWWorkflowSchema get(int index) {
     BZWWorkflowSchema tSchema = (BZWWorkflowSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZWWorkflowSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZWWorkflowSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZWWorkflowSet
 * JD-Core Version:    0.5.4
 */