package org.onlinestore;

//DOCUMENTATION OF TESTED SCENARIOS
//manager logs in with default credentials                                   PASS
//manager enters incorrect username or password                              PASS
//customer creates account followed by login                                 PASS
//customer enters incorrect username or password                             PASS
//customer creates account but username is unavailable                       PASS
//close window (exit program)                                                PASS
//user selects an invalid entry                                              PASS
//manager views inventory                                                    PASS
//manager creates new item                                                   PASS
//manager edits item quantity                                                PASS
//manager enters invalid quantity                                            PASS
//manager removes item                                                       PASS
//manager logs out                                                           PASS
//customer views inventory                                                   PASS
//customer adds item to cart                                                 PASS
//customer attempts to add item that is not found in inventory               PASS
//customer views cart                                                        PASS
//customer removes item from cart                                            PASS
//customer attempts to remove item that is not found in cart                 PASS
//customer selects checkout                                                  PASS
//customer confirms shipping address                                         PASS
//customer updates shipping address                                          PASS
//customer enters payment information                                        PASS
//customer enters invalid payment information                                PASS
//customer confirms transaction                                              PASS
//customer cancels transaction                                               PASS
//customer logs out                                                          PASS


import java.util.Scanner;
import org.onlinestore.software.OnlineStore;
import org.onlinestore.people.Manager;
import org.onlinestore.people.Customer;
import org.onlinestore.people.Person;
import org.onlinestore.software.Item;
import org.onlinestore.software.Address;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        OnlineStore store = new OnlineStore();

        System.out.println("Welcome to the Online Store.");

        while (true) {
            System.out.println("Home Page");
            System.out.println("1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. Close Window");
            System.out.print("Please select from the options shown above: ");

            String usersSelection = scanner.nextLine();

            switch (usersSelection) {

                case "1":
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    Person loggedIn = store.login(loginUsername, loginPassword);

                    if (loggedIn == null) {
                        break; 
                    }

                    if (loggedIn instanceof Manager) {
                        System.out.println("Manager login successful. Welcome " + loggedIn.getName() + ".");
                        displayManagerHomepage(scanner, (Manager) loggedIn, store);
                        break;
                    }
                    else {
                        System.out.println("Customer login successful. Welcome " + loggedIn.getName() + ".");
                        displayCustomerHomepage(scanner, (Customer) loggedIn, store);
                        break;
                    }

                case "2":
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();

                    System.out.print("Choose a username: ");
                    String newUsername = scanner.nextLine();

                    System.out.print("Choose a password: ");
                    String newPassword = scanner.nextLine();

                    System.out.println("\nEnter your shipping address:");
                    System.out.print("Street Address: ");
                    String street = scanner.nextLine();

                    System.out.print("City: ");
                    String city = scanner.nextLine();

                    System.out.print("State: ");
                    String state = scanner.nextLine();

                    System.out.print("Zip Code: ");
                    String zip = scanner.nextLine();


                    Address address = new Address(street, city, state, zip);

                    store.createAccount(name, newUsername, newPassword, address);

                    break;
            }
        }
    }
    
    public static void displayManagerHomepage(Scanner scanner, Manager manager, OnlineStore store) {

        while (true) {
            System.out.println("\nManager Home Page");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Item");
            System.out.println("3. Edit Item Quantity");
            System.out.println("4. Remove Item");
            System.out.println("5. Logout");

            System.out.print("Select an option: ");
            String managerInput = scanner.nextLine();

            switch (managerInput) {

                case "1":
                    manager.viewInventory();
                    break;
                

                case "2": 
                    System.out.print("Enter item name: ");
                    String itemName = scanner.nextLine();

                    System.out.print("Enter item price: ");
                    double itemPrice = scanner.nextDouble();
                    scanner.nextLine(); 

                    System.out.print("Enter item description: ");
                    String itemDescription = scanner.nextLine();

                    System.out.print("Enter item quantity: ");
                    int itemQuantity = scanner.nextInt();
                    scanner.nextLine();

                    Item newItem = new Item(itemPrice, itemName, itemDescription, itemQuantity);
                    manager.addItem(newItem);

                    System.out.println("Item added successfully.");
                    break;

                case "3":
                    System.out.print("Enter item name: ");
                    String editName = scanner.nextLine();

                    Item itemToEdit = store.getInventory().findItem(editName);

                    if (itemToEdit == null) {
                        System.out.println("Item not found.");
                        break;
                    }

                    System.out.println("Current quantity: " + itemToEdit.getQuantity());

                    int newQuantity;

                    while (true) {
                        System.out.print("Enter new quantity: ");

                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid entry. Please enter a positive integer quantity.");
                            scanner.nextLine();
                            continue;
                        }

                        newQuantity = scanner.nextInt();
                        scanner.nextLine();

                        if (newQuantity < 0) {
                            System.out.println("Invalid entry. Please enter a positive integer quantity.");
                            continue;
                        }

                        break;
                    }

                    manager.editQuantity(itemToEdit, newQuantity);
                    System.out.println("Quantity updated.");
                    break;

                case "4":
                    System.out.print("Enter item name: ");
                    String removeName = scanner.nextLine();

                    Item itemToRemove = store.getInventory().findItem(removeName);

                    if (itemToRemove == null) {
                        System.out.println("Item not found.");
                        break;
                    }

                    manager.removeItem(itemToRemove);
                    System.out.println("Item has been removed from inventory.");
                    break;

                case "5":
                    System.out.println("Log Out");
                    return;

                default:
                    System.out.println("Invalid selection.");
            }
        }
    }
    
    public static void displayCustomerHomepage(Scanner scanner, Customer customer, OnlineStore store) {

        while (true) {
            System.out.println("\nCustomer Home Page");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Remove Item From Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Logout");

            System.out.print("Select an option: ");
            String customerInput = scanner.nextLine();

            switch (customerInput) {

            case "1":
                store.getInventory().printInventory();
                break;

            case "2":
                System.out.print("Enter item name: ");
                String itemName = scanner.nextLine();

                Item itemToAdd = store.getInventory().findItem(itemName);

                if (itemToAdd == null) {
                    System.out.println("Item not found.");
                    break;
                }

                System.out.print("Enter quantity: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid quantity.");
                    scanner.nextLine();
                    break;
                }

                int quantity = scanner.nextInt();
                scanner.nextLine();

                customer.getCart().addItems(itemToAdd, quantity);
                break;

            case "3":
                System.out.println("\nItems in cart:");
                for (Item i : customer.getCart().getItems()) {
                    System.out.println(i.getName() + " - $" + i.getPrice());
                }
                break;

            case "4":
                System.out.print("Enter item name: ");
                String removeName = scanner.nextLine();

                Item removeItem = store.getInventory().findItem(removeName);

                if (removeItem == null) {
                    System.out.println("Item not found.");
                    break;
                }

                customer.getCart().removeItem(removeItem, store.getInventory());

                System.out.println("Item has been removed from your cart.");
                break;

            case "5":
                customer.getCart().checkOut(scanner, store.getInventory());
                break;

            case "6":
                System.out.println("Logged out.");
                return;

            default:
                System.out.println("Invalid entry.");
            }
        }
    }
}
