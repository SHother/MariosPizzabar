import java.util.ArrayList;
import java.util.List;

// Class that handles pizzas
public class Pizza implements Comparable<Pizza>{
    private int pizzaId;
    private String name;
    private double price;
    private String toppings;

    public Pizza(int pizzaId, String name, double price, String toppings) {
        this.pizzaId = pizzaId;
        this.name = name;
        this.price = price;
        this.toppings = toppings;
    }

    public int getPizzaId() {
        return pizzaId;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getToppings() {
        return toppings;
    }

    @Override
    public String toString() {
        return "Nr " + pizzaId + ". " + name + ": " + toppings + " - " + price + "kr";
    }
    @Override
    public int compareTo(Pizza other){
        return this.pizzaId - other.pizzaId;
    }

    @Override
    public boolean equals(Object other){
        if(other == null || !(other instanceof Pizza)){
            return false;
        }
        Pizza otherPizza = (Pizza) other;
        return this.pizzaId == otherPizza.pizzaId;
    }
}



