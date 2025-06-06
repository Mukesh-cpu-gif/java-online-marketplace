package csc202;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProductSearchEngine implements IProductSearch {

    private ArrayList<Farmer> farmers;

    ProductSearchEngine() {
    }

    ProductSearchEngine(ArrayList<Farmer> farmers) {
        this.farmers = farmers;
    }

    @Override
    public ArrayList<Product> searchBySeason(LocalDate date) {
        ArrayList<Product> results = new ArrayList<>();
        String targetSeason = getSeasonFromDate(date);

        for (Farmer f : farmers) {
            for (Product p : f.getAvailableProducts()) {
                String productSeason = getSeasonFromDate(p.getHarvestDate());
                if (productSeason.equals(targetSeason)) {
                    results.add(p);
                }
            }
        }

        return results;
    }

    @Override
    public ArrayList<Product> searchByProximity(Coordinate origin, double radius) {
        ArrayList<Product> results = new ArrayList<>();
        for (Farmer f : farmers) {
            double dist = f.getLocationCoordinates().distanceTo(origin);
            if (dist <= radius) {
                results.addAll(f.getAvailableProducts());
            }
        }
        return results;
    }

    @Override
    public ArrayList<Product> searchByCategory(String category) {
        ArrayList<Product> results = new ArrayList<>();
        for (Farmer f : farmers) {
            for (Product p : f.getAvailableProducts()) {
                if (p.getProductCategory().equalsIgnoreCase(category)) {
                    results.add(p);
                }
            }
        }
        return results;
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
}
