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

    public void login(User user) {
        System.out.println("Logging in user...");
        System.out.flush();
        if (userSearch(user.getEmail(), user.getPassword())) {
            System.out.println("User logged in successfully");
        } else {
            System.out.println("User not found");
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

    public boolean userSearch(String email, String password) {
        System.out.println("Searching for user...");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Split by spaces, but limit to 4 tokens to handle names with spaces
                String[] userArray = line.trim().split(" ", 4);

                // Ensure the format is correct
                if (userArray.length == 4) {
                    String storedEmail = userArray[2];
                    String storedPassword = userArray[3];
                    if (storedEmail.equals(email) && storedPassword.equals(password)) {
                        System.out.println("User found.");
                        return true;
                    }
                } else {
                    System.out.println("Invalid format in line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error in reading from file");
            e.printStackTrace();
        }

        System.out.println("User not found.");
        return false;
    }




}//end of class


