package io.github.gleysongomes.oauth.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.GrupoFilter;
import io.github.gleysongomes.oauth.dto.mapping.GrupoMapping;
import io.github.gleysongomes.oauth.repository.GrupoRepositoryCustom;
import io.github.gleysongomes.oauth.util.DatasUtil;

@Repository
public class GrupoRepositoryImpl implements GrupoRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<GrupoMapping> listar(GrupoFilter grupoFilter, PageDTO<GrupoMapping> pageDTO) {
		StringBuilder filtro = new StringBuilder();

		if (grupoFilter.getDtCadastroInicial() != null && grupoFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', GR.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(grupoFilter.getDtCadastroInicial()) + "', 'DD/MM/YYYY') AND TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(grupoFilter.getDtCadastroFinal()) + "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(grupoFilter.getNome())) {
			filtro.append(" AND UPPER(GR.\"NOME\") LIKE '%" + grupoFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(grupoFilter.getDescricao())) {
			filtro.append(" AND UPPER(GR.\"DESCRICAO\") LIKE '%" + grupoFilter.getDescricao().toUpperCase() + "%'");
		}

		if (grupoFilter.getFlAtivo() != null) {
			filtro.append(" AND GR.\"FL_ATIVO\" = " + grupoFilter.getFlAtivo());
		}

		// @formatter:off
		String sql = "SELECT * \n"
				+ "FROM \n"
				+ "	(SELECT GR.\"CD_GRUPO\", \n"
				+ "			GR.\"NOME\", \n"
				+ "			GR.\"DESCRICAO\", \n"
				+ "			GR.\"DT_CADASTRO\", \n"
				+ "			GR.\"DT_ATUALIZACAO\", \n"
				+ "			GR.\"CD_USUARIO_CRIACAO\", \n"
				+ "			GR.\"CD_USUARIO_ATUALIZACAO\", \n"
				+ "			GR.\"FL_ATIVO\", \n"
				+ "			UC.\"NOME\" NM_USUARIO_CRIACAO, \n"
				+ "			UA.\"NOME\" NM_USUARIO_ATUALIZACAO, \n"
				+ "			ROW_NUMBER () OVER (ORDER BY GR.\"CD_GRUPO\") LINHA \n"
				+ "		FROM \"OAUTH\".\"TB_GRUPO\" GR \n"
				+ "		JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (GR.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "		LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (GR.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "		WHERE 1 = 1 \n"  + filtro.toString()
				+ "	) TA \n"
				+ "WHERE TA.LINHA > :pageStart \n"
				+ "	AND TA.LINHA <= :pageEnd ";
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql, GrupoMapping.class)
				.setParameter("pageStart", pageDTO.getPageStart()).setParameter("pageEnd", pageDTO.getPageEnd());

		return query.getResultList();
	}

	@Override
	public Long contar(GrupoFilter grupoFilter) {
		StringBuilder filtro = new StringBuilder();

		if (grupoFilter.getDtCadastroInicial() != null && grupoFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', GR.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(grupoFilter.getDtCadastroInicial()) + "', 'DD/MM/YYYY') AND TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(grupoFilter.getDtCadastroFinal()) + "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(grupoFilter.getNome())) {
			filtro.append(" AND UPPER(GR.\"NOME\") LIKE '%" + grupoFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(grupoFilter.getDescricao())) {
			filtro.append(" AND UPPER(GR.\"DESCRICAO\") LIKE '%" + grupoFilter.getDescricao().toUpperCase() + "%'");
		}

		if (grupoFilter.getFlAtivo() != null) {
			filtro.append(" AND GR.\"FL_ATIVO\" = " + grupoFilter.getFlAtivo());
		}

		// @formatter:off
		String sql = "SELECT COUNT(GR.\"CD_GRUPO\") \n"
				+ "FROM \"OAUTH\".\"TB_GRUPO\" GR \n"
				+ "JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (GR.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (GR.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "WHERE 1 = 1 " + filtro.toString();
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql);

		return Long.valueOf(query.getSingleResult().toString());
	}

}
