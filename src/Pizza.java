import java.util.ArrayList;
import java.util.List;

// Class that handles pizzas
public class Pizza {
    private int id;
    private String name;
    private double price;
    private List<String> toppings;

    public Pizza(int id, String name, double price, List<String> toppings) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.toppings = toppings;
    }
}
