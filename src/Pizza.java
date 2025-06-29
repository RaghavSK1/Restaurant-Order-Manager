import java.util.ArrayList;

/**
 * This class represents a pizza's details e.g., its associated toppings/price.
 * It is a subclass of 'FoodItem'.
 * @author: Raghav Senthil Kumar
 * @version: 1.0
 */

public class Pizza extends FoodItem
{
    private ArrayList<PizzaToppings> toppings;

    // Default constructor
    public Pizza()
    {
        toppings = new ArrayList<PizzaToppings>();
    }

    // Non-default constructor
    public Pizza(ArrayList<PizzaToppings> toppings)
    {
        setToppings(toppings);
    }

    // Adds a topping to pizza's topping list
    public void addTopping(PizzaToppings newTopping)
    {
        this.toppings.add(newTopping);
    }

    // Calculates the pizza's price (base price + toppings price)
    @Override
    public double calculateFoodPrice()
    {
        double pizzaPrice = this.BASE_PRICE;

        if (toppings.isEmpty())
        {
            return pizzaPrice;
        }
        else
        {
            for (PizzaToppings topping : this.toppings)
            {
                if (topping == PizzaToppings.PINEAPPLE)
                {
                    pizzaPrice += 2.50;
                }
                else if (topping == PizzaToppings.SEAFOOD)
                {
                    pizzaPrice += 3.50;
                }
                else
                {
                    pizzaPrice += 2;
                }
            }
            return pizzaPrice;
        }
    }

    // Returns the diet category of the pizza
    @Override
    public FoodType getFoodType() {
        if (toppings.contains(PizzaToppings.SEAFOOD)
                || toppings.contains(PizzaToppings.HAM))
        {
            return FoodType.NON_VEGETARIAN;
        }
        else if (toppings.contains(PizzaToppings.CHEESE))
        {
            return FoodType.VEGETARIAN;
        }
        else
        {
            return FoodType.VEGAN;
        }
    }

    // Getter method for toppings
    public ArrayList<PizzaToppings> getToppings()
    {
        return toppings;
    }

    // Setter method for toppings
    public void setToppings(ArrayList<PizzaToppings> newToppings)
    {
        boolean validToppingList = true;
        for (PizzaToppings topping: newToppings)
        {
            if (topping == null)
            {
                validToppingList = false;
            }
        }

        if (validToppingList)
        {
            this.toppings = newToppings;
        }
        else
        {
            System.out.println("All chosen pizza toppings must be valid");
        }
    }

    // Displays the details of Pizza
    @Override
    public String toString()
    {
        String toppingsString = "None";
        if (!toppings.isEmpty())
        {
            toppingsString = toppings.toString();
        }

        return "Pizza's toppings: " + toppingsString + ". " +
                "Pizza's cost: " + calculateFoodPrice() + ". " +
                "Pizza's type: " + getFoodType() + ". ";
    }
}
