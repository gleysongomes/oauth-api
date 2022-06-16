package io.github.gleysongomes.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.model.Rota;

@Repository
public interface RotaRepository extends JpaRepository<Rota, Long>, RotaRepositoryCustom {

}
