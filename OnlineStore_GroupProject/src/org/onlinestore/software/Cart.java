package org.onlinestore.software;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.onlinestore.people.Customer;

public class Cart implements Serializable {

    private ArrayList<Item> items = new ArrayList<>();
    private Customer customer;

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

    // Adds multiple quantities of an item into the cart
    public void addItems(Item item, int quantity) {
        if (item == null || quantity <= 0)
            return;

        if (item.getQuantity() < quantity)
            return; // not enough of item in stock

        for (int i = 0; i < quantity; i++) {
            items.add(item);
        }

        item.setQuantity(item.getQuantity() - quantity);
    }

    // Removes a single instance of an item from the cart
    public void removeItem(Item item, Inventory inventory) {
        if (item == null) return;

        boolean removed = items.remove(item);

        if (removed) {
            inventory.restoreItem(item);
        }
    }

    // Removes multiple quantities of an item from the cart
    public void removeItems(Item item, int count, Inventory inventory) {
        for (int i = 0; i < count; i++) {
            boolean removed = items.remove(item);
            if (removed) {
                inventory.restoreItem(item);
            }
        }
    }

    // Clears cart once transaction is complete
    public void clearCart() {
        items.clear();   
    }

    // Returns subtotal for all items in cart
    public double getSubtotal() {
        double sub = 0.0;
        for (Item item : items) {
            sub += item.getPrice();
        }
        return sub;
    }

    // Returns tax amount
    public double getTax() {
        return getSubtotal() * customer.getTaxRate();
    }

    // Returns the total amount including tax
    public double getTotal() {
        return getSubtotal() + getTax();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Returns how many of a particular item are currently in the cart
    public int getQuantityOfItem(Item item) {
        int count = 0;
        for (Item i : items) {
            if (i == item)
                count++;
        }
        return count;
    }

    // Groups additions of same item together so duplicate entries don't appear in cart
    public Map<Item, Integer> getItemsGrouped() {

        Map<Item, Integer> grouped = new LinkedHashMap<>();

        for (Item item : items) {
            grouped.put(item, grouped.getOrDefault(item, 0) + 1);
        }

        return grouped;
    }

    // Updates the quantity of an item already in the cart
    public boolean updateQuantity(Item item, int newQuantity, Inventory inventory) {

        int currentQuantity = getQuantityOfItem(item);

        // If the new quantity is the same as the current quantity then nothing changes
        if (newQuantity == currentQuantity)
            return true;

        // User increases quantity
        if (newQuantity > currentQuantity) {

            int amountToAdd = newQuantity - currentQuantity;

            // Checks if enough inventory is available
            if (amountToAdd > item.getQuantity())
                return false;

            addItems(item, amountToAdd);
        }
        else {
            // User decreases quantity
            int amountToRemove = currentQuantity - newQuantity;

            removeItems(item, amountToRemove, inventory);
        }

        return true;
    }
}
