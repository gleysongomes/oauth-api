package io.github.gleysongomes.oauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.Recurso;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long>, RecursoRepositoryCustom {

	Optional<Recurso> findByNomeAndCdAplicacao(String nome, Long cdAplicacao);

}
