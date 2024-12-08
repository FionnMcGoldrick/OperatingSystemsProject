import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    //declaring the variables for server thread
    private Socket myConnection;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String clientMessage;
    private String serverMessage;
    private int result;
    private User user;

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
            while(true) {

                //read the message from the client
                clientMessage = (String) in.readObject();
                System.out.println("Client choice: " + clientMessage);

                //if client wants to register
                if (clientMessage.equals("REGISTER")) {

                    //read the user object from the client
                    user = (User) in.readObject();

                    //register the user
                    user.register();

                }
                //if client wants to login
                else if (clientMessage.equals("LOGIN")) {

                    String username = (String) in.readObject();
                    String password = (String) in.readObject();

                }

            }


        }
        //catch errors
        catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
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