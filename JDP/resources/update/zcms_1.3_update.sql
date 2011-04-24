/*mysql升级时，需设置sql编码为utf8*/
set names utf8;

/*修改ZCCatalog.ListPageSize字段的用途*/
update ZCCatalog set ListPageSize=20;

INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('MessageActionURL','留言提交地址','/SaveMessage.jsp',NULL,NULL,NULL,NULL,NULL,'2010-01-06 14:35:52','admin',NULL,NULL);

drop table if exists ZCMessageBoard;

/*==============================================================*/
/* Table: ZCMessageBoard                                        */
/*==============================================================*/
create table ZCMessageBoard
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(100) not null,
   IsOpen               varchar(2) not null,
   Description          varchar(500),
   MessageCount         varchar(20),
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


drop table if exists ZCBoardMessage;

/*==============================================================*/
/* Table: ZCBoardMessage                                        */
/*==============================================================*/
create table ZCBoardMessage
(
   ID                   bigint not null,
   BoardID              bigint not null,
   Title                varchar(100) not null,
   Content              text not null,
   PublishFlag          varchar(2) not null,
   ReplyFlag            varchar(2) not null,
   ReplyContent         varchar(1000),
   EMail                varchar(100),
   QQ                   varchar(20),
   Prop1                varchar(50),
   Prop2                varchar(50),
   Prop4                varchar(50),
   Prop3                varchar(50),
   IP                   varchar(20) not null,
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (ID)
);

drop table if exists BZCMessageBoard;

/*==============================================================*/
/* Table: BZCMessageBoard                                       */
/*==============================================================*/
create table BZCMessageBoard
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(100) not null,
   IsOpen               varchar(2) not null,
   Description          varchar(500),
   MessageCount         varchar(20),
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

drop table if exists BZCBoardMessage;

/*==============================================================*/
/* Table: BZCBoardMessage                                       */
/*==============================================================*/
create table BZCBoardMessage
(
   ID                   bigint not null,
   BoardID              bigint not null,
   Title                varchar(100) not null,
   Content              text not null,
   PublishFlag          varchar(2) not null,
   ReplyFlag            varchar(2) not null,
   ReplyContent         varchar(1000),
   EMail                varchar(100),
   QQ                   varchar(20),
   Prop1                varchar(50),
   Prop2                varchar(50),
   Prop4                varchar(50),
   Prop3                varchar(50),
   IP                   varchar(20) not null,
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

/*增加工作流表*/
drop table if exists ZWWorkflow;
create table ZWWorkflow
(
   ID                   bigint not null,
   Name                 varchar(100) not null,
   ConfigXML            text,
   Prop1                varchar(40),
   Prop2                varchar(40),
   Prop3                varchar(40),
   Prop4                varchar(40),
   Memo                 varchar(200),
   AddTime              datetime not null,
   AddUser              varchar(50) not null,
   ModifyTime           datetime,
   ModifyUser           varchar(50),
   primary key (ID)
);

drop table if exists ZWInstance;
create table ZWInstance
(
   ID                   bigint not null,
   WorkflowID           bigint not null,
   Name                 varchar(100),
   DataID               varchar(30),
   State                varchar(10),
   Memo                 varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(50) not null,
   primary key (ID)
);

drop table if exists ZWStep;
create table ZWStep
(
   ID                   bigint not null,
   WorkflowID           bigint not null,
   InstanceID           bigint not null,
   DataVersionID        varchar(30),
   NodeID               integer,
   PreviousStepID       bigint,
   Owner                varchar(50),
   StartTime            datetime,
   FinishTime           datetime,
   State                varchar(10),
   Operators            varchar(400),
   AllowUser            varchar(400),
   AllowOrgan           varchar(200),
   AllowRole            varchar(200),
   Memo                 varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(50) not null,
   primary key (ID)
);

drop table if exists BZWWorkflow;
create table BZWWorkflow
(
   ID                   bigint not null,
   Name                 varchar(100) not null,
   ConfigXML            text,
   Prop1                varchar(40),
   Prop2                varchar(40),
   Prop3                varchar(40),
   Prop4                varchar(40),
   Memo                 varchar(200),
   AddTime              datetime not null,
   AddUser              varchar(50) not null,
   ModifyTime           datetime,
   ModifyUser           varchar(50),
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

drop table if exists BZWInstance;
create table BZWInstance
(
   ID                   bigint not null,
   WorkflowID           bigint not null,
   Name                 varchar(100),
   DataID               varchar(30),
   State                varchar(10),
   Memo                 varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(50) not null,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

drop table if exists BZWStep;
create table BZWStep
(
   ID                   bigint not null,
   WorkflowID           bigint not null,
   InstanceID           bigint not null,
   DataVersionID        varchar(30),
   NodeID               integer,
   PreviousStepID       bigint,
   StartTime            datetime,
   FinishTime           datetime,
   State                varchar(10),
   Operators            varchar(400),
   AllowUser            varchar(400),
   AllowOrgan           varchar(200),
   AllowRole            varchar(200),
   Memo                 varchar(100),
   AddTime              datetime not null,
   AddUser              varchar(50) not null,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

/*新的图形化工作流*/
update ZDMenu set URL='Workflow/WorkflowList.jsp' where URL='Platform/Workflow.jsp';


/** 汪维军 2010-01-07 图片播放器增加关联栏目字段RelaCatalogID */ 
ALTER TABLE BZCImagePlayer ADD COLUMN RelaCatalogInnerCode varchar(100) AFTER ImageSource;
ALTER TABLE ZCImagePlayer ADD COLUMN RelaCatalogInnerCode varchar(100) AFTER ImageSource;
update ZCImagePlayer set ImageSource = '0';
ALTER TABLE BZCImagePlayer ADD COLUMN IsShowText varchar(2) not null AFTER DisplayCount;
ALTER TABLE ZCImagePlayer ADD COLUMN IsShowText varchar(2) not null AFTER DisplayCount;
update zcimageplayer set IsShowText='Y';
ALTER TABLE BZCImagePlayer DROP COLUMN prop1;
ALTER TABLE ZCImagePlayer DROP COLUMN prop1;
ALTER TABLE BZCImagePlayer DROP COLUMN prop2;
ALTER TABLE ZCImagePlayer DROP COLUMN prop2;

/*去掉无用的角色关联*/
delete from ZDUserROle where RoleCode not in (select RoleCode from ZDRole) or UserName not in (select UserName from ZDUser);


/*工作流步骤备注字段加长 20100111 by wyuch*/
ALTER TABLE zwstep MODIFY COLUMN Memo VARCHAR(400);
ALTER TABLE bzwstep MODIFY COLUMN Memo VARCHAR(400);

update ZCCatalog set Workflow=null;
/** 汪维军 2010-01-11 栏目增加列表页最大分页数字段ListPage */
ALTER TABLE BZCCatalog ADD COLUMN ListPage bigint not null AFTER ListPageSize;
ALTER TABLE ZCCatalog ADD COLUMN ListPage bigint not null AFTER ListPageSize;
update ZCCatalog set ListPageSize=20;
update ZCCatalog set ListPage=-1;

/** 汪维军 2010-01-12 媒体库默认栏目设置为不可删除 */
update ZCCatalog set Prop4='N' where name='默认图片' and type=4;
update ZCCatalog set Prop4='N' where name='默认视频' and type=5;
update ZCCatalog set Prop4='N' where name='默认音频' and type=6;
update ZCCatalog set Prop4='N' where name='默认附件' and type=7;
/* 王少亭 2010-01-12 修改字段 长度 */
alter table BZDUserLog change LogType LogType varchar(20);
alter table BZDUserLog change SubType SubType varchar(20);

/** 广告新增功能 版位表修改 徐喆 2010-01-14 */
alter table zcadposition add COLUMN Align varchar(2) after PositionHeight;
alter table bzcadposition add COLUMN Align varchar(2) after PositionHeight;
alter table zcadposition add COLUMN Scroll varchar(2) after Align;
alter table bzcadposition add COLUMN Scroll varchar(2) after Align;


/** 分发添加字段 操作Operation 汪维军 2010-01-14 */
alter table ZCDeployJob add column Operation varchar(100) after Method;
alter table BZCDeployJob add column Operation varchar(100) after Method;
update ZCDeployJob set Operation='copy';


/** 站点表修改字段OrderFlag 汪维军 2010-01-16 */
update zcsite set orderflag='0';
update bzcsite set orderflag='0';
ALTER table zcsite modify column orderflag bigint;
ALTER table bzcsite modify column orderflag bigint;
update zcsite set orderflag='0';
update bzcsite set orderflag='0';

/** 添加商品品牌表 2010-01-18 */
drop table if exists BZSBrand;

/*==============================================================*/
/* Table: BZSBrand                                              */
/*==============================================================*/
create table BZSBrand
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(100) not null,
   BranchInnerCode      varchar(100),
   Alias                varchar(100) not null,
   URL                  varchar(100),
   ImagePath            varchar(50),
   Info                 varchar(1024),
   IndexTemplate        varchar(200),
   ListTemplate         varchar(200),
   ListNameRule         varchar(200),
   DetailTemplate       varchar(200),
   DetailNameRule       varchar(200),
   RssTemplate          varchar(200),
   RssNameRule          varchar(200),
   OrderFlag            bigint not null,
   ListPageSize         bigint,
   ListPage             bigint,
   PublishFlag          varchar(2) not null comment '0-否 1-是',
   SingleFlag           varchar(2),
   HitCount             bigint,
   Meta_Keywords        varchar(200),
   Meta_Description     varchar(200),
   KeywordFlag          varchar(2),
   KeywordSetting       varchar(50),
   Memo                 varchar(200),
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

drop table if exists ZSBrand;

/*==============================================================*/
/* Table: ZSBrand                                               */
/*==============================================================*/
create table ZSBrand
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(100) not null,
   BranchInnerCode      varchar(100),
   Alias                varchar(100) not null,
   URL                  varchar(100),
   ImagePath            varchar(50),
   Info                 varchar(1024),
   IndexTemplate        varchar(200),
   ListTemplate         varchar(200),
   ListNameRule         varchar(200),
   DetailTemplate       varchar(200),
   DetailNameRule       varchar(200),
   RssTemplate          varchar(200),
   RssNameRule          varchar(200),
   OrderFlag            bigint not null,
   ListPageSize         bigint,
   ListPage             bigint,
   PublishFlag          varchar(2) not null comment '0-否
            1-是',
   SingleFlag           varchar(2),
   HitCount             bigint,
   Meta_Keywords        varchar(200),
   Meta_Description     varchar(200),
   KeywordFlag          varchar(2),
   KeywordSetting       varchar(50),
   Memo                 varchar(200),
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

/** 崔建成 2010-01-19 将热点词类型设置为varchar类型 */
alter table ZCKeyword change COLUMN KeywordType KeywordType varchar(255);
alter table BZCKeyword change COLUMN KeywordType KeywordType varchar(255);

/** 崔建成 2010-01-20 将热点词类标识改为热点词类型 */
update ZCCatalogConfig set HotWordFlag = 0;
alter table ZCCatalogConfig change COLUMN HotWordFlag HotWordType bigint;
alter table BZCCatalogConfig change COLUMN HotWordFlag HotWordType bigint;

/** 李伟仪 2010-01-19 ZSOrderItem */
drop table if exists ZSOrderItem;

/*==============================================================*/
/* Table: ZSOrderItem                                           */
/*==============================================================*/
create table ZSOrderItem
(
   OrderID              bigint not null,
   GoodsID              bigint not null,
   SiteID               bigint not null,
   MemberCode           varchar(200),
   SN                   varchar(50),
   Name                 varchar(200),
   Price                float(12,2),
   Discount             float(12,2),
   DiscountPrice        float(12,2),
   Count                bigint,
   Amount               float,
   Score                bigint,
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (OrderID, GoodsID)
);

drop table if exists BZSOrderItem;

/*==============================================================*/
/* Table: BZSOrderItem                                           */
/*==============================================================*/
create table BZSOrderItem
(
   OrderID              bigint not null,
   GoodsID              bigint not null,
   SiteID               bigint not null,
   MemberCode           varchar(200),
   SN                   varchar(50),
   Name                 varchar(200),
   Price                float(12,2),
   Discount             float(12,2),
   DiscountPrice        float(12,2),
   Count                bigint,
   Amount               float,
   Score                bigint,
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (OrderID, GoodsID)
);

/** 李伟仪 2010-01-19 BZSOrder */
drop table if exists BZSOrder;

/*==============================================================*/
/* Table: BZSOrder                                               */
/*==============================================================*/
create table BZSOrder
(
   ID                   bigint not null,
   SiteID               bigint not null,
   MemberCode           varchar(200),
   IsValid              varchar(1),
   Status               varchar(40),
   Amount               float(12,2) not null,
   Score                bigint not null,
   Name                 varchar(30),
   Province             varchar(6),
   City                 varchar(6),
   District             varchar(6),
   Address              varchar(255),
   ZipCode              varchar(10),
   Tel                  varchar(20),
   Mobile               varchar(20),
   HasInvoice           varchar(1) not null,
   InvoiceTitle         varchar(100),
   SendBeginDate        date,
   SendEndDate          date,
   SendTimeSlice        varchar(40),
   SendInfo             varchar(200),
   SendType             varchar(40),
   PaymentType          varchar(40),
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID)
);

drop table if exists ZSSend;

/*==============================================================*/
/* Table: ZSSend                                                */
/*==============================================================*/
create table ZSSend
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(200),
   SendInfo             varchar(200),
   ArriveInfo           varchar(200),
   Info                 varchar(200),
   Price                float(12,2),
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (ID)
);

drop table if exists BZSSend;

/*==============================================================*/
/* Table: BZSSend                                               */
/*==============================================================*/
create table BZSSend
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(200),
   SendInfo             varchar(200),
   ArriveInfo           varchar(200),
   Info                 varchar(200),
   Price                float(12,2),
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
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

drop table if exists ZSPayment;

/*==============================================================*/
/* Table: ZSPayment                                             */
/*==============================================================*/
create table ZSPayment
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(200),
   Info                 varchar(1000),
   IsVisible            varchar(1),
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (ID)
);

drop table if exists BZSPayment;

/*==============================================================*/
/* Table: BZSPayment                                            */
/*==============================================================*/
create table BZSPayment
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(200),
   Info                 varchar(1000),
   IsVisible            varchar(1),
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
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

drop table if exists ZSGoods;

/** 李伟仪 2010-01-20 ZSGoods,BZSGoods */
/*==============================================================*/
/* Table: ZSGoods                                               */
/*==============================================================*/
create table ZSGoods
(
   ID                   bigint not null,
   SiteID               bigint not null,
   CatalogID            bigint not null,
   CatalogInnerCode     varchar(100) not null,
   BrandID              bigint,
   BranchInnerCode      varchar(100),
   Type                 varchar(2) not null comment '1 普通文章
            2 图片新闻
            3 视频新闻
            4 标题新闻',
   SN                   varchar(50),
   Name                 varchar(100) not null,
   Alias                varchar(100),
   BarCode              varchar(128),
   WorkFlowID           bigint,
   Status               varchar(20),
   Factory              varchar(100),
   OrderFlag            bigint,
   MarketPrice          float(12,2),
   Price                float(12,2),
   MemberPrice          float(12,2),
   VIPPrice             float(12,2),
   EffectDate           datetime,
   Store                bigint not null,
   Standard             varchar(100),
   Unit                 varchar(10),
   Score                bigint not null,
   CommentCount         bigint not null,
   SaleCount            bigint not null,
   HitCount             bigint not null,
   Image0               varchar(200),
   Image1               varchar(200),
   Image2               varchar(200),
   Image3               varchar(200),
   RelativeGoods        varchar(100),
   Keyword              varchar(100),
   TopDate              datetime,
   TopFlag              varchar(2) not null comment '0-不置顶
            1-置顶',
   Content              mediumtext,
   Summary              varchar(2000),
   RedirectURL          varchar(200),
   Attribute            varchar(100) comment '设置文章属性，如推荐、热点、图片、视频、音频、附件等，可以扩展',
   RecommendGoods       varchar(100),
   StickTime            bigint not null,
   PublishDate          Datetime,
   DownlineDate         datetime,
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (ID)
);

drop table if exists BZSGoods;

/*==============================================================*/
/* Table: BZSGoods                                               */
/*==============================================================*/
create table BZSGoods
(
   ID                   bigint not null,
   SiteID               bigint not null,
   CatalogID            bigint not null,
   CatalogInnerCode     varchar(100) not null,
   BrandID              bigint,
   BranchInnerCode      varchar(100),
   Type                 varchar(2) not null comment '1 普通文章
            2 图片新闻
            3 视频新闻
            4 标题新闻',
   SN                   varchar(50),
   Name                 varchar(100) not null,
   Alias                varchar(100),
   BarCode              varchar(128),
   WorkFlowID           bigint,
   Status               varchar(20),
   Factory              varchar(100),
   OrderFlag            bigint,
   MarketPrice          float(12,2),
   Price                float(12,2),
   MemberPrice          float(12,2),
   VIPPrice             float(12,2),
   EffectDate           datetime,
   Store                bigint not null,
   Standard             varchar(100),
   Unit                 varchar(10),
   Score                bigint not null,
   CommentCount         bigint not null,
   SaleCount            bigint not null,
   HitCount             bigint not null,
   Image0               varchar(200),
   Image1               varchar(200),
   Image2               varchar(200),
   Image3               varchar(200),
   RelativeGoods        varchar(100),
   Keyword              varchar(100),
   TopDate              datetime,
   TopFlag              varchar(2) not null comment '0-不置顶
            1-置顶',
   Content              mediumtext,
   Summary              varchar(2000),
   RedirectURL          varchar(200),
   Attribute            varchar(100) comment '设置文章属性，如推荐、热点、图片、视频、音频、附件等，可以扩展',
   RecommendGoods       varchar(100),
   StickTime            bigint not null,
   PublishDate          Datetime,
   DownlineDate         datetime,
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID)
);

/*	李伟仪 添加会员商品收藏夹表ZSFavorite	*/
drop table if exists ZSFavorite;

/*==============================================================*/
/* Table: ZSFavorite                                            */
/*==============================================================*/
create table ZSFavorite
(
   UserName                       varchar(200)                   not null,
   GoodsID                        bigint                         not null,
   AddUser                        varchar(200)                   not null,
   AddTime                        datetime                       not null,
   ModifyUser                     varchar(200),
   ModifyTime                     datetime,
   primary key (UserName, GoodsID)
);

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
   Gender                         varchar(1),
   Type                           varchar(10),
   SiteID                         bigint(20),
   Logo                           varchar(100),
   Status                         varchar(1)                     not null,
   Score                          varchar(20),
   Rank                           varchar(50),
   MemberLevel                    varchar(10),
   PWQuestion                     varchar(100),
   PWAnswer                       varchar(100),
   LastLoginIP                    varchar(16),
   LastLoginTime                  datetime,
   RegTime                        datetime,
   RegIP                          varchar(16),
   LoginMD5                       varchar(32),
   Prop1                          varchar(100),
   Prop2                          varchar(100),
   Prop3                          varchar(100),
   Prop4                          varchar(100),
   Prop5                          varchar(100),
   Prop6                          varchar(100),
   Prop7                          varchar(100),
   Prop8                          varchar(100),
   Prop9                          varchar(100),
   Prop10                         varchar(100),
   Prop11                         varchar(100),
   Prop12                         varchar(100),
   Prop13                         varchar(100),
   Prop14                         varchar(100),
   Prop15                         varchar(100),
   Prop16                         varchar(100),
   Prop17                         varchar(100),
   Prop18                         varchar(100),
   Prop19                         varchar(100),
   Prop20                         varchar(100),
   primary key (UserName)
);

drop table if exists ZDMemberField;

/*==============================================================*/
/* Table: ZDMemberField                                         */
/*==============================================================*/
create table ZDMemberField
(
   SiteID                         bigint                         not null,
   Name                           varchar(50),
   Code                           varchar(50)                    not null,
   RealField                      varchar(20),
   VerifyType                     varchar(2)                     not null,
   MaxLength                      smallint,
   InputType                      varchar(20)                    not null,
   DefaultValue                   varchar(50),
   ListOption                     varchar(1000),
   HTML                           text,
   IsMandatory                    varchar(2)                     not null,
   OrderFlag                      bigint,
   RowSize                        smallint,
   ColSize                        smallint,
   AddUser                        varchar(200)                   not null,
   AddTime                        datetime                       not null,
   ModifyUser                     varchar(200),
   ModifyTime                     datetime,
   primary key (SiteID, Code)
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
   Gender                         varchar(1),
   Type                           varchar(10),
   SiteID                         bigint(20),
   Logo                           varchar(100),
   Status                         varchar(1)                     not null,
   Score                          varchar(20),
   Rank                           varchar(50),
   MemberLevel                    varchar(10),
   PWQuestion                     varchar(100),
   PWAnswer                       varchar(100),
   LastLoginIP                    varchar(16),
   LastLoginTime                  datetime,
   RegTime                        datetime,
   RegIP                          varchar(16),
   LoginMD5                       varchar(32),
   Prop1                          varchar(100),
   Prop2                          varchar(100),
   Prop3                          varchar(100),
   Prop4                          varchar(100),
   Prop5                          varchar(100),
   Prop6                          varchar(100),
   Prop7                          varchar(100),
   Prop8                          varchar(100),
   Prop9                          varchar(100),
   Prop10                         varchar(100),
   Prop11                         varchar(100),
   Prop12                         varchar(100),
   Prop13                         varchar(100),
   Prop14                         varchar(100),
   Prop15                         varchar(100),
   Prop16                         varchar(100),
   Prop17                         varchar(100),
   Prop18                         varchar(100),
   Prop19                         varchar(100),
   Prop20                         varchar(100),
   BackupNo                       varchar(15)                    not null,
   BackupOperator                 varchar(200)                   not null,
   BackupTime                     datetime                       not null,
   BackupMemo                     varchar(50),
   primary key (UserName, BackupNo)
);

drop table if exists BZDMemberField;

/*==============================================================*/
/* Table: BZDMemberField                                        */
/*==============================================================*/
create table BZDMemberField
(
   SiteID                         bigint                         not null,
   Name                           varchar(50),
   Code                           varchar(50)                    not null,
   RealField                      varchar(20),
   VerifyType                     varchar(2)                     not null,
   MaxLength                      smallint,
   InputType                      varchar(20)                    not null,
   DefaultValue                   varchar(50),
   ListOption                     varchar(1000),
   HTML                           text,
   IsMandatory                    varchar(2)                     not null,
   OrderFlag                      bigint,
   RowSize                        smallint,
   ColSize                        smallint,
   AddUser                        varchar(200)                   not null,
   AddTime                        datetime                       not null,
   ModifyUser                     varchar(200),
   ModifyTime                     datetime,
   BackupNo                       varchar(15)                    not null,
   BackupOperator                 varchar(200)                   not null,
   BackupTime                     datetime                       not null,
   BackupMemo                     varchar(50),
   primary key (SiteID, Code, BackupNo)
);

/*增加全站动态应用相关的模板设置 2010-01-29 by wyuch*/
ALTER TABLE zcsite ADD COLUMN HeaderTemplate VARCHAR(100) AFTER AutoStatFlag;
ALTER TABLE zcsite ADD COLUMN TopTemplate VARCHAR(100) AFTER HeaderTemplate;
ALTER TABLE zcsite ADD COLUMN BottomTemplate VARCHAR(100) AFTER TopTemplate;
ALTER TABLE bzcsite ADD COLUMN HeaderTemplate VARCHAR(100) AFTER AutoStatFlag;
ALTER TABLE bzcsite ADD COLUMN TopTemplate VARCHAR(100) AFTER HeaderTemplate;
ALTER TABLE bzcsite ADD COLUMN BottomTemplate VARCHAR(100) AFTER TopTemplate;

/**王少亭 2010-01-31**/
ALTER TABLE ZCForumGroup ADD COLUMN BestTheme varchar(2) AFTER Verify;
ALTER TABLE ZCForumGroup ADD COLUMN BrightTheme varchar(2) AFTER Verify;
ALTER TABLE ZCForumGroup ADD COLUMN UpOrDownTheme varchar(2) AFTER Verify;
ALTER TABLE ZCForumGroup ADD COLUMN TopTheme varchar(2) AFTER Verify;
ALTER TABLE ZCForumGroup ADD COLUMN MoveTheme varchar(2) AFTER Verify;
ALTER TABLE ZCForumGroup ADD COLUMN RemoveTheme varchar(2) AFTER Verify;
ALTER TABLE ZCForumGroup ADD COLUMN Hide varchar(2) AFTER Verify;
ALTER TABLE ZCForumGroup ADD COLUMN AllowEdit varchar(2) AFTER Verify;
ALTER TABLE ZCForumGroup ADD COLUMN AllowDel varchar(2) AFTER Verify;
ALTER TABLE ZCForumGroup ADD COLUMN AllowVerfyPost varchar(2) AFTER Verify;
ALTER TABLE ZCForumGroup ADD COLUMN AllowEditForum varchar(2) AFTER Verify;
ALTER TABLE ZCForumGroup ADD COLUMN AllowForbidUser varchar(2) AFTER Verify;
ALTER TABLE ZCForumGroup ADD COLUMN AllowEditUser varchar(2) AFTER Verify;

ALTER TABLE BZCForumGroup ADD COLUMN BestTheme varchar(2) AFTER Verify;
ALTER TABLE BZCForumGroup ADD COLUMN BrightTheme varchar(2) AFTER Verify;
ALTER TABLE BZCForumGroup ADD COLUMN UpOrDownTheme varchar(2) AFTER Verify;
ALTER TABLE BZCForumGroup ADD COLUMN TopTheme varchar(2) AFTER Verify;
ALTER TABLE BZCForumGroup ADD COLUMN MoveTheme varchar(2) AFTER Verify;
ALTER TABLE BZCForumGroup ADD COLUMN RemoveTheme varchar(2) AFTER Verify;
ALTER TABLE BZCForumGroup ADD COLUMN Hide varchar(2) AFTER Verify;
ALTER TABLE BZCForumGroup ADD COLUMN AllowEdit varchar(2) AFTER Verify;
ALTER TABLE BZCForumGroup ADD COLUMN AllowDel varchar(2) AFTER Verify;
ALTER TABLE BZCForumGroup ADD COLUMN AllowVerfyPost varchar(2) AFTER Verify;
ALTER TABLE BZCForumGroup ADD COLUMN AllowEditForum varchar(2) AFTER Verify;
ALTER TABLE BZCForumGroup ADD COLUMN AllowForbidUser varchar(2) AFTER Verify;
ALTER TABLE BZCForumGroup ADD COLUMN AllowEditUser varchar(2) AFTER Verify;

ALTER TABLE ZCForumMember ADD COLUMN DefinedID bigint AFTER UserGroupID;
ALTER TABLE BZCForumMember ADD COLUMN DefinedID bigint AFTER UserGroupID;



ALTER TABLE ZCForum ADD COLUMN UnLockID varchar(300) AFTER Locked;
ALTER TABLE ZCForum ADD COLUMN UnPasswordID varchar(300) AFTER Password;

/*外部数据库增加拉丁字符集标志 2010-02-01*/
ALTER TABLE zcdatabase ADD COLUMN Latin1Flag VARCHAR(2) AFTER TestTable;
ALTER TABLE bzcdatabase ADD COLUMN Latin1Flag VARCHAR(2) AFTER TestTable;

/*工作流步骤增加来源动作ID 2010-02-01 by wyuch*/
ALTER TABLE zwstep ADD COLUMN ActionID INTEGER AFTER NodeID;
ALTER TABLE bzwstep ADD COLUMN ActionID INTEGER AFTER NodeID;

/*站点增加允许投稿的设置项 2010-02-05*/
ALTER TABLE zcsite ADD COLUMN AllowContribute VARCHAR(2) AFTER BottomTemplate;
ALTER TABLE bzcsite ADD COLUMN AllowContribute VARCHAR(2) AFTER BottomTemplate;

/*消息表主题长度不够*/
ALTER TABLE zcmessage MODIFY COLUMN Subject VARCHAR(500);
ALTER TABLE bzcmessage MODIFY COLUMN Subject VARCHAR(500);

/*站点的配置要将CatalogID设为0，不要设为null 2010-02-06*/
update ZCCatalogConfig set CatalogID=0 where CatalogID is null or CatalogID='';

ALTER TABLE zcmessage ADD COLUMN PopFlag BIGINT AFTER ReadFlag;
ALTER TABLE bzcmessage ADD COLUMN PopFlag BIGINT AFTER ReadFlag;

/*王少亭漏加了B表,2010-02-06*/
ALTER TABLE BZCForum ADD COLUMN UnLockID varchar(300) AFTER Locked;
ALTER TABLE BZCForum ADD COLUMN UnPasswordID varchar(300) AFTER Password;

/*谭喜才修改zcstatitem的字段item的长度*/
alter table zcstatitem change item item  varchar(200);

/*修改zdcode,zdconfig两表字段长度，以免UTF8下字符超长*/
ALTER TABLE zdconfig MODIFY COLUMN Name VARCHAR(100);
ALTER TABLE zdconfig MODIFY COLUMN Memo VARCHAR(400);
ALTER TABLE zdcode MODIFY COLUMN CodeName VARCHAR(100);
ALTER TABLE zdcode MODIFY COLUMN Memo VARCHAR(400);
ALTER TABLE bzdconfig MODIFY COLUMN Name VARCHAR(100);
ALTER TABLE bzdconfig MODIFY COLUMN Memo VARCHAR(400);
ALTER TABLE bzdcode MODIFY COLUMN CodeName VARCHAR(100);
ALTER TABLE bzdcode MODIFY COLUMN Memo VARCHAR(400);
 
 /**增加ZDMemberAddr表，用于会员地址功能**/
 drop table if exists ZDMemberAddr;

/*==============================================================*/
/* Table: ZDMemberAddr                                          */
/*==============================================================*/
create table ZDMemberAddr
(
   ID                             bigint(20)                     not null,
   UserName                       varchar(200)                   not null,
   RealName                       varchar(100),
   Country                        varchar(30),
   Province                       varchar(6),
   City                           varchar(6),
   District                       varchar(6),
   Address                        varchar(255),
   ZipCode                        varchar(10),
   Tel                            varchar(20),
   Mobile                         varchar(20),
   Email                          varchar(100),
   IsDefault                      varchar(2),
   AddUser                        varchar(200)                   not null,
   AddTime                        datetime                       not null,
   ModifyUser                     varchar(200),
   ModifyTime                     datetime,
   primary key (ID)
);

drop table if exists BZDMemberAddr;

/*==============================================================*/
/* Table: BZDMemberAddr                                         */
/*==============================================================*/
create table BZDMemberAddr
(
   ID                             bigint(20)                     not null,
   UserName                       varchar(200)                   not null,
   RealName                       varchar(100),
   Country                        varchar(30),
   Province                       varchar(6),
   City                           varchar(6),
   District                       varchar(6),
   Address                        varchar(255),
   ZipCode                        varchar(10),
   Tel                            varchar(20),
   Mobile                         varchar(20),
   Email                          varchar(100),
   IsDefault                      varchar(2),
   AddUser                        varchar(200)                   not null,
   AddTime                        datetime                       not null,
   ModifyUser                     varchar(200),
   ModifyTime                     datetime,
   BackupNo                       varchar(15)                    not null,
   BackupOperator                 varchar(200)                   not null,
   BackupTime                     datetime                       not null,
   BackupMemo                     varchar(50),
   primary key (ID, BackupNo)
);

/*===========================商品添加折扣和优惠价格字段 by 李伟仪===================================*/
ALTER TABLE ZSGoods ADD COLUMN Discount float(12,2) AFTER Price;
ALTER TABLE ZSGoods ADD COLUMN OfferPrice float(12,2) AFTER Discount;
ALTER TABLE BZSGoods ADD COLUMN Discount float(12,2) AFTER Price;
ALTER TABLE BZSGoods ADD COLUMN OfferPrice float(12,2) AFTER Discount;

/*===========================用户收藏夹表添加站点ID by 李伟仪===================================*/
ALTER TABLE ZSFavorite ADD COLUMN SiteID bigint AFTER GoodsID;

/*===========================删除zdconfig表无用的配置信息 by huanglei 20100319===================================*/
delete from zdconfig where type='Privilege.OwnerType.Role';
delete from zdconfig where type='Privilege.OwnerType.User';

/*===========================添加商品属性表，记录不同支付方式的特殊属性 by 李伟仪， 2010-03-24===================================*/
drop table if exists ZSPaymentProp;

/*==============================================================*/
/* Table: ZSPaymentProp                                         */
/*==============================================================*/
create table ZSPaymentProp
(
   ID                   bigint not null,
   PaymentID            bigint not null,
   PropName             varchar(200),
   PropValue            varchar(200),
   Memo                 varchar(1000),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (ID)
);

/*===========================把原来的MemberCode都改成UserName by 黄雷， 2010-03-25===================================*/
drop table if exists ZSOrder;

/*==============================================================*/
/* Table: ZSOrder                                               */
/*==============================================================*/
create table ZSOrder
(
   ID                   bigint not null,
   SiteID               bigint not null,
   UserName             varchar(200),
   IsValid              varchar(1),
   Status               varchar(40),
   Amount               float(12,2) not null,
   SendFee              float(12,2),
   OrderAmount          float(12,2),
   Score                bigint not null,
   Name                 varchar(30),
   Province             varchar(6),
   City                 varchar(6),
   District             varchar(6),
   Address              varchar(255),
   ZipCode              varchar(10),
   Tel                  varchar(20),
   Mobile               varchar(20),
   HasInvoice           varchar(1) not null,
   InvoiceTitle         varchar(100),
   SendBeginDate        date,
   SendEndDate          date,
   SendTimeSlice        varchar(40),
   SendInfo             varchar(200),
   SendType             varchar(40),
   PaymentType          varchar(40),
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (ID)
);

drop table if exists ZSOrderItem;

/*==============================================================*/
/* Table: ZSOrderItem                                           */
/*==============================================================*/
create table ZSOrderItem
(
   OrderID              bigint not null,
   GoodsID              bigint not null,
   SiteID               bigint not null,
   UserName             varchar(200),
   SN                   varchar(50),
   Name                 varchar(200),
   Price                float(12,2),
   Discount             float(12,2),
   DiscountPrice        float(12,2),
   Count                bigint,
   Amount               float,
   Score                bigint,
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (OrderID, GoodsID)
);

drop table if exists BZSOrder;

/*==============================================================*/
/* Table: BZSOrder                                               */
/*==============================================================*/
create table BZSOrder
(
   ID                   bigint not null,
   SiteID               bigint not null,
   UserName             varchar(200),
   IsValid              varchar(1),
   Status               varchar(40),
   Amount               float(12,2) not null,
   SendFee              float(12,2),
   OrderAmount          float(12,2),
   Score                bigint not null,
   Name                 varchar(30),
   Province             varchar(6),
   City                 varchar(6),
   District             varchar(6),
   Address              varchar(255),
   ZipCode              varchar(10),
   Tel                  varchar(20),
   Mobile               varchar(20),
   HasInvoice           varchar(1) not null,
   InvoiceTitle         varchar(100),
   SendBeginDate        date,
   SendEndDate          date,
   SendTimeSlice        varchar(40),
   SendInfo             varchar(200),
   SendType             varchar(40),
   PaymentType          varchar(40),
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   BackupNo                       varchar(15)                    not null,
   BackupOperator                 varchar(200)                   not null,
   BackupTime                     datetime                       not null,
   BackupMemo                     varchar(50),
   primary key (ID, BackupNo)
);

drop table if exists BZSOrderItem;

/*==============================================================*/
/* Table: BZSOrderItem                                           */
/*==============================================================*/
create table BZSOrderItem
(
   OrderID              bigint not null,
   GoodsID              bigint not null,
   SiteID               bigint not null,
   UserName             varchar(200),
   SN                   varchar(50),
   Name                 varchar(200),
   Price                float(12,2),
   Discount             float(12,2),
   DiscountPrice        float(12,2),
   Count                bigint,
   Amount               float,
   Score                bigint,
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   BackupNo                       varchar(15)                    not null,
   BackupOperator                 varchar(200)                   not null,
   BackupTime                     datetime                       not null,
   BackupMemo                     varchar(50),
   primary key (OrderID, GoodsID, BackupNo)
);

/*===========================支付方式表添加ImagePath字段 by 李伟仪， 2010-03-25===================================*/
ALTER TABLE ZSPayment ADD COLUMN ImagePath varchar(200) AFTER IsVisible;
ALTER TABLE BZSPayment ADD COLUMN ImagePath varchar(200) AFTER IsVisible;

/*================工作流步骤表的权限相关字段长段加大================*/
ALTER TABLE zwstep MODIFY COLUMN AllowUser VARCHAR(4000) DEFAULT NULL;
ALTER TABLE zwstep MODIFY COLUMN AllowOrgan VARCHAR(4000) DEFAULT NULL;
ALTER TABLE zwstep MODIFY COLUMN AllowRole VARCHAR(4000) DEFAULT NULL;
ALTER TABLE bzwstep MODIFY COLUMN AllowUser VARCHAR(4000) DEFAULT NULL;
ALTER TABLE bzwstep MODIFY COLUMN AllowOrgan VARCHAR(4000) DEFAULT NULL;
ALTER TABLE bzwstep MODIFY COLUMN AllowRole VARCHAR(4000) DEFAULT NULL;
 
 /** 2010-04-02 增加广告、留言板、投票跟栏目关联的功能字段 **/
 alter table zcadposition add column RelaCatalogID bigint after JsName;
 alter table bzcadposition add column RelaCatalogID bigint after JsName;
 alter table ZCMessageBoard add column RelaCatalogID bigint after MessageCount;
 alter table BZCMessageBoard add column RelaCatalogID bigint after MessageCount;
 alter table ZCVote add column RelaCatalogID bigint after Width;
 alter table BZCVote add column RelaCatalogID bigint after Width;
 
/*2010-04-02 为媒体文件增加发布状态字段 wyuch*/
ALTER TABLE zcvideo drop COLUMN Status;
ALTER TABLE bzcvideo drop COLUMN Status;

ALTER TABLE zcvideo ADD COLUMN Status bigint AFTER SourceURL;
ALTER TABLE zcaudio ADD COLUMN Status bigint AFTER SourceURL;
ALTER TABLE zcimage ADD COLUMN Status bigint AFTER SourceURL;
ALTER TABLE zcattachment ADD COLUMN Status bigint AFTER SourceURL;
ALTER TABLE bzcvideo ADD COLUMN Status bigint AFTER SourceURL;
ALTER TABLE bzcaudio ADD COLUMN Status bigint AFTER SourceURL;
ALTER TABLE bzcimage ADD COLUMN Status bigint AFTER SourceURL;
ALTER TABLE bzcattachment ADD COLUMN Status bigint AFTER SourceURL;

/*2010-04-06 添加商品收藏夹备份表，商品收藏夹表添加降价提醒标识字段 by 李伟仪*/
drop table if exists BZSFavorite;
/*==============================================================*/
/* Table: BZSFavorite                                            */
/*==============================================================*/
create table BZSFavorite
(
   UserName             varchar(200) not null,
   GoodsID              bigint not null,
   SiteID               bigint,
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (UserName, GoodsID)
);
ALTER TABLE ZSFavorite ADD COLUMN PriceNoteFlag char(2) AFTER SiteID;
ALTER TABLE BZSFavorite ADD COLUMN PriceNoteFlag char(2) AFTER SiteID;

/*bzwstep未加Owner字段 by wyuch 2010-04-06*/
ALTER TABLE bzwstep ADD COLUMN Owner VARCHAR(50) AFTER PreviousStepID;

/*huanglei 增加文章投票功能*/
 alter table ZCVote add column VoteCatalogID bigint after RelaCatalogID;
 alter table BZCVote add column VoteCatalogID bigint after RelaCatalogID;
 alter table ZCVoteSubject add column VoteCatalogID bigint after Subject;
 alter table BZCVoteSubject add column VoteCatalogID bigint after Subject;
 alter table ZCVoteItem add column VoteDocID bigint after ItemType;
 alter table BZCVoteItem add column VoteDocID bigint after ItemType;
 
 alter table ZCVoteSubject add column OrderFlag bigint after VoteCatalogID;
 alter table BZCVoteSubject add column OrderFlag bigint after VoteCatalogID;
 alter table ZCVoteItem add column OrderFlag bigint after VoteDocID;
 alter table BZCVoteItem add column OrderFlag bigint after VoteDocID;

/*调查，广告，图片播放器，留言板升级时都需要将RelaCatalogID置为0*/
update ZCAdposition set RelaCatalogID=0;
update ZCVote set RelaCatalogID=0;
update ZCMessageBoard set RelaCatalogID=0;
update ZCImagePlayer set RelaCatalogInnerCode='0';

/*增加商城缺失的B表 2010-04-23 by wyuch*/
drop table if exists BZSShopConfig;
create table BZSShopConfig  (
   SiteID             bigint                          not null,
   Name               varchar(50),
   Info               varchar(1024),
   prop1              varchar(50),
   prop2              varchar(50),
   prop3              varchar(50),
   prop4              varchar(50),
   AddUser            varchar(100)                    not null,
   AddTime            datetime                        not null,
   ModifyUser         varchar(100),
   ModifyTime         datetime,
   BackupNo           varchar(15)                    not null,
   BackupOperator     varchar(200)                   not null,
   BackupTime         date                            not null,
   BackupMemo         varchar(50),
   primary key (SiteID, BackupNo)
);

drop table if exists BZSStore;
create table BZSStore  (
   StoreCode          varchar(100)                    not null,
   ParentCode         varchar(100)                    not null,
   Name               varchar(100)                    not null,
   Alias              varchar(100),
   TreeLevel          bigint                          not null,
   SiteID             bigint                          not null,
   OrderFlag          bigint                          not null,
   URL                  varchar(100),
   Info               varchar(2000),
   Country            varchar(30),
   Province           char(6),
   City               char(6),
   District           char(6),
   Address            varchar(400),
   ZipCode            varchar(10),
   Tel                varchar(20),
   Fax                varchar(20),
   Mobile             varchar(30),
   Contacter          varchar(40),
   ContacterEmail     varchar(100),
   ContacterTel       varchar(20),
   ContacterMobile    varchar(20),
   ContacterQQ        varchar(20),
   ContacterMSN       varchar(50),
   Memo               varchar(200),
   Prop1              varchar(200),
   Prop2              varchar(200),
   Prop3              varchar(200),
   Prop4              varchar(200),
   AddUser            varchar(200)                    not null,
   AddTime            datetime                        not null,
   ModifyUser         varchar(200),
   ModifyTime         datetime,
   BackupNo           varchar(15)                    not null,
   BackupOperator     varchar(200)                   not null,
   BackupTime         date                            not null,
   BackupMemo         varchar(50),
   primary key (StoreCode, BackupNo)
);

drop table if exists BZSFavorite;
create table BZSFavorite  (
   UserName           varchar(200)                    not null,
   GoodsID            bigint                          not null,
   SiteID             bigint,
   PriceNoteFlag      char(2),
   AddUser            varchar(200)                    not null,
   AddTime            datetime                        not null,
   ModifyUser         varchar(200),
   ModifyTime         datetime,
   BackupNo           varchar(15)                    not null,
   BackupOperator     varchar(200)                   not null,
   BackupTime         date                            not null,
   BackupMemo         varchar(50),
   primary key (UserName, GoodsID, BackupNo)
);

drop table if exists BZSSend;
create table BZSSend  (
   ID                   bigint                          not null,
   SiteID             bigint                          not null,
   Name               varchar(200),
   SendInfo           varchar(200),
   ArriveInfo         varchar(200),
   Info               varchar(200),
   Price              float(12,2),
   Memo               varchar(200),
   Prop1              varchar(200),
   Prop2              varchar(200),
   Prop3              varchar(200),
   Prop4              varchar(200),
   AddUser            varchar(200)                    not null,
   AddTime            datetime                        not null,
   ModifyUser         varchar(200),
   ModifyTime         datetime,
   BackupNo           varchar(15)                    not null,
   BackupOperator     varchar(200)                   not null,
   BackupTime         date                            not null,
   BackupMemo         varchar(50),
   primary key (ID, BackupNo)
);

drop table if exists ZSStore;
create table ZSStore
(
   StoreCode            varchar(100) not null,
   ParentCode           varchar(100) not null,
   Name                 varchar(100) not null,
   Alias                varchar(100),
   TreeLevel            bigint not null,
   SiteID               bigint not null,
   OrderFlag            bigint not null,
   URL                  varchar(100),
   Info                 varchar(2000),
   Country              varchar(30),
   Province             char(6),
   City                 char(6),
   District             char(6),
   Address              varchar(400),
   ZipCode              varchar(10),
   Tel                  varchar(20),
   Fax                  varchar(20),
   Mobile               varchar(30),
   Contacter            varchar(40),
   ContacterEmail       varchar(100),
   ContacterTel         varchar(20),
   ContacterMobile      varchar(20),
   ContacterQQ          varchar(20),
   ContacterMSN         varchar(50),
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (StoreCode)
);

drop table if exists BZSPayment;
create table BZSPayment
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(200),
   Info                 varchar(1000),
   IsVisible            varchar(1),
   ImagePath            varchar(200),
   Memo                 varchar(200),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
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

drop table if exists BZSPaymentProp;
create table BZSPaymentProp
(
   ID                   bigint not null,
   PaymentID            bigint not null,
   PropName             varchar(200),
   PropValue            varchar(200),
   Memo                 varchar(1000),
   Prop1                varchar(200),
   Prop2                varchar(200),
   Prop3                varchar(200),
   Prop4                varchar(200),
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

/*文章批注字段太短*/
ALTER TABLE zcarticlelog MODIFY COLUMN ActionDetail VARCHAR(2000);
ALTER TABLE bzcarticlelog MODIFY COLUMN ActionDetail VARCHAR(2000);

/*填补表里的机构编码，统计需要用到 2010-04-26 by huanglei*/
update zccatalog set branchinnercode = (select branchinnercode from zduser where UserName=zccatalog.adduser) ;
update zcarticle set branchinnercode = (select branchinnercode from zduser where UserName=zcarticle.adduser) ;
update zcimage set branchinnercode = (select branchinnercode from zduser where UserName=zcimage.adduser) ;
update zcvideo set branchinnercode = (select branchinnercode from zduser where UserName=zcvideo.adduser) ;
update zcaudio set branchinnercode = (select branchinnercode from zduser where UserName=zcaudio.adduser) ;

/*增加系统内采集/分发相关的表*/
drop table if exists ZCInnerGather;
create table ZCInnerGather
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(200) not null,
   CatalogInnerCode     varchar(255) not null,
   TargetCatalog        varchar(4000) not null,
   SyncCatalogInsert    varchar(2),
   SyncCatalogModify    varchar(2),
   SyncArticleModify    varchar(2),
   AfterInsertStatus    bigint,
   AfterModifyStatus    bigint,
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

drop table if exists ZCInnerDeploy;
create table ZCInnerDeploy
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(200) not null,
   DeployType           varchar(2) not null,
   CatalogInnerCode     varchar(200) not null,
   TargetCatalog        varchar(4000) not null,
   SyncCatalogInsert    varchar(2),
   SyncCatalogModify    varchar(2),
   SyncArticleModify    varchar(2),
   AfterSyncStatus      bigint,
   AfterModifyStatus    bigint,
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

drop table if exists BZCInnerGather;
create table BZCInnerGather
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(200) not null,
   CatalogInnerCode     varchar(255) not null,
   TargetCatalog        varchar(4000) not null,
   SyncCatalogInsert    varchar(2),
   SyncCatalogModify    varchar(2),
   SyncArticleModify    varchar(2),
   AfterInsertStatus    bigint,
   AfterModifyStatus    bigint,
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

drop table if exists BZCInnerDeploy;
create table BZCInnerDeploy
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(200) not null,
   DeployType           varchar(2) not null,
   CatalogInnerCode     varchar(200) not null,
   TargetCatalog        varchar(4000) not null,
   SyncCatalogInsert    varchar(2),
   SyncCatalogModify    varchar(2),
   SyncArticleModify    varchar(2),
   AfterSyncStatus      bigint,
   AfterModifyStatus    bigint,
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

/*增加系统内采集/分发相关的栏目配置字段*/
ALTER TABLE zccatalogconfig ADD COLUMN AllowInnerDeploy VARCHAR(2) AFTER AttachDownFlag;
ALTER TABLE zccatalogconfig ADD COLUMN InnerDeployPassword VARCHAR(255) AFTER AllowInnerDeploy;
ALTER TABLE zccatalogconfig ADD COLUMN SyncCatalogInsert VARCHAR(2) AFTER InnerDeployPassword;
ALTER TABLE zccatalogconfig ADD COLUMN SyncCatalogModify VARCHAR(2) AFTER SyncCatalogInsert;
ALTER TABLE zccatalogconfig ADD COLUMN SyncArticleModify VARCHAR(2) AFTER SyncCatalogModify;
ALTER TABLE zccatalogconfig ADD COLUMN AfterSyncStatus bigint AFTER SyncArticleModify;
ALTER TABLE zccatalogconfig ADD COLUMN AfterModifyStatus bigint AFTER AfterSyncStatus;
ALTER TABLE zccatalogconfig ADD COLUMN AllowInnerGather VARCHAR(2) AFTER AfterModifyStatus;
ALTER TABLE zccatalogconfig ADD COLUMN InnerGatherPassword VARCHAR(255) AFTER AllowInnerGather;

ALTER TABLE bzccatalogconfig ADD COLUMN AllowInnerDeploy VARCHAR(2) AFTER AttachDownFlag;
ALTER TABLE bzccatalogconfig ADD COLUMN InnerDeployPassword VARCHAR(255) AFTER AllowInnerDeploy;
ALTER TABLE bzccatalogconfig ADD COLUMN SyncCatalogInsert VARCHAR(2) AFTER InnerDeployPassword;
ALTER TABLE bzccatalogconfig ADD COLUMN SyncCatalogModify VARCHAR(2) AFTER SyncCatalogInsert;
ALTER TABLE bzccatalogconfig ADD COLUMN SyncArticleModify VARCHAR(2) AFTER SyncCatalogModify;
ALTER TABLE bzccatalogconfig ADD COLUMN AfterSyncStatus bigint AFTER SyncArticleModify;
ALTER TABLE bzccatalogconfig ADD COLUMN AfterModifyStatus bigint AFTER AfterSyncStatus;
ALTER TABLE bzccatalogconfig ADD COLUMN AllowInnerGather VARCHAR(2) AFTER AfterModifyStatus;
ALTER TABLE bzccatalogconfig ADD COLUMN InnerGatherPassword VARCHAR(255) AFTER AllowInnerGather;

/**为栏目配置项加上是否 允许机构独立管理 lanjun20100429*/
ALTER TABLE bzccatalogconfig ADD COLUMN BranchManageFlag  varchar(2) AFTER AttachDownFlag;
ALTER TABLE zccatalogconfig ADD COLUMN BranchManageFlag  varchar(2) AFTER AttachDownFlag;

/**添加站点是否启用商城、论坛选项 lanjun 20100429*/
ALTER TABLE zcsite ADD COLUMN BBSEnableFlag  varchar(2) AFTER AllowContribute;
ALTER TABLE zcsite ADD COLUMN ShopEnableFlag varchar(2) AFTER BBSEnableFlag;
ALTER TABLE bzcsite ADD COLUMN BBSEnableFlag varchar(2) AFTER AllowContribute;
ALTER TABLE bzcsite ADD COLUMN ShopEnableFlag varchar(2) AFTER BBSEnableFlag;

/**添加Tag表格  修改ZCarticle表加入字段TagWord txc 20100512*/
drop table if exists ZCTag;

/*==============================================================*/
/* Table: ZCTag                                                 */
/*==============================================================*/
create table ZCTag
(
   ID                   bigint not null,
   Tag                  varchar(100) not null,
   SiteID               bigint not null,
   LinkURL              varchar(500),
   Type                 varchar(20),
   RelaTag              varchar(4000),
   UsedCount            bigint,
   TagText              varchar(400),
   Prop1                varchar(50),
   Prop2                varchar(50),
   AddUser              varchar(200) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (ID)
);


drop table if exists BZCTag;

/*==============================================================*/
/* Table: BZCTag                                                */
/*==============================================================*/
create table BZCTag
(
   ID                   bigint not null,
   Tag                  varchar(100) not null,
   SiteID               bigint not null,
   LinkURL              varchar(500),
   Type                 varchar(20),
   RelaTag              varchar(4000),
   UsedCount            bigint,
   TagText              varchar(400),
   Prop1                varchar(50),
   Prop2                varchar(50),
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

alter table zcarticle add Tag varchar(1000) after keyword;
alter table bzcarticle add Tag varchar(1000) after keyword;

update zctag set UsedCount=0;

/*为文章增加网站群来源ID，分页标题，引导图三个字段 2010-05-21*/
alter table zcarticle add Logo varchar(100) after IssueID;
alter table zcarticle add PageTitle varchar(200) after Logo;
alter table zcarticle add ClusterSource varchar(200) after PageTitle;
alter table zcarticle add ClusterTarget varchar(1000) after ClusterSource;
alter table zcarticle add ReferTarget varchar(1000) after ClusterTarget;

alter table bzcarticle add Logo varchar(100) after IssueID;
alter table bzcarticle add PageTitle varchar(200) after Logo;
alter table bzcarticle add ClusterSource varchar(200) after PageTitle;
alter table bzcarticle add ClusterTarget varchar(1000) after ClusterSource;
alter table bzcarticle add ReferTarget varchar(1000) after ClusterTarget;

/*为站点增加Meta_keyword,Meta_Description两个字段 2010-05-21*/
alter table zcsite add Meta_Keywords varchar(200) after ShopEnableFlag;
alter table zcsite add Meta_Description varchar(400) after Meta_Keywords;
alter table bzcsite add Meta_Keywords varchar(200) after ShopEnableFlag;
alter table bzcsite add Meta_Description varchar(400) after Meta_Keywords;


/*为栏目增加网站群来源ID 2010-05-21*/
alter table zccatalog add ClusterSourceID varchar(200) after AllowContribute;
alter table bzccatalog add ClusterSourceID varchar(200) after AllowContribute;

/*修改文章表中一些字段长度  2010-05-21*/
alter table bzcarticle modify column RelativeArticle varchar(200);
alter table bzcarticle modify column RecommendArticle varchar(200);
alter table bzcarticle modify column LockUser varchar(50);
alter table bzcarticle modify column AddUser varchar(50);
alter table bzcarticle modify column Prop1 varchar(500);

alter table zcarticle modify column RelativeArticle varchar(200);
alter table zcarticle modify column RecommendArticle varchar(200);
alter table zcarticle modify column LockUser varchar(50);
alter table zcarticle modify column AddUser varchar(50);
alter table zcarticle modify column Prop1 varchar(500);

/*增加系统内采集的功能 2010-05-26*/
drop table if exists ZCDBGather;
create table ZCDBGather
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(200) not null,
   DatabaseID           bigint not null,
   TableName            varchar(50) not null,
   CatalogID            bigint not null,
   ArticleStatus        bigint not null,
   PathReplacePartOld   varchar(200),
   PathReplacePartNew   varchar(200),
   NewRecordRule        varchar(200),
   SQLCondition        varchar(200),
   Status        varchar(2),
   MappingConfig        text not null,
   Memo                 varchar(400),
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
drop table if exists BZCDBGather;
create table BZCDBGather
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Name                 varchar(200) not null,
   DatabaseID           bigint not null,
   TableName            varchar(50) not null,
   CatalogID            bigint not null,
   ArticleStatus        bigint not null,
   PathReplacePartOld   varchar(200),
   PathReplacePartNew   varchar(200),
   NewRecordRule        varchar(200),
   SQLCondition        varchar(200),
   Status        varchar(2),
   MappingConfig        text not null,
   Memo                 varchar(400),
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

/*给采集分发任务加上运行状态字段 2010-05-28*/
ALTER TABLE zcinnergather ADD COLUMN Status VARCHAR(2) AFTER AfterModifyStatus;
ALTER TABLE bzcinnergather ADD COLUMN Status VARCHAR(2) AFTER AfterModifyStatus;
ALTER TABLE zcinnerdeploy ADD COLUMN Status VARCHAR(2) AFTER AfterModifyStatus;
ALTER TABLE bzcinnerdeploy ADD COLUMN Status VARCHAR(2) AFTER AfterModifyStatus;
ALTER TABLE zcwebgather ADD COLUMN Status VARCHAR(2) AFTER ProxyPassword;
ALTER TABLE bzcwebgather ADD COLUMN Status VARCHAR(2) AFTER ProxyPassword;
update zcwebgather set Status='Y';
update zcinnergather set Status='Y';
update zcinnerdeploy set Status='Y';

/*配置是否可以匿名发表评论*/
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('CommentAllowAnonymity','是否可以匿名发表评论','N',NULL,NULL,NULL,NULL,NULL,'2010-01-06 14:35:52','admin',NULL,NULL);

/*加入缺失的ZSShopConfig 2010-05-31*/
drop table if exists ZSShopConfig;
create table ZSShopConfig
(
   SiteID               bigint not null,
   Name                 varchar(50),
   Info                 varchar(1024),
   prop1                varchar(50),
   prop2                varchar(50),
   prop3                varchar(50),
   prop4                varchar(50),
   AddUser              varchar(100) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(100),
   ModifyTime           datetime,
   primary key (SiteID)
);

/*加入永不归档的设置 2010-05-31*/
insert into zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,AddTime,AddUser) values ('ArchiveTime','ArchiveTime','0','永不归档','1260418093080','2010-05-31 11:11:11','admin');

/*文章表增加归档时间字段  2010-06-02*/
alter TABLE zcarticle add column ArchiveDate datetime after DownlineDate;
alter TABLE bzcarticle add column ArchiveDate datetime after DownlineDate;

/*增加复制方式代码中缺少的SFTP，并增加新加入的采集分发菜单项 2010-06-02*/
insert into zdcode (CodeType,ParentCode,CodeValue,CodeName,CodeOrder,AddTime,AddUser) values ('DeployMethod','DeployMethod','SFTP','SFTP远程复制','1274171976500','2010-05-31 11:11:11','admin');
insert into zdmenu values(396, 125, '网站群采集', '2', 'DataChannel/FromCatalog.jsp', 'Y', 'Icons/icon003a10.gif', 46, '', '', '', '2010-03-22 10:36:00', 'admin', '2010-03-22 10:36:00', 'admin');
insert into zdmenu values(397, 120, '文档回收站', '2', 'Document/RecycleBin.jsp', 'Y', 'Icons/icon050a18.gif', 3, '', '', '', '2010-04-06 14:12:31', 'admin', '2010-03-22 10:36:00', 'admin');
insert into zdmenu values(403, 125, '网站群分发', '2', 'DataChannel/DeployCatalog.jsp', 'Y', 'Icons/icon003a7.gif', 47, '', '', '', '2010-05-04 21:08:22', 'admin', '2010-03-22 10:36:00', 'admin');
insert into zdmenu values(404, 122, 'Tag管理', '2', 'Site/Tag.jsp', 'Y', 'Icons/icon011a1.gif', 21, '', '', '', '2010-05-11 18:46:22', 'admin', '2010-03-22 10:36:00', 'admin');
insert into zdmenu values(405, 125, '数据库采集', '2', 'DataChannel/FromDB.jsp', 'Y', 'Icons/icon005a13.gif', 48, '', '', '', '2010-05-27 13:11:58', 'admin', '2010-03-22 10:36:00', 'admin');
update zdmaxno set MaxValue='406' where NoType='ZDMenuID';

/*ZCCatalogConfig里面增加评论相关配置字段*/
alter table zccatalogconfig add column AllowComment varchar(2) after InnerGatherPassword;
alter table zccatalogconfig add column CommentAnonymous varchar(2) after AllowComment;
alter table zccatalogconfig add column CommentVerify varchar(2) after CommentAnonymous;

alter table bzccatalogconfig add column AllowComment varchar(2) after InnerGatherPassword;
alter table bzccatalogconfig add column CommentAnonymous varchar(2) after AllowComment;
alter table bzccatalogconfig add column CommentVerify varchar(2) after CommentAnonymous;

/*企业会员形象图片字段长度由50改为100 2010-06-09*/
alter table zdmembercompanyinfo modify Pic varchar(100);
alter table bzdmembercompanyinfo modify Pic varchar(100);

/*---------------1.3.1,另有ArticleRefer.dat,IPRanges.dat通过Patch.java更新-------------*/

/** 增加是否将站点首页重新生成index.html的配置 lanjun 2010-06-18*/
INSERT INTO zdconfig  VALUES ('RewriteIndex','是否将站点首页重新生成index.html','N',NULL,NULL,NULL,NULL,NULL,'2010-01-18 14:35:52','admin',NULL,NULL);

/*投票验证码标志 2010-06-18*/
update zdcode set codevalue ='Y' where codetype = 'VerifyFlag' and parentcode ='VerifyFlag' and codevalue ='S';

/*栏目树设置功能和文章来源管理功能增加数据 2010-06-22*/
INSERT INTO 'zdmenu' ('ID','ParentID','Name','Type','URL','Visiable','Icon','OrderFlag','Prop1','Prop2','Memo','AddTime','AddUser','ModifyTime','ModifyUser') VALUES (408,122,'工作台配置','2','Site/WorkBenchConfig.jsp','Y','Icons/icon003a10.gif',23,NULL,NULL,'','2010-06-22 11:22:18','admin',NULL,NULL);
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleCatalogLoadType','文章栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:03:06','admin',NULL,NULL);
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleCatalogShowLevel','文章栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:01:26','admin',NULL,NULL);
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AttachLibLoadType','附件库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:20:52','admin',NULL,NULL);
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AttachLibShowLevel','附件库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:19:39','admin',NULL,NULL);
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AudioLibLoadType','音频库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:22:32','admin',NULL,NULL);
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AudioLibShowLevel','音频库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:22:11','admin',NULL,NULL);
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ImageLibLoadType','图片库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:14:16','admin',NULL,NULL);
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ImageLibShowLevel','图片库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:06:30','admin',NULL,NULL);
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('VideoLibLoadType','视频库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:21:47','admin',NULL,NULL);
INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('VideoLibShowLevel','视频库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:21:19','admin',NULL,NULL);

/**将article的prop字段转移回pageTitle 2010-06-23*/
update zcarticle set pageTitle=prop1 where prop1 is not null;

/*将统计分析中区域统计单立出来 2010-06-23 by wyuch*/
update ZCStatItem set Type='District',SubType='PV' where Type='Client' and SubType='District';

/*会员配置菜单和会员等级新增 2010-06-29*/
UPDATE zdmenu set Name = '会员配置',URL='DataService/MemberConfig.jsp',Icon='Icons/icon025a6.gif' where ID = 344;
INSERT INTO zdmemberlevel (ID,Name,Type,Discount,IsDefault,TreeLevel,Score,IsValidate,AddUser,AddTime,ModifyUser,ModifyTime) VALUES (31,'注册会员','用户',1,'Y',0,0,'Y','admin','2010-06-29 18:50:45',NULL,NULL);