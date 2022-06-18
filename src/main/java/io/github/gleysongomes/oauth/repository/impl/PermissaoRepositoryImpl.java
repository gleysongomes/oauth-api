package io.github.gleysongomes.oauth.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.PermissaoFilter;
import io.github.gleysongomes.oauth.dto.mapping.PermissaoMapping;
import io.github.gleysongomes.oauth.dto.mapping.PermissaoResumoMapping;
import io.github.gleysongomes.oauth.repository.PermissaoRepositoryCustom;
import io.github.gleysongomes.oauth.util.DatasUtil;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<PermissaoMapping> listar(PermissaoFilter permissaoFilter, PageDTO<PermissaoMapping> pageDTO) {
		StringBuilder filtro = new StringBuilder();

		if (permissaoFilter.getDtCadastroInicial() != null && permissaoFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', P.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(permissaoFilter.getDtCadastroInicial())
					+ "', 'DD/MM/YYYY') AND TO_DATE('" + DatasUtil.formataDataDiaMesAno(permissaoFilter.getDtCadastroFinal())
					+ "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(permissaoFilter.getNome())) {
			filtro.append(" AND UPPER(P.\"NOME\") LIKE '%" + permissaoFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(permissaoFilter.getDescricao())) {
			filtro.append(" AND UPPER(P.\"DESCRICAO\") LIKE '%" + permissaoFilter.getDescricao().toUpperCase() + "%'");
		}

		if (permissaoFilter.getFlAtiva() != null) {
			filtro.append(" AND P.\"FL_ATIVA\" = " + permissaoFilter.getFlAtiva());
		}

		// @formatter:off
		String sql = "SELECT * \n"
				+ "FROM \n"
				+ "	(SELECT P.\"CD_PERMISSAO\", \n"
				+ "			P.\"NOME\", \n"
				+ "			P.\"DESCRICAO\", \n"
				+ "			P.\"DT_CADASTRO\", \n"
				+ "			P.\"DT_ATUALIZACAO\", \n"
				+ "			P.\"CD_USUARIO_CRIACAO\", \n"
				+ "			P.\"CD_USUARIO_ATUALIZACAO\", \n"
				+ "			P.\"FL_ATIVA\", \n"
				+ "			P.\"CD_APLICACAO\", \n"
				+ "			A.\"NOME\" NM_APLICACAO, \n"
				+ "			UC.\"NOME\" NM_USUARIO_CRIACAO, \n"
				+ "			UA.\"NOME\" NM_USUARIO_ATUALIZACAO, \n"
				+ "			ROW_NUMBER () OVER () LINHA \n"
				+ "		FROM \"OAUTH\".\"TB_PERMISSAO\" P \n"
				+ "		JOIN \"OAUTH\".\"TB_APLICACAO\" A ON (P.\"CD_APLICACAO\" = A.\"CD_APLICACAO\") \n"
				+ "		JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (P.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "		LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (P.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "		WHERE 1 = 1 \n"  + filtro.toString()
				+ "		ORDER BY P.\"CD_PERMISSAO\") TA \n"
				+ "WHERE TA.LINHA > :pageStart \n"
				+ "	AND TA.LINHA <= :pageEnd ";
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql, PermissaoMapping.class)
				.setParameter("pageStart", pageDTO.getPageStart()).setParameter("pageEnd", pageDTO.getPageEnd());

		return query.getResultList();
	}

	@Override
	public Long contar(PermissaoFilter permissaoFilter) {
		StringBuilder filtro = new StringBuilder();

		if (permissaoFilter.getDtCadastroInicial() != null && permissaoFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', P.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(permissaoFilter.getDtCadastroInicial())
					+ "', 'DD/MM/YYYY') AND TO_DATE('" + DatasUtil.formataDataDiaMesAno(permissaoFilter.getDtCadastroFinal())
					+ "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(permissaoFilter.getNome())) {
			filtro.append(" AND UPPER(P.\"NOME\") LIKE '%" + permissaoFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(permissaoFilter.getDescricao())) {
			filtro.append(" AND UPPER(P.\"DESCRICAO\") LIKE '%" + permissaoFilter.getDescricao().toUpperCase() + "%'");
		}

		if (permissaoFilter.getFlAtiva() != null) {
			filtro.append(" AND P.\"FL_ATIVA\" = " + permissaoFilter.getFlAtiva());
		}

		// @formatter:off
		String sql = "SELECT COUNT(P.\"CD_PERMISSAO\") \n"
				+ "FROM \"OAUTH\".\"TB_PERMISSAO\" P \n"
				+ "JOIN \"OAUTH\".\"TB_APLICACAO\" A ON (P.\"CD_APLICACAO\" = A.\"CD_APLICACAO\") \n"
				+ "JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (P.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (P.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "WHERE 1 = 1 " + filtro.toString();
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql);

		return Long.valueOf(query.getSingleResult().toString());
	}

	@Override
	public List<PermissaoResumoMapping> findPermissoesGrupoByCdUsuario(Long cdUsuario) {
		try {
			// @formatter:off
			String sql = "SELECT P.\"CD_PERMISSAO\", " + 
					"		P.\"NOME\", " + 
					"FROM \"OAUTH\".\"TB_PERMISSAO\" P, " + 
					"	\"OAUTH\".\"TB_GRUPO\" G," + 
					"	\"OAUTH\".\"TB_USUARIO\" U, " + 
					"	\"OAUTH\".\"TB_PERMISSAO_GRUPO\" PG, " + 
					"	\"OAUTH\".\"TB_GRUPO_USUARIO\" GU " + 
					"WHERE P.CD_PERMISSAO = PG.CD_PERMISSAO " + 
					"AND G.CD_GRUPO = PG.CD_GRUPO " + 
					"AND U.CD_USUARIO = GU.CD_USUARIO " + 
					"AND G.CD_GRUPO = GU.CD_GRUPO " + 
					"AND P.FL_ATIVA = TRUE " + 
					"AND G.FL_ATIVO = TRUE " + 
					"AND PG.FL_ATIVA = TRUE " + 
					"AND GU.FL_ATIVO = TRUE " + 
					"AND U.CD_USUARIO = :cdUsuario " + 
					"ORDER BY P.CD_PERMISSAO ";
			// @formatter:on

			TypedQuery<PermissaoResumoMapping> query = entityManager.createQuery(sql, PermissaoResumoMapping.class)
					.setParameter("cdUsuario", cdUsuario);

			return query.getResultList();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

}
