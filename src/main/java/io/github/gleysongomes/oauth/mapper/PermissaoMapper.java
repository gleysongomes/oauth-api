package io.github.gleysongomes.oauth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.gleysongomes.oauth.dto.PermissaoDTO;
import io.github.gleysongomes.oauth.dto.input.PermissaoInput;
import io.github.gleysongomes.oauth.model.Permissao;

@Mapper(componentModel = "spring")
public interface PermissaoMapper extends EntityMapper<Permissao, PermissaoDTO, PermissaoInput> {

	@Mapping(source = "aplicacao.nome", target = "nmAplicacao")
	PermissaoDTO toDto(Permissao permissao);
}
