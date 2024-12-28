import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    // Declaring the variables for server thread
    private final Socket myConnection;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    // Constructor for server thread
    public ServerThread(Socket s) {
        myConnection = s;
    }

    // Run main logic of server thread
    @Override
    public void run() {

        try {
            // Server preparing to communicate
            out = new ObjectOutputStream(myConnection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(myConnection.getInputStream());

            // Server is ready to communicate...
            while (true) {
                // Read the message from the client
                String clientMessage = (String) in.readObject();
                System.out.println("Client choice: " + clientMessage);

                switch (clientMessage) {
                    case "REGISTER":
                        try {
                            User user = (User) in.readObject(); // Read user object
                            user.register(); // Register user
                            out.writeObject("User registered successfully");
                            out.flush();
                        } catch (IOException | ClassNotFoundException e) {
                            System.out.println("Error in registering user");
                            e.printStackTrace();
                        }
                        break;

                    case "LOGIN":
                        String email = (String) in.readObject(); // Read email
                        String password = (String) in.readObject(); // Read password

                        UserManager userManager = new UserManager();
                        boolean userExists = userManager.userSearch(email, password);

                        if (userExists) {
                            out.writeObject("User logged in successfully");
                            out.flush();
                            displayUserMenu(email); // Call user menu
                        } else {
                            out.writeObject("User not found. Please register.");
                            out.flush();
                        }
                        break;

                    default:
                        out.writeObject("Invalid choice. Please choose REGISTER or LOGIN.");
                        out.flush();
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (myConnection != null) myConnection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method for displaying a user-specific menu for reports
    private void displayUserMenu(String email) {
        try {
            while (true) { // Menu loop
                System.out.println("User logged in: " + email);
                out.writeObject("Welcome " + email + "!");
                out.writeObject("Please choose an option:\n1. CREATE\n2. VIEW\n3. EXIT");
                out.flush();

                String userChoice = (String) in.readObject(); // Read user choice
                switch (userChoice) {
                    case "CREATE": // Create report
                        out.writeObject("Creating report...");
                        out.flush();
                        break;

                    case "VIEW": // View reports
                        out.writeObject("Viewing reports...");
                        break;

                    case "EXIT": // Logout
                        out.writeObject("Logging out...");
                        out.flush();
                        return; // Exit the menu loop

                    default: // Invalid choice
                        out.writeObject("Invalid choice. Please try again.");
                        out.flush();
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
