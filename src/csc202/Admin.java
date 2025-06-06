package csc202;

public class Admin extends User{

    public Admin() {
        setUserID(1);
        setUserName("Admin");
        setUserPassword("admin123");
    }

    @Override
    public void displayUser() {
        System.out.println("Admin ID: " + getUserID());
        System.out.println("Admin Name: " + getUserName());
        System.out.println("Admin Password: " + getUserPassword());
    }
}
