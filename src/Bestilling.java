import java.time.LocalDateTime;
import java.util.ArrayList;

//Class that handles the specific orders
public class Bestilling {
    private String navnPaaBestilling;
    private ArrayList<Pizza> pizzasBestilt;
    private Boolean inHouse; // Er bestilling lavet personligt eller over telefon
    private double price;
    private LocalDateTime tidBestilt;

    public Bestilling(String navnPaaBestilling, ArrayList<Pizza> pizzasBestilt, Boolean inHouse, double price) {
        this.navnPaaBestilling = navnPaaBestilling;
        this.tidBestilt = LocalDateTime.now();
        this.pizzasBestilt = pizzasBestilt;
        this.inHouse = inHouse;
        this.price = price;
    }
}


