package csc202;

public class CartItem {

    private Product product;
    private int quantity;

    CartItem() {
    }

    CartItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return product.getProductName() + " (Quantity: " + quantity + ")";
    }
}
