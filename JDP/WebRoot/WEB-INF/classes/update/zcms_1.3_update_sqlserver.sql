
/*修改ZCCatalog.ListPageSize字段的用途*/
update ZCCatalog set ListPageSize=20
go

INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('MessageActionURL','留言提交地址','/SaveMessage.jsp',NULL,NULL,NULL,NULL,NULL,'2010-01-06 14:35:52','admin',NULL,NULL)
go

if exists (select 1 from  sysobjects where id = object_id('ZCMessageBoard') and type='U') drop table ZCMessageBoard
go

/*==============================================================*/
/* Table: ZCMessageBoard */
/*==============================================================*/
create table ZCMessageBoard(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (100) not null,
	IsOpen varchar (2) not null,
	Description varchar (500) ,
	MessageCount varchar (20) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZCMessageBoard primary key nonclustered (ID)
)
go


if exists (select 1 from  sysobjects where id = object_id('ZCBoardMessage') and type='U') drop table ZCBoardMessage
go

/*==============================================================*/
/* Table: ZCBoardMessage */
/*==============================================================*/
create table ZCBoardMessage(
	ID bigint not null,
	BoardID bigint not null,
	Title varchar (100) not null,
	Content text not null,
	PublishFlag varchar (2) not null,
	ReplyFlag varchar (2) not null,
	ReplyContent varchar (1000) ,
	EMail varchar (100) ,
	QQ varchar (20) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop4 varchar (50) ,
	Prop3 varchar (50) ,
	IP varchar (20) not null,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZCBoardMessage primary key nonclustered (ID)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZCMessageBoard') and type='U') drop table BZCMessageBoard
go

/*==============================================================*/
/* Table: BZCMessageBoard */
/*==============================================================*/
create table BZCMessageBoard(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (100) not null,
	IsOpen varchar (2) not null,
	Description varchar (500) ,
	MessageCount varchar (20) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZCMessageBoard primary key nonclustered (ID,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZCBoardMessage') and type='U') drop table BZCBoardMessage
go

/*==============================================================*/
/* Table: BZCBoardMessage */
/*==============================================================*/
create table BZCBoardMessage(
	ID bigint not null,
	BoardID bigint not null,
	Title varchar (100) not null,
	Content text not null,
	PublishFlag varchar (2) not null,
	ReplyFlag varchar (2) not null,
	ReplyContent varchar (1000) ,
	EMail varchar (100) ,
	QQ varchar (20) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop4 varchar (50) ,
	Prop3 varchar (50) ,
	IP varchar (20) not null,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZCBoardMessage primary key nonclustered (ID,BackupNo)
)
go

/*增加工作流表*/
if exists (select 1 from  sysobjects where id = object_id('ZWWorkflow') and type='U') drop table ZWWorkflow
go
create table ZWWorkflow(
	ID bigint not null,
	Name varchar (100) not null,
	ConfigXML text ,
	Prop1 varchar (40) ,
	Prop2 varchar (40) ,
	Prop3 varchar (40) ,
	Prop4 varchar (40) ,
	Memo varchar (200) ,
	AddTime datetime not null,
	AddUser varchar (50) not null,
	ModifyTime datetime ,
	ModifyUser varchar (50) ,
	constraint PK_ZWWorkflow primary key nonclustered (ID)
)
go

if exists (select 1 from  sysobjects where id = object_id('ZWInstance') and type='U') drop table ZWInstance
go
create table ZWInstance(
	ID bigint not null,
	WorkflowID bigint not null,
	Name varchar (100) ,
	DataID varchar (30) ,
	State varchar (10) ,
	Memo varchar (100) ,
	AddTime datetime not null,
	AddUser varchar (50) not null,
	constraint PK_ZWInstance primary key nonclustered (ID)
)
go

if exists (select 1 from  sysobjects where id = object_id('ZWStep') and type='U') drop table ZWStep
go
create table ZWStep(
	ID bigint not null,
	WorkflowID bigint not null,
	InstanceID bigint not null,
	DataVersionID varchar (30) ,
	NodeID int ,
	PreviousStepID bigint ,
	Owner varchar (50) ,
	StartTime datetime ,
	FinishTime datetime ,
	State varchar (10) ,
	Operators varchar (400) ,
	AllowUser varchar (400) ,
	AllowOrgan varchar (200) ,
	AllowRole varchar (200) ,
	Memo varchar (100) ,
	AddTime datetime not null,
	AddUser varchar (50) not null,
	constraint PK_ZWStep primary key nonclustered (ID)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZWWorkflow') and type='U') drop table BZWWorkflow
go
create table BZWWorkflow(
	ID bigint not null,
	Name varchar (100) not null,
	ConfigXML text ,
	Prop1 varchar (40) ,
	Prop2 varchar (40) ,
	Prop3 varchar (40) ,
	Prop4 varchar (40) ,
	Memo varchar (200) ,
	AddTime datetime not null,
	AddUser varchar (50) not null,
	ModifyTime datetime ,
	ModifyUser varchar (50) ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZWWorkflow primary key nonclustered (ID,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZWInstance') and type='U') drop table BZWInstance
go
create table BZWInstance(
	ID bigint not null,
	WorkflowID bigint not null,
	Name varchar (100) ,
	DataID varchar (30) ,
	State varchar (10) ,
	Memo varchar (100) ,
	AddTime datetime not null,
	AddUser varchar (50) not null,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZWInstance primary key nonclustered (ID,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZWStep') and type='U') drop table BZWStep
go
create table BZWStep(
	ID bigint not null,
	WorkflowID bigint not null,
	InstanceID bigint not null,
	DataVersionID varchar (30) ,
	NodeID int ,
	PreviousStepID bigint ,
	StartTime datetime ,
	FinishTime datetime ,
	State varchar (10) ,
	Operators varchar (400) ,
	AllowUser varchar (400) ,
	AllowOrgan varchar (200) ,
	AllowRole varchar (200) ,
	Memo varchar (100) ,
	AddTime datetime not null,
	AddUser varchar (50) not null,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZWStep primary key nonclustered (ID,BackupNo)
)
go

/*新的图形化工作流*/
update ZDMenu set URL='Workflow/WorkflowList.jsp' where URL='Platform/Workflow.jsp'
go


/** 汪维军 2010-01-07 图片播放器增加关联栏目字段RelaCatalogID */
if exists (select 1 from  sysobjects where id = object_id('BZCImagePlayer_TMP') and type='U') drop table BZCImagePlayer_TMP
go
create table BZCImagePlayer_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	Code varchar (100) not null,
	SiteID bigint not null,
	DisplayType varchar (2) not null,
	ImageSource varchar (2) not null,
	RelaCatalogInnerCode varchar (100) ,
	Height bigint not null,
	Width bigint not null,
	DisplayCount bigint ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCImagePlayer) exec ('insert into BZCImagePlayer_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,DisplayCount,Width,Height,ImageSource,DisplayType,SiteID,Code,Name,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,DisplayCount,Width,Height,ImageSource,DisplayType,SiteID,Code,Name,ID from BZCImagePlayer')
go
drop table BZCImagePlayer
go
sp_rename 'BZCImagePlayer_TMP', 'BZCImagePlayer', 'OBJECT'
go
alter table BZCImagePlayer add constraint PK_BZCImagePlayer primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('ZCImagePlayer_TMP') and type='U') drop table ZCImagePlayer_TMP
go
create table ZCImagePlayer_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	Code varchar (100) not null,
	SiteID bigint not null,
	DisplayType varchar (2) not null,
	ImageSource varchar (2) not null,
	RelaCatalogInnerCode varchar (100) ,
	Height bigint not null,
	Width bigint not null,
	DisplayCount bigint ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from ZCImagePlayer) exec ('insert into ZCImagePlayer_TMP (ModifyTime,ModifyUser,AddTime,AddUser,DisplayCount,Width,Height,ImageSource,DisplayType,SiteID,Code,Name,ID) select ModifyTime,ModifyUser,AddTime,AddUser,DisplayCount,Width,Height,ImageSource,DisplayType,SiteID,Code,Name,ID from ZCImagePlayer')
go
drop table ZCImagePlayer
go
sp_rename 'ZCImagePlayer_TMP', 'ZCImagePlayer', 'OBJECT'
go
alter table ZCImagePlayer add constraint PK_ZCImagePlayer primary key  NONCLUSTERED(ID)
go
update ZCImagePlayer set ImageSource = '0'
go
if exists (select 1 from  sysobjects where id = object_id('BZCImagePlayer_TMP') and type='U') drop table BZCImagePlayer_TMP
go
create table BZCImagePlayer_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	Code varchar (100) not null,
	SiteID bigint not null,
	DisplayType varchar (2) not null,
	ImageSource varchar (2) not null,
	RelaCatalogInnerCode varchar (100) ,
	Height bigint not null,
	Width bigint not null,
	DisplayCount bigint ,
	IsShowText varchar (2) not null,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCImagePlayer) exec ('insert into BZCImagePlayer_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,DisplayCount,IsShowText,Width,Height,RelaCatalogInnerCode,ImageSource,DisplayType,SiteID,Code,Name,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,DisplayCount,''0'' as IsShowText,Width,Height,RelaCatalogInnerCode,ImageSource,DisplayType,SiteID,Code,Name,ID from BZCImagePlayer')
go
drop table BZCImagePlayer
go
sp_rename 'BZCImagePlayer_TMP', 'BZCImagePlayer', 'OBJECT'
go
alter table BZCImagePlayer add constraint PK_BZCImagePlayer primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('ZCImagePlayer_TMP') and type='U') drop table ZCImagePlayer_TMP
go
create table ZCImagePlayer_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	Code varchar (100) not null,
	SiteID bigint not null,
	DisplayType varchar (2) not null,
	ImageSource varchar (2) not null,
	RelaCatalogInnerCode varchar (100) ,
	Height bigint not null,
	Width bigint not null,
	DisplayCount bigint ,
	IsShowText varchar (2) not null,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from ZCImagePlayer) exec ('insert into ZCImagePlayer_TMP (ModifyTime,ModifyUser,AddTime,AddUser,DisplayCount,IsShowText,Width,Height,RelaCatalogInnerCode,ImageSource,DisplayType,SiteID,Code,Name,ID) select ModifyTime,ModifyUser,AddTime,AddUser,DisplayCount,''0'' as IsShowText,Width,Height,RelaCatalogInnerCode,ImageSource,DisplayType,SiteID,Code,Name,ID from ZCImagePlayer')
go
drop table ZCImagePlayer
go
sp_rename 'ZCImagePlayer_TMP', 'ZCImagePlayer', 'OBJECT'
go
alter table ZCImagePlayer add constraint PK_ZCImagePlayer primary key  NONCLUSTERED(ID)
go
update zcimageplayer set IsShowText='Y'
go


/*去掉无用的角色关联*/
delete from ZDUserROle where RoleCode not in (select RoleCode from ZDRole) or UserName not in (select UserName from ZDUser)
go


/*工作流步骤备注字段加长 20100111 by wyuch*/
alter table zwstep alter column Memo varchar(400)
go
alter table bzwstep alter column Memo varchar(400)
go

update ZCCatalog set Workflow=null
go
/** 汪维军 2010-01-11 栏目增加列表页最大分页数字段ListPage */
if exists (select 1 from  sysobjects where id = object_id('BZCCatalog_TMP') and type='U') drop table BZCCatalog_TMP
go
create table BZCCatalog_TMP(
	ID bigint not null,
	ParentID bigint not null,
	SiteID bigint not null,
	Name varchar (100) not null,
	InnerCode varchar (100) not null,
	BranchInnerCode varchar (100) ,
	Alias varchar (100) not null,
	URL varchar (100) ,
	ImagePath varchar (50) ,
	Type bigint not null,
	IndexTemplate varchar (200) ,
	ListTemplate varchar (200) ,
	ListNameRule varchar (200) ,
	DetailTemplate varchar (200) ,
	DetailNameRule varchar (200) ,
	RssTemplate varchar (200) ,
	RssNameRule varchar (200) ,
	Workflow varchar (100) ,
	TreeLevel bigint not null,
	ChildCount bigint not null,
	IsLeaf bigint not null,
	IsDirty bigint ,
	Total bigint not null,
	OrderFlag bigint not null,
	Logo varchar (100) ,
	ListPageSize bigint ,
	ListPage bigint not null,
	PublishFlag varchar (2) not null,
	SingleFlag varchar (2) ,
	HitCount bigint ,
	Meta_Keywords varchar (200) ,
	Meta_Description varchar (200) ,
	OrderColumn varchar (20) ,
	Integral bigint ,
	KeywordFlag varchar (2) ,
	KeywordSetting varchar (50) ,
	AllowContribute varchar (2) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCCatalog) exec ('insert into BZCCatalog_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AllowContribute,KeywordSetting,KeywordFlag,Integral,OrderColumn,Meta_Description,Meta_Keywords,HitCount,SingleFlag,PublishFlag,ListPageSize,ListPage,Logo,OrderFlag,Total,IsDirty,IsLeaf,ChildCount,TreeLevel,Workflow,RssNameRule,RssTemplate,DetailNameRule,DetailTemplate,ListNameRule,ListTemplate,IndexTemplate,Type,ImagePath,URL,Alias,BranchInnerCode,InnerCode,Name,SiteID,ParentID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AllowContribute,KeywordSetting,KeywordFlag,Integral,OrderColumn,Meta_Description,Meta_Keywords,HitCount,SingleFlag,PublishFlag,ListPageSize,''0'' as ListPage,Logo,OrderFlag,Total,IsDirty,IsLeaf,ChildCount,TreeLevel,Workflow,RssNameRule,RssTemplate,DetailNameRule,DetailTemplate,ListNameRule,ListTemplate,IndexTemplate,Type,ImagePath,URL,Alias,BranchInnerCode,InnerCode,Name,SiteID,ParentID,ID from BZCCatalog')
go
drop table BZCCatalog
go
sp_rename 'BZCCatalog_TMP', 'BZCCatalog', 'OBJECT'
go
alter table BZCCatalog add constraint PK_BZCCatalog primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('ZCCatalog_TMP') and type='U') drop table ZCCatalog_TMP
go
create table ZCCatalog_TMP(
	ID bigint not null,
	ParentID bigint not null,
	SiteID bigint not null,
	Name varchar (100) not null,
	InnerCode varchar (100) not null,
	BranchInnerCode varchar (100) ,
	Alias varchar (100) not null,
	URL varchar (100) ,
	ImagePath varchar (50) ,
	Type bigint not null,
	IndexTemplate varchar (200) ,
	ListTemplate varchar (200) ,
	ListNameRule varchar (200) ,
	DetailTemplate varchar (200) ,
	DetailNameRule varchar (200) ,
	RssTemplate varchar (200) ,
	RssNameRule varchar (200) ,
	Workflow varchar (100) ,
	TreeLevel bigint not null,
	ChildCount bigint not null,
	IsLeaf bigint not null,
	IsDirty bigint ,
	Total bigint not null,
	OrderFlag bigint not null,
	Logo varchar (100) ,
	ListPageSize bigint ,
	ListPage bigint not null,
	PublishFlag varchar (2) not null,
	SingleFlag varchar (2) ,
	HitCount bigint ,
	Meta_Keywords varchar (200) ,
	Meta_Description varchar (200) ,
	OrderColumn varchar (20) ,
	Integral bigint ,
	KeywordFlag varchar (2) ,
	KeywordSetting varchar (50) ,
	AllowContribute varchar (2) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from ZCCatalog) exec ('insert into ZCCatalog_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AllowContribute,KeywordSetting,KeywordFlag,Integral,OrderColumn,Meta_Description,Meta_Keywords,HitCount,SingleFlag,PublishFlag,ListPageSize,ListPage,Logo,OrderFlag,Total,IsDirty,IsLeaf,ChildCount,TreeLevel,Workflow,RssNameRule,RssTemplate,DetailNameRule,DetailTemplate,ListNameRule,ListTemplate,IndexTemplate,Type,ImagePath,URL,Alias,BranchInnerCode,InnerCode,Name,SiteID,ParentID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AllowContribute,KeywordSetting,KeywordFlag,Integral,OrderColumn,Meta_Description,Meta_Keywords,HitCount,SingleFlag,PublishFlag,ListPageSize,''0'' as ListPage,Logo,OrderFlag,Total,IsDirty,IsLeaf,ChildCount,TreeLevel,Workflow,RssNameRule,RssTemplate,DetailNameRule,DetailTemplate,ListNameRule,ListTemplate,IndexTemplate,Type,ImagePath,URL,Alias,BranchInnerCode,InnerCode,Name,SiteID,ParentID,ID from ZCCatalog')
go
drop table ZCCatalog
go
sp_rename 'ZCCatalog_TMP', 'ZCCatalog', 'OBJECT'
go
alter table ZCCatalog add constraint PK_ZCCatalog primary key  NONCLUSTERED(ID)
go
create index idx42_catalog on zccatalog (SiteID,Type)
go
create index idx43_catalog on zccatalog (InnerCode)
go
update ZCCatalog set ListPageSize=20
go
update ZCCatalog set ListPage=-1
go

/** 汪维军 2010-01-12 媒体库默认栏目设置为不可删除 */
update ZCCatalog set Prop4='N' where name='默认图片' and type=4
go
update ZCCatalog set Prop4='N' where name='默认视频' and type=5
go
update ZCCatalog set Prop4='N' where name='默认音频' and type=6
go
update ZCCatalog set Prop4='N' where name='默认附件' and type=7
go
/* 王少亭 2010-01-12 修改字段 长度 */
alter table BZDUserLog alter column LogType varchar(20)
go
alter table BZDUserLog alter column SubType varchar(20)
go

/** 广告新增功能 版位表修改 徐喆 2010-01-14 */
if exists (select 1 from  sysobjects where id = object_id('zcadposition_TMP') and type='U') drop table zcadposition_TMP
go
create table zcadposition_TMP(
	ID bigint not null,
	SiteID bigint not null,
	PositionName varchar (100) not null,
	Code varchar (50) not null,
	Description text ,
	PositionType varchar (20) ,
	PaddingTop bigint ,
	PaddingLeft bigint ,
	PositionWidth bigint ,
	PositionHeight bigint ,
	Align varchar (2) ,
	JsName varchar (100) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zcadposition) exec ('insert into zcadposition_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,JsName,PositionHeight,PositionWidth,PaddingLeft,PaddingTop,PositionType,Description,Code,PositionName,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,JsName,PositionHeight,PositionWidth,PaddingLeft,PaddingTop,PositionType,Description,Code,PositionName,SiteID,ID from zcadposition')
go
drop table zcadposition
go
sp_rename 'zcadposition_TMP', 'zcadposition', 'OBJECT'
go
alter table zcadposition add constraint PK_zcadposition primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('bzcadposition_TMP') and type='U') drop table bzcadposition_TMP
go
create table bzcadposition_TMP(
	ID bigint not null,
	SiteID bigint not null,
	PositionName varchar (100) not null,
	Code varchar (50) not null,
	Description text ,
	PositionType varchar (20) ,
	PaddingTop bigint ,
	PaddingLeft bigint ,
	PositionWidth bigint ,
	PositionHeight bigint ,
	Align varchar (2) ,
	JsName varchar (100) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcadposition) exec ('insert into bzcadposition_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,JsName,PositionHeight,PositionWidth,PaddingLeft,PaddingTop,PositionType,Description,Code,PositionName,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,JsName,PositionHeight,PositionWidth,PaddingLeft,PaddingTop,PositionType,Description,Code,PositionName,SiteID,ID from bzcadposition')
go
drop table bzcadposition
go
sp_rename 'bzcadposition_TMP', 'bzcadposition', 'OBJECT'
go
alter table bzcadposition add constraint PK_bzcadposition primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('zcadposition_TMP') and type='U') drop table zcadposition_TMP
go
create table zcadposition_TMP(
	ID bigint not null,
	SiteID bigint not null,
	PositionName varchar (100) not null,
	Code varchar (50) not null,
	Description text ,
	PositionType varchar (20) ,
	PaddingTop bigint ,
	PaddingLeft bigint ,
	PositionWidth bigint ,
	PositionHeight bigint ,
	Align varchar (2) ,
	Scroll varchar (2) ,
	JsName varchar (100) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zcadposition) exec ('insert into zcadposition_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,JsName,Align,PositionHeight,PositionWidth,PaddingLeft,PaddingTop,PositionType,Description,Code,PositionName,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,JsName,Align,PositionHeight,PositionWidth,PaddingLeft,PaddingTop,PositionType,Description,Code,PositionName,SiteID,ID from zcadposition')
go
drop table zcadposition
go
sp_rename 'zcadposition_TMP', 'zcadposition', 'OBJECT'
go
alter table zcadposition add constraint PK_zcadposition primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('bzcadposition_TMP') and type='U') drop table bzcadposition_TMP
go
create table bzcadposition_TMP(
	ID bigint not null,
	SiteID bigint not null,
	PositionName varchar (100) not null,
	Code varchar (50) not null,
	Description text ,
	PositionType varchar (20) ,
	PaddingTop bigint ,
	PaddingLeft bigint ,
	PositionWidth bigint ,
	PositionHeight bigint ,
	Align varchar (2) ,
	Scroll varchar (2) ,
	JsName varchar (100) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcadposition) exec ('insert into bzcadposition_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,JsName,Align,PositionHeight,PositionWidth,PaddingLeft,PaddingTop,PositionType,Description,Code,PositionName,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,JsName,Align,PositionHeight,PositionWidth,PaddingLeft,PaddingTop,PositionType,Description,Code,PositionName,SiteID,ID from bzcadposition')
go
drop table bzcadposition
go
sp_rename 'bzcadposition_TMP', 'bzcadposition', 'OBJECT'
go
alter table bzcadposition add constraint PK_bzcadposition primary key  NONCLUSTERED(ID,BackupNo)
go


/** 分发添加字段 操作Operation 汪维军 2010-01-14 */
if exists (select 1 from  sysobjects where id = object_id('ZCDeployJob_TMP') and type='U') drop table ZCDeployJob_TMP
go
create table ZCDeployJob_TMP(
	ID bigint not null,
	ConfigID bigint not null,
	SiteID bigint not null,
	Source varchar (255) ,
	Target varchar (255) ,
	Method varchar (50) ,
	Operation varchar (100) ,
	Host varchar (255) ,
	Port bigint ,
	UserName varchar (100) ,
	Password varchar (100) ,
	Status bigint ,
	RetryCount bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from ZCDeployJob) exec ('insert into ZCDeployJob_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,RetryCount,Status,Password,UserName,Port,Host,Method,Target,Source,SiteID,ConfigID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,RetryCount,Status,Password,UserName,Port,Host,Method,Target,Source,SiteID,ConfigID,ID from ZCDeployJob')
go
drop table ZCDeployJob
go
sp_rename 'ZCDeployJob_TMP', 'ZCDeployJob', 'OBJECT'
go
alter table ZCDeployJob add constraint PK_ZCDeployJob primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('BZCDeployJob_TMP') and type='U') drop table BZCDeployJob_TMP
go
create table BZCDeployJob_TMP(
	ID bigint not null,
	ConfigID bigint not null,
	SiteID bigint not null,
	Source varchar (255) ,
	Target varchar (255) ,
	Method varchar (50) ,
	Operation varchar (100) ,
	Host varchar (255) ,
	Port bigint ,
	UserName varchar (100) ,
	Password varchar (100) ,
	Status bigint ,
	RetryCount bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCDeployJob) exec ('insert into BZCDeployJob_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,RetryCount,Status,Password,UserName,Port,Host,Method,Target,Source,SiteID,ConfigID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,RetryCount,Status,Password,UserName,Port,Host,Method,Target,Source,SiteID,ConfigID,ID from BZCDeployJob')
go
drop table BZCDeployJob
go
sp_rename 'BZCDeployJob_TMP', 'BZCDeployJob', 'OBJECT'
go
alter table BZCDeployJob add constraint PK_BZCDeployJob primary key  NONCLUSTERED(ID,BackupNo)
go
update ZCDeployJob set Operation='copy'
go


/** 站点表修改字段OrderFlag 汪维军 2010-01-16 */
update zcsite set orderflag='0'
go
update bzcsite set orderflag='0'
go
alter table zcsite alter column orderflag bigint
go
alter table bzcsite alter column orderflag bigint
go
update zcsite set orderflag='0'
go
update bzcsite set orderflag='0'
go

/** 添加商品品牌表 2010-01-18 */
if exists (select 1 from  sysobjects where id = object_id('BZSBrand') and type='U') drop table BZSBrand
go

/*==============================================================*/
/* Table: BZSBrand */
/*==============================================================*/
create table BZSBrand(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (100) not null,
	BranchInnerCode varchar (100) ,
	Alias varchar (100) not null,
	URL varchar (100) ,
	ImagePath varchar (50) ,
	Info varchar (1024) ,
	IndexTemplate varchar (200) ,
	ListTemplate varchar (200) ,
	ListNameRule varchar (200) ,
	DetailTemplate varchar (200) ,
	DetailNameRule varchar (200) ,
	RssTemplate varchar (200) ,
	RssNameRule varchar (200) ,
	OrderFlag bigint not null,
	ListPageSize bigint ,
	ListPage bigint ,
	PublishFlag varchar (2) not null,
	SingleFlag varchar (2) ,
	HitCount bigint ,
	Meta_Keywords varchar (200) ,
	Meta_Description varchar (200) ,
	KeywordFlag varchar (2) ,
	KeywordSetting varchar (50) ,
	Memo varchar (200) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSBrand primary key nonclustered (ID,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('ZSBrand') and type='U') drop table ZSBrand
go

/*==============================================================*/
/* Table: ZSBrand */
/*==============================================================*/
create table ZSBrand(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (100) not null,
	BranchInnerCode varchar (100) ,
	Alias varchar (100) not null,
	URL varchar (100) ,
	ImagePath varchar (50) ,
	Info varchar (1024) ,
	IndexTemplate varchar (200) ,
	ListTemplate varchar (200) ,
	ListNameRule varchar (200) ,
	DetailTemplate varchar (200) ,
	DetailNameRule varchar (200) ,
	RssTemplate varchar (200) ,
	RssNameRule varchar (200) ,
	OrderFlag bigint not null,
	ListPageSize bigint ,
	ListPage bigint ,
	PublishFlag varchar (2) not null,
	SingleFlag varchar (2) ,
	HitCount bigint ,
	Meta_Keywords varchar (200) ,
	Meta_Description varchar (200) ,
	KeywordFlag varchar (2) ,
	KeywordSetting varchar (50) ,
	Memo varchar (200) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZSBrand primary key nonclustered (ID)
)
go

/** 崔建成 2010-01-19 将热点词类型设置为varchar类型 */
alter table ZCKeyword alter column KeywordType varchar(255)
go
alter table BZCKeyword alter column KeywordType varchar(255)
go

/** 崔建成 2010-01-20 将热点词类标识改为热点词类型 */
update ZCCatalogConfig set HotWordFlag = 0
go
alter table ZCCatalogConfig alter column HotWordFlag bigint
go
 sp_rename 'ZCCatalogConfig.HotWordFlag','HotWordType','column'
go
alter table BZCCatalogConfig alter column HotWordFlag bigint
go
 sp_rename 'BZCCatalogConfig.HotWordFlag','HotWordType','column'
go

/** 李伟仪 2010-01-19 ZSOrderItem */
if exists (select 1 from  sysobjects where id = object_id('ZSOrderItem') and type='U') drop table ZSOrderItem
go

/*==============================================================*/
/* Table: ZSOrderItem */
/*==============================================================*/
create table ZSOrderItem(
	OrderID bigint not null,
	GoodsID bigint not null,
	SiteID bigint not null,
	MemberCode varchar (200) ,
	SN varchar (50) ,
	Name varchar (200) ,
	Price numeric (12,2) ,
	Discount numeric (12,2) ,
	DiscountPrice numeric (12,2) ,
	Count bigint ,
	Amount numeric ,
	Score bigint ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZSOrderItem primary key nonclustered (OrderID,GoodsID)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZSOrderItem') and type='U') drop table BZSOrderItem
go

/*==============================================================*/
/* Table: BZSOrderItem */
/*==============================================================*/
create table BZSOrderItem(
	OrderID bigint not null,
	GoodsID bigint not null,
	SiteID bigint not null,
	MemberCode varchar (200) ,
	SN varchar (50) ,
	Name varchar (200) ,
	Price numeric (12,2) ,
	Discount numeric (12,2) ,
	DiscountPrice numeric (12,2) ,
	Count bigint ,
	Amount numeric ,
	Score bigint ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSOrderItem primary key nonclustered (OrderID,GoodsID)
)
go

/** 李伟仪 2010-01-19 BZSOrder */
if exists (select 1 from  sysobjects where id = object_id('BZSOrder') and type='U') drop table BZSOrder
go

/*==============================================================*/
/* Table: BZSOrder */
/*==============================================================*/
create table BZSOrder(
	ID bigint not null,
	SiteID bigint not null,
	MemberCode varchar (200) ,
	IsValid varchar (1) ,
	Status varchar (40) ,
	Amount numeric (12,2) not null,
	Score bigint not null,
	Name varchar (30) ,
	Province varchar (6) ,
	City varchar (6) ,
	District varchar (6) ,
	Address varchar (255) ,
	ZipCode varchar (10) ,
	Tel varchar (20) ,
	Mobile varchar (20) ,
	HasInvoice varchar (1) not null,
	InvoiceTitle varchar (100) ,
	SendBeginDate datetime ,
	SendEndDate datetime ,
	SendTimeSlice varchar (40) ,
	SendInfo varchar (200) ,
	SendType varchar (40) ,
	PaymentType varchar (40) ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSOrder primary key nonclustered (ID)
)
go

if exists (select 1 from  sysobjects where id = object_id('ZSSend') and type='U') drop table ZSSend
go

/*==============================================================*/
/* Table: ZSSend */
/*==============================================================*/
create table ZSSend(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) ,
	SendInfo varchar (200) ,
	ArriveInfo varchar (200) ,
	Info varchar (200) ,
	Price numeric (12,2) ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZSSend primary key nonclustered (ID)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZSSend') and type='U') drop table BZSSend
go

/*==============================================================*/
/* Table: BZSSend */
/*==============================================================*/
create table BZSSend(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) ,
	SendInfo varchar (200) ,
	ArriveInfo varchar (200) ,
	Info varchar (200) ,
	Price numeric (12,2) ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSSend primary key nonclustered (ID,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('ZSPayment') and type='U') drop table ZSPayment
go

/*==============================================================*/
/* Table: ZSPayment */
/*==============================================================*/
create table ZSPayment(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) ,
	Info varchar (1000) ,
	IsVisible varchar (1) ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZSPayment primary key nonclustered (ID)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZSPayment') and type='U') drop table BZSPayment
go

/*==============================================================*/
/* Table: BZSPayment */
/*==============================================================*/
create table BZSPayment(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) ,
	Info varchar (1000) ,
	IsVisible varchar (1) ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSPayment primary key nonclustered (ID,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('ZSGoods') and type='U') drop table ZSGoods
go

/** 李伟仪 2010-01-20 ZSGoods,BZSGoods */
/*==============================================================*/
/* Table: ZSGoods */
/*==============================================================*/
create table ZSGoods(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BrandID bigint ,
	BranchInnerCode varchar (100) ,
	Type varchar (2) not null,
	SN varchar (50) ,
	Name varchar (100) not null,
	Alias varchar (100) ,
	BarCode varchar (128) ,
	WorkFlowID bigint ,
	Status varchar (20) ,
	Factory varchar (100) ,
	OrderFlag bigint ,
	MarketPrice numeric (12,2) ,
	Price numeric (12,2) ,
	MemberPrice numeric (12,2) ,
	VIPPrice numeric (12,2) ,
	EffectDate datetime ,
	Store bigint not null,
	Standard varchar (100) ,
	Unit varchar (10) ,
	Score bigint not null,
	CommentCount bigint not null,
	SaleCount bigint not null,
	HitCount bigint not null,
	Image0 varchar (200) ,
	Image1 varchar (200) ,
	Image2 varchar (200) ,
	Image3 varchar (200) ,
	RelativeGoods varchar (100) ,
	Keyword varchar (100) ,
	TopDate datetime ,
	TopFlag varchar (2) not null,
	Content text ,
	Summary varchar (2000) ,
	RedirectURL varchar (200) ,
	Attribute varchar (100) ,
	RecommendGoods varchar (100) ,
	StickTime bigint not null,
	PublishDate datetime ,
	DownlineDate datetime ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZSGoods primary key nonclustered (ID)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZSGoods') and type='U') drop table BZSGoods
go

/*==============================================================*/
/* Table: BZSGoods */
/*==============================================================*/
create table BZSGoods(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BrandID bigint ,
	BranchInnerCode varchar (100) ,
	Type varchar (2) not null,
	SN varchar (50) ,
	Name varchar (100) not null,
	Alias varchar (100) ,
	BarCode varchar (128) ,
	WorkFlowID bigint ,
	Status varchar (20) ,
	Factory varchar (100) ,
	OrderFlag bigint ,
	MarketPrice numeric (12,2) ,
	Price numeric (12,2) ,
	MemberPrice numeric (12,2) ,
	VIPPrice numeric (12,2) ,
	EffectDate datetime ,
	Store bigint not null,
	Standard varchar (100) ,
	Unit varchar (10) ,
	Score bigint not null,
	CommentCount bigint not null,
	SaleCount bigint not null,
	HitCount bigint not null,
	Image0 varchar (200) ,
	Image1 varchar (200) ,
	Image2 varchar (200) ,
	Image3 varchar (200) ,
	RelativeGoods varchar (100) ,
	Keyword varchar (100) ,
	TopDate datetime ,
	TopFlag varchar (2) not null,
	Content text ,
	Summary varchar (2000) ,
	RedirectURL varchar (200) ,
	Attribute varchar (100) ,
	RecommendGoods varchar (100) ,
	StickTime bigint not null,
	PublishDate datetime ,
	DownlineDate datetime ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSGoods primary key nonclustered (ID)
)
go

/* 李伟仪 添加会员商品收藏夹表ZSFavorite */
if exists (select 1 from  sysobjects where id = object_id('ZSFavorite') and type='U') drop table ZSFavorite
go

/*==============================================================*/
/* Table: ZSFavorite */
/*==============================================================*/
create table ZSFavorite(
	UserName varchar (200) not null,
	GoodsID bigint not null,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZSFavorite primary key nonclustered (UserName,GoodsID)
)
go

if exists (select 1 from  sysobjects where id = object_id('ZDMember') and type='U') drop table ZDMember
go

/*==============================================================*/
/* Table: ZDMember */
/*==============================================================*/
create table ZDMember(
	UserName varchar (50) not null,
	Password varchar (32) not null,
	Name varchar (100) not null,
	Email varchar (100) not null,
	Gender varchar (1) ,
	Type varchar (10) ,
	SiteID bigint ,
	Logo varchar (100) ,
	Status varchar (1) not null,
	Score varchar (20) ,
	Rank varchar (50) ,
	MemberLevel varchar (10) ,
	PWQuestion varchar (100) ,
	PWAnswer varchar (100) ,
	LastLoginIP varchar (16) ,
	LastLoginTime datetime ,
	RegTime datetime ,
	RegIP varchar (16) ,
	LoginMD5 varchar (32) ,
	Prop1 varchar (100) ,
	Prop2 varchar (100) ,
	Prop3 varchar (100) ,
	Prop4 varchar (100) ,
	Prop5 varchar (100) ,
	Prop6 varchar (100) ,
	Prop7 varchar (100) ,
	Prop8 varchar (100) ,
	Prop9 varchar (100) ,
	Prop10 varchar (100) ,
	Prop11 varchar (100) ,
	Prop12 varchar (100) ,
	Prop13 varchar (100) ,
	Prop14 varchar (100) ,
	Prop15 varchar (100) ,
	Prop16 varchar (100) ,
	Prop17 varchar (100) ,
	Prop18 varchar (100) ,
	Prop19 varchar (100) ,
	Prop20 varchar (100) ,
	constraint PK_ZDMember primary key nonclustered (UserName)
)
go

if exists (select 1 from  sysobjects where id = object_id('ZDMemberField') and type='U') drop table ZDMemberField
go

/*==============================================================*/
/* Table: ZDMemberField */
/*==============================================================*/
create table ZDMemberField(
	SiteID bigint not null,
	Name varchar (50) ,
	Code varchar (50) not null,
	RealField varchar (20) ,
	VerifyType varchar (2) not null,
	MaxLength int ,
	InputType varchar (20) not null,
	DefaultValue varchar (50) ,
	ListOption varchar (1000) ,
	HTML text ,
	IsMandatory varchar (2) not null,
	OrderFlag bigint ,
	RowSize int ,
	ColSize int ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZDMemberField primary key nonclustered (SiteID,Code)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZDMember') and type='U') drop table BZDMember
go

/*==============================================================*/
/* Table: BZDMember */
/*==============================================================*/
create table BZDMember(
	UserName varchar (50) not null,
	Password varchar (32) not null,
	Name varchar (100) not null,
	Email varchar (100) not null,
	Gender varchar (1) ,
	Type varchar (10) ,
	SiteID bigint ,
	Logo varchar (100) ,
	Status varchar (1) not null,
	Score varchar (20) ,
	Rank varchar (50) ,
	MemberLevel varchar (10) ,
	PWQuestion varchar (100) ,
	PWAnswer varchar (100) ,
	LastLoginIP varchar (16) ,
	LastLoginTime datetime ,
	RegTime datetime ,
	RegIP varchar (16) ,
	LoginMD5 varchar (32) ,
	Prop1 varchar (100) ,
	Prop2 varchar (100) ,
	Prop3 varchar (100) ,
	Prop4 varchar (100) ,
	Prop5 varchar (100) ,
	Prop6 varchar (100) ,
	Prop7 varchar (100) ,
	Prop8 varchar (100) ,
	Prop9 varchar (100) ,
	Prop10 varchar (100) ,
	Prop11 varchar (100) ,
	Prop12 varchar (100) ,
	Prop13 varchar (100) ,
	Prop14 varchar (100) ,
	Prop15 varchar (100) ,
	Prop16 varchar (100) ,
	Prop17 varchar (100) ,
	Prop18 varchar (100) ,
	Prop19 varchar (100) ,
	Prop20 varchar (100) ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZDMember primary key nonclustered (UserName,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZDMemberField') and type='U') drop table BZDMemberField
go

/*==============================================================*/
/* Table: BZDMemberField */
/*==============================================================*/
create table BZDMemberField(
	SiteID bigint not null,
	Name varchar (50) ,
	Code varchar (50) not null,
	RealField varchar (20) ,
	VerifyType varchar (2) not null,
	MaxLength int ,
	InputType varchar (20) not null,
	DefaultValue varchar (50) ,
	ListOption varchar (1000) ,
	HTML text ,
	IsMandatory varchar (2) not null,
	OrderFlag bigint ,
	RowSize int ,
	ColSize int ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZDMemberField primary key nonclustered (SiteID,Code,BackupNo)
)
go

/*增加全站动态应用相关的模板设置 2010-01-29 by wyuch*/
if exists (select 1 from  sysobjects where id = object_id('zcsite_TMP') and type='U') drop table zcsite_TMP
go
create table zcsite_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	Alias varchar (100) not null,
	Info varchar (100) ,
	BranchInnerCode varchar (100) not null,
	URL varchar (100) not null,
	RootPath varchar (100) ,
	IndexTemplate varchar (100) ,
	ListTemplate varchar (100) ,
	DetailTemplate varchar (100) ,
	EditorCss varchar (100) ,
	Workflow varchar (100) ,
	OrderFlag bigint ,
	LogoFileName varchar (100) ,
	MessageBoardFlag varchar (2) ,
	CommentAuditFlag varchar (1) ,
	ChannelCount bigint not null,
	MagzineCount bigint not null,
	SpecialCount bigint not null,
	ImageLibCount bigint not null,
	VideoLibCount bigint not null,
	AudioLibCount bigint not null,
	AttachmentLibCount bigint not null,
	ArticleCount bigint not null,
	HitCount bigint not null,
	ConfigXML text ,
	AutoIndexFlag varchar (2) ,
	AutoStatFlag varchar (2) ,
	HeaderTemplate varchar (100) ,
	TopTemplate varchar (100) ,
	BottomTemplate varchar (100) ,
	Prop1 varchar (100) ,
	Prop2 varchar (100) ,
	Prop3 varchar (100) ,
	Prop4 varchar (100) ,
	Prop5 varchar (100) ,
	Prop6 varchar (100) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zcsite) exec ('insert into zcsite_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID from zcsite')
go
drop table zcsite
go
sp_rename 'zcsite_TMP', 'zcsite', 'OBJECT'
go
alter table zcsite add constraint PK_zcsite primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('bzcsite_TMP') and type='U') drop table bzcsite_TMP
go
create table bzcsite_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	Alias varchar (100) not null,
	Info varchar (100) ,
	BranchInnerCode varchar (100) not null,
	URL varchar (100) not null,
	RootPath varchar (100) ,
	IndexTemplate varchar (100) ,
	ListTemplate varchar (100) ,
	DetailTemplate varchar (100) ,
	EditorCss varchar (100) ,
	Workflow varchar (100) ,
	OrderFlag bigint ,
	LogoFileName varchar (100) ,
	MessageBoardFlag varchar (2) ,
	CommentAuditFlag varchar (1) ,
	ChannelCount bigint not null,
	MagzineCount bigint not null,
	SpecialCount bigint not null,
	ImageLibCount bigint not null,
	VideoLibCount bigint not null,
	AudioLibCount bigint not null,
	AttachmentLibCount bigint not null,
	ArticleCount bigint not null,
	HitCount bigint not null,
	ConfigXML text ,
	AutoIndexFlag varchar (2) ,
	AutoStatFlag varchar (2) ,
	HeaderTemplate varchar (100) ,
	TopTemplate varchar (100) ,
	BottomTemplate varchar (100) ,
	Prop1 varchar (100) ,
	Prop2 varchar (100) ,
	Prop3 varchar (100) ,
	Prop4 varchar (100) ,
	Prop5 varchar (100) ,
	Prop6 varchar (100) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcsite) exec ('insert into bzcsite_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID from bzcsite')
go
drop table bzcsite
go
sp_rename 'bzcsite_TMP', 'bzcsite', 'OBJECT'
go
alter table bzcsite add constraint PK_bzcsite primary key  NONCLUSTERED(ID,BackupNo)
go

/**王少亭 2010-01-31**/
if exists (select 1 from  sysobjects where id = object_id('ZCForumGroup_TMP') and type='U') drop table ZCForumGroup_TMP
go
create table ZCForumGroup_TMP(
	ID bigint not null,
	SiteID bigint ,
	RadminID bigint ,
	Name varchar (100) not null,
	SystemName varchar (100) ,
	Type varchar (100) not null,
	Color varchar (100) ,
	Image varchar (100) ,
	LowerScore bigint ,
	UpperScore bigint ,
	AllowTheme varchar (2) ,
	AllowReply varchar (2) ,
	AllowSearch varchar (2) ,
	AllowUserInfo varchar (2) ,
	AllowPanel varchar (2) ,
	AllowNickName varchar (2) ,
	AllowVisit varchar (2) ,
	AllowHeadImage varchar (2) ,
	AllowFace varchar (2) ,
	AllowAutograph varchar (2) ,
	Verify varchar (2) ,
	AllowEditUser varchar (2) ,
	AllowForbidUser varchar (2) ,
	AllowEditForum varchar (2) ,
	AllowVerfyPost varchar (2) ,
	AllowDel varchar (2) ,
	AllowEdit varchar (2) ,
	Hide varchar (2) ,
	RemoveTheme varchar (2) ,
	MoveTheme varchar (2) ,
	TopTheme varchar (2) ,
	UpOrDownTheme varchar (2) ,
	BrightTheme varchar (2) ,
	BestTheme varchar (2) ,
	prop1 varchar (50) ,
	prop2 varchar (50) ,
	prop3 varchar (50) ,
	prop4 varchar (50) ,
	AddUser varchar (100) not null,
	AddTime datetime not null,
	ModifyUser varchar (100) ,
	ModifyTime datetime ,
	OrderFlag bigint )
go
if exists(select * from ZCForumGroup) exec ('insert into ZCForumGroup_TMP (OrderFlag,ModifyTime,ModifyUser,AddTime,AddUser,prop4,prop3,prop2,prop1,Verify,AllowAutograph,AllowFace,AllowHeadImage,AllowVisit,AllowNickName,AllowPanel,AllowUserInfo,AllowSearch,AllowReply,AllowTheme,UpperScore,LowerScore,Image,Color,Type,SystemName,Name,RadminID,SiteID,ID) select OrderFlag,ModifyTime,ModifyUser,AddTime,AddUser,prop4,prop3,prop2,prop1,Verify,AllowAutograph,AllowFace,AllowHeadImage,AllowVisit,AllowNickName,AllowPanel,AllowUserInfo,AllowSearch,AllowReply,AllowTheme,UpperScore,LowerScore,Image,Color,Type,SystemName,Name,RadminID,SiteID,ID from ZCForumGroup')
go
drop table ZCForumGroup
go
sp_rename 'ZCForumGroup_TMP', 'ZCForumGroup', 'OBJECT'
go
alter table ZCForumGroup add constraint PK_ZCForumGroup primary key  NONCLUSTERED(ID)
go

if exists (select 1 from  sysobjects where id = object_id('BZCForumGroup_TMP') and type='U') drop table BZCForumGroup_TMP
go
create table BZCForumGroup_TMP(
	ID bigint not null,
	SiteID bigint ,
	RadminID bigint ,
	Name varchar (100) not null,
	SystemName varchar (100) ,
	Type varchar (100) not null,
	Color varchar (100) ,
	Image varchar (100) ,
	LowerScore bigint ,
	UpperScore bigint ,
	AllowTheme varchar (2) ,
	AllowReply varchar (2) ,
	AllowSearch varchar (2) ,
	AllowUserInfo varchar (2) ,
	AllowPanel varchar (2) ,
	AllowNickName varchar (2) ,
	AllowVisit varchar (2) ,
	AllowHeadImage varchar (2) ,
	AllowFace varchar (2) ,
	AllowAutograph varchar (2) ,
	Verify varchar (2) ,
	AllowEditUser varchar (2) ,
	AllowForbidUser varchar (2) ,
	AllowEditForum varchar (2) ,
	AllowVerfyPost varchar (2) ,
	AllowDel varchar (2) ,
	AllowEdit varchar (2) ,
	Hide varchar (2) ,
	RemoveTheme varchar (2) ,
	MoveTheme varchar (2) ,
	TopTheme varchar (2) ,
	UpOrDownTheme varchar (2) ,
	BrightTheme varchar (2) ,
	BestTheme varchar (2) ,
	prop1 varchar (50) ,
	prop2 varchar (50) ,
	prop3 varchar (50) ,
	prop4 varchar (50) ,
	AddUser varchar (100) not null,
	AddTime datetime not null,
	ModifyUser varchar (100) ,
	ModifyTime datetime ,
	OrderFlag bigint ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCForumGroup) exec ('insert into BZCForumGroup_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,OrderFlag,ModifyTime,ModifyUser,AddTime,AddUser,prop4,prop3,prop2,prop1,Verify,AllowAutograph,AllowFace,AllowHeadImage,AllowVisit,AllowNickName,AllowPanel,AllowUserInfo,AllowSearch,AllowReply,AllowTheme,UpperScore,LowerScore,Image,Color,Type,SystemName,Name,RadminID,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,OrderFlag,ModifyTime,ModifyUser,AddTime,AddUser,prop4,prop3,prop2,prop1,Verify,AllowAutograph,AllowFace,AllowHeadImage,AllowVisit,AllowNickName,AllowPanel,AllowUserInfo,AllowSearch,AllowReply,AllowTheme,UpperScore,LowerScore,Image,Color,Type,SystemName,Name,RadminID,SiteID,ID from BZCForumGroup')
go
drop table BZCForumGroup
go
sp_rename 'BZCForumGroup_TMP', 'BZCForumGroup', 'OBJECT'
go
alter table BZCForumGroup add constraint PK_BZCForumGroup primary key  NONCLUSTERED(ID,BackupNo)
go

if exists (select 1 from  sysobjects where id = object_id('ZCForumMember_TMP') and type='U') drop table ZCForumMember_TMP
go
create table ZCForumMember_TMP(
	UserName varchar (50) not null,
	SiteID bigint ,
	AdminID bigint ,
	UserGroupID bigint ,
	DefinedID bigint ,
	NickName varchar (100) ,
	ThemeCount bigint ,
	ReplyCount bigint ,
	HeadImage varchar (500) ,
	UseSelfImage varchar (2) ,
	Status varchar (2) ,
	ForumScore bigint ,
	ForumSign varchar (1000) ,
	LastLoginTime datetime ,
	LastLogoutTime datetime ,
	prop1 varchar (50) ,
	prop2 varchar (50) ,
	prop3 varchar (50) ,
	prop4 varchar (50) ,
	AddUser varchar (100) not null,
	AddTime datetime not null,
	ModifyUser varchar (100) ,
	ModifyTime datetime )
go
if exists(select * from ZCForumMember) exec ('insert into ZCForumMember_TMP (ModifyTime,ModifyUser,AddTime,AddUser,prop4,prop3,prop2,prop1,LastLogoutTime,LastLoginTime,ForumSign,ForumScore,Status,UseSelfImage,HeadImage,ReplyCount,ThemeCount,NickName,UserGroupID,AdminID,SiteID,UserName) select ModifyTime,ModifyUser,AddTime,AddUser,prop4,prop3,prop2,prop1,LastLogoutTime,LastLoginTime,ForumSign,ForumScore,Status,UseSelfImage,HeadImage,ReplyCount,ThemeCount,NickName,UserGroupID,AdminID,SiteID,UserName from ZCForumMember')
go
drop table ZCForumMember
go
sp_rename 'ZCForumMember_TMP', 'ZCForumMember', 'OBJECT'
go
alter table ZCForumMember add constraint PK_ZCForumMember primary key  NONCLUSTERED(UserName)
go
if exists (select 1 from  sysobjects where id = object_id('BZCForumMember_TMP') and type='U') drop table BZCForumMember_TMP
go
create table BZCForumMember_TMP(
	UserName varchar (50) not null,
	SiteID bigint ,
	AdminID bigint ,
	UserGroupID bigint ,
	DefinedID bigint ,
	NickName varchar (100) ,
	ThemeCount bigint ,
	ReplyCount bigint ,
	HeadImage varchar (500) ,
	UseSelfImage varchar (2) ,
	Status varchar (2) ,
	ForumScore bigint ,
	ForumSign varchar (1000) ,
	LastLoginTime datetime ,
	LastLogoutTime datetime ,
	prop1 varchar (50) ,
	prop2 varchar (50) ,
	prop3 varchar (50) ,
	prop4 varchar (50) ,
	AddUser varchar (100) not null,
	AddTime datetime not null,
	ModifyUser varchar (100) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCForumMember) exec ('insert into BZCForumMember_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,prop4,prop3,prop2,prop1,LastLogoutTime,LastLoginTime,ForumSign,ForumScore,Status,UseSelfImage,HeadImage,ReplyCount,ThemeCount,NickName,UserGroupID,AdminID,SiteID,UserName) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,prop4,prop3,prop2,prop1,LastLogoutTime,LastLoginTime,ForumSign,ForumScore,Status,UseSelfImage,HeadImage,ReplyCount,ThemeCount,NickName,UserGroupID,AdminID,SiteID,UserName from BZCForumMember')
go
drop table BZCForumMember
go
sp_rename 'BZCForumMember_TMP', 'BZCForumMember', 'OBJECT'
go
alter table BZCForumMember add constraint PK_BZCForumMember primary key  NONCLUSTERED(UserName,BackupNo)
go



if exists (select 1 from  sysobjects where id = object_id('ZCForum_TMP') and type='U') drop table ZCForum_TMP
go
create table ZCForum_TMP(
	ID bigint not null,
	SiteID bigint ,
	ParentID bigint not null,
	Type varchar (20) not null,
	Name varchar (100) not null,
	Status varchar (2) not null,
	Pic varchar (100) ,
	Visible varchar (2) ,
	Info varchar (1000) ,
	ThemeCount int not null,
	Verify varchar (2) ,
	Locked varchar (2) ,
	UnLockID varchar (300) ,
	AllowTheme varchar (2) ,
	EditPost varchar (2) ,
	ReplyPost varchar (2) ,
	Recycle varchar (2) ,
	AllowHTML varchar (2) ,
	AllowFace varchar (2) ,
	AnonyPost varchar (2) ,
	URL varchar (200) ,
	Image varchar (200) ,
	Password varchar (100) ,
	UnPasswordID varchar (300) ,
	Word varchar (200) ,
	PostCount int not null,
	ForumAdmin varchar (100) ,
	TodayPostCount int ,
	LastThemeID bigint ,
	LastPost varchar (100) ,
	LastPoster varchar (100) ,
	OrderFlag bigint not null,
	prop1 varchar (50) ,
	prop2 varchar (50) ,
	prop3 varchar (50) ,
	prop4 varchar (50) ,
	AddUser varchar (100) not null,
	AddTime datetime not null,
	ModifyUser varchar (100) ,
	ModifyTime datetime )
go
if exists(select * from ZCForum) exec ('insert into ZCForum_TMP (ModifyTime,ModifyUser,AddTime,AddUser,prop4,prop3,prop2,prop1,OrderFlag,LastPoster,LastPost,LastThemeID,TodayPostCount,ForumAdmin,PostCount,Word,Password,Image,URL,AnonyPost,AllowFace,AllowHTML,Recycle,ReplyPost,EditPost,AllowTheme,Locked,Verify,ThemeCount,Info,Visible,Pic,Status,Name,Type,ParentID,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,prop4,prop3,prop2,prop1,OrderFlag,LastPoster,LastPost,LastThemeID,TodayPostCount,ForumAdmin,PostCount,Word,Password,Image,URL,AnonyPost,AllowFace,AllowHTML,Recycle,ReplyPost,EditPost,AllowTheme,Locked,Verify,ThemeCount,Info,Visible,Pic,Status,Name,Type,ParentID,SiteID,ID from ZCForum')
go
drop table ZCForum
go
sp_rename 'ZCForum_TMP', 'ZCForum', 'OBJECT'
go
alter table ZCForum add constraint PK_ZCForum primary key  NONCLUSTERED(ID)
go

/*外部数据库增加拉丁字符集标志 2010-02-01*/
if exists (select 1 from  sysobjects where id = object_id('zcdatabase_TMP') and type='U') drop table zcdatabase_TMP
go
create table zcdatabase_TMP(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (100) not null,
	ServerType varchar (10) not null,
	Address varchar (100) not null,
	Port bigint not null,
	UserName varchar (50) not null,
	Password varchar (50) not null,
	DBName varchar (50) not null,
	TestTable varchar (50) ,
	Latin1Flag varchar (2) ,
	Memo varchar (100) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zcdatabase) exec ('insert into zcdatabase_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Memo,TestTable,DBName,Password,UserName,Port,Address,ServerType,Name,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Memo,TestTable,DBName,Password,UserName,Port,Address,ServerType,Name,SiteID,ID from zcdatabase')
go
drop table zcdatabase
go
sp_rename 'zcdatabase_TMP', 'zcdatabase', 'OBJECT'
go
alter table zcdatabase add constraint PK_zcdatabase primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('bzcdatabase_TMP') and type='U') drop table bzcdatabase_TMP
go
create table bzcdatabase_TMP(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (100) not null,
	ServerType varchar (10) not null,
	Address varchar (100) not null,
	Port bigint not null,
	UserName varchar (50) not null,
	Password varchar (50) not null,
	DBName varchar (50) not null,
	TestTable varchar (50) ,
	Latin1Flag varchar (2) ,
	Memo varchar (100) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcdatabase) exec ('insert into bzcdatabase_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Memo,TestTable,DBName,Password,UserName,Port,Address,ServerType,Name,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Memo,TestTable,DBName,Password,UserName,Port,Address,ServerType,Name,SiteID,ID from bzcdatabase')
go
drop table bzcdatabase
go
sp_rename 'bzcdatabase_TMP', 'bzcdatabase', 'OBJECT'
go
alter table bzcdatabase add constraint PK_bzcdatabase primary key  NONCLUSTERED(ID,BackupNo)
go

/*工作流步骤增加来源动作ID 2010-02-01 by wyuch*/
if exists (select 1 from  sysobjects where id = object_id('zwstep_TMP') and type='U') drop table zwstep_TMP
go
create table zwstep_TMP(
	ID bigint not null,
	WorkflowID bigint not null,
	InstanceID bigint not null,
	DataVersionID varchar (30) ,
	NodeID int ,
	ActionID int ,
	PreviousStepID bigint ,
	Owner varchar (50) ,
	StartTime datetime ,
	FinishTime datetime ,
	State varchar (10) ,
	Operators varchar (400) ,
	AllowUser varchar (4000) ,
	AllowOrgan varchar (4000) ,
	AllowRole varchar (4000) ,
	Memo varchar (400) ,
	AddTime datetime not null,
	AddUser varchar (50) not null)
go
if exists(select * from zwstep) exec ('insert into zwstep_TMP (AddUser,AddTime,Memo,AllowRole,AllowOrgan,AllowUser,Operators,State,FinishTime,StartTime,Owner,PreviousStepID,NodeID,DataVersionID,InstanceID,WorkflowID,ID) select AddUser,AddTime,Memo,AllowRole,AllowOrgan,AllowUser,Operators,State,FinishTime,StartTime,Owner,PreviousStepID,NodeID,DataVersionID,InstanceID,WorkflowID,ID from zwstep')
go
drop table zwstep
go
sp_rename 'zwstep_TMP', 'zwstep', 'OBJECT'
go
alter table zwstep add constraint PK_zwstep primary key  NONCLUSTERED(ID)
go
create index idx50_step on zwstep (InstanceID)
go
create index idx51_step on zwstep (NodeID)
go
create index idx52_step on zwstep (owner)
go
if exists (select 1 from  sysobjects where id = object_id('bzwstep_TMP') and type='U') drop table bzwstep_TMP
go
create table bzwstep_TMP(
	ID bigint not null,
	WorkflowID bigint not null,
	InstanceID bigint not null,
	DataVersionID varchar (30) ,
	NodeID int ,
	ActionID int ,
	PreviousStepID bigint ,
	StartTime datetime ,
	FinishTime datetime ,
	State varchar (10) ,
	Operators varchar (400) ,
	AllowUser varchar (4000) ,
	AllowOrgan varchar (4000) ,
	AllowRole varchar (4000) ,
	Memo varchar (400) ,
	AddTime datetime not null,
	AddUser varchar (50) not null,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzwstep) exec ('insert into bzwstep_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,AddUser,AddTime,Memo,AllowRole,AllowOrgan,AllowUser,Operators,State,FinishTime,StartTime,PreviousStepID,NodeID,DataVersionID,InstanceID,WorkflowID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,AddUser,AddTime,Memo,AllowRole,AllowOrgan,AllowUser,Operators,State,FinishTime,StartTime,PreviousStepID,NodeID,DataVersionID,InstanceID,WorkflowID,ID from bzwstep')
go
drop table bzwstep
go
sp_rename 'bzwstep_TMP', 'bzwstep', 'OBJECT'
go
alter table bzwstep add constraint PK_bzwstep primary key  NONCLUSTERED(ID,BackupNo)
go

/*站点增加允许投稿的设置项 2010-02-05*/
if exists (select 1 from  sysobjects where id = object_id('zcsite_TMP') and type='U') drop table zcsite_TMP
go
create table zcsite_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	Alias varchar (100) not null,
	Info varchar (100) ,
	BranchInnerCode varchar (100) not null,
	URL varchar (100) not null,
	RootPath varchar (100) ,
	IndexTemplate varchar (100) ,
	ListTemplate varchar (100) ,
	DetailTemplate varchar (100) ,
	EditorCss varchar (100) ,
	Workflow varchar (100) ,
	OrderFlag bigint ,
	LogoFileName varchar (100) ,
	MessageBoardFlag varchar (2) ,
	CommentAuditFlag varchar (1) ,
	ChannelCount bigint not null,
	MagzineCount bigint not null,
	SpecialCount bigint not null,
	ImageLibCount bigint not null,
	VideoLibCount bigint not null,
	AudioLibCount bigint not null,
	AttachmentLibCount bigint not null,
	ArticleCount bigint not null,
	HitCount bigint not null,
	ConfigXML text ,
	AutoIndexFlag varchar (2) ,
	AutoStatFlag varchar (2) ,
	HeaderTemplate varchar (100) ,
	TopTemplate varchar (100) ,
	BottomTemplate varchar (100) ,
	AllowContribute varchar (2) ,
	Prop1 varchar (100) ,
	Prop2 varchar (100) ,
	Prop3 varchar (100) ,
	Prop4 varchar (100) ,
	Prop5 varchar (100) ,
	Prop6 varchar (100) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zcsite) exec ('insert into zcsite_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,BottomTemplate,TopTemplate,HeaderTemplate,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,BottomTemplate,TopTemplate,HeaderTemplate,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID from zcsite')
go
drop table zcsite
go
sp_rename 'zcsite_TMP', 'zcsite', 'OBJECT'
go
alter table zcsite add constraint PK_zcsite primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('bzcsite_TMP') and type='U') drop table bzcsite_TMP
go
create table bzcsite_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	Alias varchar (100) not null,
	Info varchar (100) ,
	BranchInnerCode varchar (100) not null,
	URL varchar (100) not null,
	RootPath varchar (100) ,
	IndexTemplate varchar (100) ,
	ListTemplate varchar (100) ,
	DetailTemplate varchar (100) ,
	EditorCss varchar (100) ,
	Workflow varchar (100) ,
	OrderFlag bigint ,
	LogoFileName varchar (100) ,
	MessageBoardFlag varchar (2) ,
	CommentAuditFlag varchar (1) ,
	ChannelCount bigint not null,
	MagzineCount bigint not null,
	SpecialCount bigint not null,
	ImageLibCount bigint not null,
	VideoLibCount bigint not null,
	AudioLibCount bigint not null,
	AttachmentLibCount bigint not null,
	ArticleCount bigint not null,
	HitCount bigint not null,
	ConfigXML text ,
	AutoIndexFlag varchar (2) ,
	AutoStatFlag varchar (2) ,
	HeaderTemplate varchar (100) ,
	TopTemplate varchar (100) ,
	BottomTemplate varchar (100) ,
	AllowContribute varchar (2) ,
	Prop1 varchar (100) ,
	Prop2 varchar (100) ,
	Prop3 varchar (100) ,
	Prop4 varchar (100) ,
	Prop5 varchar (100) ,
	Prop6 varchar (100) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcsite) exec ('insert into bzcsite_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,BottomTemplate,TopTemplate,HeaderTemplate,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,BottomTemplate,TopTemplate,HeaderTemplate,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID from bzcsite')
go
drop table bzcsite
go
sp_rename 'bzcsite_TMP', 'bzcsite', 'OBJECT'
go
alter table bzcsite add constraint PK_bzcsite primary key  NONCLUSTERED(ID,BackupNo)
go

/*消息表主题长度不够*/
alter table zcmessage alter column Subject varchar(500)
go
alter table bzcmessage alter column Subject varchar(500)
go

/*站点的配置要将CatalogID设为0，不要设为null 2010-02-06*/
update ZCCatalogConfig set CatalogID=0 where CatalogID is null or CatalogID=''
go

if exists (select 1 from  sysobjects where id = object_id('zcmessage_TMP') and type='U') drop table zcmessage_TMP
go
create table zcmessage_TMP(
	ID bigint not null,
	FromUser varchar (50) ,
	ToUser varchar (50) ,
	Box varchar (10) ,
	ReadFlag bigint ,
	PopFlag bigint ,
	Subject varchar (500) ,
	Content text ,
	AddTime datetime )
go
if exists(select * from zcmessage) exec ('insert into zcmessage_TMP (AddTime,Content,Subject,ReadFlag,Box,ToUser,FromUser,ID) select AddTime,Content,Subject,ReadFlag,Box,ToUser,FromUser,ID from zcmessage')
go
drop table zcmessage
go
sp_rename 'zcmessage_TMP', 'zcmessage', 'OBJECT'
go
alter table zcmessage add constraint PK_zcmessage primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('bzcmessage_TMP') and type='U') drop table bzcmessage_TMP
go
create table bzcmessage_TMP(
	ID bigint not null,
	FromUser varchar (50) ,
	ToUser varchar (50) ,
	Box varchar (10) ,
	ReadFlag bigint ,
	PopFlag bigint ,
	Subject varchar (500) ,
	Content text ,
	AddTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcmessage) exec ('insert into bzcmessage_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,AddTime,Content,Subject,ReadFlag,Box,ToUser,FromUser,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,AddTime,Content,Subject,ReadFlag,Box,ToUser,FromUser,ID from bzcmessage')
go
drop table bzcmessage
go
sp_rename 'bzcmessage_TMP', 'bzcmessage', 'OBJECT'
go
alter table bzcmessage add constraint PK_bzcmessage primary key  NONCLUSTERED(ID,BackupNo)
go

/*王少亭漏加了B表,2010-02-06*/
if exists (select 1 from  sysobjects where id = object_id('BZCForum_TMP') and type='U') drop table BZCForum_TMP
go
create table BZCForum_TMP(
	ID bigint not null,
	SiteID bigint ,
	ParentID bigint not null,
	Type varchar (20) not null,
	Name varchar (100) not null,
	Status varchar (2) not null,
	Pic varchar (100) ,
	Visible varchar (2) ,
	Info varchar (1000) ,
	ThemeCount int not null,
	Verify varchar (2) ,
	Locked varchar (2) ,
	UnLockID varchar (300) ,
	AllowTheme varchar (2) ,
	EditPost varchar (2) ,
	ReplyPost varchar (2) ,
	Recycle varchar (2) ,
	AllowHTML varchar (2) ,
	AllowFace varchar (2) ,
	AnonyPost varchar (2) ,
	URL varchar (200) ,
	Image varchar (200) ,
	Password varchar (100) ,
	UnPasswordID varchar (300) ,
	Word varchar (200) ,
	PostCount int not null,
	ForumAdmin varchar (100) ,
	TodayPostCount int ,
	LastThemeID bigint ,
	LastPost varchar (100) ,
	LastPoster varchar (100) ,
	OrderFlag bigint not null,
	prop1 varchar (50) ,
	prop2 varchar (50) ,
	prop3 varchar (50) ,
	prop4 varchar (50) ,
	AddUser varchar (100) not null,
	AddTime datetime not null,
	ModifyUser varchar (100) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCForum) exec ('insert into BZCForum_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,prop4,prop3,prop2,prop1,OrderFlag,LastPoster,LastPost,LastThemeID,TodayPostCount,ForumAdmin,PostCount,Word,Password,Image,URL,AnonyPost,AllowFace,AllowHTML,Recycle,ReplyPost,EditPost,AllowTheme,Locked,Verify,ThemeCount,Info,Visible,Pic,Status,Name,Type,ParentID,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,prop4,prop3,prop2,prop1,OrderFlag,LastPoster,LastPost,LastThemeID,TodayPostCount,ForumAdmin,PostCount,Word,Password,Image,URL,AnonyPost,AllowFace,AllowHTML,Recycle,ReplyPost,EditPost,AllowTheme,Locked,Verify,ThemeCount,Info,Visible,Pic,Status,Name,Type,ParentID,SiteID,ID from BZCForum')
go
drop table BZCForum
go
sp_rename 'BZCForum_TMP', 'BZCForum', 'OBJECT'
go
alter table BZCForum add constraint PK_BZCForum primary key  NONCLUSTERED(ID,BackupNo)
go

/*谭喜才修改zcstatitem的字段item的长度*/
alter table zcstatitem drop constraint PK_zcstatitem
go
alter table zcstatitem alter column item varchar(200) not null;
go
alter table zcstatitem add constraint PK_zcstatitem primary key NONCLUSTERED(SiteID,Period,Type,SubType,Item)
go

/*修改zdcode,zdconfig两表字段长度，以免UTF8下字符超长*/
alter table zdconfig alter column Name varchar(100)
go
alter table zdconfig alter column Memo varchar(400)
go
alter table zdcode alter column CodeName varchar(100)
go
alter table zdcode alter column Memo varchar(400)
go
alter table bzdconfig alter column Name varchar(100)
go
alter table bzdconfig alter column Memo varchar(400)
go
alter table bzdcode alter column CodeName varchar(100)
go
alter table bzdcode alter column Memo varchar(400)
go

/**增加ZDMemberAddr表，用于会员地址功能**/
if exists (select 1 from  sysobjects where id = object_id('ZDMemberAddr') and type='U') drop table ZDMemberAddr
go

/*==============================================================*/
/* Table: ZDMemberAddr */
/*==============================================================*/
create table ZDMemberAddr(
	ID bigint not null,
	UserName varchar (200) not null,
	RealName varchar (100) ,
	Country varchar (30) ,
	Province varchar (6) ,
	City varchar (6) ,
	District varchar (6) ,
	Address varchar (255) ,
	ZipCode varchar (10) ,
	Tel varchar (20) ,
	Mobile varchar (20) ,
	Email varchar (100) ,
	IsDefault varchar (2) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZDMemberAddr primary key nonclustered (ID)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZDMemberAddr') and type='U') drop table BZDMemberAddr
go

/*==============================================================*/
/* Table: BZDMemberAddr */
/*==============================================================*/
create table BZDMemberAddr(
	ID bigint not null,
	UserName varchar (200) not null,
	RealName varchar (100) ,
	Country varchar (30) ,
	Province varchar (6) ,
	City varchar (6) ,
	District varchar (6) ,
	Address varchar (255) ,
	ZipCode varchar (10) ,
	Tel varchar (20) ,
	Mobile varchar (20) ,
	Email varchar (100) ,
	IsDefault varchar (2) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZDMemberAddr primary key nonclustered (ID,BackupNo)
)
go

/*===========================商品添加折扣和优惠价格字段 by 李伟仪===================================*/
if exists (select 1 from  sysobjects where id = object_id('ZSGoods_TMP') and type='U') drop table ZSGoods_TMP
go
create table ZSGoods_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BrandID bigint ,
	BranchInnerCode varchar (100) ,
	Type varchar (2) not null,
	SN varchar (50) ,
	Name varchar (100) not null,
	Alias varchar (100) ,
	BarCode varchar (128) ,
	WorkFlowID bigint ,
	Status varchar (20) ,
	Factory varchar (100) ,
	OrderFlag bigint ,
	MarketPrice numeric (12,2) ,
	Price numeric (12,2) ,
	Discount numeric (12,2) ,
	OfferPrice numeric (12,2) ,
	MemberPrice numeric (12,2) ,
	VIPPrice numeric (12,2) ,
	EffectDate datetime ,
	Store bigint not null,
	Standard varchar (100) ,
	Unit varchar (10) ,
	Score bigint not null,
	CommentCount bigint not null,
	SaleCount bigint not null,
	HitCount bigint not null,
	Image0 varchar (200) ,
	Image1 varchar (200) ,
	Image2 varchar (200) ,
	Image3 varchar (200) ,
	RelativeGoods varchar (100) ,
	Keyword varchar (100) ,
	TopDate datetime ,
	TopFlag varchar (2) not null,
	Content text ,
	Summary varchar (2000) ,
	RedirectURL varchar (200) ,
	Attribute varchar (100) ,
	RecommendGoods varchar (100) ,
	StickTime bigint not null,
	PublishDate datetime ,
	DownlineDate datetime ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from ZSGoods) exec ('insert into ZSGoods_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Memo,DownlineDate,PublishDate,StickTime,RecommendGoods,Attribute,RedirectURL,Summary,Content,TopFlag,TopDate,Keyword,RelativeGoods,Image3,Image2,Image1,Image0,HitCount,SaleCount,CommentCount,Score,Unit,Standard,Store,EffectDate,VIPPrice,MemberPrice,Price,MarketPrice,OrderFlag,Factory,Status,WorkFlowID,BarCode,Alias,Name,SN,Type,BranchInnerCode,BrandID,CatalogInnerCode,CatalogID,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Memo,DownlineDate,PublishDate,StickTime,RecommendGoods,Attribute,RedirectURL,Summary,Content,TopFlag,TopDate,Keyword,RelativeGoods,Image3,Image2,Image1,Image0,HitCount,SaleCount,CommentCount,Score,Unit,Standard,Store,EffectDate,VIPPrice,MemberPrice,Price,MarketPrice,OrderFlag,Factory,Status,WorkFlowID,BarCode,Alias,Name,SN,Type,BranchInnerCode,BrandID,CatalogInnerCode,CatalogID,SiteID,ID from ZSGoods')
go
drop table ZSGoods
go
sp_rename 'ZSGoods_TMP', 'ZSGoods', 'OBJECT'
go
alter table ZSGoods add constraint PK_ZSGoods primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('BZSGoods_TMP') and type='U') drop table BZSGoods_TMP
go
create table BZSGoods_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BrandID bigint ,
	BranchInnerCode varchar (100) ,
	Type varchar (2) not null,
	SN varchar (50) ,
	Name varchar (100) not null,
	Alias varchar (100) ,
	BarCode varchar (128) ,
	WorkFlowID bigint ,
	Status varchar (20) ,
	Factory varchar (100) ,
	OrderFlag bigint ,
	MarketPrice numeric (12,2) ,
	Price numeric (12,2) ,
	Discount numeric (12,2) ,
	OfferPrice numeric (12,2) ,
	MemberPrice numeric (12,2) ,
	VIPPrice numeric (12,2) ,
	EffectDate datetime ,
	Store bigint not null,
	Standard varchar (100) ,
	Unit varchar (10) ,
	Score bigint not null,
	CommentCount bigint not null,
	SaleCount bigint not null,
	HitCount bigint not null,
	Image0 varchar (200) ,
	Image1 varchar (200) ,
	Image2 varchar (200) ,
	Image3 varchar (200) ,
	RelativeGoods varchar (100) ,
	Keyword varchar (100) ,
	TopDate datetime ,
	TopFlag varchar (2) not null,
	Content text ,
	Summary varchar (2000) ,
	RedirectURL varchar (200) ,
	Attribute varchar (100) ,
	RecommendGoods varchar (100) ,
	StickTime bigint not null,
	PublishDate datetime ,
	DownlineDate datetime ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZSGoods) exec ('insert into BZSGoods_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Memo,DownlineDate,PublishDate,StickTime,RecommendGoods,Attribute,RedirectURL,Summary,Content,TopFlag,TopDate,Keyword,RelativeGoods,Image3,Image2,Image1,Image0,HitCount,SaleCount,CommentCount,Score,Unit,Standard,Store,EffectDate,VIPPrice,MemberPrice,Price,MarketPrice,OrderFlag,Factory,Status,WorkFlowID,BarCode,Alias,Name,SN,Type,BranchInnerCode,BrandID,CatalogInnerCode,CatalogID,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Memo,DownlineDate,PublishDate,StickTime,RecommendGoods,Attribute,RedirectURL,Summary,Content,TopFlag,TopDate,Keyword,RelativeGoods,Image3,Image2,Image1,Image0,HitCount,SaleCount,CommentCount,Score,Unit,Standard,Store,EffectDate,VIPPrice,MemberPrice,Price,MarketPrice,OrderFlag,Factory,Status,WorkFlowID,BarCode,Alias,Name,SN,Type,BranchInnerCode,BrandID,CatalogInnerCode,CatalogID,SiteID,ID from BZSGoods')
go
drop table BZSGoods
go
sp_rename 'BZSGoods_TMP', 'BZSGoods', 'OBJECT'
go
alter table BZSGoods add constraint PK_BZSGoods primary key  NONCLUSTERED(ID,BackupNo)
go

/*===========================用户收藏夹表添加站点ID by 李伟仪===================================*/
if exists (select 1 from  sysobjects where id = object_id('ZSFavorite_TMP') and type='U') drop table ZSFavorite_TMP
go
create table ZSFavorite_TMP(
	UserName varchar (200) not null,
	GoodsID bigint not null,
	SiteID bigint ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from ZSFavorite) exec ('insert into ZSFavorite_TMP (ModifyTime,ModifyUser,AddTime,AddUser,GoodsID,UserName) select ModifyTime,ModifyUser,AddTime,AddUser,GoodsID,UserName from ZSFavorite')
go
drop table ZSFavorite
go
sp_rename 'ZSFavorite_TMP', 'ZSFavorite', 'OBJECT'
go
alter table ZSFavorite add constraint PK_ZSFavorite primary key  NONCLUSTERED(UserName,GoodsID)
go

/*===========================删除zdconfig表无用的配置信息 by huanglei 20100319===================================*/
delete from zdconfig where type='Privilege.OwnerType.Role'
go
delete from zdconfig where type='Privilege.OwnerType.User'
go

/*===========================添加商品属性表，记录不同支付方式的特殊属性 by 李伟仪， 2010-03-24===================================*/
if exists (select 1 from  sysobjects where id = object_id('ZSPaymentProp') and type='U') drop table ZSPaymentProp
go

/*==============================================================*/
/* Table: ZSPaymentProp */
/*==============================================================*/
create table ZSPaymentProp(
	ID bigint not null,
	PaymentID bigint not null,
	PropName varchar (200) ,
	PropValue varchar (200) ,
	Memo varchar (1000) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZSPaymentProp primary key nonclustered (ID)
)
go

/*===========================把原来的MemberCode都改成UserName by 黄雷， 2010-03-25===================================*/
if exists (select 1 from  sysobjects where id = object_id('ZSOrder') and type='U') drop table ZSOrder
go

/*==============================================================*/
/* Table: ZSOrder */
/*==============================================================*/
create table ZSOrder(
	ID bigint not null,
	SiteID bigint not null,
	UserName varchar (200) ,
	IsValid varchar (1) ,
	Status varchar (40) ,
	Amount numeric (12,2) not null,
	SendFee numeric (12,2) ,
	OrderAmount numeric (12,2) ,
	Score bigint not null,
	Name varchar (30) ,
	Province varchar (6) ,
	City varchar (6) ,
	District varchar (6) ,
	Address varchar (255) ,
	ZipCode varchar (10) ,
	Tel varchar (20) ,
	Mobile varchar (20) ,
	HasInvoice varchar (1) not null,
	InvoiceTitle varchar (100) ,
	SendBeginDate datetime ,
	SendEndDate datetime ,
	SendTimeSlice varchar (40) ,
	SendInfo varchar (200) ,
	SendType varchar (40) ,
	PaymentType varchar (40) ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZSOrder primary key nonclustered (ID)
)
go

if exists (select 1 from  sysobjects where id = object_id('ZSOrderItem') and type='U') drop table ZSOrderItem
go

/*==============================================================*/
/* Table: ZSOrderItem */
/*==============================================================*/
create table ZSOrderItem(
	OrderID bigint not null,
	GoodsID bigint not null,
	SiteID bigint not null,
	UserName varchar (200) ,
	SN varchar (50) ,
	Name varchar (200) ,
	Price numeric (12,2) ,
	Discount numeric (12,2) ,
	DiscountPrice numeric (12,2) ,
	Count bigint ,
	Amount numeric ,
	Score bigint ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZSOrderItem primary key nonclustered (OrderID,GoodsID)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZSOrder') and type='U') drop table BZSOrder
go

/*==============================================================*/
/* Table: BZSOrder */
/*==============================================================*/
create table BZSOrder(
	ID bigint not null,
	SiteID bigint not null,
	UserName varchar (200) ,
	IsValid varchar (1) ,
	Status varchar (40) ,
	Amount numeric (12,2) not null,
	SendFee numeric (12,2) ,
	OrderAmount numeric (12,2) ,
	Score bigint not null,
	Name varchar (30) ,
	Province varchar (6) ,
	City varchar (6) ,
	District varchar (6) ,
	Address varchar (255) ,
	ZipCode varchar (10) ,
	Tel varchar (20) ,
	Mobile varchar (20) ,
	HasInvoice varchar (1) not null,
	InvoiceTitle varchar (100) ,
	SendBeginDate datetime ,
	SendEndDate datetime ,
	SendTimeSlice varchar (40) ,
	SendInfo varchar (200) ,
	SendType varchar (40) ,
	PaymentType varchar (40) ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSOrder primary key nonclustered (ID,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZSOrderItem') and type='U') drop table BZSOrderItem
go

/*==============================================================*/
/* Table: BZSOrderItem */
/*==============================================================*/
create table BZSOrderItem(
	OrderID bigint not null,
	GoodsID bigint not null,
	SiteID bigint not null,
	UserName varchar (200) ,
	SN varchar (50) ,
	Name varchar (200) ,
	Price numeric (12,2) ,
	Discount numeric (12,2) ,
	DiscountPrice numeric (12,2) ,
	Count bigint ,
	Amount numeric ,
	Score bigint ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSOrderItem primary key nonclustered (OrderID,GoodsID,BackupNo)
)
go

/*===========================支付方式表添加ImagePath字段 by 李伟仪， 2010-03-25===================================*/
if exists (select 1 from  sysobjects where id = object_id('ZSPayment_TMP') and type='U') drop table ZSPayment_TMP
go
create table ZSPayment_TMP(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) ,
	Info varchar (1000) ,
	IsVisible varchar (1) ,
	ImagePath varchar (200) ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from ZSPayment) exec ('insert into ZSPayment_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Memo,IsVisible,Info,Name,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Memo,IsVisible,Info,Name,SiteID,ID from ZSPayment')
go
drop table ZSPayment
go
sp_rename 'ZSPayment_TMP', 'ZSPayment', 'OBJECT'
go
alter table ZSPayment add constraint PK_ZSPayment primary key  NONCLUSTERED(ID)
go

/*================工作流步骤表的权限相关字段长段加大================*/
alter table zwstep alter column AllowUser varchar(4000)
go
alter table zwstep alter column AllowOrgan varchar(4000)
go
alter table zwstep alter column AllowRole varchar(4000)
go
alter table bzwstep alter column AllowUser varchar(4000)
go
alter table bzwstep alter column AllowOrgan varchar(4000)
go
alter table bzwstep alter column AllowRole varchar(4000)
go

/** 2010-04-02 增加广告、留言板、投票跟栏目关联的功能字段 **/
if exists (select 1 from  sysobjects where id = object_id('zcadposition_TMP') and type='U') drop table zcadposition_TMP
go
create table zcadposition_TMP(
	ID bigint not null,
	SiteID bigint not null,
	PositionName varchar (100) not null,
	Code varchar (50) not null,
	Description text ,
	PositionType varchar (20) ,
	PaddingTop bigint ,
	PaddingLeft bigint ,
	PositionWidth bigint ,
	PositionHeight bigint ,
	Align varchar (2) ,
	Scroll varchar (2) ,
	JsName varchar (100) ,
	RelaCatalogID bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zcadposition) exec ('insert into zcadposition_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,JsName,Scroll,Align,PositionHeight,PositionWidth,PaddingLeft,PaddingTop,PositionType,Description,Code,PositionName,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,JsName,Scroll,Align,PositionHeight,PositionWidth,PaddingLeft,PaddingTop,PositionType,Description,Code,PositionName,SiteID,ID from zcadposition')
go
drop table zcadposition
go
sp_rename 'zcadposition_TMP', 'zcadposition', 'OBJECT'
go
alter table zcadposition add constraint PK_zcadposition primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('bzcadposition_TMP') and type='U') drop table bzcadposition_TMP
go
create table bzcadposition_TMP(
	ID bigint not null,
	SiteID bigint not null,
	PositionName varchar (100) not null,
	Code varchar (50) not null,
	Description text ,
	PositionType varchar (20) ,
	PaddingTop bigint ,
	PaddingLeft bigint ,
	PositionWidth bigint ,
	PositionHeight bigint ,
	Align varchar (2) ,
	Scroll varchar (2) ,
	JsName varchar (100) ,
	RelaCatalogID bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcadposition) exec ('insert into bzcadposition_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,JsName,Scroll,Align,PositionHeight,PositionWidth,PaddingLeft,PaddingTop,PositionType,Description,Code,PositionName,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,JsName,Scroll,Align,PositionHeight,PositionWidth,PaddingLeft,PaddingTop,PositionType,Description,Code,PositionName,SiteID,ID from bzcadposition')
go
drop table bzcadposition
go
sp_rename 'bzcadposition_TMP', 'bzcadposition', 'OBJECT'
go
alter table bzcadposition add constraint PK_bzcadposition primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('ZCMessageBoard_TMP') and type='U') drop table ZCMessageBoard_TMP
go
create table ZCMessageBoard_TMP(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (100) not null,
	IsOpen varchar (2) not null,
	Description varchar (500) ,
	MessageCount varchar (20) ,
	RelaCatalogID bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from ZCMessageBoard) exec ('insert into ZCMessageBoard_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,MessageCount,Description,IsOpen,Name,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,MessageCount,Description,IsOpen,Name,SiteID,ID from ZCMessageBoard')
go
drop table ZCMessageBoard
go
sp_rename 'ZCMessageBoard_TMP', 'ZCMessageBoard', 'OBJECT'
go
alter table ZCMessageBoard add constraint PK_ZCMessageBoard primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('BZCMessageBoard_TMP') and type='U') drop table BZCMessageBoard_TMP
go
create table BZCMessageBoard_TMP(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (100) not null,
	IsOpen varchar (2) not null,
	Description varchar (500) ,
	MessageCount varchar (20) ,
	RelaCatalogID bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCMessageBoard) exec ('insert into BZCMessageBoard_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,MessageCount,Description,IsOpen,Name,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,MessageCount,Description,IsOpen,Name,SiteID,ID from BZCMessageBoard')
go
drop table BZCMessageBoard
go
sp_rename 'BZCMessageBoard_TMP', 'BZCMessageBoard', 'OBJECT'
go
alter table BZCMessageBoard add constraint PK_BZCMessageBoard primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('ZCVote_TMP') and type='U') drop table ZCVote_TMP
go
create table ZCVote_TMP(
	ID bigint not null,
	Code varchar (100) ,
	SiteID bigint not null,
	Title varchar (100) not null,
	Total bigint not null,
	StartTime datetime not null,
	EndTime datetime ,
	IPLimit varchar (1) not null,
	VerifyFlag varchar (1) not null,
	Width int ,
	RelaCatalogID bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from ZCVote) exec ('insert into ZCVote_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Width,VerifyFlag,IPLimit,EndTime,StartTime,Total,Title,SiteID,Code,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Width,VerifyFlag,IPLimit,EndTime,StartTime,Total,Title,SiteID,Code,ID from ZCVote')
go
drop table ZCVote
go
sp_rename 'ZCVote_TMP', 'ZCVote', 'OBJECT'
go
alter table ZCVote add constraint PK_ZCVote primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('BZCVote_TMP') and type='U') drop table BZCVote_TMP
go
create table BZCVote_TMP(
	ID bigint not null,
	Code varchar (100) ,
	SiteID bigint not null,
	Title varchar (100) not null,
	Total bigint not null,
	StartTime datetime not null,
	EndTime datetime ,
	IPLimit varchar (1) not null,
	VerifyFlag varchar (1) not null,
	Width int ,
	RelaCatalogID bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCVote) exec ('insert into BZCVote_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Width,VerifyFlag,IPLimit,EndTime,StartTime,Total,Title,SiteID,Code,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,Width,VerifyFlag,IPLimit,EndTime,StartTime,Total,Title,SiteID,Code,ID from BZCVote')
go
drop table BZCVote
go
sp_rename 'BZCVote_TMP', 'BZCVote', 'OBJECT'
go
alter table BZCVote add constraint PK_BZCVote primary key  NONCLUSTERED(ID,BackupNo)
go

/*2010-04-02 为媒体文件增加发布状态字段 wyuch*/
alter table zcvideo drop column Status
go
alter table bzcvideo drop column Status
go

if exists (select 1 from  sysobjects where id = object_id('zcvideo_TMP') and type='U') drop table zcvideo_TMP
go
create table zcvideo_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	OldName varchar (100) not null,
	SiteID bigint not null,
	Tag varchar (100) ,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (50) ,
	Path varchar (100) not null,
	FileName varchar (100) not null,
	SrcFileName varchar (100) not null,
	Suffix varchar (10) not null,
	IsOriginal varchar (1) ,
	Info varchar (500) ,
	System varchar (20) ,
	FileSize varchar (20) ,
	PublishDate datetime ,
	ImageName varchar (100) ,
	Count bigint not null,
	Width bigint not null,
	Height bigint not null,
	Duration bigint not null,
	ProduceTime datetime ,
	Author varchar (100) ,
	Integral bigint ,
	OrderFlag bigint not null,
	HitCount bigint not null,
	StickTime bigint not null,
	SourceURL varchar (500) ,
	Status bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime )
go
if exists(select * from zcvideo) exec ('insert into zcvideo_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,SourceURL,StickTime,HitCount,OrderFlag,Integral,Author,ProduceTime,Duration,Height,Width,Count,ImageName,PublishDate,FileSize,System,Info,IsOriginal,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,Tag,SiteID,OldName,Name,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,SourceURL,StickTime,HitCount,OrderFlag,Integral,Author,ProduceTime,Duration,Height,Width,Count,ImageName,PublishDate,FileSize,System,Info,IsOriginal,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,Tag,SiteID,OldName,Name,ID from zcvideo')
go
drop table zcvideo
go
sp_rename 'zcvideo_TMP', 'zcvideo', 'OBJECT'
go
alter table zcvideo add constraint PK_zcvideo primary key  NONCLUSTERED(ID)
go
create index idx17_video on zcvideo (CatalogID)
go
create index idx18_video on zcvideo (CatalogInnercode)
go
create index idx19_video on zcvideo (SiteID)
go
create index idx20_video on zcvideo (OrderFlag)
go
create index idx21_video on zcvideo (publishDate)
go
create index idx22_video on zcvideo (addtime)
go
create index idx23_video on zcvideo (modifytime)
go
if exists (select 1 from  sysobjects where id = object_id('zcaudio_TMP') and type='U') drop table zcaudio_TMP
go
create table zcaudio_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	OldName varchar (100) not null,
	SiteID bigint not null,
	Tag varchar (100) ,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (50) ,
	Path varchar (100) not null,
	FileName varchar (100) not null,
	SrcFileName varchar (100) not null,
	Suffix varchar (10) not null,
	IsOriginal varchar (1) ,
	Info varchar (500) ,
	System varchar (20) ,
	FileSize varchar (20) ,
	PublishDate datetime ,
	Duration varchar (20) ,
	ProduceTime datetime ,
	Author varchar (100) ,
	Integral bigint ,
	SourceURL varchar (500) ,
	Status bigint ,
	OrderFlag bigint not null,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime )
go
if exists(select * from zcaudio) exec ('insert into zcaudio_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,OrderFlag,SourceURL,Integral,Author,ProduceTime,Duration,PublishDate,FileSize,System,Info,IsOriginal,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,Tag,SiteID,OldName,Name,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,OrderFlag,SourceURL,Integral,Author,ProduceTime,Duration,PublishDate,FileSize,System,Info,IsOriginal,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,Tag,SiteID,OldName,Name,ID from zcaudio')
go
drop table zcaudio
go
sp_rename 'zcaudio_TMP', 'zcaudio', 'OBJECT'
go
alter table zcaudio add constraint PK_zcaudio primary key  NONCLUSTERED(ID)
go
create index idx24_audio on zcaudio (CatalogID)
go
create index idx25_audio on zcaudio (CatalogInnercode)
go
create index idx26_audio on zcaudio (SiteID)
go
create index idx27_audio on zcaudio (OrderFlag)
go
create index idx28_audio on zcaudio (publishDate)
go
create index idx29_audio on zcaudio (addtime)
go
create index idx30_audio on zcaudio (modifytime)
go
if exists (select 1 from  sysobjects where id = object_id('zcimage_TMP') and type='U') drop table zcimage_TMP
go
create table zcimage_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	OldName varchar (100) not null,
	Tag varchar (100) ,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (50) ,
	Path varchar (100) not null,
	FileName varchar (100) not null,
	SrcFileName varchar (100) not null,
	Suffix varchar (10) not null,
	Width bigint not null,
	Height bigint not null,
	Count bigint not null,
	Info varchar (500) ,
	IsOriginal varchar (1) ,
	FileSize varchar (20) ,
	System varchar (20) ,
	LinkURL varchar (100) ,
	LinkText varchar (100) ,
	ProduceTime datetime ,
	Author varchar (50) ,
	PublishDate datetime ,
	Integral bigint ,
	OrderFlag bigint not null,
	HitCount bigint not null,
	StickTime bigint not null,
	SourceURL varchar (500) ,
	Status bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime )
go
if exists(select * from zcimage) exec ('insert into zcimage_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,SourceURL,StickTime,HitCount,OrderFlag,Integral,PublishDate,Author,ProduceTime,LinkText,LinkURL,System,FileSize,IsOriginal,Info,Count,Height,Width,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,Tag,OldName,Name,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,SourceURL,StickTime,HitCount,OrderFlag,Integral,PublishDate,Author,ProduceTime,LinkText,LinkURL,System,FileSize,IsOriginal,Info,Count,Height,Width,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,Tag,OldName,Name,ID from zcimage')
go
drop table zcimage
go
sp_rename 'zcimage_TMP', 'zcimage', 'OBJECT'
go
alter table zcimage add constraint PK_zcimage primary key  NONCLUSTERED(ID)
go
create index idx10_image on zcimage (CatalogID)
go
create index idx11_image on zcimage (CatalogInnercode)
go
create index idx12_image on zcimage (SiteID)
go
create index idx13_image on zcimage (OrderFlag)
go
create index idx14_image on zcimage (publishDate)
go
create index idx15_image on zcimage (addtime)
go
create index idx16_image on zcimage (modifytime)
go
if exists (select 1 from  sysobjects where id = object_id('zcattachment_TMP') and type='U') drop table zcattachment_TMP
go
create table zcattachment_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	OldName varchar (100) not null,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (50) ,
	Path varchar (100) not null,
	FileName varchar (100) not null,
	SrcFileName varchar (100) not null,
	Suffix varchar (10) not null,
	Info varchar (500) ,
	FileSize varchar (20) ,
	System varchar (20) ,
	PublishDate datetime ,
	Integral bigint ,
	IsLocked varchar (5) not null,
	Password varchar (50) ,
	SourceURL varchar (200) ,
	Status bigint ,
	OrderFlag bigint not null,
	ImagePath varchar (100) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime )
go
if exists(select * from zcattachment) exec ('insert into zcattachment_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,ImagePath,OrderFlag,SourceURL,Password,IsLocked,Integral,PublishDate,System,FileSize,Info,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,OldName,Name,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,ImagePath,OrderFlag,SourceURL,Password,IsLocked,Integral,PublishDate,System,FileSize,Info,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,OldName,Name,ID from zcattachment')
go
drop table zcattachment
go
sp_rename 'zcattachment_TMP', 'zcattachment', 'OBJECT'
go
alter table zcattachment add constraint PK_zcattachment primary key  NONCLUSTERED(ID)
go
create index idx31_attachment on zcattachment (CatalogID)
go
create index idx32_attachment on zcattachment (CatalogInnercode)
go
create index idx33_attachment on zcattachment (SiteID)
go
create index idx34_attachment on zcattachment (OrderFlag)
go
create index idx35_attachment on zcattachment (publishDate)
go
create index idx36_attachment on zcattachment (addtime)
go
create index idx37_attachment on zcattachment (modifytime)
go
if exists (select 1 from  sysobjects where id = object_id('bzcvideo_TMP') and type='U') drop table bzcvideo_TMP
go
create table bzcvideo_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	OldName varchar (100) not null,
	SiteID bigint not null,
	Tag varchar (100) ,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (50) ,
	Path varchar (100) not null,
	FileName varchar (100) not null,
	SrcFileName varchar (100) not null,
	Suffix varchar (10) not null,
	IsOriginal varchar (1) ,
	Info varchar (500) ,
	System varchar (20) ,
	FileSize varchar (20) ,
	PublishDate datetime ,
	ImageName varchar (100) ,
	Count bigint not null,
	Width bigint not null,
	Height bigint not null,
	Duration bigint not null,
	ProduceTime datetime ,
	Author varchar (100) ,
	Integral bigint ,
	OrderFlag bigint not null,
	HitCount bigint not null,
	StickTime bigint not null,
	SourceURL varchar (500) ,
	Status bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcvideo) exec ('insert into bzcvideo_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,SourceURL,StickTime,HitCount,OrderFlag,Integral,Author,ProduceTime,Duration,Height,Width,Count,ImageName,PublishDate,FileSize,System,Info,IsOriginal,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,Tag,SiteID,OldName,Name,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,SourceURL,StickTime,HitCount,OrderFlag,Integral,Author,ProduceTime,Duration,Height,Width,Count,ImageName,PublishDate,FileSize,System,Info,IsOriginal,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,Tag,SiteID,OldName,Name,ID from bzcvideo')
go
drop table bzcvideo
go
sp_rename 'bzcvideo_TMP', 'bzcvideo', 'OBJECT'
go
alter table bzcvideo add constraint PK_bzcvideo primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('bzcaudio_TMP') and type='U') drop table bzcaudio_TMP
go
create table bzcaudio_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	OldName varchar (100) not null,
	SiteID bigint not null,
	Tag varchar (100) ,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (50) ,
	Path varchar (100) not null,
	FileName varchar (100) not null,
	SrcFileName varchar (100) not null,
	Suffix varchar (10) not null,
	IsOriginal varchar (1) ,
	Info varchar (500) ,
	System varchar (20) ,
	FileSize varchar (20) ,
	PublishDate datetime ,
	Duration varchar (20) ,
	ProduceTime datetime ,
	Author varchar (100) ,
	Integral bigint ,
	SourceURL varchar (500) ,
	Status bigint ,
	OrderFlag bigint not null,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcaudio) exec ('insert into bzcaudio_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,OrderFlag,SourceURL,Integral,Author,ProduceTime,Duration,PublishDate,FileSize,System,Info,IsOriginal,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,Tag,SiteID,OldName,Name,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,OrderFlag,SourceURL,Integral,Author,ProduceTime,Duration,PublishDate,FileSize,System,Info,IsOriginal,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,Tag,SiteID,OldName,Name,ID from bzcaudio')
go
drop table bzcaudio
go
sp_rename 'bzcaudio_TMP', 'bzcaudio', 'OBJECT'
go
alter table bzcaudio add constraint PK_bzcaudio primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('bzcimage_TMP') and type='U') drop table bzcimage_TMP
go
create table bzcimage_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	OldName varchar (100) not null,
	Tag varchar (100) ,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (50) ,
	Path varchar (100) not null,
	FileName varchar (100) not null,
	SrcFileName varchar (100) not null,
	Suffix varchar (10) not null,
	Width bigint not null,
	Height bigint not null,
	Count bigint not null,
	Info varchar (500) ,
	IsOriginal varchar (1) ,
	FileSize varchar (20) ,
	System varchar (20) ,
	LinkURL varchar (100) ,
	LinkText varchar (100) ,
	ProduceTime datetime ,
	Author varchar (50) ,
	PublishDate datetime ,
	Integral bigint ,
	OrderFlag bigint not null,
	HitCount bigint not null,
	StickTime bigint not null,
	SourceURL varchar (500) ,
	Status bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcimage) exec ('insert into bzcimage_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,SourceURL,StickTime,HitCount,OrderFlag,Integral,PublishDate,Author,ProduceTime,LinkText,LinkURL,System,FileSize,IsOriginal,Info,Count,Height,Width,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,Tag,OldName,Name,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,SourceURL,StickTime,HitCount,OrderFlag,Integral,PublishDate,Author,ProduceTime,LinkText,LinkURL,System,FileSize,IsOriginal,Info,Count,Height,Width,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,Tag,OldName,Name,ID from bzcimage')
go
drop table bzcimage
go
sp_rename 'bzcimage_TMP', 'bzcimage', 'OBJECT'
go
alter table bzcimage add constraint PK_bzcimage primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('bzcattachment_TMP') and type='U') drop table bzcattachment_TMP
go
create table bzcattachment_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	OldName varchar (100) not null,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (50) ,
	Path varchar (100) not null,
	FileName varchar (100) not null,
	SrcFileName varchar (100) not null,
	Suffix varchar (10) not null,
	Info varchar (500) ,
	FileSize varchar (20) ,
	System varchar (20) ,
	PublishDate datetime ,
	Integral bigint ,
	IsLocked varchar (5) not null,
	Password varchar (50) ,
	SourceURL varchar (200) ,
	Status bigint ,
	OrderFlag bigint not null,
	ImagePath varchar (100) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcattachment) exec ('insert into bzcattachment_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,ImagePath,OrderFlag,SourceURL,Password,IsLocked,Integral,PublishDate,System,FileSize,Info,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,OldName,Name,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,ImagePath,OrderFlag,SourceURL,Password,IsLocked,Integral,PublishDate,System,FileSize,Info,Suffix,SrcFileName,FileName,Path,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,OldName,Name,ID from bzcattachment')
go
drop table bzcattachment
go
sp_rename 'bzcattachment_TMP', 'bzcattachment', 'OBJECT'
go
alter table bzcattachment add constraint PK_bzcattachment primary key  NONCLUSTERED(ID,BackupNo)
go

/*2010-04-06 添加商品收藏夹备份表，商品收藏夹表添加降价提醒标识字段 by 李伟仪*/
if exists (select 1 from  sysobjects where id = object_id('BZSFavorite') and type='U') drop table BZSFavorite
go
/*==============================================================*/
/* Table: BZSFavorite */
/*==============================================================*/
create table BZSFavorite(
	UserName varchar (200) not null,
	GoodsID bigint not null,
	SiteID bigint ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_BZSFavorite primary key nonclustered (UserName,GoodsID)
)
go
if exists (select 1 from  sysobjects where id = object_id('ZSFavorite_TMP') and type='U') drop table ZSFavorite_TMP
go
create table ZSFavorite_TMP(
	UserName varchar (200) not null,
	GoodsID bigint not null,
	SiteID bigint ,
	PriceNoteFlag varchar (2) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from ZSFavorite) exec ('insert into ZSFavorite_TMP (ModifyTime,ModifyUser,AddTime,AddUser,SiteID,GoodsID,UserName) select ModifyTime,ModifyUser,AddTime,AddUser,SiteID,GoodsID,UserName from ZSFavorite')
go
drop table ZSFavorite
go
sp_rename 'ZSFavorite_TMP', 'ZSFavorite', 'OBJECT'
go
alter table ZSFavorite add constraint PK_ZSFavorite primary key  NONCLUSTERED(UserName,GoodsID)
go

/*bzwstep未加Owner字段 by wyuch 2010-04-06*/
if exists (select 1 from  sysobjects where id = object_id('bzwstep_TMP') and type='U') drop table bzwstep_TMP
go
create table bzwstep_TMP(
	ID bigint not null,
	WorkflowID bigint not null,
	InstanceID bigint not null,
	DataVersionID varchar (30) ,
	NodeID int ,
	ActionID int ,
	PreviousStepID bigint ,
	Owner varchar (50) ,
	StartTime datetime ,
	FinishTime datetime ,
	State varchar (10) ,
	Operators varchar (400) ,
	AllowUser varchar (4000) ,
	AllowOrgan varchar (4000) ,
	AllowRole varchar (4000) ,
	Memo varchar (400) ,
	AddTime datetime not null,
	AddUser varchar (50) not null,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzwstep) exec ('insert into bzwstep_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,AddUser,AddTime,Memo,AllowRole,AllowOrgan,AllowUser,Operators,State,FinishTime,StartTime,PreviousStepID,ActionID,NodeID,DataVersionID,InstanceID,WorkflowID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,AddUser,AddTime,Memo,AllowRole,AllowOrgan,AllowUser,Operators,State,FinishTime,StartTime,PreviousStepID,ActionID,NodeID,DataVersionID,InstanceID,WorkflowID,ID from bzwstep')
go
drop table bzwstep
go
sp_rename 'bzwstep_TMP', 'bzwstep', 'OBJECT'
go
alter table bzwstep add constraint PK_bzwstep primary key  NONCLUSTERED(ID,BackupNo)
go

/*huanglei 增加文章投票功能*/
if exists (select 1 from  sysobjects where id = object_id('ZCVote_TMP') and type='U') drop table ZCVote_TMP
go
create table ZCVote_TMP(
	ID bigint not null,
	Code varchar (100) ,
	SiteID bigint not null,
	Title varchar (100) not null,
	Total bigint not null,
	StartTime datetime not null,
	EndTime datetime ,
	IPLimit varchar (1) not null,
	VerifyFlag varchar (1) not null,
	Width int ,
	RelaCatalogID bigint ,
	VoteCatalogID bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from ZCVote) exec ('insert into ZCVote_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,RelaCatalogID,Width,VerifyFlag,IPLimit,EndTime,StartTime,Total,Title,SiteID,Code,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,RelaCatalogID,Width,VerifyFlag,IPLimit,EndTime,StartTime,Total,Title,SiteID,Code,ID from ZCVote')
go
drop table ZCVote
go
sp_rename 'ZCVote_TMP', 'ZCVote', 'OBJECT'
go
alter table ZCVote add constraint PK_ZCVote primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('BZCVote_TMP') and type='U') drop table BZCVote_TMP
go
create table BZCVote_TMP(
	ID bigint not null,
	Code varchar (100) ,
	SiteID bigint not null,
	Title varchar (100) not null,
	Total bigint not null,
	StartTime datetime not null,
	EndTime datetime ,
	IPLimit varchar (1) not null,
	VerifyFlag varchar (1) not null,
	Width int ,
	RelaCatalogID bigint ,
	VoteCatalogID bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCVote) exec ('insert into BZCVote_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,RelaCatalogID,Width,VerifyFlag,IPLimit,EndTime,StartTime,Total,Title,SiteID,Code,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,RelaCatalogID,Width,VerifyFlag,IPLimit,EndTime,StartTime,Total,Title,SiteID,Code,ID from BZCVote')
go
drop table BZCVote
go
sp_rename 'BZCVote_TMP', 'BZCVote', 'OBJECT'
go
alter table BZCVote add constraint PK_BZCVote primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('ZCVoteSubject_TMP') and type='U') drop table ZCVoteSubject_TMP
go
create table ZCVoteSubject_TMP(
	ID bigint not null,
	VoteID bigint not null,
	Type varchar (1) not null,
	Subject varchar (100) not null,
	VoteCatalogID bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) )
go
if exists(select * from ZCVoteSubject) exec ('insert into ZCVoteSubject_TMP (Prop2,Prop1,Subject,Type,VoteID,ID) select Prop2,Prop1,Subject,Type,VoteID,ID from ZCVoteSubject')
go
drop table ZCVoteSubject
go
sp_rename 'ZCVoteSubject_TMP', 'ZCVoteSubject', 'OBJECT'
go
alter table ZCVoteSubject add constraint PK_ZCVoteSubject primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('BZCVoteSubject_TMP') and type='U') drop table BZCVoteSubject_TMP
go
create table BZCVoteSubject_TMP(
	ID bigint not null,
	VoteID bigint not null,
	Type varchar (1) not null,
	Subject varchar (100) not null,
	VoteCatalogID bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCVoteSubject) exec ('insert into BZCVoteSubject_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,Prop2,Prop1,Subject,Type,VoteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,Prop2,Prop1,Subject,Type,VoteID,ID from BZCVoteSubject')
go
drop table BZCVoteSubject
go
sp_rename 'BZCVoteSubject_TMP', 'BZCVoteSubject', 'OBJECT'
go
alter table BZCVoteSubject add constraint PK_BZCVoteSubject primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('ZCVoteItem_TMP') and type='U') drop table ZCVoteItem_TMP
go
create table ZCVoteItem_TMP(
	ID bigint not null,
	SubjectID bigint not null,
	VoteID bigint not null,
	Item varchar (100) not null,
	Score bigint not null,
	ItemType varchar (1) not null,
	VoteDocID bigint )
go
if exists(select * from ZCVoteItem) exec ('insert into ZCVoteItem_TMP (ItemType,Score,Item,VoteID,SubjectID,ID) select ItemType,Score,Item,VoteID,SubjectID,ID from ZCVoteItem')
go
drop table ZCVoteItem
go
sp_rename 'ZCVoteItem_TMP', 'ZCVoteItem', 'OBJECT'
go
alter table ZCVoteItem add constraint PK_ZCVoteItem primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('BZCVoteItem_TMP') and type='U') drop table BZCVoteItem_TMP
go
create table BZCVoteItem_TMP(
	ID bigint not null,
	SubjectID bigint not null,
	VoteID bigint not null,
	Item varchar (100) not null,
	Score bigint not null,
	ItemType varchar (1) not null,
	VoteDocID bigint ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCVoteItem) exec ('insert into BZCVoteItem_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ItemType,Score,Item,VoteID,SubjectID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ItemType,Score,Item,VoteID,SubjectID,ID from BZCVoteItem')
go
drop table BZCVoteItem
go
sp_rename 'BZCVoteItem_TMP', 'BZCVoteItem', 'OBJECT'
go
alter table BZCVoteItem add constraint PK_BZCVoteItem primary key  NONCLUSTERED(ID,BackupNo)
go

if exists (select 1 from  sysobjects where id = object_id('ZCVoteSubject_TMP') and type='U') drop table ZCVoteSubject_TMP
go
create table ZCVoteSubject_TMP(
	ID bigint not null,
	VoteID bigint not null,
	Type varchar (1) not null,
	Subject varchar (100) not null,
	VoteCatalogID bigint ,
	OrderFlag bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) )
go
if exists(select * from ZCVoteSubject) exec ('insert into ZCVoteSubject_TMP (Prop2,Prop1,VoteCatalogID,Subject,Type,VoteID,ID) select Prop2,Prop1,VoteCatalogID,Subject,Type,VoteID,ID from ZCVoteSubject')
go
drop table ZCVoteSubject
go
sp_rename 'ZCVoteSubject_TMP', 'ZCVoteSubject', 'OBJECT'
go
alter table ZCVoteSubject add constraint PK_ZCVoteSubject primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('BZCVoteSubject_TMP') and type='U') drop table BZCVoteSubject_TMP
go
create table BZCVoteSubject_TMP(
	ID bigint not null,
	VoteID bigint not null,
	Type varchar (1) not null,
	Subject varchar (100) not null,
	VoteCatalogID bigint ,
	OrderFlag bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCVoteSubject) exec ('insert into BZCVoteSubject_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,Prop2,Prop1,VoteCatalogID,Subject,Type,VoteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,Prop2,Prop1,VoteCatalogID,Subject,Type,VoteID,ID from BZCVoteSubject')
go
drop table BZCVoteSubject
go
sp_rename 'BZCVoteSubject_TMP', 'BZCVoteSubject', 'OBJECT'
go
alter table BZCVoteSubject add constraint PK_BZCVoteSubject primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('ZCVoteItem_TMP') and type='U') drop table ZCVoteItem_TMP
go
create table ZCVoteItem_TMP(
	ID bigint not null,
	SubjectID bigint not null,
	VoteID bigint not null,
	Item varchar (100) not null,
	Score bigint not null,
	ItemType varchar (1) not null,
	VoteDocID bigint ,
	OrderFlag bigint )
go
if exists(select * from ZCVoteItem) exec ('insert into ZCVoteItem_TMP (VoteDocID,ItemType,Score,Item,VoteID,SubjectID,ID) select VoteDocID,ItemType,Score,Item,VoteID,SubjectID,ID from ZCVoteItem')
go
drop table ZCVoteItem
go
sp_rename 'ZCVoteItem_TMP', 'ZCVoteItem', 'OBJECT'
go
alter table ZCVoteItem add constraint PK_ZCVoteItem primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('BZCVoteItem_TMP') and type='U') drop table BZCVoteItem_TMP
go
create table BZCVoteItem_TMP(
	ID bigint not null,
	SubjectID bigint not null,
	VoteID bigint not null,
	Item varchar (100) not null,
	Score bigint not null,
	ItemType varchar (1) not null,
	VoteDocID bigint ,
	OrderFlag bigint ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from BZCVoteItem) exec ('insert into BZCVoteItem_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,VoteDocID,ItemType,Score,Item,VoteID,SubjectID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,VoteDocID,ItemType,Score,Item,VoteID,SubjectID,ID from BZCVoteItem')
go
drop table BZCVoteItem
go
sp_rename 'BZCVoteItem_TMP', 'BZCVoteItem', 'OBJECT'
go
alter table BZCVoteItem add constraint PK_BZCVoteItem primary key  NONCLUSTERED(ID,BackupNo)
go

/*调查，广告，图片播放器，留言板升级时都需要将RelaCatalogID置为0*/
update ZCAdposition set RelaCatalogID=0
go
update ZCVote set RelaCatalogID=0
go
update ZCMessageBoard set RelaCatalogID=0
go
update ZCImagePlayer set RelaCatalogInnerCode='0'
go

/*增加商城缺失的B表 2010-04-23 by wyuch*/
if exists (select 1 from  sysobjects where id = object_id('BZSShopConfig') and type='U') drop table BZSShopConfig
go
create table BZSShopConfig(
	SiteID bigint not null,
	Name varchar (50) ,
	Info varchar (1024) ,
	prop1 varchar (50) ,
	prop2 varchar (50) ,
	prop3 varchar (50) ,
	prop4 varchar (50) ,
	AddUser varchar (100) not null,
	AddTime datetime not null,
	ModifyUser varchar (100) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSShopConfig primary key nonclustered (SiteID,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZSStore') and type='U') drop table BZSStore
go
create table BZSStore(
	StoreCode varchar (100) not null,
	ParentCode varchar (100) not null,
	Name varchar (100) not null,
	Alias varchar (100) ,
	TreeLevel bigint not null,
	SiteID bigint not null,
	OrderFlag bigint not null,
	URL varchar (100) ,
	Info varchar (2000) ,
	Country varchar (30) ,
	Province varchar (6) ,
	City varchar (6) ,
	District varchar (6) ,
	Address varchar (400) ,
	ZipCode varchar (10) ,
	Tel varchar (20) ,
	Fax varchar (20) ,
	Mobile varchar (30) ,
	Contacter varchar (40) ,
	ContacterEmail varchar (100) ,
	ContacterTel varchar (20) ,
	ContacterMobile varchar (20) ,
	ContacterQQ varchar (20) ,
	ContacterMSN varchar (50) ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSStore primary key nonclustered (StoreCode,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZSFavorite') and type='U') drop table BZSFavorite
go
create table BZSFavorite(
	UserName varchar (200) not null,
	GoodsID bigint not null,
	SiteID bigint ,
	PriceNoteFlag varchar (2) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSFavorite primary key nonclustered (UserName,GoodsID,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZSSend') and type='U') drop table BZSSend
go
create table BZSSend(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) ,
	SendInfo varchar (200) ,
	ArriveInfo varchar (200) ,
	Info varchar (200) ,
	Price numeric (12,2) ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSSend primary key nonclustered (ID,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('ZSStore') and type='U') drop table ZSStore
go
create table ZSStore(
	StoreCode varchar (100) not null,
	ParentCode varchar (100) not null,
	Name varchar (100) not null,
	Alias varchar (100) ,
	TreeLevel bigint not null,
	SiteID bigint not null,
	OrderFlag bigint not null,
	URL varchar (100) ,
	Info varchar (2000) ,
	Country varchar (30) ,
	Province varchar (6) ,
	City varchar (6) ,
	District varchar (6) ,
	Address varchar (400) ,
	ZipCode varchar (10) ,
	Tel varchar (20) ,
	Fax varchar (20) ,
	Mobile varchar (30) ,
	Contacter varchar (40) ,
	ContacterEmail varchar (100) ,
	ContacterTel varchar (20) ,
	ContacterMobile varchar (20) ,
	ContacterQQ varchar (20) ,
	ContacterMSN varchar (50) ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZSStore primary key nonclustered (StoreCode)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZSPayment') and type='U') drop table BZSPayment
go
create table BZSPayment(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) ,
	Info varchar (1000) ,
	IsVisible varchar (1) ,
	ImagePath varchar (200) ,
	Memo varchar (200) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSPayment primary key nonclustered (ID,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZSPaymentProp') and type='U') drop table BZSPaymentProp
go
create table BZSPaymentProp(
	ID bigint not null,
	PaymentID bigint not null,
	PropName varchar (200) ,
	PropValue varchar (200) ,
	Memo varchar (1000) ,
	Prop1 varchar (200) ,
	Prop2 varchar (200) ,
	Prop3 varchar (200) ,
	Prop4 varchar (200) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZSPaymentProp primary key nonclustered (ID,BackupNo)
)
go

/*文章批注字段太短*/
alter table zcarticlelog alter column ActionDetail varchar(2000)
go
alter table bzcarticlelog alter column ActionDetail varchar(2000)
go

/*填补表里的机构编码，统计需要用到 2010-04-26 by huanglei*/
update zccatalog set branchinnercode = (select branchinnercode from zduser where UserName=zccatalog.adduser)
go
update zcarticle set branchinnercode = (select branchinnercode from zduser where UserName=zcarticle.adduser)
go
update zcimage set branchinnercode = (select branchinnercode from zduser where UserName=zcimage.adduser)
go
update zcvideo set branchinnercode = (select branchinnercode from zduser where UserName=zcvideo.adduser)
go
update zcaudio set branchinnercode = (select branchinnercode from zduser where UserName=zcaudio.adduser)
go

/*增加系统内采集/分发相关的表*/
if exists (select 1 from  sysobjects where id = object_id('ZCInnerGather') and type='U') drop table ZCInnerGather
go
create table ZCInnerGather(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) not null,
	CatalogInnerCode varchar (255) not null,
	TargetCatalog varchar (4000) not null,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterInsertStatus bigint ,
	AfterModifyStatus bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZCInnerGather primary key nonclustered (ID)
)
go

if exists (select 1 from  sysobjects where id = object_id('ZCInnerDeploy') and type='U') drop table ZCInnerDeploy
go
create table ZCInnerDeploy(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) not null,
	DeployType varchar (2) not null,
	CatalogInnerCode varchar (200) not null,
	TargetCatalog varchar (4000) not null,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterSyncStatus bigint ,
	AfterModifyStatus bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZCInnerDeploy primary key nonclustered (ID)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZCInnerGather') and type='U') drop table BZCInnerGather
go
create table BZCInnerGather(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) not null,
	CatalogInnerCode varchar (255) not null,
	TargetCatalog varchar (4000) not null,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterInsertStatus bigint ,
	AfterModifyStatus bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZCInnerGather primary key nonclustered (ID,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('BZCInnerDeploy') and type='U') drop table BZCInnerDeploy
go
create table BZCInnerDeploy(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) not null,
	DeployType varchar (2) not null,
	CatalogInnerCode varchar (200) not null,
	TargetCatalog varchar (4000) not null,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterSyncStatus bigint ,
	AfterModifyStatus bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZCInnerDeploy primary key nonclustered (ID,BackupNo)
)
go

/*增加系统内采集/分发相关的栏目配置字段*/
if exists (select 1 from  sysobjects where id = object_id('zccatalogconfig_TMP') and type='U') drop table zccatalogconfig_TMP
go
create table zccatalogconfig_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint ,
	CatalogInnerCode varchar (100) ,
	CronExpression varchar (100) ,
	PlanType varchar (10) ,
	StartTime datetime ,
	IsUsing varchar (2) not null,
	HotWordType bigint ,
	AllowStatus varchar (50) ,
	AfterEditStatus varchar (50) ,
	Encoding varchar (20) ,
	ArchiveTime varchar (10) ,
	AttachDownFlag varchar (2) ,
	AllowInnerDeploy varchar (2) ,
	InnerDeployPassword varchar (255) ,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterSyncStatus bigint ,
	AfterModifyStatus bigint ,
	AllowInnerGather varchar (2) ,
	InnerGatherPassword varchar (255) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zccatalogconfig) exec ('insert into zccatalogconfig_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AttachDownFlag,ArchiveTime,Encoding,AfterEditStatus,AllowStatus,HotWordType,IsUsing,StartTime,PlanType,CronExpression,CatalogInnerCode,CatalogID,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AttachDownFlag,ArchiveTime,Encoding,AfterEditStatus,AllowStatus,HotWordType,IsUsing,StartTime,PlanType,CronExpression,CatalogInnerCode,CatalogID,SiteID,ID from zccatalogconfig')
go
drop table zccatalogconfig
go
sp_rename 'zccatalogconfig_TMP', 'zccatalogconfig', 'OBJECT'
go
alter table zccatalogconfig add constraint PK_zccatalogconfig primary key  NONCLUSTERED(ID)
go

if exists (select 1 from  sysobjects where id = object_id('bzccatalogconfig_TMP') and type='U') drop table bzccatalogconfig_TMP
go
create table bzccatalogconfig_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint ,
	CatalogInnerCode varchar (100) ,
	CronExpression varchar (100) ,
	PlanType varchar (10) ,
	StartTime datetime ,
	IsUsing varchar (2) not null,
	HotWordType bigint ,
	AllowStatus varchar (50) ,
	AfterEditStatus varchar (50) ,
	Encoding varchar (20) ,
	ArchiveTime varchar (10) ,
	AttachDownFlag varchar (2) ,
	AllowInnerDeploy varchar (2) ,
	InnerDeployPassword varchar (255) ,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterSyncStatus bigint ,
	AfterModifyStatus bigint ,
	AllowInnerGather varchar (2) ,
	InnerGatherPassword varchar (255) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzccatalogconfig) exec ('insert into bzccatalogconfig_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AttachDownFlag,ArchiveTime,Encoding,AfterEditStatus,AllowStatus,HotWordType,IsUsing,StartTime,PlanType,CronExpression,CatalogInnerCode,CatalogID,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AttachDownFlag,ArchiveTime,Encoding,AfterEditStatus,AllowStatus,HotWordType,IsUsing,StartTime,PlanType,CronExpression,CatalogInnerCode,CatalogID,SiteID,ID from bzccatalogconfig')
go
drop table bzccatalogconfig
go
sp_rename 'bzccatalogconfig_TMP', 'bzccatalogconfig', 'OBJECT'
go
alter table bzccatalogconfig add constraint PK_bzccatalogconfig primary key  NONCLUSTERED(ID,BackupNo)
go

/**为栏目配置项加上是否 允许机构独立管理 lanjun20100429*/
if exists (select 1 from  sysobjects where id = object_id('bzccatalogconfig_TMP') and type='U') drop table bzccatalogconfig_TMP
go
create table bzccatalogconfig_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint ,
	CatalogInnerCode varchar (100) ,
	CronExpression varchar (100) ,
	PlanType varchar (10) ,
	StartTime datetime ,
	IsUsing varchar (2) not null,
	HotWordType bigint ,
	AllowStatus varchar (50) ,
	AfterEditStatus varchar (50) ,
	Encoding varchar (20) ,
	ArchiveTime varchar (10) ,
	AttachDownFlag varchar (2) ,
	BranchManageFlag varchar (2) ,
	AllowInnerDeploy varchar (2) ,
	InnerDeployPassword varchar (255) ,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterSyncStatus bigint ,
	AfterModifyStatus bigint ,
	AllowInnerGather varchar (2) ,
	InnerGatherPassword varchar (255) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzccatalogconfig) exec ('insert into bzccatalogconfig_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,InnerGatherPassword,AllowInnerGather,AfterModifyStatus,AfterSyncStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,InnerDeployPassword,AllowInnerDeploy,AttachDownFlag,ArchiveTime,Encoding,AfterEditStatus,AllowStatus,HotWordType,IsUsing,StartTime,PlanType,CronExpression,CatalogInnerCode,CatalogID,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,InnerGatherPassword,AllowInnerGather,AfterModifyStatus,AfterSyncStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,InnerDeployPassword,AllowInnerDeploy,AttachDownFlag,ArchiveTime,Encoding,AfterEditStatus,AllowStatus,HotWordType,IsUsing,StartTime,PlanType,CronExpression,CatalogInnerCode,CatalogID,SiteID,ID from bzccatalogconfig')
go
drop table bzccatalogconfig
go
sp_rename 'bzccatalogconfig_TMP', 'bzccatalogconfig', 'OBJECT'
go
alter table bzccatalogconfig add constraint PK_bzccatalogconfig primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('zccatalogconfig_TMP') and type='U') drop table zccatalogconfig_TMP
go
create table zccatalogconfig_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint ,
	CatalogInnerCode varchar (100) ,
	CronExpression varchar (100) ,
	PlanType varchar (10) ,
	StartTime datetime ,
	IsUsing varchar (2) not null,
	HotWordType bigint ,
	AllowStatus varchar (50) ,
	AfterEditStatus varchar (50) ,
	Encoding varchar (20) ,
	ArchiveTime varchar (10) ,
	AttachDownFlag varchar (2) ,
	BranchManageFlag varchar (2) ,
	AllowInnerDeploy varchar (2) ,
	InnerDeployPassword varchar (255) ,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterSyncStatus bigint ,
	AfterModifyStatus bigint ,
	AllowInnerGather varchar (2) ,
	InnerGatherPassword varchar (255) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zccatalogconfig) exec ('insert into zccatalogconfig_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,InnerGatherPassword,AllowInnerGather,AfterModifyStatus,AfterSyncStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,InnerDeployPassword,AllowInnerDeploy,AttachDownFlag,ArchiveTime,Encoding,AfterEditStatus,AllowStatus,HotWordType,IsUsing,StartTime,PlanType,CronExpression,CatalogInnerCode,CatalogID,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,InnerGatherPassword,AllowInnerGather,AfterModifyStatus,AfterSyncStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,InnerDeployPassword,AllowInnerDeploy,AttachDownFlag,ArchiveTime,Encoding,AfterEditStatus,AllowStatus,HotWordType,IsUsing,StartTime,PlanType,CronExpression,CatalogInnerCode,CatalogID,SiteID,ID from zccatalogconfig')
go
drop table zccatalogconfig
go
sp_rename 'zccatalogconfig_TMP', 'zccatalogconfig', 'OBJECT'
go
alter table zccatalogconfig add constraint PK_zccatalogconfig primary key  NONCLUSTERED(ID)
go

/**添加站点是否启用商城、论坛选项 lanjun 20100429*/
if exists (select 1 from  sysobjects where id = object_id('zcsite_TMP') and type='U') drop table zcsite_TMP
go
create table zcsite_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	Alias varchar (100) not null,
	Info varchar (100) ,
	BranchInnerCode varchar (100) not null,
	URL varchar (100) not null,
	RootPath varchar (100) ,
	IndexTemplate varchar (100) ,
	ListTemplate varchar (100) ,
	DetailTemplate varchar (100) ,
	EditorCss varchar (100) ,
	Workflow varchar (100) ,
	OrderFlag bigint ,
	LogoFileName varchar (100) ,
	MessageBoardFlag varchar (2) ,
	CommentAuditFlag varchar (1) ,
	ChannelCount bigint not null,
	MagzineCount bigint not null,
	SpecialCount bigint not null,
	ImageLibCount bigint not null,
	VideoLibCount bigint not null,
	AudioLibCount bigint not null,
	AttachmentLibCount bigint not null,
	ArticleCount bigint not null,
	HitCount bigint not null,
	ConfigXML text ,
	AutoIndexFlag varchar (2) ,
	AutoStatFlag varchar (2) ,
	HeaderTemplate varchar (100) ,
	TopTemplate varchar (100) ,
	BottomTemplate varchar (100) ,
	AllowContribute varchar (2) ,
	BBSEnableFlag varchar (2) ,
	ShopEnableFlag varchar (2) ,
	Prop1 varchar (100) ,
	Prop2 varchar (100) ,
	Prop3 varchar (100) ,
	Prop4 varchar (100) ,
	Prop5 varchar (100) ,
	Prop6 varchar (100) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zcsite) exec ('insert into zcsite_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,AllowContribute,BottomTemplate,TopTemplate,HeaderTemplate,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,AllowContribute,BottomTemplate,TopTemplate,HeaderTemplate,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID from zcsite')
go
drop table zcsite
go
sp_rename 'zcsite_TMP', 'zcsite', 'OBJECT'
go
alter table zcsite add constraint PK_zcsite primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('bzcsite_TMP') and type='U') drop table bzcsite_TMP
go
create table bzcsite_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	Alias varchar (100) not null,
	Info varchar (100) ,
	BranchInnerCode varchar (100) not null,
	URL varchar (100) not null,
	RootPath varchar (100) ,
	IndexTemplate varchar (100) ,
	ListTemplate varchar (100) ,
	DetailTemplate varchar (100) ,
	EditorCss varchar (100) ,
	Workflow varchar (100) ,
	OrderFlag bigint ,
	LogoFileName varchar (100) ,
	MessageBoardFlag varchar (2) ,
	CommentAuditFlag varchar (1) ,
	ChannelCount bigint not null,
	MagzineCount bigint not null,
	SpecialCount bigint not null,
	ImageLibCount bigint not null,
	VideoLibCount bigint not null,
	AudioLibCount bigint not null,
	AttachmentLibCount bigint not null,
	ArticleCount bigint not null,
	HitCount bigint not null,
	ConfigXML text ,
	AutoIndexFlag varchar (2) ,
	AutoStatFlag varchar (2) ,
	HeaderTemplate varchar (100) ,
	TopTemplate varchar (100) ,
	BottomTemplate varchar (100) ,
	AllowContribute varchar (2) ,
	BBSEnableFlag varchar (2) ,
	ShopEnableFlag varchar (2) ,
	Prop1 varchar (100) ,
	Prop2 varchar (100) ,
	Prop3 varchar (100) ,
	Prop4 varchar (100) ,
	Prop5 varchar (100) ,
	Prop6 varchar (100) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcsite) exec ('insert into bzcsite_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,AllowContribute,BottomTemplate,TopTemplate,HeaderTemplate,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,AllowContribute,BottomTemplate,TopTemplate,HeaderTemplate,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID from bzcsite')
go
drop table bzcsite
go
sp_rename 'bzcsite_TMP', 'bzcsite', 'OBJECT'
go
alter table bzcsite add constraint PK_bzcsite primary key  NONCLUSTERED(ID,BackupNo)
go

/**添加Tag表格 修改ZCarticle表加入字段TagWord txc 20100512*/
if exists (select 1 from  sysobjects where id = object_id('ZCTag') and type='U') drop table ZCTag
go

/*==============================================================*/
/* Table: ZCTag */
/*==============================================================*/
create table ZCTag(
	ID bigint not null,
	Tag varchar (100) not null,
	SiteID bigint not null,
	LinkURL varchar (500) ,
	Type varchar (20) ,
	RelaTag varchar (4000) ,
	UsedCount bigint ,
	TagText varchar (400) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZCTag primary key nonclustered (ID)
)
go


if exists (select 1 from  sysobjects where id = object_id('BZCTag') and type='U') drop table BZCTag
go

/*==============================================================*/
/* Table: BZCTag */
/*==============================================================*/
create table BZCTag(
	ID bigint not null,
	Tag varchar (100) not null,
	SiteID bigint not null,
	LinkURL varchar (500) ,
	Type varchar (20) ,
	RelaTag varchar (4000) ,
	UsedCount bigint ,
	TagText varchar (400) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZCTag primary key nonclustered (ID,BackupNo)
)
go

if exists (select 1 from  sysobjects where id = object_id('zcarticle_TMP') and type='U') drop table zcarticle_TMP
go
create table zcarticle_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (100) ,
	Title varchar (200) not null,
	SubTitle varchar (200) ,
	ShortTitle varchar (200) ,
	TitleStyle varchar (100) ,
	ShortTitleStyle varchar (100) ,
	Author varchar (50) ,
	Type varchar (2) not null,
	Attribute varchar (100) ,
	URL varchar (200) ,
	RedirectURL varchar (200) ,
	Status bigint ,
	Summary varchar (2000) ,
	Content text ,
	TopFlag varchar (2) not null,
	TopDate datetime ,
	TemplateFlag varchar (2) not null,
	Template varchar (100) ,
	CommentFlag varchar (2) not null,
	CopyImageFlag varchar (2) ,
	OrderFlag bigint not null,
	ReferName varchar (100) ,
	ReferURL varchar (200) ,
	Keyword varchar (100) ,
	Tag varchar (1000) ,
	RelativeArticle varchar (200) ,
	RecommendArticle varchar (200) ,
	ReferType bigint ,
	ReferSourceID bigint ,
	HitCount bigint not null,
	StickTime bigint not null,
	PublishFlag varchar (2) not null,
	Priority varchar (2) ,
	LockUser varchar (50) ,
	PublishDate datetime ,
	DownlineDate datetime ,
	WorkFlowID bigint ,
	IssueID bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime )
go
if exists(select * from zcarticle) exec ('insert into zcarticle_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,IssueID,WorkFlowID,DownlineDate,PublishDate,LockUser,Priority,PublishFlag,StickTime,HitCount,ReferSourceID,ReferType,RecommendArticle,RelativeArticle,Keyword,ReferURL,ReferName,OrderFlag,CopyImageFlag,CommentFlag,Template,TemplateFlag,TopDate,TopFlag,Content,Summary,Status,RedirectURL,URL,Attribute,Type,Author,ShortTitleStyle,TitleStyle,ShortTitle,SubTitle,Title,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,IssueID,WorkFlowID,DownlineDate,PublishDate,LockUser,Priority,PublishFlag,StickTime,HitCount,ReferSourceID,ReferType,RecommendArticle,RelativeArticle,Keyword,ReferURL,ReferName,OrderFlag,CopyImageFlag,CommentFlag,Template,TemplateFlag,TopDate,TopFlag,Content,Summary,Status,RedirectURL,URL,Attribute,Type,Author,ShortTitleStyle,TitleStyle,ShortTitle,SubTitle,Title,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,ID from zcarticle')
go
drop table zcarticle
go
sp_rename 'zcarticle_TMP', 'zcarticle', 'OBJECT'
go
alter table zcarticle add constraint PK_zcarticle primary key  NONCLUSTERED(ID)
go
create index idx0_article on zcarticle (CatalogID,Status)
go
create index idx1_article on zcarticle (Orderflag)
go
create index idx2_article on zcarticle (publishDate)
go
create index idx3_article on zcarticle (addtime)
go
create index idx4_article on zcarticle (modifytime)
go
create index idx5_article on zcarticle (DownlineDate)
go
create index idx6_article on zcarticle (CatalogInnercode)
go
create index idx7_article on zcarticle (SiteID)
go
create index idx8_article on zcarticle (refersourceid)
go
create index idx9_article on zcarticle (keyword)
go
if exists (select 1 from  sysobjects where id = object_id('bzcarticle_TMP') and type='U') drop table bzcarticle_TMP
go
create table bzcarticle_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (100) ,
	Title varchar (200) not null,
	SubTitle varchar (200) ,
	ShortTitle varchar (200) ,
	TitleStyle varchar (100) ,
	ShortTitleStyle varchar (100) ,
	Author varchar (50) ,
	Type varchar (2) not null,
	Attribute varchar (100) ,
	URL varchar (200) ,
	RedirectURL varchar (200) ,
	Status bigint ,
	Summary varchar (2000) ,
	Content text ,
	TopFlag varchar (2) not null,
	TopDate datetime ,
	TemplateFlag varchar (2) not null,
	Template varchar (100) ,
	CommentFlag varchar (2) not null,
	CopyImageFlag varchar (2) ,
	OrderFlag bigint not null,
	ReferName varchar (100) ,
	ReferURL varchar (200) ,
	Keyword varchar (100) ,
	Tag varchar (1000) ,
	RelativeArticle varchar (200) ,
	RecommendArticle varchar (200) ,
	ReferType bigint ,
	ReferSourceID bigint ,
	HitCount bigint not null,
	StickTime bigint not null,
	PublishFlag varchar (2) not null,
	Priority varchar (2) ,
	LockUser varchar (50) ,
	PublishDate datetime ,
	DownlineDate datetime ,
	WorkFlowID bigint ,
	IssueID bigint ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcarticle) exec ('insert into bzcarticle_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,IssueID,WorkFlowID,DownlineDate,PublishDate,LockUser,Priority,PublishFlag,StickTime,HitCount,ReferSourceID,ReferType,RecommendArticle,RelativeArticle,Keyword,ReferURL,ReferName,OrderFlag,CopyImageFlag,CommentFlag,Template,TemplateFlag,TopDate,TopFlag,Content,Summary,Status,RedirectURL,URL,Attribute,Type,Author,ShortTitleStyle,TitleStyle,ShortTitle,SubTitle,Title,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,IssueID,WorkFlowID,DownlineDate,PublishDate,LockUser,Priority,PublishFlag,StickTime,HitCount,ReferSourceID,ReferType,RecommendArticle,RelativeArticle,Keyword,ReferURL,ReferName,OrderFlag,CopyImageFlag,CommentFlag,Template,TemplateFlag,TopDate,TopFlag,Content,Summary,Status,RedirectURL,URL,Attribute,Type,Author,ShortTitleStyle,TitleStyle,ShortTitle,SubTitle,Title,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,ID from bzcarticle')
go
drop table bzcarticle
go
sp_rename 'bzcarticle_TMP', 'bzcarticle', 'OBJECT'
go
alter table bzcarticle add constraint PK_bzcarticle primary key  NONCLUSTERED(ID,BackupNo)
go

update zctag set UsedCount=0
go

/*为文章增加网站群来源ID，分页标题，引导图三个字段 2010-05-21*/
if exists (select 1 from  sysobjects where id = object_id('zcarticle_TMP') and type='U') drop table zcarticle_TMP
go
create table zcarticle_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (100) ,
	Title varchar (200) not null,
	SubTitle varchar (200) ,
	ShortTitle varchar (200) ,
	TitleStyle varchar (100) ,
	ShortTitleStyle varchar (100) ,
	Author varchar (50) ,
	Type varchar (2) not null,
	Attribute varchar (100) ,
	URL varchar (200) ,
	RedirectURL varchar (200) ,
	Status bigint ,
	Summary varchar (2000) ,
	Content text ,
	TopFlag varchar (2) not null,
	TopDate datetime ,
	TemplateFlag varchar (2) not null,
	Template varchar (100) ,
	CommentFlag varchar (2) not null,
	CopyImageFlag varchar (2) ,
	OrderFlag bigint not null,
	ReferName varchar (100) ,
	ReferURL varchar (200) ,
	Keyword varchar (100) ,
	Tag varchar (1000) ,
	RelativeArticle varchar (200) ,
	RecommendArticle varchar (200) ,
	ReferType bigint ,
	ReferSourceID bigint ,
	HitCount bigint not null,
	StickTime bigint not null,
	PublishFlag varchar (2) not null,
	Priority varchar (2) ,
	LockUser varchar (50) ,
	PublishDate datetime ,
	DownlineDate datetime ,
	WorkFlowID bigint ,
	IssueID bigint ,
	Logo varchar (100) ,
	PageTitle varchar (200) ,
	ClusterSource varchar (200) ,
	ClusterTarget varchar (1000) ,
	ReferTarget varchar (1000) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime )
go
if exists(select * from zcarticle) exec ('insert into zcarticle_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,IssueID,WorkFlowID,DownlineDate,PublishDate,LockUser,Priority,PublishFlag,StickTime,HitCount,ReferSourceID,ReferType,RecommendArticle,RelativeArticle,Tag,Keyword,ReferURL,ReferName,OrderFlag,CopyImageFlag,CommentFlag,Template,TemplateFlag,TopDate,TopFlag,Content,Summary,Status,RedirectURL,URL,Attribute,Type,Author,ShortTitleStyle,TitleStyle,ShortTitle,SubTitle,Title,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,IssueID,WorkFlowID,DownlineDate,PublishDate,LockUser,Priority,PublishFlag,StickTime,HitCount,ReferSourceID,ReferType,RecommendArticle,RelativeArticle,Tag,Keyword,ReferURL,ReferName,OrderFlag,CopyImageFlag,CommentFlag,Template,TemplateFlag,TopDate,TopFlag,Content,Summary,Status,RedirectURL,URL,Attribute,Type,Author,ShortTitleStyle,TitleStyle,ShortTitle,SubTitle,Title,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,ID from zcarticle')
go
drop table zcarticle
go
sp_rename 'zcarticle_TMP', 'zcarticle', 'OBJECT'
go
alter table zcarticle add constraint PK_zcarticle primary key  NONCLUSTERED(ID)
go
create index idx0_article on zcarticle (CatalogID,Status)
go
create index idx1_article on zcarticle (Orderflag)
go
create index idx2_article on zcarticle (publishDate)
go
create index idx3_article on zcarticle (addtime)
go
create index idx4_article on zcarticle (modifytime)
go
create index idx5_article on zcarticle (DownlineDate)
go
create index idx6_article on zcarticle (CatalogInnercode)
go
create index idx7_article on zcarticle (SiteID)
go
create index idx8_article on zcarticle (refersourceid)
go
create index idx9_article on zcarticle (keyword)
go

if exists (select 1 from  sysobjects where id = object_id('bzcarticle_TMP') and type='U') drop table bzcarticle_TMP
go
create table bzcarticle_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (100) ,
	Title varchar (200) not null,
	SubTitle varchar (200) ,
	ShortTitle varchar (200) ,
	TitleStyle varchar (100) ,
	ShortTitleStyle varchar (100) ,
	Author varchar (50) ,
	Type varchar (2) not null,
	Attribute varchar (100) ,
	URL varchar (200) ,
	RedirectURL varchar (200) ,
	Status bigint ,
	Summary varchar (2000) ,
	Content text ,
	TopFlag varchar (2) not null,
	TopDate datetime ,
	TemplateFlag varchar (2) not null,
	Template varchar (100) ,
	CommentFlag varchar (2) not null,
	CopyImageFlag varchar (2) ,
	OrderFlag bigint not null,
	ReferName varchar (100) ,
	ReferURL varchar (200) ,
	Keyword varchar (100) ,
	Tag varchar (1000) ,
	RelativeArticle varchar (200) ,
	RecommendArticle varchar (200) ,
	ReferType bigint ,
	ReferSourceID bigint ,
	HitCount bigint not null,
	StickTime bigint not null,
	PublishFlag varchar (2) not null,
	Priority varchar (2) ,
	LockUser varchar (50) ,
	PublishDate datetime ,
	DownlineDate datetime ,
	WorkFlowID bigint ,
	IssueID bigint ,
	Logo varchar (100) ,
	PageTitle varchar (200) ,
	ClusterSource varchar (200) ,
	ClusterTarget varchar (1000) ,
	ReferTarget varchar (1000) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcarticle) exec ('insert into bzcarticle_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,IssueID,WorkFlowID,DownlineDate,PublishDate,LockUser,Priority,PublishFlag,StickTime,HitCount,ReferSourceID,ReferType,RecommendArticle,RelativeArticle,Tag,Keyword,ReferURL,ReferName,OrderFlag,CopyImageFlag,CommentFlag,Template,TemplateFlag,TopDate,TopFlag,Content,Summary,Status,RedirectURL,URL,Attribute,Type,Author,ShortTitleStyle,TitleStyle,ShortTitle,SubTitle,Title,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,IssueID,WorkFlowID,DownlineDate,PublishDate,LockUser,Priority,PublishFlag,StickTime,HitCount,ReferSourceID,ReferType,RecommendArticle,RelativeArticle,Tag,Keyword,ReferURL,ReferName,OrderFlag,CopyImageFlag,CommentFlag,Template,TemplateFlag,TopDate,TopFlag,Content,Summary,Status,RedirectURL,URL,Attribute,Type,Author,ShortTitleStyle,TitleStyle,ShortTitle,SubTitle,Title,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,ID from bzcarticle')
go
drop table bzcarticle
go
sp_rename 'bzcarticle_TMP', 'bzcarticle', 'OBJECT'
go
alter table bzcarticle add constraint PK_bzcarticle primary key  NONCLUSTERED(ID,BackupNo)
go

/*为站点增加Meta_keyword,Meta_Description两个字段 2010-05-21*/
if exists (select 1 from  sysobjects where id = object_id('zcsite_TMP') and type='U') drop table zcsite_TMP
go
create table zcsite_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	Alias varchar (100) not null,
	Info varchar (100) ,
	BranchInnerCode varchar (100) not null,
	URL varchar (100) not null,
	RootPath varchar (100) ,
	IndexTemplate varchar (100) ,
	ListTemplate varchar (100) ,
	DetailTemplate varchar (100) ,
	EditorCss varchar (100) ,
	Workflow varchar (100) ,
	OrderFlag bigint ,
	LogoFileName varchar (100) ,
	MessageBoardFlag varchar (2) ,
	CommentAuditFlag varchar (1) ,
	ChannelCount bigint not null,
	MagzineCount bigint not null,
	SpecialCount bigint not null,
	ImageLibCount bigint not null,
	VideoLibCount bigint not null,
	AudioLibCount bigint not null,
	AttachmentLibCount bigint not null,
	ArticleCount bigint not null,
	HitCount bigint not null,
	ConfigXML text ,
	AutoIndexFlag varchar (2) ,
	AutoStatFlag varchar (2) ,
	HeaderTemplate varchar (100) ,
	TopTemplate varchar (100) ,
	BottomTemplate varchar (100) ,
	AllowContribute varchar (2) ,
	BBSEnableFlag varchar (2) ,
	ShopEnableFlag varchar (2) ,
	Meta_Keywords varchar (200) ,
	Meta_Description varchar (400) ,
	Prop1 varchar (100) ,
	Prop2 varchar (100) ,
	Prop3 varchar (100) ,
	Prop4 varchar (100) ,
	Prop5 varchar (100) ,
	Prop6 varchar (100) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zcsite) exec ('insert into zcsite_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,ShopEnableFlag,BBSEnableFlag,AllowContribute,BottomTemplate,TopTemplate,HeaderTemplate,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,ShopEnableFlag,BBSEnableFlag,AllowContribute,BottomTemplate,TopTemplate,HeaderTemplate,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID from zcsite')
go
drop table zcsite
go
sp_rename 'zcsite_TMP', 'zcsite', 'OBJECT'
go
alter table zcsite add constraint PK_zcsite primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('bzcsite_TMP') and type='U') drop table bzcsite_TMP
go
create table bzcsite_TMP(
	ID bigint not null,
	Name varchar (100) not null,
	Alias varchar (100) not null,
	Info varchar (100) ,
	BranchInnerCode varchar (100) not null,
	URL varchar (100) not null,
	RootPath varchar (100) ,
	IndexTemplate varchar (100) ,
	ListTemplate varchar (100) ,
	DetailTemplate varchar (100) ,
	EditorCss varchar (100) ,
	Workflow varchar (100) ,
	OrderFlag bigint ,
	LogoFileName varchar (100) ,
	MessageBoardFlag varchar (2) ,
	CommentAuditFlag varchar (1) ,
	ChannelCount bigint not null,
	MagzineCount bigint not null,
	SpecialCount bigint not null,
	ImageLibCount bigint not null,
	VideoLibCount bigint not null,
	AudioLibCount bigint not null,
	AttachmentLibCount bigint not null,
	ArticleCount bigint not null,
	HitCount bigint not null,
	ConfigXML text ,
	AutoIndexFlag varchar (2) ,
	AutoStatFlag varchar (2) ,
	HeaderTemplate varchar (100) ,
	TopTemplate varchar (100) ,
	BottomTemplate varchar (100) ,
	AllowContribute varchar (2) ,
	BBSEnableFlag varchar (2) ,
	ShopEnableFlag varchar (2) ,
	Meta_Keywords varchar (200) ,
	Meta_Description varchar (400) ,
	Prop1 varchar (100) ,
	Prop2 varchar (100) ,
	Prop3 varchar (100) ,
	Prop4 varchar (100) ,
	Prop5 varchar (100) ,
	Prop6 varchar (100) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcsite) exec ('insert into bzcsite_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,ShopEnableFlag,BBSEnableFlag,AllowContribute,BottomTemplate,TopTemplate,HeaderTemplate,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop6,Prop5,Prop4,Prop3,Prop2,Prop1,ShopEnableFlag,BBSEnableFlag,AllowContribute,BottomTemplate,TopTemplate,HeaderTemplate,AutoStatFlag,AutoIndexFlag,ConfigXML,HitCount,ArticleCount,AttachmentLibCount,AudioLibCount,VideoLibCount,ImageLibCount,SpecialCount,MagzineCount,ChannelCount,CommentAuditFlag,MessageBoardFlag,LogoFileName,OrderFlag,Workflow,EditorCss,DetailTemplate,ListTemplate,IndexTemplate,RootPath,URL,BranchInnerCode,Info,Alias,Name,ID from bzcsite')
go
drop table bzcsite
go
sp_rename 'bzcsite_TMP', 'bzcsite', 'OBJECT'
go
alter table bzcsite add constraint PK_bzcsite primary key  NONCLUSTERED(ID,BackupNo)
go


/*为栏目增加网站群来源ID 2010-05-21*/
if exists (select 1 from  sysobjects where id = object_id('zccatalog_TMP') and type='U') drop table zccatalog_TMP
go
create table zccatalog_TMP(
	ID bigint not null,
	ParentID bigint not null,
	SiteID bigint not null,
	Name varchar (100) not null,
	InnerCode varchar (100) not null,
	BranchInnerCode varchar (100) ,
	Alias varchar (100) not null,
	URL varchar (100) ,
	ImagePath varchar (50) ,
	Type bigint not null,
	IndexTemplate varchar (200) ,
	ListTemplate varchar (200) ,
	ListNameRule varchar (200) ,
	DetailTemplate varchar (200) ,
	DetailNameRule varchar (200) ,
	RssTemplate varchar (200) ,
	RssNameRule varchar (200) ,
	Workflow varchar (100) ,
	TreeLevel bigint not null,
	ChildCount bigint not null,
	IsLeaf bigint not null,
	IsDirty bigint ,
	Total bigint not null,
	OrderFlag bigint not null,
	Logo varchar (100) ,
	ListPageSize bigint ,
	ListPage bigint ,
	PublishFlag varchar (2) not null,
	SingleFlag varchar (2) ,
	HitCount bigint ,
	Meta_Keywords varchar (200) ,
	Meta_Description varchar (200) ,
	OrderColumn varchar (20) ,
	Integral bigint ,
	KeywordFlag varchar (2) ,
	KeywordSetting varchar (50) ,
	AllowContribute varchar (2) ,
	ClusterSourceID varchar (200) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zccatalog) exec ('insert into zccatalog_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AllowContribute,KeywordSetting,KeywordFlag,Integral,OrderColumn,Meta_Description,Meta_Keywords,HitCount,SingleFlag,PublishFlag,ListPage,ListPageSize,Logo,OrderFlag,Total,IsDirty,IsLeaf,ChildCount,TreeLevel,Workflow,RssNameRule,RssTemplate,DetailNameRule,DetailTemplate,ListNameRule,ListTemplate,IndexTemplate,Type,ImagePath,URL,Alias,BranchInnerCode,InnerCode,Name,SiteID,ParentID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AllowContribute,KeywordSetting,KeywordFlag,Integral,OrderColumn,Meta_Description,Meta_Keywords,HitCount,SingleFlag,PublishFlag,ListPage,ListPageSize,Logo,OrderFlag,Total,IsDirty,IsLeaf,ChildCount,TreeLevel,Workflow,RssNameRule,RssTemplate,DetailNameRule,DetailTemplate,ListNameRule,ListTemplate,IndexTemplate,Type,ImagePath,URL,Alias,BranchInnerCode,InnerCode,Name,SiteID,ParentID,ID from zccatalog')
go
drop table zccatalog
go
sp_rename 'zccatalog_TMP', 'zccatalog', 'OBJECT'
go
alter table zccatalog add constraint PK_zccatalog primary key  NONCLUSTERED(ID)
go
create index idx42_catalog on zccatalog (SiteID,Type)
go
create index idx43_catalog on zccatalog (InnerCode)
go
if exists (select 1 from  sysobjects where id = object_id('bzccatalog_TMP') and type='U') drop table bzccatalog_TMP
go
create table bzccatalog_TMP(
	ID bigint not null,
	ParentID bigint not null,
	SiteID bigint not null,
	Name varchar (100) not null,
	InnerCode varchar (100) not null,
	BranchInnerCode varchar (100) ,
	Alias varchar (100) not null,
	URL varchar (100) ,
	ImagePath varchar (50) ,
	Type bigint not null,
	IndexTemplate varchar (200) ,
	ListTemplate varchar (200) ,
	ListNameRule varchar (200) ,
	DetailTemplate varchar (200) ,
	DetailNameRule varchar (200) ,
	RssTemplate varchar (200) ,
	RssNameRule varchar (200) ,
	Workflow varchar (100) ,
	TreeLevel bigint not null,
	ChildCount bigint not null,
	IsLeaf bigint not null,
	IsDirty bigint ,
	Total bigint not null,
	OrderFlag bigint not null,
	Logo varchar (100) ,
	ListPageSize bigint ,
	ListPage bigint ,
	PublishFlag varchar (2) not null,
	SingleFlag varchar (2) ,
	HitCount bigint ,
	Meta_Keywords varchar (200) ,
	Meta_Description varchar (200) ,
	OrderColumn varchar (20) ,
	Integral bigint ,
	KeywordFlag varchar (2) ,
	KeywordSetting varchar (50) ,
	AllowContribute varchar (2) ,
	ClusterSourceID varchar (200) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzccatalog) exec ('insert into bzccatalog_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AllowContribute,KeywordSetting,KeywordFlag,Integral,OrderColumn,Meta_Description,Meta_Keywords,HitCount,SingleFlag,PublishFlag,ListPage,ListPageSize,Logo,OrderFlag,Total,IsDirty,IsLeaf,ChildCount,TreeLevel,Workflow,RssNameRule,RssTemplate,DetailNameRule,DetailTemplate,ListNameRule,ListTemplate,IndexTemplate,Type,ImagePath,URL,Alias,BranchInnerCode,InnerCode,Name,SiteID,ParentID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AllowContribute,KeywordSetting,KeywordFlag,Integral,OrderColumn,Meta_Description,Meta_Keywords,HitCount,SingleFlag,PublishFlag,ListPage,ListPageSize,Logo,OrderFlag,Total,IsDirty,IsLeaf,ChildCount,TreeLevel,Workflow,RssNameRule,RssTemplate,DetailNameRule,DetailTemplate,ListNameRule,ListTemplate,IndexTemplate,Type,ImagePath,URL,Alias,BranchInnerCode,InnerCode,Name,SiteID,ParentID,ID from bzccatalog')
go
drop table bzccatalog
go
sp_rename 'bzccatalog_TMP', 'bzccatalog', 'OBJECT'
go
alter table bzccatalog add constraint PK_bzccatalog primary key  NONCLUSTERED(ID,BackupNo)
go

/*修改文章表中一些字段长度 2010-05-21*/
alter table bzcarticle alter column RelativeArticle varchar(200)
go
alter table bzcarticle alter column RecommendArticle varchar(200)
go
alter table bzcarticle alter column LockUser varchar(50)
go
alter table bzcarticle alter column AddUser varchar(50)
go
alter table bzcarticle alter column Prop1 varchar(500)
go

alter table zcarticle alter column RelativeArticle varchar(200)
go
alter table zcarticle alter column RecommendArticle varchar(200)
go
alter table zcarticle alter column LockUser varchar(50)
go
alter table zcarticle alter column AddUser varchar(50)
go
alter table zcarticle alter column Prop1 varchar(500)
go

/*增加系统内采集的功能 2010-05-26*/
if exists (select 1 from  sysobjects where id = object_id('ZCDBGather') and type='U') drop table ZCDBGather
go
create table ZCDBGather(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) not null,
	DatabaseID bigint not null,
	TableName varchar (50) not null,
	CatalogID bigint not null,
	ArticleStatus bigint not null,
	PathReplacePartOld varchar (200) ,
	PathReplacePartNew varchar (200) ,
	NewRecordRule varchar (200) ,
	SQLCondition varchar (200) ,
	Status varchar (2) ,
	MappingConfig text not null,
	Memo varchar (400) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	constraint PK_ZCDBGather primary key nonclustered (ID)
)
go
if exists (select 1 from  sysobjects where id = object_id('BZCDBGather') and type='U') drop table BZCDBGather
go
create table BZCDBGather(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) not null,
	DatabaseID bigint not null,
	TableName varchar (50) not null,
	CatalogID bigint not null,
	ArticleStatus bigint not null,
	PathReplacePartOld varchar (200) ,
	PathReplacePartNew varchar (200) ,
	NewRecordRule varchar (200) ,
	SQLCondition varchar (200) ,
	Status varchar (2) ,
	MappingConfig text not null,
	Memo varchar (400) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) ,
	constraint PK_BZCDBGather primary key nonclustered (ID,BackupNo)
)
go

/*给采集分发任务加上运行状态字段 2010-05-28*/
if exists (select 1 from  sysobjects where id = object_id('zcinnergather_TMP') and type='U') drop table zcinnergather_TMP
go
create table zcinnergather_TMP(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) not null,
	CatalogInnerCode varchar (200) not null,
	TargetCatalog varchar (4000) not null,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterInsertStatus bigint ,
	AfterModifyStatus bigint ,
	Status varchar (2) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zcinnergather) exec ('insert into zcinnergather_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AfterModifyStatus,AfterInsertStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,TargetCatalog,CatalogInnerCode,Name,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AfterModifyStatus,AfterInsertStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,TargetCatalog,CatalogInnerCode,Name,SiteID,ID from zcinnergather')
go
drop table zcinnergather
go
sp_rename 'zcinnergather_TMP', 'zcinnergather', 'OBJECT'
go
alter table zcinnergather add constraint PK_zcinnergather primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('bzcinnergather_TMP') and type='U') drop table bzcinnergather_TMP
go
create table bzcinnergather_TMP(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) not null,
	CatalogInnerCode varchar (200) not null,
	TargetCatalog varchar (4000) not null,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterInsertStatus bigint ,
	AfterModifyStatus bigint ,
	Status varchar (2) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcinnergather) exec ('insert into bzcinnergather_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AfterModifyStatus,AfterInsertStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,TargetCatalog,CatalogInnerCode,Name,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AfterModifyStatus,AfterInsertStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,TargetCatalog,CatalogInnerCode,Name,SiteID,ID from bzcinnergather')
go
drop table bzcinnergather
go
sp_rename 'bzcinnergather_TMP', 'bzcinnergather', 'OBJECT'
go
alter table bzcinnergather add constraint PK_bzcinnergather primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('zcinnerdeploy_TMP') and type='U') drop table zcinnerdeploy_TMP
go
create table zcinnerdeploy_TMP(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) not null,
	DeployType varchar (2) not null,
	CatalogInnerCode varchar (200) not null,
	TargetCatalog varchar (4000) not null,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterSyncStatus bigint ,
	AfterModifyStatus bigint ,
	Status varchar (2) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zcinnerdeploy) exec ('insert into zcinnerdeploy_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AfterModifyStatus,AfterSyncStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,TargetCatalog,CatalogInnerCode,DeployType,Name,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AfterModifyStatus,AfterSyncStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,TargetCatalog,CatalogInnerCode,DeployType,Name,SiteID,ID from zcinnerdeploy')
go
drop table zcinnerdeploy
go
sp_rename 'zcinnerdeploy_TMP', 'zcinnerdeploy', 'OBJECT'
go
alter table zcinnerdeploy add constraint PK_zcinnerdeploy primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('bzcinnerdeploy_TMP') and type='U') drop table bzcinnerdeploy_TMP
go
create table bzcinnerdeploy_TMP(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (200) not null,
	DeployType varchar (2) not null,
	CatalogInnerCode varchar (200) not null,
	TargetCatalog varchar (4000) not null,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterSyncStatus bigint ,
	AfterModifyStatus bigint ,
	Status varchar (2) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcinnerdeploy) exec ('insert into bzcinnerdeploy_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AfterModifyStatus,AfterSyncStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,TargetCatalog,CatalogInnerCode,DeployType,Name,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,AfterModifyStatus,AfterSyncStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,TargetCatalog,CatalogInnerCode,DeployType,Name,SiteID,ID from bzcinnerdeploy')
go
drop table bzcinnerdeploy
go
sp_rename 'bzcinnerdeploy_TMP', 'bzcinnerdeploy', 'OBJECT'
go
alter table bzcinnerdeploy add constraint PK_bzcinnerdeploy primary key  NONCLUSTERED(ID,BackupNo)
go
if exists (select 1 from  sysobjects where id = object_id('zcwebgather_TMP') and type='U') drop table zcwebgather_TMP
go
create table zcwebgather_TMP(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (50) not null,
	Intro varchar (255) ,
	Type varchar (2) not null,
	ConfigXML text not null,
	ProxyFlag varchar (2) ,
	ProxyHost varchar (255) ,
	ProxyPort bigint ,
	ProxyUserName varchar (50) ,
	ProxyPassword varchar (100) ,
	Status varchar (2) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime )
go
if exists(select * from zcwebgather) exec ('insert into zcwebgather_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,ProxyPassword,ProxyUserName,ProxyPort,ProxyHost,ProxyFlag,ConfigXML,Type,Intro,Name,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,ProxyPassword,ProxyUserName,ProxyPort,ProxyHost,ProxyFlag,ConfigXML,Type,Intro,Name,SiteID,ID from zcwebgather')
go
drop table zcwebgather
go
sp_rename 'zcwebgather_TMP', 'zcwebgather', 'OBJECT'
go
alter table zcwebgather add constraint PK_zcwebgather primary key  NONCLUSTERED(ID)
go
if exists (select 1 from  sysobjects where id = object_id('bzcwebgather_TMP') and type='U') drop table bzcwebgather_TMP
go
create table bzcwebgather_TMP(
	ID bigint not null,
	SiteID bigint not null,
	Name varchar (50) not null,
	Intro varchar (255) ,
	Type varchar (2) not null,
	ConfigXML text not null,
	ProxyFlag varchar (2) ,
	ProxyHost varchar (255) ,
	ProxyPort bigint ,
	ProxyUserName varchar (50) ,
	ProxyPassword varchar (100) ,
	Status varchar (2) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcwebgather) exec ('insert into bzcwebgather_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,ProxyPassword,ProxyUserName,ProxyPort,ProxyHost,ProxyFlag,ConfigXML,Type,Intro,Name,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,ProxyPassword,ProxyUserName,ProxyPort,ProxyHost,ProxyFlag,ConfigXML,Type,Intro,Name,SiteID,ID from bzcwebgather')
go
drop table bzcwebgather
go
sp_rename 'bzcwebgather_TMP', 'bzcwebgather', 'OBJECT'
go
alter table bzcwebgather add constraint PK_bzcwebgather primary key  NONCLUSTERED(ID,BackupNo)
go
update zcwebgather set Status='Y'
go
update zcinnergather set Status='Y'
go
update zcinnerdeploy set Status='Y'
go

/*配置是否可以匿名发表评论*/
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('CommentAllowAnonymity','是否可以匿名发表评论','N',NULL,NULL,NULL,NULL,NULL,'2010-01-06 14:35:52','admin',NULL,NULL)
go

/*加入缺失的ZSShopConfig 2010-05-31*/
if exists (select 1 from  sysobjects where id = object_id('ZSShopConfig') and type='U') drop table ZSShopConfig
go
create table ZSShopConfig(
	SiteID bigint not null,
	Name varchar (50) ,
	Info varchar (1024) ,
	prop1 varchar (50) ,
	prop2 varchar (50) ,
	prop3 varchar (50) ,
	prop4 varchar (50) ,
	AddUser varchar (100) not null,
	AddTime datetime not null,
	ModifyUser varchar (100) ,
	ModifyTime datetime ,
	constraint PK_ZSShopConfig primary key nonclustered (SiteID)
)
go


/*加入永不归档的设置 2010-05-31*/
insert into zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,AddTime,AddUser) values ('ArchiveTime','ArchiveTime','0','永不归档','1260418093080','2010-05-31 11:11:11','admin');
go

/*文章表增加归档时间字段 2010-06-02*/
if exists (select 1 from  sysobjects where id = object_id('zcarticle_TMP') and type='U') drop table zcarticle_TMP
go
create table zcarticle_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (100) ,
	Title varchar (200) not null,
	SubTitle varchar (200) ,
	ShortTitle varchar (200) ,
	TitleStyle varchar (100) ,
	ShortTitleStyle varchar (100) ,
	Author varchar (50) ,
	Type varchar (2) not null,
	Attribute varchar (100) ,
	URL varchar (200) ,
	RedirectURL varchar (200) ,
	Status bigint ,
	Summary varchar (2000) ,
	Content text ,
	TopFlag varchar (2) not null,
	TopDate datetime ,
	TemplateFlag varchar (2) not null,
	Template varchar (100) ,
	CommentFlag varchar (2) not null,
	CopyImageFlag varchar (2) ,
	OrderFlag bigint not null,
	ReferName varchar (100) ,
	ReferURL varchar (200) ,
	Keyword varchar (100) ,
	Tag varchar (1000) ,
	RelativeArticle varchar (200) ,
	RecommendArticle varchar (200) ,
	ReferType bigint ,
	ReferSourceID bigint ,
	HitCount bigint not null,
	StickTime bigint not null,
	PublishFlag varchar (2) not null,
	Priority varchar (2) ,
	LockUser varchar (50) ,
	PublishDate datetime ,
	DownlineDate datetime ,
	ArchiveDate datetime ,
	WorkFlowID bigint ,
	IssueID bigint ,
	Logo varchar (100) ,
	PageTitle varchar (200) ,
	ClusterSource varchar (200) ,
	ClusterTarget varchar (1000) ,
	ReferTarget varchar (1000) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime )
go
if exists(select * from zcarticle) exec ('insert into zcarticle_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,ReferTarget,ClusterTarget,ClusterSource,PageTitle,Logo,IssueID,WorkFlowID,DownlineDate,PublishDate,LockUser,Priority,PublishFlag,StickTime,HitCount,ReferSourceID,ReferType,RecommendArticle,RelativeArticle,Tag,Keyword,ReferURL,ReferName,OrderFlag,CopyImageFlag,CommentFlag,Template,TemplateFlag,TopDate,TopFlag,Content,Summary,Status,RedirectURL,URL,Attribute,Type,Author,ShortTitleStyle,TitleStyle,ShortTitle,SubTitle,Title,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,ReferTarget,ClusterTarget,ClusterSource,PageTitle,Logo,IssueID,WorkFlowID,DownlineDate,PublishDate,LockUser,Priority,PublishFlag,StickTime,HitCount,ReferSourceID,ReferType,RecommendArticle,RelativeArticle,Tag,Keyword,ReferURL,ReferName,OrderFlag,CopyImageFlag,CommentFlag,Template,TemplateFlag,TopDate,TopFlag,Content,Summary,Status,RedirectURL,URL,Attribute,Type,Author,ShortTitleStyle,TitleStyle,ShortTitle,SubTitle,Title,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,ID from zcarticle')
go
drop table zcarticle
go
sp_rename 'zcarticle_TMP', 'zcarticle', 'OBJECT'
go
alter table zcarticle add constraint PK_zcarticle primary key  NONCLUSTERED(ID)
go
create index idx0_article on zcarticle (CatalogID,Status)
go
create index idx1_article on zcarticle (Orderflag)
go
create index idx2_article on zcarticle (publishDate)
go
create index idx3_article on zcarticle (addtime)
go
create index idx4_article on zcarticle (modifytime)
go
create index idx5_article on zcarticle (DownlineDate)
go
create index idx6_article on zcarticle (CatalogInnercode)
go
create index idx7_article on zcarticle (SiteID)
go
create index idx8_article on zcarticle (refersourceid)
go
create index idx9_article on zcarticle (keyword)
go
create index idx10_article on zcarticle (ArchiveDate)
go
if exists (select 1 from  sysobjects where id = object_id('bzcarticle_TMP') and type='U') drop table bzcarticle_TMP
go
create table bzcarticle_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint not null,
	CatalogInnerCode varchar (100) not null,
	BranchInnerCode varchar (100) ,
	Title varchar (200) not null,
	SubTitle varchar (200) ,
	ShortTitle varchar (200) ,
	TitleStyle varchar (100) ,
	ShortTitleStyle varchar (100) ,
	Author varchar (50) ,
	Type varchar (2) not null,
	Attribute varchar (100) ,
	URL varchar (200) ,
	RedirectURL varchar (200) ,
	Status bigint ,
	Summary varchar (2000) ,
	Content text ,
	TopFlag varchar (2) not null,
	TopDate datetime ,
	TemplateFlag varchar (2) not null,
	Template varchar (100) ,
	CommentFlag varchar (2) not null,
	CopyImageFlag varchar (2) ,
	OrderFlag bigint not null,
	ReferName varchar (100) ,
	ReferURL varchar (200) ,
	Keyword varchar (100) ,
	Tag varchar (1000) ,
	RelativeArticle varchar (200) ,
	RecommendArticle varchar (200) ,
	ReferType bigint ,
	ReferSourceID bigint ,
	HitCount bigint not null,
	StickTime bigint not null,
	PublishFlag varchar (2) not null,
	Priority varchar (2) ,
	LockUser varchar (50) ,
	PublishDate datetime ,
	DownlineDate datetime ,
	ArchiveDate datetime ,
	WorkFlowID bigint ,
	IssueID bigint ,
	Logo varchar (100) ,
	PageTitle varchar (200) ,
	ClusterSource varchar (200) ,
	ClusterTarget varchar (1000) ,
	ReferTarget varchar (1000) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (50) not null,
	AddTime datetime not null,
	ModifyUser varchar (50) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzcarticle) exec ('insert into bzcarticle_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,ReferTarget,ClusterTarget,ClusterSource,PageTitle,Logo,IssueID,WorkFlowID,DownlineDate,PublishDate,LockUser,Priority,PublishFlag,StickTime,HitCount,ReferSourceID,ReferType,RecommendArticle,RelativeArticle,Tag,Keyword,ReferURL,ReferName,OrderFlag,CopyImageFlag,CommentFlag,Template,TemplateFlag,TopDate,TopFlag,Content,Summary,Status,RedirectURL,URL,Attribute,Type,Author,ShortTitleStyle,TitleStyle,ShortTitle,SubTitle,Title,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,ReferTarget,ClusterTarget,ClusterSource,PageTitle,Logo,IssueID,WorkFlowID,DownlineDate,PublishDate,LockUser,Priority,PublishFlag,StickTime,HitCount,ReferSourceID,ReferType,RecommendArticle,RelativeArticle,Tag,Keyword,ReferURL,ReferName,OrderFlag,CopyImageFlag,CommentFlag,Template,TemplateFlag,TopDate,TopFlag,Content,Summary,Status,RedirectURL,URL,Attribute,Type,Author,ShortTitleStyle,TitleStyle,ShortTitle,SubTitle,Title,BranchInnerCode,CatalogInnerCode,CatalogID,SiteID,ID from bzcarticle')
go
drop table bzcarticle
go
sp_rename 'bzcarticle_TMP', 'bzcarticle', 'OBJECT'
go
alter table bzcarticle add constraint PK_bzcarticle primary key  NONCLUSTERED(ID,BackupNo)
go


/*复制方式代码中缺少SFTP 2010-06-02*/
insert into zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,AddTime,AddUser) values ('DeployMethod','DeployMethod','SFTP','SFTP远程复制','1274171976500','2010-05-31 11:11:11','admin');
go
insert into zdmenu values(396, 125, '网站群采集', '2', 'DataChannel/FromCatalog.jsp', 'Y', 'Icons/icon003a10.gif', 46, '', '', '', '2010-03-22 10:36:00', 'admin', '2010-03-22 10:36:00', 'admin');
go
insert into zdmenu values(397, 120, '文档回收站', '2', 'Document/RecycleBin.jsp', 'Y', 'Icons/icon050a18.gif', 3, '', '', '', '2010-04-06 14:12:31', 'admin', '2010-03-22 10:36:00', 'admin');
go
insert into zdmenu values(403, 125, '网站群分发', '2', 'DataChannel/DeployCatalog.jsp', 'Y', 'Icons/icon003a7.gif', 47, '', '', '', '2010-05-04 21:08:22', 'admin', '2010-03-22 10:36:00', 'admin');
go
insert into zdmenu values(404, 122, 'Tag管理', '2', 'Site/Tag.jsp', 'Y', 'Icons/icon011a1.gif', 21, '', '', '', '2010-05-11 18:46:22', 'admin', '2010-03-22 10:36:00', 'admin');
go
insert into zdmenu values(405, 125, '数据库采集', '2', 'DataChannel/FromDB.jsp', 'Y', 'Icons/icon005a13.gif', 48, '', '', '', '2010-05-27 13:11:58', 'admin', '2010-03-22 10:36:00', 'admin');
go
update zdmaxno set MaxValue='406' where NoType='ZDMenuID';
go

/*ZCCatalogConfig里面增加评论相关配置字段*/
if exists (select 1 from  sysobjects where id = object_id('zccatalogconfig_TMP') and type='U') drop table zccatalogconfig_TMP
go
create table zccatalogconfig_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint ,
	CatalogInnerCode varchar (100) ,
	CronExpression varchar (100) ,
	PlanType varchar (10) ,
	StartTime datetime ,
	IsUsing varchar (2) not null,
	HotWordType bigint ,
	AllowStatus varchar (50) ,
	AfterEditStatus varchar (50) ,
	Encoding varchar (20) ,
	ArchiveTime varchar (10) ,
	AttachDownFlag varchar (2) ,
	BranchManageFlag varchar (2) ,
	AllowInnerDeploy varchar (2) ,
	InnerDeployPassword varchar (255) ,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterSyncStatus bigint ,
	AfterModifyStatus bigint ,
	AllowInnerGather varchar (2) ,
	InnerGatherPassword varchar (255) ,
	AllowComment varchar (2) ,
	CommentAnonymous varchar (2) ,
	CommentVerify varchar (2) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime )
go
if exists(select * from zccatalogconfig) exec ('insert into zccatalogconfig_TMP (ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,InnerGatherPassword,AllowInnerGather,AfterModifyStatus,AfterSyncStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,InnerDeployPassword,AllowInnerDeploy,BranchManageFlag,AttachDownFlag,ArchiveTime,Encoding,AfterEditStatus,AllowStatus,HotWordType,IsUsing,StartTime,PlanType,CronExpression,CatalogInnerCode,CatalogID,SiteID,ID) select ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,InnerGatherPassword,AllowInnerGather,AfterModifyStatus,AfterSyncStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,InnerDeployPassword,AllowInnerDeploy,BranchManageFlag,AttachDownFlag,ArchiveTime,Encoding,AfterEditStatus,AllowStatus,HotWordType,IsUsing,StartTime,PlanType,CronExpression,CatalogInnerCode,CatalogID,SiteID,ID from zccatalogconfig')
go
drop table zccatalogconfig
go
sp_rename 'zccatalogconfig_TMP', 'zccatalogconfig', 'OBJECT'
go
alter table zccatalogconfig add constraint PK_zccatalogconfig primary key  NONCLUSTERED(ID)
go

if exists (select 1 from  sysobjects where id = object_id('bzccatalogconfig_TMP') and type='U') drop table bzccatalogconfig_TMP
go
create table bzccatalogconfig_TMP(
	ID bigint not null,
	SiteID bigint not null,
	CatalogID bigint ,
	CatalogInnerCode varchar (100) ,
	CronExpression varchar (100) ,
	PlanType varchar (10) ,
	StartTime datetime ,
	IsUsing varchar (2) not null,
	HotWordType bigint ,
	AllowStatus varchar (50) ,
	AfterEditStatus varchar (50) ,
	Encoding varchar (20) ,
	ArchiveTime varchar (10) ,
	AttachDownFlag varchar (2) ,
	BranchManageFlag varchar (2) ,
	AllowInnerDeploy varchar (2) ,
	InnerDeployPassword varchar (255) ,
	SyncCatalogInsert varchar (2) ,
	SyncCatalogModify varchar (2) ,
	SyncArticleModify varchar (2) ,
	AfterSyncStatus bigint ,
	AfterModifyStatus bigint ,
	AllowInnerGather varchar (2) ,
	InnerGatherPassword varchar (255) ,
	AllowComment varchar (2) ,
	CommentAnonymous varchar (2) ,
	CommentVerify varchar (2) ,
	Prop1 varchar (50) ,
	Prop2 varchar (50) ,
	Prop3 varchar (50) ,
	Prop4 varchar (50) ,
	AddUser varchar (200) not null,
	AddTime datetime not null,
	ModifyUser varchar (200) ,
	ModifyTime datetime ,
	BackupNo varchar (15) not null,
	BackupOperator varchar (200) not null,
	BackupTime datetime not null,
	BackupMemo varchar (50) )
go
if exists(select * from bzccatalogconfig) exec ('insert into bzccatalogconfig_TMP (BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,InnerGatherPassword,AllowInnerGather,AfterModifyStatus,AfterSyncStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,InnerDeployPassword,AllowInnerDeploy,BranchManageFlag,AttachDownFlag,ArchiveTime,Encoding,AfterEditStatus,AllowStatus,HotWordType,IsUsing,StartTime,PlanType,CronExpression,CatalogInnerCode,CatalogID,SiteID,ID) select BackupMemo,BackupTime,BackupOperator,BackupNo,ModifyTime,ModifyUser,AddTime,AddUser,Prop4,Prop3,Prop2,Prop1,InnerGatherPassword,AllowInnerGather,AfterModifyStatus,AfterSyncStatus,SyncArticleModify,SyncCatalogModify,SyncCatalogInsert,InnerDeployPassword,AllowInnerDeploy,BranchManageFlag,AttachDownFlag,ArchiveTime,Encoding,AfterEditStatus,AllowStatus,HotWordType,IsUsing,StartTime,PlanType,CronExpression,CatalogInnerCode,CatalogID,SiteID,ID from bzccatalogconfig')
go
drop table bzccatalogconfig
go
sp_rename 'bzccatalogconfig_TMP', 'bzccatalogconfig', 'OBJECT'
go
alter table bzccatalogconfig add constraint PK_bzccatalogconfig primary key  NONCLUSTERED(ID,BackupNo)
go

/*企业会员形象图片字段长度由50改为100 2010-06-09*/
alter table zdmembercompanyinfo alter column Pic varchar(100)
go
alter table bzdmembercompanyinfo alter column Pic varchar(100)
go
