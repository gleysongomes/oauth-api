package io.github.gleysongomes.oauth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.gleysongomes.oauth.dto.RotaDTO;
import io.github.gleysongomes.oauth.dto.input.RotaInput;
import io.github.gleysongomes.oauth.model.Rota;

@Mapper(componentModel = "spring")
public interface RotaMapper extends EntityMapper<Rota, RotaDTO, RotaInput> {

	@Mapping(source = "aplicacao.nome", target = "nmAplicacao")
	RotaDTO toDto(Rota rota);
}
