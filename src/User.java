import java.io.Serializable;

public class User implements Serializable {

    //declare variables
    private String firstName;
    private String secondName;
    private String email;
    private String password;

    //constructor to initialize the variables
    public User(String firstName, String secondName, String email, String password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
    }

    public void login(){
        //login logic
    }

    public void logout(){
        //logout logic
    }

    public void register(){
        //register logic
        System.out.println("User registered successfully");
    }



    //getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(){
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
