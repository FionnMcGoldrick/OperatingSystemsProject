import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args){

        //Declaring the variables
        ServerSocket provider;
        Socket connection;
        ServerThread handler;


        try {

            //Creating a server socket
            provider = new ServerSocket(2004, 10);
            System.out.println("Server is running and listening on port 2004...");

            //looping to accept multiple connections
            while (true) {

                //Creating a server socket
                connection = provider.accept();
                handler = new ServerThread(connection);
                handler.start();

            }


        }
        //catching the exception
        catch (IOException e) {
            System.out.println("Error in creating server socket");
            e.printStackTrace();
        }


    }

}