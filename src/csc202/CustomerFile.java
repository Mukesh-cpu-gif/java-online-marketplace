package csc202;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerFile {

    public static void writeCustomers(ArrayList<Customer> customers) throws IOException {
        File customerFile = new File("customers.txt");
        try (PrintWriter output = new PrintWriter(customerFile)) {
            for (Customer i : customers) {
                output.println("Customer ID: " + i.getUserID());
                output.println("Customer Name: " + i.getUserName());
                output.println("Customer Email: " + i.getUserEmail());
                output.println("Delivery Address: " + i.getUserAddress());
                output.println("X Coordinate: " + i.getLocationCoordinates().getX());
                output.println("Y Coordinate: " + i.getLocationCoordinates().getY());
                output.println();
            }
        }
    }

    public static void readCustomers() throws FileNotFoundException {
        File file = new File("customers.txt");
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                System.out.println(line);
            }
        }
    }
}
