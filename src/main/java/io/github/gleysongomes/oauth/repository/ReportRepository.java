package io.github.gleysongomes.oauth.repository;

import java.util.List;

public interface ReportRepository<T, F> {

	public List<T> getAnaliticReport(F filter);

	default List<T> getSinteticReport(F filter) {
		return getAnaliticReport(filter);
	}

}
