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
import io.github.gleysongomes.oauth.dto.input.filter.RecursoFilter;
import io.github.gleysongomes.oauth.dto.mapping.RecursoMapping;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.Recurso;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.repository.RecursoRepository;
import io.github.gleysongomes.oauth.service.AplicacaoService;
import io.github.gleysongomes.oauth.service.RecursoService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class RecursoServiceImpl implements RecursoService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final RecursoRepository recursoRepository;

	private final UsuarioService usuarioService;

	private final AplicacaoService aplicacaoService;

	public RecursoServiceImpl(RecursoRepository recursoRepository, UsuarioService usuarioService,
			AplicacaoService aplicacaoService) {
		this.recursoRepository = recursoRepository;
		this.usuarioService = usuarioService;
		this.aplicacaoService = aplicacaoService;
	}

	@Override
	@Transactional
	public Recurso adicionar(Recurso recurso) {
		validarRecurso(recurso);

		aplicacaoService.buscar(recurso.getCdAplicacao());

		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		validarDuplicidade(recurso);

		try {
			recurso.setDtCadastro(new Date());
			recurso.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			recurso = recursoRepository.save(recurso);
		} catch (Exception e) {
			log.debug("Erro ao adicionar recurso: {}.", recurso);
			throw new ApiException("Erro ao adicionar recurso.", e);
		}
		return recurso;
	}

	private void validarRecurso(Recurso recurso) {
		if (recurso == null) {
			throw new ValidacaoException("Informar recurso.");
		}
		if (StringUtils.isBlank(recurso.getNome())) {
			throw new ValidacaoException("Informar nome do recurso.");
		}
		if (StringUtils.isBlank(recurso.getDescricao())) {
			throw new ValidacaoException("Informar descrição do recurso.");
		}
		if (recurso.getCdAplicacao() == null) {
			throw new ValidacaoException("Informar código da aplicação.");
		}
		if (recurso.getFlAtivo() == null) {
			throw new ValidacaoException("Informar se recurso será ativo ou inativo.");
		}
	}

	private void validarDuplicidade(Recurso recurso) {
		try {
			Optional<Recurso> recursoOptional = recursoRepository.findByNomeAndCdAplicacao(recurso.getNome(),
					recurso.getCdAplicacao());

			if (recursoOptional.isPresent()) {
				throw new ValidacaoException("Esse nome de recurso para essa aplicação já existe.");
			}
		} catch (ValidacaoException e) {
			log.debug("Esse nome de recurso para essa aplicação já existe: {}.", recurso);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar recurso pelo nome e código da aplicação: {}.", recurso);
			throw new ApiException("Erro ao buscar recurso pelo nome e código da aplicação.", e);
		}
	}

	@Override
	@Transactional
	public Recurso atualizar(Recurso recurso) {
		validarRecurso(recurso);

		aplicacaoService.buscar(recurso.getCdAplicacao());

		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			recurso.setDtAtualizacao(new Date());
			recurso.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			recurso = recursoRepository.save(recurso);
		} catch (Exception e) {
			log.debug("Erro ao atualizar recurso: {}.", recurso);
			throw new ApiException("Erro ao atualizar recurso.", e);
		}
		return recurso;
	}

	@Override
	public PageDTO<RecursoMapping> listar(RecursoFilter recursoFilter, PageDTO<RecursoMapping> pageDTO) {
		try {
			List<RecursoMapping> recursos = recursoRepository.listar(recursoFilter, pageDTO);

			Long total = recursoRepository.contar(recursoFilter);

			return new PageDTO<>(pageDTO.getNumber(), pageDTO.getSize(), total, recursos);
		} catch (Exception e) {
			log.debug("Erro ao buscar lista de recursos com os filtros: {} e paginação: {}.", recursoFilter, pageDTO);
			throw new ApiException("Erro ao buscar lista de recursos.", e);
		}
	}

	@Override
	public Recurso buscar(Long cdRecurso) {
		try {
			return recursoRepository.findById(cdRecurso)
					.orElseThrow(() -> new NaoEncontradoException("Recurso não encontrado."));
		} catch (NaoEncontradoException e) {
			log.debug("Recurso {} não encontrado.", cdRecurso);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar recurso com o código: {}.", cdRecurso);
			throw new ApiException("Erro ao buscar recurso com o código.", e);
		}
	}

}
