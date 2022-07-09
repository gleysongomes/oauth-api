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

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.PermissaoDTO;
import io.github.gleysongomes.oauth.dto.input.PermissaoInput;
import io.github.gleysongomes.oauth.dto.input.filter.PermissaoFilter;
import io.github.gleysongomes.oauth.dto.mapping.PermissaoMapping;
import io.github.gleysongomes.oauth.mapper.PermissaoMapper;
import io.github.gleysongomes.oauth.model.Permissao;
import io.github.gleysongomes.oauth.service.PermissaoService;
import io.github.gleysongomes.oauth.webservice.authorize.PermissaoAuthorize;

@RestController
@RequestMapping(path = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoWebService {

	private final PermissaoService permissaoService;

	private final PermissaoMapper permissaoMapper;

	public PermissaoWebService(PermissaoService permissaoService, PermissaoMapper permissaoMapper) {
		this.permissaoService = permissaoService;
		this.permissaoMapper = permissaoMapper;
	}

	@GetMapping
	@PermissaoAuthorize.Listar
	public PageDTO<PermissaoMapping> listar(PermissaoFilter permissaoFilter, @Valid PageDTO<PermissaoMapping> pageDTO) {
		PageDTO<PermissaoMapping> permissoes = permissaoService.listar(permissaoFilter, pageDTO);

		return permissoes;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PermissaoAuthorize.Adicionar
	public PermissaoDTO adicionar(@RequestBody @Valid PermissaoInput gupoInput) {
		Permissao permissao = permissaoMapper.toDomainObject(gupoInput);

		permissao = permissaoService.adicionar(permissao);

		PermissaoDTO permissaoDTO = permissaoMapper.toDto(permissao);

		return permissaoDTO;
	}

	@GetMapping("/{cdPermissao}")
	@PermissaoAuthorize.Buscar
	public PermissaoDTO buscar(@PathVariable Long cdPermissao) {
		Permissao permissao = permissaoService.buscar(cdPermissao);

		PermissaoDTO permissaoDTO = permissaoMapper.toDto(permissao);

		return permissaoDTO;
	}

	@PutMapping("/{cdPermissao}")
	@PermissaoAuthorize.Atualizar
	public PermissaoDTO atualizar(@PathVariable Long cdPermissao, @RequestBody @Valid PermissaoInput permissaoInput) {
		Permissao permissao = permissaoService.buscar(cdPermissao);

		permissaoMapper.copyToDomainObject(permissaoInput, permissao);

		permissao = permissaoService.atualizar(permissao);

		PermissaoDTO permissaoDTO = permissaoMapper.toDto(permissao);

		return permissaoDTO;
	}

}
