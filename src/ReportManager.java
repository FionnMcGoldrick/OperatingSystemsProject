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
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(reportFileName, true))) {
            // Validate fields before saving
            if (report.getReportType() == null || report.getCreatedByEmployeeId() == null || report.getStatus() == null) {
                System.err.println("Invalid report details. Skipping save.");
                return;
            }

            // Save report in consistent format
            bufferedWriter.write(report.getReportType() + "," +
                    report.getReportId() + "," +
                    report.getReportDate() + "," +
                    report.getCreatedByEmployeeId() + "," +
                    report.getStatus() + "\n");
            System.out.println("Report saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method to view specific reports based on the user email
    public String viewUserReports(String email) {
        StringBuilder userReports = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(reportFileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                // Validate line format
                String[] reportDetails = line.split(",", 5);
                if (reportDetails.length != 5) {
                    System.err.println("Invalid format in line: " + line);
                    continue;
                }

                // Check if report matches user email
                if (reportDetails[3].trim().equalsIgnoreCase(email.trim())) {
                    userReports.append("Report Type: ").append(reportDetails[0])
                            .append("\nReport ID: ").append(reportDetails[1])
                            .append("\nReport Date: ").append(reportDetails[2])
                            .append("\nCreated By: ").append(reportDetails[3])
                            .append("\nStatus: ").append(reportDetails[4])
                            .append("\n\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userReports.length() == 0 ? "No reports found for this user." : "Your Reports:\n" + userReports;
    }

    //method to display all reports
    public String viewALLReports() {

        //creating a string builder to store all reports
        StringBuilder allReports = new StringBuilder();

        //reading the file
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(reportFileName))) {
            String line;

            //looping through the file
            while ((line = bufferedReader.readLine()) != null) {

                //appending the report details to the string builder
                String[] reportDetails = line.split(",");

                //checking if the report details are complete
                if (reportDetails.length >= 5) {
                    allReports.append("\nReport Type: ").append(reportDetails[0])
                            .append("\nReport ID: ").append(reportDetails[1])
                            .append("\nReport Date: ").append(reportDetails[2])
                            .append("\nCreated By: ").append(reportDetails[3])
                            .append("\nStatus: ").append(reportDetails[4])
                            .append("\n\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allReports.length() == 0 ? "No reports found." : "\nAll Reports:\n\n" + allReports;
    }

    //method to generate a random and unique three digit ID for the report
    public int generateReportId() {

        //generating a random report ID
        int ReportID = (int) (Math.random() * 900 + 100);

        try {
            //reading the file
            bufferedReader = new BufferedReader(new FileReader(reportFileName));
            String line;

            //looping through the file
            while ((line = bufferedReader.readLine()) != null) {

                //checking if the report ID is already in use
                String[] reportDetails = line.split(",");
                if (reportDetails.length > 2 && reportDetails[2].equals(String.valueOf(ReportID))) {
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
