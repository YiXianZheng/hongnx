package com.hongnx.cloud.common.security.component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hongnx.cloud.common.security.exception.BaseAuth2Exception;
import com.hongnx.cloud.common.core.constant.CommonConstants;
import lombok.SneakyThrows;

/**
 * @author
 * <p>
 * OAuth2 异常格式化
 */
public class BaseAuth2ExceptionSerializer extends StdSerializer<BaseAuth2Exception> {
	public BaseAuth2ExceptionSerializer() {
		super(BaseAuth2Exception.class);
	}

	@Override
	@SneakyThrows
	public void serialize(BaseAuth2Exception value, JsonGenerator gen, SerializerProvider provider) {
		gen.writeStartObject();
		gen.writeObjectField("code", CommonConstants.FAIL);
		gen.writeStringField("msg", value.getMessage());
		gen.writeStringField("data", value.getErrorCode());
		gen.writeEndObject();
	}
}
