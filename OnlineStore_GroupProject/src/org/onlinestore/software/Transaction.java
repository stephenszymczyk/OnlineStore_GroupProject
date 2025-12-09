package org.onlinestore.software;

import java.time.LocalDate;  
import org.onlinestore.people.Customer;

public class Transaction {

    private Customer customer;
    private OnlineStore store;

    public Transaction(Customer customer, OnlineStore store) {
        this.customer = customer;
        this.store = store;
    }

    // Returns true once payment information is validated
    public Order processPayment(String creditCardNumber, String expirationDate, String cvvNumber) {

        // Checks that credit card number is 16 digits
        if (!creditCardNumber.matches("\\d{16}")) {
            return null;
        }

        // Checks that date is correct format
        if (!expirationDate.matches("\\d{2}/\\d{2}")) {
            return null;
        }

        // Gets month and year integers
        String[] parts = expirationDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        // Checks month is valid
        if (month < 1 || month > 12) {
            return null;
        }

        // Gets today's date
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear() % 100;  
        int currentMonth = now.getMonthValue();

        // Checks if card is expired
        if (year < currentYear || (year == currentYear && month < currentMonth)) {
            return null;
        }

        // Checks that cvv is 3 digits
        if (!cvvNumber.matches("\\d{3}")) {
            return null;
        }

        // Gets items and quantities at moment of purchase
        var itemsPurchased = customer.getCart().getItemsGrouped();

        // Gets total cost including tax
        double totalCost = customer.getCart().getTotal();

        // Creates the order
        Order newOrder = new Order(customer, itemsPurchased, totalCost);

        // Adds order to system history
        store.getOrderHistory().addOrder(newOrder);

        // Saves store data
        store.saveOnlineStore();

        // Clears customer's cart
        customer.getCart().clearCart();

        // Returns the completed order (confirmation number included)
        return newOrder;
    }
}