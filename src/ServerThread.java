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
            while(true) {

                //read the message from the client
                String clientMessage = (String) in.readObject();
                System.out.println("Client choice: " + clientMessage);

                //if client wants to register
                switch (clientMessage) {
                    case "REGISTER":
                        try {
                            //read the user object from the client
                            User user = (User) in.readObject();

                            //register the user
                            user.register();

                            out.writeObject("User registered successfully");
                            out.flush();
                        } catch (IOException e) {
                            System.out.println("Error in registering user");
                            e.printStackTrace();
                        } break;


                    //if client wants to log in
                    case "LOGIN":

                        //String email = (String) in.readObject();
                        //String password = (String) in.readObject();

                        break;
                    //if client wants to exit
                    case "EXIT":
                        System.out.println("Client has exited");
                        System.exit(0);
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