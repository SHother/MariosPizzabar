import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//Class that handles the specific orders
public class Order implements Comparable<Order> {

    private static int orderCount = 1;
    private int orderId;
    private String customerName;
    private ArrayList<Pizza> pizzasOrdered;
    private double price;
    private String pickUpTime;
    private int costumerPhone;
    private LocalDate orderDate;

    public Order(String customerName, ArrayList<Pizza> pizzasOrdered, String pickUpTime, int costumerPhone) {
        this.customerName = customerName;
        this.pizzasOrdered = pizzasOrdered;
        this.pickUpTime = pickUpTime;
        this.costumerPhone = costumerPhone;

        LocalTime timeNow = LocalTime.now();
        String timeNowFormatted = timeNow.format(DateTimeFormatter.ofPattern("HHmm"));

        if(pickUpTime.compareTo(timeNowFormatted) < 0){
            this.orderDate = LocalDate.now().plusDays(1);
        } else {
            this.orderDate = LocalDate.now();
        }

        if(orderCount == 99){
            orderCount = 1;
        }
        this.orderId = orderCount++;

        //Beregner nu totalprisen baseret på bestilte pizzaer
        this.price = pizzasOrdered.stream().mapToDouble(Pizza::getPrice).sum();
    }

// Constructor til
    public Order(int id, String name, ArrayList<Pizza> pizzasOrdered, double price, String pickUpTime, int costumerPhone){
        this.orderId = id;
        this.customerName = name;
        this.pizzasOrdered = pizzasOrdered;
        this.price = price;
        this.pickUpTime = pickUpTime;
        this.costumerPhone = costumerPhone;

        LocalTime timeNow = LocalTime.now();
        String timeNowFormatted = timeNow.format(DateTimeFormatter.ofPattern("HHmm"));

        if(pickUpTime.compareTo(timeNowFormatted) < 0){
            this.orderDate = LocalDate.now().plusDays(1);
        } else {
            this.orderDate = LocalDate.now();
        }

    }

    //TODO tilføj at hvis 2 eller flere af den samme pizza skal laves, så print fx: "2 x Cacciatore" - nice to have
    @Override
    public String toString() {
        String pizzasOrderedDisplay = "";

        for(Pizza pizza : pizzasOrdered){
            pizzasOrderedDisplay += "\n\t" + pizza.getName();
        }

        return "Order ID " + orderId +
                "\n" + customerName +
                "\nPizzaer Bestilt:" + pizzasOrderedDisplay +
                "\n\nPris: " + price +
                "\nAfhenting: " + pickUpTime +
                "\nTlf: " + costumerPhone;
    }

    @Override
    public int compareTo(Order that){

        if (this.orderDate.isBefore(that.orderDate)) {
            return -1;

        } else if (this.orderDate.isAfter(that.orderDate)){
            return 1;
        }

        return this.pickUpTime.compareTo(that.pickUpTime);
    }


    public void addPizzaToOrder(Pizza order){
        pizzasOrdered.add(order);
    }

    public int getOrderId(){
        return orderId;
    }
    public String getPickUpTime(){
        return pickUpTime;
    }
    public double getPrice() {
        return price;
    }
    public int getCostumerPhone() {
        return costumerPhone;
    }
    public String getCustomerName() {
        return customerName;
    }
    public ArrayList<Pizza> getPizzasOrdered() {
        return pizzasOrdered;
    }

    public static void setOrderCount(int orderCount) {
        Order.orderCount = orderCount;
    }
    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }
    public void setCostumerPhone(int costumerPhone) {
        this.costumerPhone = costumerPhone;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setPrice(double price) {
        this.price = price;
    }

}


