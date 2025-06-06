package csc202;

public class Subscription {

    private int subscriptionID;
    private String category;
    private int weeklyQuantity;
    private boolean subscriptionActive;

    Subscription() {
    }

    Subscription(int subscriptionID, String category, int weeklyQuantity) {
        setSubscriptionID(subscriptionID);
        setCategory(category);
        setWeeklyQuantity(weeklyQuantity);
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

    public void setSubscriptionID(int subscriptionID) {
        if (subscriptionID <= 0) {
            throw new IllegalArgumentException("SubscriptionID must be positive.");
        }
        this.subscriptionID = subscriptionID;
    }

    public void setCategory(String category) {
        if(category == null || category.trim().isEmpty()){
            throw new IllegalArgumentException("Category can't be null or empty.");
        }
        this.category = category;
    }

    public void setWeeklyQuantity(int weeklyQuantity) {
        if (weeklyQuantity <= 0) {
            throw new IllegalArgumentException("Weekly Quantity must be positive.");
        }
        this.weeklyQuantity = weeklyQuantity;
    }

    public void activateSubscription() {
        this.subscriptionActive = true;
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
