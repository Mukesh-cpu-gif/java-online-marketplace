package csc202;

import java.time.LocalDate;

public class Product {

    private int productID;
    private String productName;
    private String productDescription;
    private String productCategory;
    private double productPrice;
    private int quantityAvailable;
    private LocalDate harvestDate;
    private String season;
    private boolean organic;

    Product() {
    }

    Product(int productID, String productName, String productDescription,
            String productCategory, double productPrice, int quantityAvailable,
            LocalDate harvestDate, String season, boolean organic) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        setProductPrice(productPrice);
        setQuantityAvailable(quantityAvailable);
        setHarvestDate(harvestDate);
        this.season = season.toLowerCase();
        this.organic = organic;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        if (productPrice <= 0) {
            throw new IllegalArgumentException("Price can't be negative or zero.");
        }
        this.productPrice = productPrice;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        if (quantityAvailable < 0) {
            throw new IllegalArgumentException("Quantity can't be negative or zero.");
        }
        this.quantityAvailable = quantityAvailable;
    }

    public LocalDate getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(LocalDate harvestDate) {
        if (harvestDate.isBefore(LocalDate.now())) {
            throw new HarvestDateException();
        }
        this.harvestDate = harvestDate;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season.toLowerCase();
    }

    public boolean getOrganic() {
        return organic;
    }

    public void setOrganic(boolean organic) {
        this.organic = organic;
    }

    private String getSeasonFromDate(LocalDate date) {
        int month = date.getMonthValue();
        switch (month) {
            case 12:
            case 1:
            case 2:
                return "Winter";
            case 3:
            case 4:
            case 5:
                return "Spring";
            case 6:
            case 7:
            case 8:
                return "Summer";
            case 9:
            case 10:
            case 11:
                return "Fall";
            default:
                return "No Such Month";
        }
    }

    @Override
    public String toString() {
        return "Product ID: " + productID
                + "\nProduct Name: " + productName
                + "\nCategory: " + productCategory
                + "\nDescription: " + productDescription
                + "\nSeason: " + season
                + "\nOrganic: " + organic
                + "\nPrice: " + productPrice
                + "\nQuantity Available: " + quantityAvailable
                + "\nHarvest Date: " + harvestDate + "\n";
    }
}
