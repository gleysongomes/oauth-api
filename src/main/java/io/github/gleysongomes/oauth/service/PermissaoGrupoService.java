package io.github.gleysongomes.oauth.service;

import java.util.List;

import io.github.gleysongomes.oauth.dto.input.PermissaoGrupoInput;
import io.github.gleysongomes.oauth.model.PermissaoGrupo;

public interface PermissaoGrupoService {

	PermissaoGrupo adicionar(PermissaoGrupo permissaoGrupo);

	PermissaoGrupo atualizar(PermissaoGrupo permissaoGrupo);

	PermissaoGrupo buscar(Long cdPermissao, Long cdGrupo);

	List<PermissaoGrupo> findByCdPermissaoAndFlAtivaIsTrue(Long cdPermissao);

	List<PermissaoGrupo> findByCdGrupoAndFlAtivaIsTrue(Long cdGrupo);

	void validarAtualizacao(Long cdPermissao, Long cdGrupo, PermissaoGrupoInput permissaoGrupoInput);
}
