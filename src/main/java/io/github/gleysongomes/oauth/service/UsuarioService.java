package io.github.gleysongomes.oauth.service;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.UsuarioFilter;
import io.github.gleysongomes.oauth.dto.mapping.UsuarioMapping;
import io.github.gleysongomes.oauth.model.Usuario;

public interface UsuarioService {

	Usuario adicionar(Usuario usuario);

	Usuario atualizar(Usuario usuario);

	PageDTO<UsuarioMapping> listar(UsuarioFilter usuarioFilter, PageDTO<UsuarioMapping> pageDTO);

	Usuario buscar(Long cdUsuario, String mensagem);

	Usuario buscar(Long cdUsuario);

	void validarAtualizacaoUsuario(Usuario usuario);

	void validarAdicaoUsuario(Usuario usuario);

	void validarAtualizacaoSenha(String senhaAtual, String novaSenha, String novaSenhaConfirmada);

	void validarSenhaAtual(String senhaAtual, String hashSenha);

	void validarConfirmacaoSenha(String senha, String senhaConfirmada);

	boolean isOwner(Long cdUsuario);

	Usuario buscarPorLogin(String login, String mensagem);

	Usuario buscarPorLogin(String login);
}
