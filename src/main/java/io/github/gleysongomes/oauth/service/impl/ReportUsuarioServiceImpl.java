package io.github.gleysongomes.oauth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import io.github.gleysongomes.oauth.dto.input.filter.UsuarioFilter;
import io.github.gleysongomes.oauth.dto.mapping.ReportUsuarioMapping;
import io.github.gleysongomes.oauth.repository.ReportRepository;
import io.github.gleysongomes.oauth.service.ReportService;

@Service("reportUsuarioService")
public class ReportUsuarioServiceImpl implements ReportService<ReportUsuarioMapping, UsuarioFilter> {

	private String reportName = "report_usuario_analitico";

	private final ReportRepository<ReportUsuarioMapping, UsuarioFilter> repository;

	public ReportUsuarioServiceImpl(
			@Qualifier("reportUsuarioRepository") ReportRepository<ReportUsuarioMapping, UsuarioFilter> repository) {
		this.repository = repository;
	}

	@Override
	public List<ReportUsuarioMapping> getSinteticReportData(UsuarioFilter usuarioFilter) {
		return null;
	}

	@Override
	public List<ReportUsuarioMapping> getAnalitcReportData(UsuarioFilter usuarioFilter) {
		return repository.getAnaliticReport(usuarioFilter);
	}

	@Override
	public String getReportFileName(UsuarioFilter usuarioFilter) {
		return reportName;
	}

}
