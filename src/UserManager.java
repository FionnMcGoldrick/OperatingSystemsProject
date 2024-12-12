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
            bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true));
            bufferedWriter.write(user.getFirstName() + " " + user.getSecondName() + " " + user.getEmail() + " " + user.getPassword() + "\n");

            //closing the file
            bufferedWriter.close();
            System.out.println("User registered successfully\n\n< DISPLAYING ALL USERS >");
            displayUsers();

        } catch (IOException e) {
            System.out.println("Error in writing to file");
            e.printStackTrace();
        }

    }

    //method for displaying all users in the database
    public void displayUsers() {

        try {

            bufferedReader = new BufferedReader(new FileReader(FILE_NAME));

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


    //check if user exists for login
    public void userSearch(String email, String password) {

        System.out.println("Searching for user...");
        System.out.flush();
        try {
            bufferedReader = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] userArray = line.split(" ");
                if (userArray.length >= 4 && userArray[2].equals(email) && userArray[3].equals(password)) {
                    System.out.println("User exists");
                    return;
                }
            }
            System.out.println("User does not exist");
        } catch (IOException e) {
            System.out.println("Error in reading from file");
            e.printStackTrace();
        }
    }


}//end of class


