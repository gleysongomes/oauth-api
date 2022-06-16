package io.github.gleysongomes.oauth.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.dto.input.TipoGrantAplicacaoInput;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.TipoGrantAplicacao;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.model.primarykey.TipoGrantAplicacaoId;
import io.github.gleysongomes.oauth.repository.TipoGrantAplicacaoRepository;
import io.github.gleysongomes.oauth.service.AplicacaoService;
import io.github.gleysongomes.oauth.service.TipoGrantAplicacaoService;
import io.github.gleysongomes.oauth.service.TipoGrantService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class TipoGrantAplicacaoServiceImpl implements TipoGrantAplicacaoService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final TipoGrantAplicacaoRepository tipoGrantAplicacaoRepository;

	private final UsuarioService usuarioService;

	private final TipoGrantService tipoGrantService;

	private final AplicacaoService aplicacaoService;

	public TipoGrantAplicacaoServiceImpl(TipoGrantAplicacaoRepository tipoGrantAplicacaoRepository,
			UsuarioService usuarioService, TipoGrantService tipoGrantService, AplicacaoService aplicacaoService) {
		this.tipoGrantAplicacaoRepository = tipoGrantAplicacaoRepository;
		this.usuarioService = usuarioService;
		this.tipoGrantService = tipoGrantService;
		this.aplicacaoService = aplicacaoService;
	}

	@Override
	@Transactional
	public TipoGrantAplicacao adicionar(TipoGrantAplicacao tipoGrantAplicacao) {
		validarAssociacaoTipoGrantAplicacao(tipoGrantAplicacao);

		tipoGrantService.buscar(tipoGrantAplicacao.getCdTipoGrant());
		aplicacaoService.buscar(tipoGrantAplicacao.getCdAplicacao());
		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		validarDuplicidade(tipoGrantAplicacao);

		try {
			tipoGrantAplicacao.setDtCadastro(new Date());
			tipoGrantAplicacao.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			tipoGrantAplicacao = tipoGrantAplicacaoRepository.save(tipoGrantAplicacao);
		} catch (Exception e) {
			log.debug("Erro ao associar tipo grant com aplicação: {}.", tipoGrantAplicacao);
			throw new ApiException("Erro ao associar tipo grant com aplicação.", e);
		}
		return tipoGrantAplicacao;
	}

	private void validarDuplicidade(TipoGrantAplicacao tipoGrantAplicacao) {
		try {
			Optional<TipoGrantAplicacao> tipoGrantAplicacaoOptional = tipoGrantAplicacaoRepository.findById(
					new TipoGrantAplicacaoId(tipoGrantAplicacao.getCdTipoGrant(), tipoGrantAplicacao.getCdAplicacao()));

			if (tipoGrantAplicacaoOptional.isPresent()) {
				throw new ValidacaoException("Essa associação do tipo grant com aplicação já existe.");
			}
		} catch (ValidacaoException e) {
			log.debug("Essa associação do tipo grant com aplicação já existe: {}.", tipoGrantAplicacao);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associação do tipo grant com aplicação: {}.", tipoGrantAplicacao);
			throw new ApiException("Erro ao buscar associação do tipo grant com aplicação.", e);
		}
	}

	@Override
	@Transactional
	public TipoGrantAplicacao atualizar(TipoGrantAplicacao tipoGrantAplicacao) {
		validarAssociacaoTipoGrantAplicacao(tipoGrantAplicacao);

		tipoGrantService.buscar(tipoGrantAplicacao.getCdTipoGrant());
		aplicacaoService.buscar(tipoGrantAplicacao.getCdAplicacao());
		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			tipoGrantAplicacao.setDtAtualizacao(new Date());
			tipoGrantAplicacao.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			tipoGrantAplicacao = tipoGrantAplicacaoRepository.save(tipoGrantAplicacao);
		} catch (Exception e) {
			log.debug("Erro ao atualizar associação de tipo grant com aplicação: {}.", tipoGrantAplicacao);
			throw new ApiException("Erro ao atualizar associação do tipo grant com aplicação.", e);
		}
		return tipoGrantAplicacao;
	}

	@Override
	public TipoGrantAplicacao buscar(Long cdTipoGrant, Long cdAplicacao) {
		try {
			return tipoGrantAplicacaoRepository.findById(new TipoGrantAplicacaoId(cdTipoGrant, cdAplicacao))
					.orElseThrow(() -> new NaoEncontradoException("Associação do tipo grant com aplicação não encontrada."));
		} catch (NaoEncontradoException e) {
			log.debug("Associação do tipo grant {} com aplicação {} não encontrada.", cdTipoGrant, cdAplicacao);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associação do tipo grant {} com aplicação {}.", cdTipoGrant, cdAplicacao);
			throw new ApiException("Erro ao buscar associação do tipo grant com aplicação.", e);
		}
	}

	@Override
	public List<TipoGrantAplicacao> findByCdTipoGrantAndFlAtivoIsTrue(Long cdTipoGrant) {
		try {
			return tipoGrantAplicacaoRepository.findByCdTipoGrantAndFlAtivoIsTrue(cdTipoGrant);
		} catch (Exception e) {
			log.debug("Erro ao listar usuários por tipo grant: {}.", cdTipoGrant);
			throw new ApiException("Erro ao listar aplicações por tipo grant.", e);
		}
	}

	@Override
	public List<TipoGrantAplicacao> findByCdAplicacaoAndFlAtivoIsTrue(Long cdAplicacao) {
		try {
			return tipoGrantAplicacaoRepository.findByCdAplicacaoAndFlAtivoIsTrue(cdAplicacao);
		} catch (Exception e) {
			log.debug("Erro ao listar grupos por aplicação: {}.", cdAplicacao);
			throw new ApiException("Erro ao listar tipos grants por aplicação.", e);
		}
	}

	private void validarAssociacaoTipoGrantAplicacao(TipoGrantAplicacao tipoGrantAplicacao) {
		if (tipoGrantAplicacao == null) {
			throw new ValidacaoException("Não existem informações para associar tipo grant com aplicação.");
		}

		if (tipoGrantAplicacao.getCdTipoGrant() == null) {
			throw new ValidacaoException("Informar código do tipo grant.");
		}

		if (tipoGrantAplicacao.getCdAplicacao() == null) {
			throw new ValidacaoException("Informar código do aplicação.");
		}

		if (tipoGrantAplicacao.getFlAtivo() == null) {
			throw new ValidacaoException("Informar se associação de tipo grant com aplicação será ativo ou inativo.");
		}
	}

	@Override
	public void validarAtualizacao(Long cdTipoGrant, Long cdAplicacao, TipoGrantAplicacaoInput tipoGrantAplicacaoInput) {
		if (!tipoGrantAplicacaoInput.getCdTipoGrant().equals(cdTipoGrant)) {
			throw new ValidacaoException(
					"O valor do código do tipo grant enviado na url deve ser igual ao enviado no corpo da requisição.");
		}
		if (!tipoGrantAplicacaoInput.getCdAplicacao().equals(cdAplicacao)) {
			throw new ValidacaoException(
					"O valor do código da aplicação enviado na url deve ser igual ao enviado no corpo da requisição.");
		}
	}

}
