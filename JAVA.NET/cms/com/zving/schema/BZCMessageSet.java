 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZCMessageSet extends SchemaSet
 {
   public BZCMessageSet()
   {
     this(10, 0);
   }
 
   public BZCMessageSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZCMessageSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZCMessage";
     this.Columns = BZCMessageSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZCMessage values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCMessage set ID=?,FromUser=?,ToUser=?,Box=?,ReadFlag=?,PopFlag=?,Subject=?,Content=?,AddTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCMessage  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCMessage  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZCMessageSet();
   }
 
   public boolean add(BZCMessageSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZCMessageSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZCMessageSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZCMessageSchema get(int index) {
     BZCMessageSchema tSchema = (BZCMessageSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZCMessageSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZCMessageSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZCMessageSet
 * JD-Core Version:    0.5.4
 */