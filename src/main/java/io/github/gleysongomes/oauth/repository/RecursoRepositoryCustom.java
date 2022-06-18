package io.github.gleysongomes.oauth.repository;

import java.util.List;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.RecursoFilter;
import io.github.gleysongomes.oauth.dto.mapping.RecursoMapping;

public interface RecursoRepositoryCustom {

	List<RecursoMapping> listar(RecursoFilter recursoFilter, PageDTO<RecursoMapping> pageDTO);

	Long contar(RecursoFilter recursoFilterTO);
}
