package io.github.gleysongomes.oauth.service;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.EscopoFilter;
import io.github.gleysongomes.oauth.dto.mapping.EscopoMapping;
import io.github.gleysongomes.oauth.model.Escopo;

public interface EscopoService {

	Escopo adicionar(Escopo escopo);

	Escopo atualizar(Escopo escopo);

	PageDTO<EscopoMapping> listar(EscopoFilter escopoFilter, PageDTO<EscopoMapping> pageDTO);

	Escopo buscar(Long cdEscopo);
}
