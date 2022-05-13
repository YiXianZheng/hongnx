/*如果执行此脚本报错，请看解决方法：https://git.joolun.com/joolun-mp-ma/mp-ma/issues/298 */
ALTER TABLE `base_wx`.`wx_user` ADD UNIQUE INDEX `uk_appid_openid` (`app_id`, `open_id`);