package io.github.gleysongomes.oauth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.github.gleysongomes.oauth.dto.input.filter.GrupoFilter;
import io.github.gleysongomes.oauth.dto.mapping.ReportGrupoMapping;
import io.github.gleysongomes.oauth.repository.ReportRepository;
import io.github.gleysongomes.oauth.service.ReportService;

@Service("reportGrupoService")
public class ReportGrupoServiceImpl implements ReportService<ReportGrupoMapping, GrupoFilter> {

	private String reportName = "report_grupo_analitico";

	private final ReportRepository<ReportGrupoMapping, GrupoFilter> repository;

	public ReportGrupoServiceImpl(
			@Qualifier("reportGrupoRepository") ReportRepository<ReportGrupoMapping, GrupoFilter> repository) {
		this.repository = repository;
	}

	@Override
	public List<ReportGrupoMapping> getSinteticReportData(GrupoFilter grupoFilter) {
		return null;
	}

	@Override
	public List<ReportGrupoMapping> getAnalitcReportData(GrupoFilter grupoFilter) {
		return repository.getAnaliticReport(grupoFilter);
	}

	@Override
	public String getReportFileName(GrupoFilter grupoFilter) {
		return reportName;
	}

}
