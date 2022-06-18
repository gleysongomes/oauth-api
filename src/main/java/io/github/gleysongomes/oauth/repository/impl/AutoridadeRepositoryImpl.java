package io.github.gleysongomes.oauth.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import io.github.gleysongomes.oauth.dto.PageDTO;
import io.github.gleysongomes.oauth.dto.input.filter.AutoridadeFilter;
import io.github.gleysongomes.oauth.dto.mapping.AutoridadeMapping;
import io.github.gleysongomes.oauth.repository.AutoridadeRepositoryCustom;
import io.github.gleysongomes.oauth.util.DatasUtil;

@Repository
public class AutoridadeRepositoryImpl implements AutoridadeRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<AutoridadeMapping> listar(AutoridadeFilter autoridadeFilter, PageDTO<AutoridadeMapping> pageDTO) {
		StringBuilder filtro = new StringBuilder();

		if (autoridadeFilter.getDtCadastroInicial() != null && autoridadeFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', AUT.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(autoridadeFilter.getDtCadastroInicial())
					+ "', 'DD/MM/YYYY') AND TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(autoridadeFilter.getDtCadastroFinal()) + "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(autoridadeFilter.getNome())) {
			filtro.append(" AND UPPER(AUT.\"NOME\") LIKE '%" + autoridadeFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(autoridadeFilter.getDescricao())) {
			filtro.append(" AND UPPER(AUT.\"DESCRICAO\") LIKE '%" + autoridadeFilter.getDescricao().toUpperCase() + "%'");
		}

		if (autoridadeFilter.getFlAtiva() != null) {
			filtro.append(" AND AUT.\"FL_ATIVA\" = " + autoridadeFilter.getFlAtiva());
		}

		// @formatter:off
		String sql = "SELECT * \n"
				+ "FROM \n"
				+ "	(SELECT AUT.\"CD_AUTORIDADE\", \n"
				+ "			AUT.\"NOME\", \n"
				+ "			AUT.\"DESCRICAO\", \n"
				+ "			AUT.\"DT_CADASTRO\", \n"
				+ "			AUT.\"DT_ATUALIZACAO\", \n"
				+ "			AUT.\"CD_USUARIO_CRIACAO\", \n"
				+ "			AUT.\"CD_USUARIO_ATUALIZACAO\", \n"
				+ "			AUT.\"FL_ATIVA\", \n"
				+ "			AUT.\"CD_APLICACAO\", \n"
				+ "			A.\"NOME\" NM_APLICACAO, \n"
				+ "			UC.\"NOME\" NM_USUARIO_CRIACAO, \n"
				+ "			UA.\"NOME\" NM_USUARIO_ATUALIZACAO, \n"
				+ "			ROW_NUMBER () OVER () LINHA \n"
				+ "		FROM \"OAUTH\".\"TB_AUTORIDADE\" AUT \n"
				+ "		JOIN \"OAUTH\".\"TB_APLICACAO\" A ON (AUT.\"CD_APLICACAO\" = A.\"CD_APLICACAO\") \n"
				+ "		JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (AUT.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "		LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (AUT.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "		WHERE 1 = 1 \n"  + filtro.toString()
				+ "		ORDER BY AUT.\"CD_AUTORIDADE\") TA \n"
				+ "WHERE TA.LINHA > :pageStart \n"
				+ "	AND TA.LINHA <= :pageEnd ";
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql, AutoridadeMapping.class)
				.setParameter("pageStart", pageDTO.getPageStart()).setParameter("pageEnd", pageDTO.getPageEnd());

		return query.getResultList();
	}

	@Override
	public Long contar(AutoridadeFilter autoridadeFilter) {
		StringBuilder filtro = new StringBuilder();

		if (autoridadeFilter.getDtCadastroInicial() != null && autoridadeFilter.getDtCadastroFinal() != null) {
			filtro.append(" AND DATE_TRUNC('DAY', AUT.\"DT_CADASTRO\") BETWEEN TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(autoridadeFilter.getDtCadastroInicial())
					+ "', 'DD/MM/YYYY') AND TO_DATE('"
					+ DatasUtil.formataDataDiaMesAno(autoridadeFilter.getDtCadastroFinal()) + "', 'DD/MM/YYYY')");
		}

		if (StringUtils.isNotBlank(autoridadeFilter.getNome())) {
			filtro.append(" AND UPPER(AUT.\"NOME\") LIKE '%" + autoridadeFilter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(autoridadeFilter.getDescricao())) {
			filtro.append(" AND UPPER(AUT.\"DESCRICAO\") LIKE '%" + autoridadeFilter.getDescricao().toUpperCase() + "%'");
		}

		if (autoridadeFilter.getFlAtiva() != null) {
			filtro.append(" AND AUT.\"FL_ATIVA\" = " + autoridadeFilter.getFlAtiva());
		}

		// @formatter:off
		String sql = "SELECT COUNT(AUT.\"CD_AUTORIDADE\") \n"
				+ "FROM \"OAUTH\".\"TB_AUTORIDADE\" AUT \n"
				+ "JOIN \"OAUTH\".\"TB_APLICACAO\" A ON (AUT.\"CD_APLICACAO\" = A.\"CD_APLICACAO\") \n"
				+ "JOIN \"OAUTH\".\"TB_USUARIO\" UC ON (AUT.\"CD_USUARIO_CRIACAO\" = UC.\"CD_USUARIO\") \n"
				+ "LEFT JOIN \"OAUTH\".\"TB_USUARIO\" UA ON (AUT.\"CD_USUARIO_ATUALIZACAO\" = UA.\"CD_USUARIO\") \n"
				+ "WHERE 1 = 1 " + filtro.toString();
		// @formatter:on

		Query query = entityManager.createNativeQuery(sql);

		return Long.valueOf(query.getSingleResult().toString());
	}

}
