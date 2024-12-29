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
    public void viewUserReports(String email) {
        try {
            bufferedReader = new BufferedReader(new FileReader(reportFileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] reportDetails = line.split(" ");
                if (reportDetails[3].equals(email)) {
                    System.out.println("Report Type: " + reportDetails[0] + "\nReport ID: " + reportDetails[1] + "\nReport Date: " + reportDetails[2] + "\nCreated By: " + reportDetails[3] + "\nStatus: " + reportDetails[4] + "\n");
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    //method to display all reports
    public void displayReports() {
        try {
            bufferedReader = new BufferedReader(new FileReader(reportFileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] reportDetails = line.split(" ");
                System.out.println("Report Type: " + reportDetails[0] + "\nReport ID: " + reportDetails[1] + "\nReport Date: " + reportDetails[2] + "\nCreated By: " + reportDetails[3] + "\nStatus: " + reportDetails[4] + "\n");
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
