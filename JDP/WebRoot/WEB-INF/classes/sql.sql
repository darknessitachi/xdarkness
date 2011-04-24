
DROP TABLE TablesRemark;
DROP TABLE ColsRemark;
DROP TABLE Category;
DROP TABLE CategoryValue;

CREATE TABLE TablesRemark (
	tablename varchar (50) PRIMARY KEY  NOT NULL COMMENT '表名',
	addtime DATETIME COMMENT '添加时间',
	moder int NOT NULL DEFAULT 0 COMMENT '最后修改者',
	modtime TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '最后修改时间',
	alias varchar (255)  NOT NULL DEFAULT 'Not Defined' COMMENT '别名',
	remark text  NOT NULL DEFAULT '' COMMENT '生成类时的注释内容(即对该表的作用解释)',
	primaryEx1 varchar (100)  NOT NULL DEFAULT '' COMMENT '组合主键1,形如 chinaname+loginname 表示chinaname和loginname不能同时相同',
	primaryEx2 varchar (100)  NOT NULL DEFAULT '' COMMENT '组合主键2,形如 chinaname+loginname 表示chinaname和loginname不能同时相同',
	ordercol varchar (100)  NOT NULL DEFAULT 'id' COMMENT '主排序串（形如“parentid,name desc”）',
	BoolUseCache tinyint NOT NULL DEFAULT 0 COMMENT '是否使用静态类存储其所有行的内容',
	tabletype tinyint NOT NULL DEFAULT 0 COMMENT '表类型（下拉选框）',
	categoryid int NOT NULL DEFAULT 8 COMMENT '业务表所处目录',
	summarytable varchar (50)  NOT NULL DEFAULT '' COMMENT 'summarytable表名',
	summarycol varchar (50)  NOT NULL DEFAULT '' COMMENT '本表关联主表(summarytable)的字段',
	indexid int NOT NULL DEFAULT 0 COMMENT '排序号',
	tablechanged tinyint NOT NULL DEFAULT 1 COMMENT '开发标识，说明是否是新的未经设定的表(或添加修改过字段的表)，1表示是，0表示否',
	colchanged tinyint NOT NULL DEFAULT 1 COMMENT '开发标识，说明是可能有新的修改过字段的表，1表示是，0表示否',
	tracelog tinyint NOT NULL DEFAULT 1 COMMENT '启用插入数据日志跟踪',
	baseTitle varchar (100)  NOT NULL DEFAULT '' COMMENT '展示信息时所用标题'
) ;


CREATE TABLE ColsRemark (
	id varchar (125) PRIMARY KEY  NOT NULL COMMENT '表名+char(13)+字段名 作为主键',
	addtime datetime NOT NULL COMMENT '添加时间',
	moder int NOT NULL DEFAULT 0 COMMENT '最后修改者',
	modtime TIMESTAMP NOT NULL DEFAULT NOW() COMMENT '最后修改时间',
	tablename varchar (50) DEFAULT '' NOT NULL COMMENT '表名',
	colname varchar (50) NOT NULL DEFAULT '' COMMENT '字段名',
	alias varchar (100)  NOT NULL DEFAULT '' COMMENT '别名',
	categoryid varchar (40)  NOT NULL DEFAULT '' COMMENT '绑定何下拉选框',
	minvalue varchar (20)  NOT NULL DEFAULT '' COMMENT '填写值的上下限（目前设计当字段为int或double时此处需填写，填写格式为“下限 上限”，如“-20.0 32.37”）',
	maxvalue varchar (20)  NOT NULL DEFAULT '' COMMENT '填写值的上下限（目前设计当字段为int或double时此处需填写，填写格式为“下限 上限”，如“-20.0 32.37”）',
	multilines tinyint NOT NULL DEFAULT 0 COMMENT '当为varchar或text时，此值为1表示是多行文本填写',
	rigor tinyint NOT NULL  DEFAULT 0 COMMENT '当为double时，此字段表示显示时小数点后保留几位',
	Fmttypetime tinyint NOT NULL  DEFAULT 0 COMMENT '当为datetime时，此值表示采用何种格式显示时间（下拉列表）',
	openout tinyint NOT NULL DEFAULT 1 COMMENT '是否是展现给操作者的业务数据',
	colchanged tinyint NOT NULL DEFAULT 1 COMMENT '开发标识，说明是否是新的未经设定的字段，1表示是，0表示否',
	Required tinyint NOT NULL DEFAULT 0 COMMENT 'varchar或text是否要大于0长度,数字或时间是否必须填写,0表示否，1表示是',
	ValidExp varchar (255)  NOT NULL  DEFAULT '' COMMENT '校验字符串（正则表达式）',
	MultiSelected tinyint NOT NULL DEFAULT 0  COMMENT '对于下拉选框来说，是否是多选框',
	devinfo varchar (1000)  NOT NULL DEFAULT ''  COMMENT '开发备注',
	inputtype tinyint NOT NULL DEFAULT 0 COMMENT '(业务表)字段输入方式',
	colorder int NOT NULL  DEFAULT 0 COMMENT '同一张表中的排序号',
	tracelog tinyint NOT NULL  DEFAULT 0 COMMENT '启用日志跟踪',
	usedRefid varchar (20)  NOT NULL  DEFAULT '' COMMENT '使用该下拉选框的某个子节点以下选框（如果该字段是绑定到下拉选框的）',
	usedRefidRoot tinyint NOT NULL  DEFAULT 0 COMMENT '包含该下拉选框的useRefid的根节点',
	dynamicbind tinyint NOT NULL  DEFAULT 0 COMMENT '动态绑定'
) ;

CREATE TABLE CategoryValue (
	Id varchar (40) PRIMARY KEY NOT NULL ,
	adder int NOT NULL ,
	addtime datetime NOT NULL ,
	moder int NOT NULL ,
	modtime datetime NOT NULL ,
	delstatus tinyint NOT NULL ,
	refid varchar (30) NOT NULL ,
	categoryid varchar (40) NOT NULL ,
	chinaname varchar (255) NOT NULL ,
	extint1 int NOT NULL ,
	extint2 int NOT NULL ,
	extint3 int NOT NULL ,
	extchar1 varchar (255) NOT NULL ,
	extchar2 varchar (255) NOT NULL ,
	extchar3 varchar (255) NOT NULL ,
	extchar4 varchar (255) NOT NULL ,
	parentid varchar (40) NOT NULL ,
	indexid int NOT NULL ,
	sortcode varchar (100) NOT NULL ,
	remark varchar (1000) NOT NULL ,
	isleaf tinyint NOT NULL 
) ; 

CREATE TABLE Category (
	Id varchar (40) PRIMARY KEY NOT NULL ,
	adder int NOT NULL ,
	addtime datetime NOT NULL ,
	moder int NOT NULL ,
	modtime datetime NOT NULL ,
	delstatus tinyint NOT NULL ,
	constname varchar (50) NOT NULL ,
	chinaname varchar (255) NOT NULL ,
	optionname varchar (50) NOT NULL ,
	maxdepth tinyint NOT NULL ,
	remark varchar (1000) NOT NULL ,
	diclevel tinyint NOT NULL ,
	folderid int NOT NULL ,
	extchar1name varchar (50) NOT NULL ,
	extchar2name varchar (50) NOT NULL ,
	extchar3name varchar (50) NOT NULL ,
	extchar4name varchar (50) NOT NULL ,
	extint1name varchar (50) NOT NULL ,
	extint2name varchar (50) NOT NULL ,
	extint3name varchar (50) NOT NULL ,
	extint1categoryid varchar (40) NOT NULL ,
	extint2categoryid varchar (40) NOT NULL ,
	extint3categoryid varchar (40) NOT NULL ,
	refidOrder tinyint NOT NULL ,
	refidInt tinyint NOT NULL ,
	leafuse tinyint NOT NULL ,
	extTablename varchar (50) NOT NULL ,
	sortBy tinyint NOT NULL 
) ;

 