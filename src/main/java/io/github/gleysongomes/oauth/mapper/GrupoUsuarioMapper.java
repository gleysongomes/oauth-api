package io.github.gleysongomes.oauth.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.github.gleysongomes.oauth.dto.GrupoUsuarioDTO;
import io.github.gleysongomes.oauth.dto.input.GrupoUsuarioInput;
import io.github.gleysongomes.oauth.model.GrupoUsuario;

@Mapper(componentModel = "spring")
public interface GrupoUsuarioMapper {

	@Mapping(source = "usuarioCriacao.nome", target = "nmUsuarioCriacao")
	@Mapping(source = "usuarioAtualizacao.nome", target = "nmUsuarioAtualizacao")
	GrupoUsuarioDTO toDto(GrupoUsuario grupoUsuario);

	List<GrupoUsuarioDTO> toDtos(List<GrupoUsuario> gruposUsuarios);

	@Mapping(target = "cdUsuarioCriacao", ignore = true)
	@Mapping(target = "cdUsuarioAtualizacao", ignore = true)
	@Mapping(target = "dtCadastro", ignore = true)
	@Mapping(target = "dtAtualizacao", ignore = true)
	@Mapping(target = "usuario", ignore = true)
	@Mapping(target = "grupo", ignore = true)
	@Mapping(target = "usuarioCriacao", ignore = true)
	@Mapping(target = "usuarioAtualizacao", ignore = true)
	GrupoUsuario toDomainObject(GrupoUsuarioInput grupoUsuarioInput);

	@Mapping(target = "cdUsuarioCriacao", ignore = true)
	@Mapping(target = "cdUsuarioAtualizacao", ignore = true)
	@Mapping(target = "dtCadastro", ignore = true)
	@Mapping(target = "dtAtualizacao", ignore = true)
	@Mapping(target = "usuario", ignore = true)
	@Mapping(target = "grupo", ignore = true)
	@Mapping(target = "usuarioCriacao", ignore = true)
	@Mapping(target = "usuarioAtualizacao", ignore = true)
	void copyToDomainObject(GrupoUsuarioInput grupoUsuarioInput, @MappingTarget GrupoUsuario grupoUsuario);
}
