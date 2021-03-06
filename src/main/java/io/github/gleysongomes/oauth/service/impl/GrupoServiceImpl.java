package io.github.gleysongomes.oauth.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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
import io.github.gleysongomes.oauth.security.ApiSecurity;
import io.github.gleysongomes.oauth.service.GrupoService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class GrupoServiceImpl implements GrupoService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final GrupoRepository grupoRepository;

	private final UsuarioService usuarioService;

	private final ApiSecurity apiSecurity;

	public GrupoServiceImpl(GrupoRepository grupoRepository, UsuarioService usuarioService, ApiSecurity apiSecurity) {
		this.grupoRepository = grupoRepository;
		this.usuarioService = usuarioService;
		this.apiSecurity = apiSecurity;
	}

	@Override
	@Transactional
	public Grupo adicionar(Grupo grupo) {
		validarGrupo(grupo);

		Usuario usuarioCriacao = usuarioService.buscarPorLogin(apiSecurity.getLoginUsuarioLogado(),
				"Usuário de criação não encontrado.");

		validarDuplicidade(grupo);

		try {
			grupo.setDtCadastro(new Date());
			grupo.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			grupo = grupoRepository.save(grupo);
		} catch (Exception e) {
			log.debug("Erro ao adicionar grupo: {}.", grupo);
			throw new ApiException("Erro ao adicionar grupo.", e);
		}
		return grupo;
	}

	private void validarGrupo(Grupo grupo) {
		if (grupo == null) {
			throw new ValidacaoException("Informar grupo.");
		}
		if (StringUtils.isBlank(grupo.getNome())) {
			throw new ValidacaoException("Informar nome do grupo.");
		}
		if (StringUtils.isBlank(grupo.getDescricao())) {
			throw new ValidacaoException("Informar descrição do grupo.");
		}
		if (grupo.getFlAtivo() == null) {
			throw new ValidacaoException("Informar se grupo será ativo ou inativo.");
		}
	}

	private void validarDuplicidade(Grupo grupo) {
		try {
			Optional<Grupo> grupoOptional = grupoRepository.findByNome(grupo.getNome());

			if (grupoOptional.isPresent()) {
				throw new ValidacaoException("Esse nome do grupo já existe.");
			}
		} catch (ValidacaoException e) {
			log.debug("Esse nome do grupo já existe: {}.", grupo);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar grupo pelo nome: {}.", grupo);
			throw new ApiException("Erro ao buscar grupo pelo nome.", e);
		}
	}

	@Override
	@Transactional
	public Grupo atualizar(Grupo grupo) {
		validarGrupo(grupo);

		Usuario usuarioAtualizacao = usuarioService.buscarPorLogin(apiSecurity.getLoginUsuarioLogado(),
				"Usuário de atualização não encontrado.");

		try {
			grupo.setDtAtualizacao(new Date());
			grupo.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			grupo = grupoRepository.save(grupo);
		} catch (Exception e) {
			log.debug("Erro ao atualizar grupo: {}.", grupo);
			throw new ApiException("Erro ao atualizar grupo com o código.", e);
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
			log.debug("Erro ao buscar lista de grupos com os filtros: {} e paginação: {}.", grupoFilter, pageDTO);
			throw new ApiException("Erro ao buscar lista de grupos.", e);
		}
	}

	@Override
	public Grupo buscar(Long cdGrupo) {
		try {
			return grupoRepository.findById(cdGrupo).orElseThrow(() -> new NaoEncontradoException("Grupo não encontrado."));
		} catch (NaoEncontradoException e) {
			log.debug("Grupo {} não encontrado.", cdGrupo);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar grupo com o código: {}.", cdGrupo);
			throw new ApiException("Erro ao buscar grupo com o código.", e);
		}
	}

	@Override
	public boolean isOwner(Long cdGrupo) {
		Grupo grupo = buscar(cdGrupo);
		Usuario usuarioLogado = usuarioService.buscarPorLogin(apiSecurity.getLoginUsuarioLogado());
		if (grupo.getCdUsuarioCriacao() != null && grupo.getCdUsuarioCriacao().equals(usuarioLogado.getCdUsuario())) {
			return true;
		}
		return false;
	}

}
