package io.github.gleysongomes.oauth.service;

import java.util.List;

import io.github.gleysongomes.oauth.dto.input.RotaUsuarioInput;
import io.github.gleysongomes.oauth.model.RotaUsuario;

public interface RotaUsuarioService {

	RotaUsuario adicionar(RotaUsuario rotaUsuario);

	RotaUsuario atualizar(RotaUsuario rotaUsuario);

	RotaUsuario buscar(Long cdRota, Long cdGrupo);

	List<RotaUsuario> findByCdRotaAndFlAtivaIsTrue(Long cdRota);

	List<RotaUsuario> findByCdUsuarioAndFlAtivaIsTrue(Long cdUsuario);

	void validarAtualizacao(Long cdRota, Long cdUsuario, RotaUsuarioInput rotaUsuarioInput);
}
