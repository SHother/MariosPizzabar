import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Arrays;

public class Main {

    public static ArrayList<Order> activeOrders = new ArrayList<>();
    public static ArrayList<Pizza> pizzas = new ArrayList<>();

    public static void main(String[] args) {
        Pizza pizza = new Pizza(1, "Margherita", 120, "Pep, Dress");
        ArrayList<Pizza> pizzasOrdered = new ArrayList<>();
        pizzasOrdered.add(pizza);
        Order testOrder1 = new Order("Søren", pizzasOrdered, true, 101010, "05.10.2010 12:12", 12345678);
        Order testOrder2 = new Order("Søren", pizzasOrdered, true, 101010, "06.10.2010 12:56", 12345678);
        Order testOrder3 = new Order("Søren", pizzasOrdered, true, 101010, "05.10.2010 11:12", 12345678);
        Order testOrder4 = new Order("Søren", pizzasOrdered, true, 101010, "06.10.2010 00:56", 12345678);

//        System.out.print(testOrder1);


        activeOrders.add(testOrder1);
        activeOrders.add(testOrder2);
        activeOrders.add(testOrder3);
        activeOrders.add(testOrder4);
        /*
        System.out.println(orders);
        orders.sort(null);
        System.out.println(orders);
        */
        displayOrders();
    }


    //1. opret order, 2. fjern ordre, 3. se ordre, 4. se sorteret mest købte pizzaer,

    public static void run() {
        Scanner scanner = new Scanner(System.in);

        //TODO take input and validate that it is an int
        int choice = 0;

        switch (choice) {
            case 1:
                createOrder(scanner);
                break;
            case 2:
                removeOrder(scanner);
                break;
            case 3:
                displayOrders();
        }
    }

    public static void displayOrders() {
        Collections.sort(activeOrders,
                (o1, o2) -> o1.getpickUpTime().compareTo(o2.getpickUpTime()));

        for (Order order : activeOrders) {
            System.out.println(order);
        }
    }

    //TODO
    public static void createOrder(Scanner scanner) {
        ArrayList<Pizza> pizzasOrdered = new ArrayList<>();
        //Should promt the user (alfonso) to give the requiered info to create a new Order

        System.out.println("Skriv kundens navn: ");
        String customerName = scanner.nextLine();

        System.out.println("Skriv kundens telefonnummer: ");
        int phoneNumber = getValidInt(scanner);


        System.out.print("Vælg pizzaer, adskilt af mellemrum");

        String pizzaChoice = scanner.nextLine();

        //Lav om så man indtager en pizza ad gangen

        //TODO validate input
        int[] arr = Arrays.stream(pizzaChoice.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();




            //loop thorugh array of pizza id's and add to pizzasOrdered

          for (int i = 0; i < arr.length; i++) {
            int a = arr[i];

            for (Pizza pizza : pizzas) {
                if (pizza.getPizzaId() == a) {
                    pizzasOrdered.add(pizza);
                }
            }
        }

        //TODO rabat og pickUp
        int price = 0;
        String pickUpTime = "";


        scanner.nextLine(); // Håndter newline


        System.out.print("Er bestillingen in-house? (true/false): ");
        boolean inHouse = scanner.nextBoolean();
        scanner.nextLine(); // Håndter newline


        Order newOrder = new Order(customerName, pizzasOrdered, inHouse, price, pickUpTime, phoneNumber);
        activeOrders.add(newOrder);
    }

    //TODO
    public static void removeOrder(Scanner scanner) {
        //Should request input for the ID of and active order and then remove that order
    }

    //TODO
    public static void showMenu() {
        System.out.println("\n--- Pizzamenu ---");
        for (Pizza pizza : pizzas) {
            System.out.println(pizza.getPizzaId() + ": " + pizza.getName() + " - " + pizza.getPrice() + "kr. (" + pizza.getToppings() + ")");
    }
}

    //TODO Create a method that scans the terminal for a new line and returns an int if one is found
    //if no int is found, promt the user to give a valid int
    public static int getValidInt(Scanner scanner){
        return 0;
    }

    //TODO
    public static void showStartMessage(){}

    //TODO
    public static int showTotalNumOfOrders(){
        return 0;
    }

    //TODO takes the id of a specific pizza and return the number of pizzas sold of that type
    public static int pizzasSold(int pizzaID){
        return 0;
    }

    //Creates an Array of the Pizzas on the menu for use in the program
    public static void createPizzas(){
        pizzas.add(new Pizza(1, "Vesuvio", 57, "tomatsauce, ost, skinke, oregano"));
        pizzas.add(new Pizza(2, "Amerikaner", 53, "tomatsauce, ost, oksefars, oregano"));
        pizzas.add(new Pizza(3, "Cacciatore", 57, "tomatsauce, ost, pepperoni, oregano"));
        pizzas.add(new Pizza(4, "Carbonara", 63, "tomatsauce, ost, kødsauce, spaghetti, cocktailpølser, oregano"));
        pizzas.add(new Pizza(5, "Dennis", 65, "tomatsauce, ost, skinke, pepperoni, cocktailpølser, oregano"));
        pizzas.add(new Pizza(6, "Bertil", 57, "tomatsauce, ost, bacon, oregano"));
        pizzas.add(new Pizza(7, "Silvia", 61, "tomatsauce, ost, pepperoni, rød peber, løg, oliven, oregano"));
        pizzas.add(new Pizza(8, "Victoria", 61, "tomatsauce, ost, skinke, ananas, champignon, løg, oregano"));
        pizzas.add(new Pizza(9, "Toronto", 61, "tomatsauce, ost, skinke, bacon, kebab, chili, oregano"));
        pizzas.add(new Pizza(10, "Capricciosa", 61, "tomatsauce, ost, skinke, champignon, oregano"));
        pizzas.add(new Pizza(11, "Hawaii", 61, "tomatsauce, ost, skinke, ananas, oregano"));
        pizzas.add(new Pizza(12, "Le Blissola", 61, "tomatsauce, ost, skinke, rejer, oregano"));
        pizzas.add(new Pizza(13, "Venezia", 61, "tomatsauce, ost, bacon, oregano"));
        pizzas.add(new Pizza(14, "Mafia", 61, "tomatsauce, ost, pepperoni, bacon, løg, oregano"));
    }


}
