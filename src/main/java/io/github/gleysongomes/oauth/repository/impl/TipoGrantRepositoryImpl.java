package io.github.gleysongomes.oauth.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.TipoGrantFilter;
import io.github.gleysongomes.oauth.dto.mapping.TipoGrantMapping;
import io.github.gleysongomes.oauth.repository.TipoGrantRepositoryCustom;
import io.github.gleysongomes.oauth.util.DatasUtil;

@Repository
public class TipoGrantRepositoryImpl implements TipoGrantRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<TipoGrantMapping> listar(TipoGrantFilter tipoGrantFilter, PageDTO<TipoGrantMapping> pageDTO) {
		StringBuilder filtro = new StringBuilder();

		if (tipoGrantFilter.getDtCadastroInicial() != null && tipoGrantFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', TG.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(tipoGrantFilter.getDtCadastroInicial())
					+ "', 'DD/MM/YYYY') AND TO_DATE('" + DatasUtil.formataDataDiaMesAno(tipoGrantFilter.getDtCadastroFinal())
					+ "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(tipoGrantFilter.getNome())) {
			filtro.append(" AND UPPER(TG.\"NOME\") LIKE '%" + tipoGrantFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(tipoGrantFilter.getDescricao())) {
			filtro.append(" AND UPPER(TG.\"DESCRICAO\") LIKE '%" + tipoGrantFilter.getDescricao().toUpperCase() + "%'");
		}

		if (tipoGrantFilter.getFlAtivo() != null) {
			filtro.append(" AND TG.\"FL_ATIVO\" = " + tipoGrantFilter.getFlAtivo());
		}

		// @formatter:off
		String sql = "SELECT * \n"
				+ "FROM \n"
				+ "	(SELECT TG.\"CD_TIPO_GRANT\", \n"
				+ "			TG.\"NOME\", \n"
				+ "			TG.\"DESCRICAO\", \n"
				+ "			TG.\"DT_CADASTRO\", \n"
				+ "			TG.\"DT_ATUALIZACAO\", \n"
				+ "			TG.\"CD_USUARIO_CRIACAO\", \n"
				+ "			TG.\"CD_USUARIO_ATUALIZACAO\", \n"
				+ "			TG.\"FL_ATIVO\", \n"
				+ "			UC.\"NOME\" NM_USUARIO_CRIACAO, \n"
				+ "			UA.\"NOME\" NM_USUARIO_ATUALIZACAO, \n"
				+ "			ROW_NUMBER () OVER () LINHA \n"
				+ "		FROM \"OAUTH\".\"TB_TIPO_GRANT\" TG \n"
				+ "		JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (TG.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "		LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (TG.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "		WHERE 1 = 1 \n"  + filtro.toString()
				+ "		ORDER BY TG.\"CD_TIPO_GRANT\") TA \n"
				+ "WHERE TA.LINHA > :pageStart \n"
				+ "	AND TA.LINHA <= :pageEnd ";
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql, TipoGrantMapping.class)
				.setParameter("pageStart", pageDTO.getPageStart()).setParameter("pageEnd", pageDTO.getPageEnd());

		return query.getResultList();
	}

	@Override
	public Long contar(TipoGrantFilter tipoGrantFilter) {
		StringBuilder filtro = new StringBuilder();

		if (tipoGrantFilter.getDtCadastroInicial() != null && tipoGrantFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', TG.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(tipoGrantFilter.getDtCadastroInicial())
					+ "', 'DD/MM/YYYY') AND TO_DATE('" + DatasUtil.formataDataDiaMesAno(tipoGrantFilter.getDtCadastroFinal())
					+ "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(tipoGrantFilter.getNome())) {
			filtro.append(" AND UPPER(TG.\"NOME\") LIKE '%" + tipoGrantFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(tipoGrantFilter.getDescricao())) {
			filtro.append(" AND UPPER(TG.\"DESCRICAO\") LIKE '%" + tipoGrantFilter.getDescricao().toUpperCase() + "%'");
		}

		if (tipoGrantFilter.getFlAtivo() != null) {
			filtro.append(" AND TG.\"FL_ATIVO\" = " + tipoGrantFilter.getFlAtivo());
		}

		// @formatter:off
		String sql = "SELECT COUNT(TG.\"CD_TIPO_GRANT\") \n"
				+ "FROM \"OAUTH\".\"TB_TIPO_GRANT\" TG \n"
				+ "JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (TG.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (TG.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "WHERE 1 = 1 " + filtro.toString();
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql);

		return Long.valueOf(query.getSingleResult().toString());
	}

}
