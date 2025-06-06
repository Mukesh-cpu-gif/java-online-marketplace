package csc202;

public class Subscription {

    private int subscriptionID;
    private String category;
    private int weeklyQuantity;
    private boolean subscriptionActive = true;

    Subscription() {
    }

    Subscription(int subscriptionID, String category, int weeklyQuantity) {
        this.subscriptionID = subscriptionID;
        this.category = category;
        this.weeklyQuantity = weeklyQuantity;
    }

    public int getSubscriptionID() {
        return subscriptionID;
    }

    public String getCategory() {
        return category;
    }

    public int getWeeklyQuantity() {
        return weeklyQuantity;
    }

    public boolean getSubscriptionActive() {
        return subscriptionActive;
    }

    public void cancelSubscription() {
        this.subscriptionActive = false;
    }

    @Override
    public String toString() {
        return "Subscription ID: " + subscriptionID
                + "\nCategory: " + category
                + "\nWeekly Quantity: " + weeklyQuantity
                + "\nSubscription Status: " + (subscriptionActive ? "Active" : "Not Active") + "\n";
    }
}
