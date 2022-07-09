package io.github.gleysongomes.oauth.webservice.helper;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import io.github.gleysongomes.oauth.dto.input.filter.ReportFilter;
import io.github.gleysongomes.oauth.exception.ApiException;
import io.github.gleysongomes.oauth.service.ReportService;
import io.github.gleysongomes.oauth.util.ReportsUtil;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

@Service
public class ReportHelper {

	public Map<String, Object> getDefaultParam(ReportFilter reportFilter) {
		Map<String, Object> params = new HashMap<>();
		params.put("tipoArquivo", reportFilter.getTipoArquivo());
		params.put("tipoRelatorio", reportFilter.getTipoRelatorio());
		params.put("dataInicial", reportFilter.getDtCadastroInicial());
		params.put("dataFinal", reportFilter.getDtCadastroFinal());
		params.put("sistema", "SERVIDOR OAUTH");
		return params;
	}

	public <T, F> void getReport(ReportService<T, F> service, Map<String, Object> params, F filter) {
		try {
			String tipoArquivo = params.get("tipoArquivo").toString();
			String tipoRelatorio = params.get("tipoRelatorio").toString();
			String reportName = service.getReportFileName(filter);
			List<T> source = service.getReportData(filter, tipoRelatorio);
			getJasperReportView(reportName, params, source, tipoArquivo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException("Erro ao gerar relatório.", e);
		}
	}

	public <T> void getJasperReportView(String report, Map<String, Object> params, List<T> source, String tipoArquivo)
			throws Exception {

		String reportsDir = getClass().getResource(ReportsUtil.REPORTS_DIR.getValue()).toString();
		String imagesDir = getClass().getResource(ReportsUtil.IMAGES_DIR.getValue()).toString();

		params.put(ReportsUtil.DATA_SOURCE.getValue(), new JRBeanCollectionDataSource(source));
		params.put(ReportsUtil.SUB_DATA_SOURCE.getValue(), new JRBeanCollectionDataSource(source));
		params.put(ReportsUtil.REPORTS_DIR.toString(), reportsDir);
		params.put(ReportsUtil.IMAGES_DIR.toString(), imagesDir);

		tipoArquivo = tipoArquivo.toLowerCase();

		if ("csv".equals(tipoArquivo) || "xls".equals(tipoArquivo) || "xlsx".equals(tipoArquivo)) {
			params.put(JRParameter.IS_IGNORE_PAGINATION, true);
		}

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(source);

		InputStream inputStream = this.getClass()
				.getResourceAsStream("/reports/" + report + ReportsUtil.JASPER_EXTENSAO.getValue());

		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);

		HttpServletResponse response = ResourceUriHelper.getHttpServletResponse();
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"inline;filename=" + report + "_" + new Date().getTime() + "." + tipoArquivo);
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

		ServletOutputStream output = response.getOutputStream();

		switch (tipoArquivo) {
		case "pdf":
			response.setContentType(MediaType.APPLICATION_PDF_VALUE);
			JRPdfExporter pdf = new JRPdfExporter();
			pdf.setExporterInput(new SimpleExporterInput(jasperPrint));
			pdf.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
			pdf.exportReport();
			break;
		case "csv":
			JRCsvExporter csv = new JRCsvExporter();
			csv.setExporterInput(new SimpleExporterInput(jasperPrint));
			csv.setExporterOutput(new SimpleWriterExporterOutput(output));
			csv.exportReport();
			break;
		case "xls":
			JRXlsExporter xls = new JRXlsExporter();
			xls.setExporterInput(new SimpleExporterInput(jasperPrint));
			xls.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
			xls.exportReport();
			break;
		case "xlsx":
			JRXlsxExporter xlsx = new JRXlsxExporter();
			xlsx.setExporterInput(new SimpleExporterInput(jasperPrint));
			xlsx.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
			xlsx.exportReport();
			break;
		default:
			throw new ApiException("Tipo de arquivo " + tipoArquivo + " não suportado.");
		}
	}

}
