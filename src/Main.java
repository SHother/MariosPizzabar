import java.util.*;
import java.util.stream.Stream;

public class Main {

    private static ArrayList<Order> activeOrders = new ArrayList<>();
    public static ArrayList<Pizza> pizzas = new ArrayList<>();
    private static Order testOrder;

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        createPizzas();

        //reads any potential active orders after a "potential" crash
        activeOrders = fileHandler.readActiveOrders();
        resumeOrderCounter();

        System.out.println(activeOrders);
        activeOrders.sort(null);
        System.out.println(activeOrders);



        while (true) {
            run();
        }
    }


    //1. opret order, 2. fjern ordre, 3. se ordre, 4. se sorteret mest købte pizzaer, 5. Afslut order

    public static void run() {
        System.out.println("\nMario's Pizzabar ");
        System.out.println("1. Opret ny ordre");
        System.out.println("2. Fjern ordre");
        System.out.println("3. Se aktive ordrer");
        System.out.println("4. Se menu" );
        System.out.println("5. Marker ordre som udleveret");
        System.out.print("Vælg en mulighed: ");

        
        FileHandler fileHandler = new FileHandler();
        Scanner scanner = new Scanner(System.in);
        int choice = getValidInt(scanner);


        switch (choice) {
            case 1:
                createOrder(scanner, fileHandler);
                break;
            case 2:
                removeOrder(scanner,fileHandler);
                break;
            case 3:
                displayOrders();
                break;

            case 4:
                displayMenu();
                break;
            case 5:
                //TODO test deliver order
                deliverOrder(scanner, fileHandler);
                break;
        }
    }

    public static void displayOrders() {
        Collections.sort(activeOrders,
                (o1, o2) -> o1.getPickUpTime().compareTo(o2.getPickUpTime()));

        for (Order order : activeOrders) {
            System.out.println(order + FileHandler.lineBreaker());

        }
    }
    public static void displayMenu(){

        for (Pizza pizza : pizzas ) {
            System.out.println(pizza);
        }
    }

    //Method for delivering order

    public static void deliverOrder(Scanner scanner, FileHandler fileHandler) {
        System.out.println("Indtast ordernummer på orderen der er udleveret");

        int a = getValidInt(scanner);

        Optional<Order> foundOrder = activeOrders.stream()
                .filter(p -> p.getOrderId() == a)
                .findFirst();

        if (foundOrder.isPresent()) {
                foundOrder.ifPresent(activeOrders::remove);
                fileHandler.updateActiveOrders(activeOrders);
                fileHandler.saveOrderToArchive(foundOrder.get());
                System.out.println("Order " + foundOrder.get().getOrderId() + " delivered! ");
        } else {
            System.out.println("Order not found!");
        }
    }


    public static void resumeOrderCounter() {
        if (!activeOrders.isEmpty()) {
            int maxOrderCounter = activeOrders.stream()
                            .max(Comparator.comparing(Order::getOrderId))
                            .get().getOrderId();
            Order.setOrderCount(maxOrderCounter);
        } else {
            System.out.println("Ingen aktive ordre fundet ved opstart");
        }
    }


    public static void createOrder(Scanner scanner, FileHandler fileHandler) {
        ArrayList<Pizza> pizzasOrdered = new ArrayList<>();

        System.out.println("Skriv kundens navn: ");
        scanner.nextLine();
        String customerName = scanner.nextLine();


        System.out.println("Skriv kundens telefonnummer: ");
        int costumerPhone = getValidInt(scanner);

//TODO test om dette virker
        boolean isPizzaIDsNotValid = true;
        while (isPizzaIDsNotValid) {
            isPizzaIDsNotValid = false;
            System.out.print("Vælg pizzaer, adskilt af mellemrum");
            scanner.nextLine();
            String pizzaChoice = scanner.nextLine();

            try {
                int[] arr = Arrays.stream(pizzaChoice.split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                //loop thorugh array of pizza id's and add to pizzasOrdered
                for (int i = 0; i < arr.length; i++) {
                    int a = arr[i];

                    Optional<Pizza> foundPizza = pizzas.stream()
                            .filter(p -> p.getPizzaId() == a)
                            .findFirst();

                    if (foundPizza.isEmpty()) {
                        isPizzaIDsNotValid = true;
                        System.out.println("Pizza ID " + a + " eksisterer ikke!");
                        break;
                    } else {
                        foundPizza.ifPresent(pizzasOrdered::add);
                    }
                }
            } catch (Exception e) {
                System.out.println("Du må kun indtaste tal delt med mellemrum");
                isPizzaIDsNotValid = true;
            }
        }


        String pickUpTime;
        do{
            System.out.println("Skriv tidspunkt for afhenting på 4 cifre fx: '1125': ");
            pickUpTime = scanner.nextLine();
        }
        //TODO validate input is a "real" time
        while(pickUpTime.length() != 4);

        //TODO kommentar på order - nice to have

        Order newOrder = new Order(customerName, pizzasOrdered, pickUpTime, costumerPhone);
        System.out.println(newOrder
                + "\nEr orderen korrekt ? (Y/N)");

        if(scanner.nextLine().equalsIgnoreCase("Y")) {
            activeOrders.add(newOrder);
            fileHandler.saveActiveOrders(newOrder);
            System.out.println("Ordre er tilføjet");

        } else {
            System.out.println("Order ikke tilføjet. ");
        }
    }


    public static void removeOrder(Scanner scanner, FileHandler fileHandler) {
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

    //if no int is found, promt the user to give a valid int
    public static int getValidInt (Scanner scanner){
        scanner.nextLine();
        while (!scanner.hasNextInt()){
            System.out.println("Not a valid number. Try again:");
            scanner.nextLine();
        }
        return scanner.nextInt();
    }


    //TODO showStartMessage() - nice to have
    public static void showStartMessage () {
    }

    //TODO showTotalNumOfOrders () - Nice to have
    public static int showTotalNumOfOrders () {
        return 0;
    }

    //TODO pizzasSoldOfType() - Nice to have
    //takes the id of a specific pizza and return the number of pizzas sold of that type
    public static int pizzasSoldOfType (int pizzaID){
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
        testOrder = new Order("thais", pizzas, "1845", 22101213);

    }
}