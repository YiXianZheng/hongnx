ALTER TABLE `base_mall`.`goods_category` ADD COLUMN `enable` CHAR(2) NOT NULL COMMENT '��1��������0���رգ�' AFTER `tenant_id`;
UPDATE `base_mall`.`goods_category` SET `enable` = '1';

CREATE TABLE `base_mall`.`article_info` (
  `id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '�����⻧',
  `del_flag` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '�߼�ɾ����ǣ�0����ʾ��1�����أ�',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '������ʱ��',
  `enable` CHAR(2) NOT NULL COMMENT '��1��������0���رգ�',
  `pic_url` VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '����ͼƬ',
  `category_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '���·���ID',
  `author_name` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '����',
  `article_title` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '���±���',
  `article_introduction` VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '���¼��',
  `article_content` TEXT COMMENT '��������',
  `original_url` VARCHAR(1024) DEFAULT NULL COMMENT 'ԭ�ĵ�ַ',
  `is_banner` CHAR(2) NOT NULL DEFAULT '0' COMMENT '�Ƿ�banner��ʾ��1���ǣ�0����',
  `is_hot` CHAR(2) NOT NULL DEFAULT '0' COMMENT '�Ƿ��������£�1���ǣ�0����',
  PRIMARY KEY (`id`),
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='���±�';

CREATE TABLE `base_mall`.`article_category` (
  `id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '�����⻧',
  `del_flag` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '�߼�ɾ����ǣ�0����ʾ��1�����أ�',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '������ʱ��',
  `enable` CHAR(2) NOT NULL COMMENT '��1��������0���رգ�',
  `sort` SMALLINT DEFAULT NULL COMMENT '����',
  `name` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '������',
  PRIMARY KEY (`id`),
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='���·����';

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331128115079938050','����ɾ��','mall:articleinfo:del','','1331127314508939265','','','0','0','1','2020-11-24 14:50:48','2020-11-24 14:50:48','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331128039997702145','�����޸�','mall:articleinfo:edit','','1331127314508939265','','','0','0','1','2020-11-24 14:50:30','2020-11-24 14:50:30','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331127967310413825','��������','mall:articleinfo:add','','1331127314508939265','','','0','0','1','2020-11-24 14:50:13','2020-11-24 14:50:13','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331127886733639681','���²�ѯ','mall:articleinfo:get','','1331127314508939265','','','0','0','1','2020-11-24 14:49:54','2020-11-24 14:49:54','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331127770438172674','�����б�','mall:articleinfo:index','','1331127314508939265','','','0','0','1','2020-11-24 14:49:26','2020-11-24 14:49:26','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331127314508939265','��������','','articleinfo','1331123534564425730','el-icon-s-order','views/mall/articleinfo/index','20','0','0','2020-11-24 14:47:37','2020-11-24 14:47:37','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331124631018405890','���·���ɾ��','mall:articlecategory:del','','1331123875771056130','','','0','0','1','2020-11-24 14:36:57','2020-11-24 14:36:57','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331124539226062849','���·����޸�','mall:articlecategory:edit','','1331123875771056130','','','0','0','1','2020-11-24 14:36:35','2020-11-24 14:36:35','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331124445831495681','���·�������','mall:articlecategory:add','','1331123875771056130','','','0','0','1','2020-11-24 14:36:13','2020-11-24 14:36:13','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331124349102456833','���·����ѯ','mall:articlecategory:get','','1331123875771056130','','','0','0','1','2020-11-24 14:35:50','2020-11-24 14:35:50','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331124222807769090','���·����б�','mall:articlecategory:index','','1331123875771056130','','','0','0','1','2020-11-24 14:35:20','2020-11-24 14:35:20','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331123875771056130','���·���','','articlecategory','1331123534564425730','el-icon-s-operation','views/mall/articlecategory/index','10','0','0','2020-11-24 14:33:57','2020-11-24 14:33:57','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331123534564425730','���¹���','','article','8000000','el-icon-s-management','Layout','40','0','0','2020-11-24 14:32:36','2020-11-24 14:32:36','0');

insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1331128115079938050','2020-11-24 14:50:48','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1331128039997702145','2020-11-24 14:50:30','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1331127967310413825','2020-11-24 14:50:13','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1331127886733639681','2020-11-24 14:49:54','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1331127770438172674','2020-11-24 14:49:26','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1331127314508939265','2020-11-24 14:47:37','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1331124631018405890','2020-11-24 14:36:57','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1331124539226062849','2020-11-24 14:36:35','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1331124445831495681','2020-11-24 14:36:13','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1331124349102456833','2020-11-24 14:35:50','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1331124222807769090','2020-11-24 14:35:20','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1331123875771056130','2020-11-24 14:33:57','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1331123534564425730','2020-11-24 14:32:36','1');


insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342074603792588801','�����ѯ','sys:cache:index','','1342074455276478465','','','0','0','1','2020-12-24 19:48:14','2020-12-24 19:48:14','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342074455276478465','������','','cache','1227202152985784322','el-icon-upload','views/upms/cache/index','50','0','0','2020-12-24 19:47:39','2020-12-24 19:47:39','0');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342074603792588801','2020-12-24 19:48:14','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342074455276478465','2020-12-24 19:47:39','1');


insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342089211811028994','��Ա����','','user','8000000','el-icon-user-solid','Layout','2','0','0','2020-12-24 20:46:17','2020-12-24 20:46:17','0');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342089211811028994','2020-12-24 20:46:17','1');

DELETE FROM `base_upms`.`sys_menu` WHERE id = 'db95df20df51a46afa7935f38efb7360';
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('db95df20df51a46afa7935f38efb7360','��Ա��Ϣ',NULL,'userInfo','1342089211811028994','el-icon-user','views/mall/userinfo/index','10','0','0','2019-12-10 20:57:03','2020-12-24 20:48:08','0');

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342093905342062593','��Ʒ�ղ��б�','mall:usercollect:index','','1342093572213661698','','','0','0','1','2020-12-24 21:04:56','2020-12-24 21:04:56','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342093572213661698','��Ʒ�ղ�','','goods','1342089211811028994','el-icon-s-goods','views/mall/usercollect/goods','20','0','0','2020-12-24 21:03:37','2020-12-24 21:03:37','0');

insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342093905342062593','2020-12-24 21:04:56','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342093572213661698','2020-12-24 21:03:37','1');


insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342112864309174273','�㼣�б�','mall:userfootprint:index','','1342112579171999746','','','0','0','1','2020-12-24 22:20:16','2020-12-24 22:20:16','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342112579171999746','��Ա�㼣','','userfootprint','1342089211811028994','el-icon-s-marketing','views/mall/userfootprint/index','40','0','0','2020-12-24 22:19:08','2020-12-24 22:19:08','0');

insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342112864309174273','2020-12-24 22:20:16','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342112579171999746','2020-12-24 22:19:08','1');

CREATE TABLE `base_mall`.`user_footprint` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '�����⻧',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '�߼�ɾ����ǣ�0����ʾ��1�����أ�',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '������ʱ��',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '�û����',
  `relation_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '����ID����ƷID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='�û��㼣';

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342369625242902530','С����ֱ��ɾ��','wxma:wxmalive:del','','1342368489077252098','','','0','0','1','2020-12-25 15:20:33','2020-12-25 15:20:33','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342369546528399362','С����ֱ���޸�','wxma:wxmalive:edit','','1342368489077252098','','','0','0','1','2020-12-25 15:20:14','2020-12-25 15:20:14','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342369468602425345','С����ֱ������','wxma:wxmalive:add','','1342368489077252098','','','0','0','1','2020-12-25 15:19:56','2020-12-25 15:19:56','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342368718203691010','ֱ���б�','wxma:wxmalive:index','','1342368489077252098','','','0','0','1','2020-12-25 15:16:57','2020-12-25 15:16:57','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342368489077252098','ֱ������','','wxlive','ae0619ea3e5b86a3e382081680785f7e','el-icon-video-camera-solid','views/wxma/wxlive/index','40','0','0','2020-12-25 15:16:02','2020-12-25 15:16:02','0');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342369625242902530','2020-12-25 15:20:33','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342369546528399362','2020-12-25 15:20:14','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342369468602425345','2020-12-25 15:19:56','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342368718203691010','2020-12-25 15:16:57','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342368489077252098','2020-12-25 15:16:02','1');