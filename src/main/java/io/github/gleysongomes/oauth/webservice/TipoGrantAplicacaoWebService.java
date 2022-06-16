package io.github.gleysongomes.oauth.webservice;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.gleysongomes.oauth.dto.TipoGrantAplicacaoDTO;
import io.github.gleysongomes.oauth.dto.input.TipoGrantAplicacaoInput;
import io.github.gleysongomes.oauth.mapper.TipoGrantAplicacaoMapper;
import io.github.gleysongomes.oauth.model.TipoGrantAplicacao;
import io.github.gleysongomes.oauth.service.TipoGrantAplicacaoService;

@RestController
@RequestMapping(path = "/tipos-grants-aplicacoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class TipoGrantAplicacaoWebService {

	private final TipoGrantAplicacaoService tipoGrantAplicacaoService;

	private final TipoGrantAplicacaoMapper tipoGrantAplicacaoMapper;

	public TipoGrantAplicacaoWebService(TipoGrantAplicacaoService tipoGrantAplicacaoService,
			TipoGrantAplicacaoMapper tipoGrantAplicacaoMapper) {
		this.tipoGrantAplicacaoService = tipoGrantAplicacaoService;
		this.tipoGrantAplicacaoMapper = tipoGrantAplicacaoMapper;
	}

	@GetMapping("/tipos-grants/{cdAplicacao}")
	public List<TipoGrantAplicacaoDTO> findByCdAplicacaoAndFlAtivoIsTrue(@PathVariable Long cdAplicacao) {
		List<TipoGrantAplicacao> tiposGrantsAplicacoes = tipoGrantAplicacaoService
				.findByCdAplicacaoAndFlAtivoIsTrue(cdAplicacao);

		List<TipoGrantAplicacaoDTO> tiposGrantsAplicacoesDTOs = tipoGrantAplicacaoMapper.toDtos(tiposGrantsAplicacoes);

		return tiposGrantsAplicacoesDTOs;
	}

	@GetMapping("/aplicacoes/{cdTipoGrant}")
	public List<TipoGrantAplicacaoDTO> findByCdTipoGrantAndFlAtivoIsTrue(@PathVariable Long cdTipoGrant) {
		List<TipoGrantAplicacao> tiposGrantsAplicacoes = tipoGrantAplicacaoService
				.findByCdTipoGrantAndFlAtivoIsTrue(cdTipoGrant);

		List<TipoGrantAplicacaoDTO> tiposGrantsAplicacoesDTOs = tipoGrantAplicacaoMapper.toDtos(tiposGrantsAplicacoes);

		return tiposGrantsAplicacoesDTOs;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TipoGrantAplicacaoDTO adicionar(@RequestBody @Valid TipoGrantAplicacaoInput tipoGrantAplicacaoInput) {
		TipoGrantAplicacao tipoGrantAplicacao = tipoGrantAplicacaoMapper.toDomainObject(tipoGrantAplicacaoInput);

		tipoGrantAplicacao = tipoGrantAplicacaoService.adicionar(tipoGrantAplicacao);

		TipoGrantAplicacaoDTO tipoGrantAplicacaoDTO = tipoGrantAplicacaoMapper.toDto(tipoGrantAplicacao);

		return tipoGrantAplicacaoDTO;
	}

	@GetMapping("/{cdTipoGrant}/{cdAplicacao}")
	public TipoGrantAplicacaoDTO buscar(@PathVariable Long cdTipoGrant, @PathVariable Long cdAplicacao) {
		TipoGrantAplicacao tipoGrantAplicacao = tipoGrantAplicacaoService.buscar(cdTipoGrant, cdAplicacao);

		TipoGrantAplicacaoDTO tipoGrantAplicacaoDTO = tipoGrantAplicacaoMapper.toDto(tipoGrantAplicacao);

		return tipoGrantAplicacaoDTO;
	}

	@PutMapping("/{cdTipoGrant}/{cdAplicacao}")
	public TipoGrantAplicacaoDTO atualizar(@PathVariable Long cdTipoGrant, @PathVariable Long cdAplicacao,
			@RequestBody @Valid TipoGrantAplicacaoInput tipoGrantAplicacaoInput) {
		tipoGrantAplicacaoService.validarAtualizacao(cdTipoGrant, cdAplicacao, tipoGrantAplicacaoInput);

		TipoGrantAplicacao tipoGrantAplicacao = tipoGrantAplicacaoService.buscar(cdTipoGrant, cdAplicacao);

		tipoGrantAplicacaoMapper.copyToDomainObject(tipoGrantAplicacaoInput, tipoGrantAplicacao);

		tipoGrantAplicacao = tipoGrantAplicacaoService.atualizar(tipoGrantAplicacao);

		TipoGrantAplicacaoDTO tipoGrantAplicacaoDTO = tipoGrantAplicacaoMapper.toDto(tipoGrantAplicacao);

		return tipoGrantAplicacaoDTO;
	}

}
