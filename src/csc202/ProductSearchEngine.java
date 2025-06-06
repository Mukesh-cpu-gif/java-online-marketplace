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

    public void setFarmersProductSearchEngine(ArrayList<Farmer> farmers) {
        this.farmers = farmers;
    }

    @Override
    public ArrayList<Product> searchBySeason(LocalDate date) {
        ArrayList<Product> results = new ArrayList<>();
        String targetSeason = Product.getSeasonFromDate(date);

        for (Farmer f : farmers) {
            for (Product p : f.getAvailableProducts()) {
                if (p.getSeason().equalsIgnoreCase(targetSeason) || p.getSeason().equalsIgnoreCase("all")) {
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
}
