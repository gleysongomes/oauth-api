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

import io.github.gleysongomes.oauth.dto.PermissaoGrupoDTO;
import io.github.gleysongomes.oauth.dto.input.PermissaoGrupoInput;
import io.github.gleysongomes.oauth.mapper.PermissaoGrupoMapper;
import io.github.gleysongomes.oauth.model.PermissaoGrupo;
import io.github.gleysongomes.oauth.service.PermissaoGrupoService;

@RestController
@RequestMapping(path = "/permissoes-grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoGrupoWebService {

	private final PermissaoGrupoService permissaoGrupoService;

	private final PermissaoGrupoMapper permissaoGrupoMapper;

	public PermissaoGrupoWebService(PermissaoGrupoService permissaoGrupoService, PermissaoGrupoMapper permissaoGrupoMapper) {
		this.permissaoGrupoService = permissaoGrupoService;
		this.permissaoGrupoMapper = permissaoGrupoMapper;
	}

	@GetMapping("/permissoes/{cdGrupo}")
	public List<PermissaoGrupoDTO> findByCdGrupoAndFlAtivaIsTrue(@PathVariable Long cdGrupo) {
		List<PermissaoGrupo> permissoes = permissaoGrupoService.findByCdGrupoAndFlAtivaIsTrue(cdGrupo);

		List<PermissaoGrupoDTO> permissoesDTOs = permissaoGrupoMapper.toDtos(permissoes);

		return permissoesDTOs;
	}

	@GetMapping("/grupos/{cdPermissao}")
	public List<PermissaoGrupoDTO> findByCdPermissaoAndFlAtivaIsTrue(@PathVariable Long cdPermissao) {
		List<PermissaoGrupo> grupos = permissaoGrupoService.findByCdPermissaoAndFlAtivaIsTrue(cdPermissao);

		List<PermissaoGrupoDTO> gruposDTOs = permissaoGrupoMapper.toDtos(grupos);

		return gruposDTOs;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PermissaoGrupoDTO adicionar(@RequestBody @Valid PermissaoGrupoInput permissaoGrupoInput) {
		PermissaoGrupo permissaoGrupo = permissaoGrupoMapper.toDomainObject(permissaoGrupoInput);

		permissaoGrupo = permissaoGrupoService.adicionar(permissaoGrupo);

		PermissaoGrupoDTO permissaoGrupoDTO = permissaoGrupoMapper.toDto(permissaoGrupo);

		return permissaoGrupoDTO;
	}

	@GetMapping("/{cdPermissao}/{cdGrupo}")
	public PermissaoGrupoDTO buscar(@PathVariable Long cdPermissao, @PathVariable Long cdGrupo) {
		PermissaoGrupo permissaoGrupo = permissaoGrupoService.buscar(cdPermissao, cdGrupo);

		PermissaoGrupoDTO permissaoGrupoDTO = permissaoGrupoMapper.toDto(permissaoGrupo);

		return permissaoGrupoDTO;
	}

	@PutMapping("/{cdPermissao}/{cdGrupo}")
	public PermissaoGrupoDTO atualizar(@PathVariable Long cdPermissao, @PathVariable Long cdGrupo,
			@RequestBody @Valid PermissaoGrupoInput permissaoGrupoInput) {
		permissaoGrupoService.validarAtualizacao(cdPermissao, cdGrupo, permissaoGrupoInput);

		PermissaoGrupo permissaoGrupo = permissaoGrupoService.buscar(cdPermissao, cdGrupo);

		permissaoGrupoMapper.copyToDomainObject(permissaoGrupoInput, permissaoGrupo);

		permissaoGrupo = permissaoGrupoService.atualizar(permissaoGrupo);

		PermissaoGrupoDTO permissaoGrupoDTO = permissaoGrupoMapper.toDto(permissaoGrupo);

		return permissaoGrupoDTO;
	}

}
