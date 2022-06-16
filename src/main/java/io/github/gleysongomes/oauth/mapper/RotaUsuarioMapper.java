package io.github.gleysongomes.oauth.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.github.gleysongomes.oauth.dto.RotaUsuarioDTO;
import io.github.gleysongomes.oauth.dto.input.RotaUsuarioInput;
import io.github.gleysongomes.oauth.model.RotaUsuario;

@Mapper(componentModel = "spring")
public interface RotaUsuarioMapper {

	@Mapping(source = "usuarioCriacao.nome", target = "nmUsuarioCriacao")
	@Mapping(source = "usuarioAtualizacao.nome", target = "nmUsuarioAtualizacao")
	RotaUsuarioDTO toDto(RotaUsuario rotaUsuario);

	List<RotaUsuarioDTO> toDtos(List<RotaUsuario> rotasUsuarios);

	@Mapping(target = "cdUsuarioCriacao", ignore = true)
	@Mapping(target = "cdUsuarioAtualizacao", ignore = true)
	@Mapping(target = "dtCadastro", ignore = true)
	@Mapping(target = "dtAtualizacao", ignore = true)
	@Mapping(target = "rota", ignore = true)
	@Mapping(target = "usuario", ignore = true)
	@Mapping(target = "usuarioCriacao", ignore = true)
	@Mapping(target = "usuarioAtualizacao", ignore = true)
	RotaUsuario toDomainObject(RotaUsuarioInput rotaUsuarioInput);

	@Mapping(target = "cdUsuarioCriacao", ignore = true)
	@Mapping(target = "cdUsuarioAtualizacao", ignore = true)
	@Mapping(target = "dtCadastro", ignore = true)
	@Mapping(target = "dtAtualizacao", ignore = true)
	@Mapping(target = "rota", ignore = true)
	@Mapping(target = "usuario", ignore = true)
	@Mapping(target = "usuarioCriacao", ignore = true)
	@Mapping(target = "usuarioAtualizacao", ignore = true)
	void copyToDomainObject(RotaUsuarioInput rotaUsuarioInput, @MappingTarget RotaUsuario rotaUsuario);
}
