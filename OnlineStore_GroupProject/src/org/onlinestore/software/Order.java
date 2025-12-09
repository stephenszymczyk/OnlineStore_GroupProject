package org.onlinestore.software;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import org.onlinestore.people.Customer;

public class Order implements Serializable {

    private String confirmationNumber;
    private Customer customer;
    private Map<Item, Integer> itemsPurchased;
    private double totalCost;
    private LocalDateTime orderDate;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    // Creates a new order
    public Order(Customer customer, Map<Item, Integer> itemsPurchased, double totalCost) {
        this.customer = customer;
        this.itemsPurchased = itemsPurchased;
        this.totalCost = totalCost;

        // Saves the time that the order was placed
        this.orderDate = LocalDateTime.now();

        // Saves the customer's address at the time of purchase
        this.streetAddress = customer.getAddress().getStreetAddress();
        this.city = customer.getAddress().getCity();
        this.state = customer.getAddress().getState();
        this.zipCode = customer.getAddress().getZipCode();

        // Generates unique order confirmation number using timestamp
        long millisecondsSince = System.currentTimeMillis();
        String millisecondsString = Long.toString(millisecondsSince);
        this.confirmationNumber = millisecondsString.substring(millisecondsString.length() - 8);
    }

    // Returns the confirmation number for the order
    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    // Returns the customer who placed the order
    public Customer getCustomer() {
        return customer;
    }

    // Returns all items purchased and their quantities
    public Map<Item, Integer> getItemsPurchased() {
        return itemsPurchased;
    }

    // Returns the total cost of the order
    public double getTotalCost() {
        return totalCost;
    }

    // Returns the date and time the order was placed
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    // Returns the street address at time of purchase
    public String getStreetAddress() {
        return streetAddress;
    }

    // Returns the city at time of purchase
    public String getCity() {
        return city;
    }

    // Returns the state at time of purchase
    public String getState() {
        return state;
    }

    // Returns the zip code at time of purchase
    public String getZipCode() {
        return zipCode;
    }
}