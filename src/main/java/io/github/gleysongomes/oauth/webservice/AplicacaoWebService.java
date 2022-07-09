package io.github.gleysongomes.oauth.webservice;

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

import io.github.gleysongomes.oauth.dto.AplicacaoDTO;
import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.AplicacaoInput;
import io.github.gleysongomes.oauth.dto.input.filter.AplicacaoFilter;
import io.github.gleysongomes.oauth.dto.mapping.AplicacaoMapping;
import io.github.gleysongomes.oauth.mapper.AplicacaoMapper;
import io.github.gleysongomes.oauth.model.Aplicacao;
import io.github.gleysongomes.oauth.service.AplicacaoService;
import io.github.gleysongomes.oauth.webservice.authorize.AplicacaoAuthorize;

@RestController
@RequestMapping(path = "/aplicacoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class AplicacaoWebService {

	private final AplicacaoService aplicacaoService;

	private final AplicacaoMapper aplicacaoMapper;

	public AplicacaoWebService(AplicacaoService aplicacaoService, AplicacaoMapper aplicacaoMapper) {
		this.aplicacaoService = aplicacaoService;
		this.aplicacaoMapper = aplicacaoMapper;
	}

	@GetMapping
	@AplicacaoAuthorize.Listar
	public PageDTO<AplicacaoMapping> listar(AplicacaoFilter aplicacaoFilter, @Valid PageDTO<AplicacaoMapping> paginacaoTO) {
		PageDTO<AplicacaoMapping> aplicacoes = aplicacaoService.listar(aplicacaoFilter, paginacaoTO);

		return aplicacoes;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@AplicacaoAuthorize.Adicionar
	public AplicacaoDTO adicionar(@RequestBody @Valid AplicacaoInput gupoInput) {
		Aplicacao aplicacao = aplicacaoMapper.toDomainObject(gupoInput);

		aplicacao = aplicacaoService.adicionar(aplicacao);

		AplicacaoDTO aplicacaoDTO = aplicacaoMapper.toDto(aplicacao);

		return aplicacaoDTO;
	}

	@GetMapping("/{cdAplicacao}")
	@AplicacaoAuthorize.Buscar
	public AplicacaoDTO buscar(@PathVariable Long cdAplicacao) {
		Aplicacao aplicacao = aplicacaoService.buscar(cdAplicacao);

		AplicacaoDTO aplicacaoDTO = aplicacaoMapper.toDto(aplicacao);

		return aplicacaoDTO;
	}

	@PutMapping("/{cdAplicacao}")
	@AplicacaoAuthorize.Atualizar
	public AplicacaoDTO atualizar(@PathVariable Long cdAplicacao, @RequestBody @Valid AplicacaoInput aplicacaoInput) {
		Aplicacao aplicacao = aplicacaoService.buscar(cdAplicacao);

		aplicacaoMapper.copyToDomainObject(aplicacaoInput, aplicacao);

		aplicacao = aplicacaoService.atualizar(aplicacao);

		AplicacaoDTO aplicacaoDTO = aplicacaoMapper.toDto(aplicacao);

		return aplicacaoDTO;
	}

}
