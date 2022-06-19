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
import io.github.gleysongomes.oauth.dto.UsuarioDTO;
import io.github.gleysongomes.oauth.dto.input.AdicaoUsuarioInput;
import io.github.gleysongomes.oauth.dto.input.AtualizacaoUsuarioInput;
import io.github.gleysongomes.oauth.dto.input.filter.UsuarioFilter;
import io.github.gleysongomes.oauth.dto.mapping.UsuarioMapping;
import io.github.gleysongomes.oauth.mapper.UsuarioMapper;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.service.UsuarioService;
import io.github.gleysongomes.oauth.webservice.authorize.UsuarioAuthorize;

@RestController
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioWebService {

	private final UsuarioService usuarioService;

	private final UsuarioMapper usuarioMapper;

	public UsuarioWebService(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
		this.usuarioService = usuarioService;
		this.usuarioMapper = usuarioMapper;
	}

	@GetMapping
	@UsuarioAuthorize.Listar
	public PageDTO<UsuarioMapping> listar(UsuarioFilter usuarioFilter, @Valid PageDTO<UsuarioMapping> pageDTO) {
		PageDTO<UsuarioMapping> usuarios = usuarioService.listar(usuarioFilter, pageDTO);

		return usuarios;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@UsuarioAuthorize.Adicionar
	public UsuarioDTO adicionar(@RequestBody @Valid AdicaoUsuarioInput adicaoUsuarioInput) {
		usuarioService.validarConfirmacaoSenha(adicaoUsuarioInput.getSenha(), adicaoUsuarioInput.getSenhaConfirmada());

		Usuario usuario = usuarioMapper.toDomainObject(adicaoUsuarioInput);

		usuarioService.validarAdicaoUsuario(usuario);

		usuario = usuarioService.adicionar(usuario);

		UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

		return usuarioDTO;
	}

	@GetMapping("/{cdUsuario}")
	@UsuarioAuthorize.Buscar
	public UsuarioDTO buscar(@PathVariable Long cdUsuario) {
		Usuario usuario = usuarioService.buscar(cdUsuario);

		UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

		return usuarioDTO;
	}

	@PutMapping("/{cdUsuario}")
	@UsuarioAuthorize.Atualizar
	public UsuarioDTO atualizar(@PathVariable Long cdUsuario,
			@RequestBody @Valid AtualizacaoUsuarioInput atualizacaoUsuarioInput) {
		usuarioService.validarAtualizacaoSenha(atualizacaoUsuarioInput.getSenhaAtual(),
				atualizacaoUsuarioInput.getNovaSenha(), atualizacaoUsuarioInput.getNovaSenhaConfirmada());

		Usuario usuario = usuarioService.buscar(cdUsuario);

		usuarioService.validarSenhaAtual(atualizacaoUsuarioInput.getSenhaAtual(), usuario.getHashSenha());

		usuarioMapper.copyToDomainObject(atualizacaoUsuarioInput, usuario);

		usuarioService.validarAtualizacaoUsuario(usuario);

		usuario = usuarioService.atualizar(usuario);

		UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

		return usuarioDTO;
	}

}
