package csc202;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    // Scanner object for user input
    private static Scanner input = new Scanner(System.in);

    // DeliverySystem object
    private static DeliverySystem system = new DeliverySystem();

    // Getter method for scanner object
    public static Scanner getInput() {
        return input;
    }

    // Main method
    public static void main(String[] args) {

        // Loop for main menu
        while (true) {
            System.out.println("""
            \n=== Welcome to the Online Marketplace ===
            1) Login as Admin
            2) Login as Farmer
            3) Login as Customer
            4) Register as Farmer
            5) Register as Customer
            6) Exit""");
            System.out.print("Enter your choice: ");

            // Variable for user input
            int choice;
            try {
                choice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input.");
                continue;
            }

            // Switch statement for user input
            switch (choice) {
                case 1:
                    handleLoginAdmin();
                    break;

                case 2:
                    handleLoginFarmer();
                    break;

                case 3:
                    handleLoginCustomer();
                    break;

                case 4:
                    Farmer farmer = new Farmer();
                    system.registerUser(farmer);
                    break;

                case 5:
                    Customer customer = new Customer();
                    system.registerUser(customer);
                    break;

                case 6:
                    System.out.println("Exiting the Marketplace System. \n Hope to see you again.\n Goodbye!");
                    return;

                default:
                    System.out.println("Please choose a valid option (1–6).");
                    break;
            }
        }
    }

    // Method to handle customer login
    private static void handleLoginCustomer() {
        Customer loggedInCustomer = system.loginCustomer();

        if (loggedInCustomer == null) {
            System.out.println("Invalid customer credentials.");
            return;
        }
        System.out.println("Logged in as customer " + loggedInCustomer.getUserName() + ".");
        customerMenu(loggedInCustomer);
    }

    // Method to handle farmer login
    private static void handleLoginFarmer() {
        Farmer loggedInFarmer = system.loginFarmer();

        if (loggedInFarmer == null) {
            System.out.println("Invalid farmer credentials.");
            return;
        }
        System.out.println("Logged in as farmer " + loggedInFarmer.getUserName() + ".");
        farmerMenu(loggedInFarmer);
    }

    // Method to handle admin login
    private static void handleLoginAdmin() {
        Admin loggedInAdmin = system.loginAdmin();
        if (loggedInAdmin == null) {
            System.out.println("Invalid Admin credentials.");
            return;
        }
        System.out.println("Logged in as Admin.");
        AdminMenu(loggedInAdmin);
    }

    // Method to display admin menu
    private static void AdminMenu(Admin me) {
        while (true) {
            System.out.println("""
            \n=== Admin Menu ===
            1) Display Farmers
            2) Display Customers
            3) Register new Farmer
            4) Register new Customer
            5) Remove Farmer
            6) Remove Customer
            7) Logout""");
            System.out.print("Choice: ");
            int achoice;
            try {
                achoice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }
            switch (achoice) {
                case 1:
                    system.displayAllFarmers();
                    break;

                case 2:
                    system.displayAllCustomers();
                    break;

                case 3:
                    Farmer newFarmer = new Farmer();
                    system.registerUser(newFarmer);
                    break;

                case 4:
                    Customer newCustomer = new Customer();
                    system.registerUser(newCustomer);
                    break;

                case 5:
                    Farmer farmer = new Farmer();
                    system.removeUser(farmer);
                    break;

                case 6:
                    Customer customer = new Customer();
                    system.removeUser(customer);
                    break;

                case 7:
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Please choose a valid option (1–7).");
                    break;
            }
        }
    }

    // Method to display farmer menu
    private static void farmerMenu(Farmer me) {
        while (true) {
            System.out.println("""
            \n=== Farmer Menu ===
            1) View my inventory
            2) Add product to inventory
            3) Remove product from inventory
            4) Logout""");
            System.out.print("Choice: ");
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
                    system.addProduct(me);
                    break;

                case 3:
                    system.removeProduct(me);
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

    // Method to display customer menu
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
            13) Logout""");
            System.out.print("Choice: ");
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
                    system.handleAddToCart(me);
                    break;

                case 3:
                    system.handleRemoveFromCart(me);
                    break;

                case 4:
                    me.displayShoppingCart();
                    break;

                case 5:
                    system.handleSearchByCategory();
                    break;

                case 6:
                    handleSearchBySeason();
                    break;

                case 7:
                    handleSearchByProximity(me);
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

    // Method to handle searchBySeason()
    private static void handleSearchBySeason() {
        System.out.print("\nEnter season date (YYYY-MM-DD): ");
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

    // Method to handle searchByProximity()
    private static void handleSearchByProximity(Customer me) {
        Coordinate coord = me.getLocationCoordinates();

        System.out.print("\nEnter search radius: ");
        double r;
        try {
            r = Double.parseDouble(input.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number.");
            return;
        }
        ArrayList<Product> byProx = system.searchByProximity(coord, r);
        if (byProx.isEmpty()) {
            System.out.println("No products found within that radius.");
        } else {
            for (Product prox : byProx) {
                System.out.println(prox);
            }
        }
    }

    // Method to handle matched products
    private static void handleMatchProduct(Customer me) {
        System.out.print("\nEnter product name: ");
        String pname = input.nextLine().trim();
        Product targetproduct = system.checkProduct(pname);
        Farmer targetFarmer = system.matchFarmer(targetproduct, me);
        targetFarmer.displayUser();
    }

    // Method to handle adding subscriptions
    private static void handleAddSubscription(Customer me) {
        System.out.print("\nEnter category for weekly box: ");
        String subCat;
        try {
            subCat = input.nextLine().trim();
        } catch (InputMismatchException ex) {
            System.out.println("Invalid Category.");
            return;
        }
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

    // Method to handle cancel subscriptions
    private static void handleCancelSubscription(Customer me) {
        me.displaySubscriptions();
        if (me.getSubscriptions().isEmpty()) {
            return;
        }
        System.out.print("\nEnter Subscription ID to cancel: ");
        int subId;
        try {
            subId = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Invalid ID.");
            return;
        }
        system.unsubscribeCustomer(me, subId);
        System.out.println("Subscription " + subId + " removed.");
    }

    // Method to handle order checkout
    private static void handleCheckoutCart(Customer me) {
        if (me.getShoppingCart().isEmpty()) {
            System.out.println("\nCart is empty. Nothing to checkout.");
            return;
        }
        ArrayList<CartItem> cart = new ArrayList<>(me.getShoppingCart());
        for (CartItem ci : cart) {
            Product p = ci.getProduct();
            int q = ci.getQuantity();
            system.processOrder(me, p, q);
        }
        me.getShoppingCart().clear();
        System.out.println("\nCheckout complete; cart is now empty.");
    }
}
