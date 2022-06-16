package io.github.gleysongomes.oauth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.RotaUsuario;
import io.github.gleysongomes.oauth.model.primarykey.RotaUsuarioId;

@Repository
public interface RotaUsuarioRepository extends JpaRepository<RotaUsuario, RotaUsuarioId> {

	List<RotaUsuario> findByCdRotaAndFlAtivaIsTrue(Long cdRota);

	List<RotaUsuario> findByCdUsuarioAndFlAtivaIsTrue(Long cdUsuario);
}
