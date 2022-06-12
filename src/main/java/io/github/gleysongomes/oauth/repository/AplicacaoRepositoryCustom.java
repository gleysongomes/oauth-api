package io.github.gleysongomes.oauth.repository;

import java.util.List;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.AplicacaoFilter;
import io.github.gleysongomes.oauth.dto.mapping.AplicacaoMapping;

public interface AplicacaoRepositoryCustom {

	List<AplicacaoMapping> listar(AplicacaoFilter aplicacaoFilter, PageDTO<AplicacaoMapping> pageDTO);

	Long contar(AplicacaoFilter aplicacaoFilter);
}
