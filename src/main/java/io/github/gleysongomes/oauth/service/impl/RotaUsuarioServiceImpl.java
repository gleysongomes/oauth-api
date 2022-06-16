package io.github.gleysongomes.oauth.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.dto.input.RotaUsuarioInput;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.RotaUsuario;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.model.primarykey.RotaUsuarioId;
import io.github.gleysongomes.oauth.repository.RotaUsuarioRepository;
import io.github.gleysongomes.oauth.service.RotaService;
import io.github.gleysongomes.oauth.service.RotaUsuarioService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class RotaUsuarioServiceImpl implements RotaUsuarioService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final RotaUsuarioRepository rotaUsuarioRepository;

	private final UsuarioService usuarioService;

	private final RotaService rotaService;

	public RotaUsuarioServiceImpl(RotaUsuarioRepository rotaUsuarioRepository, UsuarioService usuarioService,
			RotaService rotaService) {
		this.rotaUsuarioRepository = rotaUsuarioRepository;
		this.usuarioService = usuarioService;
		this.rotaService = rotaService;
	}

	@Override
	@Transactional
	public RotaUsuario adicionar(RotaUsuario rotaUsuario) {
		validarAssociacaoRotaUsuario(rotaUsuario);

		rotaService.buscar(rotaUsuario.getCdRota());
		usuarioService.buscar(rotaUsuario.getCdUsuario());
		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		validarDuplicidade(rotaUsuario);

		try {
			rotaUsuario.setDtCadastro(new Date());
			rotaUsuario.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			rotaUsuario = rotaUsuarioRepository.save(rotaUsuario);
		} catch (Exception e) {
			log.debug("Erro ao associar rota com usuário: {}.", rotaUsuario);
			throw new ApiException("Erro ao associar rota com usuário.", e);
		}
		return rotaUsuario;
	}

	private void validarDuplicidade(RotaUsuario rotaUsuario) {
		try {
			Optional<RotaUsuario> rotaUsuarioOptional = rotaUsuarioRepository
					.findById(new RotaUsuarioId(rotaUsuario.getCdRota(), rotaUsuario.getCdUsuario()));

			if (rotaUsuarioOptional.isPresent()) {
				throw new ValidacaoException("Essa associação da rota com o usuário já existe.");
			}
		} catch (ValidacaoException e) {
			log.debug("Essa associação da rota com o usuário já existe: {}.", rotaUsuario);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associação da rota com o usuário: {}.", rotaUsuario);
			throw new ApiException("Erro ao buscar associação da rota com o usuário.", e);
		}
	}

	@Override
	@Transactional
	public RotaUsuario atualizar(RotaUsuario rotaUsuario) {
		validarAssociacaoRotaUsuario(rotaUsuario);

		rotaService.buscar(rotaUsuario.getCdRota());
		usuarioService.buscar(rotaUsuario.getCdUsuario());
		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			rotaUsuario.setDtAtualizacao(new Date());
			rotaUsuario.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			rotaUsuario = rotaUsuarioRepository.save(rotaUsuario);
		} catch (Exception e) {
			log.debug("Erro ao atualizar associação de rota com usuário: {}.", rotaUsuario);
			throw new ApiException("Erro ao atualizar associação de rota com usuário.", e);
		}
		return rotaUsuario;
	}

	@Override
	public RotaUsuario buscar(Long cdRota, Long cdUsuario) {
		try {
			return rotaUsuarioRepository.findById(new RotaUsuarioId(cdRota, cdUsuario))
					.orElseThrow(() -> new NaoEncontradoException("Associação da rota com o usuário não encontrada."));
		} catch (NaoEncontradoException e) {
			log.debug("Associação da rota {} com o usuário {} não encontrada.", cdRota, cdUsuario);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associação de rota {} com usuário {}.", cdRota, cdUsuario);
			throw new ApiException("Erro ao buscar associação de rota com usuário.", e);
		}
	}

	@Override
	public List<RotaUsuario> findByCdRotaAndFlAtivaIsTrue(Long cdRota) {
		try {
			return rotaUsuarioRepository.findByCdRotaAndFlAtivaIsTrue(cdRota);
		} catch (Exception e) {
			log.debug("Erro ao listar usuários por rota: {}.", cdRota);
			throw new ApiException("Erro ao listar usuários por rota.", e);
		}
	}

	@Override
	public List<RotaUsuario> findByCdUsuarioAndFlAtivaIsTrue(Long cdUsuario) {
		try {
			return rotaUsuarioRepository.findByCdUsuarioAndFlAtivaIsTrue(cdUsuario);
		} catch (Exception e) {
			log.debug("Erro ao listar rotas por usuário: {}.", cdUsuario);
			throw new ApiException("Erro ao listar rotas por usuário.", e);
		}
	}

	private void validarAssociacaoRotaUsuario(RotaUsuario rotaUsuario) {
		if (rotaUsuario == null) {
			throw new ValidacaoException("Não existem informações para associar rota com usuário.");
		}

		if (rotaUsuario.getCdRota() == null) {
			throw new ValidacaoException("Informar código da rota.");
		}

		if (rotaUsuario.getCdUsuario() == null) {
			throw new ValidacaoException("Informar código do usuário.");
		}

		if (rotaUsuario.getFlAtiva() == null) {
			throw new ValidacaoException("Informar se associação de rota com usuário está ativa.");
		}
	}

	@Override
	public void validarAtualizacao(Long cdRota, Long cdUsuario, RotaUsuarioInput rotaUsuarioInput) {
		if (!rotaUsuarioInput.getCdRota().equals(cdRota)) {
			throw new ValidacaoException(
					"O valor do código da rota enviado na url deve ser igual ao enviado no corpo da requisição.");
		}
		if (!rotaUsuarioInput.getCdUsuario().equals(cdUsuario)) {
			throw new ValidacaoException(
					"O valor do código do usuário enviado na url deve ser igual ao enviado no corpo da requisição.");
		}
	}

}
