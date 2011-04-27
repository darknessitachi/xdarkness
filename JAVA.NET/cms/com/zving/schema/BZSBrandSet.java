 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZSBrandSet extends SchemaSet
 {
   public BZSBrandSet()
   {
     this(10, 0);
   }
 
   public BZSBrandSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZSBrandSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZSBrand";
     this.Columns = BZSBrandSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZSBrand values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSBrand set ID=?,SiteID=?,Name=?,BranchInnerCode=?,Alias=?,URL=?,ImagePath=?,Info=?,IndexTemplate=?,ListTemplate=?,ListNameRule=?,DetailTemplate=?,DetailNameRule=?,RssTemplate=?,RssNameRule=?,OrderFlag=?,ListPageSize=?,ListPage=?,PublishFlag=?,SingleFlag=?,HitCount=?,Meta_Keywords=?,Meta_Description=?,KeywordFlag=?,KeywordSetting=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSBrand  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSBrand  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZSBrandSet();
   }
 
   public boolean add(BZSBrandSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZSBrandSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZSBrandSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZSBrandSchema get(int index) {
     BZSBrandSchema tSchema = (BZSBrandSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZSBrandSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZSBrandSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZSBrandSet
 * JD-Core Version:    0.5.4
 */