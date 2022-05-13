CREATE TABLE `base_mall`.`seckill_hall` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序字段',
  `hall_date` varchar(20) NOT NULL COMMENT '会场日期',
  `hall_time` int NOT NULL COMMENT '会场时间（单位时：0~23）',
  `enable` char(2) NOT NULL COMMENT '（1：开启；0：关闭）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_date_time` (`hall_date`,`hall_time`),
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='秒杀会场';

CREATE TABLE `base_mall`.`seckill_info` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序字段',
  `enable` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '（1：开启；0：关闭）',
  `spu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品Id',
  `sku_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'skuId',
  `name` varchar(100) DEFAULT NULL COMMENT '秒杀名称',
  `seckill_price` decimal(10,2) NOT NULL COMMENT '秒杀底价',
  `seckill_num` int DEFAULT '0' COMMENT '已售数量',
  `limit_num` int NOT NULL COMMENT '总限量',
  `each_limit_num` int NOT NULL COMMENT '每人限购数量',
  `pic_url` varchar(500) DEFAULT NULL COMMENT '图片',
  `seckill_rule` text COMMENT '秒杀规则',
  `share_title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分享标题 ',
  `version` int DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='秒杀商品';

CREATE TABLE `base_mall`.`seckill_hall_info` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建者ID',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序字段',
  `seckill_hall_id` varchar(32) NOT NULL COMMENT '会场Id',
  `seckill_info_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '秒杀商品Id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='秒杀会场商品';

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293384409782743041','秒杀商品删除','mall:seckillinfo:del','','1293383822223667201','','','0','0','1','2020-08-12 11:10:47','2020-08-12 11:10:47','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293384332062289922','秒杀商品修改','mall:seckillinfo:edit','','1293383822223667201','','','0','0','1','2020-08-12 11:10:29','2020-08-12 11:10:29','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293384241469517825','秒杀商品新增','mall:seckillinfo:add','','1293383822223667201','','','0','0','1','2020-08-12 11:10:07','2020-08-12 11:10:07','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293384142387474433','秒杀商品查询','mall:seckillinfo:get','','1293383822223667201','','','0','0','1','2020-08-12 11:09:44','2020-08-12 11:09:44','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293384016864538626','秒杀商品列表','mall:seckillinfo:index','','1293383822223667201','','','0','0','1','2020-08-12 11:09:14','2020-08-12 11:09:14','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293383822223667201','秒杀商品','','seckillinfo','1293360350588104706','el-icon-s-cooperation','views/mall/seckillinfo/index','10','0','0','2020-08-12 11:08:27','2020-08-12 11:08:27','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293361554047168513','秒杀会场查询','mall:seckillhall:get','','1293360792097320962','','','0','0','1','2020-08-12 09:39:58','2020-08-12 09:39:58','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293361400409812994','秒杀会场列表','mall:seckillhall:index','','1293360792097320962','','','0','0','1','2020-08-12 09:39:22','2020-08-12 09:39:22','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293361232570544129','秒杀会场删除','mall:seckillhall:del','','1293360792097320962','','','0','0','1','2020-08-12 09:38:41','2020-08-12 09:38:41','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293361105973866497','秒杀会场修改','mall:seckillhall:edit','','1293360792097320962','','','0','0','1','2020-08-12 09:38:11','2020-08-12 09:38:11','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293360954580463618','秒杀会场新增','mall:seckillhall:add','','1293360792097320962','','','0','0','1','2020-08-12 09:37:35','2020-08-12 09:37:35','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293360792097320962','秒杀会场','','seckillhall','1293360350588104706','el-icon-s-management','views/mall/seckillhall/index','20','0','0','2020-08-12 09:36:56','2020-08-12 09:36:56','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293360350588104706','秒杀管理','','seckill','8000000','el-icon-goods','Layout','30','0','0','2020-08-12 09:35:11','2020-08-12 09:35:11','0');

insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1293384409782743041','2020-08-12 11:10:47','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1293384332062289922','2020-08-12 11:10:29','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1293384241469517825','2020-08-12 11:10:07','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1293384142387474433','2020-08-12 11:09:44','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1293384016864538626','2020-08-12 11:09:14','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1293383822223667201','2020-08-12 11:08:27','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1293361554047168513','2020-08-12 09:39:58','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1293361400409812994','2020-08-12 09:39:22','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1293361232570544129','2020-08-12 09:38:42','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1293361105973866497','2020-08-12 09:38:11','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1293360954580463618','2020-08-12 09:37:35','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1293360792097320962','2020-08-12 09:36:57','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1293360350588104706','2020-08-12 09:35:11','1');

ALTER TABLE `base_mall`.`bargain_user` ADD UNIQUE INDEX `uk_user_bargain` (`bargain_id`, `user_id`); 