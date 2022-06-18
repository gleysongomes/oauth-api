package io.github.gleysongomes.oauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.Redirecionamento;

@Repository
public interface RedirecionamentoRepository extends JpaRepository<Redirecionamento, Long>, RedirecionamentoRepositoryCustom {

	Optional<Redirecionamento> findByUrlAndCdAplicacao(String url, Long cdAplicacao);

}
