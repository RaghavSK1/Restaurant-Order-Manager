
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the overall driver class that runs the order management system.
 * @author: Raghav Senthil Kumar
 * @version: 1.0
 */
public class OrderManagementSystem
{
    // Class level scanner allows use in all of this class' methods
    private static final Scanner console = new Scanner(System.in);
    private static ArrayList<Order> customerOrders = new ArrayList<Order>();
    private static ArrayList<Customer> allCustomers = new ArrayList<Customer>();

    // Adds a new customer from user inputs
    public static Customer addNewCustomer()
    {
        System.out.println("REGISTERING NEW CUSTOMER");
        // Get a valid customer name
        System.out.print("Enter customer's name: ");
        String customerName = console.nextLine().trim();
        while (!UserInputValidator.validateAsString(customerName))
        {
            System.out.print("Name must not be empty. Try again: ");
            customerName = console.nextLine().trim();
        }

        // Get a valid customer contact
        System.out.print("Enter customer contact (10 digit): ");
        String customerContact = console.nextLine().trim();
        while (!UserInputValidator.validateAsString(customerContact)
                || customerContact.length() != 10)
        {
            System.out.print("Contact must be 10 digits. " +
                    "Try again: ");
            customerContact = console.nextLine().trim();
        }

        // Get a valid customer age
        System.out.print("Enter customer age (must be 18 or older): ");
        String ageString = console.nextLine().trim();
        while (!UserInputValidator.validateAsInt(ageString, 18, 100))
        {
            System.out.print("Age must be a whole number between 18 and 100. " +
                    "Try again: ");
            ageString = console.nextLine().trim();
        }

        int customerAge = Integer.parseInt(ageString);

        // Only add customer if they have not been previously registered
        Customer newCustomer = null;
        if (getPriorCustomerWithDetails(customerName,customerContact) == null)
        {
            newCustomer = new Customer(customerName, customerContact, customerAge);

            allCustomers.add(newCustomer);
            System.out.println("Customer successfully registered!\n");
        }
        else
        {
            // Use the previous customer registration to continue ordering
            newCustomer = getPriorCustomerWithDetails(customerName,customerContact);
            System.out.println("Customer has been previously registered.\n");
        }

        return newCustomer;
    }


    // Creates a new restaurant order from user inputs
    public static void addNewOrder()
    {
        Customer orderCustomer = null;

        System.out.println("Is the order for a new customer (Y/N)? ");
        String newCustomerResponse = console.nextLine().trim();

        // Keep requesting until a valid response ("Y"/"N") is provided
        while (!newCustomerResponse.equalsIgnoreCase("Y")
                && !newCustomerResponse.equalsIgnoreCase("N"))
        {
            System.out.println("Invalid response. " +
                    "Please enter 'Y' if the order is for a new customer. " +
                    "Otherwise enter 'N' if the customer has previously ordered");
            newCustomerResponse = console.nextLine().trim();
        }

        // If the customer is new, create a new customer, otherwise find the customer
        if (newCustomerResponse.equalsIgnoreCase("Y"))
        {
            orderCustomer = addNewCustomer();
        }
        else
        {
            orderCustomer = getPriorCustomerFromUser();

            // If null was returned, then the user requested to exit to main menu
            if (orderCustomer == null)
            {
                return;
            }
        }

        // Get the delivery address
        System.out.println("Enter the order delivery address " +
                "(greater than 5 characters): ");
        String orderDeliveryAddress = console.nextLine().trim();

        while (orderDeliveryAddress.length() <= 5)
        {
            System.out.println("Invalid address - " +
                    "must be greater than 5 characters. Try again: ");
            orderDeliveryAddress = console.nextLine().trim();
        }

        // Get the order food items
        ArrayList<FoodItem> orderItems = new ArrayList<>();
        String foodItemResponse = "";
        System.out.println("NOW ENTER FOOD ITEMS TO ORDER");

        while (!foodItemResponse.equalsIgnoreCase("submit")
                && !foodItemResponse.equalsIgnoreCase("cancel"))
        {
            System.out.println("Enter a food item type (pizza/pasta), " +
                    "or 'submit' to finish order, " +
                    "or 'cancel' to cancel order");
            foodItemResponse = console.nextLine().trim();

            // Depending on the response, create a pizza, pasta, exit, submit the order
            if (foodItemResponse.equalsIgnoreCase("pizza"))
            {
                orderItems.add(createNewPizza());
            }
            else if (foodItemResponse.equalsIgnoreCase("pasta"))
            {
                orderItems.add(createNewPasta());
            }
            else if (foodItemResponse.equalsIgnoreCase("cancel"))
            {
                System.out.println("Cancelling order.\n");
            }
            else if (foodItemResponse.equalsIgnoreCase("submit"))
            {
                if (orderItems.isEmpty())
                {
                    System.out.println("Can't place an empty order without items");
                }
                else
                {
                    Order newOrder =
                            new Order(orderItems, orderCustomer, orderDeliveryAddress);

                    customerOrders.add(newOrder);
                    System.out.println("Order successfully placed! Total cost: $"
                            + newOrder.calculateOrderCost());

                    // Add the reward points earned for that customer
                    int pointsEarned = (int)newOrder.calculateOrderCost();
                    RewardPoints.addRewardPoints(orderCustomer, pointsEarned);
                    System.out.println("Earned " + pointsEarned + " reward points\n");
                }
            }
        }
    }

    // Creates a new Pasta object from inputs
    public static Pasta createNewPasta()
    {
        PastaToppings pastaTopping = null;

        String toppingName = "";
        while (true)
        {
            System.out.println("Enter the pasta topping ('end' if no topping)");
            toppingName = console.nextLine().trim();

            if (toppingName.equalsIgnoreCase("end"))
            {
                System.out.println("Added Pasta with no topping");
                break;
            }

            PastaToppings toppingValue = null;
            for (PastaToppings topping: PastaToppings.values())
            {
                if (topping.name().equalsIgnoreCase(toppingName))
                {
                    toppingValue = topping;
                }
            }

            if (toppingValue == null)
            {
                System.out.println("The topping must be one of: " +
                        "bolognese, marinara, primavera, tomato.");
            }

            else
            {
                pastaTopping = toppingValue;
                System.out.println("Adding a Pasta with " + pastaTopping.name());
                break;
            }
        }
        return new Pasta(pastaTopping);
    }

    // Creates a new Pizza object from inputs
    public static Pizza createNewPizza()
    {
        ArrayList<PizzaToppings> orderPizzaToppings = new ArrayList<>();
        System.out.println("Enter the pizza toppings ('end' when done)");

        String toppingName = "";
        int i = 1;
        while (true)
        {
            System.out.print(i + ". ");
            toppingName = console.nextLine().trim();

            if (toppingName.equalsIgnoreCase("end"))
            {
                System.out.println("Added a pizza with:");
                if (orderPizzaToppings.size() == 0)
                {
                    System.out.print("No toppings");
                }
                for (PizzaToppings topping : orderPizzaToppings)
                {
                    System.out.println(topping.name());
                }
                break;
            }

            PizzaToppings toppingValue = null;
            for (PizzaToppings topping: PizzaToppings.values())
            {
                if (topping.name().equalsIgnoreCase(toppingName))
                {
                    toppingValue = topping;
                }
            }

            if (toppingValue == null)
            {
                System.out.println("The topping must be one of: " +
                        "ham, cheese, pineapple, mushrooms, tomato, seafood");
            }

            else if (orderPizzaToppings.contains(toppingValue))
            {
                System.out.println("Already added to pizza!");
            }
            else
            {
                orderPizzaToppings.add(toppingValue);
                i++;
            }
        }
        return new Pizza(orderPizzaToppings);
    }

    public static void deliverOrder()
    {
        if (customerOrders.isEmpty())
        {
            System.out.println("No orders to deliver.\n");
            return;
        }

        Order deliveredOrder = customerOrders.remove(0);
        System.out.println("DELIVERED FOLLOWING ORDER\n" + deliveredOrder);
    }


    // Prints the main menu to the screen
    public static void displayMainMenu()
    {
        System.out.println("MAIN MENU");
        System.out.println("1. Enter new order");
        System.out.println("2. Deliver order");
        System.out.println("3. Print all orders");
        System.out.println("4. Redeem reward points");
        System.out.println("5. Exit");
    }

    // Prints all the current orders to the screen
    public static void displayOrders()
    {
        if (customerOrders.isEmpty())
        {
            System.out.println("No orders in progress\n");
            return;
        }

        int orderNumber = 1;
        for (Order customerOrder : customerOrders)
        {
            System.out.println("ORDER NUMBER " + orderNumber);
            System.out.println(customerOrder);
            orderNumber++;
        }
    }

    // Gets a prior customer from user input (if it exists)
    public static Customer getPriorCustomerFromUser()
    {
        System.out.println("FINDING PRIOR CUSTOMER");
        String priorCustomerName = "";
        String priorCustomerContact = "";

        while (getPriorCustomerWithDetails
                (priorCustomerName, priorCustomerContact) == null)
        {
            System.out.print("Enter the customer's name " +
                    "(or 'exit' to main menu): ");
            priorCustomerName = console.nextLine().trim();

            if (priorCustomerName.equalsIgnoreCase("exit"))
            {
                System.out.println("Returning to main menu\n");
                return null;
            }

            System.out.print("Enter the customer's contact number " +
                    "(10 digits): ");
            priorCustomerContact = console.nextLine().trim();

            if (getPriorCustomerWithDetails
                    (priorCustomerName, priorCustomerContact) == null)
            {
                System.out.println("Customer not found in database.");
            }
        }
        System.out.println("Customer found in database!");
        return getPriorCustomerWithDetails(priorCustomerName, priorCustomerContact);
    }


    // Returns the registered customer using name and contact (returns null if no customer is found)
    public static Customer getPriorCustomerWithDetails
        (String customerName, String customerContact)
    {
        Customer priorCustomer = null;

        for (Customer customer: allCustomers)
        {
            if (customer.getName().equalsIgnoreCase(customerName)
                    && customer.getContactNumber().equalsIgnoreCase(customerContact))
            {
                priorCustomer = customer;
            }
        }
        return priorCustomer;
    }

    // Runs the whole program
    public static void main(String[] args)
    {
        int option = 0;
        while (option != 5)
        {
            displayMainMenu();
            String inputOption = console.nextLine();
            boolean validOption =
                    UserInputValidator.validateAsInt(inputOption, 1, 5);

            if (validOption)
            {
                option = Integer.parseInt(inputOption);
                System.out.println();

                if (option == 1)
                {
                    addNewOrder();
                }
                else if (option == 2)
                {
                    deliverOrder();
                }
                else if (option == 3)
                {
                    displayOrders();
                }
                else if (option == 4)
                {
                    redeemRewardPoints();
                }
            }
            else
            {
                System.out.println("Please choose a valid option (1-4)");
            }
        }

        // After selecting option 4 and exiting loop
        System.out.println("Exiting program!");
    }

    // Redeems customer's reward points if they have sufficient points
    public static void redeemRewardPoints()
    {
        Customer customer = getPriorCustomerFromUser();
        // If null was returned, then the user requested to exit to main menu
        if (customer == null)
        {
            return;
        }

        int option = 0;
        while(option != 3)
        {
            System.out.println("REWARD POINTS SYSTEM");
            System.out.println("1. Free Small Drink (100 points)");
            System.out.println("2. Free Small Chips (150 points)");
            System.out.println("3. Exit");

            System.out.print("Choose option: ");
            String inputOption = console.nextLine();

            boolean validOption =
                    UserInputValidator.validateAsInt(inputOption, 1, 3);
            if (validOption)
            {
                option = Integer.parseInt(inputOption);
                System.out.println();

                if (option == 1)
                {
                    boolean validRedemption =
                            RewardPoints.redeemCustomerPoints(customer, 100);
                    if (validRedemption)
                    {
                        System.out.println("Here's a free small drink!");
                    }
                }
                else if (option == 2)
                {
                    boolean validRedemption =
                            RewardPoints.redeemCustomerPoints(customer, 150);

                    if (validRedemption)
                    {
                        System.out.println("Here's a free small chips");
                    }
                }
            }
            else
            {
                System.out.println("Please choose a valid option (1-3)");
            }
        }
        System.out.println("Returning to main menu!\n");

    }
}
