ALTER TABLE `base_upms`.`sys_log_login` ADD COLUMN `type` CHAR(1) NULL COMMENT '��־���ͣ�0��������9���쳣��' AFTER `tenant_id`; 

ALTER TABLE `base_upms`.`sys_log_login` ADD COLUMN `exception` TEXT NULL COMMENT '�쳣��Ϣ' AFTER `type`; 

UPDATE `base_upms`.`sys_log_login` SET `type` = '0';