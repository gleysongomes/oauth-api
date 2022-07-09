package io.github.gleysongomes.oauth.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.dto.input.filter.PermissaoFilter;
import io.github.gleysongomes.oauth.dto.mapping.ReportPermissaoMapping;
import io.github.gleysongomes.oauth.util.DatasUtil;

@Repository("reportPermissaoRepository")
public class ReportPermissaoRepositoryImpl extends ReportRepositoryImpl<ReportPermissaoMapping, PermissaoFilter> {

	@Override
	public List<ReportPermissaoMapping> getAnaliticReport(PermissaoFilter permissaoFilter) {
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
		String sql = "SELECT P.\"CD_PERMISSAO\", \n"
				+ "			P.\"NOME\", \n"
				+ "			P.\"DESCRICAO\", \n"
				+ "			P.\"DT_CADASTRO\", \n"
				+ "			P.\"DT_ATUALIZACAO\", \n"
				+ "			P.\"FL_ATIVA\", \n"
				+ "			A.\"NOME\" NM_APLICACAO, \n"
				+ "			UC.\"NOME\" NM_USUARIO_CRIACAO, \n"
				+ "			UA.\"NOME\" NM_USUARIO_ATUALIZACAO \n"
				+ "		FROM \"OAUTH\".\"TB_PERMISSAO\" P \n"
				+ "		JOIN \"OAUTH\".\"TB_APLICACAO\" A ON (P.\"CD_APLICACAO\" = A.\"CD_APLICACAO\") \n"
				+ "		JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (P.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "		LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (P.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "		WHERE 1 = 1 \n"  + filtro.toString()
				+ "		ORDER BY P.\"CD_PERMISSAO\"";
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql, ReportPermissaoMapping.class);

		return query.getResultList();
	}

}
