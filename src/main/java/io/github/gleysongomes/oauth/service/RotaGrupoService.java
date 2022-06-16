package io.github.gleysongomes.oauth.service;

import java.util.List;

import io.github.gleysongomes.oauth.dto.input.RotaGrupoInput;
import io.github.gleysongomes.oauth.model.RotaGrupo;

public interface RotaGrupoService {

	RotaGrupo adicionar(RotaGrupo rotaGrupo);

	RotaGrupo atualizar(RotaGrupo rotaGrupo);

	RotaGrupo buscar(Long cdRota, Long cdGrupo);

	List<RotaGrupo> findByCdRotaAndFlAtivaIsTrue(Long cdRota);

	List<RotaGrupo> findByCdGrupoAndFlAtivaIsTrue(Long cdGrupo);

	void validarAtualizacao(Long cdRota, Long cdGrupo, RotaGrupoInput rotaGrupoInput);
}
