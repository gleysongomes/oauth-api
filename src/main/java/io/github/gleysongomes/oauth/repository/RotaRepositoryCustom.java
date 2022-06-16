package io.github.gleysongomes.oauth.repository;

import java.util.List;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.RotaFilter;
import io.github.gleysongomes.oauth.dto.mapping.RotaMapping;

public interface RotaRepositoryCustom {

	List<RotaMapping> listar(RotaFilter rotaFilter, PageDTO<RotaMapping> pageDTO);

	Long contar(RotaFilter rotaFilter);
}
