package io.github.gleysongomes.oauth.mapper;

import org.mapstruct.Mapper;

import io.github.gleysongomes.oauth.dto.GrupoDTO;
import io.github.gleysongomes.oauth.dto.input.GrupoInput;
import io.github.gleysongomes.oauth.model.Grupo;

@Mapper(componentModel = "spring")
public interface GrupoMapper extends EntityMapper<Grupo, GrupoDTO, GrupoInput> {

}
