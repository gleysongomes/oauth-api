package io.github.gleysongomes.oauth.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.RecursoFilter;
import io.github.gleysongomes.oauth.dto.mapping.RecursoMapping;
import io.github.gleysongomes.oauth.repository.RecursoRepositoryCustom;
import io.github.gleysongomes.oauth.util.DatasUtil;

@Repository
public class RecursoRepositoryImpl implements RecursoRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<RecursoMapping> listar(RecursoFilter recursoFilter, PageDTO<RecursoMapping> pageDTO) {
		StringBuilder filtro = new StringBuilder();

		if (recursoFilter.getDtCadastroInicial() != null && recursoFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', R.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(recursoFilter.getDtCadastroInicial()) + "', 'DD/MM/YYYY') AND TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(recursoFilter.getDtCadastroFinal()) + "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(recursoFilter.getNome())) {
			filtro.append(" AND UPPER(R.\"NOME\") LIKE '%" + recursoFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(recursoFilter.getDescricao())) {
			filtro.append(" AND UPPER(R.\"DESCRICAO\") LIKE '%" + recursoFilter.getDescricao().toUpperCase() + "%'");
		}

		if (recursoFilter.getFlAtivo() != null) {
			filtro.append(" AND R.\"FL_ATIVO\" = " + recursoFilter.getFlAtivo());
		}

		// @formatter:off
		String sql = "SELECT * \n"
				+ "FROM \n"
				+ "	(SELECT R.\"CD_RECURSO\", \n"
				+ "			R.\"NOME\", \n"
				+ "			R.\"DESCRICAO\", \n"
				+ "			R.\"DT_CADASTRO\", \n"
				+ "			R.\"DT_ATUALIZACAO\", \n"
				+ "			R.\"CD_USUARIO_CRIACAO\", \n"
				+ "			R.\"CD_USUARIO_ATUALIZACAO\", \n"
				+ "			R.\"FL_ATIVO\", \n"
				+ "			R.\"CD_APLICACAO\", \n"
				+ "			A.\"NOME\" NM_APLICACAO, \n"
				+ "			UC.\"NOME\" NM_USUARIO_CRIACAO, \n"
				+ "			UA.\"NOME\" NM_USUARIO_ATUALIZACAO, \n"
				+ "			ROW_NUMBER () OVER () LINHA \n"
				+ "		FROM \"OAUTH\".\"TB_RECURSO\" R \n"
				+ "		JOIN \"OAUTH\".\"TB_APLICACAO\" A ON (R.\"CD_APLICACAO\" = A.\"CD_APLICACAO\") \n"
				+ "		JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (R.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "		LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (R.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "		WHERE 1 = 1 \n"  + filtro.toString()
				+ "		ORDER BY R.\"CD_RECURSO\") TA \n"
				+ "WHERE TA.LINHA > :pageStart \n"
				+ "	AND TA.LINHA <= :pageEnd ";
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql, RecursoMapping.class)
				.setParameter("pageStart", pageDTO.getPageStart()).setParameter("pageEnd", pageDTO.getPageEnd());

		return query.getResultList();
	}

	@Override
	public Long contar(RecursoFilter recursoFilter) {
		StringBuilder filtro = new StringBuilder();

		if (recursoFilter.getDtCadastroInicial() != null && recursoFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', R.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(recursoFilter.getDtCadastroInicial()) + "', 'DD/MM/YYYY') AND TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(recursoFilter.getDtCadastroFinal()) + "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(recursoFilter.getNome())) {
			filtro.append(" AND UPPER(R.\"NOME\") LIKE '%" + recursoFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(recursoFilter.getDescricao())) {
			filtro.append(" AND UPPER(R.\"DESCRICAO\") LIKE '%" + recursoFilter.getDescricao().toUpperCase() + "%'");
		}

		if (recursoFilter.getFlAtivo() != null) {
			filtro.append(" AND R.\"FL_ATIVO\" = " + recursoFilter.getFlAtivo());
		}

		// @formatter:off
		String sql = "SELECT COUNT(R.\"CD_RECURSO\") \n"
				+ "FROM \"OAUTH\".\"TB_RECURSO\" R \n"
				+ "JOIN \"OAUTH\".\"TB_APLICACAO\" A ON (R.\"CD_APLICACAO\" = A.\"CD_APLICACAO\") \n"
				+ "JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (R.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (R.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "WHERE 1 = 1 " + filtro.toString();
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql);

		return Long.valueOf(query.getSingleResult().toString());
	}

}
