package io.github.gleysongomes.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long>, PermissaoRepositoryCustom {

}
