package io.github.gleysongomes.oauth.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.github.gleysongomes.oauth.dto.RotaGrupoDTO;
import io.github.gleysongomes.oauth.dto.input.RotaGrupoInput;
import io.github.gleysongomes.oauth.model.RotaGrupo;

@Mapper(componentModel = "spring")
public interface RotaGrupoMapper {

	@Mapping(source = "usuarioCriacao.nome", target = "nmUsuarioCriacao")
	@Mapping(source = "usuarioAtualizacao.nome", target = "nmUsuarioAtualizacao")
	RotaGrupoDTO toDto(RotaGrupo rotaGrupo);

	List<RotaGrupoDTO> toDtos(List<RotaGrupo> rotasGrupos);

	@Mapping(target = "cdUsuarioCriacao", ignore = true)
	@Mapping(target = "cdUsuarioAtualizacao", ignore = true)
	@Mapping(target = "dtCadastro", ignore = true)
	@Mapping(target = "dtAtualizacao", ignore = true)
	@Mapping(target = "rota", ignore = true)
	@Mapping(target = "grupo", ignore = true)
	@Mapping(target = "usuarioCriacao", ignore = true)
	@Mapping(target = "usuarioAtualizacao", ignore = true)
	RotaGrupo toDomainObject(RotaGrupoInput rotaGrupoInput);

	@Mapping(target = "cdUsuarioCriacao", ignore = true)
	@Mapping(target = "cdUsuarioAtualizacao", ignore = true)
	@Mapping(target = "dtCadastro", ignore = true)
	@Mapping(target = "dtAtualizacao", ignore = true)
	@Mapping(target = "rota", ignore = true)
	@Mapping(target = "grupo", ignore = true)
	@Mapping(target = "usuarioCriacao", ignore = true)
	@Mapping(target = "usuarioAtualizacao", ignore = true)
	void copyToDomainObject(RotaGrupoInput rotaGrupoInput, @MappingTarget RotaGrupo rotaGrupo);
}
