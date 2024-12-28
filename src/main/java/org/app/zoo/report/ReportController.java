package org.app.zoo.report;

import java.util.HashMap;
import java.util.Map;

import org.app.zoo.report.dto.ActiveVetInputDTO;
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
            @RequestBody DatesInputDTO datesDTO){

        try {
            Map<String, Object> parameters = new HashMap<>();
            

            parameters.put("fecha_inicio", datesDTO.fecha_inicio());
            parameters.put("fecha_terminacion", datesDTO.fecha_terminacion());

            ReportTypeEnum reportType = ReportTypeEnum.getReportTypeByCode(datesDTO.fileType());
            
            byte[] reportBytes = reportService.generateAndExportReport("ContratosAlimentos", parameters, reportType);

            if (reportType == ReportTypeEnum.HTML) {
                
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .body(new String(reportBytes));
            }

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

    @Operation(summary = "Get veterinarian contracts report", description = "Generates a report based on the specified dates")
    @PostMapping("/report/veterinarian-contracts")
    public ResponseEntity<?> getVetContractsReport(
            @RequestBody DatesInputDTO datesDTO){

        try {
            Map<String, Object> parameters = new HashMap<>();
            

            parameters.put("fecha_inicio", datesDTO.fecha_inicio());
            parameters.put("fecha_terminacion", datesDTO.fecha_terminacion());

            ReportTypeEnum reportType = ReportTypeEnum.getReportTypeByCode(datesDTO.fileType());
            
            byte[] reportBytes = reportService.generateAndExportReport("ContratosVeterinarios", parameters, reportType);

            if (reportType == ReportTypeEnum.HTML) {
                
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .body(new String(reportBytes));
            }

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
                            "attachment; filename=ContratosVeterinarios." + datesDTO.fileType().toLowerCase())
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(reportBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al generar el reporte: " + e.getMessage());
        }
    }

    @Operation(summary = "Get complementary contracts report", description = "Generates a report based on the specified dates")
    @PostMapping("/report/complementary-contracts")
    public ResponseEntity<?> getComplContractsReport(
            @RequestBody DatesInputDTO datesDTO){

        try {
            Map<String, Object> parameters = new HashMap<>();
            

            parameters.put("fecha_inicio", datesDTO.fecha_inicio());
            parameters.put("fecha_terminacion", datesDTO.fecha_terminacion());

            ReportTypeEnum reportType = ReportTypeEnum.getReportTypeByCode(datesDTO.fileType());
            
            byte[] reportBytes = reportService.generateAndExportReport("ContratosComplementarios", parameters, reportType);

            if (reportType == ReportTypeEnum.HTML) {
                
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .body(new String(reportBytes));
            }

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
                            "attachment; filename=ContratosComplementarios." + datesDTO.fileType().toLowerCase())
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(reportBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al generar el reporte: " + e.getMessage());
        }
    }

    @Operation(summary = "Get active veterinarians report", description = "Generates a report based on the specified dates")
    @PostMapping("/report/active-veterinarians")
    public ResponseEntity<?> getActiveVetReport( 
            @Parameter(description = "Active veterinarians input data: provinceName should be null in case you wanna get all the provinces", required = true)
            @RequestBody ActiveVetInputDTO activeVetDTO){

        try {
            Map<String, Object> parameters = new HashMap<>();
            
            if(activeVetDTO.provinceName() == null || activeVetDTO.provinceName().isEmpty()){
                parameters.put("provinciaParam", null);
            }
            else{
                parameters.put("provinciaParam", activeVetDTO.provinceName());
            }
            

            ReportTypeEnum reportType = ReportTypeEnum.getReportTypeByCode(activeVetDTO.fileType());
            
            byte[] reportBytes = reportService.generateAndExportReport("VeterinariosActivos", parameters, reportType);

            if (reportType == ReportTypeEnum.HTML) {
                
                return ResponseEntity.ok()
                        .contentType(MediaType.TEXT_HTML)
                        .body(new String(reportBytes));
            }

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
                            "attachment; filename=VeterinariosActivos." + activeVetDTO.fileType().toLowerCase())
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(reportBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al generar el reporte: " + e.getMessage());
        }
    }
}
