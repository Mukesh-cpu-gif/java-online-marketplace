package csc202;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static Scanner input = new Scanner(System.in);
    private static DeliverySystem system = new DeliverySystem();

    private static ArrayList<Farmer> farmers = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<Product> products = new ArrayList<>();

    public static void main(String[] args) {

        while (true) {
            System.out.println("""
            \n=== Welcome to the Online Marketplace ===");
            1) Login as Farmer
            2) Login as Customer
            3) Register as Farmer
            4) Register as Customer
            5) Exit
            Enter your choice: """); // should we have one menu for login and register then customer and farmer in another menu

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1:
                    loginFarmer();
                    break;

                case 2:
                    loginCustomer();
                    break;

                case 3:
                    registerFarmer();
                    break;

                case 4:
                    registerCustomer();
                    break;

                case 5:
                    System.out.println("Exiting the Marketplace System. \n Hope to see you again.\n Goodbye!");
                    return;

                default:
                    System.out.println("Please choose a valid option (1–5).");
                    break;
            }
        }
    }

    public static void loginFarmer() {
        Farmer loggedInFarmer = null;
        System.out.print("\nEnter Farmer ID: ");
        int farmerID;
        try {
            farmerID = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid ID format.");
            return;
        }
        System.out.print("Enter password: ");
        String farmerPassword = input.nextLine().trim();
        for (Farmer f : farmers) {
            if (f.getUserID() == farmerID && f.userAuthentication(farmerPassword)) {
                loggedInFarmer = f;
                break;
            }
        }
        if (loggedInFarmer == null) {
            System.out.println("Invalid farmer credentials.");
            return;
        }
        System.out.println("Logged in as farmer " + loggedInFarmer.getUserName() + ".");
        farmerMenu(loggedInFarmer);
    }

    public static void loginCustomer() {
        Customer loggedInCustomer = null;
        System.out.print("\nEnter Customer ID: ");
        int customerID;
        try {
            customerID = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid ID format.");
            return;
        }
        System.out.print("Enter password: ");
        String customerPassword = input.nextLine().trim();
        for (Customer c : customers) {
            if (c.getUserID() == customerID && c.userAuthentication(customerPassword)) {
                loggedInCustomer = c;
                break;
            }
        }
        if (loggedInCustomer == null) {
            System.out.println("Invalid customer credentials.");
            return;
        }
        System.out.println("Logged in as customer " + loggedInCustomer.getUserName() + ".");
        customerMenu(loggedInCustomer);
    }

    public static void registerFarmer() {
        try {
            System.out.print("\nEnter Farmer ID (int): ");
            int idF = Integer.parseInt(input.nextLine().trim());
            System.out.print("Enter name: ");
            String nameF = input.nextLine().trim();
            System.out.print("Enter email: ");
            String emailF = input.nextLine().trim();
            System.out.print("Enter farm address: ");
            String addressF = input.nextLine().trim();
            System.out.print("Enter password: ");
            String passwordF = input.nextLine().trim();
            System.out.print("Enter location X coordinate: ");
            double xF = Double.parseDouble(input.nextLine().trim());
            System.out.print("Enter location Y coordinate: ");
            double yF = Double.parseDouble(input.nextLine().trim());
            Coordinate coordF = new Coordinate(xF, yF);
            Farmer newFarmer = new Farmer(idF, nameF, emailF, addressF, passwordF, coordF);
            system.registerFarmer(newFarmer);
            farmers.add(newFarmer);
            System.out.println("Farmer registered successfully!");
        } catch (Exception e) {
            System.out.println("Error registering farmer: " + e.getMessage());
        }
    }

    public static void registerCustomer() {
        try {
            System.out.print("\nEnter Customer ID (int): ");
            int idC = Integer.parseInt(input.nextLine().trim());
            System.out.print("Enter name: ");
            String nameC = input.nextLine().trim();
            System.out.print("Enter email: ");
            String emailC = input.nextLine().trim();
            System.out.print("Enter delivery address: ");
            String addressC = input.nextLine().trim();
            System.out.print("Enter password: ");
            String passwordC = input.nextLine().trim();
            System.out.print("Enter location X coordinate: ");
            double xC = Double.parseDouble(input.nextLine().trim());
            System.out.print("Enter location Y coordinate: ");
            double yC = Double.parseDouble(input.nextLine().trim());
            Coordinate coordC = new Coordinate(xC, yC);
            Customer newCustomer = new Customer(idC, nameC, emailC, addressC, passwordC, coordC);
            System.out.print("Receive seasonal updates? (yes/no): ");
            String upd = input.nextLine().trim().toLowerCase();
            if (upd.equals("yes")) {
                newCustomer.setSeasonalUpdates(true);
            }
            system.registerCustomer(newCustomer);
            customers.add(newCustomer);
            System.out.println("Customer registered successfully!");
        } catch (Exception e) {
            System.out.println("Error registering customer: " + e.getMessage());
        }

    }

    private static void farmerMenu(Farmer me) {
        while (true) {
            System.out.println("""
            \n=== Farmer Menu ===
            1) View my inventory
            2) Add product to inventory
            3) Remove product from inventory
            4) Logout");
            Choice: """);
            int fchoice;
            try {
                fchoice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }
            switch (fchoice) {
                case 1:
                    me.displayUser();
                    me.displayAvailableProducts();
                    break;

                case 2:
                    handleAddProduct(me);
                    break;

                case 3:
                    handleRemoveProduct();
                    break;

                case 4:
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Please choose a valid option (1–4).");
                    break;
            }
        }
    }

    private static void handleAddProduct(Farmer me) {
        try {
            System.out.print("\nEnter Product ID: ");
            int id = Integer.parseInt(input.nextLine().trim());
            System.out.print("Enter Product name: ");
            String name = input.nextLine().trim();
            System.out.print("Enter Description: ");
            String description = input.nextLine().trim();
            System.out.print("Enter product category: ");
            String category = input.nextLine().trim();
            System.out.print("Enter price: ");
            double price = Double.parseDouble(input.nextLine().trim());
            System.out.print("Enter quantity available: ");
            int quantityAvailable = Integer.parseInt(input.nextLine().trim());
            System.out.print("Enter Harvest Date in YYYY-MM-DD format: ");
            LocalDate harvestDate = LocalDate.parse(input.nextLine().trim());
            if (harvestDate.isBefore(LocalDate.now()) ) {
                throw new HarvestDateException();
            }
            System.out.print("Enter Season: "); // edit it
            String season = input.nextLine().trim();
            System.out.print("Is it organic? (yes/no): ");
            String oranicInput = input.nextLine().trim();
            boolean organic = oranicInput.equalsIgnoreCase("yes");

            Product newProduct = new Product(id, name, description, category, price, quantityAvailable, harvestDate, season, organic);
            system.addProduct(newProduct, me );
        } catch (Exception e) {
            System.out.println("Error adding product!");
        }
    }

    private static void handleRemoveProduct() {
        System.out.print("\nEnter Product ID to remove: ");
        try {
            int productID = Integer.parseInt(input.nextLine().trim());
            system.removeProduct(productID);
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid ID format.");
        }
    }

    private static void customerMenu(Customer me) {
        while (true) {
            System.out.println("""
            \n=== Customer Menu ===
            1) View all farmers and inventories
            2) Add product to cart
            3) Remove product to cart
            4) View my cart
            5) Search products by category
            6) Search products by season
            7) Search products by proximity
            8) Match product by product name & farmer address
            9) Add a subscription
            10) Remove a subscription
            11) Display all subscriptions
            12) Checkout cart
            13) Logout
            System.out.print("Choice: """);
            int cchoice;
            try {
                cchoice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }
            switch (cchoice) {
                case 1:
                    system.displayAllFarmers();
                    break;

                case 2:
                    handleAddToCart(me);
                    break;

                case 3:
                    handleRemoveFromCart(me);
                    break;

                case 4:
                    me.displayUser();
                    me.displayShoppingCart();
                    break;

                case 5:
                    handleSearchByCategory();
                    break;

                case 6:
                    handleSearchBySeason();
                    break;

                case 7:
                    handleSearchByProximity();
                    break;

                case 8:
                    handleMatchProduct(me);
                    break;

                case 9:
                    handleAddSubscription(me);
                    break;

                case 10:
                    handleCancelSubscription(me);
                    break;


                case 11:
                    me.displaySubscriptions();
                    break;

                case 12:
                    handleCheckoutCart(me);
                    break;

                case 13:
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Choose 1–11.");
                    break;
            }
        }
    }

    private static void handleAddToCart(Customer me) {
        System.out.print("Enter Product name to add to cart: ");
        String pname;
        pname = input.nextLine().trim();

        System.out.print("Enter quantity: ");
        int qty;
        try {
            qty = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid quantity.");
            return;
        }
        Product toCart = null;
        for (Product product : products) {
            if (pname.equals(product.getProductName())) {
                toCart = product;
                break;
            }
        }
        if (toCart != null) {
            try {
                me.addToCart(toCart, qty);
                System.out.println("Added to cart: " + toCart.getProductName() + " (Qty: " + qty + ")");
            } catch (IllegalArgumentException iae) {
                System.out.println("Error: " + iae.getMessage());
            }
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void handleRemoveFromCart(Customer me) {
        System.out.print("Enter Product name to remove from cart: ");
        String pname;
        pname = input.nextLine().trim();

        System.out.print("Enter quantity to remove: ");
        int qty;
        try {
            qty = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid quantity.");
            return;
        }
        Product toCart = null;
        for (Product product : products) {
            if (pname.equals(product.getProductName())) {
                toCart = product;
                break;
            }
        }
        if (toCart != null) {
            try {
                me.removeFromCart(toCart, qty);
                System.out.println("Removed from cart: " + toCart.getProductName() + " (Qty: " + qty + ")");
            } catch (IllegalArgumentException iae) {
                System.out.println("Error: " + iae.getMessage());
            }
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void handleSearchByCategory() {
        System.out.print("Enter category: ");
        String cat = input.nextLine().trim();
        ArrayList<Product> byCat = system.searchByCategory(cat);
        if (byCat.isEmpty()) {
            System.out.println("No products found in that category.");
        } else {
            for (Product product : byCat) {
                System.out.println(product);
            }
        }
    }
    
    private static void handleAddSubscription(Customer me) {
        System.out.print("Enter category for weekly box: ");
        String subCat = input.nextLine().trim();
        System.out.print("Enter quantity per week: ");
        int subQty;
        try {
            subQty = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid quantity.");
            return;
        }
        system.subscribeCustomer(me, subCat, subQty);
        System.out.println("Subscribed to category \"" + subCat + "\" (" + subQty + "/week).");
    }

    private static void handleCancelSubscription(Customer me) {
        me.displaySubscriptions();
        if (me.getSubscriptions().isEmpty()) {
            return;
        }
        System.out.print("Enter Subscription ID to cancel: ");
        int subId;
        try {
            subId = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid ID.");
            return;
        }
        system.unsubscribeCustomer(me, subId);
        System.out.println("Subscription " + subId + " removed (if it existed).");
    }

    private static void handleCheckoutCart(Customer me) {
        if (me.getShoppingCart().isEmpty()) {
            System.out.println("Cart is empty. Nothing to checkout.");
            return;
        }
        ArrayList<CartItem> cart = new ArrayList<>(me.getShoppingCart());
        for (CartItem ci : cart) {
            Product p = ci.getProduct();
            int q = ci.getQuantity();
            system.processOrder(me, p, q);
        }
        me.getShoppingCart().clear();
        System.out.println("Checkout complete; cart is now empty.");
    }

    private static void handleMatchProduct(Customer me) {
        System.out.print("Enter product name: ");
        String pname = input.nextLine().trim();
        system.matchFarmer(system.getProduct(pname), me);
    }

    private static void handleSearchByProximity() {
        System.out.print("Enter your X coordinate: ");
        double x;
        try {
            x = Double.parseDouble(input.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number.");
            return;
        }
        System.out.print("Enter your Y coordinate: ");
        double y;
        try {
            y = Double.parseDouble(input.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number.");
            return;
        }
        System.out.print("Enter search radius: ");
        double r;
        try {
            r = Double.parseDouble(input.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number.");
            return;
        }
        ArrayList<Product> byProx = system.searchByProximity(new Coordinate(x, y), r);
        if (byProx.isEmpty()) {
            System.out.println("No products found within that radius.");
        } else {
            for (Product prox : byProx) {
                System.out.println(prox);
            }
        }
    }

    private static void handleSearchBySeason() {
        System.out.print("Enter season date (YYYY-MM-DD): ");
        LocalDate date;
        try {
            date = LocalDate.parse(input.nextLine().trim());
        } catch (Exception ex) {
            System.out.println("Invalid date format.");
            return;
        }
        ArrayList<Product> bySeason = system.searchBySeason(date);
        if (bySeason.isEmpty()) {
            System.out.println("No seasonal products found.");
        } else {
            for (Product product : bySeason) {
                System.out.println(product);
            }
        }
    }




}



