package io.github.gleysongomes.oauth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.github.gleysongomes.oauth.dto.input.filter.PermissaoFilter;
import io.github.gleysongomes.oauth.dto.mapping.ReportPermissaoMapping;
import io.github.gleysongomes.oauth.repository.ReportRepository;
import io.github.gleysongomes.oauth.service.ReportService;

@Service("reportPermissaoService")
public class ReportPermissaoServiceImpl implements ReportService<ReportPermissaoMapping, PermissaoFilter> {

	private String reportName = "report_permissao_analitico";

	private final ReportRepository<ReportPermissaoMapping, PermissaoFilter> repository;

	public ReportPermissaoServiceImpl(
			@Qualifier("reportPermissaoRepository") ReportRepository<ReportPermissaoMapping, PermissaoFilter> repository) {
		this.repository = repository;
	}

	@Override
	public List<ReportPermissaoMapping> getSinteticReportData(PermissaoFilter permissaoFilter) {
		return null;
	}

	@Override
	public List<ReportPermissaoMapping> getAnalitcReportData(PermissaoFilter permissaoFilter) {
		return repository.getAnaliticReport(permissaoFilter);
	}

	@Override
	public String getReportFileName(PermissaoFilter permissaoFilter) {
		return reportName;
	}

}
