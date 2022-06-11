package io.github.gleysongomes.oauth.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.GrupoFilter;
import io.github.gleysongomes.oauth.dto.mapping.GrupoMapping;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.Grupo;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.repository.GrupoRepository;
import io.github.gleysongomes.oauth.repository.UsuarioRepository;
import io.github.gleysongomes.oauth.service.GrupoService;
import io.github.gleysongomes.oauth.util.ObjectMapperUtil;

@Service
@Transactional(readOnly = true)
public class GrupoServiceImpl implements GrupoService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final GrupoRepository grupoRepository;

	private final UsuarioRepository usuarioRepository;

	public GrupoServiceImpl(GrupoRepository grupoRepository, UsuarioRepository usuarioRepository) {
		this.grupoRepository = grupoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	@Transactional
	public Grupo adicionar(Grupo grupo) {
		if (grupo == null) {
			throw new ValidacaoException("Informe um grupo.");
		}

		Usuario usuarioCricao = usuarioRepository.findById(1L)
				.orElseThrow(() -> new NaoEncontradoException("Usuário de criação não encontrado."));

		try {
			grupo.setDtCadastro(new Date());
			grupo.setCdUsuarioCriacao(usuarioCricao.getCdUsuario());

			grupo = grupoRepository.save(grupo);
		} catch (Exception e) {
			log.debug("Erro ao adicionar grupo: {}.", ObjectMapperUtil.writeValueAsString(grupo));
			throw new ApiException("Erro ao adicionar grupo.", e);
		}
		return grupo;
	}

	@Override
	@Transactional
	public Grupo atualizar(Grupo grupo) {
		if (grupo == null) {
			throw new ValidacaoException("Informe um grupo.");
		}

		Usuario usuarioAtualizacao = usuarioRepository.findById(1L)
				.orElseThrow(() -> new NaoEncontradoException("Usuário de atualização não encontrado."));

		try {
			grupo.setDtAtualizacao(new Date());
			grupo.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			grupo = grupoRepository.save(grupo);
		} catch (Exception e) {
			log.debug("Erro ao atualizar grupo: {}.", ObjectMapperUtil.writeValueAsString(grupo));
			throw new ApiException(String.format("Erro ao atualizar grupo com o código: %s.", grupo.getCdGrupo()), e);
		}
		return grupo;
	}

	@Override
	public PageDTO<GrupoMapping> listar(GrupoFilter grupoFilter, PageDTO<GrupoMapping> pageDTO) {
		try {
			List<GrupoMapping> grupos = grupoRepository.listar(grupoFilter, pageDTO);

			Long total = grupoRepository.contar(grupoFilter);

			return new PageDTO<>(pageDTO.getNumber(), pageDTO.getSize(), total, grupos);
		} catch (Exception e) {
			log.debug("Erro ao buscar lista de grupos com os filtros: {} e paginação: {}.",
					ObjectMapperUtil.writeValueAsString(grupoFilter), ObjectMapperUtil.writeValueAsString(pageDTO));
			throw new ApiException("Erro ao buscar lista de grupos.", e);
		}
	}

	@Override
	public Grupo buscar(Long cdGrupo) {
		try {
			return grupoRepository.findById(cdGrupo).orElseThrow(() -> new NaoEncontradoException("Grupo não encontrado."));
		} catch (Exception e) {
			log.debug("Erro ao buscar grupo com o código: {}.", cdGrupo);
			throw new ApiException(String.format("Erro ao buscar grupo com o código: %s.", cdGrupo), e);
		}
	}

}
