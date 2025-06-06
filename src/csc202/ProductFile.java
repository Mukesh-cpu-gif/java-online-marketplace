package csc202;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductFile {

    public static void writeProducts(ArrayList<Product> products) throws IOException {
        File productFile = new File("produce.txt");
        try (PrintWriter output = new PrintWriter(productFile)) {
            for (Product i : products) {
                output.println("Product ID: " + i.getProductID());
                output.println("Product Name: " + i.getProductName());
                output.println("Harvest Date: " + i.getHarvestDate());
                output.println();
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void readProducts() throws FileNotFoundException {
        File productFile = new File("produce.txt");
        try (Scanner input = new Scanner(productFile)) {
            input.useDelimiter("\\Z");
            String fileRead = input.next();
        }
    }
}
