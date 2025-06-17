CREATE TABLE `user` (
    `user_id` BIGINT NOT NULL AUTO_INCREMENT,
    `activate_id` VARCHAR(50) NULL,
    `password` VARCHAR(255) NULL,
    `role_type` VARCHAR(10) NOT NULL,
    `address` VARCHAR(255) NULL,
    `email` VARCHAR(255) NULL,
    `provider` VARCHAR(10) NULL,
    `provider_id` VARCHAR(255) NULL,
    `reg_dt` DATE NOT NULL ,
    `reg_user` VARCHAR(50) NOT NULL,
    `mod_dt` DATE NOT NULL ,
    `mod_user` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`user_id`)
);

CREATE TABLE `ledger_payment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '결제수단 아이디',
    `user_id` BIGINT NOT NULL COMMENT '사용자 아이디',
    `name` VARCHAR(100) NULL COMMENT '결제수단 이름',
    `payment_type` VARCHAR(10) NULL COMMENT '결제수단 타입',
    `reg_dt` DATE NOT NULL ,
    `reg_user` VARCHAR(50) NOT NULL,
    `mod_dt` DATE NOT NULL ,
    `mod_user` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_user_TO_ledger_payment` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `ledger_category_group` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '카테고리 그룹 아이디',
    `user_id` BIGINT NOT NULL COMMENT '사용자 아이디',
    `name` VARCHAR(50) NOT NULL COMMENT '카테고리 그룹 아이디',
    `category_type` VARCHAR(10) NOT NULL COMMENT '수입, 지출 타입 enum(INCOME, EXPENSE)',
    `image_url` VARCHAR(255) NULL COMMENT '카테고리 아이콘 이미지',
    `reg_dt` DATE NOT NULL ,
    `reg_user` VARCHAR(50) NOT NULL,
    `mod_dt` DATE NOT NULL ,
    `mod_user` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_user_TO_ledger_category_group` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `ledger_category` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '카테고리 아이디',
    `category_group_id` BIGINT NOT NULL COMMENT '카테고리 그룹 아이디',
    `name` VARCHAR(50) NULL COMMENT '카테고리 소분류 이름',
    `investment_yn` VARCHAR(1) NOT NULL DEFAULT 'N' COMMENT '자산계획 사용여부',
    `recurring_type` VARCHAR(10) NULL COMMENT '반복 등록 여부(일, 주, 월)',
    `recurring_start_dt` DATE NULL COMMENT '반복시작일',
    `recurring_end_dt` DATE NULL COMMENT '반복종료일',
    `image_url` VARCHAR(255) NULL COMMENT '카테고리 아이콘 이미지',
    `reg_dt` DATE NOT NULL ,
    `reg_user` VARCHAR(50) NOT NULL,
    `mod_dt` DATE NOT NULL ,
    `mod_user` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_category_group_TO_category` FOREIGN KEY (`category_group_id`) REFERENCES `ledger_category_group` (`id`)
);

CREATE TABLE `ledger_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '아이템 아이디',
    `category_id` BIGINT NOT NULL COMMENT '카테고리 아이디',
    `payment_id` BIGINT NOT NULL COMMENT '결제수단 아이디',
    `amount` DECIMAL(10,2) NULL COMMENT '금액',
    `occurred_at` DATE NULL COMMENT '날짜',
    `memo` VARCHAR(100) NULL COMMENT '입출금 내용',
    `reg_dt` DATE NOT NULL ,
    `reg_user` VARCHAR(50) NOT NULL,
    `mod_dt` DATE NOT NULL ,
    `mod_user` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_category_TO_item` FOREIGN KEY (`category_id`) REFERENCES `ledger_category` (`id`),
    CONSTRAINT `FK_payment_TO_item` FOREIGN KEY (`payment_id`) REFERENCES `ledger_payment` (`id`)
);

CREATE TABLE `ledger_asset_plan` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '자산계획 아이디',
    `category_id` BIGINT NOT NULL COMMENT '카테고리 아이디',
    `interest_rate` DECIMAL(5,2) NULL COMMENT '예상 수익률(%)',
    `interest_type` VARCHAR(10) NULL COMMENT '이자 계산 방식(단리, 복리)',
    `reg_dt` DATE NOT NULL ,
    `reg_user` VARCHAR(50) NOT NULL,
    `mod_dt` DATE NOT NULL ,
    `mod_user` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_category_TO_asset_plan` FOREIGN KEY (`category_id`) REFERENCES `ledger_category` (`id`)
);
