package org.app.zoo.report;

import java.util.HashMap;
import java.util.Map;

import org.app.zoo.report.dto.DatesInputDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/reports")
@Schema(description = "Reports controller class to handle HTTP requests")
public class ReportController {

    private ReportServices reportService;

    public ReportController(ReportServices reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Get food contracts report", description = "Generates a report based on the specified dates")
    @PostMapping("/report/food-contracts")
    public ResponseEntity<?> getFoodContractsReport(
            @Parameter(description = "yyyy-MM-dd format") 
            @RequestBody DatesInputDTO datesDTO){

        try {
            Map<String, Object> parameters = new HashMap<>();
            // Convertir los valores de String a Date
            //java.sql.Date fechaInicio = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(fecha_inicio).getTime());
            //java.sql.Date fechaTerminacion = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(fecha_inicio).getTime());

            parameters.put("fecha_inicio", datesDTO.fecha_inicio());
            parameters.put("fecha_terminacion", datesDTO.fecha_terminacion());

            ReportTypeEnum reportType = ReportTypeEnum.getReportTypeByCode(datesDTO.fileType());
            
            byte[] reportBytes = reportService.generateAndExportReport("ContratosAlimentos", parameters, reportType);

            if (reportType == ReportTypeEnum.HTML) {
                // Respuesta para visualizar en el navegador
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .body(new String(reportBytes));
            }

            // Respuesta para descarga de archivo
            String contentType = switch (reportType) {
                case CSV -> "text/csv";
                case XLSX -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                case HTML -> "text/html";
                case XML -> "application/xml";
                case DOC -> "application/rtf";
                default -> "application/pdf";
            };

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=ContratosAlimentos." + datesDTO.fileType().toLowerCase())
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(reportBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al generar el reporte: " + e.getMessage());
        }
    }
}
