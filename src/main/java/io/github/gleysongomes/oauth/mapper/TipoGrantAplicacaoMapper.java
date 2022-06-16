package io.github.gleysongomes.oauth.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import io.github.gleysongomes.oauth.dto.TipoGrantAplicacaoDTO;
import io.github.gleysongomes.oauth.dto.input.TipoGrantAplicacaoInput;
import io.github.gleysongomes.oauth.model.TipoGrantAplicacao;

@Mapper(componentModel = "spring")
public interface TipoGrantAplicacaoMapper {

	@Mapping(source = "usuarioCriacao.nome", target = "nmUsuarioCriacao")
	@Mapping(source = "usuarioAtualizacao.nome", target = "nmUsuarioAtualizacao")
	TipoGrantAplicacaoDTO toDto(TipoGrantAplicacao tipoGrantAplicacao);

	List<TipoGrantAplicacaoDTO> toDtos(List<TipoGrantAplicacao> tiposGrantAplicacao);

	@Mapping(target = "cdUsuarioCriacao", ignore = true)
	@Mapping(target = "cdUsuarioAtualizacao", ignore = true)
	@Mapping(target = "dtCadastro", ignore = true)
	@Mapping(target = "dtAtualizacao", ignore = true)
	@Mapping(target = "tipoGrant", ignore = true)
	@Mapping(target = "aplicacao", ignore = true)
	@Mapping(target = "usuarioCriacao", ignore = true)
	@Mapping(target = "usuarioAtualizacao", ignore = true)
	TipoGrantAplicacao toDomainObject(TipoGrantAplicacaoInput tipoGrantAplicacaoInput);

	@Mapping(target = "cdUsuarioCriacao", ignore = true)
	@Mapping(target = "cdUsuarioAtualizacao", ignore = true)
	@Mapping(target = "dtCadastro", ignore = true)
	@Mapping(target = "dtAtualizacao", ignore = true)
	@Mapping(target = "tipoGrant", ignore = true)
	@Mapping(target = "aplicacao", ignore = true)
	@Mapping(target = "usuarioCriacao", ignore = true)
	@Mapping(target = "usuarioAtualizacao", ignore = true)
	void copyToDomainObject(TipoGrantAplicacaoInput tipoGrantAplicacaoInput,
			@MappingTarget TipoGrantAplicacao tipoGrantAplicacao);
}
