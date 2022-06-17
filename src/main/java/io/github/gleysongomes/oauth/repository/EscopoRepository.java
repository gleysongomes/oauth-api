package io.github.gleysongomes.oauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.Escopo;

@Repository
public interface EscopoRepository extends JpaRepository<Escopo, Long>, EscopoRepositoryCustom {

	Optional<Escopo> findByNomeAndCdAplicacao(String nome, Long cdAplicacao);

}
