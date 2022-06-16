package io.github.gleysongomes.oauth.service;

import java.util.List;

import io.github.gleysongomes.oauth.dto.input.TipoGrantAplicacaoInput;
import io.github.gleysongomes.oauth.model.TipoGrantAplicacao;

public interface TipoGrantAplicacaoService {

	TipoGrantAplicacao adicionar(TipoGrantAplicacao tipoGrantAplicacao);

	TipoGrantAplicacao atualizar(TipoGrantAplicacao tipoGrantAplicacao);

	TipoGrantAplicacao buscar(Long cdTipoGrant, Long cdAplicacao);

	List<TipoGrantAplicacao> findByCdTipoGrantAndFlAtivoIsTrue(Long cdTipoGrant);

	List<TipoGrantAplicacao> findByCdAplicacaoAndFlAtivoIsTrue(Long cdAplicacao);

	void validarAtualizacao(Long cdTipoGrant, Long cdAplicacao, TipoGrantAplicacaoInput tipoGrantAplicacaoInput);
}
