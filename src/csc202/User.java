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
            Coordinate locationCoordinates) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userPassword = userPassword;
        this.locationCoordinates = locationCoordinates;
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

    public void setLocationCoordinates(Coordinate locationCoordinates) {
        this.locationCoordinates = locationCoordinates;
    }

    public String getStoredPassword() {
        return userPassword;
    }

    public boolean userAuthentication(String enteredPassword) {
        return this.userPassword.equals(enteredPassword);
    }

    public boolean getSeasonalUpdates() {
        return seasonalUpdates;
    }

    public void setSeasonalUpdates(boolean seasonalUpdates) {
        this.seasonalUpdates = seasonalUpdates;
    }

    public abstract void displayUser();
}
