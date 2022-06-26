package io.github.gleysongomes.oauth.repository.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.dto.input.filter.UsuarioFilter;
import io.github.gleysongomes.oauth.dto.mapping.ReportUsuarioMapping;
import io.github.gleysongomes.oauth.util.DatasUtil;

@Repository("reportUsuarioRepository")
public class ReportUsuarioRepositoryImpl extends ReportRepositoryImpl<ReportUsuarioMapping, UsuarioFilter> {

	@Override
	public List<ReportUsuarioMapping> getAnaliticReport(UsuarioFilter usuarioFilter) {
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
		String sql = "SELECT U.\"CD_USUARIO\", \n"
				+ "			U.\"LOGIN\", \n"
				+ "			U.\"EMAIL\", \n"
				+ "			U.\"NOME\", \n"
				+ "			U.\"DT_CADASTRO\", \n"
				+ "			U.\"DT_ATUALIZACAO\", \n"
				+ "			U.\"FL_ATIVO\", \n"
				+ "			UC.\"NOME\" NM_USUARIO_CRIACAO, \n"
				+ "			UA.\"NOME\" NM_USUARIO_ATUALIZACAO \n"
				+ "		FROM \"OAUTH\".\"TB_USUARIO\" U \n"
				+ "		JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (U.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "		LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (U.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "		WHERE 1 = 1 \n"  + filtro.toString()
				+ "		ORDER BY U.\"CD_USUARIO\"";
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql, "ReportUsuarioMap");

		return query.getResultList();
	}

}
