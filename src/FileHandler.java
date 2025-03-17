import java.util.ArrayList;

public class FileHandler {
    private final String menuFilename = "Menu.txt";
    private final String ordersFilename = "Orders.txt";

    public FileHandler(){}


    //TODO
    //Should add one order to the file
    public void saveOrder(Order order){

    }

    //TODO
    public ArrayList<Order> getOldOrders(){
        return null;
    }

    //TODO
    public ArrayList<Order> getMenu(){
        return null;
    }




}


/*

import java.io.*;
import java.util.ArrayList;

public class FileReader {
    private final String menuFilename = "Menu.txt";
    private final String ordersFilename = "Orders.txt";

    public FileReader() {}

    // Læs gamle ordrer fra filen
    public ArrayList<Order> getOldOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(ordersFilename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    int orderId = Integer.parseInt(data[0]);
                    String customerName = data[1];
                    boolean inHouse = Boolean.parseBoolean(data[2]);
                    double price = Double.parseDouble(data[3]);
                    String pickUpTime = data[4];

                    // Læs bestilte pizzaer (data[5] er en liste af pizza-navne, adskilt af ;)
                    ArrayList<Pizza> pizzasOrdered = new ArrayList<>();
                    String[] pizzaNames = data[5].split(";");
                    for (String pizzaName : pizzaNames) {
                        pizzasOrdered.add(new Pizza(pizzaName, 0)); // Prisen er ukendt her
                    }

                    orders.add(new Order(orderId, customerName, pizzasOrdered, inHouse, price, pickUpTime));
                }
            }
        } catch (IOException e) {
            System.err.println("Fejl ved indlæsning af ordrer: " + e.getMessage());
        }
        return orders;
    }

    // Læs pizzamenuen fra filen
    public ArrayList<Pizza> getMenu() {
        ArrayList<Pizza> menu = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(menuFilename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String name = data[0];
                    double price = Double.parseDouble(data[1]);
                    menu.add(new Pizza(name, price));
                }
            }
        } catch (IOException e) {
            System.err.println("Fejl ved indlæsning af menu: " + e.getMessage());
        }
        return menu;
    }

    // Gem en ordre i filen
    public void saveOrder(Order order) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ordersFilename, true))) {
            // Konverter pizza-listen til en string med ; mellem pizza-navne
            StringBuilder pizzaNames = new StringBuilder();
            for (Pizza pizza : order.pizzasOrdered) {
                pizzaNames.append(pizza.getName()).append(";");
            }

            bw.write(order.getOrderId() + "," +
                    order.getCustomerName() + "," +
                    order.inHouse + "," +
                    order.price + "," +
                    order.getpickUpTime() + "," +
                    pizzaNames);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Fejl ved lagring af ordre: " + e.getMessage());
        }
    }
}
 */