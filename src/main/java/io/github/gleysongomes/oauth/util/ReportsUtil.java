package io.github.gleysongomes.oauth.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportsUtil {

	// @formatter:off
	DATA_SOURCE("datasource"),
	SUB_DATA_SOURCE("subdatasource"),
	REPORTS_DIR("/reports/"),
	IMAGES_DIR("/images/"),
	JASPER_EXTENSAO(".jasper"),
	JRXML_EXTENSAO(".jrxml");
	// @formatter:on

	private String value;
}
