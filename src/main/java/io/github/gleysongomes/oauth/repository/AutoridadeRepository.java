package io.github.gleysongomes.oauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.Autoridade;

@Repository
public interface AutoridadeRepository extends JpaRepository<Autoridade, Long>, AutoridadeRepositoryCustom {

	Optional<Autoridade> findByNomeAndCdAplicacao(String nome, Long cdAplicacao);

}
