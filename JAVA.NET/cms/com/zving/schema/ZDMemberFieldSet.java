 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZDMemberFieldSet extends SchemaSet
 {
   public ZDMemberFieldSet()
   {
     this(10, 0);
   }
 
   public ZDMemberFieldSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZDMemberFieldSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZDMemberField";
     this.Columns = ZDMemberFieldSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZDMemberField values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZDMemberField set SiteID=?,Name=?,Code=?,RealField=?,VerifyType=?,MaxLength=?,InputType=?,DefaultValue=?,ListOption=?,HTML=?,IsMandatory=?,OrderFlag=?,RowSize=?,ColSize=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where SiteID=? and Code=?";
     this.FillAllSQL = "select * from ZDMemberField  where SiteID=? and Code=?";
     this.DeleteSQL = "delete from ZDMemberField  where SiteID=? and Code=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZDMemberFieldSet();
   }
 
   public boolean add(ZDMemberFieldSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZDMemberFieldSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZDMemberFieldSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZDMemberFieldSchema get(int index) {
     ZDMemberFieldSchema tSchema = (ZDMemberFieldSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZDMemberFieldSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZDMemberFieldSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZDMemberFieldSet
 * JD-Core Version:    0.5.4
 */