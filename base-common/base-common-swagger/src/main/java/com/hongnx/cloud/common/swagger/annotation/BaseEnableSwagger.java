package com.hongnx.cloud.common.swagger.annotation;

import com.hongnx.cloud.common.swagger.config.SwaggerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启 swagger
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerConfiguration.class})
public @interface BaseEnableSwagger {
}
