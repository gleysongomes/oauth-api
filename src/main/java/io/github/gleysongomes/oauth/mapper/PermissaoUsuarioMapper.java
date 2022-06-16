package io.github.gleysongomes.oauth.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.github.gleysongomes.oauth.dto.PermissaoUsuarioDTO;
import io.github.gleysongomes.oauth.dto.input.PermissaoUsuarioInput;
import io.github.gleysongomes.oauth.model.PermissaoUsuario;

@Mapper(componentModel = "spring")
public interface PermissaoUsuarioMapper {

	@Mapping(source = "usuarioCriacao.nome", target = "nmUsuarioCriacao")
	@Mapping(source = "usuarioAtualizacao.nome", target = "nmUsuarioAtualizacao")
	PermissaoUsuarioDTO toDto(PermissaoUsuario permissaoUsuario);

	List<PermissaoUsuarioDTO> toDtos(List<PermissaoUsuario> permissoesUsuarios);

	@Mapping(target = "cdUsuarioCriacao", ignore = true)
	@Mapping(target = "cdUsuarioAtualizacao", ignore = true)
	@Mapping(target = "dtCadastro", ignore = true)
	@Mapping(target = "dtAtualizacao", ignore = true)
	@Mapping(target = "permissao", ignore = true)
	@Mapping(target = "usuario", ignore = true)
	@Mapping(target = "usuarioCriacao", ignore = true)
	@Mapping(target = "usuarioAtualizacao", ignore = true)
	PermissaoUsuario toDomainObject(PermissaoUsuarioInput permissaoUsuarioInput);

	@Mapping(target = "cdUsuarioCriacao", ignore = true)
	@Mapping(target = "cdUsuarioAtualizacao", ignore = true)
	@Mapping(target = "dtCadastro", ignore = true)
	@Mapping(target = "dtAtualizacao", ignore = true)
	@Mapping(target = "permissao", ignore = true)
	@Mapping(target = "usuario", ignore = true)
	@Mapping(target = "usuarioCriacao", ignore = true)
	@Mapping(target = "usuarioAtualizacao", ignore = true)
	void copyToDomainObject(PermissaoUsuarioInput permissaoUsuarioInput, @MappingTarget PermissaoUsuario permissaoUsuario);
}
