import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    // Declaring the variables for server thread
    private final Socket myConnection;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    // Constructor for server thread
    public ServerThread(Socket s) {
        myConnection = s;
    }

    // Run main logic of server thread
    @Override
    public void run() {

        try {
            // Server preparing to communicate
            out = new ObjectOutputStream(myConnection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(myConnection.getInputStream());

            // Server is ready to communicate...
            while (true) {
                // Read the message from the client
                String clientMessage = (String) in.readObject();
                System.out.println("Client choice: " + clientMessage);

                // Switch statement for handling client choice
                switch (clientMessage.toUpperCase()) {

                    // Switch case for handling client choice
                    case "REGISTER": case "1":
                        handleRegister();
                        break;


                    case "LOGIN": case "2":
                        handleLogin();
                        break;

                    case "VIEW ALL REPORTS": case "3":
                        viewAllReports();
                        break;

                        case "EXIT": case "4":
                        out.writeObject("Exiting...");
                        out.flush();
                        return;

                    default:
                        out.writeObject("Invalid choice. Please choose a valid option.");
                        out.flush();
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (myConnection != null) myConnection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Method for handling user registration
    private void handleRegister() {
        try {
            User user = (User) in.readObject(); // Read user object
            user.register(); // Register user
            out.writeObject("User registered successfully");
            out.flush();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in registering user");
            e.printStackTrace();
        }
    }

    //method for handling login
    private void handleLogin() {
        try {
            String email = (String) in.readObject(); // Read email
            String password = (String) in.readObject(); // Read password

            UserManager userManager = new UserManager();
            boolean userExists = userManager.userSearch(email, password);

            if (userExists) {
                out.writeObject("User logged in successfully");
                out.flush();
                displayUserMenu(email); // Call user menu
            } else {
                out.writeObject("User not found. Please register.");
                out.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method for displaying a user-specific menu for reports
    private void displayUserMenu(String email) {
        try {
            while (true) { // Menu loop
                // Send menu options to client
                out.writeObject("\nLOGGED IN AS: " + email + "!\n\nChoose an option:\n1. CREATE\n2. VIEW\n3. CHANGE PASSWORD\n4. EXIT\n");
                out.flush();

                // Read user choice
                String userChoice = (String) in.readObject();
                System.out.println("User choice: " + userChoice);

                switch (userChoice.toUpperCase()) {
                    case "CREATE": case "1":
                        createReport(email);
                        break;

                    case "VIEW": case "2":
                        viewUserReports(email);
                        break;

                    case "CHANGE PASSWORD": case "3":
                        String newPassword = (String) in.readObject();
                        UserManager userManager = new UserManager();
                        userManager.changePassword(email, newPassword);
                        out.writeObject("Password changed successfully.");
                        out.flush();
                        break;

                    case "LOGOUT": case "4":
                        out.writeObject("Logging out...");
                        out.flush();
                        return; // Exit menu loop

                    default:
                        out.writeObject("Invalid choice. Please try again.");
                        out.flush();
                        break;
                }
            }
        } catch (EOFException e) {
            System.err.println("Client disconnected unexpectedly: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method for creating a report
    private void createReport(String email) throws IOException, ClassNotFoundException {

        // Read report object from client
        Report report = (Report) in.readObject();

        // Set report creator
        report.setCreatedByEmployeeId(email.trim().toLowerCase());

        // Save report
        saveReport(report);

        out.writeObject("Report created successfully.\nDetails: " + "\nReport Type: " + report.getReportType() + "\nReport ID: " + report.getReportId() + "\nReport Date: " + report.getReportDate() + "\nCreated By: " + report.getCreatedByEmployeeId() + "\nStatus: " + report.getStatus());
        out.flush();
    }

    //saving report to txt file
    private void saveReport(Report report) {

        try {
            //creating instance of report manager
            ReportManager reportManager = new ReportManager();

            //saving the report
            reportManager.saveReport(report);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //method for calling view reports in report manager
    private void viewAllReports(){
        try {

            //creating instance of report manager
            ReportManager reportManager = new ReportManager();

            //getting all reports
            String allReports = reportManager.viewALLReports();

            out.writeObject(allReports);
            out.flush();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //method for viewing user reports
    private void viewUserReports(String email) {
        try {

            //creating instance of report manager
            ReportManager reportManager = new ReportManager();

            //getting user reports
            String userReports = reportManager.viewUserReports(email);
            out.writeObject(userReports);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                out.writeObject("Error retrieving reports.");
                out.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


}
