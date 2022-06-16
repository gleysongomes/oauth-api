package io.github.gleysongomes.oauth.mapper;

import org.mapstruct.Mapper;

import io.github.gleysongomes.oauth.dto.TipoGrantDTO;
import io.github.gleysongomes.oauth.dto.input.TipoGrantInput;
import io.github.gleysongomes.oauth.model.TipoGrant;

@Mapper(componentModel = "spring")
public interface TipoGrantMapper extends EntityMapper<TipoGrant, TipoGrantDTO, TipoGrantInput> {

}
