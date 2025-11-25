package org.onlinestore.software;

import java.util.ArrayList;
import java.util.Scanner; 
import org.onlinestore.people.Customer;


//items and transaction stuff
public class Cart {

    private ArrayList<Item> items = new ArrayList<Item>();
    Customer customer;

    public Cart(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<Item> getItems() {
    	return items; 
    }
    
    public void setItems(ArrayList<Item> items) {
    	this.items = items; 
    }

    public void addItem(Item item) {
        addItems(item, 1);
    }

    public void addItems(Item item, int quantity) {

        if (item == null || quantity <= 0) {
            return;
        }
        //Display error if customer selects a quantity that is unavailable. 
        if (item.getQuantity() < quantity) {
            System.out.println("Invalid quantity. Only " 
            + item.getQuantity() + " remaining in inventory.");
            return;
        }
        //adds items to customer's cart
        for (int i = 0; i < quantity; i++) {
            items.add(item);
        }
        //decrements quantity of item in inventory when customer adds items to cart
        item.setQuantity(item.getQuantity() - quantity);
    }
    
    //items removed from cart get restored back to inventory
    public void removeItem(Item item, Inventory inventory) {
        if (item != null) {
            items.remove(item);
            inventory.addItem(item);
        }
    }

    public void removeAll() {
        items.clear();
    }
    
    public void cancelTransaction(Inventory inventory) {
    	for (Item item : new ArrayList<>(items)) {
    	    inventory.addItem(item);
    	    items.remove(item);
    	}
    }

    public void checkOut(Scanner scanner, Inventory inventory) {

        //Display error if trying to checkOut with nothing in cart
        if (items.isEmpty()) {
            System.out.println("Cart is empty. Add items to proceed to checkout.");
            return;
        }

        //sum total price
        double subtotal = 0.0;
        for (Item item : items) {
            subtotal += item.getPrice();
        }

        double taxRate = customer.getTaxRate();
        double tax = subtotal * taxRate;
        double total = subtotal + tax;

        //Transaction Display
        System.out.println("Checkout Summary:");
        System.out.println("Items in cart: " + items.size());
        System.out.printf("Subtotal: $%.2f%n", subtotal);
        System.out.printf("Tax (%.1f%%): $%.2f%n", taxRate * 100, tax);
        System.out.printf("Total: $%.2f%n", total);

        //Asks customer to confirm their shipping address.
        System.out.println("\nPlease confirm your shipping address:");
        System.out.println(customer.getAddress().getStreetAddress());
        System.out.println(customer.getAddress().getCity() + ", "
                + customer.getAddress().getState() + " "
                + customer.getAddress().getZipCode());
        
        System.out.println("\n1. Confirm");
        System.out.println("2. Change Shipping Address");
        System.out.print("Select an option: ");

        String addressSelection = scanner.nextLine();

        if (addressSelection.equals("2")) {

            System.out.println("\nPlease enter your new shipping address:");

            System.out.print("Street Address: ");
            String street = scanner.nextLine();

            System.out.print("City: ");
            String city = scanner.nextLine();

            System.out.print("State: ");
            String state = scanner.nextLine();

            System.out.print("Zip Code: ");
            String zip = scanner.nextLine();

            // updates customer's address
            customer.setAddress(new Address(street, city, state, zip));

            System.out.println("Your shipping address was updated sucessfully.");
        }

        //gets customer's payment information
        String creditCardNumber;
        while (true) {
            System.out.print("\nPlease enter your 16 digit credit card number: ");
            creditCardNumber = scanner.nextLine();
            if (creditCardNumber.matches("\\d{16}")) break;
            System.out.println("Invalid entry. Please try again.");
        }

        String expirationDate;
        while (true) {
            System.out.print("Expiration Date (MM/YY): ");
            expirationDate = scanner.nextLine();
            if (expirationDate.matches("\\d{2}/\\d{2}")) break;
            System.out.println("Invalid entry. Please try again.");
        }

        String cvv;
        while (true) {
            System.out.print("Please enter the 3 digit CVV number on the back of your credit card: ");
            cvv = scanner.nextLine();
            if (cvv.matches("\\d{3}")) break;
            System.out.println("Invalid entry. Please try again.");
        }
        
        //display transaction summary (address, total cost) and ask customer to confirm transaction.
        System.out.println("\nOrder Summary:");
        System.out.println("\nShipping Address:");
        System.out.println(customer.getAddress().getStreetAddress());
        System.out.println(customer.getAddress().getCity() + ", "
        + customer.getAddress().getState() + " "
        + customer.getAddress().getZipCode());
        System.out.printf("Final Total: $%.2f%n", total);   
        System.out.println("\n1. Confirm Transaction");
        System.out.println("2. Cancel Transaction");
        System.out.print("Select an option: ");

        String customerSelection = scanner.nextLine();

        if (customerSelection.equals("2")) {
            System.out.println("Transaction has been cancelled.");

            cancelTransaction(inventory);

            return;
        }

        System.out.println("\nYour payment has been approved. Thank you for your order.");
        
        //Add logic for confirmation number.

        items.clear();
    }
}



