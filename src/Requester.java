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
                        " 3. EXIT\n");

                //get user choice
                System.out.print("TYPE LOGIN OR REGISTER: ");
                message = input.nextLine();
                out.writeObject(message);
                out.flush();

                //if user chooses to register
                if (message.equals("REGISTER")) {

                    //get user details
                    System.out.print("Enter your first name: ");
                    String firstName = input.nextLine();
                    System.out.print("Enter your second name: ");
                    String secondName = input.nextLine();
                    String email = getEmail(); //get and handle email validation
                    String password = getPassword(); //get and handle password validation

                    //create a new user object
                    user = new User(firstName, secondName, email, password);

                    try {
                        //send user details to the server
                        out.writeObject(user);
                        out.flush();

                        //get server response
                        serverResponse = (String) in.readObject();
                        System.out.println(serverResponse);

                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error in registering user");
                        e.printStackTrace();
                    }

                }

                //if user chooses to login
                else if (message.equals("LOGIN")) {
                    //get user details
                    String email = getEmail(); //get and handle email validation
                    String password = getPassword(); //get and handle password validation

                    //send user details to the server
                    out.writeObject(email);
                    out.writeObject(password);
                    out.flush();



                }

                else if (message.equals("EXIT")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                else {
                    System.out.println("Invalid choice. Please type LOGIN or REGISTER.");
                }

            }

        }
        //catch error
        catch (UnknownHostException  unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
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


