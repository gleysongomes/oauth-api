package io.github.gleysongomes.oauth.service;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.GrupoFilter;
import io.github.gleysongomes.oauth.dto.mapping.GrupoMapping;
import io.github.gleysongomes.oauth.model.Grupo;

public interface GrupoService {

	Grupo adicionar(Grupo grupo);

	Grupo atualizar(Grupo grupo);

	PageDTO<GrupoMapping> listar(GrupoFilter grupoFilter, PageDTO<GrupoMapping> pageDTO);

	Grupo buscar(Long cdGrupo);

	boolean isOwner(Long cdGrupo);
}
