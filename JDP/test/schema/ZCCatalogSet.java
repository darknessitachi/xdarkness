/*    */ package schema;
/*    */ 
/*    */ import com.xdarkness.framework.orm.SchemaSet;
/*    */ 
/*    */ public class ZCCatalogSet extends SchemaSet
/*    */ {
/*    */   public ZCCatalogSet()
/*    */   {
/*  8 */     this(10, 0);
/*    */   }
/*    */ 
/*    */   public ZCCatalogSet(int initialCapacity) {
/* 12 */     this(initialCapacity, 0);
/*    */   }
/*    */ 
/*    */   public ZCCatalogSet(int initialCapacity, int capacityIncrement) {
/* 16 */     super(initialCapacity, capacityIncrement);
/* 17 */     this.TableCode = "ZCCatalog";
/* 18 */     this.Columns = ZCCatalogSchema._Columns;
/* 19 */     this.NameSpace = "com.xdarkness.schema";
/* 20 */     this.InsertAllSQL = "insert into ZCCatalog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/* 21 */     this.UpdateAllSQL = "update ZCCatalog set ID=?,ParentID=?,SiteID=?,Name=?,InnerCode=?,BranchInnerCode=?,Alias=?,URL=?,ImagePath=?,Type=?,IndexTemplate=?,ListTemplate=?,ListNameRule=?,DetailTemplate=?,DetailNameRule=?,RssTemplate=?,RssNameRule=?,Workflow=?,TreeLevel=?,ChildCount=?,IsLeaf=?,IsDirty=?,Total=?,OrderFlag=?,Logo=?,ListPageSize=?,ListPage=?,PublishFlag=?,SingleFlag=?,HitCount=?,Meta_Keywords=?,Meta_Description=?,OrderColumn=?,Integral=?,KeywordFlag=?,KeywordSetting=?,AllowContribute=?,ClusterSourceID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
/* 22 */     this.FillAllSQL = "select * from ZCCatalog  where ID=?";
/* 23 */     this.DeleteSQL = "delete from ZCCatalog  where ID=?";
/*    */   }
/*    */ 
/*    */   protected SchemaSet newInstance() {
/* 27 */     return new ZCCatalogSet();
/*    */   }
/*    */ 
/*    */   public boolean add(ZCCatalogSchema aSchema) {
/* 31 */     return super.add(aSchema);
/*    */   }
/*    */ 
/*    */   public boolean add(ZCCatalogSet aSet) {
/* 35 */     return super.add(aSet);
/*    */   }
/*    */ 
/*    */   public boolean remove(ZCCatalogSchema aSchema) {
/* 39 */     return super.remove(aSchema);
/*    */   }
/*    */ 
/*    */   public ZCCatalogSchema get(int index) {
/* 43 */     ZCCatalogSchema tSchema = (ZCCatalogSchema)super.getObject(index);
/* 44 */     return tSchema;
/*    */   }
/*    */ 
/*    */   public boolean set(int index, ZCCatalogSchema aSchema) {
/* 48 */     return super.set(index, aSchema);
/*    */   }
/*    */ 
/*    */   public boolean set(ZCCatalogSet aSet) {
/* 52 */     return super.set(aSet);
/*    */   }
/*    */ }

/* Location:       
 * Qualified Name:     com.xdarkness.schema.ZCCatalogSet
 * JD-Core Version:    0.6.0
 */