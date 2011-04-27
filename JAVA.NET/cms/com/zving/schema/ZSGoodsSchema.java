 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZSGoodsSchema extends Schema
 {
   private Long ID;
   private Long SiteID;
   private Long CatalogID;
   private String CatalogInnerCode;
   private Long BrandID;
   private String BranchInnerCode;
   private String Type;
   private String SN;
   private String Name;
   private String Alias;
   private String BarCode;
   private Long WorkFlowID;
   private String Status;
   private String Factory;
   private Long OrderFlag;
   private Float MarketPrice;
   private Float Price;
   private Float Discount;
   private Float OfferPrice;
   private Float MemberPrice;
   private Float VIPPrice;
   private Date EffectDate;
   private Long Store;
   private String Standard;
   private String Unit;
   private Long Score;
   private Long CommentCount;
   private Long SaleCount;
   private Long HitCount;
   private String Image0;
   private String Image1;
   private String Image2;
   private String Image3;
   private String RelativeGoods;
   private String Keyword;
   private Date TopDate;
   private String TopFlag;
   private String Content;
   private String Summary;
   private String RedirectURL;
   private String Attribute;
   private String RecommendGoods;
   private Long StickTime;
   private Date PublishDate;
   private Date DownlineDate;
   private String Memo;
   private String Prop1;
   private String Prop2;
   private String Prop3;
   private String Prop4;
   private String AddUser;
   private Date AddTime;
   private String ModifyUser;
   private Date ModifyTime;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("ID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("SiteID", 7, 1, 0, 0, true, false), 
     new SchemaColumn("CatalogID", 7, 2, 0, 0, true, false), 
     new SchemaColumn("CatalogInnerCode", 1, 3, 100, 0, true, false), 
     new SchemaColumn("BrandID", 7, 4, 0, 0, false, false), 
     new SchemaColumn("BranchInnerCode", 1, 5, 100, 0, false, false), 
     new SchemaColumn("Type", 1, 6, 2, 0, true, false), 
     new SchemaColumn("SN", 1, 7, 50, 0, false, false), 
     new SchemaColumn("Name", 1, 8, 100, 0, true, false), 
     new SchemaColumn("Alias", 1, 9, 100, 0, false, false), 
     new SchemaColumn("BarCode", 1, 10, 128, 0, false, false), 
     new SchemaColumn("WorkFlowID", 7, 11, 0, 0, false, false), 
     new SchemaColumn("Status", 1, 12, 20, 0, false, false), 
     new SchemaColumn("Factory", 1, 13, 100, 0, false, false), 
     new SchemaColumn("OrderFlag", 7, 14, 0, 0, false, false), 
     new SchemaColumn("MarketPrice", 5, 15, 12, 2, false, false), 
     new SchemaColumn("Price", 5, 16, 12, 2, false, false), 
     new SchemaColumn("Discount", 5, 17, 12, 2, false, false), 
     new SchemaColumn("OfferPrice", 5, 18, 12, 2, false, false), 
     new SchemaColumn("MemberPrice", 5, 19, 12, 2, false, false), 
     new SchemaColumn("VIPPrice", 5, 20, 12, 2, false, false), 
     new SchemaColumn("EffectDate", 0, 21, 0, 0, false, false), 
     new SchemaColumn("Store", 7, 22, 0, 0, true, false), 
     new SchemaColumn("Standard", 1, 23, 100, 0, false, false), 
     new SchemaColumn("Unit", 1, 24, 10, 0, false, false), 
     new SchemaColumn("Score", 7, 25, 0, 0, true, false), 
     new SchemaColumn("CommentCount", 7, 26, 0, 0, true, false), 
     new SchemaColumn("SaleCount", 7, 27, 0, 0, true, false), 
     new SchemaColumn("HitCount", 7, 28, 0, 0, true, false), 
     new SchemaColumn("Image0", 1, 29, 200, 0, false, false), 
     new SchemaColumn("Image1", 1, 30, 200, 0, false, false), 
     new SchemaColumn("Image2", 1, 31, 200, 0, false, false), 
     new SchemaColumn("Image3", 1, 32, 200, 0, false, false), 
     new SchemaColumn("RelativeGoods", 1, 33, 100, 0, false, false), 
     new SchemaColumn("Keyword", 1, 34, 100, 0, false, false), 
     new SchemaColumn("TopDate", 0, 35, 0, 0, false, false), 
     new SchemaColumn("TopFlag", 1, 36, 2, 0, true, false), 
     new SchemaColumn("Content", 10, 37, 0, 0, false, false), 
     new SchemaColumn("Summary", 1, 38, 2000, 0, false, false), 
     new SchemaColumn("RedirectURL", 1, 39, 200, 0, false, false), 
     new SchemaColumn("Attribute", 1, 40, 100, 0, false, false), 
     new SchemaColumn("RecommendGoods", 1, 41, 100, 0, false, false), 
     new SchemaColumn("StickTime", 7, 42, 0, 0, true, false), 
     new SchemaColumn("PublishDate", 0, 43, 0, 0, false, false), 
     new SchemaColumn("DownlineDate", 0, 44, 0, 0, false, false), 
     new SchemaColumn("Memo", 1, 45, 200, 0, false, false), 
     new SchemaColumn("Prop1", 1, 46, 200, 0, false, false), 
     new SchemaColumn("Prop2", 1, 47, 200, 0, false, false), 
     new SchemaColumn("Prop3", 1, 48, 200, 0, false, false), 
     new SchemaColumn("Prop4", 1, 49, 200, 0, false, false), 
     new SchemaColumn("AddUser", 1, 50, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 51, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 52, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 53, 0, 0, false, false) };
   public static final String _TableCode = "ZSGoods";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZSGoods values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZSGoods set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,BrandID=?,BranchInnerCode=?,Type=?,SN=?,Name=?,Alias=?,BarCode=?,WorkFlowID=?,Status=?,Factory=?,OrderFlag=?,MarketPrice=?,Price=?,Discount=?,OfferPrice=?,MemberPrice=?,VIPPrice=?,EffectDate=?,Store=?,Standard=?,Unit=?,Score=?,CommentCount=?,SaleCount=?,HitCount=?,Image0=?,Image1=?,Image2=?,Image3=?,RelativeGoods=?,Keyword=?,TopDate=?,TopFlag=?,Content=?,Summary=?,RedirectURL=?,Attribute=?,RecommendGoods=?,StickTime=?,PublishDate=?,DownlineDate=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZSGoods  where ID=?";
   protected static final String _FillAllSQL = "select * from ZSGoods  where ID=?";
 
   public ZSGoodsSchema()
   {
     this.TableCode = "ZSGoods";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZSGoods values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSGoods set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,BrandID=?,BranchInnerCode=?,Type=?,SN=?,Name=?,Alias=?,BarCode=?,WorkFlowID=?,Status=?,Factory=?,OrderFlag=?,MarketPrice=?,Price=?,Discount=?,OfferPrice=?,MemberPrice=?,VIPPrice=?,EffectDate=?,Store=?,Standard=?,Unit=?,Score=?,CommentCount=?,SaleCount=?,HitCount=?,Image0=?,Image1=?,Image2=?,Image3=?,RelativeGoods=?,Keyword=?,TopDate=?,TopFlag=?,Content=?,Summary=?,RedirectURL=?,Attribute=?,RecommendGoods=?,StickTime=?,PublishDate=?,DownlineDate=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.DeleteSQL = "delete from ZSGoods  where ID=?";
     this.FillAllSQL = "select * from ZSGoods  where ID=?";
     this.HasSetFlag = new boolean[54];
   }
 
   protected Schema newInstance() {
     return new ZSGoodsSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZSGoodsSet();
   }
 
   public ZSGoodsSet query() {
     return query(null, -1, -1);
   }
 
   public ZSGoodsSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZSGoodsSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZSGoodsSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZSGoodsSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 2) { if (v == null) this.CatalogID = null; else this.CatalogID = new Long(v.toString()); return; }
     if (i == 3) { this.CatalogInnerCode = ((String)v); return; }
     if (i == 4) { if (v == null) this.BrandID = null; else this.BrandID = new Long(v.toString()); return; }
     if (i == 5) { this.BranchInnerCode = ((String)v); return; }
     if (i == 6) { this.Type = ((String)v); return; }
     if (i == 7) { this.SN = ((String)v); return; }
     if (i == 8) { this.Name = ((String)v); return; }
     if (i == 9) { this.Alias = ((String)v); return; }
     if (i == 10) { this.BarCode = ((String)v); return; }
     if (i == 11) { if (v == null) this.WorkFlowID = null; else this.WorkFlowID = new Long(v.toString()); return; }
     if (i == 12) { this.Status = ((String)v); return; }
     if (i == 13) { this.Factory = ((String)v); return; }
     if (i == 14) { if (v == null) this.OrderFlag = null; else this.OrderFlag = new Long(v.toString()); return; }
     if (i == 15) { if (v == null) this.MarketPrice = null; else this.MarketPrice = new Float(v.toString()); return; }
     if (i == 16) { if (v == null) this.Price = null; else this.Price = new Float(v.toString()); return; }
     if (i == 17) { if (v == null) this.Discount = null; else this.Discount = new Float(v.toString()); return; }
     if (i == 18) { if (v == null) this.OfferPrice = null; else this.OfferPrice = new Float(v.toString()); return; }
     if (i == 19) { if (v == null) this.MemberPrice = null; else this.MemberPrice = new Float(v.toString()); return; }
     if (i == 20) { if (v == null) this.VIPPrice = null; else this.VIPPrice = new Float(v.toString()); return; }
     if (i == 21) { this.EffectDate = ((Date)v); return; }
     if (i == 22) { if (v == null) this.Store = null; else this.Store = new Long(v.toString()); return; }
     if (i == 23) { this.Standard = ((String)v); return; }
     if (i == 24) { this.Unit = ((String)v); return; }
     if (i == 25) { if (v == null) this.Score = null; else this.Score = new Long(v.toString()); return; }
     if (i == 26) { if (v == null) this.CommentCount = null; else this.CommentCount = new Long(v.toString()); return; }
     if (i == 27) { if (v == null) this.SaleCount = null; else this.SaleCount = new Long(v.toString()); return; }
     if (i == 28) { if (v == null) this.HitCount = null; else this.HitCount = new Long(v.toString()); return; }
     if (i == 29) { this.Image0 = ((String)v); return; }
     if (i == 30) { this.Image1 = ((String)v); return; }
     if (i == 31) { this.Image2 = ((String)v); return; }
     if (i == 32) { this.Image3 = ((String)v); return; }
     if (i == 33) { this.RelativeGoods = ((String)v); return; }
     if (i == 34) { this.Keyword = ((String)v); return; }
     if (i == 35) { this.TopDate = ((Date)v); return; }
     if (i == 36) { this.TopFlag = ((String)v); return; }
     if (i == 37) { this.Content = ((String)v); return; }
     if (i == 38) { this.Summary = ((String)v); return; }
     if (i == 39) { this.RedirectURL = ((String)v); return; }
     if (i == 40) { this.Attribute = ((String)v); return; }
     if (i == 41) { this.RecommendGoods = ((String)v); return; }
     if (i == 42) { if (v == null) this.StickTime = null; else this.StickTime = new Long(v.toString()); return; }
     if (i == 43) { this.PublishDate = ((Date)v); return; }
     if (i == 44) { this.DownlineDate = ((Date)v); return; }
     if (i == 45) { this.Memo = ((String)v); return; }
     if (i == 46) { this.Prop1 = ((String)v); return; }
     if (i == 47) { this.Prop2 = ((String)v); return; }
     if (i == 48) { this.Prop3 = ((String)v); return; }
     if (i == 49) { this.Prop4 = ((String)v); return; }
     if (i == 50) { this.AddUser = ((String)v); return; }
     if (i == 51) { this.AddTime = ((Date)v); return; }
     if (i == 52) { this.ModifyUser = ((String)v); return; }
     if (i != 53) return; this.ModifyTime = ((Date)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.SiteID;
     if (i == 2) return this.CatalogID;
     if (i == 3) return this.CatalogInnerCode;
     if (i == 4) return this.BrandID;
     if (i == 5) return this.BranchInnerCode;
     if (i == 6) return this.Type;
     if (i == 7) return this.SN;
     if (i == 8) return this.Name;
     if (i == 9) return this.Alias;
     if (i == 10) return this.BarCode;
     if (i == 11) return this.WorkFlowID;
     if (i == 12) return this.Status;
     if (i == 13) return this.Factory;
     if (i == 14) return this.OrderFlag;
     if (i == 15) return this.MarketPrice;
     if (i == 16) return this.Price;
     if (i == 17) return this.Discount;
     if (i == 18) return this.OfferPrice;
     if (i == 19) return this.MemberPrice;
     if (i == 20) return this.VIPPrice;
     if (i == 21) return this.EffectDate;
     if (i == 22) return this.Store;
     if (i == 23) return this.Standard;
     if (i == 24) return this.Unit;
     if (i == 25) return this.Score;
     if (i == 26) return this.CommentCount;
     if (i == 27) return this.SaleCount;
     if (i == 28) return this.HitCount;
     if (i == 29) return this.Image0;
     if (i == 30) return this.Image1;
     if (i == 31) return this.Image2;
     if (i == 32) return this.Image3;
     if (i == 33) return this.RelativeGoods;
     if (i == 34) return this.Keyword;
     if (i == 35) return this.TopDate;
     if (i == 36) return this.TopFlag;
     if (i == 37) return this.Content;
     if (i == 38) return this.Summary;
     if (i == 39) return this.RedirectURL;
     if (i == 40) return this.Attribute;
     if (i == 41) return this.RecommendGoods;
     if (i == 42) return this.StickTime;
     if (i == 43) return this.PublishDate;
     if (i == 44) return this.DownlineDate;
     if (i == 45) return this.Memo;
     if (i == 46) return this.Prop1;
     if (i == 47) return this.Prop2;
     if (i == 48) return this.Prop3;
     if (i == 49) return this.Prop4;
     if (i == 50) return this.AddUser;
     if (i == 51) return this.AddTime;
     if (i == 52) return this.ModifyUser;
     if (i == 53) return this.ModifyTime;
     return null;
   }
 
   public long getID()
   {
     if (this.ID == null) return 0L;
     return this.ID.longValue();
   }
 
   public void setID(long iD)
   {
     this.ID = new Long(iD);
   }
 
   public void setID(String iD)
   {
     if (iD == null) {
       this.ID = null;
       return;
     }
     this.ID = new Long(iD);
   }
 
   public long getSiteID()
   {
     if (this.SiteID == null) return 0L;
     return this.SiteID.longValue();
   }
 
   public void setSiteID(long siteID)
   {
     this.SiteID = new Long(siteID);
   }
 
   public void setSiteID(String siteID)
   {
     if (siteID == null) {
       this.SiteID = null;
       return;
     }
     this.SiteID = new Long(siteID);
   }
 
   public long getCatalogID()
   {
     if (this.CatalogID == null) return 0L;
     return this.CatalogID.longValue();
   }
 
   public void setCatalogID(long catalogID)
   {
     this.CatalogID = new Long(catalogID);
   }
 
   public void setCatalogID(String catalogID)
   {
     if (catalogID == null) {
       this.CatalogID = null;
       return;
     }
     this.CatalogID = new Long(catalogID);
   }
 
   public String getCatalogInnerCode()
   {
     return this.CatalogInnerCode;
   }
 
   public void setCatalogInnerCode(String catalogInnerCode)
   {
     this.CatalogInnerCode = catalogInnerCode;
   }
 
   public long getBrandID()
   {
     if (this.BrandID == null) return 0L;
     return this.BrandID.longValue();
   }
 
   public void setBrandID(long brandID)
   {
     this.BrandID = new Long(brandID);
   }
 
   public void setBrandID(String brandID)
   {
     if (brandID == null) {
       this.BrandID = null;
       return;
     }
     this.BrandID = new Long(brandID);
   }
 
   public String getBranchInnerCode()
   {
     return this.BranchInnerCode;
   }
 
   public void setBranchInnerCode(String branchInnerCode)
   {
     this.BranchInnerCode = branchInnerCode;
   }
 
   public String getType()
   {
     return this.Type;
   }
 
   public void setType(String type)
   {
     this.Type = type;
   }
 
   public String getSN()
   {
     return this.SN;
   }
 
   public void setSN(String sN)
   {
     this.SN = sN;
   }
 
   public String getName()
   {
     return this.Name;
   }
 
   public void setName(String name)
   {
     this.Name = name;
   }
 
   public String getAlias()
   {
     return this.Alias;
   }
 
   public void setAlias(String alias)
   {
     this.Alias = alias;
   }
 
   public String getBarCode()
   {
     return this.BarCode;
   }
 
   public void setBarCode(String barCode)
   {
     this.BarCode = barCode;
   }
 
   public long getWorkFlowID()
   {
     if (this.WorkFlowID == null) return 0L;
     return this.WorkFlowID.longValue();
   }
 
   public void setWorkFlowID(long workFlowID)
   {
     this.WorkFlowID = new Long(workFlowID);
   }
 
   public void setWorkFlowID(String workFlowID)
   {
     if (workFlowID == null) {
       this.WorkFlowID = null;
       return;
     }
     this.WorkFlowID = new Long(workFlowID);
   }
 
   public String getStatus()
   {
     return this.Status;
   }
 
   public void setStatus(String status)
   {
     this.Status = status;
   }
 
   public String getFactory()
   {
     return this.Factory;
   }
 
   public void setFactory(String factory)
   {
     this.Factory = factory;
   }
 
   public long getOrderFlag()
   {
     if (this.OrderFlag == null) return 0L;
     return this.OrderFlag.longValue();
   }
 
   public void setOrderFlag(long orderFlag)
   {
     this.OrderFlag = new Long(orderFlag);
   }
 
   public void setOrderFlag(String orderFlag)
   {
     if (orderFlag == null) {
       this.OrderFlag = null;
       return;
     }
     this.OrderFlag = new Long(orderFlag);
   }
 
   public float getMarketPrice()
   {
     if (this.MarketPrice == null) return 0.0F;
     return this.MarketPrice.floatValue();
   }
 
   public void setMarketPrice(float marketPrice)
   {
     this.MarketPrice = new Float(marketPrice);
   }
 
   public void setMarketPrice(String marketPrice)
   {
     if (marketPrice == null) {
       this.MarketPrice = null;
       return;
     }
     this.MarketPrice = new Float(marketPrice);
   }
 
   public float getPrice()
   {
     if (this.Price == null) return 0.0F;
     return this.Price.floatValue();
   }
 
   public void setPrice(float price)
   {
     this.Price = new Float(price);
   }
 
   public void setPrice(String price)
   {
     if (price == null) {
       this.Price = null;
       return;
     }
     this.Price = new Float(price);
   }
 
   public float getDiscount()
   {
     if (this.Discount == null) return 0.0F;
     return this.Discount.floatValue();
   }
 
   public void setDiscount(float discount)
   {
     this.Discount = new Float(discount);
   }
 
   public void setDiscount(String discount)
   {
     if (discount == null) {
       this.Discount = null;
       return;
     }
     this.Discount = new Float(discount);
   }
 
   public float getOfferPrice()
   {
     if (this.OfferPrice == null) return 0.0F;
     return this.OfferPrice.floatValue();
   }
 
   public void setOfferPrice(float offerPrice)
   {
     this.OfferPrice = new Float(offerPrice);
   }
 
   public void setOfferPrice(String offerPrice)
   {
     if (offerPrice == null) {
       this.OfferPrice = null;
       return;
     }
     this.OfferPrice = new Float(offerPrice);
   }
 
   public float getMemberPrice()
   {
     if (this.MemberPrice == null) return 0.0F;
     return this.MemberPrice.floatValue();
   }
 
   public void setMemberPrice(float memberPrice)
   {
     this.MemberPrice = new Float(memberPrice);
   }
 
   public void setMemberPrice(String memberPrice)
   {
     if (memberPrice == null) {
       this.MemberPrice = null;
       return;
     }
     this.MemberPrice = new Float(memberPrice);
   }
 
   public float getVIPPrice()
   {
     if (this.VIPPrice == null) return 0.0F;
     return this.VIPPrice.floatValue();
   }
 
   public void setVIPPrice(float vIPPrice)
   {
     this.VIPPrice = new Float(vIPPrice);
   }
 
   public void setVIPPrice(String vIPPrice)
   {
     if (vIPPrice == null) {
       this.VIPPrice = null;
       return;
     }
     this.VIPPrice = new Float(vIPPrice);
   }
 
   public Date getEffectDate()
   {
     return this.EffectDate;
   }
 
   public void setEffectDate(Date effectDate)
   {
     this.EffectDate = effectDate;
   }
 
   public long getStore()
   {
     if (this.Store == null) return 0L;
     return this.Store.longValue();
   }
 
   public void setStore(long store)
   {
     this.Store = new Long(store);
   }
 
   public void setStore(String store)
   {
     if (store == null) {
       this.Store = null;
       return;
     }
     this.Store = new Long(store);
   }
 
   public String getStandard()
   {
     return this.Standard;
   }
 
   public void setStandard(String standard)
   {
     this.Standard = standard;
   }
 
   public String getUnit()
   {
     return this.Unit;
   }
 
   public void setUnit(String unit)
   {
     this.Unit = unit;
   }
 
   public long getScore()
   {
     if (this.Score == null) return 0L;
     return this.Score.longValue();
   }
 
   public void setScore(long score)
   {
     this.Score = new Long(score);
   }
 
   public void setScore(String score)
   {
     if (score == null) {
       this.Score = null;
       return;
     }
     this.Score = new Long(score);
   }
 
   public long getCommentCount()
   {
     if (this.CommentCount == null) return 0L;
     return this.CommentCount.longValue();
   }
 
   public void setCommentCount(long commentCount)
   {
     this.CommentCount = new Long(commentCount);
   }
 
   public void setCommentCount(String commentCount)
   {
     if (commentCount == null) {
       this.CommentCount = null;
       return;
     }
     this.CommentCount = new Long(commentCount);
   }
 
   public long getSaleCount()
   {
     if (this.SaleCount == null) return 0L;
     return this.SaleCount.longValue();
   }
 
   public void setSaleCount(long saleCount)
   {
     this.SaleCount = new Long(saleCount);
   }
 
   public void setSaleCount(String saleCount)
   {
     if (saleCount == null) {
       this.SaleCount = null;
       return;
     }
     this.SaleCount = new Long(saleCount);
   }
 
   public long getHitCount()
   {
     if (this.HitCount == null) return 0L;
     return this.HitCount.longValue();
   }
 
   public void setHitCount(long hitCount)
   {
     this.HitCount = new Long(hitCount);
   }
 
   public void setHitCount(String hitCount)
   {
     if (hitCount == null) {
       this.HitCount = null;
       return;
     }
     this.HitCount = new Long(hitCount);
   }
 
   public String getImage0()
   {
     return this.Image0;
   }
 
   public void setImage0(String image0)
   {
     this.Image0 = image0;
   }
 
   public String getImage1()
   {
     return this.Image1;
   }
 
   public void setImage1(String image1)
   {
     this.Image1 = image1;
   }
 
   public String getImage2()
   {
     return this.Image2;
   }
 
   public void setImage2(String image2)
   {
     this.Image2 = image2;
   }
 
   public String getImage3()
   {
     return this.Image3;
   }
 
   public void setImage3(String image3)
   {
     this.Image3 = image3;
   }
 
   public String getRelativeGoods()
   {
     return this.RelativeGoods;
   }
 
   public void setRelativeGoods(String relativeGoods)
   {
     this.RelativeGoods = relativeGoods;
   }
 
   public String getKeyword()
   {
     return this.Keyword;
   }
 
   public void setKeyword(String keyword)
   {
     this.Keyword = keyword;
   }
 
   public Date getTopDate()
   {
     return this.TopDate;
   }
 
   public void setTopDate(Date topDate)
   {
     this.TopDate = topDate;
   }
 
   public String getTopFlag()
   {
     return this.TopFlag;
   }
 
   public void setTopFlag(String topFlag)
   {
     this.TopFlag = topFlag;
   }
 
   public String getContent()
   {
     return this.Content;
   }
 
   public void setContent(String content)
   {
     this.Content = content;
   }
 
   public String getSummary()
   {
     return this.Summary;
   }
 
   public void setSummary(String summary)
   {
     this.Summary = summary;
   }
 
   public String getRedirectURL()
   {
     return this.RedirectURL;
   }
 
   public void setRedirectURL(String redirectURL)
   {
     this.RedirectURL = redirectURL;
   }
 
   public String getAttribute()
   {
     return this.Attribute;
   }
 
   public void setAttribute(String attribute)
   {
     this.Attribute = attribute;
   }
 
   public String getRecommendGoods()
   {
     return this.RecommendGoods;
   }
 
   public void setRecommendGoods(String recommendGoods)
   {
     this.RecommendGoods = recommendGoods;
   }
 
   public long getStickTime()
   {
     if (this.StickTime == null) return 0L;
     return this.StickTime.longValue();
   }
 
   public void setStickTime(long stickTime)
   {
     this.StickTime = new Long(stickTime);
   }
 
   public void setStickTime(String stickTime)
   {
     if (stickTime == null) {
       this.StickTime = null;
       return;
     }
     this.StickTime = new Long(stickTime);
   }
 
   public Date getPublishDate()
   {
     return this.PublishDate;
   }
 
   public void setPublishDate(Date publishDate)
   {
     this.PublishDate = publishDate;
   }
 
   public Date getDownlineDate()
   {
     return this.DownlineDate;
   }
 
   public void setDownlineDate(Date downlineDate)
   {
     this.DownlineDate = downlineDate;
   }
 
   public String getMemo()
   {
     return this.Memo;
   }
 
   public void setMemo(String memo)
   {
     this.Memo = memo;
   }
 
   public String getProp1()
   {
     return this.Prop1;
   }
 
   public void setProp1(String prop1)
   {
     this.Prop1 = prop1;
   }
 
   public String getProp2()
   {
     return this.Prop2;
   }
 
   public void setProp2(String prop2)
   {
     this.Prop2 = prop2;
   }
 
   public String getProp3()
   {
     return this.Prop3;
   }
 
   public void setProp3(String prop3)
   {
     this.Prop3 = prop3;
   }
 
   public String getProp4()
   {
     return this.Prop4;
   }
 
   public void setProp4(String prop4)
   {
     this.Prop4 = prop4;
   }
 
   public String getAddUser()
   {
     return this.AddUser;
   }
 
   public void setAddUser(String addUser)
   {
     this.AddUser = addUser;
   }
 
   public Date getAddTime()
   {
     return this.AddTime;
   }
 
   public void setAddTime(Date addTime)
   {
     this.AddTime = addTime;
   }
 
   public String getModifyUser()
   {
     return this.ModifyUser;
   }
 
   public void setModifyUser(String modifyUser)
   {
     this.ModifyUser = modifyUser;
   }
 
   public Date getModifyTime()
   {
     return this.ModifyTime;
   }
 
   public void setModifyTime(Date modifyTime)
   {
     this.ModifyTime = modifyTime;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZSGoodsSchema
 * JD-Core Version:    0.5.4
 */