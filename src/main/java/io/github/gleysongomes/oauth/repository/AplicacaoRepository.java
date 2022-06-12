package io.github.gleysongomes.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.Aplicacao;

@Repository
public interface AplicacaoRepository extends JpaRepository<Aplicacao, Long>, AplicacaoRepositoryCustom {

	Aplicacao findByNome(String nome);
}
