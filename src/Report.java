import java.io.Serializable;

public class Report implements Serializable {

    // Declare variables
    private String reportType;
    private String reportId;
    private String reportDate;
    private String createdByEmployeeId;
    private String status;

    public Report(String reportType, String reportId, String reportDate, String createdByEmployeeId, String status) {
        this.reportType = reportType;
        this.reportId = reportId;
        this.reportDate = reportDate;
        this.createdByEmployeeId = createdByEmployeeId;
        this.status = status;

    }




    // Getters and setters
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getCreatedByEmployeeId() {
        return createdByEmployeeId;
    }

    public void setCreatedByEmployeeId(String createdByEmployeeId) {
        this.createdByEmployeeId = createdByEmployeeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
