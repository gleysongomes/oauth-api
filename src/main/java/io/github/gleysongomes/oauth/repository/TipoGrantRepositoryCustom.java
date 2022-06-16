package io.github.gleysongomes.oauth.repository;

import java.util.List;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.TipoGrantFilter;
import io.github.gleysongomes.oauth.dto.mapping.TipoGrantMapping;

public interface TipoGrantRepositoryCustom {

	List<TipoGrantMapping> listar(TipoGrantFilter tipoGrantFilter, PageDTO<TipoGrantMapping> pageDTO);

	Long contar(TipoGrantFilter tipoGrantFilter);
}
