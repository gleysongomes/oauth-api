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
import io.github.gleysongomes.oauth.dto.input.filter.TipoGrantFilter;
import io.github.gleysongomes.oauth.dto.mapping.TipoGrantMapping;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.TipoGrant;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.repository.TipoGrantRepository;
import io.github.gleysongomes.oauth.service.TipoGrantService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class TipoGrantServiceImpl implements TipoGrantService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final TipoGrantRepository tipoGrantRepository;

	private final UsuarioService usuarioService;

	public TipoGrantServiceImpl(TipoGrantRepository tipoGrantRepository, UsuarioService usuarioService) {
		this.tipoGrantRepository = tipoGrantRepository;
		this.usuarioService = usuarioService;
	}

	@Override
	@Transactional
	public TipoGrant adicionar(TipoGrant tipoGrant) {
		validarTipoGrant(tipoGrant);

		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		validarDuplicidade(tipoGrant);

		try {
			tipoGrant.setDtCadastro(new Date());
			tipoGrant.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			tipoGrant = tipoGrantRepository.save(tipoGrant);
		} catch (Exception e) {
			log.debug("Erro ao adicionar tipo grant: {}.", tipoGrant);
			throw new ApiException("Erro ao adicionar tipo grant.", e);
		}
		return tipoGrant;
	}

	private void validarTipoGrant(TipoGrant tipoGrant) {
		if (tipoGrant == null) {
			throw new ValidacaoException("Informar tipo grant.");
		}
		if (StringUtils.isBlank(tipoGrant.getNome())) {
			throw new ValidacaoException("Informar nome do tipo grant.");
		}
		if (StringUtils.isBlank(tipoGrant.getDescricao())) {
			throw new ValidacaoException("Informar descrição do tipo grant.");
		}
		if (tipoGrant.getFlAtivo() == null) {
			throw new ValidacaoException("Informar se tipo grant será ativo ou inativo.");
		}
	}

	private void validarDuplicidade(TipoGrant tipoGrant) {
		try {
			Optional<TipoGrant> tipoGrantOptional = tipoGrantRepository.findByNome(tipoGrant.getNome());

			if (tipoGrantOptional.isPresent()) {
				throw new ValidacaoException("Esse nome do tipo grant já existe.");
			}
		} catch (ValidacaoException e) {
			log.debug("Esse nome do tipo grant já existe: {}.", tipoGrant);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar nome do tipo grant: {}.", tipoGrant);
			throw new ApiException("Erro ao buscar nome do tipo grant.", e);
		}
	}

	@Override
	@Transactional
	public TipoGrant atualizar(TipoGrant tipoGrant) {
		validarTipoGrant(tipoGrant);

		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			tipoGrant.setDtAtualizacao(new Date());
			tipoGrant.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			tipoGrant = tipoGrantRepository.save(tipoGrant);
		} catch (Exception e) {
			log.debug("Erro ao atualizar tipo grant com o código: {}.", tipoGrant.getCdTipoGrant());
			throw new ApiException("Erro ao atualizar tipo grant com o código.", e);
		}
		return tipoGrant;
	}

	@Override
	public PageDTO<TipoGrantMapping> listar(TipoGrantFilter tipoGrantFilter, PageDTO<TipoGrantMapping> pageDTO) {
		try {
			List<TipoGrantMapping> tiposGrants = tipoGrantRepository.listar(tipoGrantFilter, pageDTO);

			Long total = tipoGrantRepository.contar(tipoGrantFilter);

			return new PageDTO<>(pageDTO.getNumber(), pageDTO.getSize(), total, tiposGrants);
		} catch (Exception e) {
			log.debug("Erro ao buscar lista de tipo grants com os filtros: {} e paginação: {}.", tipoGrantFilter, pageDTO);
			throw new ApiException("Erro ao buscar lista de tipo grants.", e);
		}
	}

	@Override
	public TipoGrant buscar(Long cdTipoGrant) {
		try {
			return tipoGrantRepository.findById(cdTipoGrant)
					.orElseThrow(() -> new NaoEncontradoException("Tipo grant não encontrado."));
		} catch (NaoEncontradoException e) {
			log.debug("Tipo grant {} não encontrada.", cdTipoGrant);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar tipo grant com o código: {}.", cdTipoGrant);
			throw new ApiException("Erro ao buscar tipo grant com o código.", e);
		}
	}

}
