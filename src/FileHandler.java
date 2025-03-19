/*import java.util.ArrayList;

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
*/

import java.io.*;
import java.util.ArrayList;

/**
 * FileHandler klassen håndterer læsning og skrivning af data til filer.
 * Den arbejder med to filer: Menu.txt og Orders.txt.
 */
public class FileHandler {
    private final String menuFilename = "Menu.txt"; // Filnavn for pizzamenuen
    private final String ordersFilename = "Orders.txt"; // Filnavn for ordrer

    /**
     * Constructor for FileHandler.
     * Ingen specifik initialisering er nødvendig.
     */
    public FileHandler() {}

    /**
     * Henter gamle ordrer fra Orders.txt og returnerer dem som en liste af Order-objekter.
     * @return En ArrayList af tidligere ordrer.
     */
    public ArrayList<Order> getOldOrders() {
        ArrayList<Order> orders = new ArrayList<>(); // Liste til at gemme ordrer
        try (BufferedReader br = new BufferedReader(new FileReader(ordersFilename))) {
            String line;
            while ((line = br.readLine()) != null) { // Læs hver linje fra filen
                String[] data = line.split(","); // Opdel linjen ved kommaer

                // Sikrer at linjen har det rigtige antal elementer
                if (data.length >= 6) {
                    int orderId = Integer.parseInt(data[0]); // Konverterer ID til et heltal
                    String customerName = data[1]; // Kundens navn
                    boolean inHouse = Boolean.parseBoolean(data[2]); // Om ordren er spisested eller takeaway
                    double price = Double.parseDouble(data[3]); // Ordrepris
                    String pickUpTime = data[4]; // Afhentningstidspunkt

                    // Opret liste af bestilte pizzaer
                    ArrayList<Pizza> pizzasOrdered = new ArrayList<>();
                    String[] pizzaNames = data[5].split(";"); // Opdel pizza-navne efter semikolon
                    for (String pizzaName : pizzaNames) {
                        pizzasOrdered.add(new Pizza(pizzaName.trim(), 0)); // Prisen kendes ikke her
                    }

                    // Opret en ny ordre og tilføj den til listen
                    orders.add(new Order(customerName, pizzasOrdered, inHouse, pickUpTime, 0));
                }
            }
        } catch (IOException e) {
            System.err.println("Fejl ved indlæsning af ordrer: " + e.getMessage()); // Fejlbesked hvis noget går galt
        }
        return orders; // Returnerer listen af gamle ordrer
    }

    /**
     * Læser pizzamenuen fra Menu.txt og returnerer en liste af Pizza-objekter.
     * @return En ArrayList af pizzaer med deres navn og pris.
     */
    public ArrayList<Pizza> getMenu() {
        ArrayList<Pizza> menu = new ArrayList<>(); // Liste til at gemme menuens pizzaer
        try (BufferedReader br = new BufferedReader(new FileReader(menuFilename))) {
            String line;
            while ((line = br.readLine()) != null) { // Læs hver linje i menu-filen
                String[] data = line.split(","); // Opdel linjen i navn og pris

                // Tjek at linjen har præcis to elementer
                if (data.length == 2) {
                    String name = data[0].trim(); // Hent pizzanavn og fjern evt. mellemrum
                    double price = Double.parseDouble(data[1].trim()); // Konverter prisen til double
                    menu.add(new Pizza(name, price)); // Opret pizza og tilføj til menu-listen
                }
            }
        } catch (IOException e) {
            System.err.println("Fejl ved indlæsning af menu: " + e.getMessage()); // Fejlbesked ved læsefejl
        }
        return menu; // Returnerer listen af pizzaer
    }

    /**
     * Gemmer en ny ordre i Orders.txt.
     * @param order Ordren, der skal gemmes.
     */
    public void saveOrder(Order order) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ordersFilename, true))) {
            StringBuilder pizzaNames = new StringBuilder(); // Samler pizzanavne i en streng

            // Tilføj alle bestilte pizzaer til strengen, adskilt af semikolon
            for (Pizza pizza : order.pizzasOrdered) {
                pizzaNames.append(pizza.getName()).append(";");
            }

            // Fjern sidste semikolon for at undgå fejl i dataformatet
            if (pizzaNames.length() > 0) {
                pizzaNames.setLength(pizzaNames.length() - 1);
            }

            // Skriv ordredetaljer til filen
            bw.write(order.getOrderId() + "," +
                    order.getCustomerName() + "," +
                    order.getInHouse() + "," +
                    order.price + "," +
                    order.getPickUpTime() + "," +
                    pizzaNames);
            bw.newLine(); // Gå til næste linje i filen
        } catch (IOException e) {
            System.err.println("Fejl ved lagring af ordre: " + e.getMessage()); // Fejlbesked hvis noget går galt
        }
    }
}
