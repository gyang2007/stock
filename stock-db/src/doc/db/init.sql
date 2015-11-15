CREATE DATABASE IF NOT EXISTS stockdb
  DEFAULT CHARSET utf8
  COLLATE utf8_general_ci;

USE stockdb;

DROP TABLE IF EXISTS stock_base_info;
CREATE TABLE stock_base_info (
  id          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  code        INT(6) UNSIGNED  NOT NULL DEFAULT 0
  COMMENT '股票代码',
  name        VARCHAR(16)      NOT NULL DEFAULT '',
  type        TINYINT UNSIGNED NOT NULL DEFAULT 1,
  descp       VARCHAR(128)     NOT NULL DEFAULT '',
  record_time TIMESTAMP        NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY pk_id(id),
  UNIQUE KEY uniq_code_type(code, type)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT = '基本信息';

DROP TABLE IF EXISTS stock_exchange_info;
CREATE TABLE stock_exchange_info (
  id                 INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  code               INT(6) UNSIGNED  NOT NULL DEFAULT 0
  COMMENT '股票代码',
  type               TINYINT UNSIGNED NOT NULL DEFAULT 1,
  open               DECIMAL(8, 3)    NOT NULL DEFAULT 0.00,
  hight              DECIMAL(8, 3)    NOT NULL DEFAULT 0.00,
  low                DECIMAL(8, 3)    NOT NULL DEFAULT 0.00,
  close              DECIMAL(8, 3)    NOT NULL DEFAULT 0.00,
  volume             BIGINT UNSIGNED  NOT NULL DEFAULT 0,
  price_change       DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '涨跌幅（%）',
  total_price_change DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '振幅（%）',
  exch_rate          DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '换手率（%）',
  tx_date            DATE             NOT NULL
  COMMENT '交易日期',
  record_time        TIMESTAMP        NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY pk_id(id),
  UNIQUE KEY idx_code_type_tx_date(code, type, tx_date)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT = '每日交易信息';


DROP TABLE IF EXISTS stock_ma;
CREATE TABLE stock_ma (
  id          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  code        INT(6) UNSIGNED  NOT NULL DEFAULT 0
  COMMENT '股票代码',
  type        TINYINT UNSIGNED NOT NULL DEFAULT 1,
  close       DECIMAL(8, 3)    NOT NULL DEFAULT 0.00
  COMMENT '当天收盘价',
  ma5         DECIMAL(8, 3)    NOT NULL DEFAULT 0.00
  COMMENT '5日均线',
  ma10        DECIMAL(8, 3)    NOT NULL DEFAULT 0.00
  COMMENT '10日均线',
  ma20        DECIMAL(8, 3)    NOT NULL DEFAULT 0.00
  COMMENT '20日均线',
  ma30        DECIMAL(8, 3)    NOT NULL DEFAULT 0.00
  COMMENT '30日均线',
  ma60        DECIMAL(8, 3)    NOT NULL DEFAULT 0.00
  COMMENT '60日均线',
  ma120       DECIMAL(8, 3)    NOT NULL DEFAULT 0.00
  COMMENT '120日均线',
  ma250       DECIMAL(8, 3)    NOT NULL DEFAULT 0.00
  COMMENT '250日均线',
  tx_date     DATE             NOT NULL
  COMMENT '交易日期',
  record_time TIMESTAMP        NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY pk_id(id),
  UNIQUE KEY idx_code_type_tx_date(code, type, tx_date)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT = '股票均线';

DROP TABLE IF EXISTS stock_deal_statistics_m;
CREATE TABLE stock_deal_statistics_m (
  id                 INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  code               INT(6) UNSIGNED  NOT NULL DEFAULT 0
  COMMENT '股票代码',
  type               TINYINT UNSIGNED NOT NULL DEFAULT 1
  COMMENT '股票类型',
  open_price         DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '当月股票开盘价',
  close_price        DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '当月股票收盘价',
  min_low_price      DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '当月股票最低价',
  max_high_price     DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '当月股票最高价',
  price_change       DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '涨跌幅（%）',
  total_price_change DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '振幅（%）',
  total_exch_rate    DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '总换手率（%）',
  close_market_value INT(11) UNIQUE   NOT NULL DEFAULT 0
  COMMENT '月末总市值（万）',
  total_tx_count     INT(11)          NOT NULL DEFAULT 0
  COMMENT '当月总成交笔数（万）',
  total_tx_date      TINYINT UNSIGNED NOT NULL DEFAULT 0
  COMMENT '当月累计交易日',
  pe                 DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '月末市盈率',
  pb                 DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '月末市净率',
  roe                DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '',
  recode_time        TIMESTAMP        NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY pk_id(id),
  KEY idx_code_type(code, type)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT = '月度成交概况';

DROP TABLE IF EXISTS stock_deal_statistics_y;
CREATE TABLE stock_deal_statistics_y (
  id                 INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  code               INT(6) UNSIGNED  NOT NULL DEFAULT 0
  COMMENT '股票代码',
  type               TINYINT UNSIGNED NOT NULL DEFAULT 1
  COMMENT '股票类型',
  open_price         DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '当月股票开盘价',
  close_price        DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '当月股票收盘价',
  min_low_price      DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '当月股票最低价',
  max_high_price     DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '当月股票最高价',
  price_change       DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '涨跌幅（%）',
  total_price_change DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '振幅（%）',
  total_exch_rate    DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '总换手率（%）',
  close_market_value INT(11) UNIQUE   NOT NULL DEFAULT 0
  COMMENT '月末总市值（万）',
  total_tx_count     INT(11)          NOT NULL DEFAULT 0
  COMMENT '当月总成交笔数（万）',
  total_tx_date      TINYINT UNSIGNED NOT NULL DEFAULT 0
  COMMENT '当月累计交易日',
  pe                 DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '月末市盈率',
  pb                 DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '月末市净率',
  roe                DECIMAL(8, 3)    NOT NULL DEFAULT 0.000
  COMMENT '',
  recode_time        TIMESTAMP        NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY pk_id(id),
  KEY idx_code_type(code, type)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  COMMENT = '年度成交概况';
