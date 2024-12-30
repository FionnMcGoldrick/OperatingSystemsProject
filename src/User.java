import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User extends UserManager implements Serializable {

    //declare variables
    private String firstName;
    private String secondName;
    private String email;
    private String password;
    private String employeeId;
    private String departmentName;
    private String role;


    //constructor to initialize the variables
    public User(String firstName, String secondName, String email, String password, String employeeId, String departmentName, String role) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
        this.employeeId = employeeId;
        this.departmentName = departmentName;
        this.role = role;
    }

    public void login(){

        //Calling the login method from UserManager
        UserManager userManager = new UserManager();
        userManager.login(this);
    }

    public void register(){

        //Calling  the register method from UserManager
        UserManager userManager = new UserManager();
        userManager.register(this);

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

    public String getEmployeeId() {
        return employeeId;
    }

    public String setEmployeeId(String employeeId) {
        return employeeId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String setDepartmentName(String departmentName) {
        return departmentName;
    }

    public String getRole() {
        return role;
    }

    public String setRole(String role) {
        return role;
    }


}
