import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

//FileHandler klassen håndterer læsning og skrivning af data til filer.
public class FileHandler {
    private final String ordersCompletedFilename = "OrdersCompleted.txt"; // Filnavn for ordrer
    private final String activeOrdersFilename = "ActiveOrders.txt";


    //Constructor for FileHandler.
    public FileHandler() {
    }

    //Gemmer en færgjort ordre i OrdersCompleted.txt.
    public void saveOrderToArchive(Order order) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ordersCompletedFilename, true))) {
            StringBuilder pizzaNames = new StringBuilder(); // Samler pizzanavne i en streng

            // Tilføj alle bestilte pizzaer til strengen, adskilt af semikolon
            for (Pizza pizza : order.getPizzasOrdered()) {
                pizzaNames.append(pizza.getName()).append(";");
            }

            // Fjern sidste semikolon for at undgå fejl i dataformatet
            if (!pizzaNames.isEmpty()) {
                pizzaNames.setLength(pizzaNames.length() - 1);
            }

            // Skriv ordredetaljer til filen
            bw.write(order.getOrderId() + "," +
                    order.getPrice() + "," +
                    order.getPickUpTime() + "," +
                    pizzaNames);
            bw.newLine(); // Gå til næste linje i filen
        } catch (IOException e) {
            System.err.println("Fejl ved lagring af ordre: " + e.getMessage()); // Fejlbesked hvis noget går galt
        }
    }

    //Overskriver "ActiveOrders.txt" med den information den modtager
    public void updateActiveOrders(ArrayList<Order> activeOrders) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(activeOrdersFilename))) {
            bw.write("Next Pizza to be made:" + lineBreaker() + "\n");
        } catch (IOException e) {
            System.err.println("Fejl ved lagring af ordre: " + e.getMessage()); // Fejlbesked hvis noget går galt
        }

        for (Order order : activeOrders) {
            saveActiveOrders(order);
        }
    }

    //Tilføjer en order til "ActiveOrders.txt"
    public void saveActiveOrders(Order order) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(activeOrdersFilename, true))) {
            StringBuilder pizzaNames = new StringBuilder(); // Samler pizzanavne i en streng

            // Tilføj alle bestilte pizzaer til strengen, adskilt af semikolon
            for (Pizza pizza : order.getPizzasOrdered()) {
                pizzaNames.append(pizza.getName()).append(";");
            }

            // Fjern sidste semikolon for at undgå fejl i dataformatet
            if (!pizzaNames.isEmpty()) {
                pizzaNames.setLength(pizzaNames.length() - 1);
            }

            StringBuilder pizzaIDs = new StringBuilder(); // Samler pizzaIDs i en streng
            for (Pizza pizza : order.getPizzasOrdered()) {
                pizzaIDs.append(pizza.getPizzaId()).append(";");
            }
            if (!pizzaIDs.isEmpty()) {
                pizzaIDs.setLength(pizzaIDs.length() - 1);
            }

            // Skriv ordredetaljer til filen
            bw.write(
                    "Bestilling ,\n" +
                    order.getPickUpTime() + ",\n" +
                    pizzaIDs + ",\n" +
                    pizzaNames + "," +
                    order.getOrderId() + "," +
                    order.getPrice() + "," +
                    order.getCustomerName() + "," +
                    order.getCostumerPhone() +
                    lineBreaker()
                    //Comment added
            );
            bw.newLine(); // Gå til næste linje i filen
        } catch (IOException e) {
            System.err.println("Fejl ved lagring af ordre: " + e.getMessage()); // Fejlbesked hvis noget går galt
        }
    }

    public void displayArchivedOrders(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(ordersCompletedFilename));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Fejl ved læsning af ordre");
        }
    }

    //Bruges ikke
    public ArrayList<Order> readArchivedOrders(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(ordersCompletedFilename));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Fejl ved læsning af ordre");
        }
        return null;
    }

    //Læser "ActiveOrders.txt" og formattere texten til en liste af ordre
    public ArrayList<Order> readActiveOrders(){
        ArrayList<Order> allActiveOrders = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(activeOrdersFilename))) {

            String wholeOrder = br.lines().collect(Collectors.joining());
            String[] eachOrder = wholeOrder.split("--------------------");

            for (int i = 1; i < eachOrder.length; i++) {
                String[] orderAttributes = eachOrder[i].split(",");
                int orderID = Integer.parseInt(orderAttributes[4]);
                double price = Double.parseDouble(orderAttributes[5]);
                String pickUpTime = (orderAttributes[1]);
                String customerName = orderAttributes[6];
                int costumerPhone = Integer.parseInt(orderAttributes[7]);
                ArrayList<Pizza> pizzasOrdered = new ArrayList<>();

                int[] arr = Arrays.stream(orderAttributes[2].split(";"))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                //loop thorugh array of pizza id's and add to pizzasOrdered
                for (int a : arr) {
                    Optional<Pizza> foundPizza = Main.pizzas.stream()
                            .filter(p -> p.getPizzaId() == a)
                            .findFirst();
                    foundPizza.ifPresent(pizzasOrdered::add);
                }
                //int id, String name, ArrayList<Pizza> pizzasOrdered, double price, String pickUpTime, int costumerPhone){
                Order newOrder = new Order(orderID, customerName, pizzasOrdered, price, pickUpTime, costumerPhone);
                allActiveOrders.add(newOrder);
            }
        }catch(IOException e){
            System.err.println("SUM TING WONG, CALL SOREN");
        }
        return allActiveOrders;
    }

    public static String lineBreaker() {
        return "\n--------------------";
    }
}



