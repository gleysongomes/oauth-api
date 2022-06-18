package io.github.gleysongomes.oauth.service;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.RecursoFilter;
import io.github.gleysongomes.oauth.dto.mapping.RecursoMapping;
import io.github.gleysongomes.oauth.model.Recurso;

public interface RecursoService {

	Recurso adicionar(Recurso recurso);

	Recurso atualizar(Recurso recurso);

	PageDTO<RecursoMapping> listar(RecursoFilter recursoFilter, PageDTO<RecursoMapping> pageDTO);

	Recurso buscar(Long cdRecurso);
}
