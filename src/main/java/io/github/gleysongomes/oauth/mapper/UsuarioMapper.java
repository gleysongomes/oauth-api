package io.github.gleysongomes.oauth.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.github.gleysongomes.oauth.dto.UsuarioDTO;
import io.github.gleysongomes.oauth.dto.input.AdicaoUsuarioInput;
import io.github.gleysongomes.oauth.dto.input.AtualizacaoUsuarioInput;
import io.github.gleysongomes.oauth.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	UsuarioDTO toDto(Usuario usuario);

	List<UsuarioDTO> toDtos(List<Usuario> usuarios);

	@Mapping(source = "senha", target = "hashSenha")
	Usuario toDomainObject(AdicaoUsuarioInput adicaoUsuarioInput);

	@Mapping(source = "novaSenha", target = "hashSenha")
	void copyToDomainObject(AtualizacaoUsuarioInput atualizacaoUsuarioInput, @MappingTarget Usuario usuario);
}
