/**
 * This class validates inputs to ensure the program works as intended
 * @author: Raghav Senthil Kumar
 * @version: 1.0
 */

public class UserInputValidator
{
    // Validates whether a string can be parsed as an integer (within specified range)
    public static boolean validateAsInt(String input, int min, int max)
    {
        try
        {
            int value = Integer.parseInt(input);

            // Valid - only if it's an integer within specified range
            return (value >= min && value <= max);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    // Validates whether a string is non-empty or is just whitespace
    public static boolean validateAsString(String input)
    {
        if (input.trim().isEmpty())
        {
            return false;
        }
        return true;
    }


}
