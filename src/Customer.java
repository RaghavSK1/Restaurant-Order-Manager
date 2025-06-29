/**
 * This class represents a customer of the restaurant
 * @author:  Raghav Senthil Kumar
 * @version: 1.0
 */


public class Customer
{
    private static int customerCount = 0;

    private String name;
    private String contactNumber;
    private int age;
    private final int customerID;

    // Default constructor
    public Customer()
    {
        name = "Unknown";
        contactNumber = "Unknown";
        age = 0;
        customerID = ++customerCount;
    }

    // Non-default constructor
    public Customer(String name, String contactNumber, int age)
    {
        setName(name);
        setContactNumber(contactNumber);
        setAge(age);
        customerID = ++customerCount;
    }

    // Getter methods
    public int getAge()
    {
        return age;
    }

    public int getCustomerID()
    {
        return customerID;
    }

    public String getContactNumber()
    {
        return contactNumber;
    }

    public String getName()
    {
        return name;
    }

    // Setter methods (note: customerID should not be set!)
    public void setAge(int age)
    {
        if (age >= 18 && age <= 100)
        {
            this.age = age;
        }
        else
        {
            System.out.println("Invalid age range, must be 18 to 100");
        }
    }

    public void setContactNumber(String contactNumber)
    {
        // All phone numbers must consist of 10 digits
        if (contactNumber.trim().length() == 10)
        {
            try
            {
                // If it is possible to convert to number, it must be only digits
                Long.parseLong(contactNumber);
                this.contactNumber = contactNumber.trim();
            }
            catch (NumberFormatException e) {
                System.out.println("Contact number must only consist of digits");
            }
        }
        else
        {
            System.out.println("Contact number must be exactly 10 digits");
        }
    }

    public void setName(String name)
    {
        if (!name.trim().isEmpty())
        {
            this.name = name;
        }
        else
        {
            System.out.println("Name cannot be empty");
        }
    }

    @Override
    public String toString()
    {
        return "Customer name: " + getName() + "\n" +
                "Customer contact: " + getContactNumber() + "\n" +
                "Customer age: " + getAge() + "\n" +
                "Customer id: " + getCustomerID() + "\n";
    }
}
