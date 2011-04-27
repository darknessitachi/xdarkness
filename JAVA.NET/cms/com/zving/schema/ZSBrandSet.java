 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZSBrandSet extends SchemaSet
 {
   public ZSBrandSet()
   {
     this(10, 0);
   }
 
   public ZSBrandSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZSBrandSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZSBrand";
     this.Columns = ZSBrandSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZSBrand values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSBrand set ID=?,SiteID=?,Name=?,BranchInnerCode=?,Alias=?,URL=?,ImagePath=?,Info=?,IndexTemplate=?,ListTemplate=?,ListNameRule=?,DetailTemplate=?,DetailNameRule=?,RssTemplate=?,RssNameRule=?,OrderFlag=?,ListPageSize=?,ListPage=?,PublishFlag=?,SingleFlag=?,HitCount=?,Meta_Keywords=?,Meta_Description=?,KeywordFlag=?,KeywordSetting=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.FillAllSQL = "select * from ZSBrand  where ID=?";
     this.DeleteSQL = "delete from ZSBrand  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZSBrandSet();
   }
 
   public boolean add(ZSBrandSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZSBrandSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZSBrandSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZSBrandSchema get(int index) {
     ZSBrandSchema tSchema = (ZSBrandSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZSBrandSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZSBrandSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZSBrandSet
 * JD-Core Version:    0.5.4
 */