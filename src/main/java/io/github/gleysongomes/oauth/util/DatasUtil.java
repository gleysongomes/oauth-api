package io.github.gleysongomes.oauth.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DatasUtil {

	public static final String formataDataDiaMesAno(Date data) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(data);
	}

}
