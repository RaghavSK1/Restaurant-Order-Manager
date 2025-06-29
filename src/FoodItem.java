/**
 * This class represents a food item that can be ordered (pizza or pasta)
 * Note: the price and type are not attributes
 *       since they should always be dynamically calculated from the object's state
 * @author: Raghav Senthil Kumar
 * @version: 1.0
 */

public abstract class FoodItem
{
    // Class final variable since all food items share this constant base price
    protected final double BASE_PRICE = 11.50;

    public abstract double calculateFoodPrice();
    public abstract FoodType getFoodType();
}
