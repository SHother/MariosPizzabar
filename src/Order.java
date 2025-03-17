import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

//Class that handles the specific orders
public class Order {
    private String customerName;
    private static ArrayList<Pizza> pizzasOrdered;
    private Boolean inHouse; // Er bestilling lavet personligt eller over telefon
    private double price;
    private LocalDateTime collectionTime;
    private String formattedTime;

    public Order(String customerName, ArrayList<Pizza> pizzasOrdered, Boolean inHouse, double price) {
        this.customerName = customerName;
        this.collectionTime = LocalDateTime.now();
        this.pizzasOrdered = pizzasOrdered;
        this.inHouse = inHouse;
        this.price = price;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        formattedTime = collectionTime.format(formatter);
        this.collectionTime = LocalDateTime.parse(formattedTime, formatter);
    }

    public void addOrder(Pizza order){
        pizzasOrdered.add(order);
    }

}


