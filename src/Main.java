import java.util.*;
import java.util.stream.Stream;

public class Main {

    private static ArrayList<Order> activeOrders = new ArrayList<>();
    private static ArrayList<Pizza> pizzas = new ArrayList<>();
    private static ArrayList<Pizza> menu = new ArrayList<>();

    public static void main(String[] args) {
        createPizzas();
        //FileHandler fileHandler = new FileHandler();

        //menu = fileHandler.getMenu();

        while (true) {
            run();
        }
    }


    //1. opret order, 2. fjern ordre, 3. se ordre, 4. se sorteret mest købte pizzaer,

    public static void run() {
        System.out.println("\n--- Menu ---");
        System.out.println("vælg:" +
                "\nOpret order tast 1" +
                "\nFjern Order tast 2" +
                "\nSe ordre tast 3" +
                "\nSe mest købte pizzaer tast 4");


        Scanner scanner = new Scanner(System.in);
        int choice = getValidInt(scanner);
        //TODO take input and validate that it is an int

        switch (choice) {
            case 1:
                createOrder(scanner);
                break;
            case 2:
                removeOrder(scanner);
                break;
            case 3:
                displayOrders();
                break;

            case 4:
                //TODO display most sold pizza
        }
    }

    public static void displayOrders() {
        Collections.sort(activeOrders,
                (o1, o2) -> o1.getPickUpTime().compareTo(o2.getPickUpTime()));

        for (Order order : activeOrders) {
            System.out.println(order);
        }
    }


    //TODO
    public static void createOrder(Scanner scanner) {
        ArrayList<Pizza> pizzasOrdered = new ArrayList<>();

        System.out.println("Skriv kundens navn: ");
        scanner.nextLine();
        String customerName = scanner.nextLine();


        System.out.println("Skriv kundens telefonnummer: ");
        int costumerPhone = getValidInt(scanner);


        System.out.print("Vælg pizzaer, adskilt af mellemrum");
        scanner.nextLine();
        String pizzaChoice = scanner.nextLine();

        //TODO validate input
        int[] arr = Arrays.stream(pizzaChoice.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();


        //loop thorugh array of pizza id's and add to pizzasOrdered
        for (int i = 0; i < arr.length; i++) {
            int a = arr[i];

            Optional<Pizza> foundPizza = pizzas.stream()
                    .filter(p -> p.getPizzaId() == a)
                    .findFirst();
            foundPizza.ifPresent(pizzasOrdered::add);
        }

        System.out.println("Skriv tidspunkt for afhenting: ");
        String pickUpTime = scanner.nextLine();


        Order newOrder = new Order(customerName, pizzasOrdered, pickUpTime, costumerPhone);
        System.out.println(newOrder
                + "\nEr orderen korrekt ? (Y/N)");

        if(scanner.nextLine().equalsIgnoreCase("Y")) {
            activeOrders.add(newOrder);
            System.out.println("Ordre er tilføjet");
        } else {
            System.out.println("Order ikke tilføjet. ");
        }
    }

    //TODO
    public static void removeOrder(Scanner scanner) {
        //Should request input for the ID of and active order and then remove that order
        System.out.println("Indtast ordre-ID for at fjerne en ordre: ");

        int orderId;
        while (true) {
            if (scanner.hasNextInt()) {
                orderId = getValidInt(scanner);
                scanner.nextLine(); // Håndterer newline
                break;
            } else {
                System.out.println("Ugyldigt input. Indtast et gyldigt ordre-ID.");
                scanner.next(); // Forbruger ugyldigt input
            }
        }

        // Finder ordren med det givne ordre-ID
        Optional<Order> orderToRemove = activeOrders.stream()
                .filter(order -> order.getOrderId() == orderId)
                .findFirst();

        // Fjerner ordren, hvis den findes
        if (orderToRemove.isPresent()) {
            activeOrders.remove(orderToRemove.get());
            System.out.println("Ordren med ID " + orderId + " er blevet fjernet.");
        } else {
            System.out.println("Ingen ordre fundet med ID " + orderId + ".");
        }

    }

    public static void showMenu() {
        System.out.println("\n--- Pizzamenu ---");
        for (Pizza pizza : pizzas) {
            System.out.println(pizza.getPizzaId() + ": " + pizza.getName() + " - " + pizza.getPrice() + "kr. (" + pizza.getToppings() + ")");
        }
    }

    //TODO Create a method that scans the terminal for a new line and returns an int if one is found
    //if no int is found, promt the user to give a valid int
    public static int getValidInt (Scanner scanner){
        scanner.nextLine();
        while (!scanner.hasNextInt()){
            System.out.println("Not a valid number. Try again:");
            scanner.nextLine();
        }
        return scanner.nextInt();
    }

    //TODO
    public static void showStartMessage () {
    }

    //TODO
    public static int showTotalNumOfOrders () {
        return 0;
    }

    //TODO takes the id of a specific pizza and return the number of pizzas sold of that type
    public static int pizzasSoldofType (int pizzaID){
        return 0;
    }

    //Creates an Array of the Pizzas on the menu for use in the program
    public static void createPizzas () {
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