-- USER
CREATE TABLE `user` (
                        `id`      BIGINT NOT NULL AUTO_INCREMENT,
                        `activate_id`  VARCHAR(50)  NULL,
                        `password`     VARCHAR(255) NULL,
                        `role_type`    VARCHAR(10)  NOT NULL,
                        `address`      VARCHAR(255) NULL,
                        `email`        VARCHAR(255) NULL,
                        `provider`     VARCHAR(10)  NULL,
                        `provider_id`  VARCHAR(255) NULL,

                        `reg_dt`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        `reg_user`     VARCHAR(50) NOT NULL,
                        `mod_dt`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        `mod_user`     VARCHAR(50) NOT NULL,

                        CONSTRAINT `PK_USER` PRIMARY KEY (`id`),
                        CONSTRAINT `CHK_USER_PROVIDER` CHECK (`provider` IN ('GOOGLE','KAKAO') OR `provider` IS NULL)
);

-- 결제수단
CREATE TABLE `ledger_payment` (
                                  `id`   BIGINT NOT NULL AUTO_INCREMENT COMMENT '결제수단 아이디',
                                  `parent_payment_id`    BIGINT NULL  COMMENT '결제수단 그룹 아이디',
                                  `user_id`      BIGINT NOT NULL COMMENT '사용자 아이디',
                                  `name`         VARCHAR(100) NULL COMMENT '결제수단 이름',
                                  `payment_type` VARCHAR(10)  NULL COMMENT '결제수단 타입',
                                  `use_yn`        VARCHAR(1)   NOT NULL DEFAULT 'Y' COMMENT '결제수단 사용여부 (Y/N)',

                                  `reg_dt`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  `reg_user`     VARCHAR(50) NOT NULL,
                                  `mod_dt`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  `mod_user`     VARCHAR(50) NOT NULL,

                                  CONSTRAINT `PK_LEDGER_PAYMENT` PRIMARY KEY (`id`, `user_id`),
                                  CONSTRAINT `CHK_PAYMENT_TYPE` CHECK (`payment_type` IN ('CASH','ATM','CREDIT','ETC') OR `payment_type` IS NULL),
                                  CONSTRAINT `CHK_PAYMENT_USE_YN` CHECK (`use_yn` IN ('Y','N')),
                                  CONSTRAINT `uk_payment_user_name` UNIQUE (`user_id`, `name`)
);

-- 카테고리
CREATE TABLE `ledger_category` (
                                   `id`  BIGINT NOT NULL AUTO_INCREMENT COMMENT '카테고리 아이디',
                                   `parent_category_id`    BIGINT NULL  COMMENT '카테고리 그룹 아이디',
                                   `user_id`      BIGINT NOT NULL COMMENT '사용자 아이디',
                                   `name`         VARCHAR(50)  NULL COMMENT '카테고리 소분류 이름',
                                   `investment_yn` VARCHAR(1)   NOT NULL DEFAULT 'N' COMMENT '자산계획 사용여부 (Y/N)',
                                   `image_url`    VARCHAR(255) NULL COMMENT '카테고리 아이콘 이미지',
                                   `use_yn`        VARCHAR(1)   NOT NULL DEFAULT 'Y' COMMENT '카테고리 사용여부 (Y/N)',

                                   `reg_dt`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   `reg_user`     VARCHAR(50) NOT NULL,
                                   `mod_dt`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   `mod_user`     VARCHAR(50) NOT NULL,

                                   CONSTRAINT `PK_LEDGER_CATEGORY` PRIMARY KEY (`id`),
                                   CONSTRAINT `CHK_CATEGORY_INVEST_YN` CHECK (`investment_yn` IN ('Y','N')),
                                   CONSTRAINT `CHK_CATEGORY_USE_YN` CHECK (`use_yn` IN ('Y','N'))
);

-- 반복 계획
CREATE TABLE `ledger_item_plan` (
                                    `id`       BIGINT NOT NULL AUTO_INCREMENT COMMENT '아이템 반복 아이디',
                                    `item_id`   BIGINT NOT NULL,
                                    `recurring_type`     VARCHAR(10)  NOT NULL COMMENT '반복 타입',
                                    `recurring_start_dt` DATETIME         NOT NULL COMMENT '반복시작일',
                                    `recurring_end_dt`   DATETIME         NOT NULL COMMENT '반복종료일',
                                    `reg_dt`             DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `reg_user`           VARCHAR(50) NOT NULL,
                                    `mod_dt`             DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    `mod_user`           VARCHAR(50) NOT NULL,

                                    CONSTRAINT `PK_LEDGER_ITEM_PLAN` PRIMARY KEY (`id`),
                                    CONSTRAINT `CHK_RECURRING_TYPE` CHECK (`recurring_type` IN ('DAY','WEEK','MONTH'))
);

-- 아이템
CREATE TABLE `ledger_item` (
                               `id`      BIGINT NOT NULL AUTO_INCREMENT COMMENT '아이템 아이디',
                               `category_id`  BIGINT NOT NULL COMMENT '카테고리 아이디',
                               `payment_id`   BIGINT NOT NULL COMMENT '결제수단 아이디',
                               `user_id`      BIGINT NOT NULL COMMENT '사용자 아이디',
                               `item_plan_id` BIGINT NULL COMMENT '아이템 반복 아이디',
                               `amount`       DECIMAL(10,2) NULL COMMENT '금액',
                               `occurred_at`  DATE          NULL COMMENT '날짜',
                               `memo`         VARCHAR(100)  NULL COMMENT '입출금 내용',

                               `reg_dt`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `reg_user`     VARCHAR(50) NOT NULL,
                               `mod_dt`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               `mod_user`     VARCHAR(50) NOT NULL,

                               CONSTRAINT `PK_LEDGER_ITEM` PRIMARY KEY (`id`, `category_id`, `payment_id`)
);

-- 자산 계획
CREATE TABLE `ledger_asset_plan` (
                                     `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '자산계획 아이디',
                                     `category_id`   BIGINT NOT NULL COMMENT '카테고리 아이디',
                                     `interest_rate` DECIMAL(5,2) NULL COMMENT '예상 수익률(%)',
                                     `interest_type` VARCHAR(10)  NULL COMMENT '이자 계산 방식(단리/복리)',

                                     `reg_dt`        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     `reg_user`      VARCHAR(50) NOT NULL,
                                     `mod_dt`        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     `mod_user`      VARCHAR(50) NOT NULL,

                                     CONSTRAINT `PK_LEDGER_ASSET_PLAN` PRIMARY KEY (`id`, `category_id`),
                                     CONSTRAINT `CHK_INTEREST_TYPE` CHECK (`interest_type` IN ('SIMPLE','COMPOUND') OR `interest_type` IS NULL)
);

-- 투자 집계 설정
CREATE TABLE `ledger_invest_setting` (
                                              `id`     BIGINT NOT NULL AUTO_INCREMENT,
                                              `category_id` BIGINT NOT NULL,
                                              `show_yn`   VARCHAR(1) NOT NULL DEFAULT 'Y',
                                              `use_yn`  VARCHAR(1) NOT NULL DEFAULT 'Y',
                                              `reg_dt`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              `reg_user`    VARCHAR(50) NOT NULL,
                                              `mod_dt`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                              `mod_user`    VARCHAR(50) NOT NULL,
                                              CONSTRAINT `PK_LEDGER_INVEST_SETTING` PRIMARY KEY (`id`),
                                              UNIQUE KEY `UQ_LEDGER_INVEST_SETTING` (`category_id`),
                                              CHECK (`show_yn` IN ('Y','N')),
                                              CHECK (`use_yn` IN ('Y','N'))
);

-- 투자 집계
CREATE TABLE `ledger_invest_summary` (
                                              `id`     BIGINT NOT NULL AUTO_INCREMENT,
                                              `invest_setting_id` BIGINT NOT NULL,
                                              `year`        INTEGER NOT NULL,
                                              `month`       INTEGER  NOT NULL,
                                              `principal`   bigint   NOT NULL DEFAULT 0,
                                              `reg_dt`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              `reg_user`    VARCHAR(50) NOT NULL,
                                              `mod_dt`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                              `mod_user`    VARCHAR(50) NOT NULL,
                                              CONSTRAINT `PK_LEDGER_INVEST_SUMMARY` PRIMARY KEY (`id`),
                                              UNIQUE KEY `UQ_LEDGER_INVEST_SUMMARY` (`invest_setting_id`,`year`,`month`),
                                              CHECK (`month` BETWEEN 1 AND 12)
);