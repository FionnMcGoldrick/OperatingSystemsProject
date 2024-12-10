import java.util.HashMap;
import java.util.Map;
import java.io.*;


public class UserManager {

    private static final String FILE_NAME = "UserDatabase.txt";
    //creating a file reader
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    //importing new user to UserDatabase.txt

    public void register(User user) {

        try {

            //writing the user to the file
            bufferedWriter.write(user.getFirstName() + " " + user.getSecondName() + " " + user.getEmail() + " " + user.getPassword() + "\n");

            //closing the file
            bufferedWriter.close();
            System.out.println("User registered successfully");

        } catch (IOException e) {
            System.out.println("Error in writing to file");
            e.printStackTrace();
        }

    }

    //method for displaying all users in the database
    public void displayUsers() {

        try {

            //reading the file
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            //closing the file
            bufferedReader.close();

        } catch (IOException e) {
            System.out.println("Error in reading from file");
            e.printStackTrace();


        }
    }


}//end of class


