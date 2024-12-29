import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class ReportManager {

    public final String reportFileName = "reports.txt";

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    //method to save report to txt file
    public void saveReport(Report report) {
        try {

            //writing the report to the file
            bufferedWriter = new BufferedWriter(new FileWriter(reportFileName, true));
            bufferedWriter.write(report.getReportType() + " " + report.getReportId() + " " + report.getReportDate() + " " + report.getCreatedByEmployeeId() + " " + report.getStatus() + "\n");
            bufferedWriter.close();
            System.out.println("Report saved successfully\n\n< DISPLAYING ALL REPORTS >");
            //displayReports();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method to view specific reports based on the user email
    public String viewUserReports(String email) {
        StringBuilder userReports = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new FileReader(reportFileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] reportDetails = line.split(" ");
                if (reportDetails[3].equals(email)) {
                    userReports.append("Report Type: ").append(reportDetails[0])
                            .append("\nReport ID: ").append(reportDetails[1])
                            .append("\nReport Date: ").append(reportDetails[2])
                            .append("\nCreated By: ").append(reportDetails[3])
                            .append("\nStatus: ").append(reportDetails[4])
                            .append("\n\n");
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userReports.length() == 0 ? "No reports found for this user." : "Your Reports:\n" + userReports;
    }

    //method to display all reports
    public String viewALLReports() {
        StringBuilder allReports = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new FileReader(reportFileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] reportDetails = line.split(" ");
                    allReports.append("Report Type: ").append(reportDetails[0])
                            .append("\nReport ID: ").append(reportDetails[1])
                            .append("\nReport Date: ").append(reportDetails[2])
                            .append("\nCreated By: ").append(reportDetails[3])
                            .append("\nStatus: ").append(reportDetails[4])
                            .append("\n\n");
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allReports.length() == 0 ? "No reports found for this user." : "Your Reports:\n" + allReports;
    }


}
