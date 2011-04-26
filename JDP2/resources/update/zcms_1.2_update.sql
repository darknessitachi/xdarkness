/*王少亭*/
drop table if exists ZDMember;

/*==============================================================*/
/* Table: ZDMember                                              */
/*==============================================================*/
create table ZDMember
(
   UserName                       varchar(50)                    not null,
   Password                       varchar(32)                    not null,
   Name                           varchar(100)                   not null,
   Email                          varchar(100)                   not null,
   Gender                         char(1),
   Type                           char(10),
   SiteID                         bigint(20),
   Logo                           varchar(100),
   Status                         char(1)                        not null,
   Score                          varchar(20),
   Rank                           varchar(50),
   MemberLevel                    varchar(10),
   PWQuestion                     varchar(100),
   PWAnswer                       varchar(100),
   LastLoginIP                    varchar(16),
   LastLoginTime                  datetime,
   RegTime                        datetime,
   RegIP                          varchar(16),
   Prop1                          varchar(100),
   Prop2                          varchar(100),
   Prop3                          varchar(100),
   Prop4                          varchar(100),
   Prop5                          varchar(100),
   Prop6                          varchar(100),
   primary key (UserName)
);

drop table if exists BZDMember;

/*==============================================================*/
/* Table: BZDMember                                             */
/*==============================================================*/
create table BZDMember
(
   UserName                       varchar(50)                    not null,
   Password                       varchar(32)                    not null,
   Name                           varchar(100)                   not null,
   Email                          varchar(100)                   not null,
   Gender                         char(1),
   Type                           char(10),
   SiteID                         bigint(20),
   Logo                           varchar(100),
   Status                         char(1)                        not null,
   Score                          varchar(20),
   Rank                           varchar(50),
   MemberLevel                    varchar(10),
   PWQuestion                     varchar(100),
   PWAnswer                       varchar(100),
   LastLoginIP                    varchar(16),
   LastLoginTime                  datetime,
   RegTime                        datetime,
   RegIP                          varchar(16),
   Prop1                          varchar(100),
   Prop2                          varchar(100),
   Prop3                          varchar(100),
   Prop4                          varchar(100),
   Prop5                          varchar(100),
   Prop6                          varchar(100),
   BackupNo                       varchar(15)                    not null,
   BackupOperator                 varchar(200)                   not null,
   BackupTime                     datetime                       not null,
   BackupMemo                     varchar(50),
   primary key (UserName, BackupNo)
);


drop table if exists ZDMemberPersonInfo;

/*==============================================================*/
/* Table: ZDMemberPersonInfo                                    */
/*==============================================================*/
create table ZDMemberPersonInfo
(
   UserName                       varchar(50)                    not null,
   NickName                       varchar(100),
   Birthday                       varchar(20),
   QQ                             varchar(10),
   MSN                            varchar(50),
   Tel                            varchar(20),
   Mobile                         varchar(20),
   Address                        varchar(100),
   ZipCode                        varchar(10),
   primary key (UserName)
);

drop table if exists ZDMemberCompanyInfo;

/*==============================================================*/
/* Table: ZDMemberCompanyInfo                                   */
/*==============================================================*/
create table ZDMemberCompanyInfo
(
   UserName                       varchar(50)                    not null,
   CompanyName                    varchar(100),
   Scale                          varchar(10),
   BusinessType                   varchar(10),
   Products                       varchar(50),
   CompanySite                    varchar(50),
   Tel                            varchar(20),
   Fax                            varchar(20),
   LinkMan                        varchar(20),
   Mobile                         varchar(20),
   Email                          varchar(50),
   Address                        varchar(100),
   ZipCode                        varchar(10),
   Pic                            varchar(50),
   Intro                          text,
   primary key (UserName)
);

drop table if exists BZDMemberPersonInfo;

/*==============================================================*/
/* Table: BZDMemberPersonInfo                                   */
/*==============================================================*/
create table BZDMemberPersonInfo
(
   UserName                       varchar(50)                    not null,
   NickName                       varchar(100),
   Birthday                       varchar(20),
   QQ                             varchar(10),
   MSN                            varchar(50),
   Tel                            varchar(20),
   Mobile                         varchar(20),
   Address                        varchar(100),
   ZipCode                        varchar(10),
   BackupNo                       varchar(15)                    not null,
   BackupOperator                 varchar(200)                   not null,
   BackupTime                     datetime                       not null,
   BackupMemo                     varchar(50),
   primary key (UserName, BackupNo)
);


drop table if exists BZDMemberCompanyInfo;

/*==============================================================*/
/* Table: BZDMemberCompanyInfo                                  */
/*==============================================================*/
create table BZDMemberCompanyInfo
(
   UserName                       varchar(50)                    not null,
   CompanyName                    varchar(100),
   Scale                          varchar(10),
   BusinessType                   varchar(10),
   Products                       varchar(50),
   CompanySite                    varchar(50),
   Tel                            varchar(20),
   Fax                            varchar(20),
   LinkMan                        varchar(20),
   Mobile                         varchar(20),
   Email                          varchar(50),
   Address                        varchar(100),
   ZipCode                        varchar(10),
   Pic                            varchar(50),
   Intro                          text,
   BackupNo                       varchar(15)                    not null,
   BackupOperator                 varchar(200)                   not null,
   BackupTime                     datetime                       not null,
   BackupMemo                     varchar(50),
   primary key (UserName, BackupNo)
);

/*何锋 2009.11.26 附件表增加字段ImagePath缩略图路径*/
alter table ZCattachment add COLUMN ImagePath varchar(100) after OrderFlag;
alter table BZCattachment add COLUMN ImagePath varchar(100)  after OrderFlag;

/*王育春，为自定义数据增加SiteID 20091201*/
ALTER TABLE BZCCustomTable ADD COLUMN SiteID BIGINT NOT NULL AFTER ID;
ALTER TABLE ZCCustomTable ADD COLUMN SiteID BIGINT NOT NULL AFTER ID;
ALTER TABLE BZCDataBase ADD COLUMN SiteID BIGINT NOT NULL AFTER ID;
ALTER TABLE ZCDataBase ADD COLUMN SiteID BIGINT NOT NULL AFTER ID;

/*王育春，栏目表结构修改 20091203*/
ALTER TABLE bzccatalog ADD COLUMN AllowContribute VARCHAR(2) After KeywordSetting;
ALTER TABLE bzccatalog MODIFY COLUMN SingleFlag VARCHAR(2);
ALTER TABLE zccatalog ADD COLUMN AllowContribute VARCHAR(2) After KeywordSetting;
ALTER TABLE zccatalog MODIFY COLUMN SingleFlag VARCHAR(2);

/*汪维军，zdmaxno表字段Length类型改为bigint，与oracle配套 20091203*/
alter table zdmaxno modify column Length bigint;
alter table bzdmaxno modify column Length bigint;

/*兰军 修改文章表*/
ALTER TABLE ZCArticle ADD COLUMN Attribute varchar(200) AFTER Type;
ALTER TABLE BZCArticle ADD COLUMN Attribute varchar(200) AFTER Type;
ALTER TABLE ZCArticle ADD COLUMN CopyImageFlag char(2) AFTER CommentFlag;
ALTER TABLE BZCArticle ADD COLUMN CopyImageFlag char(2) AFTER CommentFlag;

/*黄雷，栏目innerCode4位变6位*/
update zccatalog set innercode=concat('00',substr(innercode,1,4),'00',substr(innercode,5,4),'00',substr(innercode,9,4),'00',substr(innercode,13,4),'00',substr(innercode,17,4),'00',substr(innercode,21,4)) where length(innercode)=24;
update zccatalog set innercode=concat('00',substr(innercode,1,4),'00',substr(innercode,5,4),'00',substr(innercode,9,4),'00',substr(innercode,13,4),'00',substr(innercode,17,4))  where length(innercode)=20;
update zccatalog set innercode=concat('00',substr(innercode,1,4),'00',substr(innercode,5,4),'00',substr(innercode,9,4),'00',substr(innercode,13,4)) where length(innercode)=16;
update zccatalog set innercode=concat('00',substr(innercode,1,4),'00',substr(innercode,5,4),'00',substr(innercode,9,4)) where length(innercode)=12;
update zccatalog set innercode=concat('00',substr(innercode,1,4),'00',substr(innercode,5,4))  where length(innercode)=8;
update zccatalog set innercode=concat('00',substr(innercode,1,4)) where length(innercode)=4;


update  zcarticle a set a.cataloginnercode=(select b.innercode from zccatalog b where b.id =a.catalogid) where exists (select id  from zccatalog where zccatalog.id=a.catalogid);
update  zcimage a set a.cataloginnercode=(select b.innercode from zccatalog b where b.id =a.catalogid) where exists (select id  from zccatalog where zccatalog.id=a.catalogid);
update  zcvideo a set a.cataloginnercode=(select b.innercode from zccatalog b where b.id =a.catalogid) where exists (select id  from zccatalog where zccatalog.id=a.catalogid);
update  zcaudio a set a.cataloginnercode=(select b.innercode from zccatalog b where b.id =a.catalogid) where exists (select id  from zccatalog where zccatalog.id=a.catalogid);
update  zcattachment a set a.cataloginnercode=(select b.innercode from zccatalog b where b.id =a.catalogid) where exists (select id  from zccatalog where zccatalog.id=a.catalogid);
update  zcdocrela a set a.cataloginnercode=(select b.innercode from zccatalog b where b.id =a.catalogid) where exists (select id  from zccatalog where zccatalog.id=a.catalogid);

update zdprivilege set id=concat('00',substr(id,1,4),'00',substr(id,5,4),'00',substr(id,9,4),'00',substr(id,13,4),'00',substr(id,17,4),'00',substr(id,21,4)) where length(id)=24 and privtype in ('image','article','video','audio','attach');
update zdprivilege set id=concat('00',substr(id,1,4),'00',substr(id,5,4),'00',substr(id,9,4),'00',substr(id,13,4),'00',substr(id,17,4))  where length(id)=20 and privtype in ('image','article','video','audio','attach');
update zdprivilege set id=concat('00',substr(id,1,4),'00',substr(id,5,4),'00',substr(id,9,4),'00',substr(id,13,4)) where length(id)=16 and privtype in ('image','article','video','audio','attach');
update zdprivilege set id=concat('00',substr(id,1,4),'00',substr(id,5,4),'00',substr(id,9,4)) where length(id)=12 and privtype in ('image','article','video','audio','attach');
update zdprivilege set id=concat('00',substr(id,1,4),'00',substr(id,5,4))  where length(id)=8 and privtype in ('image','article','video','audio','attach');
update zdprivilege set id=concat('00',substr(id,1,4)) where length(id)=4 and privtype in ('image','article','video','audio','attach');

update zdmaxno set nosubtype=concat('00',substr(nosubtype,1,4),'00',substr(nosubtype,5,4),'00',substr(nosubtype,9,4),'00',substr(nosubtype,13,4),'00',substr(nosubtype,17,4),'00',substr(nosubtype,21,4)) where notype='CatalogInnerCode' and length(nosubtype)=24;
update zdmaxno set nosubtype=concat('00',substr(nosubtype,1,4),'00',substr(nosubtype,5,4),'00',substr(nosubtype,9,4),'00',substr(nosubtype,13,4),'00',substr(nosubtype,17,4))  where notype='CatalogInnerCode' and length(nosubtype)=20;
update zdmaxno set nosubtype=concat('00',substr(nosubtype,1,4),'00',substr(nosubtype,5,4),'00',substr(nosubtype,9,4),'00',substr(nosubtype,13,4)) where notype='CatalogInnerCode' and length(nosubtype)=16;
update zdmaxno set nosubtype=concat('00',substr(nosubtype,1,4),'00',substr(nosubtype,5,4),'00',substr(nosubtype,9,4)) where notype='CatalogInnerCode' and length(nosubtype)=12;
update zdmaxno set nosubtype=concat('00',substr(nosubtype,1,4),'00',substr(nosubtype,5,4))  where notype='CatalogInnerCode' and length(nosubtype)=8;
update zdmaxno set nosubtype=concat('00',substr(nosubtype,1,4)) where notype='CatalogInnerCode' and length(nosubtype)=4;

update zcstatitem set item=concat('00',substr(item,1,4),'00',substr(item,5,4),'00',substr(item,9,4),'00',substr(item,13,4),'00',substr(item,17,4),'00',substr(item,21,4) )  where  length(item)=24 and type ='Catalog' and item not like '%Index%';
update zcstatitem set item=concat('00',substr(item,1,4),'00',substr(item,5,4),'00',substr(item,9,4),'00',substr(item,13,4),'00',substr(item,17,4)  )  where  length(item)=20 and type ='Catalog' and item not like '%Index%';
update zcstatitem set item=concat('00',substr(item,1,4),'00',substr(item,5,4),'00',substr(item,9,4),'00',substr(item,13,4) )  where  length(item)=16 and type ='Catalog' and item not like '%Index%';
update zcstatitem set item=concat('00',substr(item,1,4),'00',substr(item,5,4),'00',substr(item,9,4) )  where  length(item)=12 and type ='Catalog' and item not like '%Index%';
update zcstatitem set item=concat('00',substr(item,1,4),'00',substr(item,5,4)  )  where  length(item)=8 and type ='Catalog' and item not like '%Index%';
update zcstatitem set item=concat('00',substr(item,1,4) )  where  length(item)=4 and type ='Catalog' and item not like '%Index%';

update zcstatitem set item=concat('00',substr(item,1,4),'00',substr(item,5,4),'00',substr(item,9,4),'00',substr(item,13,4),'00',substr(item,17,4),'00',substr(item,21,9) )  where  length(item)=29 and type ='Catalog' and item  like '%Index%';
update zcstatitem set item=concat('00',substr(item,1,4),'00',substr(item,5,4),'00',substr(item,9,4),'00',substr(item,13,4),'00',substr(item,17,9)  )  where  length(item)=25 and type ='Catalog' and item  like '%Index%';
update zcstatitem set item=concat('00',substr(item,1,4),'00',substr(item,5,4),'00',substr(item,9,4),'00',substr(item,13,9) )  where  length(item)=21 and type ='Catalog' and item  like '%Index%';
update zcstatitem set item=concat('00',substr(item,1,4),'00',substr(item,5,4),'00',substr(item,9,9) )  where  length(item)=17 and type ='Catalog' and item  like '%Index%';
update zcstatitem set item=concat('00',substr(item,1,4),'00',substr(item,5,9)  )  where  length(item)=13 and type ='Catalog' and item  like '%Index%';
update zcstatitem set item=concat('00',substr(item,1,9) )  where  length(item)=9 and type ='Catalog' and item  like '%Index%';

/*王育春，为ZDColumn字段FormID修改为SiteID*/
ALTER TABLE bzdcolumn CHANGE COLUMN FormID SiteID BIGINT(20) DEFAULT NULL;
ALTER TABLE zdcolumn CHANGE COLUMN FormID SiteID BIGINT(20) DEFAULT NULL;
update ZDColumn set SiteID=(select distinct SiteID from ZCCatalog where ID in (select RelaID from ZDColumnRela where ColumnID=ZDColumn.ID));
delete from ZDColumn where SiteID is null;
delete from BZDColumn ;

/*李伟仪，为ZCVoteLog表添加字段addUser2009-12-07*/
ALTER table ZCVoteLog add COLUMN addUser varchar(50) after Prop2;
ALTER table BZCVoteLog add COLUMN addUser varchar(50) after Prop2;

/*徐喆 修改计划任务表*/
alter table ZDSchedule change IsUsing IsUsing char(2);
alter table BZDSchedule change IsUsing IsUsing char(2);

/**汪维军 20091209 文章表增加字段置顶有效期限TopDate ***/
ALTER table ZCArticle add COLUMN TopDate datetime after TopFlag;
ALTER table BZCArticle add COLUMN TopDate datetime after TopFlag;

/*BBS相关的所有表 20091211*/
drop table if exists ZCAdminGroup;

drop table if exists ZCForum;

drop table if exists ZCForumAttachment;

drop table if exists ZCForumConfig;

drop table if exists ZCForumGroup;

drop table if exists ZCForumMember;

drop table if exists ZCForumScore;

drop table if exists ZCPost;

drop table if exists ZCTheme;

/*==============================================================*/
/* Table: ZCAdminGroup                                          */
/*==============================================================*/
create table ZCAdminGroup
(
   GroupID              bigint not null,
   SiteID               bigint,
   AllowEditUser        varchar(2),
   AllowForbidUser      varchar(2),
   AllowEditForum       varchar(2),
   AllowVerfyPost       varchar(2),
   AllowDel             varchar(2),
   AllowEdit            varchar(2),
   Hide                 varchar(2),
   RemoveTheme          varchar(2),
   MoveTheme            varchar(2),
   TopTheme             varchar(2),
   BrightTheme          varchar(2),
   UpOrDownTheme        varchar(2),
   BestTheme            varchar(2),
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   primary key (GroupID)
);

/*==============================================================*/
/* Table: ZCForum                                               */
/*==============================================================*/
create table ZCForum
(
   ID                   bigint not null,
   SiteID               bigint,
   ParentID             bigint not null,
   Type                 varchar(20) not null,
   Name                 varchar(100) not null,
   Status               varchar(2) not null,
   Pic                  varchar(100),
   Visible              varchar(2),
   Info                 varchar(1000),
   ThemeCount           int not null,
   Verify               varchar(2),
   Locked               varchar(2),
   AllowTheme           varchar(2),
   EditPost             varchar(2),
   ReplyPost            varchar(2),
   Recycle              varchar(2),
   AllowHTML            varchar(2),
   AllowFace            varchar(2),
   AnonyPost            varchar(2),
   URL                  varchar (200),
   Image                varchar(200),
   Password             varchar(100),
   Word                 varchar(200),
   PostCount            int not null,
   ForumAdmin           varchar(100),
   TodayPostCount       int,
   LastThemeID          bigint,
   LastPost             varchar(100),
   LastPoster           varchar(100),
   OrderFlag            bigint not null,
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   primary key (ID)
);

/*==============================================================*/
/* Table: ZCForumAttachment                                     */
/*==============================================================*/
create table ZCForumAttachment
(
   ID                   bigint not null,
   PostID               bigint not null,
   SiteID               bigint,
   Name                 varchar(200) not null,
   Path                 varchar(200) not null,
   Type                 varchar(100) not null,
   Suffix               varchar(50),
   AttSize              bigint,
   DownCount            bigint,
   Note                 varchar(500),
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   primary key (ID)
);

/*==============================================================*/
/* Table: ZCForumConfig                                         */
/*==============================================================*/
create table ZCForumConfig
(
   ID                   bigint not null,
   Name                 varchar(50),
   SiteID               bigint,
   Subdomains           varchar(50),
   Des                  varchar(1024),
   TempCloseFlag        varchar(2),
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   primary key (ID)
);

/*==============================================================*/
/* Table: ZCForumGroup                                          */
/*==============================================================*/
create table ZCForumGroup
(
   ID                   bigint not null,
   SiteID               bigint,
   RadminID             bigint,
   Name                 varchar(100) not null,
   SystemName           varchar(100),
   Type                 varchar(100) not null,
   Color                varchar(100),
   Image                varchar(100),
   LowerScore           bigint,
   UpperScore           bigint,
   AllowTheme           varchar(2),
   AllowReply           varchar(2),
   AllowSearch          varchar(2),
   AllowUserInfo        varchar(2),
   AllowPanel           varchar(2),
   AllowNickName        varchar(2),
   AllowVisit           varchar(2),
   AllowHeadImage       varchar(2),
   AllowFace            varchar(2),
   AllowAutograph       varchar(2),
   Verify               varchar(2),
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   OrderFlag            bigint,
   primary key (ID)
);

/*==============================================================*/
/* Table: ZCForumMember                                         */
/*==============================================================*/
create table ZCForumMember
(
   UserName             varchar(50) not null,
   SiteID               bigint,
   AdminID              bigint,
   UserGroupID          bigint,
   NickName             varchar(100),
   ThemeCount           bigint,
   ReplyCount           bigint,
   HeadImage            varchar(500),
   UseSelfImage         varchar(2),
   Status               varchar(2),
   ForumScore           bigint,
   ForumSign            varchar(1000),
   LastLoginTime        dateTime,
   LastLogoutTime       dateTime,
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   primary key (UserName)
);

/*==============================================================*/
/* Table: ZCForumScore                                          */
/*==============================================================*/
create table ZCForumScore
(
   ID                   bigint not null,
   SiteID               bigint,
   InitScore            bigint,
   PublishTheme         bigint,
   DeleteTheme          bigint,
   PublishPost          bigint,
   DeletePost           bigint,
   Best                 bigint,
   CancelBest           bigint,
   Bright               bigint,
   CancelBright         bigint,
   TopTheme             bigint,
   CancelTop            bigint,
   UpTheme              bigint,
   DownTheme            bigint,
   Upload               bigint,
   Download             bigint,
   Search               bigint,
   Vote                 bigint,
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   primary key (ID)
);

/*==============================================================*/
/* Table: ZCPost                                                */
/*==============================================================*/
create table ZCPost
(
   ID                   bigint not null,
   SiteID               bigint,
   ForumID              bigint not null,
   ThemeID              bigint not null,
   First                varchar(2),
   Subject              varchar(100),
   Message              text,
   IP                   char(20),
   Status               varchar(2),
   IsHidden             varchar(2),
   Invisible            varchar(2),
   VerifyFlag           varchar(2),
   Layer                bigint,
   ApplyDel             varchar(2),
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   primary key (ID)
);

/*==============================================================*/
/* Table: ZCTheme                                               */
/*==============================================================*/
create table ZCTheme
(
   ID                   bigint not null,
   SiteID               bigint,
   ForumID              bigint not null,
   Subject              varchar(100),
   IPAddress            varchar(20),
   Type                 varchar(2),
   Status               varchar(2),
   LastPost             varchar(100),
   LastPoster           varchar(100),
   ViewCount            int,
   ReplyCount           int,
   OrderFlag            bigint not null,
   VerifyFlag           varchar(2),
   TopTheme             varchar(2),
   Best                 varchar(2),
   Bright               varchar(100),
   Applydel             varchar(2),
   LastPostTime         datetime,
   OrderTime            datetime,
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   primary key (ID)
);

drop table if exists BZCAdminGroup;

drop table if exists BZCForum;

drop table if exists BZCForumAttachment;

drop table if exists BZCForumConfig;

drop table if exists BZCForumGroup;

drop table if exists BZCForumMember;

drop table if exists BZCForumScore;

drop table if exists BZCPost;

drop table if exists BZCTheme;

/*==============================================================*/
/* Table: BZCAdminGroup                                         */
/*==============================================================*/
create table BZCAdminGroup
(
   GroupID              bigint not null,
   SiteID               bigint,
   AllowEditUser        varchar(2),
   AllowForbidUser      varchar(2),
   AllowEditForum       varchar(2),
   AllowVerfyPost       varchar(2),
   AllowDel             varchar(2),
   AllowEdit            varchar(2),
   Hide                 varchar(2),
   RemoveTheme          varchar(2),
   MoveTheme            varchar(2),
   TopTheme             varchar(2),
   BrightTheme          varchar(2),
   UpOrDownTheme        varchar(2),
   BestTheme            varchar(2),
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (GroupID, BackupNo)
);

/*==============================================================*/
/* Table: BZCForum                                              */
/*==============================================================*/
create table BZCForum
(
   ID                   bigint not null,
   SiteID               bigint,
   ParentID             bigint not null,
   Type                 varchar(20) not null,
   Name                 varchar(100) not null,
   Status               varchar(2) not null,
   Pic                  varchar(100),
   Visible              varchar(2),
   Info                 varchar(1000),
   ThemeCount           int not null,
   Verify               varchar(2),
   Locked               varchar(2),
   AllowTheme           varchar(2),
   EditPost             varchar(2),
   ReplyPost            varchar(2),
   Recycle              varchar(2),
   AllowHTML            varchar(2),
   AllowFace            varchar(2),
   AnonyPost            varchar(2),
   URL                  varchar (200),
   Image                varchar(200),
   Password             varchar(100),
   Word                 varchar(200),
   PostCount            int not null,
   ForumAdmin           varchar(100),
   TodayPostCount       int,
   LastThemeID          bigint,
   LastPost             varchar(100),
   LastPoster           varchar(100),
   OrderFlag            bigint not null,
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

/*==============================================================*/
/* Table: BZCForumAttachment                                    */
/*==============================================================*/
create table BZCForumAttachment
(
   ID                   bigint not null,
   PostID               bigint not null,
   SiteID               bigint,
   Name                 varchar(200) not null,
   Path                 varchar(200) not null,
   Type                 varchar(100) not null,
   Suffix               varchar(50),
   AttSize              bigint,
   DownCount            bigint,
   Note                 varchar(500),
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

/*==============================================================*/
/* Table: BZCForumConfig                                        */
/*==============================================================*/
create table BZCForumConfig
(
   ID                   bigint not null,
   Name                 varchar(50),
   SiteID               bigint,
   Subdomains           varchar(50),
   Des                  varchar(1024),
   TempCloseFlag        varchar(2),
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

/*==============================================================*/
/* Table: BZCForumGroup                                         */
/*==============================================================*/
create table BZCForumGroup
(
   ID                   bigint not null,
   SiteID               bigint,
   RadminID             bigint,
   Name                 varchar(100) not null,
   SystemName           varchar(100),
   Type                 varchar(100) not null,
   Color                varchar(100),
   Image                varchar(100),
   LowerScore           bigint,
   UpperScore           bigint,
   AllowTheme           varchar(2),
   AllowReply           varchar(2),
   AllowSearch          varchar(2),
   AllowUserInfo        varchar(2),
   AllowPanel           varchar(2),
   AllowNickName        varchar(2),
   AllowVisit           varchar(2),
   AllowHeadImage       varchar(2),
   AllowFace            varchar(2),
   AllowAutograph       varchar(2),
   Verify               varchar(2),
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   OrderFlag            bigint,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

/*==============================================================*/
/* Table: BZCForumMember                                        */
/*==============================================================*/
create table BZCForumMember
(
   UserName             varchar(50) not null,
   SiteID               bigint,
   AdminID              bigint,
   UserGroupID          bigint,
   NickName             varchar(100),
   ThemeCount           bigint,
   ReplyCount           bigint,
   HeadImage            varchar(500),
   UseSelfImage         varchar(2),
   Status               varchar(2),
   ForumScore           bigint,
   ForumSign            varchar(1000),
   LastLoginTime        dateTime,
   LastLogoutTime       dateTime,
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (UserName, BackupNo)
);

/*==============================================================*/
/* Table: BZCForumScore                                         */
/*==============================================================*/
create table BZCForumScore
(
   ID                   bigint not null,
   SiteID               bigint,
   InitScore            bigint,
   PublishTheme         bigint,
   DeleteTheme          bigint,
   PublishPost          bigint,
   DeletePost           bigint,
   Best                 bigint,
   CancelBest           bigint,
   Bright               bigint,
   CancelBright         bigint,
   TopTheme             bigint,
   CancelTop            bigint,
   UpTheme              bigint,
   DownTheme            bigint,
   Upload               bigint,
   Download             bigint,
   Search               bigint,
   Vote                 bigint,
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

/*==============================================================*/
/* Table: BZCPost                                               */
/*==============================================================*/
create table BZCPost
(
   ID                   bigint not null,
   SiteID               bigint,
   ForumID              bigint not null,
   ThemeID              bigint not null,
   First                varchar(2),
   Subject              varchar(100),
   Message              text,
   IP                   char(20),
   Status               varchar(2),
   IsHidden             varchar(2),
   Invisible            varchar(2),
   VerifyFlag           varchar(2),
   Layer                bigint,
   ApplyDel             varchar(2),
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

/*==============================================================*/
/* Table: BZCTheme                                              */
/*==============================================================*/
create table BZCTheme
(
   ID                   bigint not null,
   SiteID               bigint,
   ForumID              bigint not null,
   Subject              varchar(100),
   IPAddress            varchar(20),
   Type                 varchar(2),
   Status               varchar(2),
   LastPost             varchar(100),
   LastPoster           varchar(100),
   ViewCount            int,
   ReplyCount           int,
   OrderFlag            bigint not null,
   VerifyFlag           varchar(2),
   TopTheme             varchar(2),
   Best                 varchar(2),
   Bright               varchar(100),
   Applydel             varchar(2),
   LastPostTime         datetime,
   OrderTime            datetime,
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

/*问答相关表 20091211*/
drop table if exists ZCQuestionGroup;

/*==============================================================*/
/* Table: ZCQuestionGroup                                       */
/*==============================================================*/
create table ZCQuestionGroup
(
   InnerCode            varchar(100) not null,
   ParentInnerCode      varchar(100) not null,
   Name                 varchar(100) not null,
   TreeLevel            int not null,
   IsLeaf               varchar(2),
   OrderFlag            bigint not null,
   Memo                 varchar(100),
   Prop1                varchar(100),
   Prop2                varchar(100),
   Prop3                varchar(100),
   Prop4                varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(100) not null,
   ModifyTime           datetime,
   ModifyUser           varchar(100),
   primary key (InnerCode)
);

drop table if exists ZCQuestion;

/*==============================================================*/
/* Table: ZCQuestion                                            */
/*==============================================================*/
create table ZCQuestion
(
   ID                   bigint not null,
   QuestionInnerCode    varchar(100),
   Title                varchar(250),
   Content              text,
   ReplyCount           int(11),
   Status               char(1),
   IsCommend            char(1),
   EndTime              datetime,
   Memo                 varchar(100),
   Prop1                varchar(100),
   Prop2                varchar(100),
   Prop3                varchar(100),
   Prop4                varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(100) not null,
   ModifyTime           datetime,
   ModifyUser           varchar(100),
   primary key (ID)
);

drop table if exists ZCAnswer;

/*==============================================================*/
/* Table: ZCAnswer                                              */
/*==============================================================*/
create table ZCAnswer
(
   ID                   bigint not null,
   QuestionID           bigint not null,
   Content              text,
   IsBest               char(1),
   Memo                 varchar(100),
   Prop1                varchar(100),
   Prop2                varchar(100),
   Prop3                varchar(100),
   Prop4                varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(100) not null,
   ModifyTime           datetime,
   ModifyUser           varchar(100),
   primary key (ID)
);

drop table if exists ZCAnswerComment;

/*==============================================================*/
/* Table: ZCAnswerComment                                       */
/*==============================================================*/
create table ZCAnswerComment
(
   ID                   bigint not null,
   QuestionID           bigint not null,
   Content              text,
   Memo                 varchar(100),
   Prop1                varchar(100),
   Prop2                varchar(100),
   Prop3                varchar(100),
   Prop4                varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(100) not null,
   ModifyTime           datetime,
   ModifyUser           varchar(100),
   primary key (ID)
);

drop table if exists BZCQuestionGroup;

/*==============================================================*/
/* Table: BZCQuestionGroup                                      */
/*==============================================================*/
create table BZCQuestionGroup
(
   InnerCode            varchar(100) not null,
   ParentInnerCode      varchar(100) not null,
   Name                 varchar(100) not null,
   TreeLevel            int not null,
   IsLeaf               varchar(2),
   OrderFlag            bigint not null,
   Memo                 varchar(100),
   Prop1                varchar(100),
   Prop2                varchar(100),
   Prop3                varchar(100),
   Prop4                varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(100) not null,
   ModifyTime           datetime,
   ModifyUser           varchar(100),
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (InnerCode, BackupNo)
);

drop table if exists BZCQuestion;

/*==============================================================*/
/* Table: BZCQuestion                                           */
/*==============================================================*/
create table BZCQuestion
(
   ID                   bigint not null,
   QuestionInnerCode    varchar(100),
   Title                varchar(250),
   Content              text,
   ReplyCount           int(11),
   Status               char(1),
   IsCommend            char(1),
   EndTime              datetime,
   Memo                 varchar(100),
   Prop1                varchar(100),
   Prop2                varchar(100),
   Prop3                varchar(100),
   Prop4                varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(100) not null,
   ModifyTime           datetime,
   ModifyUser           varchar(100),
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

drop table if exists BZCAnswer;

/*==============================================================*/
/* Table: BZCAnswer                                             */
/*==============================================================*/
create table BZCAnswer
(
   ID                   bigint not null,
   QuestionID           bigint not null,
   Content              text,
   IsBest               char(1),
   Memo                 varchar(100),
   Prop1                varchar(100),
   Prop2                varchar(100),
   Prop3                varchar(100),
   Prop4                varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(100) not null,
   ModifyTime           datetime,
   ModifyUser           varchar(100),
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

drop table if exists BZCAnswerComment;

/*==============================================================*/
/* Table: BZCAnswerComment                                      */
/*==============================================================*/
create table BZCAnswerComment
(
   ID                   bigint not null,
   QuestionID           bigint not null,
   Content              text,
   Memo                 varchar(100),
   Prop1                varchar(100),
   Prop2                varchar(100),
   Prop3                varchar(100),
   Prop4                varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(100) not null,
   ModifyTime           datetime,
   ModifyUser           varchar(100),
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

/*2009-12-11 黄雷 初始化栏目排序的问题*/
update zccatalog set orderflag=0;

/* 张金灿 zcpublishconfig改成zccatalogconfig表,zccatalogconfig加入“处理热点词汇”字段 */
drop table if exists ZCPublishConfig;
drop table if exists ZCCatalogConfig;

/*==============================================================*/
/* Table: ZCCatalogConfig                                       */
/*==============================================================*/
create table ZCCatalogConfig
(
   ID                   bigint not null,
   SiteID               bigint not null,
   CatalogID            bigint,
   CronExpression       varchar(100),
   PlanType             varchar(10),
   StartTime            datetime,
   IsUsing              char(2) not null,
   HotWordFlag              char(2) not null,
   AllowStatus          varchar(50),
   AfterEditStatus      varchar(50),
   Encoding             varchar(20),
   ArchiveTime          varchar(10),
   Prop1                varchar(50),
   Prop2                varchar(50),
   Prop3                varchar(50),
   Prop4                varchar(50),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (ID)
);

drop table if exists BZCPublishConfig;
drop table if exists BZCCatalogConfig;

/*==============================================================*/
/* Table: BZCCatalogConfig                                      */
/*==============================================================*/
create table BZCCatalogConfig
(
   ID                   bigint not null,
   SiteID               bigint not null,
   CatalogID            bigint,
   CronExpression       varchar(100),
   PlanType             varchar(10),
   StartTime            datetime,
   IsUsing              char(2) not null,
   HotWordFlag              char(2) not null,
   AllowStatus          varchar(50),
   AfterEditStatus      varchar(50),
   Encoding             varchar(20),
   ArchiveTime          varchar(10),
   Prop1                varchar(50),
   Prop2                varchar(50),
   Prop3                varchar(50),
   Prop4                varchar(50),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);
/* 王少亭 ZDUserLog表的加一个SubType字段 */
ALTER table ZDUserLog add COLUMN SubType varchar(10) after LogType;
ALTER table BZDUserLog add COLUMN SubType varchar(10) after LogType;
/* 张金灿 zckeywork表的加一个siteid字段 */
ALTER table ZCKeyWord add COLUMN SiteId bigint not null after Keyword ;
ALTER table BZCKeyWord add COLUMN SiteId bigint not null after Keyword ;
/* 王少亭 修改字段varchar长度 */
alter table ZDUserLog change LogType LogType varchar(20);
alter table ZDUserLog change SubType SubType varchar(20);

/* 自定义数据增加几个字段 20091219 by wyuch*/
ALTER TABLE bzccustomtable ADD COLUMN AllowView VARCHAR(2) AFTER Memo;
ALTER TABLE zccustomtable ADD COLUMN AllowView VARCHAR(2) AFTER Memo;
ALTER TABLE bzccustomtable ADD COLUMN AllowModify VARCHAR(2) AFTER AllowView;
ALTER TABLE zccustomtable ADD COLUMN AllowModify VARCHAR(2) AFTER AllowView;

ALTER TABLE ZCCustomTableColumn ADD COLUMN DefaultValue varchar(50) AFTER isMandatory;
ALTER TABLE ZCCustomTableColumn ADD COLUMN InputType varchar(2) AFTER DefaultValue;
ALTER TABLE ZCCustomTableColumn ADD COLUMN CSSStyle varchar(200) AFTER InputType;
ALTER TABLE ZCCustomTableColumn ADD COLUMN MaxLength integer AFTER CSSStyle;
ALTER TABLE ZCCustomTableColumn ADD COLUMN ListOptions text AFTER MaxLength;

ALTER TABLE BZCCustomTableColumn ADD COLUMN DefaultValue varchar(50) AFTER isMandatory;
ALTER TABLE BZCCustomTableColumn ADD COLUMN InputType varchar(2) AFTER DefaultValue;
ALTER TABLE BZCCustomTableColumn ADD COLUMN CSSStyle varchar(200) AFTER InputType;
ALTER TABLE BZCCustomTableColumn ADD COLUMN MaxLength integer AFTER CSSStyle;
ALTER TABLE BZCCustomTableColumn ADD COLUMN ListOptions text AFTER MaxLength;

/*2009-12-18 汪维军 栏目发布配置表增加栏目编码、附件下载配置字段*/
ALTER table ZCCatalogConfig add COLUMN CatalogInnerCode varchar(100) after CatalogID ;
ALTER table BZCCatalogConfig add COLUMN CatalogInnerCode varchar(100) after CatalogID ;
ALTER table ZCCatalogConfig add COLUMN AttachDownFlag varchar(2) after ArchiveTime ;
ALTER table BZCCatalogConfig add COLUMN AttachDownFlag varchar(2) after ArchiveTime ;


/*2009-12-18 汪维军 zdcode增加归档时限代码**/
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES 
 ('ArchiveTime','ArchiveTime','1','一个月之后',1260418063512,NULL,NULL,NULL,NULL,'','2009-12-10 12:07:43','admin',NULL,NULL),
 ('ArchiveTime','ArchiveTime','12','一年之后',1260418063515,NULL,NULL,NULL,NULL,'','2009-12-08 18:16:20','admin',NULL,NULL),
 ('ArchiveTime','ArchiveTime','24','两年之后',1260418093078,NULL,NULL,NULL,NULL,'','2009-12-10 12:08:13','admin',NULL,NULL),
 ('ArchiveTime','ArchiveTime','3','一个季度之后',1260418063513,NULL,NULL,NULL,NULL,'','2009-12-08 18:16:31','admin',NULL,NULL),
 ('ArchiveTime','ArchiveTime','6','半年之后',1260418063514,NULL,NULL,NULL,NULL,'','2009-12-08 18:16:08','admin',NULL,NULL),
 ('ArchiveTime','System','System','归档时限',1260262592015,NULL,NULL,NULL,NULL,'多长时间之后对新闻进行归档处理','2009-12-08 16:56:32','admin',NULL,NULL);
 
 /**2009-12-18 徐喆 广告改版**/
 drop table if exists ZCAdvertisement;

/*==============================================================*/
/* Table: ZCAdvertisement                                       */
/*==============================================================*/
create table ZCAdvertisement
(
   ID                   bigint not null,
   PositionID           bigint not null,
   PositionCode         varchar(50) not null,
   SiteID               bigint not null,
   AdName               varchar(100) not null,
   AdType               varchar(20) not null,
   AdContent            mediumtext,
   OrderFlag            bigint,
   IsHitCount           char(2),
   HitCount             bigint,
   StickTime            bigint,
   IsOpen               char(2),
   StartTime            datetime,
   EndTime              datetime,
   Prop1                varchar(50),
   Prop2                varchar(50),
   Prop3                varchar(50),
   Prop4                varchar(50),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (ID)
);

drop table if exists ZCAdPosition;

/*==============================================================*/
/* Table: ZCAdPosition                                          */
/*==============================================================*/
create table ZCAdPosition
(
   ID                   bigint not null,
   SiteID               bigint not null,
   PositionName         varchar(100) not null,
   Code                 varchar(50) not null,
   Description          text,
   PositionType         varchar(20),
   PaddingTop           bigint,
   PaddingLeft          bigint,
   PositionWidth        bigint,
   PositionHeight       bigint,
   JsName               varchar(100),
   Prop1                varchar(50),
   Prop2                varchar(50),
   Prop3                varchar(50),
   Prop4                varchar(50),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (ID)
);

drop table if exists BZCAdPosition;

/*==============================================================*/
/* Table: BZCAdPosition                                         */
/*==============================================================*/
create table BZCAdPosition
(
   ID                   bigint not null,
   SiteID               bigint not null,
   PositionName         varchar(100) not null,
   Code                 varchar(50) not null,
   Description          text,
   PositionType         varchar(20),
   PaddingTop           bigint,
   PaddingLeft          bigint,
   PositionWidth        bigint,
   PositionHeight       bigint,
   JsName               varchar(100),
   Prop1                varchar(50),
   Prop2                varchar(50),
   Prop3                varchar(50),
   Prop4                varchar(50),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

drop table if exists BZCAdvertisement;

/*==============================================================*/
/* Table: BZCAdvertisement                                      */
/*==============================================================*/
create table BZCAdvertisement
(
   ID                   bigint not null,
   PositionID           bigint not null,
   PositionCode         varchar(50) not null,
   SiteID               bigint not null,
   AdName               varchar(100) not null,
   AdType               varchar(20) not null,
   AdContent            mediumtext,
   OrderFlag            bigint,
   IsHitCount           char(2),
   HitCount             bigint,
   StickTime            bigint,
   IsOpen               char(2),
   StartTime            datetime,
   EndTime              datetime,
   Prop1                varchar(50),
   Prop2                varchar(50),
   Prop3                varchar(50),
   Prop4                varchar(50),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);


/* 王少亭 新建招聘申请表 */
drop table if exists ZCApply;

/*==============================================================*/
/* Table: ZCApply                                               */
/*==============================================================*/
create table ZCApply
(
   ID                   bigint not null,
   SiteID               bigint,
   Name                 varchar(25),
   Gender               varchar(1),
   BirthDate            datetime,
   Picture              varchar(50),
   Ethnicity            varchar(3),
   NativePlace          varchar(10),
   Political            varchar(3),
   CertNumber           varchar(20),
   Phone                varchar(20),
   Mobile               varchar(20),
   Address              varchar(200),
   Postcode             varchar(10),
   Email                varchar(100),
   ForeignLanguage      varchar(50),
   LanguageLevel        varchar(50),
   Authentification     varchar(200),
   PersonIntro          varchar(1500),
   Honour               varchar(1500),
   PracticeExperience   varchar(2000),
   RegisteredPlace      varchar(10),
   EduLevel             varchar(3),
   University           varchar(40),
   Speacility           varchar(100),
   WillPosition         varchar(50),
   AuditUser            varchar(50),
   AuditStatus          varchar(5),
   Prop1                varchar(100),
   Prop2                varchar(100),
   Prop3                varchar(100),
   Prop4                varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(100) not null,
   ModifyTime           datetime,
   ModifyUser           varchar(100),
   primary key (ID)
);
drop table if exists BZCApply;

/*==============================================================*/
/* Table: BZCApply                                              */
/*==============================================================*/
create table BZCApply
(
   ID                   bigint not null,
   SiteID               bigint,
   Name                 varchar(25),
   Gender               varchar(1),
   BirthDate            datetime,
   Picture              varchar(50),
   Ethnicity            varchar(3),
   NativePlace          varchar(10),
   Political            varchar(3),
   CertNumber           varchar(20),
   Phone                varchar(20),
   Mobile               varchar(20),
   Address              varchar(200),
   Postcode             varchar(10),
   Email                varchar(100),
   ForeignLanguage      varchar(50),
   LanguageLevel        varchar(50),
   Authentification     varchar(200),
   PersonIntro          varchar(1500),
   Honour               varchar(1500),
   PracticeExperience   varchar(2000),
   RegisteredPlace      varchar(10),
   EduLevel             varchar(3),
   University           varchar(40),
   Speacility           varchar(100),
   WillPosition         varchar(50),
   AuditUser            varchar(50),
   AuditStatus          varchar(5),
   Prop1                varchar(100),
   Prop2                varchar(100),
   Prop3                varchar(100),
   Prop4                varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(100) not null,
   ModifyTime           datetime,
   ModifyUser           varchar(100),
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','01','汉族',1,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','02','蒙古族',2,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','03','回族',3,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','04','藏族',4,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','05','维吾尔族',5,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','06','苗族',6,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','07','彝族',7,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','08','壮族',8,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','09','布依族',9,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','10','朝鲜族',10,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','11','满族',11,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','12','侗族',12,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','13','瑶族',13,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','14','白族',14,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','15','土家族',15,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','16','哈尼族',16,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','17','哈萨克族',17,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','18','傣族',18,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','19','黎族',19,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','20','傈僳族',20,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','21','佤族',21,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','22','畲族',22,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','23','高山族',23,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','24','拉祜族',24,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','25','水族',25,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','26','东乡族',26,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','27','纳西族',27,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','28','景颇族',28,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','29','柯尔克孜',29,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','30','土族',30,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','31','达斡尔族',31,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','32','仫佬族',32,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','33','羌族',33,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','34','布朗族',34,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','35','撒拉族',35,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','36','毛难族',36,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','37','仡佬族',37,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','38','锡伯族',38,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','39','阿昌族',39,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','40','普米族',40,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','41','塔吉克族',41,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','42','怒族',42,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','43','乌孜别克',43,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','44','俄罗斯族',44,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','45','鄂温克族',45,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','46','崩龙族',46,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','47','保安族',47,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','48','裕固族',48,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','49','京族',49,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','50','塔塔尔族',50,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','51','独龙族',51,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','52','鄂伦春族',52,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','53','赫哲族',53,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','54','门巴族',54,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','55','珞巴族',55,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','56','基诺族',56,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','57','其他',97,NULL,NULL,NULL,NULL,'','2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','Ethnicity','58','外国血统',98,NULL,NULL,NULL,NULL,'','2008-12-29 21:10:43','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Ethnicity','System','System','民族',1240230354951,NULL,NULL,NULL,NULL,NULL,'2008-12-29 21:10:43','admin',NULL,NULL);

/* 王少亭 把Article中addUser不存在于zduser表中的用户全部变成admin*/
update ZCArticle set AddUser='admin' and BranchInnerCode=0001 where AddUser not in (select UserName from ZDUser);

/* 自定义表单和自定义数据两个功能合并 */
ALTER TABLE BZCCustomTable ADD COLUMN FormContent TEXT AFTER OldCode;
ALTER TABLE ZCCustomTable ADD COLUMN FormContent TEXT AFTER OldCode;

/* 张金灿 ZCVote 增加字段VoteCheckFlag */
ALTER table ZCVote add COLUMN VerifyFlag char(1) not null after IPLimit;
ALTER table BZCVote add COLUMN VerifyFlag char(1) not null after IPLimit;
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('VerifyFlag','System','System','投票验证码标志',1261635594484,NULL,NULL,NULL,NULL,'','2009-12-24 14:19:54','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('VerifyFlag','VerifyFlag','N','无验证码',1261635666811,NULL,NULL,NULL,NULL,'','2009-12-24 14:21:06','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('VerifyFlag','VerifyFlag','S','验证码',1261635666812,NULL,NULL,NULL,NULL,'','2009-12-24 14:20:54','admin',NULL,NULL);

/*摘要太短，自定义表某些字段需要有自动编号 by wyuch 20091224*/
ALTER TABLE bzcarticle MODIFY COLUMN Summary VARCHAR(2000);
ALTER TABLE zcarticle MODIFY COLUMN Summary VARCHAR(2000);
ALTER TABLE bzccustomtablecolumn ADD COLUMN isAutoID VARCHAR(2) AFTER ListOptions;
ALTER TABLE zccustomtablecolumn ADD COLUMN isAutoID VARCHAR(2) AFTER ListOptions;

/*李伟仪 2009-12-25*/
alter table zcvotelog modify column Result VARCHAR(2000);
alter table bzcvotelog modify column Result VARCHAR(2000);

drop table if exists ZDMember;

/*==============================================================*/
/* Table: ZDMember                                              */
/*==============================================================*/
create table ZDMember
(
   UserName                       varchar(50)                    not null,
   Password                       varchar(32)                    not null,
   Name                           varchar(100)                   not null,
   Email                          varchar(100)                   not null,
   Gender                         char(1),
   Type                           char(10),
   SiteID                         bigint(20),
   Logo                           varchar(100),
   Status                         char(1)                        not null,
   Score                          varchar(20),
   Rank                           varchar(50),
   MemberLevel                    varchar(10),
   PWQuestion                     varchar(100),
   PWAnswer                       varchar(100),
   LastLoginIP                    varchar(16),
   LastLoginTime                  datetime,
   RegTime                        datetime,
   RegIP                          varchar(16),
   Prop1                          varchar(100),
   Prop2                          varchar(100),
   Prop3                          varchar(100),
   Prop4                          varchar(100),
   Prop5                          varchar(100),
   Prop6                          varchar(100),
   primary key (UserName)
);

drop table if exists BZDMember;

/*==============================================================*/
/* Table: BZDMember                                             */
/*==============================================================*/
create table BZDMember
(
   UserName                       varchar(50)                    not null,
   Password                       varchar(32)                    not null,
   Name                           varchar(100)                   not null,
   Email                          varchar(100)                   not null,
   Gender                         char(1),
   Type                           char(10),
   SiteID                         bigint(20),
   Logo                           varchar(100),
   Status                         char(1)                        not null,
   Score                          varchar(20),
   Rank                           varchar(50),
   MemberLevel                    varchar(10),
   PWQuestion                     varchar(100),
   PWAnswer                       varchar(100),
   LastLoginIP                    varchar(16),
   LastLoginTime                  datetime,
   RegTime                        datetime,
   RegIP                          varchar(16),
   Prop1                          varchar(100),
   Prop2                          varchar(100),
   Prop3                          varchar(100),
   Prop4                          varchar(100),
   Prop5                          varchar(100),
   Prop6                          varchar(100),
   BackupNo                       varchar(15)                    not null,
   BackupOperator                 varchar(200)                   not null,
   BackupTime                     datetime                       not null,
   BackupMemo                     varchar(50),
   primary key (UserName, BackupNo)
);

/*huanglei 文章中的图片是否有点击看大图的功能*/
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleImageHeight','文章中图片超过这个高度则点击看大图','600',NULL,NULL,NULL,NULL,NULL,'2009-12-25 17:25:01','admin',NULL,NULL);
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleImageWidth','文章中图片超过这个宽度则点击看大图','800',NULL,NULL,NULL,NULL,NULL,'2009-12-25 17:27:07','admin',NULL,NULL);

/*huanglei 栏目详细页默认命名规则*/
update zccatalog set detailnamerule='/${catalogpath}/${document.id}.shtml';

/*投票默认置成不需要验证码 wyuch*/
update ZCVote set VerifyFlag='N';


INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleAttribute','ArticleAttribute','attchment','附件',1260151120766,NULL,NULL,NULL,NULL,'','2009-12-07 11:27:38','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleAttribute','ArticleAttribute','hot','热点',1260151130501,NULL,NULL,NULL,NULL,'','2009-12-07 09:58:50','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleAttribute','ArticleAttribute','image','图片',1260151107453,NULL,NULL,NULL,NULL,'','2009-12-07 09:58:27','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleAttribute','ArticleAttribute','recommend','推荐',1260156439797,NULL,NULL,NULL,NULL,'','2009-12-07 11:27:19','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleAttribute','ArticleAttribute','video','视频',1260151120765,NULL,NULL,NULL,NULL,'','2009-12-07 09:58:40','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleAttribute','System','System','文档属性',1260150969687,NULL,NULL,NULL,NULL,'','2009-12-07 09:56:09','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Gender','Gender','F','女',1255665814859,NULL,NULL,NULL,NULL,'','2009-10-16 12:03:34','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Gender','Gender','M','男',1255665799000,NULL,NULL,NULL,NULL,'','2009-10-16 12:03:19','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Gender','System','System','性别',1255665516218,NULL,NULL,NULL,NULL,'','2009-10-16 11:58:36','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Member.Type','Member.Type','Company','企业会员',1256030198562,NULL,NULL,NULL,NULL,'','2009-10-20 17:16:38','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Member.Type','Member.Type','Person','个人会员',1255665839578,NULL,NULL,NULL,NULL,'','2009-10-16 12:03:59','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Member.Type','System','System','会员类型',1255665559687,NULL,NULL,NULL,NULL,'','2009-10-16 11:59:19','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Comment.Status','Comment.Status','N','未通过',1257840942359,NULL,NULL,NULL,NULL,'','2009-11-10 16:15:42','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Comment.Status','Comment.Status','X','未审核',1257840916859,NULL,NULL,NULL,NULL,'','2009-11-10 16:15:16','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Comment.Status','Comment.Status','Y','已通过',1257840930156,NULL,NULL,NULL,NULL,'','2009-11-10 16:15:30','admin',NULL,NULL);
INSERT INTO zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('Comment.Status','System','System','评论状态',1257840881750,NULL,NULL,NULL,NULL,'','2009-11-10 16:14:41','admin',NULL,NULL);

/*增加ZCStatItem.Item的长度*/
alter table ZCStatItem modify column Item VARCHAR(150);
alter table BZCStatItem modify column Item VARCHAR(150);