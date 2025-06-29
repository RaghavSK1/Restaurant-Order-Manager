import java.util.HashMap;

/**
 * This class is my extra functionality and represents reward points that customers can earn.
 * I have imported the Hashmap class (from java.util) to effectively connect each customer to their points.
 *
 * Note: Although the current functionality of RewardPoints could be easily implemented as an attribute
 * of Customer class, I choose to create a separate class to enable easy future developments to the currently simple
 * reward point functionality. Furthermore, it gives each class a single specific responsibility.
 *
 * @author: Raghav Senthil Kumar
 * @version: 1.0
 */
public class RewardPoints
{
    // Class variable since it represents the reward point for each customer
    private static HashMap<Customer, Integer> rewardPoints = new HashMap<>();

    // Adds rewards points to a customer in the hash map, or creates a new entry
    public static void addRewardPoints(Customer customer, int pointsEarned)
    {
        if (rewardPoints.containsKey(customer))
        {
            int priorPoints = rewardPoints.get(customer);
            rewardPoints.put(customer, (priorPoints + pointsEarned)) ;
        }
        else
        {
            rewardPoints.put(customer, pointsEarned);
        }
    }

    // Gets the reward points earned by a customer
    public static int getPointsForCustomer(Customer customer)
    {
        if (rewardPoints.containsKey(customer))
        {
            return rewardPoints.get(customer);
        }
        else
        {
            return 0;
        }
    }

    // Checks and returns whether a customer has sufficient points to redeem
    public static boolean redeemCustomerPoints
        (Customer customer, int pointsRedeemed)
    {
        // If the customer has points, check if its sufficient to redeem
        if (rewardPoints.containsKey(customer))
        {
            int originalPoints = RewardPoints.getPointsForCustomer(customer);

            if (pointsRedeemed <= originalPoints)
            {
                rewardPoints.put(customer, originalPoints - pointsRedeemed);
                System.out.println("Redeemed " + pointsRedeemed + " points! " +
                        "Customer now has " + (originalPoints - pointsRedeemed)
                        + " points!");
                return true;
            }

            else
            {
                System.out.println(
                        "Customer does not have enough points to redeem. " +
                        "Customer currently has " + originalPoints + " points!");
                return false;
            }
        }
        else
        {
            System.out.println("Customer does not have any points to redeem");
            return false;
        }
    }
}
