package io.github.gleysongomes.oauth.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.github.gleysongomes.oauth.dto.PermissaoGrupoDTO;
import io.github.gleysongomes.oauth.dto.input.PermissaoGrupoInput;
import io.github.gleysongomes.oauth.model.PermissaoGrupo;

@Mapper(componentModel = "spring")
public interface PermissaoGrupoMapper {

	@Mapping(source = "usuarioCriacao.nome", target = "nmUsuarioCriacao")
	@Mapping(source = "usuarioAtualizacao.nome", target = "nmUsuarioAtualizacao")
	PermissaoGrupoDTO toDto(PermissaoGrupo permissaoGrupo);

	List<PermissaoGrupoDTO> toDtos(List<PermissaoGrupo> permissoesGrupos);

	@Mapping(target = "cdUsuarioCriacao", ignore = true)
	@Mapping(target = "cdUsuarioAtualizacao", ignore = true)
	@Mapping(target = "dtCadastro", ignore = true)
	@Mapping(target = "dtAtualizacao", ignore = true)
	@Mapping(target = "permissao", ignore = true)
	@Mapping(target = "grupo", ignore = true)
	@Mapping(target = "usuarioCriacao", ignore = true)
	@Mapping(target = "usuarioAtualizacao", ignore = true)
	PermissaoGrupo toDomainObject(PermissaoGrupoInput permissaoGrupoInput);

	@Mapping(target = "cdUsuarioCriacao", ignore = true)
	@Mapping(target = "cdUsuarioAtualizacao", ignore = true)
	@Mapping(target = "dtCadastro", ignore = true)
	@Mapping(target = "dtAtualizacao", ignore = true)
	@Mapping(target = "permissao", ignore = true)
	@Mapping(target = "grupo", ignore = true)
	@Mapping(target = "usuarioCriacao", ignore = true)
	@Mapping(target = "usuarioAtualizacao", ignore = true)
	void copyToDomainObject(PermissaoGrupoInput permissaoGrupoInput, @MappingTarget PermissaoGrupo permissaoGrupo);
}
