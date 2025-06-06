package csc202;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class DeliverySystem {

    private ArrayList<Farmer> farmers;
    private ArrayList<Customer> customers;
    private ArrayList<Product> products;
    private ProductSearchEngine searchEngine;

    private CustomerFile customerFile;
    private FarmerFile farmerFile;
    private ProductFile productFile;

    private int nextSubscriptionID = 1;

    public DeliverySystem() {
        this.farmers = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.products = new ArrayList<>();
        this.searchEngine = new ProductSearchEngine(farmers);

        this.customerFile = new CustomerFile();
        this.farmerFile = new FarmerFile();
        this.productFile = new ProductFile();
    }

    public void registerFarmer(Farmer farmer) {
        if (farmer != null && !farmers.contains(farmer)) {
            farmers.add(farmer);
            try {
                farmerFile.writeFarmers(farmers);
            } catch (IOException e) {
                System.out.println("Error writing farmers.txt: " + e.getMessage());
            }
            this.searchEngine = new ProductSearchEngine(farmers);
        }
    }

    public void removeFarmer(int farmerID) {    
        Farmer toRemove = null;
        for (int i = 0; i < farmers.size(); i++) {
            if (farmers.get(i).getUserID() == farmerID) {
                toRemove = farmers.get(i);
                break;
            }
        }
        if (toRemove != null) {
            ArrayList<Product> owned = toRemove.getAvailableProducts();
            for (int i = 0; i < owned.size(); i++) {
                Product p = owned.get(i);
                for (int j = 0; j < products.size(); j++) {
                    if (products.get(j).getProductID() == p.getProductID()) {
                        products.remove(j);
                        break;
                    }
                }
            }
            farmers.remove(toRemove);

            try {
                farmerFile.writeFarmers(farmers);
            } catch (IOException e) {
                System.out.println("Error writing farmers.txt: " + e.getMessage());
            }
            try {
                productFile.writeProducts(products);
            } catch (IOException e) {
                System.out.println("Error writing produce.txt: " + e.getMessage());
            }
            this.searchEngine = new ProductSearchEngine(farmers);
            System.out.println("Farmer " + farmerID + " removed.");
        } else {
            System.out.println("No farmer with ID " + farmerID + " found.");
        }
    }

    public void registerCustomer(Customer customer) {
        if (customer != null && !customers.contains(customer)) {
            customers.add(customer);
            try {
                customerFile.writeCustomers(customers);
            } catch (IOException e) {
                System.out.println("Error writing customers.txt: " + e.getMessage());
            }
        }
    }

    public void removeCustomer(int customerID) {
        Customer toRemove = null;
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getUserID() == customerID) {
                toRemove = customers.get(i);
                break;
            }
        }
        if (toRemove != null) {
            customers.remove(toRemove);
            try {
                customerFile.writeCustomers(customers);
                System.out.println("Customer " + customerID + " removed.");
            } catch (IOException e) {
                System.out.println("Error writing customers.txt: " + e.getMessage());
            }
        } else {
            System.out.println("No customer with ID " + customerID + " found.");
        }
    }

    public void addProduct(Product p, Farmer f) {
        if (p != null && !products.contains(p)) {
            products.add(p);
            // Also add to owning farmer’s inventory:
            f.addProduct(p);
            System.out.println("Product added successfully!");

            try {
                productFile.writeProducts(products);
            } catch (IOException e) {
                System.out.println("Error writing produce.txt: " + e.getMessage());
            }
        }
    }

    public void removeProduct(int productID) {
        Product toRemove = null;
        for (Product product : products) {
            if (product.getProductID() == productID) {
                toRemove = product;
                break;
            }
        }
        if (toRemove != null) {
            products.remove(toRemove);
            try {
                productFile.writeProducts(products);
                System.out.println("Product " + productID + " removed.");
            } catch (IOException e) {
                System.out.println("Error writing product.txt: " + e.getMessage());
            }
        } else {
            System.out.println("No product with ID " + productID + " found.");
        }
    }
    public Product getProduct(String pName){
        for (Product product : products) {
            if (pName.equals(product.getProductName())) {
                return product;
            }
        }
        return null;
    }

    public ArrayList<Product> searchByCategory(String category) {
        return searchEngine.searchByCategory(category);
    }

    public ArrayList<Product> searchBySeason(LocalDate date) {
        return searchEngine.searchBySeason(date);
    }

    public ArrayList<Product> searchByProximity(Coordinate origin, double radius) {
        return searchEngine.searchByProximity(origin, radius);
    }

    public void processOrder(Customer customer, Product product, int quantity) {
        LocalDate now = LocalDate.now();

        if (quantity > product.getQuantityAvailable()) {
            System.out.println("Insufficient stock for \"" + product.getProductName() + "\".");
            return;
        }

        boolean canDeliver = false;
        for (Farmer f : farmers) {
            if (f.getLocationCoordinates().distanceTo(customer.getLocationCoordinates()) <= 50.0) {
                canDeliver = true;
                break;
            }
        }
        if (!canDeliver) {
            throw new DeliveryUnavailableException();
        }

        product.setQuantityAvailable(product.getQuantityAvailable() - quantity);
        customer.addToCart(product, quantity);

        try {
            productFile.writeProducts(products);
        } catch (IOException e) {
            System.out.println("Error writing produce.txt: " + e.getMessage());
        }
        try {
            customerFile.writeCustomers(customers);
        } catch (IOException e) {
            System.out.println("Error writing customers.txt: " + e.getMessage());
        }

        System.out.println("Order placed successfully.");
    }

    public Farmer matchFarmer(Product targetproduct,Customer customer) {
        double minDistance = 999999999;
        Farmer targetfarmer = null;
        for (Farmer farmer : farmers) {
            double distance = customer.getLocationCoordinates().distanceTo(farmer.getLocationCoordinates());

            if (distance < minDistance && farmer.checkProduct(targetproduct)) {
                targetfarmer = farmer;
                minDistance = distance;
            }
        }
        return targetfarmer;
    }


    public void subscribeCustomer(Customer c, String category, int quantityPerWeek) {
        Subscription s = new Subscription(nextSubscriptionID++, category, quantityPerWeek);
        c.addSubscription(s);
        try {
            customerFile.writeCustomers(customers);
        } catch (IOException e) {
            System.out.println("Error writing customers.txt: " + e.getMessage());
        }
    }

    public void unsubscribeCustomer(Customer c, int subscriptionID) {
        c.removeSubscription(subscriptionID);
        try {
            customerFile.writeCustomers(customers);
        } catch (IOException e) {
            System.out.println("Error writing customers.txt: " + e.getMessage());
        }
    }

    public void displayAllFarmers() {
        for (int i = 0; i < farmers.size(); i++) {
            Farmer f = farmers.get(i);
            f.displayUser();
            f.displayAvailableProducts();
            System.out.println();
        }
    }

    public void displayAllCustomers() {
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            c.displayUser();
            c.displayShoppingCart();
            System.out.println();
        }
    }
}
