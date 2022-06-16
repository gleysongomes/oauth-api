package io.github.gleysongomes.oauth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.PermissaoUsuario;
import io.github.gleysongomes.oauth.model.primarykey.PermissaoUsuarioId;

@Repository
public interface PermissaoUsuarioRepository extends JpaRepository<PermissaoUsuario, PermissaoUsuarioId> {

	List<PermissaoUsuario> findByCdPermissaoAndFlAtivaIsTrue(Long cdPermissao);

	List<PermissaoUsuario> findByCdUsuarioAndFlAtivaIsTrue(Long cdUsuario);
}
