package csc202;

public abstract class User {

    private int userID;
    private String userName;
    private String userEmail;
    private String userAddress;
    private String userPassword;
    private Coordinate locationCoordinates;
    private boolean seasonalUpdates = false;

    User() {
    }

    public User(int userID, String userName, String userEmail,
                String userAddress, String userPassword,
                Coordinate locationCoordinates) throws IllegalArgumentException{
        setUserID(userID);
        setUserName(userName);
        setUserEmail(userEmail);
        setUserAddress(userAddress);
        setUserPassword(userPassword);
        setLocationCoordinates(locationCoordinates);
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public Coordinate getLocationCoordinates() {
        return locationCoordinates;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public boolean getSeasonalUpdates() {
        return seasonalUpdates;
    }

    public void setUserID(int userID) {
        if (userID <= 0) {
            throw new IllegalArgumentException("User ID can't be negative or zero.");
        }
        this.userID = userID;
    }

    public void setUserName(String userName) {
        if(userName == null || userName.trim().isEmpty()){
            throw new IllegalArgumentException("User Name can't be null or empty.");
        }
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        while (true) {
            try {
                if (!userEmail.trim().contains("@")) {
                    throw new IllegalArgumentException("Email ID should contain '@'.");
                }
                this.userEmail = userEmail;
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.print("Enter a valid email: ");
                userEmail = Main.getInput().nextLine().trim();
            }
        }
    }

    public void setUserAddress(String userAddress) {
        if(userAddress == null || userAddress.trim().isEmpty()){
            throw new IllegalArgumentException("User Address can't be null or empty.");
        }
        this.userAddress = userAddress;
    }

    public void setUserPassword(String userPassword) {
        if(userPassword == null || userPassword.trim().isEmpty()){
            throw new IllegalArgumentException("User Password can't be null or empty.");
        }
        this.userPassword = userPassword;
    }

    public void setLocationCoordinates(Coordinate locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }

    public void setSeasonalUpdates(boolean seasonalUpdates) {
        this.seasonalUpdates = seasonalUpdates;
    }

    public boolean userAuthentication(String enteredPassword) {
        return this.userPassword.equals(enteredPassword);
    }

    public abstract void displayUser();
}
