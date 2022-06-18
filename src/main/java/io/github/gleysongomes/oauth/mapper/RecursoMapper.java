package io.github.gleysongomes.oauth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.gleysongomes.oauth.dto.RecursoDTO;
import io.github.gleysongomes.oauth.dto.input.RecursoInput;
import io.github.gleysongomes.oauth.model.Recurso;

@Mapper(componentModel = "spring")
public interface RecursoMapper extends EntityMapper<Recurso, RecursoDTO, RecursoInput> {

	@Mapping(source = "aplicacao.nome", target = "nmAplicacao")
	RecursoDTO toDto(Recurso recurso);
}
