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

    public Order(String customerName, ArrayList<Pizza> pizzasOrdered, String pickUpTime, int costumerPhone) {
        this.customerName = customerName;
        this.pizzasOrdered = pizzasOrdered;
        this.pickUpTime = pickUpTime;
        this.costumerPhone = costumerPhone;

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
    }

    //TODO tilføj at hvis 2 eller flere af den samme pizza skal laves, så print fx: "2 x Cacciatore"
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
    public int compareTo(Order other){
        return this.pickUpTime.compareTo(other.pickUpTime);
    }

    public void addOrder(Pizza order){
        pizzasOrdered.add(order);
    }
    public String getPickUpTime(){
        return pickUpTime;
    }

    public double getPrice() {
        return price;
    }

    public int getOrderId(){
        return orderId;
    }
    public int getCostumerPhone() {
        return costumerPhone;
    }
    public static int getOrderCount() {
        return orderCount;
    }
    public void setCostumerPhone(int costumerPhone) {
        this.costumerPhone = costumerPhone;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public static void setOrderCount(int orderCount) {
        Order.orderCount = orderCount;
    }
    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Pizza> getPizzasOrdered() {
        return pizzasOrdered;
    }
}


