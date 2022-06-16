package io.github.gleysongomes.oauth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.TipoGrantAplicacao;
import io.github.gleysongomes.oauth.model.primarykey.TipoGrantAplicacaoId;

@Repository
public interface TipoGrantAplicacaoRepository extends JpaRepository<TipoGrantAplicacao, TipoGrantAplicacaoId> {

	List<TipoGrantAplicacao> findByCdTipoGrantAndFlAtivoIsTrue(Long cdTipoGrant);

	List<TipoGrantAplicacao> findByCdAplicacaoAndFlAtivoIsTrue(Long cdAplicacao);
}
