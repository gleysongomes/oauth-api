package io.github.gleysongomes.oauth.service;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.RotaFilter;
import io.github.gleysongomes.oauth.dto.mapping.RotaMapping;
import io.github.gleysongomes.oauth.model.Rota;

public interface RotaService {

	Rota adicionar(Rota rota);

	Rota atualizar(Rota rota);

	PageDTO<RotaMapping> listar(RotaFilter rotaFilter, PageDTO<RotaMapping> pageDTO);

	Rota buscar(Long cdRota);
}
