package io.github.gleysongomes.oauth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.GrupoUsuario;
import io.github.gleysongomes.oauth.model.primarykey.GrupoUsuarioId;

@Repository
public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, GrupoUsuarioId> {

	List<GrupoUsuario> findByCdGrupoAndFlAtivoIsTrue(Long cdGrupo);

	List<GrupoUsuario> findByCdUsuarioAndFlAtivoIsTrue(Long cdUsuario);
}
