package io.github.gleysongomes.oauth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.gleysongomes.oauth.dto.AplicacaoDTO;
import io.github.gleysongomes.oauth.dto.input.AplicacaoInput;
import io.github.gleysongomes.oauth.model.Aplicacao;

@Mapper(componentModel = "spring")
public interface AplicacaoMapper extends EntityMapper<Aplicacao, AplicacaoDTO, AplicacaoInput> {

	@Mapping(source = "usuarioCriacao.nome", target = "nmUsuarioCriacao")
	@Mapping(source = "usuarioAtualizacao.nome", target = "nmUsuarioAtualizacao")
	AplicacaoDTO toDto(Aplicacao aplicacao);
}
