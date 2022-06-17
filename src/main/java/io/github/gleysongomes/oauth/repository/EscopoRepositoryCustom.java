package io.github.gleysongomes.oauth.repository;

import java.util.List;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.EscopoFilter;
import io.github.gleysongomes.oauth.dto.mapping.EscopoMapping;

public interface EscopoRepositoryCustom {

	List<EscopoMapping> listar(EscopoFilter escopoFilter, PageDTO<EscopoMapping> pageDTO);

	Long contar(EscopoFilter escopoFilter);
}
