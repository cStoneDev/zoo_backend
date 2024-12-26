package org.app.zoo.report;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enum representing the types of report exports available.
 */
@Schema(description = "Enumeration of available report types for export.")
public enum ReportTypeEnum {

    @Schema(description = "Export to CSV format.")
    CSV,

    @Schema(description = "Export to Excel (XLSX) format.")
    XLSX,

    @Schema(description = "Export to HTML format.")
    HTML,

    @Schema(description = "Export to XML format.")
    XML,

    @Schema(description = "Export to Document format (RTF).")
    DOC,

    @Schema(description = "Export to PDF format. This is the default type.")
    PDF;

    /**
     * Retrieves the corresponding report type based on the provided file type code.
     *
     * @param fileType the file type code (e.g., "csv", "xlsx", "pdf").
     * @return the corresponding report type, or PDF as the default if not found.
     */
    @Schema(description = "Retrieves the report type based on the provided file type code. Defaults to PDF if the code is invalid.")
    public static ReportTypeEnum getReportTypeByCode(
            @Schema(description = "The file type code (e.g., 'csv', 'xlsx', 'pdf').") String fileType) {
        if (fileType == null || fileType.isEmpty()) {
            return PDF; // Default value
        }

        try {
            return ReportTypeEnum.valueOf(fileType.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return PDF; // Default value if the code does not match
        }
    }
}

