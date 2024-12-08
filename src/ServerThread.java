import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    //declaring the variables for server thread
    private final Socket myConnection;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    //constructor for server thread
    public ServerThread(Socket s)
    {
        //initializing the socket
        myConnection = s;
    }

    //run main logic of server thread
    public void run(){


        try{

            //server preparing to communicate
            out = new ObjectOutputStream(myConnection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(myConnection.getInputStream());

            //server is ready to communicate...
            label:
            while(true) {

                //read the message from the client
                String clientMessage = (String) in.readObject();
                System.out.println("Client choice: " + clientMessage);

                //if client wants to register
                switch (clientMessage) {
                    case "REGISTER":

                        //read the user object from the client
                        //private String serverMessage;
                        //private int result;
                        User user = (User) in.readObject();

                        //register the user
                        user.register();

                        break;
                    //if client wants to log in
                    case "LOGIN":

                        //String username = (String) in.readObject();
                        //String password = (String) in.readObject();

                        break;
                    //if client wants to exit
                    case "EXIT":
                        System.out.println("Client has exited");
                        break label;
                }

            }


        }
        //catch errors
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        //for closing the connection
        finally{

            //closing the connection
            try{
                in.close();
                out.close();
                myConnection.close();
            }
            //catch error
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }

    }



}