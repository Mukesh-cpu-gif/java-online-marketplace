package csc202;

import java.util.ArrayList;

public class Customer extends User {

    private ArrayList<CartItem> shoppingCart;
    private ArrayList<Subscription> subscriptions;

    Customer() {
    }

    public Customer(int userID, String userName, String userEmail,
                    String userAddress, String userPassword, Coordinate locationCoordinates) {
        super(userID, userName, userEmail, userAddress, userPassword, locationCoordinates);
        this.shoppingCart = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
    }

    public ArrayList<CartItem> getShoppingCart() {
        return shoppingCart;
    }

    public ArrayList<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void addToCart(Product product, int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        for (CartItem ci : shoppingCart) {
            if (ci.getProduct().getProductID() == product.getProductID()) {
                ci.setQuantity(ci.getQuantity() + qty);
                return;
            }
        }
        shoppingCart.add(new CartItem(product, qty));
    }

    public void removeFromCart(Product product, int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        for (CartItem ci : shoppingCart) {
            if (ci.getProduct().getProductID() == product.getProductID()) {
                if (qty < ci.getQuantity()){
                    throw new IllegalArgumentException("Quantity must not exceed the quantity of product in cart.");
                }
                int currentQty = ci.getQuantity();
                if (qty <= currentQty) {
                    shoppingCart.remove(ci);
                } else {
                    ci.setQuantity(currentQty - qty);
                }
                return;
            }
        }
        throw new IllegalArgumentException("Product not found in cart.");
    }

    public void activateSubscription(Subscription s) {
        subscriptions.add(s);
        s.activateSubscription();
    }

    public void removeSubscription(int subscriptionID) {
        for (int i = subscriptions.size() - 1; i >= 0; i--) {
            if (subscriptions.get(i).getSubscriptionID() == subscriptionID) {
                subscriptions.remove(i);
            }
        }
    }

    public void clearShoppingCart(){
        shoppingCart.clear();
    }

    public void clearSubscriptions(){
        subscriptions.clear();
    }

    @Override
    public void displayUser() {
        System.out.println("Customer ID: " + getUserID());
        System.out.println("Name: " + getUserName());
        System.out.println("Email: " + getUserEmail());
        System.out.println("Delivery Address: " + getUserAddress());
        System.out.println("Location: " + getLocationCoordinates());
        System.out.println("Seasonal Updates: " + (getSeasonalUpdates() ? "Yes" : "No"));
    }

    public void displayShoppingCart() {
        if (shoppingCart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("Shopping Cart:");
        for (CartItem ci : shoppingCart) {
            System.out.println(ci);
        }
    }

    public void displaySubscriptions() {
        if (subscriptions.isEmpty()) {
            System.out.println("No active subscriptions.");
            return;
        }
        System.out.println("Subscriptions:");
        for (Subscription s : subscriptions) {
            System.out.println("   - " + s);
        }
    }
}
