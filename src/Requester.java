import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.net.Socket;

public class Requester {

    //declare variables
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    String serverResponse;
    int result;
    Scanner input;

    //constructor ensures every client has own instance of scanner
    Requester() {

        input = new Scanner(System.in);

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
            System.out.println("Enter a message to send to the server: ");
            message = input.nextLine();
            out.writeObject(message);
            out.flush();
            System.out.println("client>" + message);

            serverResponse = (String) in.readObject();
            System.out.println("server>" + serverResponse);


        }
        //catch UnknownHostException and IoException
        catch (UnknownHostException  unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
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


