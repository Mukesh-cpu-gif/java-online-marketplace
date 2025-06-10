package csc202;

import java.util.ArrayList;

public class Farmer extends User {

    private ArrayList<Product> availableProducts;

    Farmer() {
    }

    Farmer(int id, String name, String email, String address, String password, Coordinate location) {
        super(id, name, email, address, password, location);
        this.availableProducts = new ArrayList<>();
    }

    public ArrayList<Product> getAvailableProducts() {
        return availableProducts;
    }

    public void addProduct(Product p) {
        availableProducts.add(p);
    }

    public void removeProduct(Product p) {
        availableProducts.remove(p);
    }

    public void clearAvailableProducts(){
        availableProducts.clear();
    }

    @Override
    public void displayUser() {
        System.out.println("\nFarmer ID: " + getUserID());
        System.out.println("Farmer Name: " + getUserName());
        System.out.println("Farmer Email: " + getUserEmail());
        System.out.println("Farm Address: " + getUserAddress());
        System.out.println("Location Coordinates: " + getLocationCoordinates());
    }

    public void displayAvailableProducts() {
        if (availableProducts.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.println("\nAvailable Products:");
        for (Product p : availableProducts) {
            System.out.println("\n   - " + p);
        }
    }

    public boolean checkProduct(Product product){
        return availableProducts.contains(product);
    }
}
