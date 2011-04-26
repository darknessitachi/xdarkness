
alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS';

/*修改ZCCatalog.ListPageSize字段的用途*/
update ZCCatalog set ListPageSize=20;

INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('MessageActionURL','留言提交地址','/SaveMessage.jsp',NULL,NULL,NULL,NULL,NULL,'2010-01-06 14:35:52','admin',NULL,NULL);

drop table ZCMessageBoard cascade constraints;

/*==============================================================*/
/* Table: ZCMessageBoard */
/*==============================================================*/
create table ZCMessageBoard(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (100) not null,
	IsOpen VARCHAR2 (2) not null,
	Description VARCHAR2 (500) ,
	MessageCount VARCHAR2 (20) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZCMessageBoard primary key (ID)
);


drop table ZCBoardMessage cascade constraints;

/*==============================================================*/
/* Table: ZCBoardMessage */
/*==============================================================*/
create table ZCBoardMessage(
	ID INTEGER not null,
	BoardID INTEGER not null,
	Title VARCHAR2 (100) not null,
	Content CLOB not null,
	PublishFlag VARCHAR2 (2) not null,
	ReplyFlag VARCHAR2 (2) not null,
	ReplyContent VARCHAR2 (1000) ,
	EMail VARCHAR2 (100) ,
	QQ VARCHAR2 (20) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	IP VARCHAR2 (20) not null,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZCBoardMessage primary key (ID)
);

drop table BZCMessageBoard cascade constraints;

/*==============================================================*/
/* Table: BZCMessageBoard */
/*==============================================================*/
create table BZCMessageBoard(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (100) not null,
	IsOpen VARCHAR2 (2) not null,
	Description VARCHAR2 (500) ,
	MessageCount VARCHAR2 (20) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZCMessageBoard primary key (ID,BackupNo)
);

drop table BZCBoardMessage cascade constraints;

/*==============================================================*/
/* Table: BZCBoardMessage */
/*==============================================================*/
create table BZCBoardMessage(
	ID INTEGER not null,
	BoardID INTEGER not null,
	Title VARCHAR2 (100) not null,
	Content CLOB not null,
	PublishFlag VARCHAR2 (2) not null,
	ReplyFlag VARCHAR2 (2) not null,
	ReplyContent VARCHAR2 (1000) ,
	EMail VARCHAR2 (100) ,
	QQ VARCHAR2 (20) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	IP VARCHAR2 (20) not null,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZCBoardMessage primary key (ID,BackupNo)
);

/*增加工作流表*/
drop table ZWWorkflow cascade constraints;
create table ZWWorkflow(
	ID INTEGER not null,
	Name VARCHAR2 (100) not null,
	ConfigXML CLOB ,
	Prop1 VARCHAR2 (40) ,
	Prop2 VARCHAR2 (40) ,
	Prop3 VARCHAR2 (40) ,
	Prop4 VARCHAR2 (40) ,
	Memo VARCHAR2 (200) ,
	AddTime DATE not null,
	AddUser VARCHAR2 (50) not null,
	ModifyTime DATE ,
	ModifyUser VARCHAR2 (50) ,
	constraint PK_ZWWorkflow primary key (ID)
);

drop table ZWInstance cascade constraints;
create table ZWInstance(
	ID INTEGER not null,
	WorkflowID INTEGER not null,
	Name VARCHAR2 (100) ,
	DataID VARCHAR2 (30) ,
	State VARCHAR2 (10) ,
	Memo VARCHAR2 (100) ,
	AddTime DATE not null,
	AddUser VARCHAR2 (50) not null,
	constraint PK_ZWInstance primary key (ID)
);

drop table ZWStep cascade constraints;
create table ZWStep(
	ID INTEGER not null,
	WorkflowID INTEGER not null,
	InstanceID INTEGER not null,
	DataVersionID VARCHAR2 (30) ,
	NodeID INTEGER ,
	PreviousStepID INTEGER ,
	Owner VARCHAR2 (50) ,
	StartTime DATE ,
	FinishTime DATE ,
	State VARCHAR2 (10) ,
	Operators VARCHAR2 (400) ,
	AllowUser VARCHAR2 (400) ,
	AllowOrgan VARCHAR2 (200) ,
	AllowRole VARCHAR2 (200) ,
	Memo VARCHAR2 (100) ,
	AddTime DATE not null,
	AddUser VARCHAR2 (50) not null,
	constraint PK_ZWStep primary key (ID)
);

drop table BZWWorkflow cascade constraints;
create table BZWWorkflow(
	ID INTEGER not null,
	Name VARCHAR2 (100) not null,
	ConfigXML CLOB ,
	Prop1 VARCHAR2 (40) ,
	Prop2 VARCHAR2 (40) ,
	Prop3 VARCHAR2 (40) ,
	Prop4 VARCHAR2 (40) ,
	Memo VARCHAR2 (200) ,
	AddTime DATE not null,
	AddUser VARCHAR2 (50) not null,
	ModifyTime DATE ,
	ModifyUser VARCHAR2 (50) ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZWWorkflow primary key (ID,BackupNo)
);

drop table BZWInstance cascade constraints;
create table BZWInstance(
	ID INTEGER not null,
	WorkflowID INTEGER not null,
	Name VARCHAR2 (100) ,
	DataID VARCHAR2 (30) ,
	State VARCHAR2 (10) ,
	Memo VARCHAR2 (100) ,
	AddTime DATE not null,
	AddUser VARCHAR2 (50) not null,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZWInstance primary key (ID,BackupNo)
);

drop table BZWStep cascade constraints;
create table BZWStep(
	ID INTEGER not null,
	WorkflowID INTEGER not null,
	InstanceID INTEGER not null,
	DataVersionID VARCHAR2 (30) ,
	NodeID INTEGER ,
	PreviousStepID INTEGER ,
	StartTime DATE ,
	FinishTime DATE ,
	State VARCHAR2 (10) ,
	Operators VARCHAR2 (400) ,
	AllowUser VARCHAR2 (400) ,
	AllowOrgan VARCHAR2 (200) ,
	AllowRole VARCHAR2 (200) ,
	Memo VARCHAR2 (100) ,
	AddTime DATE not null,
	AddUser VARCHAR2 (50) not null,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZWStep primary key (ID,BackupNo)
);

/*新的图形化工作流*/
update ZDMenu set URL='Workflow/WorkflowList.jsp' where URL='Platform/Workflow.jsp';


/** 汪维军 2010-01-07 图片播放器增加关联栏目字段RelaCatalogID */
create table BZCImagePlayer_TMP as select ID,Name,Code,SiteID,DisplayType,ImageSource,'0' as RelaCatalogInnerCode,Height,Width,DisplayCount,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCImagePlayer;
update BZCImagePlayer_TMP set RelaCatalogInnerCode=null;
alter table BZCImagePlayer_TMP modify RelaCatalogInnerCode VARCHAR2(100);
drop table BZCImagePlayer;
rename BZCImagePlayer_TMP to BZCImagePlayer;
alter table BZCImagePlayer add primary key (ID,BackupNo);
create table ZCImagePlayer_TMP as select ID,Name,Code,SiteID,DisplayType,ImageSource,'0' as RelaCatalogInnerCode,Height,Width,DisplayCount,AddUser,AddTime,ModifyUser,ModifyTime from ZCImagePlayer;
update ZCImagePlayer_TMP set RelaCatalogInnerCode=null;
alter table ZCImagePlayer_TMP modify RelaCatalogInnerCode VARCHAR2(100);
drop table ZCImagePlayer;
rename ZCImagePlayer_TMP to ZCImagePlayer;
alter table ZCImagePlayer add primary key (ID);
update ZCImagePlayer set ImageSource = '0';
create table BZCImagePlayer_TMP as select ID,Name,Code,SiteID,DisplayType,ImageSource,RelaCatalogInnerCode,Height,Width,DisplayCount,'0' as IsShowText,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCImagePlayer;
update BZCImagePlayer_TMP set IsShowText='0';
alter table BZCImagePlayer_TMP modify IsShowText VARCHAR2(2) not null;
drop table BZCImagePlayer;
rename BZCImagePlayer_TMP to BZCImagePlayer;
alter table BZCImagePlayer add primary key (ID,BackupNo);
create table ZCImagePlayer_TMP as select ID,Name,Code,SiteID,DisplayType,ImageSource,RelaCatalogInnerCode,Height,Width,DisplayCount,'0' as IsShowText,AddUser,AddTime,ModifyUser,ModifyTime from ZCImagePlayer;
update ZCImagePlayer_TMP set IsShowText='0';
alter table ZCImagePlayer_TMP modify IsShowText VARCHAR2(2) not null;
drop table ZCImagePlayer;
rename ZCImagePlayer_TMP to ZCImagePlayer;
alter table ZCImagePlayer add primary key (ID);
update zcimageplayer set IsShowText='Y';
alter table BZCImagePlayer drop column prop1;
alter table ZCImagePlayer drop column prop1;
alter table BZCImagePlayer drop column prop2;
alter table ZCImagePlayer drop column prop2;

/*去掉无用的角色关联*/
delete from ZDUserROle where RoleCode not in (select RoleCode from ZDRole) or UserName not in (select UserName from ZDUser);


/*工作流步骤备注字段加长 20100111 by wyuch*/
alter table zwstep modify Memo VARCHAR2(400);
alter table bzwstep modify Memo VARCHAR2(400);

update ZCCatalog set Workflow=null;
/** 汪维军 2010-01-11 栏目增加列表页最大分页数字段ListPage */
create table BZCCatalog_TMP as select ID,ParentID,SiteID,Name,InnerCode,BranchInnerCode,Alias,URL,ImagePath,Type,IndexTemplate,ListTemplate,ListNameRule,DetailTemplate,DetailNameRule,RssTemplate,RssNameRule,Workflow,TreeLevel,ChildCount,IsLeaf,IsDirty,Total,OrderFlag,Logo,ListPageSize,'0' as ListPage,PublishFlag,SingleFlag,HitCount,Meta_Keywords,Meta_Description,OrderColumn,Integral,KeywordFlag,KeywordSetting,AllowContribute,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCCatalog;
update BZCCatalog_TMP set ListPage=null;
alter table BZCCatalog_TMP modify ListPage INTEGER;
update BZCCatalog_TMP set ListPage=0;
alter table BZCCatalog_TMP modify ListPage INTEGER not null;
drop table BZCCatalog;
rename BZCCatalog_TMP to BZCCatalog;
alter table BZCCatalog add primary key (ID,BackupNo);
create table ZCCatalog_TMP as select ID,ParentID,SiteID,Name,InnerCode,BranchInnerCode,Alias,URL,ImagePath,Type,IndexTemplate,ListTemplate,ListNameRule,DetailTemplate,DetailNameRule,RssTemplate,RssNameRule,Workflow,TreeLevel,ChildCount,IsLeaf,IsDirty,Total,OrderFlag,Logo,ListPageSize,'0' as ListPage,PublishFlag,SingleFlag,HitCount,Meta_Keywords,Meta_Description,OrderColumn,Integral,KeywordFlag,KeywordSetting,AllowContribute,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from ZCCatalog;
update ZCCatalog_TMP set ListPage=null;
alter table ZCCatalog_TMP modify ListPage INTEGER;
update ZCCatalog_TMP set ListPage=0;
alter table ZCCatalog_TMP modify ListPage INTEGER not null;
drop table ZCCatalog;
rename ZCCatalog_TMP to ZCCatalog;
alter table ZCCatalog add primary key (ID);
create index idx45_catalog on zccatalog (SiteID,Type);
create index idx46_catalog on zccatalog (InnerCode);
update ZCCatalog set ListPageSize=20;
update ZCCatalog set ListPage=-1;

/** 汪维军 2010-01-12 媒体库默认栏目设置为不可删除 */
update ZCCatalog set Prop4='N' where name='默认图片' and type=4;
update ZCCatalog set Prop4='N' where name='默认视频' and type=5;
update ZCCatalog set Prop4='N' where name='默认音频' and type=6;
update ZCCatalog set Prop4='N' where name='默认附件' and type=7;
/* 王少亭 2010-01-12 修改字段 长度 */
alter table BZDUserLog modify LogType VARCHAR2(20);
alter table BZDUserLog modify SubType VARCHAR2(20);

/** 广告新增功能 版位表修改 徐喆 2010-01-14 */
create table zcadposition_TMP as select ID,SiteID,PositionName,Code,Description,PositionType,PaddingTop,PaddingLeft,PositionWidth,PositionHeight,'0' as Align,JsName,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcadposition;
update zcadposition_TMP set Align=null;
alter table zcadposition_TMP modify Align VARCHAR2(2);
drop table zcadposition;
rename zcadposition_TMP to zcadposition;
alter table zcadposition add primary key (ID);
create table bzcadposition_TMP as select ID,SiteID,PositionName,Code,Description,PositionType,PaddingTop,PaddingLeft,PositionWidth,PositionHeight,'0' as Align,JsName,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcadposition;
update bzcadposition_TMP set Align=null;
alter table bzcadposition_TMP modify Align VARCHAR2(2);
drop table bzcadposition;
rename bzcadposition_TMP to bzcadposition;
alter table bzcadposition add primary key (ID,BackupNo);
create table zcadposition_TMP as select ID,SiteID,PositionName,Code,Description,PositionType,PaddingTop,PaddingLeft,PositionWidth,PositionHeight,Align,'0' as Scroll,JsName,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcadposition;
update zcadposition_TMP set Scroll=null;
alter table zcadposition_TMP modify Scroll VARCHAR2(2);
drop table zcadposition;
rename zcadposition_TMP to zcadposition;
alter table zcadposition add primary key (ID);
create table bzcadposition_TMP as select ID,SiteID,PositionName,Code,Description,PositionType,PaddingTop,PaddingLeft,PositionWidth,PositionHeight,Align,'0' as Scroll,JsName,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcadposition;
update bzcadposition_TMP set Scroll=null;
alter table bzcadposition_TMP modify Scroll VARCHAR2(2);
drop table bzcadposition;
rename bzcadposition_TMP to bzcadposition;
alter table bzcadposition add primary key (ID,BackupNo);


/** 分发添加字段 操作Operation 汪维军 2010-01-14 */
create table ZCDeployJob_TMP as select ID,ConfigID,SiteID,Source,Target,Method,'0' as Operation,Host,Port,UserName,Password,Status,RetryCount,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from ZCDeployJob;
update ZCDeployJob_TMP set Operation=null;
alter table ZCDeployJob_TMP modify Operation VARCHAR2(100);
drop table ZCDeployJob;
rename ZCDeployJob_TMP to ZCDeployJob;
alter table ZCDeployJob add primary key (ID);
create table BZCDeployJob_TMP as select ID,ConfigID,SiteID,Source,Target,Method,'0' as Operation,Host,Port,UserName,Password,Status,RetryCount,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCDeployJob;
update BZCDeployJob_TMP set Operation=null;
alter table BZCDeployJob_TMP modify Operation VARCHAR2(100);
drop table BZCDeployJob;
rename BZCDeployJob_TMP to BZCDeployJob;
alter table BZCDeployJob add primary key (ID,BackupNo);
update ZCDeployJob set Operation='copy';


/** 站点表修改字段OrderFlag 汪维军 2010-01-16 */
update zcsite set orderflag='0';
update bzcsite set orderflag='0';
alter table zcsite modify orderflag INTEGER;
alter table bzcsite modify orderflag INTEGER;
update zcsite set orderflag='0';
update bzcsite set orderflag='0';

/** 添加商品品牌表 2010-01-18 */
drop table BZSBrand cascade constraints;

/*==============================================================*/
/* Table: BZSBrand */
/*==============================================================*/
create table BZSBrand(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (100) not null,
	BranchInnerCode VARCHAR2 (100) ,
	Alias VARCHAR2 (100) not null,
	URL VARCHAR2 (100) ,
	ImagePath VARCHAR2 (50) ,
	Info VARCHAR2 (1024) ,
	IndexTemplate VARCHAR2 (200) ,
	ListTemplate VARCHAR2 (200) ,
	ListNameRule VARCHAR2 (200) ,
	DetailTemplate VARCHAR2 (200) ,
	DetailNameRule VARCHAR2 (200) ,
	RssTemplate VARCHAR2 (200) ,
	RssNameRule VARCHAR2 (200) ,
	OrderFlag INTEGER not null,
	ListPageSize INTEGER ,
	ListPage INTEGER ,
	PublishFlag VARCHAR2 (2) not null,
	SingleFlag VARCHAR2 (2) ,
	HitCount INTEGER ,
	Meta_Keywords VARCHAR2 (200) ,
	Meta_Description VARCHAR2 (200) ,
	KeywordFlag VARCHAR2 (2) ,
	KeywordSetting VARCHAR2 (50) ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSBrand primary key (ID,BackupNo)
);

drop table ZSBrand cascade constraints;

/*==============================================================*/
/* Table: ZSBrand */
/*==============================================================*/
create table ZSBrand(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (100) not null,
	BranchInnerCode VARCHAR2 (100) ,
	Alias VARCHAR2 (100) not null,
	URL VARCHAR2 (100) ,
	ImagePath VARCHAR2 (50) ,
	Info VARCHAR2 (1024) ,
	IndexTemplate VARCHAR2 (200) ,
	ListTemplate VARCHAR2 (200) ,
	ListNameRule VARCHAR2 (200) ,
	DetailTemplate VARCHAR2 (200) ,
	DetailNameRule VARCHAR2 (200) ,
	RssTemplate VARCHAR2 (200) ,
	RssNameRule VARCHAR2 (200) ,
	OrderFlag INTEGER not null,
	ListPageSize INTEGER ,
	ListPage INTEGER ,
	PublishFlag VARCHAR2 (2) not null,
	SingleFlag VARCHAR2 (2) ,
	HitCount INTEGER ,
	Meta_Keywords VARCHAR2 (200) ,
	Meta_Description VARCHAR2 (200) ,
	KeywordFlag VARCHAR2 (2) ,
	KeywordSetting VARCHAR2 (50) ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZSBrand primary key (ID)
);

/** 崔建成 2010-01-19 将热点词类型设置为varchar类型 */
alter table ZCKeyword modify KeywordType VARCHAR2(255);
alter table BZCKeyword modify KeywordType VARCHAR2(255);

/** 崔建成 2010-01-20 将热点词类标识改为热点词类型 */
update ZCCatalogConfig set HotWordFlag = 0;
create table ZCCatalogConfig_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,CronExpression,PlanType,StartTime,IsUsing,HotWordFlag as HotWordType,AllowStatus,AfterEditStatus,Encoding,ArchiveTime,AttachDownFlag,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from ZCCatalogConfig;
drop table ZCCatalogConfig;
rename ZCCatalogConfig_TMP to ZCCatalogConfig;
alter table ZCCatalogConfig add primary key (ID);
create table BZCCatalogConfig_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,CronExpression,PlanType,StartTime,IsUsing,HotWordFlag as HotWordType,AllowStatus,AfterEditStatus,Encoding,ArchiveTime,AttachDownFlag,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCCatalogConfig;
drop table BZCCatalogConfig;
rename BZCCatalogConfig_TMP to BZCCatalogConfig;
alter table BZCCatalogConfig add primary key (ID,BackupNo);

/** 李伟仪 2010-01-19 ZSOrderItem */
drop table ZSOrderItem cascade constraints;

/*==============================================================*/
/* Table: ZSOrderItem */
/*==============================================================*/
create table ZSOrderItem(
	OrderID INTEGER not null,
	GoodsID INTEGER not null,
	SiteID INTEGER not null,
	MemberCode VARCHAR2 (200) ,
	SN VARCHAR2 (50) ,
	Name VARCHAR2 (200) ,
	Price NUMBER (12,2) ,
	Discount NUMBER (12,2) ,
	DiscountPrice NUMBER (12,2) ,
	Count INTEGER ,
	Amount NUMBER ,
	Score INTEGER ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZSOrderItem primary key (OrderID,GoodsID)
);

drop table BZSOrderItem cascade constraints;

/*==============================================================*/
/* Table: BZSOrderItem */
/*==============================================================*/
create table BZSOrderItem(
	OrderID INTEGER not null,
	GoodsID INTEGER not null,
	SiteID INTEGER not null,
	MemberCode VARCHAR2 (200) ,
	SN VARCHAR2 (50) ,
	Name VARCHAR2 (200) ,
	Price NUMBER (12,2) ,
	Discount NUMBER (12,2) ,
	DiscountPrice NUMBER (12,2) ,
	Count INTEGER ,
	Amount NUMBER ,
	Score INTEGER ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSOrderItem primary key (OrderID,GoodsID)
);

/** 李伟仪 2010-01-19 BZSOrder */
drop table BZSOrder cascade constraints;

/*==============================================================*/
/* Table: BZSOrder */
/*==============================================================*/
create table BZSOrder(
	ID INTEGER not null,
	SiteID INTEGER not null,
	MemberCode VARCHAR2 (200) ,
	IsValid VARCHAR2 (1) ,
	Status VARCHAR2 (40) ,
	Amount NUMBER (12,2) not null,
	Score INTEGER not null,
	Name VARCHAR2 (30) ,
	Province VARCHAR2 (6) ,
	City VARCHAR2 (6) ,
	District VARCHAR2 (6) ,
	Address VARCHAR2 (255) ,
	ZipCode VARCHAR2 (10) ,
	Tel VARCHAR2 (20) ,
	Mobile VARCHAR2 (20) ,
	HasInvoice VARCHAR2 (1) not null,
	InvoiceTitle VARCHAR2 (100) ,
	SendBeginDate DATE ,
	SendEndDate DATE ,
	SendTimeSlice VARCHAR2 (40) ,
	SendInfo VARCHAR2 (200) ,
	SendType VARCHAR2 (40) ,
	PaymentType VARCHAR2 (40) ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSOrder primary key (ID)
);

drop table ZSSend cascade constraints;

/*==============================================================*/
/* Table: ZSSend */
/*==============================================================*/
create table ZSSend(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (200) ,
	SendInfo VARCHAR2 (200) ,
	ArriveInfo VARCHAR2 (200) ,
	Info VARCHAR2 (200) ,
	Price NUMBER (12,2) ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZSSend primary key (ID)
);

drop table BZSSend cascade constraints;

/*==============================================================*/
/* Table: BZSSend */
/*==============================================================*/
create table BZSSend(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (200) ,
	SendInfo VARCHAR2 (200) ,
	ArriveInfo VARCHAR2 (200) ,
	Info VARCHAR2 (200) ,
	Price NUMBER (12,2) ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSSend primary key (ID,BackupNo)
);

drop table ZSPayment cascade constraints;

/*==============================================================*/
/* Table: ZSPayment */
/*==============================================================*/
create table ZSPayment(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (200) ,
	Info VARCHAR2 (1000) ,
	IsVisible VARCHAR2 (1) ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZSPayment primary key (ID)
);

drop table BZSPayment cascade constraints;

/*==============================================================*/
/* Table: BZSPayment */
/*==============================================================*/
create table BZSPayment(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (200) ,
	Info VARCHAR2 (1000) ,
	IsVisible VARCHAR2 (1) ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSPayment primary key (ID,BackupNo)
);

drop table ZSGoods cascade constraints;

/** 李伟仪 2010-01-20 ZSGoods,BZSGoods */
/*==============================================================*/
/* Table: ZSGoods */
/*==============================================================*/
create table ZSGoods(
	ID INTEGER not null,
	SiteID INTEGER not null,
	CatalogID INTEGER not null,
	CatalogInnerCode VARCHAR2 (100) not null,
	BrandID INTEGER ,
	BranchInnerCode VARCHAR2 (100) ,
	Type VARCHAR2 (2) not null,
	SN VARCHAR2 (50) ,
	Name VARCHAR2 (100) not null,
	Alias VARCHAR2 (100) ,
	BarCode VARCHAR2 (128) ,
	WorkFlowID INTEGER ,
	Status VARCHAR2 (20) ,
	Factory VARCHAR2 (100) ,
	OrderFlag INTEGER ,
	MarketPrice NUMBER (12,2) ,
	Price NUMBER (12,2) ,
	MemberPrice NUMBER (12,2) ,
	VIPPrice NUMBER (12,2) ,
	EffectDate DATE ,
	Store INTEGER not null,
	Standard VARCHAR2 (100) ,
	Unit VARCHAR2 (10) ,
	Score INTEGER not null,
	CommentCount INTEGER not null,
	SaleCount INTEGER not null,
	HitCount INTEGER not null,
	Image0 VARCHAR2 (200) ,
	Image1 VARCHAR2 (200) ,
	Image2 VARCHAR2 (200) ,
	Image3 VARCHAR2 (200) ,
	RelativeGoods VARCHAR2 (100) ,
	Keyword VARCHAR2 (100) ,
	TopDate DATE ,
	TopFlag VARCHAR2 (2) not null,
	Content CLOB ,
	Summary VARCHAR2 (2000) ,
	RedirectURL VARCHAR2 (200) ,
	Attribute VARCHAR2 (100) ,
	RecommendGoods VARCHAR2 (100) ,
	StickTime INTEGER not null,
	PublishDate DATE ,
	DownlineDate DATE ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZSGoods primary key (ID)
);

drop table BZSGoods cascade constraints;

/*==============================================================*/
/* Table: BZSGoods */
/*==============================================================*/
create table BZSGoods(
	ID INTEGER not null,
	SiteID INTEGER not null,
	CatalogID INTEGER not null,
	CatalogInnerCode VARCHAR2 (100) not null,
	BrandID INTEGER ,
	BranchInnerCode VARCHAR2 (100) ,
	Type VARCHAR2 (2) not null,
	SN VARCHAR2 (50) ,
	Name VARCHAR2 (100) not null,
	Alias VARCHAR2 (100) ,
	BarCode VARCHAR2 (128) ,
	WorkFlowID INTEGER ,
	Status VARCHAR2 (20) ,
	Factory VARCHAR2 (100) ,
	OrderFlag INTEGER ,
	MarketPrice NUMBER (12,2) ,
	Price NUMBER (12,2) ,
	MemberPrice NUMBER (12,2) ,
	VIPPrice NUMBER (12,2) ,
	EffectDate DATE ,
	Store INTEGER not null,
	Standard VARCHAR2 (100) ,
	Unit VARCHAR2 (10) ,
	Score INTEGER not null,
	CommentCount INTEGER not null,
	SaleCount INTEGER not null,
	HitCount INTEGER not null,
	Image0 VARCHAR2 (200) ,
	Image1 VARCHAR2 (200) ,
	Image2 VARCHAR2 (200) ,
	Image3 VARCHAR2 (200) ,
	RelativeGoods VARCHAR2 (100) ,
	Keyword VARCHAR2 (100) ,
	TopDate DATE ,
	TopFlag VARCHAR2 (2) not null,
	Content CLOB ,
	Summary VARCHAR2 (2000) ,
	RedirectURL VARCHAR2 (200) ,
	Attribute VARCHAR2 (100) ,
	RecommendGoods VARCHAR2 (100) ,
	StickTime INTEGER not null,
	PublishDate DATE ,
	DownlineDate DATE ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSGoods primary key (ID)
);

/* 李伟仪 添加会员商品收藏夹表ZSFavorite */
drop table ZSFavorite cascade constraints;

/*==============================================================*/
/* Table: ZSFavorite */
/*==============================================================*/
create table ZSFavorite(
	UserName VARCHAR2 (200) not null,
	GoodsID INTEGER not null,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZSFavorite primary key (UserName,GoodsID)
);

drop table ZDMember cascade constraints;

/*==============================================================*/
/* Table: ZDMember */
/*==============================================================*/
create table ZDMember(
	UserName VARCHAR2 (50) not null,
	Password VARCHAR2 (32) not null,
	Name VARCHAR2 (100) not null,
	Email VARCHAR2 (100) not null,
	Gender VARCHAR2 (1) ,
	Type VARCHAR2 (10) ,
	SiteID INTEGER ,
	Logo VARCHAR2 (100) ,
	Status VARCHAR2 (1) not null,
	Score VARCHAR2 (20) ,
	Rank VARCHAR2 (50) ,
	MemberLevel VARCHAR2 (10) ,
	PWQuestion VARCHAR2 (100) ,
	PWAnswer VARCHAR2 (100) ,
	LastLoginIP VARCHAR2 (16) ,
	LastLoginTime DATE ,
	RegTime DATE ,
	RegIP VARCHAR2 (16) ,
	LoginMD5 VARCHAR2 (32) ,
	Prop1 VARCHAR2 (100) ,
	Prop2 VARCHAR2 (100) ,
	Prop3 VARCHAR2 (100) ,
	Prop4 VARCHAR2 (100) ,
	Prop5 VARCHAR2 (100) ,
	Prop6 VARCHAR2 (100) ,
	Prop7 VARCHAR2 (100) ,
	Prop8 VARCHAR2 (100) ,
	Prop9 VARCHAR2 (100) ,
	Prop10 VARCHAR2 (100) ,
	Prop11 VARCHAR2 (100) ,
	Prop12 VARCHAR2 (100) ,
	Prop13 VARCHAR2 (100) ,
	Prop14 VARCHAR2 (100) ,
	Prop15 VARCHAR2 (100) ,
	Prop16 VARCHAR2 (100) ,
	Prop17 VARCHAR2 (100) ,
	Prop18 VARCHAR2 (100) ,
	Prop19 VARCHAR2 (100) ,
	Prop20 VARCHAR2 (100) ,
	constraint PK_ZDMember primary key (UserName)
);

drop table ZDMemberField cascade constraints;

/*==============================================================*/
/* Table: ZDMemberField */
/*==============================================================*/
create table ZDMemberField(
	SiteID INTEGER not null,
	Name VARCHAR2 (50) ,
	Code VARCHAR2 (50) not null,
	RealField VARCHAR2 (20) ,
	VerifyType VARCHAR2 (2) not null,
	MaxLength INTEGER ,
	InputType VARCHAR2 (20) not null,
	DefaultValue VARCHAR2 (50) ,
	ListOption VARCHAR2 (1000) ,
	HTML CLOB ,
	IsMandatory VARCHAR2 (2) not null,
	OrderFlag INTEGER ,
	RowSize INTEGER ,
	ColSize INTEGER ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZDMemberField primary key (SiteID,Code)
);

drop table BZDMember cascade constraints;

/*==============================================================*/
/* Table: BZDMember */
/*==============================================================*/
create table BZDMember(
	UserName VARCHAR2 (50) not null,
	Password VARCHAR2 (32) not null,
	Name VARCHAR2 (100) not null,
	Email VARCHAR2 (100) not null,
	Gender VARCHAR2 (1) ,
	Type VARCHAR2 (10) ,
	SiteID INTEGER ,
	Logo VARCHAR2 (100) ,
	Status VARCHAR2 (1) not null,
	Score VARCHAR2 (20) ,
	Rank VARCHAR2 (50) ,
	MemberLevel VARCHAR2 (10) ,
	PWQuestion VARCHAR2 (100) ,
	PWAnswer VARCHAR2 (100) ,
	LastLoginIP VARCHAR2 (16) ,
	LastLoginTime DATE ,
	RegTime DATE ,
	RegIP VARCHAR2 (16) ,
	LoginMD5 VARCHAR2 (32) ,
	Prop1 VARCHAR2 (100) ,
	Prop2 VARCHAR2 (100) ,
	Prop3 VARCHAR2 (100) ,
	Prop4 VARCHAR2 (100) ,
	Prop5 VARCHAR2 (100) ,
	Prop6 VARCHAR2 (100) ,
	Prop7 VARCHAR2 (100) ,
	Prop8 VARCHAR2 (100) ,
	Prop9 VARCHAR2 (100) ,
	Prop10 VARCHAR2 (100) ,
	Prop11 VARCHAR2 (100) ,
	Prop12 VARCHAR2 (100) ,
	Prop13 VARCHAR2 (100) ,
	Prop14 VARCHAR2 (100) ,
	Prop15 VARCHAR2 (100) ,
	Prop16 VARCHAR2 (100) ,
	Prop17 VARCHAR2 (100) ,
	Prop18 VARCHAR2 (100) ,
	Prop19 VARCHAR2 (100) ,
	Prop20 VARCHAR2 (100) ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZDMember primary key (UserName,BackupNo)
);

drop table BZDMemberField cascade constraints;

/*==============================================================*/
/* Table: BZDMemberField */
/*==============================================================*/
create table BZDMemberField(
	SiteID INTEGER not null,
	Name VARCHAR2 (50) ,
	Code VARCHAR2 (50) not null,
	RealField VARCHAR2 (20) ,
	VerifyType VARCHAR2 (2) not null,
	MaxLength INTEGER ,
	InputType VARCHAR2 (20) not null,
	DefaultValue VARCHAR2 (50) ,
	ListOption VARCHAR2 (1000) ,
	HTML CLOB ,
	IsMandatory VARCHAR2 (2) not null,
	OrderFlag INTEGER ,
	RowSize INTEGER ,
	ColSize INTEGER ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZDMemberField primary key (SiteID,Code,BackupNo)
);

/*增加全站动态应用相关的模板设置 2010-01-29 by wyuch*/
create table zcsite_TMP as select ID,Name,Alias,Info,BranchInnerCode,URL,RootPath,IndexTemplate,ListTemplate,DetailTemplate,EditorCss,Workflow,OrderFlag,LogoFileName,MessageBoardFlag,CommentAuditFlag,ChannelCount,MagzineCount,SpecialCount,ImageLibCount,VideoLibCount,AudioLibCount,AttachmentLibCount,ArticleCount,HitCount,ConfigXML,AutoIndexFlag,AutoStatFlag,'0' as HeaderTemplate,'0' as TopTemplate,'0' as BottomTemplate,Prop1,Prop2,Prop3,Prop4,Prop5,Prop6,AddUser,AddTime,ModifyUser,ModifyTime from zcsite;
update zcsite_TMP set HeaderTemplate=null;
alter table zcsite_TMP modify HeaderTemplate VARCHAR2(100);
update zcsite_TMP set TopTemplate=null;
alter table zcsite_TMP modify TopTemplate VARCHAR2(100);
update zcsite_TMP set BottomTemplate=null;
alter table zcsite_TMP modify BottomTemplate VARCHAR2(100);
drop table zcsite;
rename zcsite_TMP to zcsite;
alter table zcsite add primary key (ID);
create table bzcsite_TMP as select ID,Name,Alias,Info,BranchInnerCode,URL,RootPath,IndexTemplate,ListTemplate,DetailTemplate,EditorCss,Workflow,OrderFlag,LogoFileName,MessageBoardFlag,CommentAuditFlag,ChannelCount,MagzineCount,SpecialCount,ImageLibCount,VideoLibCount,AudioLibCount,AttachmentLibCount,ArticleCount,HitCount,ConfigXML,AutoIndexFlag,AutoStatFlag,'0' as HeaderTemplate,'0' as TopTemplate,'0' as BottomTemplate,Prop1,Prop2,Prop3,Prop4,Prop5,Prop6,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcsite;
update bzcsite_TMP set HeaderTemplate=null;
alter table bzcsite_TMP modify HeaderTemplate VARCHAR2(100);
update bzcsite_TMP set TopTemplate=null;
alter table bzcsite_TMP modify TopTemplate VARCHAR2(100);
update bzcsite_TMP set BottomTemplate=null;
alter table bzcsite_TMP modify BottomTemplate VARCHAR2(100);
drop table bzcsite;
rename bzcsite_TMP to bzcsite;
alter table bzcsite add primary key (ID,BackupNo);

/**王少亭 2010-01-31**/
create table ZCForumGroup_TMP as select ID,SiteID,RadminID,Name,SystemName,Type,Color,Image,LowerScore,UpperScore,AllowTheme,AllowReply,AllowSearch,AllowUserInfo,AllowPanel,AllowNickName,AllowVisit,AllowHeadImage,AllowFace,AllowAutograph,Verify,'0' as AllowEditUser,'0' as AllowForbidUser,'0' as AllowEditForum,'0' as AllowVerfyPost,'0' as AllowDel,'0' as AllowEdit,'0' as Hide,'0' as RemoveTheme,'0' as MoveTheme,'0' as TopTheme,'0' as UpOrDownTheme,'0' as BrightTheme,'0' as BestTheme,prop1,prop2,prop3,prop4,AddUser,AddTime,ModifyUser,ModifyTime,OrderFlag from ZCForumGroup;
update ZCForumGroup_TMP set BestTheme=null;
alter table ZCForumGroup_TMP modify BestTheme VARCHAR2(2);
update ZCForumGroup_TMP set BrightTheme=null;
alter table ZCForumGroup_TMP modify BrightTheme VARCHAR2(2);
update ZCForumGroup_TMP set UpOrDownTheme=null;
alter table ZCForumGroup_TMP modify UpOrDownTheme VARCHAR2(2);
update ZCForumGroup_TMP set TopTheme=null;
alter table ZCForumGroup_TMP modify TopTheme VARCHAR2(2);
update ZCForumGroup_TMP set MoveTheme=null;
alter table ZCForumGroup_TMP modify MoveTheme VARCHAR2(2);
update ZCForumGroup_TMP set RemoveTheme=null;
alter table ZCForumGroup_TMP modify RemoveTheme VARCHAR2(2);
update ZCForumGroup_TMP set Hide=null;
alter table ZCForumGroup_TMP modify Hide VARCHAR2(2);
update ZCForumGroup_TMP set AllowEdit=null;
alter table ZCForumGroup_TMP modify AllowEdit VARCHAR2(2);
update ZCForumGroup_TMP set AllowDel=null;
alter table ZCForumGroup_TMP modify AllowDel VARCHAR2(2);
update ZCForumGroup_TMP set AllowVerfyPost=null;
alter table ZCForumGroup_TMP modify AllowVerfyPost VARCHAR2(2);
update ZCForumGroup_TMP set AllowEditForum=null;
alter table ZCForumGroup_TMP modify AllowEditForum VARCHAR2(2);
update ZCForumGroup_TMP set AllowForbidUser=null;
alter table ZCForumGroup_TMP modify AllowForbidUser VARCHAR2(2);
update ZCForumGroup_TMP set AllowEditUser=null;
alter table ZCForumGroup_TMP modify AllowEditUser VARCHAR2(2);
drop table ZCForumGroup;
rename ZCForumGroup_TMP to ZCForumGroup;
alter table ZCForumGroup add primary key (ID);

create table BZCForumGroup_TMP as select ID,SiteID,RadminID,Name,SystemName,Type,Color,Image,LowerScore,UpperScore,AllowTheme,AllowReply,AllowSearch,AllowUserInfo,AllowPanel,AllowNickName,AllowVisit,AllowHeadImage,AllowFace,AllowAutograph,Verify,'0' as AllowEditUser,'0' as AllowForbidUser,'0' as AllowEditForum,'0' as AllowVerfyPost,'0' as AllowDel,'0' as AllowEdit,'0' as Hide,'0' as RemoveTheme,'0' as MoveTheme,'0' as TopTheme,'0' as UpOrDownTheme,'0' as BrightTheme,'0' as BestTheme,prop1,prop2,prop3,prop4,AddUser,AddTime,ModifyUser,ModifyTime,OrderFlag,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCForumGroup;
update BZCForumGroup_TMP set BestTheme=null;
alter table BZCForumGroup_TMP modify BestTheme VARCHAR2(2);
update BZCForumGroup_TMP set BrightTheme=null;
alter table BZCForumGroup_TMP modify BrightTheme VARCHAR2(2);
update BZCForumGroup_TMP set UpOrDownTheme=null;
alter table BZCForumGroup_TMP modify UpOrDownTheme VARCHAR2(2);
update BZCForumGroup_TMP set TopTheme=null;
alter table BZCForumGroup_TMP modify TopTheme VARCHAR2(2);
update BZCForumGroup_TMP set MoveTheme=null;
alter table BZCForumGroup_TMP modify MoveTheme VARCHAR2(2);
update BZCForumGroup_TMP set RemoveTheme=null;
alter table BZCForumGroup_TMP modify RemoveTheme VARCHAR2(2);
update BZCForumGroup_TMP set Hide=null;
alter table BZCForumGroup_TMP modify Hide VARCHAR2(2);
update BZCForumGroup_TMP set AllowEdit=null;
alter table BZCForumGroup_TMP modify AllowEdit VARCHAR2(2);
update BZCForumGroup_TMP set AllowDel=null;
alter table BZCForumGroup_TMP modify AllowDel VARCHAR2(2);
update BZCForumGroup_TMP set AllowVerfyPost=null;
alter table BZCForumGroup_TMP modify AllowVerfyPost VARCHAR2(2);
update BZCForumGroup_TMP set AllowEditForum=null;
alter table BZCForumGroup_TMP modify AllowEditForum VARCHAR2(2);
update BZCForumGroup_TMP set AllowForbidUser=null;
alter table BZCForumGroup_TMP modify AllowForbidUser VARCHAR2(2);
update BZCForumGroup_TMP set AllowEditUser=null;
alter table BZCForumGroup_TMP modify AllowEditUser VARCHAR2(2);
drop table BZCForumGroup;
rename BZCForumGroup_TMP to BZCForumGroup;
alter table BZCForumGroup add primary key (ID,BackupNo);

create table ZCForumMember_TMP as select UserName,SiteID,AdminID,UserGroupID,'0' as DefinedID,NickName,ThemeCount,ReplyCount,HeadImage,UseSelfImage,Status,ForumScore,ForumSign,LastLoginTime,LastLogoutTime,prop1,prop2,prop3,prop4,AddUser,AddTime,ModifyUser,ModifyTime from ZCForumMember;
update ZCForumMember_TMP set DefinedID=null;
alter table ZCForumMember_TMP modify DefinedID INTEGER;
drop table ZCForumMember;
rename ZCForumMember_TMP to ZCForumMember;
alter table ZCForumMember add primary key (UserName);
create table BZCForumMember_TMP as select UserName,SiteID,AdminID,UserGroupID,'0' as DefinedID,NickName,ThemeCount,ReplyCount,HeadImage,UseSelfImage,Status,ForumScore,ForumSign,LastLoginTime,LastLogoutTime,prop1,prop2,prop3,prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCForumMember;
update BZCForumMember_TMP set DefinedID=null;
alter table BZCForumMember_TMP modify DefinedID INTEGER;
drop table BZCForumMember;
rename BZCForumMember_TMP to BZCForumMember;
alter table BZCForumMember add primary key (UserName,BackupNo);



create table ZCForum_TMP as select ID,SiteID,ParentID,Type,Name,Status,Pic,Visible,Info,ThemeCount,Verify,Locked,'0' as UnLockID,AllowTheme,EditPost,ReplyPost,Recycle,AllowHTML,AllowFace,AnonyPost,URL,Image,Password,'0' as UnPasswordID,Word,PostCount,ForumAdmin,TodayPostCount,LastThemeID,LastPost,LastPoster,OrderFlag,prop1,prop2,prop3,prop4,AddUser,AddTime,ModifyUser,ModifyTime from ZCForum;
update ZCForum_TMP set UnLockID=null;
alter table ZCForum_TMP modify UnLockID VARCHAR2(300);
update ZCForum_TMP set UnPasswordID=null;
alter table ZCForum_TMP modify UnPasswordID VARCHAR2(300);
drop table ZCForum;
rename ZCForum_TMP to ZCForum;
alter table ZCForum add primary key (ID);

/*外部数据库增加拉丁字符集标志 2010-02-01*/
create table zcdatabase_TMP as select ID,SiteID,Name,ServerType,Address,Port,UserName,Password,DBName,TestTable,'0' as Latin1Flag,Memo,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcdatabase;
update zcdatabase_TMP set Latin1Flag=null;
alter table zcdatabase_TMP modify Latin1Flag VARCHAR2(2);
drop table zcdatabase;
rename zcdatabase_TMP to zcdatabase;
alter table zcdatabase add primary key (ID);
create table bzcdatabase_TMP as select ID,SiteID,Name,ServerType,Address,Port,UserName,Password,DBName,TestTable,'0' as Latin1Flag,Memo,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcdatabase;
update bzcdatabase_TMP set Latin1Flag=null;
alter table bzcdatabase_TMP modify Latin1Flag VARCHAR2(2);
drop table bzcdatabase;
rename bzcdatabase_TMP to bzcdatabase;
alter table bzcdatabase add primary key (ID,BackupNo);

/*工作流步骤增加来源动作ID 2010-02-01 by wyuch*/
create table zwstep_TMP as select ID,WorkflowID,InstanceID,DataVersionID,NodeID,'0' as ActionID,PreviousStepID,Owner,StartTime,FinishTime,State,Operators,AllowUser,AllowOrgan,AllowRole,Memo,AddTime,AddUser from zwstep;
update zwstep_TMP set ActionID=null;
alter table zwstep_TMP modify ActionID INTEGER;
drop table zwstep;
rename zwstep_TMP to zwstep;
alter table zwstep add primary key (ID);
create index idx53_step on zwstep (InstanceID);
create index idx54_step on zwstep (NodeID);
create index idx55_step on zwstep (owner);
create table bzwstep_TMP as select ID,WorkflowID,InstanceID,DataVersionID,NodeID,'0' as ActionID,PreviousStepID,StartTime,FinishTime,State,Operators,AllowUser,AllowOrgan,AllowRole,Memo,AddTime,AddUser,BackupNo,BackupOperator,BackupTime,BackupMemo from bzwstep;
update bzwstep_TMP set ActionID=null;
alter table bzwstep_TMP modify ActionID INTEGER;
drop table bzwstep;
rename bzwstep_TMP to bzwstep;
alter table bzwstep add primary key (ID,BackupNo);

/*站点增加允许投稿的设置项 2010-02-05*/
create table zcsite_TMP as select ID,Name,Alias,Info,BranchInnerCode,URL,RootPath,IndexTemplate,ListTemplate,DetailTemplate,EditorCss,Workflow,OrderFlag,LogoFileName,MessageBoardFlag,CommentAuditFlag,ChannelCount,MagzineCount,SpecialCount,ImageLibCount,VideoLibCount,AudioLibCount,AttachmentLibCount,ArticleCount,HitCount,ConfigXML,AutoIndexFlag,AutoStatFlag,HeaderTemplate,TopTemplate,BottomTemplate,'0' as AllowContribute,Prop1,Prop2,Prop3,Prop4,Prop5,Prop6,AddUser,AddTime,ModifyUser,ModifyTime from zcsite;
update zcsite_TMP set AllowContribute=null;
alter table zcsite_TMP modify AllowContribute VARCHAR2(2);
drop table zcsite;
rename zcsite_TMP to zcsite;
alter table zcsite add primary key (ID);
create table bzcsite_TMP as select ID,Name,Alias,Info,BranchInnerCode,URL,RootPath,IndexTemplate,ListTemplate,DetailTemplate,EditorCss,Workflow,OrderFlag,LogoFileName,MessageBoardFlag,CommentAuditFlag,ChannelCount,MagzineCount,SpecialCount,ImageLibCount,VideoLibCount,AudioLibCount,AttachmentLibCount,ArticleCount,HitCount,ConfigXML,AutoIndexFlag,AutoStatFlag,HeaderTemplate,TopTemplate,BottomTemplate,'0' as AllowContribute,Prop1,Prop2,Prop3,Prop4,Prop5,Prop6,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcsite;
update bzcsite_TMP set AllowContribute=null;
alter table bzcsite_TMP modify AllowContribute VARCHAR2(2);
drop table bzcsite;
rename bzcsite_TMP to bzcsite;
alter table bzcsite add primary key (ID,BackupNo);

/*消息表主题长度不够*/
alter table zcmessage modify Subject VARCHAR2(500);
alter table bzcmessage modify Subject VARCHAR2(500);

/*站点的配置要将CatalogID设为0，不要设为null 2010-02-06*/
update ZCCatalogConfig set CatalogID=0 where CatalogID is null or CatalogID='';

create table zcmessage_TMP as select ID,FromUser,ToUser,Box,ReadFlag,'0' as PopFlag,Subject,Content,AddTime from zcmessage;
update zcmessage_TMP set PopFlag=null;
alter table zcmessage_TMP modify PopFlag INTEGER;
drop table zcmessage;
rename zcmessage_TMP to zcmessage;
alter table zcmessage add primary key (ID);
create table bzcmessage_TMP as select ID,FromUser,ToUser,Box,ReadFlag,'0' as PopFlag,Subject,Content,AddTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcmessage;
update bzcmessage_TMP set PopFlag=null;
alter table bzcmessage_TMP modify PopFlag INTEGER;
drop table bzcmessage;
rename bzcmessage_TMP to bzcmessage;
alter table bzcmessage add primary key (ID,BackupNo);

/*王少亭漏加了B表,2010-02-06*/
create table BZCForum_TMP as select ID,SiteID,ParentID,Type,Name,Status,Pic,Visible,Info,ThemeCount,Verify,Locked,'0' as UnLockID,AllowTheme,EditPost,ReplyPost,Recycle,AllowHTML,AllowFace,AnonyPost,URL,Image,Password,'0' as UnPasswordID,Word,PostCount,ForumAdmin,TodayPostCount,LastThemeID,LastPost,LastPoster,OrderFlag,prop1,prop2,prop3,prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCForum;
update BZCForum_TMP set UnLockID=null;
alter table BZCForum_TMP modify UnLockID VARCHAR2(300);
update BZCForum_TMP set UnPasswordID=null;
alter table BZCForum_TMP modify UnPasswordID VARCHAR2(300);
drop table BZCForum;
rename BZCForum_TMP to BZCForum;
alter table BZCForum add primary key (ID,BackupNo);

/*谭喜才修改zcstatitem的字段item的长度*/
alter table zcstatitem modify item VARCHAR2(200);

/*修改zdcode,zdconfig两表字段长度，以免UTF8下字符超长*/
alter table zdconfig modify Name VARCHAR2(100);
alter table zdconfig modify Memo VARCHAR2(400);
alter table zdcode modify CodeName VARCHAR2(100);
alter table zdcode modify Memo VARCHAR2(400);
alter table bzdconfig modify Name VARCHAR2(100);
alter table bzdconfig modify Memo VARCHAR2(400);
alter table bzdcode modify CodeName VARCHAR2(100);
alter table bzdcode modify Memo VARCHAR2(400);

/**增加ZDMemberAddr表，用于会员地址功能**/
drop table ZDMemberAddr cascade constraints;

/*==============================================================*/
/* Table: ZDMemberAddr */
/*==============================================================*/
create table ZDMemberAddr(
	ID INTEGER not null,
	UserName VARCHAR2 (200) not null,
	RealName VARCHAR2 (100) ,
	Country VARCHAR2 (30) ,
	Province VARCHAR2 (6) ,
	City VARCHAR2 (6) ,
	District VARCHAR2 (6) ,
	Address VARCHAR2 (255) ,
	ZipCode VARCHAR2 (10) ,
	Tel VARCHAR2 (20) ,
	Mobile VARCHAR2 (20) ,
	Email VARCHAR2 (100) ,
	IsDefault VARCHAR2 (2) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZDMemberAddr primary key (ID)
);

drop table BZDMemberAddr cascade constraints;

/*==============================================================*/
/* Table: BZDMemberAddr */
/*==============================================================*/
create table BZDMemberAddr(
	ID INTEGER not null,
	UserName VARCHAR2 (200) not null,
	RealName VARCHAR2 (100) ,
	Country VARCHAR2 (30) ,
	Province VARCHAR2 (6) ,
	City VARCHAR2 (6) ,
	District VARCHAR2 (6) ,
	Address VARCHAR2 (255) ,
	ZipCode VARCHAR2 (10) ,
	Tel VARCHAR2 (20) ,
	Mobile VARCHAR2 (20) ,
	Email VARCHAR2 (100) ,
	IsDefault VARCHAR2 (2) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZDMemberAddr primary key (ID,BackupNo)
);

/*===========================商品添加折扣和优惠价格字段 by 李伟仪===================================*/
create table ZSGoods_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,BrandID,BranchInnerCode,Type,SN,Name,Alias,BarCode,WorkFlowID,Status,Factory,OrderFlag,MarketPrice,Price,'0' as Discount,'0' as OfferPrice,MemberPrice,VIPPrice,EffectDate,Store,Standard,Unit,Score,CommentCount,SaleCount,HitCount,Image0,Image1,Image2,Image3,RelativeGoods,Keyword,TopDate,TopFlag,Content,Summary,RedirectURL,Attribute,RecommendGoods,StickTime,PublishDate,DownlineDate,Memo,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from ZSGoods;
update ZSGoods_TMP set Discount=null;
alter table ZSGoods_TMP modify Discount NUMBER(12,2);
update ZSGoods_TMP set OfferPrice=null;
alter table ZSGoods_TMP modify OfferPrice NUMBER(12,2);
drop table ZSGoods;
rename ZSGoods_TMP to ZSGoods;
alter table ZSGoods add primary key (ID);
create table BZSGoods_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,BrandID,BranchInnerCode,Type,SN,Name,Alias,BarCode,WorkFlowID,Status,Factory,OrderFlag,MarketPrice,Price,'0' as Discount,'0' as OfferPrice,MemberPrice,VIPPrice,EffectDate,Store,Standard,Unit,Score,CommentCount,SaleCount,HitCount,Image0,Image1,Image2,Image3,RelativeGoods,Keyword,TopDate,TopFlag,Content,Summary,RedirectURL,Attribute,RecommendGoods,StickTime,PublishDate,DownlineDate,Memo,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from BZSGoods;
update BZSGoods_TMP set Discount=null;
alter table BZSGoods_TMP modify Discount NUMBER(12,2);
update BZSGoods_TMP set OfferPrice=null;
alter table BZSGoods_TMP modify OfferPrice NUMBER(12,2);
drop table BZSGoods;
rename BZSGoods_TMP to BZSGoods;
alter table BZSGoods add primary key (ID,BackupNo);

/*===========================用户收藏夹表添加站点ID by 李伟仪===================================*/
create table ZSFavorite_TMP as select UserName,GoodsID,'0' as SiteID,AddUser,AddTime,ModifyUser,ModifyTime from ZSFavorite;
update ZSFavorite_TMP set SiteID=null;
alter table ZSFavorite_TMP modify SiteID INTEGER;
drop table ZSFavorite;
rename ZSFavorite_TMP to ZSFavorite;
alter table ZSFavorite add primary key (UserName,GoodsID);

/*===========================删除zdconfig表无用的配置信息 by huanglei 20100319===================================*/
delete from zdconfig where type='Privilege.OwnerType.Role';
delete from zdconfig where type='Privilege.OwnerType.User';

/*===========================添加商品属性表，记录不同支付方式的特殊属性 by 李伟仪， 2010-03-24===================================*/
drop table ZSPaymentProp cascade constraints;

/*==============================================================*/
/* Table: ZSPaymentProp */
/*==============================================================*/
create table ZSPaymentProp(
	ID INTEGER not null,
	PaymentID INTEGER not null,
	PropName VARCHAR2 (200) ,
	PropValue VARCHAR2 (200) ,
	Memo VARCHAR2 (1000) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZSPaymentProp primary key (ID)
);

/*===========================把原来的MemberCode都改成UserName by 黄雷， 2010-03-25===================================*/
drop table ZSOrder cascade constraints;

/*==============================================================*/
/* Table: ZSOrder */
/*==============================================================*/
create table ZSOrder(
	ID INTEGER not null,
	SiteID INTEGER not null,
	UserName VARCHAR2 (200) ,
	IsValid VARCHAR2 (1) ,
	Status VARCHAR2 (40) ,
	Amount NUMBER (12,2) not null,
	SendFee NUMBER (12,2) ,
	OrderAmount NUMBER (12,2) ,
	Score INTEGER not null,
	Name VARCHAR2 (30) ,
	Province VARCHAR2 (6) ,
	City VARCHAR2 (6) ,
	District VARCHAR2 (6) ,
	Address VARCHAR2 (255) ,
	ZipCode VARCHAR2 (10) ,
	Tel VARCHAR2 (20) ,
	Mobile VARCHAR2 (20) ,
	HasInvoice VARCHAR2 (1) not null,
	InvoiceTitle VARCHAR2 (100) ,
	SendBeginDate DATE ,
	SendEndDate DATE ,
	SendTimeSlice VARCHAR2 (40) ,
	SendInfo VARCHAR2 (200) ,
	SendType VARCHAR2 (40) ,
	PaymentType VARCHAR2 (40) ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZSOrder primary key (ID)
);

drop table ZSOrderItem cascade constraints;

/*==============================================================*/
/* Table: ZSOrderItem */
/*==============================================================*/
create table ZSOrderItem(
	OrderID INTEGER not null,
	GoodsID INTEGER not null,
	SiteID INTEGER not null,
	UserName VARCHAR2 (200) ,
	SN VARCHAR2 (50) ,
	Name VARCHAR2 (200) ,
	Price NUMBER (12,2) ,
	Discount NUMBER (12,2) ,
	DiscountPrice NUMBER (12,2) ,
	Count INTEGER ,
	Amount NUMBER ,
	Score INTEGER ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZSOrderItem primary key (OrderID,GoodsID)
);

drop table BZSOrder cascade constraints;

/*==============================================================*/
/* Table: BZSOrder */
/*==============================================================*/
create table BZSOrder(
	ID INTEGER not null,
	SiteID INTEGER not null,
	UserName VARCHAR2 (200) ,
	IsValid VARCHAR2 (1) ,
	Status VARCHAR2 (40) ,
	Amount NUMBER (12,2) not null,
	SendFee NUMBER (12,2) ,
	OrderAmount NUMBER (12,2) ,
	Score INTEGER not null,
	Name VARCHAR2 (30) ,
	Province VARCHAR2 (6) ,
	City VARCHAR2 (6) ,
	District VARCHAR2 (6) ,
	Address VARCHAR2 (255) ,
	ZipCode VARCHAR2 (10) ,
	Tel VARCHAR2 (20) ,
	Mobile VARCHAR2 (20) ,
	HasInvoice VARCHAR2 (1) not null,
	InvoiceTitle VARCHAR2 (100) ,
	SendBeginDate DATE ,
	SendEndDate DATE ,
	SendTimeSlice VARCHAR2 (40) ,
	SendInfo VARCHAR2 (200) ,
	SendType VARCHAR2 (40) ,
	PaymentType VARCHAR2 (40) ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSOrder primary key (ID,BackupNo)
);

drop table BZSOrderItem cascade constraints;

/*==============================================================*/
/* Table: BZSOrderItem */
/*==============================================================*/
create table BZSOrderItem(
	OrderID INTEGER not null,
	GoodsID INTEGER not null,
	SiteID INTEGER not null,
	UserName VARCHAR2 (200) ,
	SN VARCHAR2 (50) ,
	Name VARCHAR2 (200) ,
	Price NUMBER (12,2) ,
	Discount NUMBER (12,2) ,
	DiscountPrice NUMBER (12,2) ,
	Count INTEGER ,
	Amount NUMBER ,
	Score INTEGER ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSOrderItem primary key (OrderID,GoodsID,BackupNo)
);

/*===========================支付方式表添加ImagePath字段 by 李伟仪， 2010-03-25===================================*/
create table ZSPayment_TMP as select ID,SiteID,Name,Info,IsVisible,'0' as ImagePath,Memo,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from ZSPayment;
update ZSPayment_TMP set ImagePath=null;
alter table ZSPayment_TMP modify ImagePath VARCHAR2(200);
drop table ZSPayment;
rename ZSPayment_TMP to ZSPayment;
alter table ZSPayment add primary key (ID);

/*================工作流步骤表的权限相关字段长段加大================*/
alter table zwstep modify AllowUser VARCHAR2(4000);
alter table zwstep modify AllowOrgan VARCHAR2(4000);
alter table zwstep modify AllowRole VARCHAR2(4000);
alter table bzwstep modify AllowUser VARCHAR2(4000);
alter table bzwstep modify AllowOrgan VARCHAR2(4000);
alter table bzwstep modify AllowRole VARCHAR2(4000);

/** 2010-04-02 增加广告、留言板、投票跟栏目关联的功能字段 **/
create table zcadposition_TMP as select ID,SiteID,PositionName,Code,Description,PositionType,PaddingTop,PaddingLeft,PositionWidth,PositionHeight,Align,Scroll,JsName,'0' as RelaCatalogID,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcadposition;
update zcadposition_TMP set RelaCatalogID=null;
alter table zcadposition_TMP modify RelaCatalogID INTEGER;
drop table zcadposition;
rename zcadposition_TMP to zcadposition;
alter table zcadposition add primary key (ID);
create table bzcadposition_TMP as select ID,SiteID,PositionName,Code,Description,PositionType,PaddingTop,PaddingLeft,PositionWidth,PositionHeight,Align,Scroll,JsName,'0' as RelaCatalogID,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcadposition;
update bzcadposition_TMP set RelaCatalogID=null;
alter table bzcadposition_TMP modify RelaCatalogID INTEGER;
drop table bzcadposition;
rename bzcadposition_TMP to bzcadposition;
alter table bzcadposition add primary key (ID,BackupNo);
create table ZCMessageBoard_TMP as select ID,SiteID,Name,IsOpen,Description,MessageCount,'0' as RelaCatalogID,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from ZCMessageBoard;
update ZCMessageBoard_TMP set RelaCatalogID=null;
alter table ZCMessageBoard_TMP modify RelaCatalogID INTEGER;
drop table ZCMessageBoard;
rename ZCMessageBoard_TMP to ZCMessageBoard;
alter table ZCMessageBoard add primary key (ID);
create table BZCMessageBoard_TMP as select ID,SiteID,Name,IsOpen,Description,MessageCount,'0' as RelaCatalogID,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCMessageBoard;
update BZCMessageBoard_TMP set RelaCatalogID=null;
alter table BZCMessageBoard_TMP modify RelaCatalogID INTEGER;
drop table BZCMessageBoard;
rename BZCMessageBoard_TMP to BZCMessageBoard;
alter table BZCMessageBoard add primary key (ID,BackupNo);
create table ZCVote_TMP as select ID,Code,SiteID,Title,Total,StartTime,EndTime,IPLimit,VerifyFlag,Width,'0' as RelaCatalogID,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from ZCVote;
update ZCVote_TMP set RelaCatalogID=null;
alter table ZCVote_TMP modify RelaCatalogID INTEGER;
drop table ZCVote;
rename ZCVote_TMP to ZCVote;
alter table ZCVote add primary key (ID);
create table BZCVote_TMP as select ID,Code,SiteID,Title,Total,StartTime,EndTime,IPLimit,VerifyFlag,Width,'0' as RelaCatalogID,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCVote;
update BZCVote_TMP set RelaCatalogID=null;
alter table BZCVote_TMP modify RelaCatalogID INTEGER;
drop table BZCVote;
rename BZCVote_TMP to BZCVote;
alter table BZCVote add primary key (ID,BackupNo);

/*2010-04-02 为媒体文件增加发布状态字段 wyuch*/
alter table zcvideo drop column Status;
alter table bzcvideo drop column Status;

create table zcvideo_TMP as select ID,Name,OldName,SiteID,Tag,CatalogID,CatalogInnerCode,BranchInnerCode,Path,FileName,SrcFileName,Suffix,IsOriginal,Info,System,FileSize,PublishDate,ImageName,Count,Width,Height,Duration,ProduceTime,Author,Integral,OrderFlag,HitCount,StickTime,SourceURL,'0' as Status,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcvideo;
update zcvideo_TMP set Status=null;
alter table zcvideo_TMP modify Status INTEGER;
drop table zcvideo;
rename zcvideo_TMP to zcvideo;
alter table zcvideo add primary key (ID);
create index idx20_video on zcvideo (CatalogID);
create index idx21_video on zcvideo (CatalogInnercode);
create index idx22_video on zcvideo (SiteID);
create index idx23_video on zcvideo (OrderFlag);
create index idx24_video on zcvideo (publishDate);
create index idx25_video on zcvideo (addtime);
create index idx26_video on zcvideo (modifytime);
create table zcaudio_TMP as select ID,Name,OldName,SiteID,Tag,CatalogID,CatalogInnerCode,BranchInnerCode,Path,FileName,SrcFileName,Suffix,IsOriginal,Info,System,FileSize,PublishDate,Duration,ProduceTime,Author,Integral,SourceURL,'0' as Status,OrderFlag,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcaudio;
update zcaudio_TMP set Status=null;
alter table zcaudio_TMP modify Status INTEGER;
drop table zcaudio;
rename zcaudio_TMP to zcaudio;
alter table zcaudio add primary key (ID);
create index idx27_audio on zcaudio (CatalogID);
create index idx28_audio on zcaudio (CatalogInnercode);
create index idx29_audio on zcaudio (SiteID);
create index idx30_audio on zcaudio (OrderFlag);
create index idx31_audio on zcaudio (publishDate);
create index idx32_audio on zcaudio (addtime);
create index idx33_audio on zcaudio (modifytime);
create table zcimage_TMP as select ID,Name,OldName,Tag,SiteID,CatalogID,CatalogInnerCode,BranchInnerCode,Path,FileName,SrcFileName,Suffix,Width,Height,Count,Info,IsOriginal,FileSize,System,LinkURL,LinkText,ProduceTime,Author,PublishDate,Integral,OrderFlag,HitCount,StickTime,SourceURL,'0' as Status,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcimage;
update zcimage_TMP set Status=null;
alter table zcimage_TMP modify Status INTEGER;
drop table zcimage;
rename zcimage_TMP to zcimage;
alter table zcimage add primary key (ID);
create index idx13_image on zcimage (CatalogID);
create index idx14_image on zcimage (CatalogInnercode);
create index idx15_image on zcimage (SiteID);
create index idx16_image on zcimage (OrderFlag);
create index idx17_image on zcimage (publishDate);
create index idx18_image on zcimage (addtime);
create index idx19_image on zcimage (modifytime);
create table zcattachment_TMP as select ID,Name,OldName,SiteID,CatalogID,CatalogInnerCode,BranchInnerCode,Path,FileName,SrcFileName,Suffix,Info,FileSize,System,PublishDate,Integral,IsLocked,Password,SourceURL,'0' as Status,OrderFlag,ImagePath,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcattachment;
update zcattachment_TMP set Status=null;
alter table zcattachment_TMP modify Status INTEGER;
drop table zcattachment;
rename zcattachment_TMP to zcattachment;
alter table zcattachment add primary key (ID);
create index idx34_attachmen on zcattachment (CatalogID);
create index idx35_attachmen on zcattachment (CatalogInnercode);
create index idx36_attachmen on zcattachment (SiteID);
create index idx37_attachmen on zcattachment (OrderFlag);
create index idx38_attachmen on zcattachment (publishDate);
create index idx39_attachmen on zcattachment (addtime);
create index idx40_attachmen on zcattachment (modifytime);
create table bzcvideo_TMP as select ID,Name,OldName,SiteID,Tag,CatalogID,CatalogInnerCode,BranchInnerCode,Path,FileName,SrcFileName,Suffix,IsOriginal,Info,System,FileSize,PublishDate,ImageName,Count,Width,Height,Duration,ProduceTime,Author,Integral,OrderFlag,HitCount,StickTime,SourceURL,'0' as Status,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcvideo;
update bzcvideo_TMP set Status=null;
alter table bzcvideo_TMP modify Status INTEGER;
drop table bzcvideo;
rename bzcvideo_TMP to bzcvideo;
alter table bzcvideo add primary key (ID,BackupNo);
create table bzcaudio_TMP as select ID,Name,OldName,SiteID,Tag,CatalogID,CatalogInnerCode,BranchInnerCode,Path,FileName,SrcFileName,Suffix,IsOriginal,Info,System,FileSize,PublishDate,Duration,ProduceTime,Author,Integral,SourceURL,'0' as Status,OrderFlag,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcaudio;
update bzcaudio_TMP set Status=null;
alter table bzcaudio_TMP modify Status INTEGER;
drop table bzcaudio;
rename bzcaudio_TMP to bzcaudio;
alter table bzcaudio add primary key (ID,BackupNo);
create table bzcimage_TMP as select ID,Name,OldName,Tag,SiteID,CatalogID,CatalogInnerCode,BranchInnerCode,Path,FileName,SrcFileName,Suffix,Width,Height,Count,Info,IsOriginal,FileSize,System,LinkURL,LinkText,ProduceTime,Author,PublishDate,Integral,OrderFlag,HitCount,StickTime,SourceURL,'0' as Status,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcimage;
update bzcimage_TMP set Status=null;
alter table bzcimage_TMP modify Status INTEGER;
drop table bzcimage;
rename bzcimage_TMP to bzcimage;
alter table bzcimage add primary key (ID,BackupNo);
create table bzcattachment_TMP as select ID,Name,OldName,SiteID,CatalogID,CatalogInnerCode,BranchInnerCode,Path,FileName,SrcFileName,Suffix,Info,FileSize,System,PublishDate,Integral,IsLocked,Password,SourceURL,'0' as Status,OrderFlag,ImagePath,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcattachment;
update bzcattachment_TMP set Status=null;
alter table bzcattachment_TMP modify Status INTEGER;
drop table bzcattachment;
rename bzcattachment_TMP to bzcattachment;
alter table bzcattachment add primary key (ID,BackupNo);

/*2010-04-06 添加商品收藏夹备份表，商品收藏夹表添加降价提醒标识字段 by 李伟仪*/
drop table BZSFavorite cascade constraints;
/*==============================================================*/
/* Table: BZSFavorite */
/*==============================================================*/
create table BZSFavorite(
	UserName VARCHAR2 (200) not null,
	GoodsID INTEGER not null,
	SiteID INTEGER ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_BZSFavorite primary key (UserName,GoodsID)
);
create table ZSFavorite_TMP as select UserName,GoodsID,SiteID,'0' as PriceNoteFlag,AddUser,AddTime,ModifyUser,ModifyTime from ZSFavorite;
update ZSFavorite_TMP set PriceNoteFlag=null;
alter table ZSFavorite_TMP modify PriceNoteFlag VARCHAR2(2);
drop table ZSFavorite;
rename ZSFavorite_TMP to ZSFavorite;
alter table ZSFavorite add primary key (UserName,GoodsID);

/*bzwstep未加Owner字段 by wyuch 2010-04-06*/
create table bzwstep_TMP as select ID,WorkflowID,InstanceID,DataVersionID,NodeID,ActionID,PreviousStepID,'0' as Owner,StartTime,FinishTime,State,Operators,AllowUser,AllowOrgan,AllowRole,Memo,AddTime,AddUser,BackupNo,BackupOperator,BackupTime,BackupMemo from bzwstep;
update bzwstep_TMP set Owner=null;
alter table bzwstep_TMP modify Owner VARCHAR2(50);
drop table bzwstep;
rename bzwstep_TMP to bzwstep;
alter table bzwstep add primary key (ID,BackupNo);

/*huanglei 增加文章投票功能*/
create table ZCVote_TMP as select ID,Code,SiteID,Title,Total,StartTime,EndTime,IPLimit,VerifyFlag,Width,RelaCatalogID,'0' as VoteCatalogID,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from ZCVote;
update ZCVote_TMP set VoteCatalogID=null;
alter table ZCVote_TMP modify VoteCatalogID INTEGER;
drop table ZCVote;
rename ZCVote_TMP to ZCVote;
alter table ZCVote add primary key (ID);
create table BZCVote_TMP as select ID,Code,SiteID,Title,Total,StartTime,EndTime,IPLimit,VerifyFlag,Width,RelaCatalogID,'0' as VoteCatalogID,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCVote;
update BZCVote_TMP set VoteCatalogID=null;
alter table BZCVote_TMP modify VoteCatalogID INTEGER;
drop table BZCVote;
rename BZCVote_TMP to BZCVote;
alter table BZCVote add primary key (ID,BackupNo);
create table ZCVoteSubject_TMP as select ID,VoteID,Type,Subject,'0' as VoteCatalogID,Prop1,Prop2 from ZCVoteSubject;
update ZCVoteSubject_TMP set VoteCatalogID=null;
alter table ZCVoteSubject_TMP modify VoteCatalogID INTEGER;
drop table ZCVoteSubject;
rename ZCVoteSubject_TMP to ZCVoteSubject;
alter table ZCVoteSubject add primary key (ID);
create table BZCVoteSubject_TMP as select ID,VoteID,Type,Subject,'0' as VoteCatalogID,Prop1,Prop2,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCVoteSubject;
update BZCVoteSubject_TMP set VoteCatalogID=null;
alter table BZCVoteSubject_TMP modify VoteCatalogID INTEGER;
drop table BZCVoteSubject;
rename BZCVoteSubject_TMP to BZCVoteSubject;
alter table BZCVoteSubject add primary key (ID,BackupNo);
create table ZCVoteItem_TMP as select ID,SubjectID,VoteID,Item,Score,ItemType,'0' as VoteDocID from ZCVoteItem;
update ZCVoteItem_TMP set VoteDocID=null;
alter table ZCVoteItem_TMP modify VoteDocID INTEGER;
drop table ZCVoteItem;
rename ZCVoteItem_TMP to ZCVoteItem;
alter table ZCVoteItem add primary key (ID);
create table BZCVoteItem_TMP as select ID,SubjectID,VoteID,Item,Score,ItemType,'0' as VoteDocID,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCVoteItem;
update BZCVoteItem_TMP set VoteDocID=null;
alter table BZCVoteItem_TMP modify VoteDocID INTEGER;
drop table BZCVoteItem;
rename BZCVoteItem_TMP to BZCVoteItem;
alter table BZCVoteItem add primary key (ID,BackupNo);

create table ZCVoteSubject_TMP as select ID,VoteID,Type,Subject,VoteCatalogID,'0' as OrderFlag,Prop1,Prop2 from ZCVoteSubject;
update ZCVoteSubject_TMP set OrderFlag=null;
alter table ZCVoteSubject_TMP modify OrderFlag INTEGER;
drop table ZCVoteSubject;
rename ZCVoteSubject_TMP to ZCVoteSubject;
alter table ZCVoteSubject add primary key (ID);
create table BZCVoteSubject_TMP as select ID,VoteID,Type,Subject,VoteCatalogID,'0' as OrderFlag,Prop1,Prop2,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCVoteSubject;
update BZCVoteSubject_TMP set OrderFlag=null;
alter table BZCVoteSubject_TMP modify OrderFlag INTEGER;
drop table BZCVoteSubject;
rename BZCVoteSubject_TMP to BZCVoteSubject;
alter table BZCVoteSubject add primary key (ID,BackupNo);
create table ZCVoteItem_TMP as select ID,SubjectID,VoteID,Item,Score,ItemType,VoteDocID,'0' as OrderFlag from ZCVoteItem;
update ZCVoteItem_TMP set OrderFlag=null;
alter table ZCVoteItem_TMP modify OrderFlag INTEGER;
drop table ZCVoteItem;
rename ZCVoteItem_TMP to ZCVoteItem;
alter table ZCVoteItem add primary key (ID);
create table BZCVoteItem_TMP as select ID,SubjectID,VoteID,Item,Score,ItemType,VoteDocID,'0' as OrderFlag,BackupNo,BackupOperator,BackupTime,BackupMemo from BZCVoteItem;
update BZCVoteItem_TMP set OrderFlag=null;
alter table BZCVoteItem_TMP modify OrderFlag INTEGER;
drop table BZCVoteItem;
rename BZCVoteItem_TMP to BZCVoteItem;
alter table BZCVoteItem add primary key (ID,BackupNo);

/*调查，广告，图片播放器，留言板升级时都需要将RelaCatalogID置为0*/
update ZCAdposition set RelaCatalogID=0;
update ZCVote set RelaCatalogID=0;
update ZCMessageBoard set RelaCatalogID=0;
update ZCImagePlayer set RelaCatalogInnerCode='0';

/*增加商城缺失的B表 2010-04-23 by wyuch*/
drop table BZSShopConfig cascade constraints;
create table BZSShopConfig(
	SiteID INTEGER not null,
	Name VARCHAR2 (50) ,
	Info VARCHAR2 (1024) ,
	prop1 VARCHAR2 (50) ,
	prop2 VARCHAR2 (50) ,
	prop3 VARCHAR2 (50) ,
	prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (100) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (100) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSShopConfig primary key (SiteID,BackupNo)
);

drop table BZSStore cascade constraints;
create table BZSStore(
	StoreCode VARCHAR2 (100) not null,
	ParentCode VARCHAR2 (100) not null,
	Name VARCHAR2 (100) not null,
	Alias VARCHAR2 (100) ,
	TreeLevel INTEGER not null,
	SiteID INTEGER not null,
	OrderFlag INTEGER not null,
	URL VARCHAR2 (100) ,
	Info VARCHAR2 (2000) ,
	Country VARCHAR2 (30) ,
	Province VARCHAR2 (6) ,
	City VARCHAR2 (6) ,
	District VARCHAR2 (6) ,
	Address VARCHAR2 (400) ,
	ZipCode VARCHAR2 (10) ,
	Tel VARCHAR2 (20) ,
	Fax VARCHAR2 (20) ,
	Mobile VARCHAR2 (30) ,
	Contacter VARCHAR2 (40) ,
	ContacterEmail VARCHAR2 (100) ,
	ContacterTel VARCHAR2 (20) ,
	ContacterMobile VARCHAR2 (20) ,
	ContacterQQ VARCHAR2 (20) ,
	ContacterMSN VARCHAR2 (50) ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSStore primary key (StoreCode,BackupNo)
);

drop table BZSFavorite cascade constraints;
create table BZSFavorite(
	UserName VARCHAR2 (200) not null,
	GoodsID INTEGER not null,
	SiteID INTEGER ,
	PriceNoteFlag VARCHAR2 (2) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSFavorite primary key (UserName,GoodsID,BackupNo)
);

drop table BZSSend cascade constraints;
create table BZSSend(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (200) ,
	SendInfo VARCHAR2 (200) ,
	ArriveInfo VARCHAR2 (200) ,
	Info VARCHAR2 (200) ,
	Price NUMBER (12,2) ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSSend primary key (ID,BackupNo)
);

drop table ZSStore cascade constraints;
create table ZSStore(
	StoreCode VARCHAR2 (100) not null,
	ParentCode VARCHAR2 (100) not null,
	Name VARCHAR2 (100) not null,
	Alias VARCHAR2 (100) ,
	TreeLevel INTEGER not null,
	SiteID INTEGER not null,
	OrderFlag INTEGER not null,
	URL VARCHAR2 (100) ,
	Info VARCHAR2 (2000) ,
	Country VARCHAR2 (30) ,
	Province VARCHAR2 (6) ,
	City VARCHAR2 (6) ,
	District VARCHAR2 (6) ,
	Address VARCHAR2 (400) ,
	ZipCode VARCHAR2 (10) ,
	Tel VARCHAR2 (20) ,
	Fax VARCHAR2 (20) ,
	Mobile VARCHAR2 (30) ,
	Contacter VARCHAR2 (40) ,
	ContacterEmail VARCHAR2 (100) ,
	ContacterTel VARCHAR2 (20) ,
	ContacterMobile VARCHAR2 (20) ,
	ContacterQQ VARCHAR2 (20) ,
	ContacterMSN VARCHAR2 (50) ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZSStore primary key (StoreCode)
);

drop table BZSPayment cascade constraints;
create table BZSPayment(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (200) ,
	Info VARCHAR2 (1000) ,
	IsVisible VARCHAR2 (1) ,
	ImagePath VARCHAR2 (200) ,
	Memo VARCHAR2 (200) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSPayment primary key (ID,BackupNo)
);

drop table BZSPaymentProp cascade constraints;
create table BZSPaymentProp(
	ID INTEGER not null,
	PaymentID INTEGER not null,
	PropName VARCHAR2 (200) ,
	PropValue VARCHAR2 (200) ,
	Memo VARCHAR2 (1000) ,
	Prop1 VARCHAR2 (200) ,
	Prop2 VARCHAR2 (200) ,
	Prop3 VARCHAR2 (200) ,
	Prop4 VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZSPaymentProp primary key (ID,BackupNo)
);

/*文章批注字段太短*/
alter table zcarticlelog modify ActionDetail VARCHAR2(2000);
alter table bzcarticlelog modify ActionDetail VARCHAR2(2000);

/*填补表里的机构编码，统计需要用到 2010-04-26 by huanglei*/
update zccatalog set branchinnercode = (select branchinnercode from zduser where UserName=zccatalog.adduser);
update zcarticle set branchinnercode = (select branchinnercode from zduser where UserName=zcarticle.adduser);
update zcimage set branchinnercode = (select branchinnercode from zduser where UserName=zcimage.adduser);
update zcvideo set branchinnercode = (select branchinnercode from zduser where UserName=zcvideo.adduser);
update zcaudio set branchinnercode = (select branchinnercode from zduser where UserName=zcaudio.adduser);

/*增加系统内采集/分发相关的表*/
drop table ZCInnerGather cascade constraints;
create table ZCInnerGather(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (200) not null,
	CatalogInnerCode VARCHAR2 (255) not null,
	TargetCatalog VARCHAR2 (4000) not null,
	SyncCatalogInsert VARCHAR2 (2) ,
	SyncCatalogModify VARCHAR2 (2) ,
	SyncArticleModify VARCHAR2 (2) ,
	AfterInsertStatus INTEGER ,
	AfterModifyStatus INTEGER ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZCInnerGather primary key (ID)
);

drop table ZCInnerDeploy cascade constraints;
create table ZCInnerDeploy(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (200) not null,
	DeployType VARCHAR2 (2) not null,
	CatalogInnerCode VARCHAR2 (200) not null,
	TargetCatalog VARCHAR2 (4000) not null,
	SyncCatalogInsert VARCHAR2 (2) ,
	SyncCatalogModify VARCHAR2 (2) ,
	SyncArticleModify VARCHAR2 (2) ,
	AfterSyncStatus INTEGER ,
	AfterModifyStatus INTEGER ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZCInnerDeploy primary key (ID)
);

drop table BZCInnerGather cascade constraints;
create table BZCInnerGather(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (200) not null,
	CatalogInnerCode VARCHAR2 (255) not null,
	TargetCatalog VARCHAR2 (4000) not null,
	SyncCatalogInsert VARCHAR2 (2) ,
	SyncCatalogModify VARCHAR2 (2) ,
	SyncArticleModify VARCHAR2 (2) ,
	AfterInsertStatus INTEGER ,
	AfterModifyStatus INTEGER ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZCInnerGather primary key (ID,BackupNo)
);

drop table BZCInnerDeploy cascade constraints;
create table BZCInnerDeploy(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (200) not null,
	DeployType VARCHAR2 (2) not null,
	CatalogInnerCode VARCHAR2 (200) not null,
	TargetCatalog VARCHAR2 (4000) not null,
	SyncCatalogInsert VARCHAR2 (2) ,
	SyncCatalogModify VARCHAR2 (2) ,
	SyncArticleModify VARCHAR2 (2) ,
	AfterSyncStatus INTEGER ,
	AfterModifyStatus INTEGER ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZCInnerDeploy primary key (ID,BackupNo)
);

/*增加系统内采集/分发相关的栏目配置字段*/
create table zccatalogconfig_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,CronExpression,PlanType,StartTime,IsUsing,HotWordType,AllowStatus,AfterEditStatus,Encoding,ArchiveTime,AttachDownFlag,'0' as AllowInnerDeploy,'0' as InnerDeployPassword,'0' as SyncCatalogInsert,'0' as SyncCatalogModify,'0' as SyncArticleModify,'0' as AfterSyncStatus,'0' as AfterModifyStatus,'0' as AllowInnerGather,'0' as InnerGatherPassword,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zccatalogconfig;
update zccatalogconfig_TMP set AllowInnerDeploy=null;
alter table zccatalogconfig_TMP modify AllowInnerDeploy VARCHAR2(2);
update zccatalogconfig_TMP set InnerDeployPassword=null;
alter table zccatalogconfig_TMP modify InnerDeployPassword VARCHAR2(255);
update zccatalogconfig_TMP set SyncCatalogInsert=null;
alter table zccatalogconfig_TMP modify SyncCatalogInsert VARCHAR2(2);
update zccatalogconfig_TMP set SyncCatalogModify=null;
alter table zccatalogconfig_TMP modify SyncCatalogModify VARCHAR2(2);
update zccatalogconfig_TMP set SyncArticleModify=null;
alter table zccatalogconfig_TMP modify SyncArticleModify VARCHAR2(2);
update zccatalogconfig_TMP set AfterSyncStatus=null;
alter table zccatalogconfig_TMP modify AfterSyncStatus INTEGER;
update zccatalogconfig_TMP set AfterModifyStatus=null;
alter table zccatalogconfig_TMP modify AfterModifyStatus INTEGER;
update zccatalogconfig_TMP set AllowInnerGather=null;
alter table zccatalogconfig_TMP modify AllowInnerGather VARCHAR2(2);
update zccatalogconfig_TMP set InnerGatherPassword=null;
alter table zccatalogconfig_TMP modify InnerGatherPassword VARCHAR2(255);
drop table zccatalogconfig;
rename zccatalogconfig_TMP to zccatalogconfig;
alter table zccatalogconfig add primary key (ID);

create table bzccatalogconfig_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,CronExpression,PlanType,StartTime,IsUsing,HotWordType,AllowStatus,AfterEditStatus,Encoding,ArchiveTime,AttachDownFlag,'0' as AllowInnerDeploy,'0' as InnerDeployPassword,'0' as SyncCatalogInsert,'0' as SyncCatalogModify,'0' as SyncArticleModify,'0' as AfterSyncStatus,'0' as AfterModifyStatus,'0' as AllowInnerGather,'0' as InnerGatherPassword,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzccatalogconfig;
update bzccatalogconfig_TMP set AllowInnerDeploy=null;
alter table bzccatalogconfig_TMP modify AllowInnerDeploy VARCHAR2(2);
update bzccatalogconfig_TMP set InnerDeployPassword=null;
alter table bzccatalogconfig_TMP modify InnerDeployPassword VARCHAR2(255);
update bzccatalogconfig_TMP set SyncCatalogInsert=null;
alter table bzccatalogconfig_TMP modify SyncCatalogInsert VARCHAR2(2);
update bzccatalogconfig_TMP set SyncCatalogModify=null;
alter table bzccatalogconfig_TMP modify SyncCatalogModify VARCHAR2(2);
update bzccatalogconfig_TMP set SyncArticleModify=null;
alter table bzccatalogconfig_TMP modify SyncArticleModify VARCHAR2(2);
update bzccatalogconfig_TMP set AfterSyncStatus=null;
alter table bzccatalogconfig_TMP modify AfterSyncStatus INTEGER;
update bzccatalogconfig_TMP set AfterModifyStatus=null;
alter table bzccatalogconfig_TMP modify AfterModifyStatus INTEGER;
update bzccatalogconfig_TMP set AllowInnerGather=null;
alter table bzccatalogconfig_TMP modify AllowInnerGather VARCHAR2(2);
update bzccatalogconfig_TMP set InnerGatherPassword=null;
alter table bzccatalogconfig_TMP modify InnerGatherPassword VARCHAR2(255);
drop table bzccatalogconfig;
rename bzccatalogconfig_TMP to bzccatalogconfig;
alter table bzccatalogconfig add primary key (ID,BackupNo);

/**为栏目配置项加上是否 允许机构独立管理 lanjun20100429*/
create table bzccatalogconfig_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,CronExpression,PlanType,StartTime,IsUsing,HotWordType,AllowStatus,AfterEditStatus,Encoding,ArchiveTime,AttachDownFlag,'0' as BranchManageFlag,AllowInnerDeploy,InnerDeployPassword,SyncCatalogInsert,SyncCatalogModify,SyncArticleModify,AfterSyncStatus,AfterModifyStatus,AllowInnerGather,InnerGatherPassword,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzccatalogconfig;
update bzccatalogconfig_TMP set BranchManageFlag=null;
alter table bzccatalogconfig_TMP modify BranchManageFlag VARCHAR2(2);
drop table bzccatalogconfig;
rename bzccatalogconfig_TMP to bzccatalogconfig;
alter table bzccatalogconfig add primary key (ID,BackupNo);
create table zccatalogconfig_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,CronExpression,PlanType,StartTime,IsUsing,HotWordType,AllowStatus,AfterEditStatus,Encoding,ArchiveTime,AttachDownFlag,'0' as BranchManageFlag,AllowInnerDeploy,InnerDeployPassword,SyncCatalogInsert,SyncCatalogModify,SyncArticleModify,AfterSyncStatus,AfterModifyStatus,AllowInnerGather,InnerGatherPassword,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zccatalogconfig;
update zccatalogconfig_TMP set BranchManageFlag=null;
alter table zccatalogconfig_TMP modify BranchManageFlag VARCHAR2(2);
drop table zccatalogconfig;
rename zccatalogconfig_TMP to zccatalogconfig;
alter table zccatalogconfig add primary key (ID);

/**添加站点是否启用商城、论坛选项 lanjun 20100429*/
create table zcsite_TMP as select ID,Name,Alias,Info,BranchInnerCode,URL,RootPath,IndexTemplate,ListTemplate,DetailTemplate,EditorCss,Workflow,OrderFlag,LogoFileName,MessageBoardFlag,CommentAuditFlag,ChannelCount,MagzineCount,SpecialCount,ImageLibCount,VideoLibCount,AudioLibCount,AttachmentLibCount,ArticleCount,HitCount,ConfigXML,AutoIndexFlag,AutoStatFlag,HeaderTemplate,TopTemplate,BottomTemplate,AllowContribute,'0' as BBSEnableFlag,'0' as ShopEnableFlag,Prop1,Prop2,Prop3,Prop4,Prop5,Prop6,AddUser,AddTime,ModifyUser,ModifyTime from zcsite;
update zcsite_TMP set BBSEnableFlag=null;
alter table zcsite_TMP modify BBSEnableFlag VARCHAR2(2);
update zcsite_TMP set ShopEnableFlag=null;
alter table zcsite_TMP modify ShopEnableFlag VARCHAR2(2);
drop table zcsite;
rename zcsite_TMP to zcsite;
alter table zcsite add primary key (ID);
create table bzcsite_TMP as select ID,Name,Alias,Info,BranchInnerCode,URL,RootPath,IndexTemplate,ListTemplate,DetailTemplate,EditorCss,Workflow,OrderFlag,LogoFileName,MessageBoardFlag,CommentAuditFlag,ChannelCount,MagzineCount,SpecialCount,ImageLibCount,VideoLibCount,AudioLibCount,AttachmentLibCount,ArticleCount,HitCount,ConfigXML,AutoIndexFlag,AutoStatFlag,HeaderTemplate,TopTemplate,BottomTemplate,AllowContribute,'0' as BBSEnableFlag,'0' as ShopEnableFlag,Prop1,Prop2,Prop3,Prop4,Prop5,Prop6,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcsite;
update bzcsite_TMP set BBSEnableFlag=null;
alter table bzcsite_TMP modify BBSEnableFlag VARCHAR2(2);
update bzcsite_TMP set ShopEnableFlag=null;
alter table bzcsite_TMP modify ShopEnableFlag VARCHAR2(2);
drop table bzcsite;
rename bzcsite_TMP to bzcsite;
alter table bzcsite add primary key (ID,BackupNo);

/**添加Tag表格 修改ZCarticle表加入字段TagWord txc 20100512*/
drop table ZCTag cascade constraints;

/*==============================================================*/
/* Table: ZCTag */
/*==============================================================*/
create table ZCTag(
	ID INTEGER not null,
	Tag VARCHAR2 (100) not null,
	SiteID INTEGER not null,
	LinkURL VARCHAR2 (500) ,
	Type VARCHAR2 (20) ,
	RelaTag VARCHAR2 (4000) ,
	UsedCount INTEGER ,
	TagText VARCHAR2 (400) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZCTag primary key (ID)
);


drop table BZCTag cascade constraints;

/*==============================================================*/
/* Table: BZCTag */
/*==============================================================*/
create table BZCTag(
	ID INTEGER not null,
	Tag VARCHAR2 (100) not null,
	SiteID INTEGER not null,
	LinkURL VARCHAR2 (500) ,
	Type VARCHAR2 (20) ,
	RelaTag VARCHAR2 (4000) ,
	UsedCount INTEGER ,
	TagText VARCHAR2 (400) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZCTag primary key (ID,BackupNo)
);

create table zcarticle_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,BranchInnerCode,Title,SubTitle,ShortTitle,TitleStyle,ShortTitleStyle,Author,Type,Attribute,URL,RedirectURL,Status,Summary,Content,TopFlag,TopDate,TemplateFlag,Template,CommentFlag,CopyImageFlag,OrderFlag,ReferName,ReferURL,Keyword,'0' as Tag,RelativeArticle,RecommendArticle,ReferType,ReferSourceID,HitCount,StickTime,PublishFlag,Priority,LockUser,PublishDate,DownlineDate,WorkFlowID,IssueID,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcarticle;
update zcarticle_TMP set Tag=null;
alter table zcarticle_TMP modify Tag VARCHAR2(1000);
drop table zcarticle;
rename zcarticle_TMP to zcarticle;
alter table zcarticle add primary key (ID);
create index idx0_article on zcarticle (CatalogID,Status);
create index idx1_article on zcarticle (Orderflag);
create index idx2_article on zcarticle (publishDate);
create index idx3_article on zcarticle (addtime);
create index idx4_article on zcarticle (modifytime);
create index idx5_article on zcarticle (DownlineDate);
create index idx6_article on zcarticle (CatalogInnercode);
create index idx7_article on zcarticle (SiteID);
create index idx8_article on zcarticle (refersourceid);
create index idx9_article on zcarticle (keyword);
create table bzcarticle_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,BranchInnerCode,Title,SubTitle,ShortTitle,TitleStyle,ShortTitleStyle,Author,Type,Attribute,URL,RedirectURL,Status,Summary,Content,TopFlag,TopDate,TemplateFlag,Template,CommentFlag,CopyImageFlag,OrderFlag,ReferName,ReferURL,Keyword,'0' as Tag,RelativeArticle,RecommendArticle,ReferType,ReferSourceID,HitCount,StickTime,PublishFlag,Priority,LockUser,PublishDate,DownlineDate,WorkFlowID,IssueID,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcarticle;
update bzcarticle_TMP set Tag=null;
alter table bzcarticle_TMP modify Tag VARCHAR2(1000);
drop table bzcarticle;
rename bzcarticle_TMP to bzcarticle;
alter table bzcarticle add primary key (ID,BackupNo);
create index idx11_carticle on bzcarticle (BackupMemo);
create index idx12_carticle on bzcarticle (CatalogID);

update zctag set UsedCount=0;

/*为文章增加网站群来源ID，分页标题，引导图三个字段 2010-05-21*/
create table zcarticle_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,BranchInnerCode,Title,SubTitle,ShortTitle,TitleStyle,ShortTitleStyle,Author,Type,Attribute,URL,RedirectURL,Status,Summary,Content,TopFlag,TopDate,TemplateFlag,Template,CommentFlag,CopyImageFlag,OrderFlag,ReferName,ReferURL,Keyword,Tag,RelativeArticle,RecommendArticle,ReferType,ReferSourceID,HitCount,StickTime,PublishFlag,Priority,LockUser,PublishDate,DownlineDate,WorkFlowID,IssueID,'0' as Logo,'0' as PageTitle,'0' as ClusterSource,'0' as ClusterTarget,'0' as ReferTarget,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcarticle;
update zcarticle_TMP set Logo=null;
alter table zcarticle_TMP modify Logo VARCHAR2(100);
update zcarticle_TMP set PageTitle=null;
alter table zcarticle_TMP modify PageTitle VARCHAR2(200);
update zcarticle_TMP set ClusterSource=null;
alter table zcarticle_TMP modify ClusterSource VARCHAR2(200);
update zcarticle_TMP set ClusterTarget=null;
alter table zcarticle_TMP modify ClusterTarget VARCHAR2(1000);
update zcarticle_TMP set ReferTarget=null;
alter table zcarticle_TMP modify ReferTarget VARCHAR2(1000);
drop table zcarticle;
rename zcarticle_TMP to zcarticle;
alter table zcarticle add primary key (ID);
create index idx0_article on zcarticle (CatalogID,Status);
create index idx1_article on zcarticle (Orderflag);
create index idx2_article on zcarticle (publishDate);
create index idx3_article on zcarticle (addtime);
create index idx4_article on zcarticle (modifytime);
create index idx5_article on zcarticle (DownlineDate);
create index idx6_article on zcarticle (CatalogInnercode);
create index idx7_article on zcarticle (SiteID);
create index idx8_article on zcarticle (refersourceid);
create index idx9_article on zcarticle (keyword);

create table bzcarticle_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,BranchInnerCode,Title,SubTitle,ShortTitle,TitleStyle,ShortTitleStyle,Author,Type,Attribute,URL,RedirectURL,Status,Summary,Content,TopFlag,TopDate,TemplateFlag,Template,CommentFlag,CopyImageFlag,OrderFlag,ReferName,ReferURL,Keyword,Tag,RelativeArticle,RecommendArticle,ReferType,ReferSourceID,HitCount,StickTime,PublishFlag,Priority,LockUser,PublishDate,DownlineDate,WorkFlowID,IssueID,'0' as Logo,'0' as PageTitle,'0' as ClusterSource,'0' as ClusterTarget,'0' as ReferTarget,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcarticle;
update bzcarticle_TMP set Logo=null;
alter table bzcarticle_TMP modify Logo VARCHAR2(100);
update bzcarticle_TMP set PageTitle=null;
alter table bzcarticle_TMP modify PageTitle VARCHAR2(200);
update bzcarticle_TMP set ClusterSource=null;
alter table bzcarticle_TMP modify ClusterSource VARCHAR2(200);
update bzcarticle_TMP set ClusterTarget=null;
alter table bzcarticle_TMP modify ClusterTarget VARCHAR2(1000);
update bzcarticle_TMP set ReferTarget=null;
alter table bzcarticle_TMP modify ReferTarget VARCHAR2(1000);
drop table bzcarticle;
rename bzcarticle_TMP to bzcarticle;
alter table bzcarticle add primary key (ID,BackupNo);
create index idx11_carticle on bzcarticle (BackupMemo);
create index idx12_carticle on bzcarticle (CatalogID);

/*为站点增加Meta_keyword,Meta_Description两个字段 2010-05-21*/
create table zcsite_TMP as select ID,Name,Alias,Info,BranchInnerCode,URL,RootPath,IndexTemplate,ListTemplate,DetailTemplate,EditorCss,Workflow,OrderFlag,LogoFileName,MessageBoardFlag,CommentAuditFlag,ChannelCount,MagzineCount,SpecialCount,ImageLibCount,VideoLibCount,AudioLibCount,AttachmentLibCount,ArticleCount,HitCount,ConfigXML,AutoIndexFlag,AutoStatFlag,HeaderTemplate,TopTemplate,BottomTemplate,AllowContribute,BBSEnableFlag,ShopEnableFlag,'0' as Meta_Keywords,'0' as Meta_Description,Prop1,Prop2,Prop3,Prop4,Prop5,Prop6,AddUser,AddTime,ModifyUser,ModifyTime from zcsite;
update zcsite_TMP set Meta_Keywords=null;
alter table zcsite_TMP modify Meta_Keywords VARCHAR2(200);
update zcsite_TMP set Meta_Description=null;
alter table zcsite_TMP modify Meta_Description VARCHAR2(400);
drop table zcsite;
rename zcsite_TMP to zcsite;
alter table zcsite add primary key (ID);
create table bzcsite_TMP as select ID,Name,Alias,Info,BranchInnerCode,URL,RootPath,IndexTemplate,ListTemplate,DetailTemplate,EditorCss,Workflow,OrderFlag,LogoFileName,MessageBoardFlag,CommentAuditFlag,ChannelCount,MagzineCount,SpecialCount,ImageLibCount,VideoLibCount,AudioLibCount,AttachmentLibCount,ArticleCount,HitCount,ConfigXML,AutoIndexFlag,AutoStatFlag,HeaderTemplate,TopTemplate,BottomTemplate,AllowContribute,BBSEnableFlag,ShopEnableFlag,'0' as Meta_Keywords,'0' as Meta_Description,Prop1,Prop2,Prop3,Prop4,Prop5,Prop6,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcsite;
update bzcsite_TMP set Meta_Keywords=null;
alter table bzcsite_TMP modify Meta_Keywords VARCHAR2(200);
update bzcsite_TMP set Meta_Description=null;
alter table bzcsite_TMP modify Meta_Description VARCHAR2(400);
drop table bzcsite;
rename bzcsite_TMP to bzcsite;
alter table bzcsite add primary key (ID,BackupNo);


/*为栏目增加网站群来源ID 2010-05-21*/
create table zccatalog_TMP as select ID,ParentID,SiteID,Name,InnerCode,BranchInnerCode,Alias,URL,ImagePath,Type,IndexTemplate,ListTemplate,ListNameRule,DetailTemplate,DetailNameRule,RssTemplate,RssNameRule,Workflow,TreeLevel,ChildCount,IsLeaf,IsDirty,Total,OrderFlag,Logo,ListPageSize,ListPage,PublishFlag,SingleFlag,HitCount,Meta_Keywords,Meta_Description,OrderColumn,Integral,KeywordFlag,KeywordSetting,AllowContribute,'0' as ClusterSourceID,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zccatalog;
update zccatalog_TMP set ClusterSourceID=null;
alter table zccatalog_TMP modify ClusterSourceID VARCHAR2(200);
drop table zccatalog;
rename zccatalog_TMP to zccatalog;
alter table zccatalog add primary key (ID);
create index idx45_catalog on zccatalog (SiteID,Type);
create index idx46_catalog on zccatalog (InnerCode);
create table bzccatalog_TMP as select ID,ParentID,SiteID,Name,InnerCode,BranchInnerCode,Alias,URL,ImagePath,Type,IndexTemplate,ListTemplate,ListNameRule,DetailTemplate,DetailNameRule,RssTemplate,RssNameRule,Workflow,TreeLevel,ChildCount,IsLeaf,IsDirty,Total,OrderFlag,Logo,ListPageSize,ListPage,PublishFlag,SingleFlag,HitCount,Meta_Keywords,Meta_Description,OrderColumn,Integral,KeywordFlag,KeywordSetting,AllowContribute,'0' as ClusterSourceID,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzccatalog;
update bzccatalog_TMP set ClusterSourceID=null;
alter table bzccatalog_TMP modify ClusterSourceID VARCHAR2(200);
drop table bzccatalog;
rename bzccatalog_TMP to bzccatalog;
alter table bzccatalog add primary key (ID,BackupNo);

/*修改文章表中一些字段长度 2010-05-21*/
alter table bzcarticle modify RelativeArticle VARCHAR2(200);
alter table bzcarticle modify RecommendArticle VARCHAR2(200);
alter table bzcarticle modify LockUser VARCHAR2(50);
alter table bzcarticle modify AddUser VARCHAR2(50);
alter table bzcarticle modify Prop1 VARCHAR2(500);

alter table zcarticle modify RelativeArticle VARCHAR2(200);
alter table zcarticle modify RecommendArticle VARCHAR2(200);
alter table zcarticle modify LockUser VARCHAR2(50);
alter table zcarticle modify AddUser VARCHAR2(50);
alter table zcarticle modify Prop1 VARCHAR2(500);

/*增加系统内采集的功能 2010-05-26*/
drop table ZCDBGather cascade constraints;
create table ZCDBGather(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (200) not null,
	DatabaseID INTEGER not null,
	TableName VARCHAR2 (50) not null,
	CatalogID INTEGER not null,
	ArticleStatus INTEGER not null,
	PathReplacePartOld VARCHAR2 (200) ,
	PathReplacePartNew VARCHAR2 (200) ,
	NewRecordRule VARCHAR2 (200) ,
	SQLCondition VARCHAR2 (200) ,
	Status VARCHAR2 (2) ,
	MappingConfig CLOB not null,
	Memo VARCHAR2 (400) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZCDBGather primary key (ID)
);
drop table BZCDBGather cascade constraints;
create table BZCDBGather(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (200) not null,
	DatabaseID INTEGER not null,
	TableName VARCHAR2 (50) not null,
	CatalogID INTEGER not null,
	ArticleStatus INTEGER not null,
	PathReplacePartOld VARCHAR2 (200) ,
	PathReplacePartNew VARCHAR2 (200) ,
	NewRecordRule VARCHAR2 (200) ,
	SQLCondition VARCHAR2 (200) ,
	Status VARCHAR2 (2) ,
	MappingConfig CLOB not null,
	Memo VARCHAR2 (400) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZCDBGather primary key (ID,BackupNo)
);

/*给采集分发任务加上运行状态字段 2010-05-28*/
create table zcinnergather_TMP as select ID,SiteID,Name,CatalogInnerCode,TargetCatalog,SyncCatalogInsert,SyncCatalogModify,SyncArticleModify,AfterInsertStatus,AfterModifyStatus,'0' as Status,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcinnergather;
update zcinnergather_TMP set Status=null;
alter table zcinnergather_TMP modify Status VARCHAR2(2);
drop table zcinnergather;
rename zcinnergather_TMP to zcinnergather;
alter table zcinnergather add primary key (ID);
create table bzcinnergather_TMP as select ID,SiteID,Name,CatalogInnerCode,TargetCatalog,SyncCatalogInsert,SyncCatalogModify,SyncArticleModify,AfterInsertStatus,AfterModifyStatus,'0' as Status,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcinnergather;
update bzcinnergather_TMP set Status=null;
alter table bzcinnergather_TMP modify Status VARCHAR2(2);
drop table bzcinnergather;
rename bzcinnergather_TMP to bzcinnergather;
alter table bzcinnergather add primary key (ID,BackupNo);
create table zcinnerdeploy_TMP as select ID,SiteID,Name,DeployType,CatalogInnerCode,TargetCatalog,SyncCatalogInsert,SyncCatalogModify,SyncArticleModify,AfterSyncStatus,AfterModifyStatus,'0' as Status,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcinnerdeploy;
update zcinnerdeploy_TMP set Status=null;
alter table zcinnerdeploy_TMP modify Status VARCHAR2(2);
drop table zcinnerdeploy;
rename zcinnerdeploy_TMP to zcinnerdeploy;
alter table zcinnerdeploy add primary key (ID);
create table bzcinnerdeploy_TMP as select ID,SiteID,Name,DeployType,CatalogInnerCode,TargetCatalog,SyncCatalogInsert,SyncCatalogModify,SyncArticleModify,AfterSyncStatus,AfterModifyStatus,'0' as Status,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcinnerdeploy;
update bzcinnerdeploy_TMP set Status=null;
alter table bzcinnerdeploy_TMP modify Status VARCHAR2(2);
drop table bzcinnerdeploy;
rename bzcinnerdeploy_TMP to bzcinnerdeploy;
alter table bzcinnerdeploy add primary key (ID,BackupNo);
create table zcwebgather_TMP as select ID,SiteID,Name,Intro,Type,ConfigXML,ProxyFlag,ProxyHost,ProxyPort,ProxyUserName,ProxyPassword,'0' as Status,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcwebgather;
update zcwebgather_TMP set Status=null;
alter table zcwebgather_TMP modify Status VARCHAR2(2);
drop table zcwebgather;
rename zcwebgather_TMP to zcwebgather;
alter table zcwebgather add primary key (ID);
create table bzcwebgather_TMP as select ID,SiteID,Name,Intro,Type,ConfigXML,ProxyFlag,ProxyHost,ProxyPort,ProxyUserName,ProxyPassword,'0' as Status,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcwebgather;
update bzcwebgather_TMP set Status=null;
alter table bzcwebgather_TMP modify Status VARCHAR2(2);
drop table bzcwebgather;
rename bzcwebgather_TMP to bzcwebgather;
alter table bzcwebgather add primary key (ID,BackupNo);
update zcwebgather set Status='Y';
update zcinnergather set Status='Y';
update zcinnerdeploy set Status='Y';

/*配置是否可以匿名发表评论*/
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('CommentAllowAnonymity','是否可以匿名发表评论','N',NULL,NULL,NULL,NULL,NULL,'2010-01-06 14:35:52','admin',NULL,NULL);

/*加入缺失的ZSShopConfig 2010-05-31*/
drop table ZSShopConfig cascade constraints;
create table ZSShopConfig(
	SiteID INTEGER not null,
	Name VARCHAR2 (50) ,
	Info VARCHAR2 (1024) ,
	prop1 VARCHAR2 (50) ,
	prop2 VARCHAR2 (50) ,
	prop3 VARCHAR2 (50) ,
	prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (100) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (100) ,
	ModifyTime DATE ,
	constraint PK_ZSShopConfig primary key (SiteID)
);

/*加入永不归档的设置 2010-05-31*/
insert into zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,AddTime,AddUser) values ('ArchiveTime','ArchiveTime','0','永不归档','1260418093080','2010-05-31 11:11:11','admin');

/*文章表增加归档时间字段 2010-06-02*/
create table zcarticle_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,BranchInnerCode,Title,SubTitle,ShortTitle,TitleStyle,ShortTitleStyle,Author,Type,Attribute,URL,RedirectURL,Status,Summary,Content,TopFlag,TopDate,TemplateFlag,Template,CommentFlag,CopyImageFlag,OrderFlag,ReferName,ReferURL,Keyword,Tag,RelativeArticle,RecommendArticle,ReferType,ReferSourceID,HitCount,StickTime,PublishFlag,Priority,LockUser,PublishDate,DownlineDate,'0' as ArchiveDate,WorkFlowID,IssueID,Logo,PageTitle,ClusterSource,ClusterTarget,ReferTarget,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zcarticle;
update zcarticle_TMP set ArchiveDate=null;
alter table zcarticle_TMP modify ArchiveDate DATE;
drop table zcarticle;
rename zcarticle_TMP to zcarticle;
alter table zcarticle add primary key (ID);
create index idx0_article on zcarticle (CatalogID,Status);
create index idx1_article on zcarticle (Orderflag);
create index idx2_article on zcarticle (publishDate);
create index idx3_article on zcarticle (addtime);
create index idx4_article on zcarticle (modifytime);
create index idx5_article on zcarticle (DownlineDate);
create index idx6_article on zcarticle (CatalogInnercode);
create index idx7_article on zcarticle (SiteID);
create index idx8_article on zcarticle (refersourceid);
create index idx9_article on zcarticle (keyword);
create index idx10_article on zcarticle (ArchiveDate);
create table bzcarticle_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,BranchInnerCode,Title,SubTitle,ShortTitle,TitleStyle,ShortTitleStyle,Author,Type,Attribute,URL,RedirectURL,Status,Summary,Content,TopFlag,TopDate,TemplateFlag,Template,CommentFlag,CopyImageFlag,OrderFlag,ReferName,ReferURL,Keyword,Tag,RelativeArticle,RecommendArticle,ReferType,ReferSourceID,HitCount,StickTime,PublishFlag,Priority,LockUser,PublishDate,DownlineDate,'0' as ArchiveDate,WorkFlowID,IssueID,Logo,PageTitle,ClusterSource,ClusterTarget,ReferTarget,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzcarticle;
update bzcarticle_TMP set ArchiveDate=null;
alter table bzcarticle_TMP modify ArchiveDate DATE;
drop table bzcarticle;
rename bzcarticle_TMP to bzcarticle;
alter table bzcarticle add primary key (ID,BackupNo);
create index idx11_carticle on bzcarticle (BackupMemo);
create index idx12_carticle on bzcarticle (CatalogID);

/*增加复制方式代码中缺少的SFTP，并增加新加入的采集分发菜单项 2010-06-02*/
insert into zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,AddTime,AddUser) values ('DeployMethod','DeployMethod','SFTP','SFTP远程复制','1274171976500','2010-05-31 11:11:11','admin');
insert into zdmenu values(396, 125, '网站群采集', '2', 'DataChannel/FromCatalog.jsp', 'Y', 'Icons/icon003a10.gif', 46, '', '', '', '2010-03-22 10:36:00', 'admin', '2010-03-22 10:36:00', 'admin');
insert into zdmenu values(397, 120, '文档回收站', '2', 'Document/RecycleBin.jsp', 'Y', 'Icons/icon050a18.gif', 3, '', '', '', '2010-04-06 14:12:31', 'admin', '2010-03-22 10:36:00', 'admin');
insert into zdmenu values(403, 125, '网站群分发', '2', 'DataChannel/DeployCatalog.jsp', 'Y', 'Icons/icon003a7.gif', 47, '', '', '', '2010-05-04 21:08:22', 'admin', '2010-03-22 10:36:00', 'admin');
insert into zdmenu values(404, 122, 'Tag管理', '2', 'Site/Tag.jsp', 'Y', 'Icons/icon011a1.gif', 21, '', '', '', '2010-05-11 18:46:22', 'admin', '2010-03-22 10:36:00', 'admin');
insert into zdmenu values(405, 125, '数据库采集', '2', 'DataChannel/FromDB.jsp', 'Y', 'Icons/icon005a13.gif', 48, '', '', '', '2010-05-27 13:11:58', 'admin', '2010-03-22 10:36:00', 'admin');
update zdmaxno set MaxValue='406' where NoType='ZDMenuID';

/*ZCCatalogConfig里面增加评论相关配置字段*/
create table zccatalogconfig_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,CronExpression,PlanType,StartTime,IsUsing,HotWordType,AllowStatus,AfterEditStatus,Encoding,ArchiveTime,AttachDownFlag,BranchManageFlag,AllowInnerDeploy,InnerDeployPassword,SyncCatalogInsert,SyncCatalogModify,SyncArticleModify,AfterSyncStatus,AfterModifyStatus,AllowInnerGather,InnerGatherPassword,'0' as AllowComment,'0' as CommentAnonymous,'0' as CommentVerify,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from zccatalogconfig;
update zccatalogconfig_TMP set AllowComment=null;
alter table zccatalogconfig_TMP modify AllowComment VARCHAR2(2);
update zccatalogconfig_TMP set CommentAnonymous=null;
alter table zccatalogconfig_TMP modify CommentAnonymous VARCHAR2(2);
update zccatalogconfig_TMP set CommentVerify=null;
alter table zccatalogconfig_TMP modify CommentVerify VARCHAR2(2);
drop table zccatalogconfig;
rename zccatalogconfig_TMP to zccatalogconfig;
alter table zccatalogconfig add primary key (ID);

create table bzccatalogconfig_TMP as select ID,SiteID,CatalogID,CatalogInnerCode,CronExpression,PlanType,StartTime,IsUsing,HotWordType,AllowStatus,AfterEditStatus,Encoding,ArchiveTime,AttachDownFlag,BranchManageFlag,AllowInnerDeploy,InnerDeployPassword,SyncCatalogInsert,SyncCatalogModify,SyncArticleModify,AfterSyncStatus,AfterModifyStatus,AllowInnerGather,InnerGatherPassword,'0' as AllowComment,'0' as CommentAnonymous,'0' as CommentVerify,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime,BackupNo,BackupOperator,BackupTime,BackupMemo from bzccatalogconfig;
update bzccatalogconfig_TMP set AllowComment=null;
alter table bzccatalogconfig_TMP modify AllowComment VARCHAR2(2);
update bzccatalogconfig_TMP set CommentAnonymous=null;
alter table bzccatalogconfig_TMP modify CommentAnonymous VARCHAR2(2);
update bzccatalogconfig_TMP set CommentVerify=null;
alter table bzccatalogconfig_TMP modify CommentVerify VARCHAR2(2);
drop table bzccatalogconfig;
rename bzccatalogconfig_TMP to bzccatalogconfig;
alter table bzccatalogconfig add primary key (ID,BackupNo);

/*企业会员形象图片字段长度由50改为100 2010-06-09*/
alter table zdmembercompanyinfo modify Pic VARCHAR2(100);
alter table bzdmembercompanyinfo modify Pic VARCHAR2(100);