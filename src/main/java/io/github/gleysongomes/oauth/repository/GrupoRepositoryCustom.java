package io.github.gleysongomes.oauth.repository;

import java.util.List;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.GrupoFilter;
import io.github.gleysongomes.oauth.dto.mapping.GrupoMapping;

public interface GrupoRepositoryCustom {

	List<GrupoMapping> listar(GrupoFilter grupoFilter, PageDTO<GrupoMapping> pageDTO);
	
	Long contar(GrupoFilter grupoFilter);
}
