/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.20 : Database - base_config
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`base_config` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `base_config`;

/*Table structure for table `config_info` */

DROP TABLE IF EXISTS `config_info`;

CREATE TABLE `config_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `c_use` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `effect` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `c_schema` text COLLATE utf8_bin,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info';

/*Data for the table `config_info` */

insert  into `config_info`(`id`,`data_id`,`group_id`,`content`,`md5`,`gmt_create`,`gmt_modified`,`src_user`,`src_ip`,`app_name`,`tenant_id`,`c_desc`,`c_use`,`effect`,`type`,`c_schema`) values 
(1,'dynamic_routes','DEFAULT_GROUP','routes:\n# base-auth\n- id: base-auth\n  predicates:\n  - name: Path\n    args: \n      _genkey_0: /auth/**\n  filters:\n  - name: ValidateCodeGatewayFilter\n    args: {}\n  - name: PasswordDecoderFilter\n    args: {}\n  uri: lb://base-auth\n  order: 0\n# base-upms-admin\n- id: base-upms-admin\n  predicates:\n  - name: Path\n    args: \n      _genkey_0: /upms/**\n  filters:\n  - name: RequestRateLimiter\n    args: \n      # 限流策略\n      key-resolver: \'#{@remoteAddrKeyResolver}\'\n      # 令牌桶每秒填充率\n      redis-rate-limiter.burstCapacity: 20\n      # 令牌桶容量\n      redis-rate-limiter.replenishRate: 20\n  uri: lb://base-upms-admin\n  order: 0\n# base-codegen\n- id: base-codegen\n  predicates:\n  - name: Path\n    args: \n      _genkey_0: /gen/**\n  filters: []\n  uri: lb://base-codegen\n  order: 0\n# base-weixin-admin\n- id: base-weixin-admin\n  predicates:\n  - name: Path\n    args: \n      _genkey_0: /weixin/**\n  filters: []\n  uri: lb://base-weixin-admin\n  order: 0\n# base-mall-admin\n- id: base-mall-admin\n  predicates:\n  - name: Path\n    args: \n      _genkey_0: /mall/**\n  filters: []\n  uri: lb://base-mall-admin\n  order: 0\n # 其他项目的配置文件，在JooLun项目中没有用到，可以自行删掉\n- id: base-gitea-admin\n  predicates:\n  - name: Path\n    args: \n      _genkey_0: /gitea/**\n  filters: []\n  uri: lb://base-gitea-admin\n  order: 0 ','f945b44da6467d4ec4431d4d5f965583','2019-07-30 14:26:08','2021-02-24 21:25:45',NULL,'127.0.0.1','','','动态路由配置','null','null','yaml','null'),
(2,'application-dev.yml','DEFAULT_GROUP','# 加解密根密码\njasypt:\n  encryptor:\n    #根密码，改完密码要把base_upms.sys_datasource数据库表清空，否则代码生成器无法启动\n    password: joolun\nspring:\n  servlet:\n    multipart:\n      location: /home\n  # redis 相关\n  redis:\n    host: base-redis\n    port: 6379\n    password: \n    database: 1\n# logging日志\nlogging:\n  level:\n    com.alibaba.nacos.client.naming: error\n# 暴露监控端点\nmanagement:\n  endpoints:\n    web:\n      exposure:\n        include: \'*\'\n# feign 配置\nfeign:\n  sentinel:\n    enabled: true\n  okhttp:\n    enabled: true\n  httpclient:\n    enabled: false\n  client:\n    config:\n      default:\n        connectTimeout: 10000\n        readTimeout: 10000\n  compression:\n    request:\n      enabled: true\n    response:\n      enabled: true\n#请求处理的超时时间\nribbon:\n  ReadTimeout: 10000\n  ConnectTimeout: 10000\n# mybaits-plus配置\nmybatis-plus:\n  # MyBatis Mapper所对应的XML文件位置\n  mapper-locations: classpath:/mapper/*Mapper.xml\n  # 自定义TypeHandler\n  type-handlers-package: com.joolun.cloud.common.data.mybatis.typehandler\n  global-config:\n    sql-parser-cache: true\n    # 关闭MP3.0自带的banner\n    banner: false\n    db-config:\n      # 主键类型\n      id-type: auto\n#swagger公共信息\nswagger:\n  title: JooLun API\n  description: JooLun\n  license: Powered By joolun\n  licenseUrl: http://www.joolun.com/\n  terms-of-service-url: http://www.joolun.com/\n  authorization:\n    name: OAuth\n    auth-regex: ^.*$\n    authorization-scope-list:\n      - scope: server\n        description: server all\n    token-url-list:\n      - http://base-gateway:9999/auth/oauth/token\n## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      # 无需token访问的url,如果子模块重写这里的配置就会被覆盖\n      release-urls:\n        - /actuator/**\n        - /v2/api-docs\n    resource:\n      loadBalanced: true\n      token-info-uri: http://base-auth/oauth/check_token\n## 文件存放目录配置（用来存放微信支付证书）\nhome-dir:\n  windows: C:/joolun-file/\n  linux: /mnt/install/joolun-file/\n','d2129f036de749b7f435ef67c5530df2','2019-07-28 23:14:26','2021-02-24 21:34:25',NULL,'127.0.0.1','','','主配置文件','null','null','yaml','null'),
(3,'base-gateway-dev.yml','DEFAULT_GROUP','security:\n  encode:\n    # 前端密码密钥，必须16位，和joolun-ui\\src\\config\\env.js中的securityKey相对应\n    key: \'1234567891234567\'\n# 不校验验证码终端\nignore:\n  clients:\n    - swagger\n  swagger-providers:\n    - base-auth\r\n','830fdc56e25794e2600532098c13a902','2019-07-28 23:14:26','2020-04-20 10:47:10',NULL,'127.0.0.1','','','网关配置','null','null','yaml','null'),
(4,'base-auth-dev.yml','DEFAULT_GROUP','# 数据源\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: root\n      password: root\n      url: jdbc:mysql://base-mysql:3306/base_upms?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n  freemarker:\n    allow-request-override: false\n    allow-session-override: false\n    cache: true\n    charset: UTF-8\n    check-template-location: true\n    content-type: text/html\n    enabled: true\n    expose-request-attributes: false\n    expose-session-attributes: false\n    expose-spring-macro-helpers: true\n    prefer-file-system-access: true\n    suffix: .ftl\n    template-loader-path: classpath:/templates/\r\n','3476b23886681325f3b3ca7ac6a800fc','2019-07-28 23:14:26','2019-11-21 14:54:23',NULL,'192.168.1.13','','','认证授权配置','null','null','yaml','null'),
(5,'base-upms-admin-dev.yml','DEFAULT_GROUP','## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: admin\n      client-secret: admin\n      scope: server\n      # 无需token访问的url\n      release-urls:\n        - /actuator/**\n        - /v2/api-docs\n        - /user/register\n        - /druid/**\n# 数据源\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: root\n      password: root\n      url: jdbc:mysql://base-mysql:3306/base_upms?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\n      web-stat-filter: \n        enabled: true\n      stat-view-servlet:\n        enabled: true\n        # 设置白名单，不填则允许所有访问\n        allow: \n        url-pattern: /druid/*\n        # 控制台管理用户名和密码\n        login-username: admin\n        login-password: 123456\n      filter:\n        stat:\n            enabled: true\n            # 慢SQL记录\n            log-slow-sql: true\n            slow-sql-millis: 1000\n            merge-sql: true\n        wall:\n            config:\n                multi-statement-allow: true\n# Logger Config sql日志\nlogging:\n  level:\n    com.joolun.cloud.upms.admin.mapper: debug\nbase:\n  # 租户表维护\n  tenant:\n    column: tenant_id\n    tables:\n      - sys_user\n      - sys_role\n      - sys_organ\n      - sys_log\n      - sys_log_login\n      - sys_config_storage\n      - sys_config_editor\n      - sys_organ_relation\n      - sys_role_menu\n      - sys_user_role\n  #数据权限配置\n  datascope:\n    mapperIds:\n      - SysUserMapper.getUserVosPage        \n#邮箱配置\nemail:\n  mailSmtpHost: smtpdm.aliyun.com\n  mailSmtpUsername: xxxxxxxxx\n  mailSmtpPassword: xxxxxxxxxxx\n  siteName: JooLun\n#阿里短信配置\nsms:\n  regionId: cn-hangzhou\n  accessKeyId: xxxxxxxxx\n  accessKeySecret: xxxxxxxxxxxxxxxxxx\n  #模板\n  templates:\n    #登录模板\n    signName1: JooLun商城\n    templateCode1: SMS_183247308\n    #绑定模板\n    signName2: JooLun商城\n    templateCode2: SMS_183247308\n    #解绑模板\n    signName3: JooLun商城\n    templateCode3: SMS_183247308\n','fef6e9fb081639c12f3bd486baddd616','2019-07-28 23:14:26','2020-12-01 14:07:00',NULL,'127.0.0.1','','','用户权限管理配置','null','null','yaml','null'),
(6,'base-codegen-dev.yml','DEFAULT_GROUP','## spring security 配置\nsecurity:\n  oauth2:\n    client:\n      client-id: gen\n      client-secret: gen\n      scope: server\r\n      # 无需token访问的url\r\n      release-urls:\r\n        - /actuator/**\r\n        - /v2/api-docs\r\n        - /druid/**\n# 数据源配置\nspring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    druid:\n      driver-class-name: com.mysql.cj.jdbc.Driver\n      username: root\n      password: root\n      url: jdbc:mysql://base-mysql:3306/base_upms?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n      web-stat-filter: \r\n        enabled: true\r\n      stat-view-servlet:\r\n        enabled: true\r\n        # 设置白名单，不填则允许所有访问\r\n        allow: \r\n        url-pattern: /druid/*\r\n        # 控制台管理用户名和密码\r\n        login-username: admin\r\n        login-password: 123456\r\n      filter:\r\n        stat:\r\n            enabled: true\r\n            # 慢SQL记录\r\n            log-slow-sql: true\r\n            slow-sql-millis: 1000\r\n            merge-sql: true\r\n        wall:\r\n            config:\r\n                multi-statement-allow: true\n  resources:\n    static-locations: classpath:/static/,classpath:/views/\n# Logger config sql日志\nlogging:\n  level:\n    com.joolun.cloud.codegen.mapper: debug\nbase:\n  tenant:\n    column: tenant_id\n    tables:\n      - sys_datasource\r\n','eb45d63629e996a1ecab6f60c7c53e66','2019-07-28 23:14:26','2020-04-20 10:48:09',NULL,'127.0.0.1','','','代码生成配置','null','null','yaml','null'),
(7,'base-weixin-admin-dev.yml','DEFAULT_GROUP','## spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: weixin\r\n      client-secret: weixin\r\n      scope: server\r\n      # 无需token访问的url\r\n      release-urls:\r\n        - /actuator/**\r\n        - /v2/api-docs\r\n        - /portal/**\r\n        - /ws/**\r\n        - /open/notify/**\r\n        - /open/auth/**\r\n        - /api/**\r\n# 数据源配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    druid:\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      username: root\r\n      password: root\r\n      url: jdbc:mysql://base-mysql:3306/base_wx?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n  resources:\r\n    static-locations: classpath:/static/,classpath:/views/\r\n# Logger Config sql日志\r\nlogging:\r\n  level:\r\n    com.joolun.cloud.weixin.admin.mapper: debug    \r\n# 租户表维护\r\nbase:\r\n  tenant:\r\n    column: tenant_id\r\n    tables:\r\n      - wx_app\r\n      - wx_menu\r\n      - wx_menu_rule\r\n      - wx_user\r\n      - wx_auto_reply\r\n      - wx_msg\r\n      - wx_mass_msg\r\n      - wx_template_msg\r\n  #数据权限配置\r\n  datascope:\r\n    mapperIds:\r\n      - WxAppMapper.selectPage\r\n      - WxAppMapper.selectList\r\n# 微信第三方平台配置，请自行申请(https://open.weixin.qq.com/)\r\nwx:\r\n  component:\r\n    appId: your-appId\r\n    appSecret: your-appSecret\r\n    token: your-token\r\n    aesKey: your-aesKey\r\n','c03974fa6490ecb3c21128fd99462ede','2019-07-28 23:14:26','2020-04-20 10:48:28',NULL,'127.0.0.1','','','微信公众号配置','null','null','yaml','null'),
(8,'base-mall-admin-dev.yml','DEFAULT_GROUP','## spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: weixin\r\n      client-secret: weixin\r\n      scope: server\r\n      # 无需token访问的url\r\n      release-urls:\r\n        - /actuator/**\r\n        - /v2/api-docs\r\n        - /api/**\r\n# 数据源配置\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    druid:\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      username: root\r\n      password: root\r\n      url: jdbc:mysql://base-mysql:3306/base_mall?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n  resources:\r\n    static-locations: classpath:/static/,classpath:/views/\r\n# Logger Config sql日志\r\nlogging:\r\n  level:\r\n    com.joolun.cloud.mall.admin.mapper: debug    \r\n# 租户表维护\r\nbase:\r\n  tenant:\r\n    column: tenant_id\r\n    tables:\r\n      - goods_category\r\n      - goods_spu\r\n      - goods_spu_spec\r\n      - goods_sku\r\n      - goods_sku_spec_value\r\n      - goods_spec\r\n      - goods_spec_value\r\n      - goods_appraises\r\n      - shopping_cart\r\n      - order_info\r\n      - order_item\r\n      - order_logistics\r\n      - order_logistics_detail\r\n      - user_address\r\n      - user_collect\r\n      - material\r\n      - material_group\r\n      - notice\r\n      - notice_item\r\n      - order_refunds\r\n      - user_info\r\n      - points_record\r\n      - points_config\r\n      - coupon_info\r\n      - coupon_goods\r\n      - coupon_user\r\n      - freight_templat\r\n      - bargain_info\r\n      - bargain_user\r\n      - bargain_cut\r\n      - delivery_place\r\n      - ensure\r\n      - ensure_goods\r\n      - groupon_info\r\n      - groupon_user\r\n      - seckill_hall\r\n      - seckill_hall_info\r\n      - seckill_info\r\n      - article_category\r\n      - article_info\r\n      - user_footprint\r\n  #商城相关配置\r\n  mall:\r\n    #支付、物流回调地址域名，注：快递100不支持https回调\r\n    notify-host: http://test.joolun.com\r\n    #快递100授权key\r\n    logistics-key: xxxxxxxxxxx\r\nxxl:\r\n  job:\r\n    # 开关\r\n    enabled: false\r\n    admin:\r\n      # xxl_job后台访问地址\r\n      addresses: http://127.0.0.1:8080/xxl-job-admin\r\n    executor:\r\n      appname: base-mall-admin','f601603f59d9f86485781b029f702ad3','2019-08-12 12:03:16','2020-12-03 15:50:29',NULL,'127.0.0.1','','','商城管理配置','null','null','yaml','null'),
(30,'base-gitea-admin-dev.yml','DEFAULT_GROUP','## spring security 配置\r\nsecurity:\r\n  oauth2:\r\n    client:\r\n      client-id: admin\r\n      client-secret: admin\r\n      scope: server\r\n      # 无需token访问的url\r\n      release-urls:\r\n        - /actuator/**\r\n        - /v2/api-docs\r\n        - /userinfo/query\r\n# 数据源\r\nspring:\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    druid:\r\n      driver-class-name: com.mysql.cj.jdbc.Driver\r\n      username: root\r\n      password: root\r\n      url: jdbc:mysql://base-mysql:3306/base_gitea?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true\r\n  resources:\r\n    static-locations: classpath:/static/,classpath:/views/\r\n# Logger Config sql日志\r\nlogging:\r\n  level:\r\n    com.joolun.cloud.gitea.admin.mapper: debug\r\n#邮箱配置\r\nemail:\r\n  mailSmtpHost: smtpdm.aliyun.com\r\n  mailSmtpUsername: xxxxxxxxxxxxx\r\n  mailSmtpPassword: xxxxxxxxxxxxxxx\r\n  siteName: JooLun \r\nxxl:\r\n  job:\r\n    # 开关\r\n    enabled: false\r\n    admin:\r\n      # xxl_job后台访问地址\r\n      addresses: http://127.0.0.1:8080/xxl-job-admin\r\n    executor:\r\n      appname: base-gitea-admin','d4356befc25de4a302016391df909e1a','2020-12-12 14:14:13','2020-12-17 17:16:53',NULL,'127.0.0.1','','','其他项目的配置文件，在JooLun项目中没有用到，可以自行删掉','','','yaml',''),
(49,'sentinal-joolun','SENTINEL_GROUP','[\r\n    {\r\n        \"resource\": \"base-auth\",\r\n        \"count\": 500,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    },{\r\n        \"resource\": \"base-upms-admin\",\r\n        \"count\": 1000,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    },{\r\n        \"resource\": \"base-weixin-admin\",\r\n        \"count\": 1000,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    },{\r\n        \"resource\": \"base-mall-admin\",\r\n        \"count\": 1000,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    }\r\n]','73d9b2fd4e08e2b55704ac012c5dc3f6','2021-02-24 21:37:10','2021-02-24 21:37:10',NULL,'127.0.0.1','','','sentinal流控规则',NULL,NULL,'json',NULL);

/*Table structure for table `config_info_aggr` */

DROP TABLE IF EXISTS `config_info_aggr`;

CREATE TABLE `config_info_aggr` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='增加租户字段';

/*Data for the table `config_info_aggr` */

/*Table structure for table `config_info_beta` */

DROP TABLE IF EXISTS `config_info_beta`;

CREATE TABLE `config_info_beta` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_beta';

/*Data for the table `config_info_beta` */

/*Table structure for table `config_info_tag` */

DROP TABLE IF EXISTS `config_info_tag`;

CREATE TABLE `config_info_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_tag';

/*Data for the table `config_info_tag` */

/*Table structure for table `config_tags_relation` */

DROP TABLE IF EXISTS `config_tags_relation`;

CREATE TABLE `config_tags_relation` (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_tag_relation';

/*Data for the table `config_tags_relation` */

/*Table structure for table `group_capacity` */

DROP TABLE IF EXISTS `group_capacity`;

CREATE TABLE `group_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='集群、各Group容量信息表';

/*Data for the table `group_capacity` */

/*Table structure for table `his_config_info` */

DROP TABLE IF EXISTS `his_config_info`;

CREATE TABLE `his_config_info` (
  `id` bigint unsigned NOT NULL,
  `nid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text COLLATE utf8_bin,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `op_type` char(10) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='多租户改造';

/*Data for the table `his_config_info` */

/*Table structure for table `permissions` */

DROP TABLE IF EXISTS `permissions`;

CREATE TABLE `permissions` (
  `role` varchar(50) NOT NULL,
  `resource` varchar(512) NOT NULL,
  `action` varchar(8) NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `permissions` */

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `username` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `roles` */

insert  into `roles`(`username`,`role`) values 
('nacos','ROLE_ADMIN');

/*Table structure for table `tenant_capacity` */

DROP TABLE IF EXISTS `tenant_capacity`;

CREATE TABLE `tenant_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户容量信息表';

/*Data for the table `tenant_capacity` */

/*Table structure for table `tenant_info` */

DROP TABLE IF EXISTS `tenant_info`;

CREATE TABLE `tenant_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tenant_info';

/*Data for the table `tenant_info` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(500) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `users` */

insert  into `users`(`username`,`password`,`enabled`) values 
('nacos','$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
