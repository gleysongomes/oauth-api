package io.github.gleysongomes.oauth.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.AplicacaoFilter;
import io.github.gleysongomes.oauth.dto.mapping.AplicacaoMapping;
import io.github.gleysongomes.oauth.repository.AplicacaoRepositoryCustom;
import io.github.gleysongomes.oauth.util.DatasUtil;

@Repository
public class AplicacaoRepositoryImpl implements AplicacaoRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<AplicacaoMapping> listar(AplicacaoFilter aplicacaoFilter, PageDTO<AplicacaoMapping> pageDTO) {
		StringBuilder filtro = new StringBuilder();

		if (aplicacaoFilter.getDtCadastroInicial() != null && aplicacaoFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', A.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(aplicacaoFilter.getDtCadastroInicial())
					+ "', 'DD/MM/YYYY') AND TO_DATE('" + DatasUtil.formataDataDiaMesAno(aplicacaoFilter.getDtCadastroFinal())
					+ "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(aplicacaoFilter.getNome())) {
			filtro.append(" AND UPPER(A.\"NOME\") LIKE '%" + aplicacaoFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(aplicacaoFilter.getDescricao())) {
			filtro.append(" AND UPPER(A.\"DESCRICAO\") LIKE '%" + aplicacaoFilter.getDescricao().toUpperCase() + "%'");
		}

		if (aplicacaoFilter.getFlAtiva() != null) {
			filtro.append(" AND A.\"FL_ATIVA\" = " + aplicacaoFilter.getFlAtiva());
		}

		// @formatter:off
		String sql = "SELECT * \n"
				+ "FROM \n"
				+ "	(SELECT A.\"CD_APLICACAO\", \n"
				+ "			A.\"NOME\", \n"
				+ "			A.\"DESCRICAO\", \n"
				+ "			A.\"DT_CADASTRO\", \n"
				+ "			A.\"DT_ATUALIZACAO\", \n"
				+ "			A.\"CD_USUARIO_CRIACAO\", \n"
				+ "			A.\"CD_USUARIO_ATUALIZACAO\", \n"
				+ "			A.\"FL_ATIVA\", \n"
				+ "			A.\"TP_APLICACAO\", \n"
				+ "			A.\"SEGREDO\", \n"
				+ "			A.\"FL_SEGURANCA\", \n"
				+ "			A.\"TMP_ACCESS_TOKEN\", \n"
				+ "			A.\"TMP_REFRESH_TOKEN\", \n"
				+ "			A.\"FL_REFRESH_TOKEN\", \n"
				+ "			UC.\"NOME\" NM_USUARIO_CRIACAO, \n"
				+ "			UA.\"NOME\" NM_USUARIO_ATUALIZACAO, \n"
				+ "			ROW_NUMBER () OVER () LINHA \n"
				+ "		FROM \"OAUTH\".\"TB_APLICACAO\" A \n"
				+ "		JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (A.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "		LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (A.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "		WHERE 1 = 1 \n"  + filtro.toString()
				+ "		ORDER BY A.\"CD_APLICACAO\") TA \n"
				+ "WHERE TA.LINHA > :pageStart \n"
				+ "	AND TA.LINHA <= :pageEnd ";
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql, AplicacaoMapping.class)
				.setParameter("pageStart", pageDTO.getPageStart()).setParameter("pageEnd", pageDTO.getPageEnd());

		return query.getResultList();
	}

	@Override
	public Long contar(AplicacaoFilter aplicacaoFilter) {
		StringBuilder filtro = new StringBuilder();

		if (aplicacaoFilter.getDtCadastroInicial() != null && aplicacaoFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', A.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(aplicacaoFilter.getDtCadastroInicial())
					+ "', 'DD/MM/YYYY') AND TO_DATE('" + DatasUtil.formataDataDiaMesAno(aplicacaoFilter.getDtCadastroFinal())
					+ "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(aplicacaoFilter.getNome())) {
			filtro.append(" AND UPPER(A.\"NOME\") LIKE '%" + aplicacaoFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(aplicacaoFilter.getDescricao())) {
			filtro.append(" AND UPPER(A.\"DESCRICAO\") LIKE '%" + aplicacaoFilter.getDescricao().toUpperCase() + "%'");
		}

		if (aplicacaoFilter.getFlAtiva() != null) {
			filtro.append(" AND A.\"FL_ATIVA\" = " + aplicacaoFilter.getFlAtiva());
		}

		// @formatter:off
		String sql = "SELECT COUNT(A.\"CD_APLICACAO\") \n"
				+ "FROM \"OAUTH\".\"TB_APLICACAO\" A \n"
				+ "JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (A.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (A.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "WHERE 1 = 1 " + filtro.toString();
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql);

		return Long.valueOf(query.getSingleResult().toString());
	}

}
