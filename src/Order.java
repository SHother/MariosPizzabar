import java.util.ArrayList;


//Class that handles the specific orders
public class Order implements Comparable<Order> {
    private static int orderCount = 0;
    private int orderId;
    private String customerName;
    private Boolean inHouse; // Er bestilling lavet personligt eller over telefon
    private double price;
    private String pickUpTime;
    private int costumerPhone;

    private static ArrayList<Pizza> pizzasOrdered;



    public Order(String customerName, ArrayList<Pizza> pizzasOrdered, Boolean inHouse, double price, String pickUpTime, int costumerPhone) {
        if(orderCount == 99){
            orderCount = 0;
        }
        if (!inHouse){
            this.costumerPhone = costumerPhone;
        }
        this.orderId = orderCount++;
        this.customerName = customerName;
        this.pizzasOrdered = pizzasOrdered;
        this.inHouse = inHouse;
        this.price = price;
        this.pickUpTime = pickUpTime;
    }

    public void addOrder(Pizza order){
        pizzasOrdered.add(order);
    }

    public String getpickUpTime(){
        return pickUpTime;
    }
    public int getOrderId(){
        return orderId;
    }


    @Override
    public String toString() {
        return "Order{" +
                "customerName='" + customerName + '\'' +
                ", pizzasOrdered=" + pizzasOrdered +
                ", inHouse=" + inHouse +
                ", price=" + price +
                ", pickUpTime=" + pickUpTime +
                '}';
    }

    @Override
    public int compareTo(Order other){
        return this.pickUpTime.compareTo(other.pickUpTime);
    }
}


