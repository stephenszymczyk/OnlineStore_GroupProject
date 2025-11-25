package org.onlinestore.software;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> items = new ArrayList<>();

    public Inventory() {}

    public ArrayList<Item> getItems() {
        return items; 
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items; 
    }

    public Item findItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void addItem(Item newItem) {
        Item itemExists = findItem(newItem.getName());

        if (itemExists != null) {
            itemExists.setQuantity(itemExists.getQuantity() + newItem.getQuantity());
        } 
        else {
            items.add(newItem);
        }
    }

    public void updateQuantity(Item item, int quantity) {
        if (items.contains(item)) {
            item.setQuantity(quantity);
        }
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void printInventory() {
        for (Item item : items) {
            System.out.println("Name: " + item.getName());
            System.out.println("Price: " + item.getPrice());
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("Description: " + item.getDescription());
            System.out.println();
        }
    }

    //Method added to fix bug found during module testing. Correctly restores items to inventory.
    public void restoreItem(Item item) {
        Item existing = findItem(item.getName());
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + 1);   // add only one back to quantity instead of entire quantity available
        }
    }
}