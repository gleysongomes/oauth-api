package io.github.gleysongomes.oauth.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.dto.input.filter.GrupoFilter;
import io.github.gleysongomes.oauth.dto.mapping.ReportGrupoMapping;
import io.github.gleysongomes.oauth.util.DatasUtil;

@Repository("reportGrupoRepository")
public class ReportGrupoRepositoryImpl extends ReportRepositoryImpl<ReportGrupoMapping, GrupoFilter> {

	@Override
	public List<ReportGrupoMapping> getAnaliticReport(GrupoFilter grupoFilter) {
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
		String sql = "SELECT GR.\"CD_GRUPO\", \n"
				+ "			GR.\"NOME\", \n"
				+ "			GR.\"DESCRICAO\", \n"
				+ "			GR.\"DT_CADASTRO\", \n"
				+ "			GR.\"DT_ATUALIZACAO\", \n"
				+ "			GR.\"FL_ATIVO\", \n"
				+ "			UC.\"NOME\" NM_USUARIO_CRIACAO, \n"
				+ "			UA.\"NOME\" NM_USUARIO_ATUALIZACAO \n"
				+ "		FROM \"OAUTH\".\"TB_GRUPO\" GR \n"
				+ "		JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (GR.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "		LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (GR.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "		WHERE 1 = 1 \n"  + filtro.toString()
				+ "		ORDER BY GR.\"CD_GRUPO\"";
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql, ReportGrupoMapping.class);

		return query.getResultList();
	}

}
