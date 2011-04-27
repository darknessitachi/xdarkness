 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZDMemberSet extends SchemaSet
 {
   public ZDMemberSet()
   {
     this(10, 0);
   }
 
   public ZDMemberSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZDMemberSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZDMember";
     this.Columns = ZDMemberSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZDMember values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZDMember set UserName=?,Password=?,Name=?,Email=?,Gender=?,Type=?,SiteID=?,Logo=?,Status=?,Score=?,Rank=?,MemberLevel=?,PWQuestion=?,PWAnswer=?,LastLoginIP=?,LastLoginTime=?,RegTime=?,RegIP=?,LoginMD5=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=?,Prop7=?,Prop8=?,Prop9=?,Prop10=?,Prop11=?,Prop12=?,Prop13=?,Prop14=?,Prop15=?,Prop16=?,Prop17=?,Prop18=?,Prop19=?,Prop20=? where UserName=?";
     this.FillAllSQL = "select * from ZDMember  where UserName=?";
     this.DeleteSQL = "delete from ZDMember  where UserName=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZDMemberSet();
   }
 
   public boolean add(ZDMemberSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZDMemberSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZDMemberSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZDMemberSchema get(int index) {
     ZDMemberSchema tSchema = (ZDMemberSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZDMemberSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZDMemberSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZDMemberSet
 * JD-Core Version:    0.5.4
 */