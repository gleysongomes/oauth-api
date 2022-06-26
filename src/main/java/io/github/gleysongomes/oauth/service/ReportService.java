package io.github.gleysongomes.oauth.service;

import java.util.List;

public interface ReportService<T, F> {

	List<T> getSinteticReportData(F filter);

	List<T> getAnalitcReportData(F filter);

	default List<T> getReportData(F filter, String tipoRelatorio) {
		if ("analitico".equalsIgnoreCase(tipoRelatorio)) {
			return getAnalitcReportData(filter);

		} else if ("sintetico".equalsIgnoreCase(tipoRelatorio)) {
			return getSinteticReportData(filter);
		}

		return null;

	}

	String getReportFileName(F filter);
}
