package org.onlinestore.software;

import java.io.Serializable;
import java.util.ArrayList;
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

    public void addItems(Item item, int quantity) {
        if (item == null || quantity <= 0)
            return;

        if (item.getQuantity() < quantity)
            return; // not enough stock

        for (int i = 0; i < quantity; i++) {
            items.add(item);
        }

        item.setQuantity(item.getQuantity() - quantity);
    }

    public void removeItem(Item item, Inventory inventory) {
        if (item == null) return;

        boolean removed = items.remove(item);

        if (removed) {
            inventory.restoreItem(item); 
        }
    }

    public void removeItems(Item item, int count, Inventory inventory) {
        for (int i = 0; i < count; i++) {
            boolean removed = items.remove(item);
            if (removed) {
                inventory.restoreItem(item);
            }
        }
    }

    public void clearCart() {
        items.clear();   
    }

    public double getSubtotal() {
        double sub = 0.0;
        for (Item item : items) {
            sub += item.getPrice();
        }
        return sub;
    }

    public double getTax() {
        return getSubtotal() * customer.getTaxRate();
    }

    public double getTotal() {
        return getSubtotal() + getTax();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}

