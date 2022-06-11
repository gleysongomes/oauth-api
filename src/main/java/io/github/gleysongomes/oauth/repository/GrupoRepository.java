package io.github.gleysongomes.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>, GrupoRepositoryCustom {

}
