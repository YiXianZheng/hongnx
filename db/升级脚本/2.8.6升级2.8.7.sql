CREATE TABLE `base_mall`.`seckill_hall` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '�����⻧',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '�߼�ɾ����ǣ�0����ʾ��1�����أ�',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '������ʱ��',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '������ID',
  `sort` int NOT NULL DEFAULT '0' COMMENT '�����ֶ�',
  `hall_date` varchar(20) NOT NULL COMMENT '�᳡����',
  `hall_time` int NOT NULL COMMENT '�᳡ʱ�䣨��λʱ��0~23��',
  `enable` char(2) NOT NULL COMMENT '��1��������0���رգ�',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_date_time` (`hall_date`,`hall_time`),
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='��ɱ�᳡';

CREATE TABLE `base_mall`.`seckill_info` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '�����⻧',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '�߼�ɾ����ǣ�0����ʾ��1�����أ�',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '������ʱ��',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '������ID',
  `sort` int NOT NULL DEFAULT '0' COMMENT '�����ֶ�',
  `enable` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '��1��������0���رգ�',
  `spu_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '��ƷId',
  `sku_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'skuId',
  `name` varchar(100) DEFAULT NULL COMMENT '��ɱ����',
  `seckill_price` decimal(10,2) NOT NULL COMMENT '��ɱ�׼�',
  `seckill_num` int DEFAULT '0' COMMENT '��������',
  `limit_num` int NOT NULL COMMENT '������',
  `each_limit_num` int NOT NULL COMMENT 'ÿ���޹�����',
  `pic_url` varchar(500) DEFAULT NULL COMMENT 'ͼƬ',
  `seckill_rule` text COMMENT '��ɱ����',
  `share_title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '������� ',
  `version` int DEFAULT NULL COMMENT '�汾��',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='��ɱ��Ʒ';

CREATE TABLE `base_mall`.`seckill_hall_info` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '�����⻧',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '�߼�ɾ����ǣ�0����ʾ��1�����أ�',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '������ʱ��',
  `create_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '������ID',
  `sort` int NOT NULL DEFAULT '0' COMMENT '�����ֶ�',
  `seckill_hall_id` varchar(32) NOT NULL COMMENT '�᳡Id',
  `seckill_info_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '��ɱ��ƷId',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='��ɱ�᳡��Ʒ';

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293384409782743041','��ɱ��Ʒɾ��','mall:seckillinfo:del','','1293383822223667201','','','0','0','1','2020-08-12 11:10:47','2020-08-12 11:10:47','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293384332062289922','��ɱ��Ʒ�޸�','mall:seckillinfo:edit','','1293383822223667201','','','0','0','1','2020-08-12 11:10:29','2020-08-12 11:10:29','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293384241469517825','��ɱ��Ʒ����','mall:seckillinfo:add','','1293383822223667201','','','0','0','1','2020-08-12 11:10:07','2020-08-12 11:10:07','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293384142387474433','��ɱ��Ʒ��ѯ','mall:seckillinfo:get','','1293383822223667201','','','0','0','1','2020-08-12 11:09:44','2020-08-12 11:09:44','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293384016864538626','��ɱ��Ʒ�б�','mall:seckillinfo:index','','1293383822223667201','','','0','0','1','2020-08-12 11:09:14','2020-08-12 11:09:14','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293383822223667201','��ɱ��Ʒ','','seckillinfo','1293360350588104706','el-icon-s-cooperation','views/mall/seckillinfo/index','10','0','0','2020-08-12 11:08:27','2020-08-12 11:08:27','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293361554047168513','��ɱ�᳡��ѯ','mall:seckillhall:get','','1293360792097320962','','','0','0','1','2020-08-12 09:39:58','2020-08-12 09:39:58','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293361400409812994','��ɱ�᳡�б�','mall:seckillhall:index','','1293360792097320962','','','0','0','1','2020-08-12 09:39:22','2020-08-12 09:39:22','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293361232570544129','��ɱ�᳡ɾ��','mall:seckillhall:del','','1293360792097320962','','','0','0','1','2020-08-12 09:38:41','2020-08-12 09:38:41','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293361105973866497','��ɱ�᳡�޸�','mall:seckillhall:edit','','1293360792097320962','','','0','0','1','2020-08-12 09:38:11','2020-08-12 09:38:11','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293360954580463618','��ɱ�᳡����','mall:seckillhall:add','','1293360792097320962','','','0','0','1','2020-08-12 09:37:35','2020-08-12 09:37:35','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293360792097320962','��ɱ�᳡','','seckillhall','1293360350588104706','el-icon-s-management','views/mall/seckillhall/index','20','0','0','2020-08-12 09:36:56','2020-08-12 09:36:56','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1293360350588104706','��ɱ����','','seckill','8000000','el-icon-goods','Layout','30','0','0','2020-08-12 09:35:11','2020-08-12 09:35:11','0');

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