package org.onlinestore.software;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderHistory implements Serializable {

    private ArrayList<Order> orders = new ArrayList<>();

    // Adds a new order to the order history
    public void addOrder(Order order) {
        orders.add(order);
    }

    // Returns the full list of all orders
    public ArrayList<Order> getAllOrders() {
        return orders;
    }
}