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
		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usu??rio de cria????o n??o encontrado.");

		validarDuplicidade(tipoGrantAplicacao);

		try {
			tipoGrantAplicacao.setDtCadastro(new Date());
			tipoGrantAplicacao.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			tipoGrantAplicacao = tipoGrantAplicacaoRepository.save(tipoGrantAplicacao);
		} catch (Exception e) {
			log.debug("Erro ao associar tipo grant com aplica????o: {}.", tipoGrantAplicacao);
			throw new ApiException("Erro ao associar tipo grant com aplica????o.", e);
		}
		return tipoGrantAplicacao;
	}

	private void validarDuplicidade(TipoGrantAplicacao tipoGrantAplicacao) {
		try {
			Optional<TipoGrantAplicacao> tipoGrantAplicacaoOptional = tipoGrantAplicacaoRepository.findById(
					new TipoGrantAplicacaoId(tipoGrantAplicacao.getCdTipoGrant(), tipoGrantAplicacao.getCdAplicacao()));

			if (tipoGrantAplicacaoOptional.isPresent()) {
				throw new ValidacaoException("Essa associa????o do tipo grant com aplica????o j?? existe.");
			}
		} catch (ValidacaoException e) {
			log.debug("Essa associa????o do tipo grant com aplica????o j?? existe: {}.", tipoGrantAplicacao);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associa????o do tipo grant com aplica????o: {}.", tipoGrantAplicacao);
			throw new ApiException("Erro ao buscar associa????o do tipo grant com aplica????o.", e);
		}
	}

	@Override
	@Transactional
	public TipoGrantAplicacao atualizar(TipoGrantAplicacao tipoGrantAplicacao) {
		validarAssociacaoTipoGrantAplicacao(tipoGrantAplicacao);

		tipoGrantService.buscar(tipoGrantAplicacao.getCdTipoGrant());
		aplicacaoService.buscar(tipoGrantAplicacao.getCdAplicacao());
		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usu??rio de atualiza????o n??o encontrado.");

		try {
			tipoGrantAplicacao.setDtAtualizacao(new Date());
			tipoGrantAplicacao.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			tipoGrantAplicacao = tipoGrantAplicacaoRepository.save(tipoGrantAplicacao);
		} catch (Exception e) {
			log.debug("Erro ao atualizar associa????o de tipo grant com aplica????o: {}.", tipoGrantAplicacao);
			throw new ApiException("Erro ao atualizar associa????o do tipo grant com aplica????o.", e);
		}
		return tipoGrantAplicacao;
	}

	@Override
	public TipoGrantAplicacao buscar(Long cdTipoGrant, Long cdAplicacao) {
		try {
			return tipoGrantAplicacaoRepository.findById(new TipoGrantAplicacaoId(cdTipoGrant, cdAplicacao))
					.orElseThrow(() -> new NaoEncontradoException("Associa????o do tipo grant com aplica????o n??o encontrada."));
		} catch (NaoEncontradoException e) {
			log.debug("Associa????o do tipo grant {} com aplica????o {} n??o encontrada.", cdTipoGrant, cdAplicacao);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associa????o do tipo grant {} com aplica????o {}.", cdTipoGrant, cdAplicacao);
			throw new ApiException("Erro ao buscar associa????o do tipo grant com aplica????o.", e);
		}
	}

	@Override
	public List<TipoGrantAplicacao> findByCdTipoGrantAndFlAtivoIsTrue(Long cdTipoGrant) {
		try {
			return tipoGrantAplicacaoRepository.findByCdTipoGrantAndFlAtivoIsTrue(cdTipoGrant);
		} catch (Exception e) {
			log.debug("Erro ao listar usu??rios por tipo grant: {}.", cdTipoGrant);
			throw new ApiException("Erro ao listar aplica????es por tipo grant.", e);
		}
	}

	@Override
	public List<TipoGrantAplicacao> findByCdAplicacaoAndFlAtivoIsTrue(Long cdAplicacao) {
		try {
			return tipoGrantAplicacaoRepository.findByCdAplicacaoAndFlAtivoIsTrue(cdAplicacao);
		} catch (Exception e) {
			log.debug("Erro ao listar grupos por aplica????o: {}.", cdAplicacao);
			throw new ApiException("Erro ao listar tipos grants por aplica????o.", e);
		}
	}

	private void validarAssociacaoTipoGrantAplicacao(TipoGrantAplicacao tipoGrantAplicacao) {
		if (tipoGrantAplicacao == null) {
			throw new ValidacaoException("N??o existem informa????es para associar tipo grant com aplica????o.");
		}

		if (tipoGrantAplicacao.getCdTipoGrant() == null) {
			throw new ValidacaoException("Informar c??digo do tipo grant.");
		}

		if (tipoGrantAplicacao.getCdAplicacao() == null) {
			throw new ValidacaoException("Informar c??digo do aplica????o.");
		}

		if (tipoGrantAplicacao.getFlAtivo() == null) {
			throw new ValidacaoException("Informar se associa????o de tipo grant com aplica????o ser?? ativo ou inativo.");
		}
	}

	@Override
	public void validarAtualizacao(Long cdTipoGrant, Long cdAplicacao, TipoGrantAplicacaoInput tipoGrantAplicacaoInput) {
		if (!tipoGrantAplicacaoInput.getCdTipoGrant().equals(cdTipoGrant)) {
			throw new ValidacaoException(
					"O valor do c??digo do tipo grant enviado na url deve ser igual ao enviado no corpo da requisi????o.");
		}
		if (!tipoGrantAplicacaoInput.getCdAplicacao().equals(cdAplicacao)) {
			throw new ValidacaoException(
					"O valor do c??digo da aplica????o enviado na url deve ser igual ao enviado no corpo da requisi????o.");
		}
	}

}
