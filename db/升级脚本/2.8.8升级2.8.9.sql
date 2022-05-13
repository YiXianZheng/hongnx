ALTER TABLE `base_mall`.`goods_category` ADD COLUMN `enable` CHAR(2) NOT NULL COMMENT '（1：开启；0：关闭）' AFTER `tenant_id`;
UPDATE `base_mall`.`goods_category` SET `enable` = '1';

CREATE TABLE `base_mall`.`article_info` (
  `id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `enable` CHAR(2) NOT NULL COMMENT '（1：开启；0：关闭）',
  `pic_url` VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章图片',
  `category_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章分类ID',
  `author_name` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '作者',
  `article_title` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章标题',
  `article_introduction` VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文章简介',
  `article_content` TEXT COMMENT '文章内容',
  `original_url` VARCHAR(1024) DEFAULT NULL COMMENT '原文地址',
  `is_banner` CHAR(2) NOT NULL DEFAULT '0' COMMENT '是否banner显示（1：是；0：否）',
  `is_hot` CHAR(2) NOT NULL DEFAULT '0' COMMENT '是否热门文章（1：是；0：否）',
  PRIMARY KEY (`id`),
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章表';

CREATE TABLE `base_mall`.`article_category` (
  `id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `enable` CHAR(2) NOT NULL COMMENT '（1：开启；0：关闭）',
  `sort` SMALLINT DEFAULT NULL COMMENT '排序',
  `name` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名',
  PRIMARY KEY (`id`),
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='文章分类表';

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331128115079938050','文章删除','mall:articleinfo:del','','1331127314508939265','','','0','0','1','2020-11-24 14:50:48','2020-11-24 14:50:48','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331128039997702145','文章修改','mall:articleinfo:edit','','1331127314508939265','','','0','0','1','2020-11-24 14:50:30','2020-11-24 14:50:30','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331127967310413825','文章新增','mall:articleinfo:add','','1331127314508939265','','','0','0','1','2020-11-24 14:50:13','2020-11-24 14:50:13','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331127886733639681','文章查询','mall:articleinfo:get','','1331127314508939265','','','0','0','1','2020-11-24 14:49:54','2020-11-24 14:49:54','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331127770438172674','文章列表','mall:articleinfo:index','','1331127314508939265','','','0','0','1','2020-11-24 14:49:26','2020-11-24 14:49:26','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331127314508939265','文章内容','','articleinfo','1331123534564425730','el-icon-s-order','views/mall/articleinfo/index','20','0','0','2020-11-24 14:47:37','2020-11-24 14:47:37','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331124631018405890','文章分类删除','mall:articlecategory:del','','1331123875771056130','','','0','0','1','2020-11-24 14:36:57','2020-11-24 14:36:57','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331124539226062849','文章分类修改','mall:articlecategory:edit','','1331123875771056130','','','0','0','1','2020-11-24 14:36:35','2020-11-24 14:36:35','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331124445831495681','文章分类新增','mall:articlecategory:add','','1331123875771056130','','','0','0','1','2020-11-24 14:36:13','2020-11-24 14:36:13','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331124349102456833','文章分类查询','mall:articlecategory:get','','1331123875771056130','','','0','0','1','2020-11-24 14:35:50','2020-11-24 14:35:50','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331124222807769090','文章分类列表','mall:articlecategory:index','','1331123875771056130','','','0','0','1','2020-11-24 14:35:20','2020-11-24 14:35:20','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331123875771056130','文章分类','','articlecategory','1331123534564425730','el-icon-s-operation','views/mall/articlecategory/index','10','0','0','2020-11-24 14:33:57','2020-11-24 14:33:57','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1331123534564425730','文章管理','','article','8000000','el-icon-s-management','Layout','40','0','0','2020-11-24 14:32:36','2020-11-24 14:32:36','0');

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


insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342074603792588801','缓存查询','sys:cache:index','','1342074455276478465','','','0','0','1','2020-12-24 19:48:14','2020-12-24 19:48:14','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342074455276478465','缓存监控','','cache','1227202152985784322','el-icon-upload','views/upms/cache/index','50','0','0','2020-12-24 19:47:39','2020-12-24 19:47:39','0');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342074603792588801','2020-12-24 19:48:14','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342074455276478465','2020-12-24 19:47:39','1');


insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342089211811028994','会员管理','','user','8000000','el-icon-user-solid','Layout','2','0','0','2020-12-24 20:46:17','2020-12-24 20:46:17','0');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342089211811028994','2020-12-24 20:46:17','1');

DELETE FROM `base_upms`.`sys_menu` WHERE id = 'db95df20df51a46afa7935f38efb7360';
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('db95df20df51a46afa7935f38efb7360','会员信息',NULL,'userInfo','1342089211811028994','el-icon-user','views/mall/userinfo/index','10','0','0','2019-12-10 20:57:03','2020-12-24 20:48:08','0');

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342093905342062593','商品收藏列表','mall:usercollect:index','','1342093572213661698','','','0','0','1','2020-12-24 21:04:56','2020-12-24 21:04:56','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342093572213661698','商品收藏','','goods','1342089211811028994','el-icon-s-goods','views/mall/usercollect/goods','20','0','0','2020-12-24 21:03:37','2020-12-24 21:03:37','0');

insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342093905342062593','2020-12-24 21:04:56','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342093572213661698','2020-12-24 21:03:37','1');


insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342112864309174273','足迹列表','mall:userfootprint:index','','1342112579171999746','','','0','0','1','2020-12-24 22:20:16','2020-12-24 22:20:16','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342112579171999746','会员足迹','','userfootprint','1342089211811028994','el-icon-s-marketing','views/mall/userfootprint/index','40','0','0','2020-12-24 22:19:08','2020-12-24 22:19:08','0');

insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342112864309174273','2020-12-24 22:20:16','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342112579171999746','2020-12-24 22:19:08','1');

CREATE TABLE `base_mall`.`user_footprint` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'PK',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属租户',
  `del_flag` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '逻辑删除标记（0：显示；1：隐藏）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户编号',
  `relation_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关联ID：商品ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ids_tenant_id` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户足迹';

insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342369625242902530','小程序直播删除','wxma:wxmalive:del','','1342368489077252098','','','0','0','1','2020-12-25 15:20:33','2020-12-25 15:20:33','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342369546528399362','小程序直播修改','wxma:wxmalive:edit','','1342368489077252098','','','0','0','1','2020-12-25 15:20:14','2020-12-25 15:20:14','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342369468602425345','小程序直播新增','wxma:wxmalive:add','','1342368489077252098','','','0','0','1','2020-12-25 15:19:56','2020-12-25 15:19:56','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342368718203691010','直播列表','wxma:wxmalive:index','','1342368489077252098','','','0','0','1','2020-12-25 15:16:57','2020-12-25 15:16:57','0');
insert into `base_upms`.`sys_menu` (`id`, `name`, `permission`, `path`, `parent_id`, `icon`, `component`, `sort`, `keep_alive`, `type`, `create_time`, `update_time`, `del_flag`) values('1342368489077252098','直播管理','','wxlive','ae0619ea3e5b86a3e382081680785f7e','el-icon-video-camera-solid','views/wxma/wxlive/index','40','0','0','2020-12-25 15:16:02','2020-12-25 15:16:02','0');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342369625242902530','2020-12-25 15:20:33','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342369546528399362','2020-12-25 15:20:14','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342369468602425345','2020-12-25 15:19:56','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342368718203691010','2020-12-25 15:16:57','1');
insert into `base_upms`.`sys_role_menu` (`role_id`, `menu_id`, `create_time`, `tenant_id`) values('1','1342368489077252098','2020-12-25 15:16:02','1');