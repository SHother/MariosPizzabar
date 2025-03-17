import java.time.LocalDateTime;
import java.util.ArrayList;

//Class that handles the specific orders
public class Order {
    private String customerName;
    private ArrayList<Pizza> pizzasOrdered;
    private Boolean inHouse; // Er bestilling lavet personligt eller over telefon
    private double price;
    private LocalDateTime collectionTime;

    public Order(String customerName, ArrayList<Pizza> pizzasOrdered, Boolean inHouse, double price) {
        this.customerName = customerName;
        this.collectionTime = LocalDateTime.now();
        this.pizzasOrdered = pizzasOrdered;
        this.inHouse = inHouse;
        this.price = price;
    }
}


