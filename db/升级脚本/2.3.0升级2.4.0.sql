ALTER TABLE `base_wx`.`wx_user` ADD COLUMN `vip_id` VARCHAR(32) NULL COMMENT '会员ID' AFTER `session_key`;
ALTER TABLE `base_wx`.`wx_user` CHANGE `vip_id` `vip_user_id` VARCHAR(32) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '会员用户ID'; 


ALTER TABLE `base_wx`.`wx_user` CHANGE `vip_user_id` `mall_user_id` VARCHAR(32) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商城用户ID';

ALTER TABLE `base_wx`.`wx_user` DROP INDEX `uk_openid`, DROP PRIMARY KEY, ADD PRIMARY KEY (`id`, `open_id`);


#兼容之前版本的数据
INSERT INTO `base_mall`.`user_info` (
  `id`,
  `tenant_id`,
  `del_flag`,
  `create_time`,
  `update_time`,
  `app_type`,
  `app_id`,
  `phone`,
  `user_grade`,
  `points_current`,
  `points_accrued`,
  `amount_accrued`,
  `number_accrued`,
  `nick_name`,
  `sex`,
  `headimg_url`,
  `city`,
  `country`,
  `province`
)
SELECT
  `id`,
  `tenant_id`,
  '0',
  `create_time`,
  now(),
  `app_type`,
  `app_id`,
  `phone`,
  IF(`headimg_url` IS NULL,0,1),
  0,
  0,
  '0',
  0,
  `nick_name`,
  `sex`,
  `headimg_url`,
  `city`,
  `country`,
  `province`
FROM
  `base_wx`.`wx_user`
  where `mall_user_id` is null and `app_type` = '1';

#兼容之前版本的数据
update
  `base_wx`.`wx_user`
SET
  `mall_user_id` = `id`
where `mall_user_id` is null
  and `app_type` = '1';

ALTER TABLE `base_mall`.`user_info` DROP COLUMN `points_accrued`, DROP COLUMN `amount_accrued`, DROP COLUMN `number_accrued`;

ALTER TABLE `base_mall`.`points_record` ADD COLUMN `spu_id` VARCHAR(32) NULL COMMENT '商品ID' AFTER `description`, ADD COLUMN `order_item_id` VARCHAR(32) NULL COMMENT '订单详情ID' AFTER `spu_id`; 

ALTER TABLE `base_mall`.`points_record` ADD COLUMN `record_type` CHAR(2) NULL COMMENT '记录类型1、系统初始化；2、商品赠送；3、退款赠送减回；4、商品抵扣；5、退款抵扣加回' AFTER `order_item_id`; 
ALTER TABLE `base_mall`.`points_record` CHANGE `record_type` `record_type` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '记录类型0、用户初始化；1、会员初始化；2、商品赠送；3、退款赠送减回；4、商品抵扣；5、退款抵扣加回'; 

ALTER TABLE `base_mall`.`points_config` CHANGE `premise_num` `premise_amount` DECIMAL(10,2) NULL COMMENT '订单金额满多少可使用抵扣'; 

ALTER TABLE `base_mall`.`order_item` ADD COLUMN `payment_price` DECIMAL(10,2) NULL COMMENT '支付金额' AFTER `sales_price`; 

UPDATE `base_mall`.`order_item` SET `payment_price` = `sales_price`*`quantity`;

ALTER TABLE `base_mall`.`order_info` CHANGE `sales_price` `sales_price` DECIMAL(10,2) NOT NULL COMMENT '销售金额' AFTER `appraises_status`, CHANGE `logistics_price` `logistics_price` DECIMAL(10,2) NOT NULL COMMENT '物流金额' AFTER `sales_price`, CHANGE `payment_price` `payment_price` DECIMAL(10,2) NOT NULL COMMENT '支付金额（销售金额+物流金额）'; 

ALTER TABLE `base_mall`.`points_record` CHANGE `record_type` `record_type` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '记录类型0、用户初始化；1、会员初始化；2、商品赠送；3、退款赠送减回；4、商品抵扣；5、订单取消抵扣加回；6、退款抵扣加回'; 

ALTER TABLE `base_mall`.`user_info` ADD COLUMN `city` VARCHAR(64) NULL COMMENT '所在城市' AFTER `headimg_url`, ADD COLUMN `country` VARCHAR(64) NULL COMMENT '所在国家' AFTER `city`, ADD COLUMN `province` VARCHAR(64) NULL COMMENT '所在省份' AFTER `country`; 

ALTER TABLE `base_mall`.`user_info` CHANGE `vip_code` `user_code` INT(11) NOT NULL AUTO_INCREMENT COMMENT '用户编码'; 

UPDATE `base_mall`.`goods_spu` SET `points_give_switch` = '0' WHERE `points_give_switch` IS NULL;
UPDATE `base_mall`.`goods_spu` SET `points_deduct_switch` = '0' WHERE `points_deduct_switch` IS NULL;
ALTER TABLE `base_mall`.`order_item` CHANGE `payment_points` `payment_points` INT(11) DEFAULT 0 NULL COMMENT '支付积分'; 
ALTER TABLE `base_mall`.`order_info` CHANGE `payment_points` `payment_points` INT(11) DEFAULT 0 NULL COMMENT '支付积分'; 


CREATE TABLE `base_mall`.`coupon_info` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
  `type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型1、代金券；2：折扣券',
  `premise_amount` decimal(10,2) DEFAULT NULL COMMENT '订单金额满多少可使用',
  `expire_type` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '到期类型1、领券后生效；2：固定时间段',
  `stock` int(11) NOT NULL DEFAULT '0' COMMENT '库存',
  `reduce_amount` decimal(10,2) NOT NULL COMMENT '减免金额（代金券特有）',
  `discount` decimal(10,1) NOT NULL COMMENT '折扣率（折扣券特有）',
  `valid_days` int(3) NOT NULL COMMENT '有效天数（领券后生效特有）',
  `valid_begin_time` datetime NOT NULL COMMENT '有效开始时间（固定时间段特有）',
  `valid_end_time` datetime NOT NULL COMMENT '有效结束时间（固定时间段特有）',
  `remarks` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `enable` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '（1：开启；0：关闭）',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='电子券';

CREATE TABLE `base_mall`.`coupon_goods` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `coupon_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电子券ID',
  `spu_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品Id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='电子券商品关联';

ALTER TABLE `base_mall`.`coupon_info` CHANGE `name` `name` VARCHAR(30) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称', CHANGE `reduce_amount` `reduce_amount` DECIMAL(10,2) NULL COMMENT '减免金额（代金券特有）', CHANGE `discount` `discount` DECIMAL(10,1) NULL COMMENT '折扣率（折扣券特有）', CHANGE `valid_days` `valid_days` INT(3) NULL COMMENT '有效天数（领券后生效特有）', CHANGE `valid_begin_time` `valid_begin_time` DATETIME NULL COMMENT '有效开始时间（固定时间段特有）', CHANGE `valid_end_time` `valid_end_time` DATETIME NULL COMMENT '有效结束时间（固定时间段特有）', CHANGE `enable` `enable` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '（1：开启；0：关闭）'; 

CREATE TABLE `base_mall`.`coupon_user` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `coupon_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电子券ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `coupon_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电子券码',
  `status` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '状态0、未使用；1、已使用',
  `used_time` datetime DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='电子券用户记录';

ALTER TABLE `base_mall`.`coupon_user` ADD KEY `ids_user` (`user_id`); 

ALTER TABLE `base_upms`.`sys_role_menu` ADD COLUMN `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间' AFTER `menu_id`; 

ALTER TABLE `base_mall`.`coupon_info` ADD COLUMN `suit_type` CHAR(2) NULL COMMENT '适用类型1、全部商品；2、部分商品' AFTER `valid_end_time`; 

ALTER TABLE `base_mall`.`coupon_user` ADD COLUMN `valid_begin_time` DATETIME NULL COMMENT '有效开始时间' AFTER `coupon_code`, ADD COLUMN `valid_end_time` DATETIME NULL COMMENT '有效结束时间' AFTER `valid_begin_time`, CHANGE `status` `status` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' NULL COMMENT '状态0、未使用；1、已使用' AFTER `valid_end_time`; 

ALTER TABLE `base_mall`.`coupon_user` DROP PRIMARY KEY, ADD PRIMARY KEY (`id`, `coupon_code`); 

ALTER TABLE `base_mall`.`coupon_user` CHANGE `coupon_code` `coupon_code` INT NOT NULL AUTO_INCREMENT COMMENT '电子券码', ADD KEY(`coupon_code`); 
ALTER TABLE `base_mall`.`coupon_info` CHANGE `suit_type` `suit_type` CHAR(2) CHARSET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '适用类型1、全部商品；2、指定商品可用'; 
ALTER TABLE `base_mall`.`coupon_user` ADD COLUMN `name` VARCHAR(30) NOT NULL COMMENT '名称' AFTER `used_time`, ADD COLUMN `type` CHAR(2) NULL COMMENT '类型1、代金券；2：折扣券' AFTER `name`, ADD COLUMN `premise_amount` DECIMAL(10,2) NULL COMMENT '订单金额满多少可使用' AFTER `type`, ADD COLUMN `reduce_amount` DECIMAL(10,2) NULL COMMENT '减免金额（代金券特有）' AFTER `premise_amount`, ADD COLUMN `discount` DECIMAL(10,1) NULL COMMENT '折扣率（折扣券特有）' AFTER `reduce_amount`; 
ALTER TABLE `base_mall`.`coupon_user` ADD COLUMN `suit_type` CHAR(2) NULL COMMENT '适用类型1、全部商品；2、指定商品可用' AFTER `discount`; 
ALTER TABLE `base_mall`.`order_info` ADD COLUMN `payment_coupon` DECIMAL(10,2) NULL COMMENT '电子券支付金额' AFTER `payment_points`; 
ALTER TABLE `base_mall`.`order_item` ADD COLUMN `payment_coupon` DECIMAL(10,2) NULL COMMENT '电子券支付金额' AFTER `payment_points`, ADD COLUMN `coupon_user_id` VARCHAR(32) NULL COMMENT '用户电子券ID' AFTER `payment_coupon`; 
ALTER TABLE `base_mall`.`coupon_info` ADD COLUMN `version` INT DEFAULT 0 NULL COMMENT '版本号' AFTER `enable`; 

ALTER TABLE `base_mall`.`order_info` CHANGE `sales_price` `sales_price` DECIMAL(10,2) DEFAULT 0 NOT NULL COMMENT '销售金额', CHANGE `logistics_price` `logistics_price` DECIMAL(10,2) DEFAULT 0 NOT NULL COMMENT '物流金额', CHANGE `payment_price` `payment_price` DECIMAL(10,2) DEFAULT 0 NOT NULL COMMENT '支付金额（销售金额+物流金额）', CHANGE `payment_coupon` `payment_coupon` DECIMAL(10,2) DEFAULT 0 NULL COMMENT '电子券支付金额'; 
ALTER TABLE `base_mall`.`order_item` CHANGE `payment_price` `payment_price` DECIMAL(10,2) DEFAULT 0 NULL COMMENT '支付金额', CHANGE `payment_coupon` `payment_coupon` DECIMAL(10,2) DEFAULT 0 NULL COMMENT '电子券支付金额'; 
UPDATE `base_mall`.`order_info` SET `payment_coupon` = 0 WHERE `payment_coupon` IS NULL;
UPDATE `base_mall`.`order_item` SET `payment_coupon` = 0 WHERE `payment_coupon` IS NULL;

ALTER TABLE `base_mall`.`order_info` CHANGE `payment_coupon` `payment_coupon_price` DECIMAL(10,2) DEFAULT 0.00 NULL COMMENT '电子券抵扣金额'; 
ALTER TABLE `base_mall`.`order_item` CHANGE `payment_coupon` `payment_coupon_price` DECIMAL(10,2) DEFAULT 0.00 NULL COMMENT '电子券抵扣金额'; 
ALTER TABLE `base_mall`.`order_item` ADD COLUMN `payment_points_price` DECIMAL(10,2) NULL COMMENT '积分抵扣金额' AFTER `payment_price`, CHANGE `payment_coupon_price` `payment_coupon_price` DECIMAL(10,2) DEFAULT 0.00 NULL COMMENT '电子券支付金额' AFTER `payment_points_price`; 
ALTER TABLE `base_mall`.`order_item` CHANGE `payment_points_price` `payment_points_price` DECIMAL(10,2) DEFAULT 0.00 NULL COMMENT '积分抵扣金额'; 
ALTER TABLE `base_mall`.`order_info` ADD COLUMN `payment_points_price` DECIMAL(10,2) DEFAULT 0.00 NULL COMMENT '积分抵扣金额' AFTER `payment_price`, CHANGE `payment_coupon_price` `payment_coupon_price` DECIMAL(10,2) DEFAULT 0.00 NULL COMMENT '电子券抵扣金额' AFTER `payment_points_price`; 

CREATE TABLE `base_mall`.`freight_templat` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `sort` int(11) NOT NULL COMMENT '排序字段',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `type` char(2) NOT NULL COMMENT '模板类型1、买家承担运费；2、卖家包邮',
  `charge_type` char(2) NOT NULL COMMENT '计费方式1、按件数；2、按重量；3、按体积',
  `first_num` decimal(10,2) DEFAULT '0.00' COMMENT '首件、首体积、首重量',
  `first_freight` decimal(10,2) DEFAULT '0.00' COMMENT '首运费',
  `continue_num` decimal(10,2) DEFAULT '0.00' COMMENT '续件、续体积、续重量',
  `continue_freight` decimal(10,2) DEFAULT '0.00' COMMENT '续运费',
  PRIMARY KEY (`id`),
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='运费模板';
UPDATE `base_upms`.`sys_menu` SET `permission` = 'sys_tenant_index' WHERE `id` = '2304aae6101ef1b56160e5cfe63b997a';