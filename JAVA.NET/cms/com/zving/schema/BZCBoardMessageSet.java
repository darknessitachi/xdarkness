 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZCBoardMessageSet extends SchemaSet
 {
   public BZCBoardMessageSet()
   {
     this(10, 0);
   }
 
   public BZCBoardMessageSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZCBoardMessageSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZCBoardMessage";
     this.Columns = BZCBoardMessageSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZCBoardMessage values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCBoardMessage set ID=?,BoardID=?,Title=?,Content=?,PublishFlag=?,ReplyFlag=?,ReplyContent=?,EMail=?,QQ=?,Prop1=?,Prop2=?,Prop4=?,Prop3=?,IP=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCBoardMessage  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCBoardMessage  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZCBoardMessageSet();
   }
 
   public boolean add(BZCBoardMessageSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZCBoardMessageSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZCBoardMessageSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZCBoardMessageSchema get(int index) {
     BZCBoardMessageSchema tSchema = (BZCBoardMessageSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZCBoardMessageSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZCBoardMessageSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZCBoardMessageSet
 * JD-Core Version:    0.5.4
 */