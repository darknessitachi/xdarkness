alter session set nls_date_format ='YYYY-MM-DD hh24:Mi:ss';
insert into zdconfig(type,name,value,adduser,addtime) values('FormProcess','表单提交处理页面','/FormProcess.jsp','admin','2009-11-02 12:15:14');
update zcsite set configxml=replace(configxml,'src="Image','src="upload/Image');

drop table BZDMemberAddr cascade constraints;
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
	IsDefault VARCHAR2 (1) ,
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

drop table BZDMemberLevel cascade constraints;
create table BZDMemberLevel(
	ID INTEGER not null,
	Name VARCHAR2 (100) not null,
	Type VARCHAR2 (10) not null,
	Discount number (3,1) not null,
	IsDefault VARCHAR2 (1) not null,
	TreeLevel INTEGER not null,
	Score INTEGER not null,
	IsValidate VARCHAR2 (1) not null,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZDMemberLevel primary key (ID,BackupNo)
);

drop table BZDMember cascade constraints;
create table BZDMember(
	UserName VARCHAR2 (50) not null,
	Password VARCHAR2 (32) not null,
	Email VARCHAR2 (100) not null,
	LevelID INTEGER not null,
	Type VARCHAR2 (10) not null,
	SiteID INTEGER ,
	RealName VARCHAR2 (100) ,
	LastName VARCHAR2 (50) ,
	FirstName VARCHAR2 (50) ,
	Logo VARCHAR2 (100) ,
	Birthday DATE ,
	Sex VARCHAR2 (1) ,
	Info VARCHAR2 (1000) ,
	Education VARCHAR2 (30) ,
	Vocation VARCHAR2 (50) ,
	Interest VARCHAR2 (500) ,
	Score INTEGER ,
	Discount number (5,3) ,
	RegIP VARCHAR2 (16) ,
	RegTime DATE ,
	Status VARCHAR2 (2) not null,
	IsComplete VARCHAR2 (1) ,
	ServiceUser VARCHAR2 (50) ,
	PWQuestion VARCHAR2 (50) ,
	PWAnswer VARCHAR2 (50) ,
	LastIP VARCHAR2 (16) ,
	LastLoginTime DATE ,
	CompanyName VARCHAR2 (50) ,
	MainBis VARCHAR2 (50) ,
	Address VARCHAR2 (100) ,
	ZipCode VARCHAR2 (10) ,
	Tel VARCHAR2 (20) ,
	Mobile VARCHAR2 (250) ,
	Fax VARCHAR2 (20) ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZDMember primary key (UserName,BackupNo)
);

drop table ZDMemberAddr cascade constraints;
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
	IsDefault VARCHAR2 (1) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZDMemberAddr primary key (ID)
);

drop table ZDMemberLevel cascade constraints;
create table ZDMemberLevel(
	ID INTEGER not null,
	Name VARCHAR2 (100) not null,
	Type VARCHAR2 (10) not null,
	Discount number (3,1) not null,
	IsDefault VARCHAR2 (1) not null,
	TreeLevel INTEGER not null,
	Score INTEGER not null,
	IsValidate VARCHAR2 (1) not null,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZDMemberLevel primary key (ID)
);

drop table ZDMember cascade constraints;
create table ZDMember(
	UserName VARCHAR2 (50) not null,
	Password VARCHAR2 (32) not null,
	Email VARCHAR2 (100) not null,
	LevelID INTEGER not null,
	Type VARCHAR2 (10) not null,
	SiteID INTEGER ,
	RealName VARCHAR2 (100) ,
	LastName VARCHAR2 (50) ,
	FirstName VARCHAR2 (50) ,
	Logo VARCHAR2 (100) ,
	Birthday DATE ,
	Sex VARCHAR2 (1) ,
	Info VARCHAR2 (1000) ,
	Education VARCHAR2 (30) ,
	Vocation VARCHAR2 (50) ,
	Interest VARCHAR2 (500) ,
	Score INTEGER ,
	Discount number (5,3) ,
	RegIP VARCHAR2 (16) ,
	RegTime DATE ,
	Status VARCHAR2 (2) not null,
	IsComplete VARCHAR2 (1) ,
	ServiceUser VARCHAR2 (50) ,
	PWQuestion VARCHAR2 (50) ,
	PWAnswer VARCHAR2 (50) ,
	LastIP VARCHAR2 (16) ,
	LastLoginTime DATE ,
	CompanyName VARCHAR2 (50) ,
	MainBis VARCHAR2 (50) ,
	Address VARCHAR2 (100) ,
	ZipCode VARCHAR2 (10) ,
	Tel VARCHAR2 (20) ,
	Mobile VARCHAR2 (250) ,
	Fax VARCHAR2 (20) ,
	constraint PK_ZDMember primary key (UserName)
);

drop table BZDFormColumn cascade constraints;
create table BZDFormColumn(
	ID INTEGER not null,
	FormID INTEGER ,
	Name VARCHAR2 (100) not null,
	Code VARCHAR2 (100) not null,
	SysCode VARCHAR2 (100) ,
	DataType VARCHAR2 (10) not null,
	MaxLength INTEGER ,
	InputType VARCHAR2 (10) not null,
	DefaultValue VARCHAR2 (50) ,
	ListOption VARCHAR2 (1000) ,
	IsMandatory VARCHAR2 (1) not null,
	VerifyType VARCHAR2 (50) ,
	OrderFlag INTEGER ,
	RowSize INTEGER ,
	ColSize INTEGER ,
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
	constraint PK_BZDFormColumn primary key (ID,BackupNo)
);

drop table BZDForm cascade constraints;
create table BZDForm(
	ID INTEGER not null,
	Name VARCHAR2 (100) not null,
	SiteID INTEGER ,
	Code VARCHAR2 (20) not null,
	Content CLOB ,
	Script CLOB ,
	CSS CLOB ,
	Memo VARCHAR2 (100) ,
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
	constraint PK_BZDForm primary key (ID,BackupNo)
);

drop table ZDFormColumn cascade constraints;
create table ZDFormColumn(
	ID INTEGER not null,
	FormID INTEGER ,
	Name VARCHAR2 (100) not null,
	Code VARCHAR2 (100) not null,
	SysCode VARCHAR2 (100) ,
	DataType VARCHAR2 (10) not null,
	MaxLength INTEGER ,
	InputType VARCHAR2 (10) not null,
	DefaultValue VARCHAR2 (50) ,
	ListOption VARCHAR2 (1000) ,
	IsMandatory VARCHAR2 (1) not null,
	VerifyType VARCHAR2 (50) ,
	OrderFlag INTEGER ,
	RowSize INTEGER ,
	ColSize INTEGER ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZDFormColumn primary key (ID)
);

drop table ZDForm cascade constraints;
create table ZDForm(
	ID INTEGER not null,
	Name VARCHAR2 (100) not null,
	SiteID INTEGER ,
	Code VARCHAR2 (20) not null,
	Content CLOB ,
	Script CLOB ,
	CSS CLOB ,
	Memo VARCHAR2 (100) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZDForm primary key (ID)
);

drop table BZCCatalog cascade constraints;
create table BZCCatalog(
	ID INTEGER not null,
	ParentID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (100) not null,
	InnerCode VARCHAR2 (100) not null,
	BranchInnerCode VARCHAR2 (100) ,
	Alias VARCHAR2 (100) not null,
	URL VARCHAR2 (100) ,
	ImagePath VARCHAR2 (50) ,
	Type INTEGER not null,
	IndexTemplate VARCHAR2 (200) ,
	ListTemplate VARCHAR2 (200) ,
	ListNameRule VARCHAR2 (200) ,
	DetailTemplate VARCHAR2 (200) ,
	DetailNameRule VARCHAR2 (200) ,
	RssTemplate VARCHAR2 (200) ,
	RssNameRule VARCHAR2 (200) ,
	Workflow VARCHAR2 (100) ,
	TreeLevel INTEGER not null,
	ChildCount INTEGER not null,
	IsLeaf INTEGER not null,
	IsDirty INTEGER ,
	Total INTEGER not null,
	OrderFlag INTEGER not null,
	Logo VARCHAR2 (100) ,
	ListPageSize INTEGER ,
	PublishFlag VARCHAR2 (2) not null,
	SingleFlag VARCHAR2 (1) ,
	HitCount INTEGER ,
	Meta_Keywords VARCHAR2 (200) ,
	Meta_Description VARCHAR2 (200) ,
	OrderColumn VARCHAR2 (20) ,
	Integral INTEGER ,
	KeywordFlag VARCHAR2 (2) ,
	KeywordSetting VARCHAR2 (50) ,
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
	constraint PK_BZCCatalog primary key (ID,BackupNo)
);

rename ZCCatalog to ZCCatalog_BAK;

drop table ZCCatalog cascade constraints;
create table ZCCatalog(
	ID INTEGER not null,
	ParentID INTEGER not null,
	SiteID INTEGER not null,
	Name VARCHAR2 (100) not null,
	InnerCode VARCHAR2 (100) not null,
	BranchInnerCode VARCHAR2 (100) ,
	Alias VARCHAR2 (100) not null,
	URL VARCHAR2 (100) ,
	ImagePath VARCHAR2 (50) ,
	Type INTEGER not null,
	IndexTemplate VARCHAR2 (200) ,
	ListTemplate VARCHAR2 (200) ,
	ListNameRule VARCHAR2 (200) ,
	DetailTemplate VARCHAR2 (200) ,
	DetailNameRule VARCHAR2 (200) ,
	RssTemplate VARCHAR2 (200) ,
	RssNameRule VARCHAR2 (200) ,
	Workflow VARCHAR2 (100) ,
	TreeLevel INTEGER not null,
	ChildCount INTEGER not null,
	IsLeaf INTEGER not null,
	IsDirty INTEGER ,
	Total INTEGER not null,
	OrderFlag INTEGER not null,
	Logo VARCHAR2 (100) ,
	ListPageSize INTEGER ,
	PublishFlag VARCHAR2 (2) not null,
	SingleFlag VARCHAR2 (1) ,
	HitCount INTEGER ,
	Meta_Keywords VARCHAR2 (200) ,
	Meta_Description VARCHAR2 (200) ,
	OrderColumn VARCHAR2 (20) ,
	Integral INTEGER ,
	KeywordFlag VARCHAR2 (2) ,
	KeywordSetting VARCHAR2 (50) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZCCatalog1 primary key (ID)
);

insert into ZCCatalog(ID,ParentID,SiteID,Name,InnerCode,BranchInnerCode,Alias,URL
	,ImagePath,Type,IndexTemplate,ListTemplate,ListNameRule
	,DetailTemplate,DetailNameRule,RssTemplate,RssNameRule,Workflow,TreeLevel
	,ChildCount,IsLeaf,IsDirty,Total,OrderFlag,Logo,ListPageSize,PublishFlag
	,SingleFlag,HitCount,Meta_Keywords,Meta_Description,OrderColumn
	,Integral,KeywordFlag,KeywordSetting,Prop1,Prop2,Prop3,Prop4
	,AddUser,AddTime,ModifyUser,ModifyTime) select ID
	,ParentID,SiteID,Name,InnerCode,BranchInnerCode,Alias,URL
	,ImagePath,Type,IndexTemplate,ListTemplate,ListNameRule
	,DetailTemplate,DetailNameRule,RssTemplate,RssNameRule,Workflow,TreeLevel
	,ChildCount,IsLeaf,IsDirty,Total,OrderFlag,Logo,ListPageSize,PublishFlag
	,SingleFlag,HitCount,Meta_Keywords,Meta_Description,OrderColumn
	,Integral,'Y','',Prop1,Prop2,Prop3,Prop4
	,AddUser,AddTime,ModifyUser,ModifyTime from ZCCatalog_BAK;
commit;

drop table BZDFavorite cascade constraints;
create table BZDFavorite(
	UserName VARCHAR2 (200) not null,
	DocID INTEGER not null,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZDFavorite primary key (UserName,DocID,BackupNo)
);

drop table ZDFavorite cascade constraints;
create table ZDFavorite(
	UserName VARCHAR2 (200) not null,
	DocID INTEGER not null,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZDFavorite primary key (UserName,DocID)
);

drop table BZCMessageBoard cascade constraints;
create table BZCMessageBoard(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Type VARCHAR2 (20) not null,
	Title VARCHAR2 (100) not null,
	Content CLOB not null,
	PublishFlag VARCHAR2 (1) not null,
	ReplyFlag VARCHAR2 (1) ,
	ReplyContent VARCHAR2 (4000) ,
	Email VARCHAR2 (100) ,
	Tel VARCHAR2 (20) ,
	Mobile VARCHAR2 (20) ,
	Address VARCHAR2 (100) ,
	AttachPath VARCHAR2 (100) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	UserName VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddUserIP VARCHAR2 (50) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZCMessageBoard primary key (ID,BackupNo)
);

drop table ZCMessageBoard cascade constraints;
create table ZCMessageBoard(
	ID INTEGER not null,
	SiteID INTEGER not null,
	Type VARCHAR2 (20) not null,
	Title VARCHAR2 (100) not null,
	Content CLOB not null,
	PublishFlag VARCHAR2 (1) not null,
	ReplyFlag VARCHAR2 (1) ,
	ReplyContent VARCHAR2 (4000) ,
	Email VARCHAR2 (100) ,
	Tel VARCHAR2 (20) ,
	Mobile VARCHAR2 (20) ,
	Address VARCHAR2 (100) ,
	AttachPath VARCHAR2 (100) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	UserName VARCHAR2 (200) ,
	AddUser VARCHAR2 (200) not null,
	AddUserIP VARCHAR2 (50) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZCMessageBoard primary key (ID)
);

drop table BZCAttachment cascade constraints;
create table BZCAttachment(
	ID INTEGER not null,
	Name VARCHAR2 (100) not null,
	OldName VARCHAR2 (100) not null,
	SiteID INTEGER not null,
	CatalogID INTEGER not null,
	CatalogInnerCode VARCHAR2 (100) not null,
	BranchInnerCode VARCHAR2 (50) ,
	Path VARCHAR2 (100) not null,
	FileName VARCHAR2 (100) not null,
	SrcFileName VARCHAR2 (100) not null,
	Suffix VARCHAR2 (10) not null,
	Info VARCHAR2 (500) ,
	FileSize VARCHAR2 (20) ,
	System VARCHAR2 (20) ,
	PublishDate DATE ,
	Integral INTEGER ,
	IsLocked VARCHAR2 (5) not null,
	Password VARCHAR2 (50) ,
	SourceURL VARCHAR2 (200) ,
	OrderFlag INTEGER not null,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (50) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (50) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZCAttachment primary key (ID,BackupNo)
);

rename ZCAttachment to ZCAttachment_BAK;
drop table ZCAttachment cascade constraints;
create table ZCAttachment(
	ID INTEGER not null,
	Name VARCHAR2 (100) not null,
	OldName VARCHAR2 (100) not null,
	SiteID INTEGER not null,
	CatalogID INTEGER not null,
	CatalogInnerCode VARCHAR2 (100) not null,
	BranchInnerCode VARCHAR2 (50) ,
	Path VARCHAR2 (100) not null,
	FileName VARCHAR2 (100) not null,
	SrcFileName VARCHAR2 (100) not null,
	Suffix VARCHAR2 (10) not null,
	Info VARCHAR2 (500) ,
	FileSize VARCHAR2 (20) ,
	System VARCHAR2 (20) ,
	PublishDate DATE ,
	Integral INTEGER ,
	IsLocked VARCHAR2 (5) not null,
	Password VARCHAR2 (50) ,
	SourceURL VARCHAR2 (200) ,
	OrderFlag INTEGER not null,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (50) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (50) ,
	ModifyTime DATE ,
	constraint PK_ZCAttachment1 primary key (ID)
);

insert into ZCAttachment(ID,Name,OldName,SiteID,CatalogID,CatalogInnerCode,BranchInnerCode,Path,FileName
	,SrcFileName,Suffix,Info,FileSize,System,PublishDate,Integral,IsLocked,Password,SourceURL,OrderFlag
	,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime) select ID,Name,OldName,SiteID,CatalogID,CatalogInnerCode,BranchInnerCode,Path,FileName
	,SrcFileName,Suffix,Info,FileSize,System,PublishDate,Integral,IsLocked,Password,SourceURL,ID
	,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from ZCAttachment_BAK;
commit;

drop table BZCAudio cascade constraints;
create table BZCAudio(
	ID INTEGER not null,
	Name VARCHAR2 (100) not null,
	OldName VARCHAR2 (100) not null,
	SiteID INTEGER not null,
	Tag VARCHAR2 (100) ,
	CatalogID INTEGER not null,
	CatalogInnerCode VARCHAR2 (100) not null,
	BranchInnerCode VARCHAR2 (50) ,
	Path VARCHAR2 (100) not null,
	FileName VARCHAR2 (100) not null,
	SrcFileName VARCHAR2 (100) not null,
	Suffix VARCHAR2 (10) not null,
	IsOriginal VARCHAR2 (1) ,
	Info VARCHAR2 (500) ,
	System VARCHAR2 (20) ,
	FileSize VARCHAR2 (20) ,
	PublishDate DATE ,
	Duration VARCHAR2 (20) ,
	ProduceTime DATE ,
	Author VARCHAR2 (100) ,
	Integral INTEGER ,
	SourceURL VARCHAR2 (500) ,
	OrderFlag INTEGER not null,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (50) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (50) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZCAudio primary key (ID,BackupNo)
);

rename ZCAudio to ZCAudio_BAK;
drop table ZCAudio cascade constraints;
create table ZCAudio(
	ID INTEGER not null,
	Name VARCHAR2 (100) not null,
	OldName VARCHAR2 (100) not null,
	SiteID INTEGER not null,
	Tag VARCHAR2 (100) ,
	CatalogID INTEGER not null,
	CatalogInnerCode VARCHAR2 (100) not null,
	BranchInnerCode VARCHAR2 (50) ,
	Path VARCHAR2 (100) not null,
	FileName VARCHAR2 (100) not null,
	SrcFileName VARCHAR2 (100) not null,
	Suffix VARCHAR2 (10) not null,
	IsOriginal VARCHAR2 (1) ,
	Info VARCHAR2 (500) ,
	System VARCHAR2 (20) ,
	FileSize VARCHAR2 (20) ,
	PublishDate DATE ,
	Duration VARCHAR2 (20) ,
	ProduceTime DATE ,
	Author VARCHAR2 (100) ,
	Integral INTEGER ,
	SourceURL VARCHAR2 (500) ,
	OrderFlag INTEGER not null,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (50) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (50) ,
	ModifyTime DATE ,
	constraint PK_ZCAudio1 primary key (ID)
);

insert into ZCAudio(ID,Name,OldName,SiteID,Tag,CatalogID,CatalogInnerCode,BranchInnerCode,Path,FileName
	,SrcFileName,Suffix,IsOriginal,Info,System,FileSize,PublishDate,Duration,ProduceTime,Author,Integral,SourceURL,OrderFlag
	,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime) select ID,Name,OldName,SiteID,Tag
	,CatalogID,CatalogInnerCode,BranchInnerCode,Path,FileName,SrcFileName,Suffix,IsOriginal,Info
	,System,FileSize,PublishDate,Duration,ProduceTime,Author,Integral,SourceURL,ID
	,Prop1,Prop2,Prop3,Prop4,AddUser,AddTime,ModifyUser,ModifyTime from ZCAudio_BAK;
commit;

alter table ZCImage rename column OrderNumber to OrderFlag;
update ZCImage set OrderFlag=ID;
alter table ZCVideo rename column OrderNumber to OrderFlag;
update ZCVideo set OrderFlag=ID;
alter table ZCImage add Tempcol VARCHAR2(20);
update ZCImage set Tempcol=FileSize;
update ZCImage set FileSize=null;
alter table ZCImage modify (FileSize VARCHAR2(20));
update ZCImage set FileSize=Tempcol;
alter table ZCImage drop column Tempcol;

drop table BZCImage cascade constraints;
create table BZCImage(
	ID INTEGER not null,
	Name VARCHAR2 (100) not null,
	OldName VARCHAR2 (100) not null,
	Tag VARCHAR2 (100) ,
	SiteID INTEGER not null,
	CatalogID INTEGER not null,
	CatalogInnerCode VARCHAR2 (100) not null,
	BranchInnerCode VARCHAR2 (50) ,
	Path VARCHAR2 (100) not null,
	FileName VARCHAR2 (100) not null,
	SrcFileName VARCHAR2 (100) not null,
	Suffix VARCHAR2 (10) not null,
	Width INTEGER not null,
	Height INTEGER not null,
	Count INTEGER not null,
	Info VARCHAR2 (500) ,
	IsOriginal VARCHAR2 (1) ,
	FileSize VARCHAR2 (20) ,
	System VARCHAR2 (20) ,
	LinkURL VARCHAR2 (100) ,
	LinkText VARCHAR2 (100) ,
	ProduceTime DATE ,
	Author VARCHAR2 (50) ,
	PublishDate DATE ,
	Integral INTEGER ,
	OrderFlag INTEGER not null,
	HitCount INTEGER not null,
	StickTime INTEGER not null,
	SourceURL VARCHAR2 (500) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (50) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (50) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZCImage primary key (ID,BackupNo)
);

alter table ZCVideo add Tempcol VARCHAR2(20);
update ZCVideo set Tempcol=FileSize;
update ZCVideo set FileSize=null;
alter table ZCVideo modify (FileSize VARCHAR2(20));
update ZCVideo set FileSize=Tempcol;
alter table ZCVideo drop column Tempcol;
drop table BZCVideo cascade constraints;
create table BZCVideo(
	ID INTEGER not null,
	Name VARCHAR2 (100) not null,
	OldName VARCHAR2 (100) not null,
	SiteID INTEGER not null,
	Tag VARCHAR2 (100) ,
	CatalogID INTEGER not null,
	CatalogInnerCode VARCHAR2 (100) not null,
	BranchInnerCode VARCHAR2 (50) ,
	Path VARCHAR2 (100) not null,
	FileName VARCHAR2 (100) not null,
	SrcFileName VARCHAR2 (100) not null,
	Suffix VARCHAR2 (10) not null,
	IsOriginal VARCHAR2 (1) ,
	Info VARCHAR2 (500) ,
	System VARCHAR2 (20) ,
	FileSize VARCHAR2 (20) ,
	PublishDate DATE ,
	ImageName VARCHAR2 (100) ,
	Count INTEGER not null,
	Width INTEGER not null,
	Height INTEGER not null,
	Duration INTEGER not null,
	ProduceTime DATE ,
	Author VARCHAR2 (100) ,
	Integral INTEGER ,
	OrderFlag INTEGER not null,
	HitCount INTEGER not null,
	StickTime INTEGER not null,
	Status VARCHAR2 (5) ,
	SourceURL VARCHAR2 (500) ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	Prop3 VARCHAR2 (50) ,
	Prop4 VARCHAR2 (50) ,
	AddUser VARCHAR2 (50) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (50) ,
	ModifyTime DATE ,
	BackupNo VARCHAR2 (15) not null,
	BackupOperator VARCHAR2 (200) not null,
	BackupTime DATE not null,
	BackupMemo VARCHAR2 (50) ,
	constraint PK_BZCVideo primary key (ID,BackupNo)
);
commit;

rename ZDColumn to ZDColumn_BAK;

drop table ZDColumn cascade constraints;
create table ZDColumn(
	ID INTEGER not null,
	FormID INTEGER ,
	Name VARCHAR2 (100) not null,
	Code VARCHAR2 (100) not null,
	VerifyType VARCHAR2 (2) not null,
	MaxLength INTEGER ,
	InputType VARCHAR2 (2) not null,
	DefaultValue VARCHAR2 (50) ,
	ListOption VARCHAR2 (1000) ,
	HTML CLOB ,
	IsMandatory VARCHAR2 (1) not null,
	OrderFlag INTEGER ,
	RowSize INTEGER ,
	ColSize INTEGER ,
	Prop1 VARCHAR2 (50) ,
	Prop2 VARCHAR2 (50) ,
	AddUser VARCHAR2 (200) not null,
	AddTime DATE not null,
	ModifyUser VARCHAR2 (200) ,
	ModifyTime DATE ,
	constraint PK_ZDColumn1 primary key (ID)
);

insert into ZDColumn(ID,FormID,Name,Code,VerifyType,MaxLength,InputType,DefaultValue,ListOption,HTML
	,IsMandatory,OrderFlag,RowSize,ColSize,Prop1,Prop2,AddUser,AddTime,ModifyUser,ModifyTime) select ID
	,FormID,Name,Code,VerifyType,MaxLength,InputType,DefaultValue,ListOption,''
	,IsMandatory,OrderFlag,RowSize,ColSize,Prop1,Prop2,AddUser,AddTime,ModifyUser,ModifyTime from ZDColumn_BAK;
commit;

drop table BZDColumn cascade constraints;
create table BZDColumn(
	ID INTEGER not null,
	FormID INTEGER ,
	Name VARCHAR2 (100) not null,
	Code VARCHAR2 (100) not null,
	VerifyType VARCHAR2 (2) not null,
	MaxLength INTEGER ,
	InputType VARCHAR2 (2) not null,
	DefaultValue VARCHAR2 (50) ,
	ListOption VARCHAR2 (1000) ,
	HTML CLOB ,
	IsMandatory VARCHAR2 (1) not null,
	OrderFlag INTEGER ,
	RowSize INTEGER ,
	ColSize INTEGER ,
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
	constraint PK_BZDColumn primary key (ID,BackupNo)
);

drop table ZCCatalog_BAK cascade constraints;
drop table ZCAttachment_BAK cascade constraints;
drop table ZCAudio_BAK cascade constraints;
drop table ZCEnterprise cascade constraints;
drop table BZCEnterprise cascade constraints;
drop table ZDColumn_BAK cascade constraints;

//黄雷，栏目innerCode4位变6位
update zccatalog set innercode='00'||substr(innercode,1,4)||'00'||substr(innercode,5,4)||'00'||substr(innercode,9,4)||'00'||substr(innercode,13,4)||'00'||substr(innercode,17,4)||'00'||substr(innercode,21,4) where length(innercode)=24;
update zccatalog set innercode='00'||substr(innercode,1,4)||'00'||substr(innercode,5,4)||'00'||substr(innercode,9,4)||'00'||substr(innercode,13,4)||'00'||substr(innercode,17,4)  where length(innercode)=20;
update zccatalog set innercode='00'||substr(innercode,1,4)||'00'||substr(innercode,5,4)||'00'||substr(innercode,9,4)||'00'||substr(innercode,13,4) where length(innercode)=16;
update zccatalog set innercode='00'||substr(innercode,1,4)||'00'||substr(innercode,5,4)||'00'||substr(innercode,9,4) where length(innercode)=12;
update zccatalog set innercode='00'||substr(innercode,1,4)||'00'||substr(innercode,5,4)  where length(innercode)=8;
update zccatalog set innercode='00'||substr(innercode,1,4) where length(innercode)=4;

update  zcarticle a set a.cataloginnercode=(select b.innercode from zccatalog b where b.id =a.catalogid) where exists (select id  from zccatalog where zccatalog.id=a.catalogid);
update  zcimage a set a.cataloginnercode=(select b.innercode from zccatalog b where b.id =a.catalogid) where exists (select id  from zccatalog where zccatalog.id=a.catalogid);
update  zcvideo a set a.cataloginnercode=(select b.innercode from zccatalog b where b.id =a.catalogid) where exists (select id  from zccatalog where zccatalog.id=a.catalogid);
update  zcaudio a set a.cataloginnercode=(select b.innercode from zccatalog b where b.id =a.catalogid) where exists (select id  from zccatalog where zccatalog.id=a.catalogid);
update  zcattachment a set a.cataloginnercode=(select b.innercode from zccatalog b where b.id =a.catalogid) where exists (select id  from zccatalog where zccatalog.id=a.catalogid);
update  zcdocrela a set a.cataloginnercode=(select b.innercode from zccatalog b where b.id =a.catalogid) where exists (select id  from zccatalog where zccatalog.id=a.catalogid);

update zdprivilege set id='00'||substr(id,1,4)||'00'||substr(id,5,4)||'00'||substr(id,9,4)||'00'||substr(id,13,4)||'00'||substr(id,17,4)||'00'||substr(id,21,4) where length(id)=24 and privtype in ('image','article','video','audio','attach');
update zdprivilege set id='00'||substr(id,1,4)||'00'||substr(id,5,4)||'00'||substr(id,9,4)||'00'||substr(id,13,4)||'00'||substr(id,17,4)  where length(id)=20 and privtype in ('image','article','video','audio','attach');
update zdprivilege set id='00'||substr(id,1,4)||'00'||substr(id,5,4)||'00'||substr(id,9,4)||'00'||substr(id,13,4) where length(id)=16 and privtype in ('image','article','video','audio','attach');
update zdprivilege set id='00'||substr(id,1,4)||'00'||substr(id,5,4)||'00'||substr(id,9,4) where length(id)=12 and privtype in ('image','article','video','audio','attach');
update zdprivilege set id='00'||substr(id,1,4)||'00'||substr(id,5,4)  where length(id)=8 and privtype in ('image','article','video','audio','attach');
update zdprivilege set id='00'||substr(id,1,4) where length(id)=4 and privtype in ('image','article','video','audio','attach');

update zdmaxno set nosubtype='00'||substr(nosubtype,1,4)||'00'||substr(nosubtype,5,4)||'00'||substr(nosubtype,9,4)||'00'||substr(nosubtype,13,4)||'00'||substr(nosubtype,17,4)||'00'||substr(nosubtype,21,4) where notype='CatalogInnerCode' and length(nosubtype)=24;
update zdmaxno set nosubtype='00'||substr(nosubtype,1,4)||'00'||substr(nosubtype,5,4)||'00'||substr(nosubtype,9,4)||'00'||substr(nosubtype,13,4)||'00'||substr(nosubtype,17,4)  where notype='CatalogInnerCode' and length(nosubtype)=20;
update zdmaxno set nosubtype='00'||substr(nosubtype,1,4)||'00'||substr(nosubtype,5,4)||'00'||substr(nosubtype,9,4)||'00'||substr(nosubtype,13,4) where notype='CatalogInnerCode' and length(nosubtype)=16;
update zdmaxno set nosubtype='00'||substr(nosubtype,1,4)||'00'||substr(nosubtype,5,4)||'00'||substr(nosubtype,9,4) where notype='CatalogInnerCode' and length(nosubtype)=12;
update zdmaxno set nosubtype='00'||substr(nosubtype,1,4)||'00'||substr(nosubtype,5,4)  where notype='CatalogInnerCode' and length(nosubtype)=8;
update zdmaxno set nosubtype='00'||substr(nosubtype,1,4) where notype='CatalogInnerCode' and  length(nosubtype)=4;

update zcstatitem set item='00'||substr(item,1,4)||'00'||substr(item,5,4)||'00'||substr(item,9,4)||'00'||substr(item,13,4)||'00'||substr(item,17,4)||'00'||substr(item,21,4) where length(item)=24 and type ='Catalog' and item not like '%Index%';
update zcstatitem set item='00'||substr(item,1,4)||'00'||substr(item,5,4)||'00'||substr(item,9,4)||'00'||substr(item,13,4)||'00'||substr(item,17,4)  where length(item)=20 and type ='Catalog' and item not like '%Index%';
update zcstatitem set item='00'||substr(item,1,4)||'00'||substr(item,5,4)||'00'||substr(item,9,4)||'00'||substr(item,13,4) where length(item)=16 and type ='Catalog' and item not like '%Index%';
update zcstatitem set item='00'||substr(item,1,4)||'00'||substr(item,5,4)||'00'||substr(item,9,4) where length(item)=12 and type ='Catalog' and item not like '%Index%';
update zcstatitem set item='00'||substr(item,1,4)||'00'||substr(item,5,4)  where length(item)=8 and type ='Catalog' and item not like '%Index%';
update zcstatitem set item='00'||substr(item,1,4) where length(item)=4 and type ='Catalog' and item not like '%Index%';

update zcstatitem set item='00'||substr(item,1,4)||'00'||substr(item,5,4)||'00'||substr(item,9,4)||'00'||substr(item,13,4)||'00'||substr(item,17,4)||'00'||substr(item,21,9) where length(item)=29 and type ='Catalog' and item  like '%Index%';
update zcstatitem set item='00'||substr(item,1,4)||'00'||substr(item,5,4)||'00'||substr(item,9,4)||'00'||substr(item,13,4)||'00'||substr(item,17,9)  where length(item)=25 and type ='Catalog' and item  like '%Index%';
update zcstatitem set item='00'||substr(item,1,4)||'00'||substr(item,5,4)||'00'||substr(item,9,4)||'00'||substr(item,13,9) where length(item)=21 and type ='Catalog' and item  like '%Index%';
update zcstatitem set item='00'||substr(item,1,4)||'00'||substr(item,5,4)||'00'||substr(item,9,9) where length(item)=17 and type ='Catalog' and item  like '%Index%';
update zcstatitem set item='00'||substr(item,1,4)||'00'||substr(item,5,9)  where length(item)=13 and type ='Catalog' and item  like '%Index%';
update zcstatitem set item='00'||substr(item,1,9) where length(item)=9 and type ='Catalog' and item  like '%Index%';

commit;
