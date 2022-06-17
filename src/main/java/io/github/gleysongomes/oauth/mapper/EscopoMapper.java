package io.github.gleysongomes.oauth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.gleysongomes.oauth.dto.EscopoDTO;
import io.github.gleysongomes.oauth.dto.input.EscopoInput;
import io.github.gleysongomes.oauth.model.Escopo;

@Mapper(componentModel = "spring")
public interface EscopoMapper extends EntityMapper<Escopo, EscopoDTO, EscopoInput> {

	@Mapping(source = "aplicacao.nome", target = "nmAplicacao")
	EscopoDTO toDto(Escopo escopo);
}
