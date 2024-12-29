import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReportManager {

    private final String reportFileName = "reports.txt";

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    //method to save report to txt file
    public void saveReport(Report report) {
        try{

            //writing the report to the file
            bufferedWriter = new BufferedWriter(new FileWriter(reportFileName, true));
            bufferedWriter.write(report.getReportType() + " " + report.getReportId() + " " + report.getReportDate() + " " + report.getCreatedByEmployeeId() + " " + report.getStatus() + "\n");
            bufferedWriter.close();
            System.out.println("Report saved successfully\n\n< DISPLAYING ALL REPORTS >");
            //displayReports();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
