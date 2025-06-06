package csc202;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


public class DeliverySystem {

    private ArrayList<Farmer> farmers;
    private ArrayList<Customer> customers;
    private ArrayList<Product> products;
    private ProductSearchEngine searchEngine;

    private CustomerFile customerFile;
    private FarmerFile farmerFile;
    private ProductFile productFile;

    private int nextSubscriptionID = 1;

    // Constructor for delivery system
    DeliverySystem() {
        this.farmers = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.products = new ArrayList<>();
        this.searchEngine = new ProductSearchEngine(farmers);

        this.customerFile = new CustomerFile();
        this.farmerFile = new FarmerFile();
        this.productFile = new ProductFile();
    }
    //check a farmer is there or no
    public Farmer checkFarmer(int farmerID){
        for (Farmer farmer : farmers){
            if (farmer.getUserID() == farmerID){
                return farmer;
            }
        }
        return null;
    }

    //check a customer is there or no
    public Customer checkCustomer(int customerID){
        for (Customer customer : customers){
            if (customer.getUserID() == customerID){
                return customer;
            }
        }
        return null;
    }

    // check a product is there or no with pName
    public Product checkProduct(String pName){
        for (Product product : products) {
            if (pName.equals(product.getProductName())) {
                return product;
            }
        }
        return null;
    }

    // check a product is there or no with pID
    public Product checkProduct(int pID){
        for (Product product : products) {
            if (pID == product.getProductID()) {
                return product;
            }
        }
        return null;
    }

    // REGISTER a farmer
    public void registerUser(User user) {
        System.out.print("\nEnter ID (int): ");
        int id = Integer.parseInt(Main.getInput().nextLine().trim());
        System.out.print("Enter name: ");
        String name = Main.getInput().nextLine().trim();
        System.out.print("Enter email: ");
        String email = Main.getInput().nextLine().trim();
        System.out.print("Enter address: ");
        String address = Main.getInput().nextLine().trim();
        System.out.print("Enter password: ");
        String password = Main.getInput().nextLine().trim();
        System.out.print("Enter location X coordinate: ");
        double x = Double.parseDouble(Main.getInput().nextLine().trim());
        System.out.print("Enter location Y coordinate: ");
        double y = Double.parseDouble(Main.getInput().nextLine().trim());
        Coordinate coord = new Coordinate(x, y);

        if (user instanceof Farmer){
            Farmer newFarmer = new Farmer(id, name, email, address, password, coord);
            try {
                if (!farmers.contains(newFarmer)) {
                    farmers.add(newFarmer);
                    farmerFile.writeFarmers(farmers);
                    searchEngine.setFarmersProductSearchEngine(farmers);
                }
                System.out.println("Farmer registered successfully!");
                displayAllFarmers();
            } catch (IOException e) {
                System.out.println("Error writing farmers.txt: " + e.getMessage());
            }
        } else if(user instanceof Customer){
            Customer newCustomer = new Customer(id, name, email, address, password, coord);
            try {
                System.out.print("Receive seasonal updates? (yes/no): ");
                String upd = Main.getInput().nextLine().trim().toLowerCase();
                if (upd.equals("yes")) {
                    newCustomer.setSeasonalUpdates(true);
                }
                if (!customers.contains(newCustomer)) {
                    customers.add(newCustomer);
                    customerFile.writeCustomers(customers);
                }
                System.out.println("Customer registered successfully!");
                displayAllCustomers();
            } catch (IOException e) {
                System.out.println("Error writing customers.txt: " + e.getMessage());
            } catch (Exception e){
                System.out.println("Error registering customer!" + e.getMessage());
            }
        }
    }

    // login as farmer
    public Farmer loginFarmer() {
        Farmer loggedInFarmer = null;
        System.out.print("\nEnter Farmer ID: ");
        int farmerID;
        try {
            farmerID = Integer.parseInt(Main.getInput().nextLine().trim());
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid ID format.");
            return loggedInFarmer;
        }
        System.out.print("Enter password: ");
        String farmerPassword = Main.getInput().nextLine().trim();
        for (Farmer f : farmers) {
            if (f.getUserID() == farmerID && f.userAuthentication(farmerPassword)) {
                loggedInFarmer = f;
                break;
            }
        }
        return loggedInFarmer;
    }

    public Customer loginCustomer() {
        Customer loggedInCustomer = null;
        System.out.print("\nEnter Customer ID: ");
        int customerID;
        try {
            customerID = Integer.parseInt(Main.getInput().nextLine().trim());
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid ID format.");
            return loggedInCustomer;
        }
        System.out.print("Enter password: ");
        String customerPassword = Main.getInput().nextLine().trim();
        for (Customer c : customers) {
            if (c.getUserID() == customerID && c.userAuthentication(customerPassword)) {
                loggedInCustomer = c;
                break;
            }
        }
        return loggedInCustomer;
    }

    // Remove a user
    public void removeUser(User user) {
        System.out.print("\nEnter User ID to remove: ");
        int userID;
        try {
            userID = Integer.parseInt(Main.getInput().nextLine().trim());
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid ID format.");
            return;
        }
        if (user instanceof Farmer) {
            Farmer farmerToRemove = checkFarmer(userID);

            if (farmerToRemove != null) {
                farmerToRemove.clearAvailableProducts();
                farmers.remove(farmerToRemove);

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
                searchEngine.setFarmersProductSearchEngine(farmers);
                System.out.println("Farmer " + userID + " removed.");
            } else {
                System.out.println("No farmer with ID " + userID + " found.");
            }
        }else if(user instanceof Customer) {
            Customer customerToRemove = checkCustomer(userID);

            if (customerToRemove != null) {
                customerToRemove.clearShoppingCart();
                customerToRemove.clearSubscriptions();
                customers.remove(customerToRemove);

                try {
                    customerFile.writeCustomers(customers);
                } catch (IOException e) {
                    System.out.println("Error writing customers.txt: " + e.getMessage());
                }
                System.out.println("Customer " + userID + " removed.");
            } else {
                System.out.println("No Customer with ID " + userID + " found.");
            }
        }
    }

    //display all farmers
    public void displayAllFarmers() {
        for (Farmer f : farmers) {
            f.displayUser();
            f.displayAvailableProducts();
            System.out.println();
        }
    }

    //display all customers
    public void displayAllCustomers() {
        for (Customer c : customers) {
            c.displayUser();
            c.displayShoppingCart();
            System.out.println();
        }
    }

    // adding product by a farmer
    public void addProduct(Farmer currentFarmer) {
        System.out.print("\nEnter Product ID: ");
        int id = Integer.parseInt(Main.getInput().nextLine().trim());
        System.out.print("Enter Product name: ");
        String name = Main.getInput().nextLine().trim();
        System.out.print("Enter Description: ");
        String description = Main.getInput().nextLine().trim();
        System.out.print("Enter product category: ");
        String category = Main.getInput().nextLine().trim();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(Main.getInput().nextLine().trim());
        System.out.print("Enter quantity available: ");
        int quantityAvailable = Integer.parseInt(Main.getInput().nextLine().trim());
        System.out.print("Enter Harvest Date in YYYY-MM-DD format: ");
        LocalDate harvestDate = LocalDate.parse(Main.getInput().nextLine().trim());
        if (harvestDate.isBefore(LocalDate.now()) ) {
            throw new HarvestDateException();
        }
        System.out.print("Is it seasonal? (yes/no): ");
        String seasonalInput = Main.getInput().nextLine().trim();
        String season;
        if(seasonalInput.equalsIgnoreCase("yes")){
            season = Product.getSeasonFromDate(harvestDate);
        }
        else{
            season = "all";
        }
        System.out.print("Is it organic? (yes/no): ");
        String organicInput = Main.getInput().nextLine().trim();
        boolean organic = organicInput.equalsIgnoreCase("yes");

        Product newProduct = new Product(id, name, description, category, price, quantityAvailable, harvestDate, season, organic);
        try {
            if (!products.contains(newProduct)) {
                products.add(newProduct);
                // Also add to owning farmer’s inventory:
                currentFarmer.addProduct(newProduct);
                productFile.writeProducts(products);
                System.out.println("Product added successfully!");
            }
        } catch (IOException e) {
            System.out.println("Error writing produce.txt: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error adding product!");
        }
    }

    // remove product by farmer
    public void removeProduct(Farmer currentFarmer) {
        System.out.print("\nEnter Product ID to remove: ");
        int productID;
        try {
            productID = Integer.parseInt(Main.getInput().nextLine().trim());
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid ID format.");
            return;
        }
        Product productToRemove = checkProduct(productID);
        if (productToRemove != null) {
            try {
                products.remove(productToRemove);
                // Also remove from owning farmer’s inventory:
                currentFarmer.removeProduct(productToRemove);
                productFile.writeProducts(products);
                System.out.println("Product " + productID + " removed.");
            } catch (IOException e) {
                System.out.println("Error writing product.txt: " + e.getMessage());
            }
        } else {
            System.out.println("No product with ID " + productID + " found.");
        }
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

        if(product.getSeason().equalsIgnoreCase(Product.getSeasonFromDate(now)) && product.getQuantityAvailable() == 0){
            throw new OutOfSeasonException(product.getProductName() +" is not in season and no quantity available.");
        }

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
}
