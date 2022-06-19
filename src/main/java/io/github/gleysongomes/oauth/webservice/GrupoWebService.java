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

import io.github.gleysongomes.oauth.dto.GrupoDTO;
import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.GrupoInput;
import io.github.gleysongomes.oauth.dto.input.filter.GrupoFilter;
import io.github.gleysongomes.oauth.dto.mapping.GrupoMapping;
import io.github.gleysongomes.oauth.mapper.GrupoMapper;
import io.github.gleysongomes.oauth.model.Grupo;
import io.github.gleysongomes.oauth.service.GrupoService;
import io.github.gleysongomes.oauth.webservice.authorize.GrupoAuthorize;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoWebService {

	private final GrupoService grupoService;

	private final GrupoMapper grupoMapper;

	public GrupoWebService(GrupoService grupoService, GrupoMapper grupoMapper) {
		this.grupoService = grupoService;
		this.grupoMapper = grupoMapper;
	}

	@GetMapping
	@GrupoAuthorize.Listar
	public PageDTO<GrupoMapping> listar(GrupoFilter grupoFilter, @Valid PageDTO<GrupoMapping> pageDTO) {
		PageDTO<GrupoMapping> grupos = grupoService.listar(grupoFilter, pageDTO);

		return grupos;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@GrupoAuthorize.Adicionar
	public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = grupoMapper.toDomainObject(grupoInput);

		grupo = grupoService.adicionar(grupo);

		GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

		return grupoDTO;
	}

	@GetMapping("/{cdGrupo}")
	@GrupoAuthorize.Buscar
	public GrupoDTO buscar(@PathVariable Long cdGrupo) {
		Grupo grupo = grupoService.buscar(cdGrupo);

		GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

		return grupoDTO;
	}

	@PutMapping("/{cdGrupo}")
	@GrupoAuthorize.Atualizar
	public GrupoDTO atualizar(@PathVariable Long cdGrupo, @RequestBody @Valid GrupoInput grupoInput) {
		Grupo grupo = grupoService.buscar(cdGrupo);

		grupoMapper.copyToDomainObject(grupoInput, grupo);

		grupo = grupoService.atualizar(grupo);

		GrupoDTO grupoDTO = grupoMapper.toDto(grupo);

		return grupoDTO;
	}

}
