package io.github.gleysongomes.oauth.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.EscopoFilter;
import io.github.gleysongomes.oauth.dto.mapping.EscopoMapping;
import io.github.gleysongomes.oauth.repository.EscopoRepositoryCustom;
import io.github.gleysongomes.oauth.util.DatasUtil;

@Repository
public class EscopoRepositoryImpl implements EscopoRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<EscopoMapping> listar(EscopoFilter escopoFilter, PageDTO<EscopoMapping> pageDTO) {
		StringBuilder filtro = new StringBuilder();

		if (escopoFilter.getDtCadastroInicial() != null && escopoFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', E.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(escopoFilter.getDtCadastroInicial()) + "', 'DD/MM/YYYY') AND TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(escopoFilter.getDtCadastroFinal()) + "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(escopoFilter.getNome())) {
			filtro.append(" AND UPPER(E.\"NOME\") LIKE '%" + escopoFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(escopoFilter.getDescricao())) {
			filtro.append(" AND UPPER(E.\"DESCRICAO\") LIKE '%" + escopoFilter.getDescricao().toUpperCase() + "%'");
		}

		if (escopoFilter.getFlAtivo() != null) {
			filtro.append(" AND E.\"FL_ATIVO\" = " + escopoFilter.getFlAtivo());
		}

		// @formatter:off
		String sql = "SELECT * \n"
				+ "FROM \n"
				+ "	(SELECT E.\"CD_ESCOPO\", \n"
				+ "			E.\"NOME\", \n"
				+ "			E.\"DESCRICAO\", \n"
				+ "			E.\"DT_CADASTRO\", \n"
				+ "			E.\"DT_ATUALIZACAO\", \n"
				+ "			E.\"CD_USUARIO_CRIACAO\", \n"
				+ "			E.\"CD_USUARIO_ATUALIZACAO\", \n"
				+ "			E.\"FL_ATIVO\", \n"
				+ "			E.\"CD_APLICACAO\", \n"
				+ "			A.\"NOME\" NM_APLICACAO, \n"
				+ "			UC.\"NOME\" NM_USUARIO_CRIACAO, \n"
				+ "			UA.\"NOME\" NM_USUARIO_ATUALIZACAO, \n"
				+ "			ROW_NUMBER () OVER () LINHA \n"
				+ "		FROM \"OAUTH\".\"TB_ESCOPO\" E \n"
				+ "		JOIN \"OAUTH\".\"TB_APLICACAO\" A ON (E.\"CD_APLICACAO\" = A.\"CD_APLICACAO\") \n"
				+ "		JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (E.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "		LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (E.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "		WHERE 1 = 1 \n"  + filtro.toString()
				+ "		ORDER BY E.\"CD_ESCOPO\") TA \n"
				+ "WHERE TA.LINHA > :pageStart \n"
				+ "	AND TA.LINHA <= :pageEnd ";
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql, EscopoMapping.class)
				.setParameter("pageStart", pageDTO.getPageStart()).setParameter("pageEnd", pageDTO.getPageEnd());

		return query.getResultList();
	}

	@Override
	public Long contar(EscopoFilter escopoFilter) {
		StringBuilder filtro = new StringBuilder();

		if (escopoFilter.getDtCadastroInicial() != null && escopoFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', E.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(escopoFilter.getDtCadastroInicial()) + "', 'DD/MM/YYYY') AND TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(escopoFilter.getDtCadastroFinal()) + "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(escopoFilter.getNome())) {
			filtro.append(" AND UPPER(E.\"NOME\") LIKE '%" + escopoFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(escopoFilter.getDescricao())) {
			filtro.append(" AND UPPER(E.\"DESCRICAO\") LIKE '%" + escopoFilter.getDescricao().toUpperCase() + "%'");
		}

		if (escopoFilter.getFlAtivo() != null) {
			filtro.append(" AND E.\"FL_ATIVO\" = " + escopoFilter.getFlAtivo());
		}

		// @formatter:off
		String sql = "SELECT COUNT(E.\"CD_ESCOPO\") \n"
				+ "FROM \"OAUTH\".\"TB_ESCOPO\" E \n"
				+ "JOIN \"OAUTH\".\"TB_APLICACAO\" A ON (E.\"CD_APLICACAO\" = A.\"CD_APLICACAO\") \n"
				+ "JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (E.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (E.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "WHERE 1 = 1 " + filtro.toString();
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql);

		return Long.valueOf(query.getSingleResult().toString());
	}

}
