package csc202;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IProductSearch {

    ArrayList<Product> searchBySeason(LocalDate date);

    ArrayList<Product> searchByProximity(Coordinate origin, double radius);

    ArrayList<Product> searchByCategory(String category);
}
