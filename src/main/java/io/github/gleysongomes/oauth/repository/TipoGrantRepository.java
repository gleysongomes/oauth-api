package io.github.gleysongomes.oauth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.TipoGrant;

@Repository
public interface TipoGrantRepository extends JpaRepository<TipoGrant, Long>, TipoGrantRepositoryCustom {

	Optional<TipoGrant> findByNome(String nome);
}
