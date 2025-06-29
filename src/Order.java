import java.util.ArrayList;

/**
 * This class stores details about a customer's order at the restaurant
 * @author: Raghav Senthil Kumar
 * @version: 1.0
 */

public class Order
{
    private ArrayList<FoodItem> orderedItems;
    private Customer customer;
    private String deliveryAddress;

    // Default constructor
    public Order()
    {
        this.orderedItems = new ArrayList<FoodItem>();
        this.customer = new Customer();
        this.deliveryAddress = "At store";
    }

    // Non-default constructor
    public Order(ArrayList<FoodItem> orderedItems,
                 Customer customer, String deliveryAddress)
    {
        setOrderedItems(orderedItems);
        setCustomer(customer);
        setDeliveryAddress(deliveryAddress);
    }

    // Adds an item to the order
    public void addItem(FoodItem item)
    {
        orderedItems.add(item);
    }

    // Calculates the total cost of the order
    public double calculateOrderCost()
    {
        double orderTotal = 0.0;

        if (orderedItems.isEmpty())
        {
            return orderTotal;
        }

        for (FoodItem item : orderedItems)
        {
            orderTotal += item.calculateFoodPrice();
        }

        return orderTotal;
    }

    // Relevant getter methods
    public Customer getCustomer()
    {
        return customer;
    }

    public String getDeliveryAddress()
    {
        return deliveryAddress;
    }

    public ArrayList<FoodItem> getOrderedItems()
    {
        return orderedItems;
    }

    // Gets the diet category of the order
    public FoodType getOrderType()
    {
        if (orderedItems.isEmpty())
        {
            return FoodType.VEGAN;
        }

        ArrayList<FoodType> orderedItemTypes = new ArrayList<FoodType>();
        for (FoodItem item : orderedItems)
        {
            orderedItemTypes.add(item.getFoodType());
        }

        if (orderedItemTypes.contains(FoodType.NON_VEGETARIAN))
        {
            return FoodType.NON_VEGETARIAN;
        }
        else if (orderedItemTypes.contains(FoodType.VEGETARIAN))
        {
            return FoodType.VEGETARIAN;
        }
        else
        {
            return FoodType.VEGAN;
        }
    }


    // Relevant setter methods
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public void setDeliveryAddress(String deliveryAddress)
    {
        if (deliveryAddress.trim().length() > 5)
        {
            this.deliveryAddress = deliveryAddress.trim();
        }
        else
        {
            System.out.println("Delivery address must be greater than 5 characters");
        }
    }

    public void setOrderedItems(ArrayList<FoodItem> orderedItems)
    {
        boolean validItemList = true;
        for (FoodItem item : orderedItems)
        {
            if (item == null)
            {
                validItemList = false;
            }
        }

        if (validItemList)
        {
            this.orderedItems = orderedItems;
        }
        else
        {
            System.out.println("All food items ordered must be valid");
        }
    }

    // Displays the details of an order
    @Override
    public String toString()
    {
        String itemsString = "No items ordered";
        if (!orderedItems.isEmpty())
        {
            itemsString = "Items ordered: \n";

            int itemNumber = 1;
            for (FoodItem item : getOrderedItems())
            {
                itemsString += itemNumber + ": " + item + "\n";
                itemNumber++;
            }
        }


        return "Customer name: " + customer.getName() + "\n" +
                "Customer contact: " + customer.getContactNumber() + "\n" +
                "Delivery address: " + deliveryAddress + "\n" +
                "Order type: " + getOrderType() + "\n" +
                "Order cost: " + calculateOrderCost() + "\n" +
                itemsString + "\n";

    }

}
