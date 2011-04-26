/* �솴20091020 */
alter table zdmemberlevel change Type Type varchar(10);
alter table bzdmemberlevel change Type Type varchar(10);

/* �η�20091029 */
alter table zcimage change FileSize FileSize varchar(20);
alter table bzcimage change FileSize FileSize varchar(20);

/* �޽���20091030 */
alter table ZCVideo change FileSize FileSize varchar(20);
alter table BZCVideo change FileSize FileSize varchar(20);
alter table ZCAudio change FileSize FileSize varchar(20);
alter table BZCAudio change FileSize FileSize varchar(20);
alter table ZCAttachment change FileSize FileSize varchar(20);
alter table BZCAttachment change FileSize FileSize varchar(20);

/*  ���� 20091104 */
insert into zdconfig(type,name,value,adduser,addtime) values('FormProcess','���ύ����ҳ��','/FormProcess.jsp','admin','2009-11-02 12:15:14');
drop table if exists ZDFormColumn;

/*==============================================================*/
/* Table: ZDFormColumn                                          */
/*==============================================================*/
create table ZDFormColumn
(
   ID                             bigint                         not null,
   FormID                         bigint,
   Name                           varchar(100)                   not null,
   Code                           varchar(100)                   not null,
   SysCode                        varchar(100),
   DataType                       varchar(10)                    not null,
   MaxLength                      smallint,
   InputType                      char(10)                       not null,
   DefaultValue                   varchar(50),
   ListOption                     varchar(1000),
   IsMandatory                    char(1)                        not null,
   VerifyType                     varchar(50),
   OrderFlag                      bigint,
   RowSize                        smallint,
   ColSize                        smallint,
   Prop1                          varchar(50),
   Prop2                          varchar(50),
   AddUser                        varchar(200)                   not null,
   AddTime                        datetime                       not null,
   ModifyUser                     varchar(200),
   ModifyTime                     datetime,
   primary key (ID)
);
drop table if exists BZDFormColumn;

/*==============================================================*/
/* Table: BZDFormColumn                                         */
/*==============================================================*/
create table BZDFormColumn
(
   ID                             bigint                         not null,
   FormID                         bigint,
   Name                           varchar(100)                   not null,
   Code                           varchar(100)                   not null,
   SysCode                        varchar(100),
   DataType                       varchar(10)                    not null,
   MaxLength                      smallint,
   InputType                      char(10)                       not null,
   DefaultValue                   varchar(50),
   ListOption                     varchar(1000),
   IsMandatory                    char(1)                        not null,
   VerifyType                     varchar(50),
   OrderFlag                      bigint,
   RowSize                        smallint,
   ColSize                        smallint,
   Prop1                          varchar(50),
   Prop2                          varchar(50),
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
drop table if exists ZDForm;

/*==============================================================*/
/* Table: ZDForm                                                */
/*==============================================================*/
create table ZDForm
(
   ID                             bigint                         not null,
   Name                           varchar(100)                   not null,
   SiteID                         bigint,
   Code                           varchar(20)                    not null,
   Content                        text,
   Script                         text,
   CSS                            text,
   Memo                           varchar(100),
   Prop1                          varchar(50),
   Prop2                          varchar(50),
   AddUser                        varchar(200)                   not null,
   AddTime                        datetime                       not null,
   ModifyUser                     varchar(200),
   ModifyTime                     datetime,
   primary key (ID)
);
drop table if exists BZDForm;

/*==============================================================*/
/* Table: BZDForm                                               */
/*==============================================================*/
create table BZDForm
(
   ID                             bigint                         not null,
   Name                           varchar(100)                   not null,
   SiteID                         bigint,
   Code                           varchar(20)                    not null,
   Content                        text,
   Script                         text,
   CSS                            text,
   Memo                           varchar(100),
   Prop1                          varchar(50),
   Prop2                          varchar(50),
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


/* �솴 2009.11.06 �Զ����ֶα� */
ALTER table ZDColumn add COLUMN HTML text after ListOption;
ALTER table bZDColumn add COLUMN HTML text after ListOption;

/*  ���� ˮӡͼƬ��upload�� */
update zcsite set configxml=replace(configxml,'src="Image','src="upload/Image');

/* ���� 20091109 ������Ŀ���µĳ�ȡ�ؼ������� */
ALTER table ZCCatalog add COLUMN KeywordFlag varchar(2) after Integral;
ALTER table bZCCatalog add COLUMN KeywordFlag varchar(2) after Integral;
ALTER table ZCCatalog add COLUMN KeywordSetting varchar(50) after KeywordFlag;
ALTER table bZCCatalog add COLUMN KeywordSetting varchar(50) after KeywordFlag;


drop table if exists ZDMember;

/*==============================================================*/
/* Table: ZDMember                                              */
/*==============================================================*/
create table ZDMember
(
   UserName                       varchar(50)                    not null,
   Password                       varchar(32)                    not null,
   Email                          varchar(100)                   not null,
   LevelID                        bigint                         not null,
   Type                           char(10)                       not null,
   SiteID                         bigint,
   RealName                       varchar(100),
   LastName                       varchar(50),
   FirstName                      varchar(50),
   Logo                           varchar(100),
   Birthday                       date,
   Sex                            char(1)                        default '1',
   Info                           varchar(1000),
   Education                      varchar(30),
   Vocation                       varchar(50),
   Interest                       varchar(500),
   Score                          int                            default 0,
   Discount                       decimal(5,3),
   RegIP                          varchar(16),
   RegTime                        date,
   Status                         char(2)                        not null default '0',
   IsComplete                     char(1)                        default '0',
   ServiceUser                    varchar(50),
   PWQuestion                     varchar(50),
   PWAnswer                       varchar(50),
   LastIP                         varchar(16),
   LastLoginTime                  date,
   CompanyName                    varchar(50),
   MainBis                        varchar(50),
   Address                        varchar(100),
   ZipCode                        varchar(10),
   Tel                            varchar(20),
   Mobile                         varchar(250),
   Fax                            varchar(20),
   primary key (UserName)
);
drop table if exists ZDMemberAddr;

/*==============================================================*/
/* Table: ZDMemberAddr                                          */
/*==============================================================*/
create table ZDMemberAddr
(
   ID                             bigint                         not null AUTO_INCREMENT,
   UserName                       varchar(200)                   not null default '0',
   RealName                       varchar(100),
   Country                        varchar(30),
   Province                       char(6),
   City                           char(6),
   District                       char(6),
   Address                        varchar(255),
   ZipCode                        varchar(10),
   Tel                            varchar(20),
   Mobile                         varchar(20),
   Email                          varchar(100),
   IsDefault                      char(1)                        default '0',
   AddUser                        varchar(200)                   not null,
   AddTime                        datetime                       not null,
   ModifyUser                     varchar(200),
   ModifyTime                     datetime,
   primary key (ID)
);
drop table if exists ZDFavorite;

/*==============================================================*/
/* Table: ZDFavorite                                            */
/*==============================================================*/
create table ZDFavorite
(
   UserName                       varchar(200)                   not null,
   DocID                          bigint                         not null,
   AddUser                        varchar(200)                   not null,
   AddTime                        datetime                       not null,
   ModifyUser                     varchar(200),
   ModifyTime                     datetime,
   primary key (UserName, DocID)
);
drop table if exists BZDMemberAddr;

/*==============================================================*/
/* Table: BZDMemberAddr                                         */
/*==============================================================*/
create table BZDMemberAddr
(
   ID                             bigint                         not null AUTO_INCREMENT,
   UserName                       varchar(200)                   not null default '0',
   RealName                       varchar(100),
   Country                        varchar(30),
   Province                       char(6),
   City                           char(6),
   District                       char(6),
   Address                        varchar(255),
   ZipCode                        varchar(10),
   Tel                            varchar(20),
   Mobile                         varchar(20),
   Email                          varchar(100),
   IsDefault                      char(1)                        default '0',
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
drop table if exists BZDMember;

/*==============================================================*/
/* Table: BZDMember                                             */
/*==============================================================*/
create table BZDMember
(
   UserName                       varchar(50)                    not null,
   Password                       varchar(32)                    not null,
   Email                          varchar(100)                   not null,
   LevelID                        bigint                         not null,
   Type                           char(10)                       not null,
   SiteID                         bigint,
   RealName                       varchar(100),
   LastName                       varchar(50),
   FirstName                      varchar(50),
   Logo                           varchar(100),
   Birthday                       date,
   Sex                            char(1)                        default '1',
   Info                           varchar(1000),
   Education                      varchar(30),
   Vocation                       varchar(50),
   Interest                       varchar(500),
   Score                          int                            default 0,
   Discount                       decimal(5,3),
   RegIP                          varchar(16),
   RegTime                        date,
   Status                         char(2)                        not null default '0',
   IsComplete                     char(1)                        default '0',
   ServiceUser                    varchar(50),
   PWQuestion                     varchar(50),
   PWAnswer                       varchar(50),
   LastIP                         varchar(16),
   LastLoginTime                  date,
   CompanyName                    varchar(50),
   MainBis                        varchar(50),
   Address                        varchar(100),
   ZipCode                        varchar(10),
   Tel                            varchar(20),
   Mobile                         varchar(250),
   Fax                            varchar(20),
   BackupNo                       varchar(15)                    not null,
   BackupOperator                 varchar(200)                   not null,
   BackupTime                     datetime                       not null,
   BackupMemo                     varchar(50),
   primary key (UserName, BackupNo)
);
drop table if exists BZDFavorite;

/*==============================================================*/
/* Table: BZDFavorite                                           */
/*==============================================================*/
create table BZDFavorite
(
   UserName                       varchar(200)                   not null,
   DocID                          bigint                         not null,
   AddUser                        varchar(200)                   not null,
   AddTime                        datetime                       not null,
   ModifyUser                     varchar(200),
   ModifyTime                     datetime,
   BackupNo                       varchar(15)                    not null,
   BackupOperator                 varchar(200)                   not null,
   BackupTime                     datetime                       not null,
   BackupMemo                     varchar(50),
   primary key (UserName, DocID, BackupNo)
);
drop table if exists ZCMessageBoard;

/*==============================================================*/
/* Table: ZCMessageBoard                                        */
/*==============================================================*/
create table ZCMessageBoard
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Type                 varchar(20) not null,
   Title                varchar(100) not null,
   Content              text not null,
   PublishFlag          varchar(1) not null comment '0-��
            1-��',
   ReplyFlag            varchar(1) comment '0-δ�ظ�
            1-���ظ�',
   ReplyContent         varchar(4000),
   Email                varchar(100),
   Tel                  varchar(20),
   Mobile               varchar(20),
   Address              varchar(100),
   AttachPath           varchar(100),
   Prop1                varchar(50),
   Prop2                varchar(50),
   UserName             varchar(200),
   AddUser              varchar(200) not null,
   AddUserIP            varchar(50) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   primary key (ID)
);
drop table if exists BZCMessageBoard;

/*==============================================================*/
/* Table: BZCMessageBoard                                        */
/*==============================================================*/
create table BZCMessageBoard
(
   ID                   bigint not null,
   SiteID               bigint not null,
   Type                 varchar(20) not null,
   Title                varchar(100) not null,
   Content              text not null,
   PublishFlag          varchar(1) not null comment '0-��
            1-��',
   ReplyFlag            varchar(1) comment '0-δ�ظ�
            1-���ظ�',
   ReplyContent         varchar(4000),
   Email                varchar(100),
   Tel                  varchar(20),
   Mobile               varchar(20),
   Address              varchar(100),
   AttachPath           varchar(100),
   Prop1                varchar(50),
   Prop2                varchar(50),
   UserName             varchar(200),
   AddUser              varchar(200) not null,
   AddUserIP            varchar(50) not null,
   AddTime              datetime not null,
   ModifyUser           varchar(200),
   ModifyTime           datetime,
   BackupNo             varchar(15) not null,
   BackupOperator       varchar(200) not null,
   BackupTime           datetime not null,
   BackupMemo           varchar(50),
   primary key (ID, BackupNo)
);

/* ��ά�� 20091110 ���ͼƬ����Ƶ����Ƶ�������������ֶ� */
ALTER table ZCImage change OrderNumber OrderFlag bigint not null;
ALTER table bZCImage change OrderNumber OrderFlag bigint not null;
ALTER table ZCVideo change OrderNumber OrderFlag bigint not null;
ALTER table bZCVideo change OrderNumber OrderFlag bigint not null;
ALTER table ZCAttachment add COLUMN OrderFlag bigint not null after SourceURL;
ALTER table bZCAttachment add COLUMN OrderFlag bigint not null after SourceURL;
ALTER table ZCAudio add COLUMN OrderFlag bigint not null after SourceURL;
ALTER table bZCAudio add COLUMN OrderFlag bigint not null after SourceURL;
update ZCImage set OrderFlag=ID where OrderFlag is null;
update bZCImage set OrderFlag=ID where OrderFlag is null;
update ZCVideo set OrderFlag=ID where OrderFlag is null;
update bZCVideo set OrderFlag=ID where OrderFlag is null;
update ZCAttachment set OrderFlag=ID where OrderFlag is null;
update bZCAttachment set OrderFlag=ID where OrderFlag is null;
update ZCAudio set OrderFlag=ID where OrderFlag is null;
update bZCAudio set OrderFlag=ID where OrderFlag is null;


REPLACE INTO `zdcode` (`CodeType`,`ParentCode`,`CodeValue`,`CodeName`,`CodeOrder`,`Prop1`,`Prop2`,`Prop3`,`Prop4`,`Memo`,`AddTime`,`AddUser`,`ModifyTime`,`ModifyUser`) VALUES ('Member.Sex','Member.Sex','F','Ů',1255665814859,NULL,NULL,NULL,NULL,'','2009-10-16 12:03:34','admin',NULL,NULL);
REPLACE INTO `zdcode` (`CodeType`,`ParentCode`,`CodeValue`,`CodeName`,`CodeOrder`,`Prop1`,`Prop2`,`Prop3`,`Prop4`,`Memo`,`AddTime`,`AddUser`,`ModifyTime`,`ModifyUser`) VALUES ('Member.Sex','Member.Sex','M','��',1255665799000,NULL,NULL,NULL,NULL,'','2009-10-16 12:03:19','admin',NULL,NULL);
REPLACE INTO `zdcode` (`CodeType`,`ParentCode`,`CodeValue`,`CodeName`,`CodeOrder`,`Prop1`,`Prop2`,`Prop3`,`Prop4`,`Memo`,`AddTime`,`AddUser`,`ModifyTime`,`ModifyUser`) VALUES ('Member.Sex','System','System','��Ա�Ա�',1255665516218,NULL,NULL,NULL,NULL,'','2009-10-16 11:58:36','admin',NULL,NULL);
REPLACE INTO `zdcode` (`CodeType`,`ParentCode`,`CodeValue`,`CodeName`,`CodeOrder`,`Prop1`,`Prop2`,`Prop3`,`Prop4`,`Memo`,`AddTime`,`AddUser`,`ModifyTime`,`ModifyUser`) VALUES ('Member.Status','Member.Status','N','��˲�ͨ��',1256005990750,NULL,NULL,NULL,NULL,'','2009-10-20 10:33:10','admin',NULL,NULL);
REPLACE INTO `zdcode` (`CodeType`,`ParentCode`,`CodeValue`,`CodeName`,`CodeOrder`,`Prop1`,`Prop2`,`Prop3`,`Prop4`,`Memo`,`AddTime`,`AddUser`,`ModifyTime`,`ModifyUser`) VALUES ('Member.Status','Member.Status','X','�ȴ����',1256005966359,NULL,NULL,NULL,NULL,'','2009-10-20 10:32:46','admin',NULL,NULL);
REPLACE INTO `zdcode` (`CodeType`,`ParentCode`,`CodeValue`,`CodeName`,`CodeOrder`,`Prop1`,`Prop2`,`Prop3`,`Prop4`,`Memo`,`AddTime`,`AddUser`,`ModifyTime`,`ModifyUser`) VALUES ('Member.Status','Member.Status','Y','���ͨ��',1256005978859,NULL,NULL,NULL,NULL,'','2009-10-20 10:32:58','admin',NULL,NULL);
REPLACE INTO `zdcode` (`CodeType`,`ParentCode`,`CodeValue`,`CodeName`,`CodeOrder`,`Prop1`,`Prop2`,`Prop3`,`Prop4`,`Memo`,`AddTime`,`AddUser`,`ModifyTime`,`ModifyUser`) VALUES ('Member.Status','System','System','��Ա���״̬',1256005930453,NULL,NULL,NULL,NULL,'','2009-10-20 10:32:10','admin',NULL,NULL);

/* ����ͤ ZDUserLog��ļ�һ��SubType�ֶ� */
ALTER table ZDUserLog add COLUMN SubType varchar(10) after LogType;
ALTER table BZDUserLog add COLUMN SubType varchar(10) after LogType;
/* �Ž�� zckeywork��ļ�һ��siteid�ֶ� */
ALTER table ZCKeyWord add COLUMN SiteId bigint not null after Keyword ;

/* ����ͤ �޸��ֶ�varchar���� */
alter table ZDUserLog change LogType LogType varchar(20);
alter table ZDUserLog change SubType SubType varchar(20);

