package io.github.gleysongomes.oauth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.gleysongomes.oauth.dto.AutoridadeDTO;
import io.github.gleysongomes.oauth.dto.input.AutoridadeInput;
import io.github.gleysongomes.oauth.model.Autoridade;

@Mapper(componentModel = "spring")
public interface AutoridadeMapper extends EntityMapper<Autoridade, AutoridadeDTO, AutoridadeInput> {

	@Mapping(source = "aplicacao.nome", target = "nmAplicacao")
	AutoridadeDTO toDto(Autoridade autoridade);
}
