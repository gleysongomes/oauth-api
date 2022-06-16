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

import io.github.gleysongomes.oauth.dto.GrupoUsuarioDTO;
import io.github.gleysongomes.oauth.dto.input.GrupoUsuarioInput;
import io.github.gleysongomes.oauth.mapper.GrupoUsuarioMapper;
import io.github.gleysongomes.oauth.model.GrupoUsuario;
import io.github.gleysongomes.oauth.service.GrupoUsuarioService;

@RestController
@RequestMapping(path = "/grupos-usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoUsuarioWebService {

	private final GrupoUsuarioService grupoUsuarioService;

	private final GrupoUsuarioMapper grupoUsuarioMapper;

	public GrupoUsuarioWebService(GrupoUsuarioService grupoUsuarioService, GrupoUsuarioMapper grupoUsuarioMapper) {
		this.grupoUsuarioService = grupoUsuarioService;
		this.grupoUsuarioMapper = grupoUsuarioMapper;
	}

	@GetMapping("/grupos/{cdUsuario}")
	public List<GrupoUsuarioDTO> findByCdUsuarioAndFlAtivoIsTrue(@PathVariable Long cdUsuario) {
		List<GrupoUsuario> gruposUsuarios = grupoUsuarioService.findByCdUsuarioAndFlAtivoIsTrue(cdUsuario);

		List<GrupoUsuarioDTO> gruposUsuariosDTOs = grupoUsuarioMapper.toDtos(gruposUsuarios);

		return gruposUsuariosDTOs;
	}

	@GetMapping("/usuarios/{cdGrupo}")
	public List<GrupoUsuarioDTO> findByCdGrupoAndFlAtivoIsTrue(@PathVariable Long cdGrupo) {
		List<GrupoUsuario> gruposUsuarios = grupoUsuarioService.findByCdGrupoAndFlAtivoIsTrue(cdGrupo);

		List<GrupoUsuarioDTO> gruposUsuariosDTOs = grupoUsuarioMapper.toDtos(gruposUsuarios);

		return gruposUsuariosDTOs;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoUsuarioDTO adicionar(@RequestBody @Valid GrupoUsuarioInput grupoUsuarioInput) {
		GrupoUsuario grupoUsuario = grupoUsuarioMapper.toDomainObject(grupoUsuarioInput);

		grupoUsuario = grupoUsuarioService.adicionar(grupoUsuario);

		GrupoUsuarioDTO grupoUsuarioDTO = grupoUsuarioMapper.toDto(grupoUsuario);

		return grupoUsuarioDTO;
	}

	@GetMapping("/{cdGrupo}/{cdUsuario}")
	public GrupoUsuarioDTO buscar(@PathVariable Long cdGrupo, @PathVariable Long cdUsuario) {
		GrupoUsuario grupoUsuario = grupoUsuarioService.buscar(cdGrupo, cdUsuario);

		GrupoUsuarioDTO grupoUsuarioDTO = grupoUsuarioMapper.toDto(grupoUsuario);

		return grupoUsuarioDTO;
	}

	@PutMapping("/{cdGrupo}/{cdUsuario}")
	public GrupoUsuarioDTO atualizar(@PathVariable Long cdGrupo, @PathVariable Long cdUsuario,
			@RequestBody @Valid GrupoUsuarioInput grupoUsuarioInput) {
		grupoUsuarioService.validarAtualizacao(cdGrupo, cdUsuario, grupoUsuarioInput);

		GrupoUsuario grupoUsuario = grupoUsuarioService.buscar(cdGrupo, cdUsuario);

		grupoUsuarioMapper.copyToDomainObject(grupoUsuarioInput, grupoUsuario);

		grupoUsuario = grupoUsuarioService.atualizar(grupoUsuario);

		GrupoUsuarioDTO grupoUsuarioDTO = grupoUsuarioMapper.toDto(grupoUsuario);

		return grupoUsuarioDTO;
	}

}
