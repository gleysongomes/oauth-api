package io.github.gleysongomes.oauth.service;

import java.util.List;

import io.github.gleysongomes.oauth.model.GrupoUsuario;

public interface GrupoUsuarioService {

	GrupoUsuario adicionar(GrupoUsuario grupoUsuario);

	GrupoUsuario atualizar(GrupoUsuario grupoUsuario);

	GrupoUsuario buscar(Long cdGrupo, Long cdUsuario);

	List<GrupoUsuario> findByCdGrupoAndFlAtivoIsTrue(Long cdGrupo);

	List<GrupoUsuario> findByCdUsuarioAndFlAtivoIsTrue(Long cdUsuario);
}
