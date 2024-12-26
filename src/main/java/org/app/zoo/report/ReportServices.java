package org.app.zoo.report;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

import javax.sql.DataSource;

import org.app.zoo.config.errorHandling.ResourceNotFoundException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.media.Schema;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXmlExporterOutput;

@Service
@Schema(description = "Reports service to stablish the connection to JasperReport and export them")
public class ReportServices {

    private DataSource dataSource;

    public ReportServices(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public byte[] generateAndExportReport(String reportName, Map<String, Object> parameters, ReportTypeEnum reportType)
            throws JRException {
        // Load the .jasper
        InputStream reportStream = getClass().getClassLoader().getResourceAsStream("reports/" + reportName + ".jasper");

        if (reportStream == null) {
            throw new ResourceNotFoundException("No se encontró el reporte: " + reportName + ".jasper");
        }

        // Get the DB connection
        Connection connection = DataSourceUtils.getConnection(dataSource);

        try {
            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, connection);

            // Export the report by the specified type
            return exportJasperReportBytes(jasperPrint, reportType);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public byte[] exportJasperReportBytes(JasperPrint jasperPrint, ReportTypeEnum reportType) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        switch (reportType) {
            case CSV:
                // Export to CSV
                JRCsvExporter csvExporter = new JRCsvExporter();
                csvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                csvExporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
                csvExporter.exportReport();
                break;
            case XLSX:
                // Export to XLSX
                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                xlsxExporter.exportReport();
                break;
            case HTML:
                // Export to HTML
                HtmlExporter htmlExporter = new HtmlExporter();
                htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));
                htmlExporter.exportReport();
                break;
            case XML:
                // Export to XML
                JRXmlExporter xmlExporter = new JRXmlExporter();
                xmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xmlExporter.setExporterOutput(new SimpleXmlExporterOutput(outputStream));
                xmlExporter.exportReport();
                break;
            case DOC:
                // Export to DOCX (RTF format)
                JRRtfExporter docxExporter = new JRRtfExporter();
                docxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                docxExporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
                docxExporter.exportReport();
                break;
            default:
                // Export to PDF by default
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                break;
        }
        return outputStream.toByteArray();
    }
}
