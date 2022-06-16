package io.github.gleysongomes.oauth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.RotaGrupo;
import io.github.gleysongomes.oauth.model.primarykey.RotaGrupoId;

@Repository
public interface RotaGrupoRepository extends JpaRepository<RotaGrupo, RotaGrupoId> {

	List<RotaGrupo> findByCdRotaAndFlAtivaIsTrue(Long cdRota);

	List<RotaGrupo> findByCdGrupoAndFlAtivaIsTrue(Long cdGrupo);
}
