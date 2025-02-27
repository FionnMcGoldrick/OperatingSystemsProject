import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.net.Socket;

public class Requester{

    //declare variables
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    String serverResponse;
    //int choice;
    Scanner input;
    User user;
    Report report;

    //constructor ensures every client has own instance of scanner
    Requester() {

        input = new Scanner(System.in);

    }

    public static void main(String[] args) {
        Requester client = new Requester();
        client.run();
    }

    //run method
    void run() {

        try {

            //creating a socket to connect to the server
            requestSocket = new Socket("127.0.0.1", 2004);

            //print connection message
            System.out.println("Connected to localhost in port 2004");

            //get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());


            //Begin communicating with the server
            while(true) {

                //print menu
                System.out.println("\n< -Healthcare Management System ->\n\n" +
                        " 1. REGISTER\n" +
                        " 2. LOGIN\n" +
                        " 3. VIEW ALL REPORTS\n" +
                        " 4. EXIT\n");

                //get user choice
                System.out.print("ENTER CHOICE: ");
                message = input.nextLine();
                out.writeObject(message);
                out.flush();

                //if user chooses to register
                if (message.equalsIgnoreCase("REGISTER") || message.equals("1")) {
                    handleRegistration();
                }

                //if user chooses to Login
                else if (message.equalsIgnoreCase("LOGIN") || message.equals("2")) {
                    handleLogin();
                }

                //if user chooses to view reports
                else if (message.equalsIgnoreCase("VIEW ALL REPORTS") || message.equals("3")) {

                    // Read and display server response
                    String serverResponse = (String) in.readObject();
                    System.out.println(serverResponse);
                }

                //if user chooses to exit
                else if (message.equalsIgnoreCase("EXIT") || message.equals("4")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                else {
                    System.out.println("Invalid choice. Please type one of the choices provided.");
                }

            }

        }
        //catch errors
        catch (UnknownHostException  unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        catch (ClassNotFoundException classNot) {
            System.err.println("Data received in unknown format");
        }

        finally {
            //closing connection
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    //Methods

    //method to handle user registration
    private void handleRegistration() throws IOException, ClassNotFoundException {

        //Client Inputs
        System.out.print("\n<REGISTER>\nEnter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        String email = getEmail();

        String password = getPassword();

        String empID = new UserManager().handleEmpID();

        System.out.print("Enter department: ");
        String department = input.nextLine();

        System.out.print("Enter role: ");
        String role = input.nextLine();

        User user = new User(firstName, lastName, email, password, empID, department, role);
        out.writeObject(user);
        out.flush();

        String response = (String) in.readObject();
        System.out.println(response);
    }

    //method to handle user login
    private void handleLogin() throws IOException, ClassNotFoundException {

        //Client Inputs for email and password
        System.out.print("\n<LOGIN>\nEnter email: ");
        String email = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();

        //Sending email and password to server
        out.writeObject(email);
        out.writeObject(password);
        out.flush();

        //server response
        String response = (String) in.readObject();
        System.out.println(response);

        if(response.contains("successfully")) {
            displayUserMenu();
        }

    }

    // Method to display user menu
    private void displayUserMenu() throws IOException, ClassNotFoundException {
        while (true) {

            // Read menu options from the server
            System.out.println((String) in.readObject());

            // Get user input
            System.out.print("ENTER CHOICE: ");
            String choice = input.nextLine();
            out.writeObject(choice);
            out.flush();

            switch (choice.toUpperCase()) {
                case "CREATE": case "1":
                    // Gather report details
                    String reportType = new ReportManager().handleReportType();
                    int reportId = new ReportManager().generateReportId();
                    System.out.print("Enter Report Date (YYYY-MM-DD): ");
                    String reportDate = input.nextLine();
                    String reportStatus = new ReportManager().handleReportStatus();

                    // Create and send report
                    Report report = new Report(reportType, reportId, reportDate, "", reportStatus);
                    out.writeObject(report);
                    out.flush();

                    // Display server response
                    System.out.println((String) in.readObject());
                    break;

                case "VIEW": case "2":
                    // Display reports
                    System.out.println((String) in.readObject());
                    break;

                case "CHANGE PASSWORD": case "3":
                    // Change password
                    String newPassword = getPassword();
                    out.writeObject(newPassword);
                    out.flush();

                    // Display server response
                    System.out.println((String) in.readObject());
                    break;

                case "LOGOUT": case "4":
                    System.out.println((String) in.readObject());
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // method to get and validate email
    private String getEmail() {
        String validEmail = "@gmail.com";
        String email;
        while (true) {
            System.out.print("Enter your email: ");
            email = input.nextLine();

            //calling method to ensure email does not already exist
            boolean emailExists = new UserManager().emailExists(email);
            if (email.contains(validEmail) && emailExists == true) {
                break;
            } else {
                System.out.println("Invalid email. Please include @gmail.com in the email.");
            }

        }

        return email;
    }

    //method to get and validate password
    private String getPassword(){
        String password;
        while(true){
            System.out.print("Enter your password: ");
            password = input.nextLine();


            // ensuring password is more than 6 characters long and contains at least one letter and one number
            if (password.length() > 6 && password.matches(".*[a-zA-Z].*") && password.matches(".*[0-9].*")) {
                break;
            } else {
                System.out.println("Password must be more than 6 characters long and contain at least one letter and one number.");
            }
        }
        return password;
    }






}


