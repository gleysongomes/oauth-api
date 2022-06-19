package io.github.gleysongomes.oauth.service;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.AplicacaoFilter;
import io.github.gleysongomes.oauth.dto.mapping.AplicacaoMapping;
import io.github.gleysongomes.oauth.model.Aplicacao;

public interface AplicacaoService {

	Aplicacao adicionar(Aplicacao aplicacao);

	Aplicacao atualizar(Aplicacao aplicacao);

	PageDTO<AplicacaoMapping> listar(AplicacaoFilter aplicacaoFilter, PageDTO<AplicacaoMapping> pageDTO);

	Aplicacao buscar(Long cdAplicacao);

	Aplicacao buscarPorNome(String nome);
}
