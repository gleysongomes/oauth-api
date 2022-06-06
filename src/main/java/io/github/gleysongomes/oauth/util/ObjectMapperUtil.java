package io.github.gleysongomes.oauth.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class ObjectMapperUtil {

	public static String writeValueAsString(Object value) {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
