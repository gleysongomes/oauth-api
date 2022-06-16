package io.github.gleysongomes.oauth.service;

import java.util.List;

import io.github.gleysongomes.oauth.dto.input.PermissaoUsuarioInput;
import io.github.gleysongomes.oauth.model.PermissaoUsuario;

public interface PermissaoUsuarioService {

	PermissaoUsuario adicionar(PermissaoUsuario permissaoUsuario);

	PermissaoUsuario atualizar(PermissaoUsuario permissaoUsuario);

	PermissaoUsuario buscar(Long cdPermissao, Long cdUsuario);

	List<PermissaoUsuario> findByCdPermissaoAndFlAtivaIsTrue(Long cdPermissao);

	List<PermissaoUsuario> findByCdUsuarioAndFlAtivaIsTrue(Long cdUsuario);

	void validarAtualizacao(Long cdPermissao, Long cdUsuario, PermissaoUsuarioInput permissaoUsuarioInput);
}
