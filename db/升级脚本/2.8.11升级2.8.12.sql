ALTER TABLE `base_upms`.`sys_log_login` ADD COLUMN `type` CHAR(1) NULL COMMENT '日志类型（0：正常；9：异常）' AFTER `tenant_id`; 

ALTER TABLE `base_upms`.`sys_log_login` ADD COLUMN `exception` TEXT NULL COMMENT '异常信息' AFTER `type`; 

UPDATE `base_upms`.`sys_log_login` SET `type` = '0';