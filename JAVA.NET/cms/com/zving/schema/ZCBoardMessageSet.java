 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZCBoardMessageSet extends SchemaSet
 {
   public ZCBoardMessageSet()
   {
     this(10, 0);
   }
 
   public ZCBoardMessageSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZCBoardMessageSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZCBoardMessage";
     this.Columns = ZCBoardMessageSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZCBoardMessage values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZCBoardMessage set ID=?,BoardID=?,Title=?,Content=?,PublishFlag=?,ReplyFlag=?,ReplyContent=?,EMail=?,QQ=?,Prop1=?,Prop2=?,Prop4=?,Prop3=?,IP=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.FillAllSQL = "select * from ZCBoardMessage  where ID=?";
     this.DeleteSQL = "delete from ZCBoardMessage  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZCBoardMessageSet();
   }
 
   public boolean add(ZCBoardMessageSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZCBoardMessageSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZCBoardMessageSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZCBoardMessageSchema get(int index) {
     ZCBoardMessageSchema tSchema = (ZCBoardMessageSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZCBoardMessageSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZCBoardMessageSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZCBoardMessageSet
 * JD-Core Version:    0.5.4
 */