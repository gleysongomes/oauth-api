package io.github.gleysongomes.oauth.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.gleysongomes.oauth.dto.input.RotaGrupoInput;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.exception.NaoEncontradoException;
import io.github.gleysongomes.oauth.exception.ValidacaoException;
import io.github.gleysongomes.oauth.model.RotaGrupo;
import io.github.gleysongomes.oauth.model.Usuario;
import io.github.gleysongomes.oauth.model.primarykey.RotaGrupoId;
import io.github.gleysongomes.oauth.repository.RotaGrupoRepository;
import io.github.gleysongomes.oauth.service.GrupoService;
import io.github.gleysongomes.oauth.service.RotaGrupoService;
import io.github.gleysongomes.oauth.service.RotaService;
import io.github.gleysongomes.oauth.service.UsuarioService;

@Service
@Transactional(readOnly = true)
public class RotaGrupoServiceImpl implements RotaGrupoService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final RotaGrupoRepository rotaGrupoRepository;

	private final UsuarioService usuarioService;

	private final GrupoService grupoService;

	private final RotaService rotaService;

	public RotaGrupoServiceImpl(RotaGrupoRepository rotaGrupoRepository, UsuarioService usuarioService,
			GrupoService grupoService, RotaService rotaService) {
		this.rotaGrupoRepository = rotaGrupoRepository;
		this.usuarioService = usuarioService;
		this.grupoService = grupoService;
		this.rotaService = rotaService;
	}

	@Override
	@Transactional
	public RotaGrupo adicionar(RotaGrupo rotaGrupo) {
		validarAssociacaoRotaGrupo(rotaGrupo);

		rotaService.buscar(rotaGrupo.getCdRota());
		grupoService.buscar(rotaGrupo.getCdGrupo());
		Usuario usuarioCriacao = usuarioService.buscar(1L, "Usuário de criação não encontrado.");

		validarDuplicidade(rotaGrupo);

		try {
			rotaGrupo.setDtCadastro(new Date());
			rotaGrupo.setCdUsuarioCriacao(usuarioCriacao.getCdUsuario());

			rotaGrupo = rotaGrupoRepository.save(rotaGrupo);
		} catch (Exception e) {
			log.debug("Erro ao associar rota com grupo: {}.", rotaGrupo);
			throw new ApiException("Erro ao associar rota com grupo.", e);
		}
		return rotaGrupo;
	}

	private void validarDuplicidade(RotaGrupo rotaGrupo) {
		try {
			Optional<RotaGrupo> rotaGrupoOptional = rotaGrupoRepository
					.findById(new RotaGrupoId(rotaGrupo.getCdRota(), rotaGrupo.getCdGrupo()));

			if (rotaGrupoOptional.isPresent()) {
				throw new ValidacaoException("Essa associação da rota com o grupo já existe.");
			}
		} catch (ValidacaoException e) {
			log.debug("Essa associação da rota com o grupo já existe: {}.", rotaGrupo);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associação da rota com o grupo: {}.", rotaGrupo);
			throw new ApiException("Erro ao buscar associação da rota com o grupo.", e);
		}
	}

	@Override
	@Transactional
	public RotaGrupo atualizar(RotaGrupo rotaGrupo) {
		validarAssociacaoRotaGrupo(rotaGrupo);

		rotaService.buscar(rotaGrupo.getCdRota());
		grupoService.buscar(rotaGrupo.getCdGrupo());
		Usuario usuarioAtualizacao = usuarioService.buscar(1L, "Usuário de atualização não encontrado.");

		try {
			rotaGrupo.setDtAtualizacao(new Date());
			rotaGrupo.setCdUsuarioAtualizacao(usuarioAtualizacao.getCdUsuario());

			rotaGrupo = rotaGrupoRepository.save(rotaGrupo);
		} catch (Exception e) {
			log.debug("Erro ao atualizar associação de rota com grupo: {}.", rotaGrupo);
			throw new ApiException("Erro ao atualizar associação de rota com grupo.", e);
		}
		return rotaGrupo;
	}

	@Override
	public RotaGrupo buscar(Long cdRota, Long cdGrupo) {
		try {
			return rotaGrupoRepository.findById(new RotaGrupoId(cdRota, cdGrupo))
					.orElseThrow(() -> new NaoEncontradoException("Associação da rota com o grupo não encontrada."));
		} catch (NaoEncontradoException e) {
			log.debug("Associação da rota {} com o grupo {} não encontrada.", cdRota, cdGrupo);
			throw e;
		} catch (Exception e) {
			log.debug("Erro ao buscar associação de rota {} com grupo {}.", cdRota, cdGrupo);
			throw new ApiException("Erro ao buscar associação de rota com grupo.", e);
		}
	}

	@Override
	public List<RotaGrupo> findByCdRotaAndFlAtivaIsTrue(Long cdRota) {
		try {
			return rotaGrupoRepository.findByCdRotaAndFlAtivaIsTrue(cdRota);
		} catch (Exception e) {
			log.debug("Erro ao listar grupos por rota: {}.", cdRota);
			throw new ApiException("Erro ao listar grupos por rota.", e);
		}
	}

	@Override
	public List<RotaGrupo> findByCdGrupoAndFlAtivaIsTrue(Long cdGrupo) {
		try {
			return rotaGrupoRepository.findByCdGrupoAndFlAtivaIsTrue(cdGrupo);
		} catch (Exception e) {
			log.debug("Erro ao listar rotas por grupo: {}.", cdGrupo);
			throw new ApiException("Erro ao listar rotas por grupo.", e);
		}
	}

	private void validarAssociacaoRotaGrupo(RotaGrupo rotaGrupo) {
		if (rotaGrupo == null) {
			throw new ValidacaoException("Não existem informações para associar rota com grupo.");
		}

		if (rotaGrupo.getCdRota() == null) {
			throw new ValidacaoException("Informar código da rota.");
		}

		if (rotaGrupo.getCdGrupo() == null) {
			throw new ValidacaoException("Informar código do grupo.");
		}

		if (rotaGrupo.getFlAtiva() == null) {
			throw new ValidacaoException("Informar se associação de rota com grupo está ativa.");
		}
	}

	@Override
	public void validarAtualizacao(Long cdRota, Long cdGrupo, RotaGrupoInput rotaGrupoInput) {
		if (!rotaGrupoInput.getCdRota().equals(cdRota)) {
			throw new ValidacaoException(
					"O valor do código da rota enviado na url deve ser igual ao enviado no corpo da requisição.");
		}
		if (!rotaGrupoInput.getCdGrupo().equals(cdGrupo)) {
			throw new ValidacaoException(
					"O valor do código do grupo enviado na url deve ser igual ao enviado no corpo da requisição.");
		}
	}

}
