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

import io.github.gleysongomes.oauth.dto.PermissaoUsuarioDTO;
import io.github.gleysongomes.oauth.dto.input.PermissaoUsuarioInput;
import io.github.gleysongomes.oauth.mapper.PermissaoUsuarioMapper;
import io.github.gleysongomes.oauth.model.PermissaoUsuario;
import io.github.gleysongomes.oauth.service.PermissaoUsuarioService;

@RestController
@RequestMapping(path = "/permissoes-usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoUsuarioWebService {

	private final PermissaoUsuarioService permissaoUsuarioService;

	private final PermissaoUsuarioMapper permissaoUsuarioMapper;

	public PermissaoUsuarioWebService(PermissaoUsuarioService permissaoUsuarioService,
			PermissaoUsuarioMapper permissaoUsuarioMapper) {
		this.permissaoUsuarioService = permissaoUsuarioService;
		this.permissaoUsuarioMapper = permissaoUsuarioMapper;
	}

	@GetMapping("/permissoes/{cdUsuario}")
	public List<PermissaoUsuarioDTO> findByCdUsuarioAndFlAtivaIsTrue(@PathVariable Long cdUsuario) {
		List<PermissaoUsuario> permissoes = permissaoUsuarioService.findByCdUsuarioAndFlAtivaIsTrue(cdUsuario);

		List<PermissaoUsuarioDTO> permissoesDTOs = permissaoUsuarioMapper.toDtos(permissoes);

		return permissoesDTOs;
	}

	@GetMapping("/usuarios/{cdPermissao}")
	public List<PermissaoUsuarioDTO> findByCdPermissaoAndFlAtivaIsTrue(@PathVariable Long cdPermissao) {
		List<PermissaoUsuario> usuarios = permissaoUsuarioService.findByCdPermissaoAndFlAtivaIsTrue(cdPermissao);

		List<PermissaoUsuarioDTO> usuariosDTOs = permissaoUsuarioMapper.toDtos(usuarios);

		return usuariosDTOs;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PermissaoUsuarioDTO adicionar(@RequestBody @Valid PermissaoUsuarioInput permissaoUsuarioInput) {
		PermissaoUsuario permissaoUsuario = permissaoUsuarioMapper.toDomainObject(permissaoUsuarioInput);

		permissaoUsuario = permissaoUsuarioService.adicionar(permissaoUsuario);

		PermissaoUsuarioDTO permissaoUsuarioDTO = permissaoUsuarioMapper.toDto(permissaoUsuario);

		return permissaoUsuarioDTO;
	}

	@GetMapping("/{cdPermissao}/{cdUsuario}")
	public PermissaoUsuarioDTO buscar(@PathVariable Long cdPermissao, @PathVariable Long cdUsuario) {
		PermissaoUsuario permissaoUsuario = permissaoUsuarioService.buscar(cdPermissao, cdUsuario);

		PermissaoUsuarioDTO permissaoUsuarioDTO = permissaoUsuarioMapper.toDto(permissaoUsuario);

		return permissaoUsuarioDTO;
	}

	@PutMapping("/{cdPermissao}/{cdUsuario}")
	public PermissaoUsuarioDTO atualizar(@PathVariable Long cdPermissao, @PathVariable Long cdUsuario,
			@RequestBody @Valid PermissaoUsuarioInput permissaoUsuarioInput) {
		permissaoUsuarioService.validarAtualizacao(cdPermissao, cdUsuario, permissaoUsuarioInput);

		PermissaoUsuario permissaoUsuario = permissaoUsuarioService.buscar(cdPermissao, cdUsuario);

		permissaoUsuarioMapper.copyToDomainObject(permissaoUsuarioInput, permissaoUsuario);

		permissaoUsuario = permissaoUsuarioService.atualizar(permissaoUsuario);

		PermissaoUsuarioDTO permissaoUsuarioDTO = permissaoUsuarioMapper.toDto(permissaoUsuario);

		return permissaoUsuarioDTO;
	}

}
