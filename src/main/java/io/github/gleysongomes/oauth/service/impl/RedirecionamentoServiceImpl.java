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
import io.github.gleysongomes.oauth.dto.input.filter.RedirecionamentoFilter;
import io.github.gleysongomes.oauth.dto.mapping.RedirecionamentoMapping;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.Redirecionamento;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.repository.RedirecionamentoRepository;
import io.github.gleysongomes.oauth.service.AplicacaoService;
import io.github.gleysongomes.oauth.service.RedirecionamentoService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class RedirecionamentoServiceImpl implements RedirecionamentoService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final RedirecionamentoRepository redirecionamentoRepository;

	private final UsuarioService usuarioService;

	private final AplicacaoService aplicacaoService;

	public RedirecionamentoServiceImpl(RedirecionamentoRepository redirecionamentoRepository, UsuarioService usuarioService,
			AplicacaoService aplicacaoService) {
		this.redirecionamentoRepository = redirecionamentoRepository;
		this.usuarioService = usuarioService;
		this.aplicacaoService = aplicacaoService;
	}

	@Override
	@Transactional
	public Redirecionamento adicionar(Redirecionamento redirecionamento) {
		validarRedirecionamento(redirecionamento);

		aplicacaoService.buscar(redirecionamento.getCdAplicacao());

		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		validarDuplicidade(redirecionamento);

		try {
			redirecionamento.setDtCadastro(new Date());
			redirecionamento.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			redirecionamento = redirecionamentoRepository.save(redirecionamento);
		} catch (Exception e) {
			log.debug("Erro ao adicionar redirecionamento: {}.", redirecionamento);
			throw new ApiException("Erro ao adicionar redirecionamento.", e);
		}
		return redirecionamento;
	}

	private void validarRedirecionamento(Redirecionamento redirecionamento) {
		if (redirecionamento == null) {
			throw new ValidacaoException("Informar redirecionamento.");
		}
		if (StringUtils.isBlank(redirecionamento.getUrl())) {
			throw new ValidacaoException("Informar url do redirecionamento.");
		}
		if (StringUtils.isBlank(redirecionamento.getDescricao())) {
			throw new ValidacaoException("Informar descrição do redirecionamento.");
		}
		if (redirecionamento.getCdAplicacao() == null) {
			throw new ValidacaoException("Informar código da aplicação.");
		}
		if (redirecionamento.getFlAtivo() == null) {
			throw new ValidacaoException("Informar se redirecionamento será ativo ou inativo.");
		}
	}

	private void validarDuplicidade(Redirecionamento redirecionamento) {
		try {
			Optional<Redirecionamento> redirecionamentoOptional = redirecionamentoRepository
					.findByUrlAndCdAplicacao(redirecionamento.getUrl(), redirecionamento.getCdAplicacao());

			if (redirecionamentoOptional.isPresent()) {
				throw new ValidacaoException("Essa url de redirecionamento para essa aplicação já existe.");
			}
		} catch (ValidacaoException e) {
			log.debug("Essa url de redirecionamento para essa aplicação já existe: {}.", redirecionamento);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar redirecionamento pela url e código da aplicação: {}.", redirecionamento);
			throw new ApiException("Erro ao buscar redirecionamento pela url e código da aplicação.", e);
		}
	}

	@Override
	@Transactional
	public Redirecionamento atualizar(Redirecionamento redirecionamento) {
		validarRedirecionamento(redirecionamento);

		aplicacaoService.buscar(redirecionamento.getCdAplicacao());

		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			redirecionamento.setDtAtualizacao(new Date());
			redirecionamento.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			redirecionamento = redirecionamentoRepository.save(redirecionamento);
		} catch (Exception e) {
			log.debug("Erro ao atualizar redirecionamento: {}.", redirecionamento);
			throw new ApiException("Erro ao atualizar redirecionamento.", e);
		}
		return redirecionamento;
	}

	@Override
	public PageDTO<RedirecionamentoMapping> listar(RedirecionamentoFilter redirecionamentoFilter,
			PageDTO<RedirecionamentoMapping> pageDTO) {
		try {
			List<RedirecionamentoMapping> redirecionamentos = redirecionamentoRepository.listar(redirecionamentoFilter,
					pageDTO);

			Long total = redirecionamentoRepository.contar(redirecionamentoFilter);

			return new PageDTO<>(pageDTO.getNumber(), pageDTO.getSize(), total, redirecionamentos);
		} catch (Exception e) {
			log.debug("Erro ao buscar lista de redirecionamentos com os filtros: {} e paginação: {}.",
					redirecionamentoFilter, pageDTO);
			throw new ApiException("Erro ao buscar lista de redirecionamentos.", e);
		}
	}

	@Override
	public Redirecionamento buscar(Long cdRedirecionamento) {
		try {
			return redirecionamentoRepository.findById(cdRedirecionamento)
					.orElseThrow(() -> new NaoEncontradoException("Redirecionamento não encontrado."));
		} catch (NaoEncontradoException e) {
			log.debug("Redirecionamento {} não encontrado.", cdRedirecionamento);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar redirecionamento com o código: {}.", cdRedirecionamento);
			throw new ApiException("Erro ao buscar redirecionamento com o código.", e);
		}
	}

}
