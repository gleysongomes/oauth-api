package io.github.gleysongomes.oauth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.PermissaoGrupo;
import io.github.gleysongomes.oauth.model.primarykey.PermissaoGrupoId;

@Repository
public interface PermissaoGrupoRepository extends JpaRepository<PermissaoGrupo, PermissaoGrupoId> {

	List<PermissaoGrupo> findByCdPermissaoAndFlAtivaIsTrue(Long cdPermissao);

	List<PermissaoGrupo> findByCdGrupoAndFlAtivaIsTrue(Long cdGrupo);
}
