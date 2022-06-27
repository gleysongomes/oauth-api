package io.github.gleysongomes.oauth.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.UsuarioFilter;
import io.github.gleysongomes.oauth.dto.mapping.UsuarioMapping;
import io.github.gleysongomes.oauth.repository.UsuarioRepositoryCustom;
import io.github.gleysongomes.oauth.util.DatasUtil;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<UsuarioMapping> listar(UsuarioFilter usuarioFilter, PageDTO<UsuarioMapping> pageDTO) {
		StringBuilder filtro = new StringBuilder();

		if (usuarioFilter.getDtCadastroInicial() != null && usuarioFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', U.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(usuarioFilter.getDtCadastroInicial()) + "', 'DD/MM/YYYY') AND TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(usuarioFilter.getDtCadastroFinal()) + "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(usuarioFilter.getLogin())) {
			filtro.append(" AND UPPER(U.\"LOGIN\") LIKE '%" + usuarioFilter.getLogin().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(usuarioFilter.getEmail())) {
			filtro.append(" AND UPPER(U.\"EMAIL\") LIKE '%" + usuarioFilter.getEmail().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(usuarioFilter.getNome())) {
			filtro.append(" AND UPPER(U.\"NOME\") LIKE '%" + usuarioFilter.getNome().toUpperCase() + "%'");
		}

		if (usuarioFilter.getFlAtivo() != null) {
			filtro.append(" AND U.\"FL_ATIVO\" = " + usuarioFilter.getFlAtivo());
		}

		// @formatter:off
		String sql = "SELECT * \n"
				+ "FROM \n"
				+ "	(SELECT U.\"CD_USUARIO\", \n"
				+ "			U.\"LOGIN\", \n"
				+ "			U.\"EMAIL\", \n"
				+ "			U.\"NOME\", \n"
				+ "			U.\"HASH_SENHA\", \n"
				+ "			U.\"DT_CADASTRO\", \n"
				+ "			U.\"DT_ATUALIZACAO\", \n"
				+ "			U.\"CD_USUARIO_CRIACAO\", \n"
				+ "			U.\"CD_USUARIO_ATUALIZACAO\", \n"
				+ "			U.\"FL_ATIVO\", \n"
				+ "			ROW_NUMBER () OVER (ORDER BY U.\"CD_USUARIO\") LINHA \n"
				+ "		FROM \"OAUTH\".\"TB_USUARIO\" U \n"
				+ "		WHERE 1 = 1 \n"  + filtro.toString()
				+ "	) TA \n"
				+ "WHERE TA.LINHA > :pageStart \n"
				+ "	AND TA.LINHA <= :pageEnd";
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql, UsuarioMapping.class)
				.setParameter("pageStart", pageDTO.getPageStart()).setParameter("pageEnd", pageDTO.getPageEnd());

		return query.getResultList();
	}

	@Override
	public Long contar(UsuarioFilter usuarioFilter) {
		StringBuilder filtro = new StringBuilder();

		if (usuarioFilter.getDtCadastroInicial() != null && usuarioFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', U.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(usuarioFilter.getDtCadastroInicial()) + "', 'DD/MM/YYYY') AND TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(usuarioFilter.getDtCadastroFinal()) + "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(usuarioFilter.getLogin())) {
			filtro.append(" AND UPPER(U.\"LOGIN\") LIKE '%" + usuarioFilter.getLogin().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(usuarioFilter.getEmail())) {
			filtro.append(" AND UPPER(U.\"EMAIL\") LIKE '%" + usuarioFilter.getEmail().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(usuarioFilter.getNome())) {
			filtro.append(" AND UPPER(U.\"NOME\") LIKE '%" + usuarioFilter.getNome().toUpperCase() + "%'");
		}

		if (usuarioFilter.getFlAtivo() != null) {
			filtro.append(" AND U.\"FL_ATIVO\" = " + usuarioFilter.getFlAtivo());
		}

		// @formatter:off
		String sql = "SELECT COUNT(U.\"CD_USUARIO\") \n"
				+ "FROM \"OAUTH\".\"TB_USUARIO\" U \n"
				+ "WHERE 1 = 1 " + filtro.toString();
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql);

		return Long.valueOf(query.getSingleResult().toString());
	}

}
