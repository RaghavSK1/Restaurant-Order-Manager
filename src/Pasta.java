
/**
 * This class represents the pasta food item with its associated toppings/prices.
 * It is a subclass of FoodItem.
 * @author: Raghav Senthil Kumar
 * @version: 1.0
 */

public class Pasta extends FoodItem
{
    private PastaToppings topping;

    // Default constructor
    public Pasta()
    {
        this.topping = null;
    }

    // Non-default constructor
    public Pasta(PastaToppings topping)
    {
        setToppings(topping);
    }

    // Calculates the pasta's price (base price + topping price)
    @Override
    public double calculateFoodPrice()
    {
        double pastaPrice = BASE_PRICE;
        if (hasTopping())
        {
            if (topping == PastaToppings.TOMATO)
            {
                pastaPrice += 4;
            }
            else if (topping == PastaToppings.MARINARA)
            {
                pastaPrice += 6.80;
            }
            else
            {
                pastaPrice += 5.20;
            }
        }
        return pastaPrice;
    }

    // Returns the diet category of the pasta
    @Override
    public FoodType getFoodType() {
        if (hasTopping())
        {
            if (topping == PastaToppings.BOLOGNESE
                    || topping == PastaToppings.MARINARA)
            {
                return FoodType.NON_VEGETARIAN;
            }
            else if (topping == PastaToppings.PRIMAVERA)
            {
                return FoodType.VEGETARIAN;
            }
            else
            {
                return FoodType.VEGAN;
            }
        }

        return FoodType.VEGAN;
    }

    // Getter method
    public PastaToppings getTopping()
    {
        return topping;
    }

    // Checks whether the pasta has a topping
    public boolean hasTopping()
    {
        if (topping == null)
        {
            return false;
        }
        return true;
    }

    // Setter method for topping
    public void setToppings(PastaToppings newTopping)
    {
        this.topping = newTopping;
    }

    // Displays the details of the pasta item
    @Override
    public String toString()
    {
        String toppingString = "None";
        if (topping != null)
        {
            toppingString = topping.name();
        }

        return "Pasta's topping: " + toppingString + ". " +
                "Pasta's cost: " + calculateFoodPrice() + ". " +
                "Pasta's type: " + getFoodType() + ". ";
    }
}
