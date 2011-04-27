 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZCImagePlayerSet extends SchemaSet
 {
   public BZCImagePlayerSet()
   {
     this(10, 0);
   }
 
   public BZCImagePlayerSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZCImagePlayerSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZCImagePlayer";
     this.Columns = BZCImagePlayerSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZCImagePlayer values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCImagePlayer set ID=?,Name=?,Code=?,SiteID=?,DisplayType=?,ImageSource=?,RelaCatalogInnerCode=?,Height=?,Width=?,DisplayCount=?,IsShowText=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCImagePlayer  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCImagePlayer  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZCImagePlayerSet();
   }
 
   public boolean add(BZCImagePlayerSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZCImagePlayerSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZCImagePlayerSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZCImagePlayerSchema get(int index) {
     BZCImagePlayerSchema tSchema = (BZCImagePlayerSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZCImagePlayerSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZCImagePlayerSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZCImagePlayerSet
 * JD-Core Version:    0.5.4
 */