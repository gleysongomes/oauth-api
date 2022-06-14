package io.github.gleysongomes.oauth.service;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.PermissaoFilter;
import io.github.gleysongomes.oauth.dto.mapping.PermissaoMapping;
import io.github.gleysongomes.oauth.model.Permissao;

public interface PermissaoService {

	Permissao adicionar(Permissao permissao);

	Permissao atualizar(Permissao permissao);

	PageDTO<PermissaoMapping> listar(PermissaoFilter permissaoFilter, PageDTO<PermissaoMapping> pageDTO);

	Permissao buscar(Long cdPermissao);
}
