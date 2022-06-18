package io.github.gleysongomes.oauth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.gleysongomes.oauth.dto.RedirecionamentoDTO;
import io.github.gleysongomes.oauth.dto.input.RedirecionamentoInput;
import io.github.gleysongomes.oauth.model.Redirecionamento;

@Mapper(componentModel = "spring")
public interface RedirecionamentoMapper extends EntityMapper<Redirecionamento, RedirecionamentoDTO, RedirecionamentoInput> {

	@Mapping(source = "aplicacao.nome", target = "nmAplicacao")
	RedirecionamentoDTO toDto(Redirecionamento redirecionamento);
}
