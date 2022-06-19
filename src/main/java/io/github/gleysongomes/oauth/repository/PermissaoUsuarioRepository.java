package io.github.gleysongomes.oauth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.PermissaoUsuario;
import io.github.gleysongomes.oauth.model.primarykey.PermissaoUsuarioId;

@Repository
public interface PermissaoUsuarioRepository extends JpaRepository<PermissaoUsuario, PermissaoUsuarioId> {

	List<PermissaoUsuario> findByCdPermissaoAndFlAtivaIsTrue(Long cdPermissao);

	List<PermissaoUsuario> findByCdUsuarioAndFlAtivaIsTrue(Long cdUsuario);

	@Query("SELECT pu FROM PermissaoUsuario pu JOIN pu.permissao p JOIN pu.usuario u JOIN p.aplicacao a WHERE pu.cdUsuario = :cdUsuario AND a.nome IN (:nomesApis)")
	List<PermissaoUsuario> findByCdUsuarioAndFlAtivaIsTrueAndNomesApis(@Param("cdUsuario") Long cdUsuario,
			@Param("nomesApis") String nomesApis);
}
