package io.github.gleysongomes.oauth.webservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.gleysongomes.oauth.dto.input.filter.UsuarioFilter;
import io.github.gleysongomes.oauth.dto.mapping.ReportUsuarioMapping;
import io.github.gleysongomes.oauth.service.ReportService;
import io.github.gleysongomes.oauth.webservice.helper.ReportHelper;

@RestController
@RequestMapping("/reports")
public class ReportsWebService {

	private final ReportHelper reportHelper;

	private final ReportService<ReportUsuarioMapping, UsuarioFilter> reportUsuarioService;

	public ReportsWebService(ReportHelper reportHelper,
			@Qualifier("reportUsuarioService") ReportService<ReportUsuarioMapping, UsuarioFilter> reportUsuarioService) {
		this.reportHelper = reportHelper;
		this.reportUsuarioService = reportUsuarioService;
	}

	@GetMapping("/usuarios")
	public void reportUsuarios(UsuarioFilter usuarioFilter) {
		Map<String, Object> params = reportHelper.getDefaultParam(usuarioFilter);
		reportHelper.getReport(reportUsuarioService, params, usuarioFilter);
	}

}
