 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZCMessageBoardSet extends SchemaSet
 {
   public BZCMessageBoardSet()
   {
     this(10, 0);
   }
 
   public BZCMessageBoardSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZCMessageBoardSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZCMessageBoard";
     this.Columns = BZCMessageBoardSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZCMessageBoard values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCMessageBoard set ID=?,SiteID=?,Name=?,IsOpen=?,Description=?,MessageCount=?,RelaCatalogID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCMessageBoard  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCMessageBoard  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZCMessageBoardSet();
   }
 
   public boolean add(BZCMessageBoardSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZCMessageBoardSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZCMessageBoardSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZCMessageBoardSchema get(int index) {
     BZCMessageBoardSchema tSchema = (BZCMessageBoardSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZCMessageBoardSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZCMessageBoardSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZCMessageBoardSet
 * JD-Core Version:    0.5.4
 */