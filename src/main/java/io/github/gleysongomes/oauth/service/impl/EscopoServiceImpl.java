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
import io.github.gleysongomes.oauth.dto.input.filter.EscopoFilter;
import io.github.gleysongomes.oauth.dto.mapping.EscopoMapping;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.Escopo;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.repository.EscopoRepository;
import io.github.gleysongomes.oauth.service.EscopoService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class EscopoServiceImpl implements EscopoService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final EscopoRepository escopoRepository;

	private final UsuarioService usuarioService;

	public EscopoServiceImpl(EscopoRepository escopoRepository, UsuarioService usuarioService) {
		this.escopoRepository = escopoRepository;
		this.usuarioService = usuarioService;
	}

	@Override
	@Transactional
	public Escopo adicionar(Escopo escopo) {
		validarEscopo(escopo);

		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		validarDuplicidade(escopo);

		try {
			escopo.setDtCadastro(new Date());
			escopo.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			escopo = escopoRepository.save(escopo);
		} catch (Exception e) {
			log.debug("Erro ao adicionar escopo: {}.", escopo.toString());
			throw new ApiException("Erro ao adicionar escopo.", e);
		}
		return escopo;
	}

	private void validarEscopo(Escopo escopo) {
		if (escopo == null) {
			throw new ValidacaoException("Informar escopo.");
		}
		if (StringUtils.isBlank(escopo.getNome())) {
			throw new ValidacaoException("Informar nome do escopo.");
		}
		if (StringUtils.isBlank(escopo.getDescricao())) {
			throw new ValidacaoException("Informar descrição do escopo.");
		}
		if (escopo.getCdAplicacao() == null) {
			throw new ValidacaoException("Informar código da aplicação.");
		}
		if (escopo.getFlAtivo() == null) {
			throw new ValidacaoException("Informar se escopo será ativo ou inativo.");
		}
	}

	private void validarDuplicidade(Escopo escopo) {
		try {
			Optional<Escopo> escopoOptional = escopoRepository.findByNomeAndCdAplicacao(escopo.getNome(),
					escopo.getCdAplicacao());

			if (escopoOptional.isPresent()) {
				throw new ValidacaoException("Esse nome de escopo para essa aplicação já existe.");
			}
		} catch (ValidacaoException e) {
			log.debug("Esse nome de escopo para essa aplicação já existe: {}.", escopo);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar escopo pelo nome e código da aplicação: {}.", escopo);
			throw new ApiException("Erro ao buscar escopo pelo nome e código da aplicação.", e);
		}
	}

	@Override
	@Transactional
	public Escopo atualizar(Escopo escopo) {
		validarEscopo(escopo);

		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			escopo.setDtAtualizacao(new Date());
			escopo.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			escopo = escopoRepository.save(escopo);
		} catch (Exception e) {
			log.debug("Erro ao atualizar escopo: {}.", escopo);
			throw new ApiException("Erro ao atualizar escopo.", e);
		}
		return escopo;
	}

	@Override
	public PageDTO<EscopoMapping> listar(EscopoFilter escopoFilter, PageDTO<EscopoMapping> pageDTO) {
		try {
			List<EscopoMapping> escopos = escopoRepository.listar(escopoFilter, pageDTO);

			Long total = escopoRepository.contar(escopoFilter);

			return new PageDTO<>(pageDTO.getNumber(), pageDTO.getSize(), total, escopos);
		} catch (Exception e) {
			log.debug("Erro ao buscar lista de escopos com os filtros: {} e paginação: {}.", escopoFilter, pageDTO);
			throw new ApiException("Erro ao buscar lista de escopos.", e);
		}
	}

	@Override
	public Escopo buscar(Long cdEscopo) {
		try {
			return escopoRepository.findById(cdEscopo)
					.orElseThrow(() -> new NaoEncontradoException("Escopo não encontrado."));
		} catch (NaoEncontradoException e) {
			log.debug("Escopo {} não encontrado.", cdEscopo);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar escopo com o código: {}.", cdEscopo);
			throw new ApiException("Erro ao buscar escopo pelo código.", e);
		}
	}

}
