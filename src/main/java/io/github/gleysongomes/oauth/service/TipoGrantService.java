package io.github.gleysongomes.oauth.service;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.TipoGrantFilter;
import io.github.gleysongomes.oauth.dto.mapping.TipoGrantMapping;
import io.github.gleysongomes.oauth.model.TipoGrant;

public interface TipoGrantService {

	TipoGrant adicionar(TipoGrant tipoGrant);

	TipoGrant atualizar(TipoGrant tipoGrant);

	PageDTO<TipoGrantMapping> listar(TipoGrantFilter tipoGrantFilter, PageDTO<TipoGrantMapping> pageDTO);

	TipoGrant buscar(Long cdTipoGrant);
}
