import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    //declaring the variables for server thread
    private Socket myConnection;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String message;
    private int result;

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

        }
        //catch error
        catch (IOException e){
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