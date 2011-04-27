 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZCArticleSet extends SchemaSet
 {
   public ZCArticleSet()
   {
     this(10, 0);
   }
 
   public ZCArticleSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZCArticleSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZCArticle";
     this.Columns = ZCArticleSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZCArticle values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZCArticle set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,BranchInnerCode=?,Title=?,SubTitle=?,ShortTitle=?,TitleStyle=?,ShortTitleStyle=?,Author=?,Type=?,Attribute=?,URL=?,RedirectURL=?,Status=?,Summary=?,Content=?,TopFlag=?,TopDate=?,TemplateFlag=?,Template=?,CommentFlag=?,CopyImageFlag=?,OrderFlag=?,ReferName=?,ReferURL=?,Keyword=?,Tag=?,RelativeArticle=?,RecommendArticle=?,ReferType=?,ReferSourceID=?,HitCount=?,StickTime=?,PublishFlag=?,Priority=?,LockUser=?,PublishDate=?,DownlineDate=?,ArchiveDate=?,WorkFlowID=?,IssueID=?,Logo=?,PageTitle=?,ClusterSource=?,ClusterTarget=?,ReferTarget=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.FillAllSQL = "select * from ZCArticle  where ID=?";
     this.DeleteSQL = "delete from ZCArticle  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZCArticleSet();
   }
 
   public boolean add(ZCArticleSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZCArticleSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZCArticleSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZCArticleSchema get(int index) {
     ZCArticleSchema tSchema = (ZCArticleSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZCArticleSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZCArticleSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZCArticleSet
 * JD-Core Version:    0.5.4
 */