import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReportManager {

    public final String reportFileName = "reports.txt";

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private Scanner input = new Scanner(System.in);

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

            //reading the file
            bufferedReader = new BufferedReader(new FileReader(reportFileName));
            String line;

            //looping through the file
            while ((line = bufferedReader.readLine()) != null) {
                String[] reportDetails = line.split(" ");

                //checking if the report was created by the user
                if (reportDetails[3].equals(email)) {
                    //appending the report details to the string builder
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

        //creating a string builder to store all reports
        StringBuilder allReports = new StringBuilder();
        try {

            //reading the file
            bufferedReader = new BufferedReader(new FileReader(reportFileName));
            String line;

            //looping through the file
            while ((line = bufferedReader.readLine()) != null) {

                //appending the report details to the string builder
                String[] reportDetails = line.split(" ");

                //appending the report details to the string builder
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

    //method to generate a random and unique three digit ID for the report
    public int generateReportId() {

        //Generating a random number between 100 and 999
        int ReportID = (int) (Math.random() * 900 + 100);

        //checking ReportID is unique
        try {
            bufferedReader = new BufferedReader(new FileReader(reportFileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] reportDetails = line.split(" ");
                if (reportDetails[2].equals(String.valueOf(ReportID))) {
                    return generateReportId();
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ReportID;
    }

    //method to ensure one of the valid report types is selected
    public String handleReportType(){

        while(true){

            // Get the report type from the user
            System.out.print("\nEnter Report Type:\n\n1. Accident Report\n2. New Health\n3. Safety Risk Report\n\nENTER CHOICE: ");
            String reportType = input.nextLine();

            // Validate the report type
            if(reportType.equals("1") || reportType.equalsIgnoreCase("Accident Report")){
                return "Accident Report";
            } else if(reportType.equals("2") || reportType.equalsIgnoreCase("New Health")){
                return "New Health";
            } else if(reportType.equals("3") || reportType.equalsIgnoreCase("Safety Risk Report")){
                return "Safety Risk Report";
            } else {
                System.out.println("Invalid choice. Please type a valid option");
            }
        }

    }

    //method to ensure a valid report status is selected
    public String handleReportStatus(){

        while(true){

            //Get the report status from the user
            System.out.print("\nEnter Report Status:\n\n1. Open\n2. Assigned\n3. Closed\n\nENTER CHOICE: ");
            String reportStatus = input.nextLine();

            // Validate the report type
            if(reportStatus.equals("1") || reportStatus.equalsIgnoreCase("Open")){
                return "Open";
            } else if(reportStatus.equals("2") || reportStatus.equalsIgnoreCase("Assigned")){
                return "Assigned";
            } else if(reportStatus.equals("3") || reportStatus.equalsIgnoreCase("Closed")){
                return "Closed";
            } else {
                System.out.println("Invalid choice. Please type a valid option");
            }
        }
    }



}
