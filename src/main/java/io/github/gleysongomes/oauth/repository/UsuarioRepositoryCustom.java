package io.github.gleysongomes.oauth.repository;

import java.util.List;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.UsuarioFilter;
import io.github.gleysongomes.oauth.dto.mapping.UsuarioMapping;

public interface UsuarioRepositoryCustom {

	List<UsuarioMapping> listar(UsuarioFilter usuarioFilter, PageDTO<UsuarioMapping> pageDTO);

	Long contar(UsuarioFilter usuarioFilter);
}
