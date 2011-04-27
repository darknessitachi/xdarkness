 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZWWorkflowSet extends SchemaSet
 {
   public ZWWorkflowSet()
   {
     this(10, 0);
   }
 
   public ZWWorkflowSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZWWorkflowSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZWWorkflow";
     this.Columns = ZWWorkflowSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZWWorkflow values(?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZWWorkflow set ID=?,Name=?,ConfigXML=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Memo=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=? where ID=?";
     this.FillAllSQL = "select * from ZWWorkflow  where ID=?";
     this.DeleteSQL = "delete from ZWWorkflow  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZWWorkflowSet();
   }
 
   public boolean add(ZWWorkflowSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZWWorkflowSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZWWorkflowSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZWWorkflowSchema get(int index) {
     ZWWorkflowSchema tSchema = (ZWWorkflowSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZWWorkflowSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZWWorkflowSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZWWorkflowSet
 * JD-Core Version:    0.5.4
 */