package io.github.gleysongomes.oauth.repository;

import java.util.List;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.PermissaoFilter;
import io.github.gleysongomes.oauth.dto.mapping.PermissaoMapping;
import io.github.gleysongomes.oauth.dto.mapping.PermissaoMappingResumo;

public interface PermissaoRepositoryCustom {

	List<PermissaoMapping> listar(PermissaoFilter permissaoFilter, PageDTO<PermissaoMapping> pageDTO);

	Long contar(PermissaoFilter permissaoFilter);

	List<PermissaoMappingResumo> findPermissoesGrupoByCdUsuario(Long cdUsuario);
}
