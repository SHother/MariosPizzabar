import java.util.*;

public class Main {

    private static ArrayList<Order> activeOrders = new ArrayList<>();
    public static ArrayList<Pizza> pizzas = new ArrayList<>();


    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        createPizzas();

        //reads any potential active orders after a "potential" crash
        activeOrders = fileHandler.readActiveOrders();
        resumeOrderCounter();

        while (true) {
            run(fileHandler);
        }
    }


    //1. opret order, 2. fjern ordre, 3. se ordre, 4. se sorteret mest købte pizzaer, 5. Afslut order

    public static void run(FileHandler fileHandler) {
        System.out.println("\nMario's Pizzabar ");
        System.out.println("1. Opret ny ordre");
        System.out.println("2. Fjern ordre");
        System.out.println("3. Se aktive ordrer");
        System.out.println("4. Se menu" );
        System.out.println("5. Marker ordre som udleveret");
        System.out.println("6. Vis udførte ordre");
        System.out.print("Vælg en mulighed: ");

        Scanner scanner = new Scanner(System.in);

        int choice = readValidInt(scanner);

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
                showMenu();
                break;
            case 5:
                //TODO test deliver order
                deliverOrder(scanner, fileHandler);
                break;
            case 6:
                showCompletedOrders(fileHandler);
        }
    }
    //Method for delivering order
    public static void displayOrders() {
        Collections.sort(activeOrders,
                (o1, o2) -> o1.getPickUpTime().compareTo(o2.getPickUpTime()));

        for (Order order : activeOrders) {
            System.out.println(FileHandler.lineBreaker());
            System.out.println(order + FileHandler.lineBreaker());

        }
    }

    public static void deliverOrder(Scanner scanner, FileHandler fileHandler) {
        System.out.println("Indtast ordernummer på orderen der er udleveret");

        int a = readValidInt(scanner);

        Optional<Order> foundOrder = activeOrders.stream()
                .filter(p -> p.getOrderId() == a)
                .findFirst();

        if (foundOrder.isPresent()) {
            //foundOrder.ifPresent(activeOrders::remove);
            //TODO slet system print og tjek

            activeOrders.remove(foundOrder.get());
            fileHandler.updateActiveOrders(activeOrders);
            fileHandler.saveOrderToArchive(foundOrder.get());

            System.out.println("Order " + foundOrder.get().getOrderId() + " delivered! ");
        } else {
            System.out.println("Order not found!");
        }
    }

    public static void showCompletedOrders(FileHandler fileHandler) {
        fileHandler.displayArchivedOrders();
    }


    public static void createOrder(Scanner scanner, FileHandler fileHandler) {
        ArrayList<Pizza> pizzasOrdered = new ArrayList<>();

        System.out.println("Skriv kundens navn: ");

        String customerName = readValidString(scanner);


        System.out.println("Skriv kundens telefonnummer: ");
        int costumerPhone = readValidInt(scanner);

//TODO test om dette virker

        boolean isPizzaIDsNotValid = true;
        while (isPizzaIDsNotValid) {
            isPizzaIDsNotValid = false;
            System.out.print("Vælg pizzaer, adskilt af mellemrum");

            String pizzaChoice = readValidString(scanner);

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

        System.out.println("Skriv tidspunkt for afhenting på 4 cifre fx: '1125': ");
        String pickUpTime = readValidString(scanner);
        pickUpTime = validateTimeInput(pickUpTime, scanner);

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
                orderId = readValidInt(scanner);
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
    public static int readValidInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Not a valid number. Try again:");
            scanner.nextLine();
        }
        int returnVal = scanner.nextInt();
        scanner.nextLine();
        return returnVal;
    }

    public static String readValidString(Scanner scanner) {
        String userInput;
        while (true) {
            userInput = scanner.nextLine();
            if (userInput.matches("^[^,;]*$")) {
                break;
            } else {
                System.out.println("Der må ikke være , eller ; i inputtet");
            }
        }
        return userInput;
    }

    public static String validateTimeInput(String s, Scanner scanner) {
        while (!(s.matches("^([01][0-9]|2[0-3])[0-5][0-9]$"))) {
            System.out.println("Tidsformat ikke indtastet korrekt, indtast nyt afhentningstidpunkt: ");
            s = readValidString(scanner);

        }
        return s;
    }

    public static void resumeOrderCounter() {
        if (!activeOrders.isEmpty()) {
            int maxOrderCounter = activeOrders.stream()
                    .max(Comparator.comparing(Order::getOrderId))
                    .get().getOrderId();
            Order.setOrderCount(maxOrderCounter + 1);
        } else {
            System.out.println("Ingen aktive ordre fundet ved opstart");
        }
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


    }
}