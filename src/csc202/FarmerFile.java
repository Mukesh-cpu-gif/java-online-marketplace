package csc202;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FarmerFile {

    public static void writeFarmers(ArrayList<Farmer> farmers) throws IOException {
        File farmerFile = new File("farmers.txt");
        try (PrintWriter output = new PrintWriter(farmerFile)) {
            for (Farmer i : farmers) {
                output.println("Farmer ID: " + i.getUserID());
                output.println("Farmer Name: " + i.getUserName());
                output.println("Farm Address: " + i.getUserAddress());
                output.println("Farm Address: " + i.getUserAddress());
                output.println("X Coorinate: " + i.getLocationCoordinates().getX());
                output.println("Y Coorinate: " + i.getLocationCoordinates().getY());
                output.println("Available Products: ");
                for (Product product : i.getAvailableProducts()) {
                    output.println(product.getProductName());
                }
                output.println();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readFarmers() throws FileNotFoundException {
        File file = new File("farmers.txt");
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                System.out.println(line);
            }
        }
    }
}
