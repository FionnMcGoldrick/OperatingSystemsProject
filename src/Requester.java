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
                        " 2. LOGIN\n");

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
                    System.out.print("Enter your email: ");
                    String email = input.nextLine();
                    System.out.print("Enter your password: ");
                    String password = input.nextLine();

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


}


