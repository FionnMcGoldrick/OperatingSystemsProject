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
                        " 3. VIEW REPORTS\n" +
                        " 4. EXIT\n");

                //get user choice
                System.out.print("TYPE CHOICE: ");
                message = input.nextLine();
                out.writeObject(message);
                out.flush();

                //if user chooses to register
                if (message.equals("REGISTER") || message.equals("register")) {
                    handleRegistration();
                }

                //if user chooses to Login
                else if (message.equals("LOGIN") || message.equals("login")) {
                    handleLogin();
                }

                //if user chooses to view reports
                else if (message.equals("VIEW REPORTS")) {

                    // Read and display server response
                    String serverResponse = (String) in.readObject();
                    System.out.println(serverResponse);
                }

                //if user chooses to exit
                else if (message.equals("EXIT")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                else {
                    System.out.println("Invalid choice. Please type LOGIN or REGISTER.");
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
    private void handleRegistration() throws IOException, ClassNotFoundException {

        //Client Inputs for first name, last name, email and password
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();
        String email = getEmail();
        String password = getPassword();
        User user = new User(firstName, lastName, email, password);
        out.writeObject(user);
        out.flush();

        String response = (String) in.readObject();
        System.out.println(response);
    }

    private void handleLogin() throws IOException, ClassNotFoundException {

        //Client Inputs for email and password
        System.out.print("Enter email: ");
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
            String choice = input.nextLine();
            out.writeObject(choice);
            out.flush();

            // If user chooses to create a report
            if (choice.equalsIgnoreCase("CREATE")) {

                // Gather report details from the client
                System.out.print("Enter Report Type (Accident Report/New Health and Safety Risk Report): ");
                String reportType = input.nextLine();
                System.out.print("Enter Report ID: ");
                String reportId = input.nextLine();
                System.out.print("Enter Report Date (YYYY-MM-DD): ");
                String reportDate = input.nextLine();
                System.out.print("Enter Report Status (Open/Assigned/Closed): ");
                String status = input.nextLine();

                // Create the report object
                Report report = new Report(reportType, reportId, reportDate, "", status);

                // Send the report object to the server
                out.writeObject(report);
                out.flush();

                // Read and display server response
                String serverResponse = (String) in.readObject();
                System.out.println(serverResponse);

            } else if (choice.equalsIgnoreCase("VIEW")) {

                // Read and display server response
                String serverResponse = (String) in.readObject();
                System.out.println(serverResponse);

            } else if (choice.equalsIgnoreCase("EXIT")) {
                // Read and display server response
                String serverResponse = (String) in.readObject();
                System.out.println(serverResponse);
                break;
            } else {
                // Read and display server response
                String serverResponse = (String) in.readObject();
                System.out.println(serverResponse);
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
            if (email.contains(validEmail)) {
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


