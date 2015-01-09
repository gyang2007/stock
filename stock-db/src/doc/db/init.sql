Create DATABASE IF NOT EXISTS stockdb default charset utf8 COLLATE utf8_general_ci;

use stockdb;

drop table if exists stock_base_info;
create table stock_base_info(
	id int(11) unsigned not null auto_increment,
	code int(6) unsigned not null default 0 comment '代码',
	name varchar(16) not null default '',
	type tinyint unsigned not null default 10,
	descp varchar(128) not null default '',
	primary key pk_id(id),
	unique key uniq_code_type(code, type)
) engine=InnoDB auto_increment=1 default charset=utf8 comment='基本信息';

drop table if exists stock_exchange_info;
create table stock_exchange_info(
	id int(11) unsigned not null auto_increment,
	code int(6) unsigned not null default 0 comment '代码',
	type tinyint unsigned not null default 10,
	open float(7,2) unsigned not null default 0.00,
	hight float(7,2) unsigned not null default 0.00,
	low float(7,2) unsigned not null default 0.00,
	close float(7,2) unsigned not null default 0.00,
	adj_close float(7,2) unsigned not null default 0.00,
	volume bigint unsigned not null default 0,
	record_time date not null,
	primary key pk_id(id),
	key idx_code_type_record_time(code, type, record_time)
) engine=InnoDB auto_increment=1 default charset=utf8 comment='每日交易信息';