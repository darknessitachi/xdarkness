 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZCAdPositionSet extends SchemaSet
 {
   public BZCAdPositionSet()
   {
     this(10, 0);
   }
 
   public BZCAdPositionSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZCAdPositionSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZCAdPosition";
     this.Columns = BZCAdPositionSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZCAdPosition values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCAdPosition set ID=?,SiteID=?,PositionName=?,Code=?,Description=?,PositionType=?,PaddingTop=?,PaddingLeft=?,PositionWidth=?,PositionHeight=?,Align=?,Scroll=?,JsName=?,RelaCatalogID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCAdPosition  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCAdPosition  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZCAdPositionSet();
   }
 
   public boolean add(BZCAdPositionSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZCAdPositionSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZCAdPositionSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZCAdPositionSchema get(int index) {
     BZCAdPositionSchema tSchema = (BZCAdPositionSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZCAdPositionSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZCAdPositionSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZCAdPositionSet
 * JD-Core Version:    0.5.4
 */