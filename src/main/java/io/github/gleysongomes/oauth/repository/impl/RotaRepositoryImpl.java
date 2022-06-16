package io.github.gleysongomes.oauth.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.RotaFilter;
import io.github.gleysongomes.oauth.dto.mapping.RotaMapping;
import io.github.gleysongomes.oauth.repository.RotaRepositoryCustom;
import io.github.gleysongomes.oauth.util.DatasUtil;

@Repository
public class RotaRepositoryImpl implements RotaRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<RotaMapping> listar(RotaFilter rotaFilter, PageDTO<RotaMapping> pageDTO) {
		StringBuilder filtro = new StringBuilder();

		if (rotaFilter.getDtCadastroInicial() != null && rotaFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', R.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(rotaFilter.getDtCadastroInicial()) + "', 'DD/MM/YYYY') AND TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(rotaFilter.getDtCadastroFinal()) + "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(rotaFilter.getNome())) {
			filtro.append(" AND UPPER(R.\"NOME\") LIKE '%" + rotaFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(rotaFilter.getDescricao())) {
			filtro.append(" AND UPPER(R.\"DESCRICAO\") LIKE '%" + rotaFilter.getDescricao().toUpperCase() + "%'");
		}

		if (rotaFilter.getFlAtiva() != null) {
			filtro.append(" AND R.\"FL_ATIVA\" = " + rotaFilter.getFlAtiva());
		}

		// @formatter:off
		String sql = "SELECT * \n"
				+ "FROM \n"
				+ "	(SELECT R.\"CD_ROTA\", \n"
				+ "			R.\"NOME\", \n"
				+ "			R.\"DESCRICAO\", \n"
				+ "			R.\"DT_CADASTRO\", \n"
				+ "			R.\"DT_ATUALIZACAO\", \n"
				+ "			R.\"CD_USUARIO_CRIACAO\", \n"
				+ "			R.\"CD_USUARIO_ATUALIZACAO\", \n"
				+ "			R.\"FL_ATIVA\", \n"
				+ "			R.\"CD_APLICACAO\", \n"
				+ "			A.\"NOME\" NM_APLICACAO, \n"
				+ "			UC.\"NOME\" NM_USUARIO_CRIACAO, \n"
				+ "			UA.\"NOME\" NM_USUARIO_ATUALIZACAO, \n"
				+ "			ROW_NUMBER () OVER () LINHA \n"
				+ "		FROM \"OAUTH\".\"TB_ROTA\" R \n"
				+ "		JOIN \"OAUTH\".\"TB_APLICACAO\" A ON (R.\"CD_APLICACAO\" = A.\"CD_APLICACAO\") \n"
				+ "		JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (R.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "		LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (R.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "		WHERE 1 = 1 \n"  + filtro.toString()
				+ "		ORDER BY R.\"CD_ROTA\") TA \n"
				+ "WHERE TA.LINHA > :pageStart \n"
				+ "	AND TA.LINHA <= :pageEnd ";
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql, RotaMapping.class)
				.setParameter("pageStart", pageDTO.getPageStart()).setParameter("pageEnd", pageDTO.getPageEnd());

		return query.getResultList();
	}

	@Override
	public Long contar(RotaFilter rotaFilter) {
		StringBuilder filtro = new StringBuilder();

		if (rotaFilter.getDtCadastroInicial() != null && rotaFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', R.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(rotaFilter.getDtCadastroInicial()) + "', 'DD/MM/YYYY') AND TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(rotaFilter.getDtCadastroFinal()) + "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(rotaFilter.getNome())) {
			filtro.append(" AND UPPER(R.\"NOME\") LIKE '%" + rotaFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(rotaFilter.getDescricao())) {
			filtro.append(" AND UPPER(R.\"DESCRICAO\") LIKE '%" + rotaFilter.getDescricao().toUpperCase() + "%'");
		}

		if (rotaFilter.getFlAtiva() != null) {
			filtro.append(" AND R.\"FL_ATIVA\" = " + rotaFilter.getFlAtiva());
		}

		// @formatter:off
		String sql = "SELECT COUNT(R.\"CD_ROTA\") \n"
				+ "FROM \"OAUTH\".\"TB_ROTA\" R \n"
				+ "JOIN \"OAUTH\".\"TB_APLICACAO\" A ON (R.\"CD_APLICACAO\" = A.\"CD_APLICACAO\") \n"
				+ "JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (R.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (R.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "WHERE 1 = 1 " + filtro.toString();
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql);

		return Long.valueOf(query.getSingleResult().toString());
	}

}
