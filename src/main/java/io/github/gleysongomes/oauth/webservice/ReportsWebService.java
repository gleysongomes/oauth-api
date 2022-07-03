package io.github.gleysongomes.oauth.webservice;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.gleysongomes.oauth.dto.input.filter.GrupoFilter;
import io.github.gleysongomes.oauth.dto.input.filter.UsuarioFilter;
import io.github.gleysongomes.oauth.dto.mapping.ReportGrupoMapping;
import io.github.gleysongomes.oauth.dto.mapping.ReportUsuarioMapping;
import io.github.gleysongomes.oauth.service.ReportService;
import io.github.gleysongomes.oauth.webservice.authorize.GrupoAuthorize;
import io.github.gleysongomes.oauth.webservice.authorize.UsuarioAuthorize;
import io.github.gleysongomes.oauth.webservice.helper.ReportHelper;

@RestController
@RequestMapping("/reports")
public class ReportsWebService {

	private final ReportHelper reportHelper;

	private final ReportService<ReportUsuarioMapping, UsuarioFilter> reportUsuarioService;

	private final ReportService<ReportGrupoMapping, GrupoFilter> reportGrupoService;

	public ReportsWebService(ReportHelper reportHelper,
			@Qualifier("reportUsuarioService") ReportService<ReportUsuarioMapping, UsuarioFilter> reportUsuarioService,
			@Qualifier("reportGrupoService") ReportService<ReportGrupoMapping, GrupoFilter> reportGrupoService) {
		this.reportHelper = reportHelper;
		this.reportUsuarioService = reportUsuarioService;
		this.reportGrupoService = reportGrupoService;
	}

	@GetMapping("/usuarios")
	@UsuarioAuthorize.Relatorio
	public void reportUsuarios(UsuarioFilter usuarioFilter) {
		Map<String, Object> params = reportHelper.getDefaultParam(usuarioFilter);
		reportHelper.getReport(reportUsuarioService, params, usuarioFilter);
	}

	@GetMapping("/grupos")
	@GrupoAuthorize.Relatorio
	public void reportGrupos(GrupoFilter grupoFilter) {
		Map<String, Object> params = reportHelper.getDefaultParam(grupoFilter);
		reportHelper.getReport(reportGrupoService, params, grupoFilter);
	}

}
