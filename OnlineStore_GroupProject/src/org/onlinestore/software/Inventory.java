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
        // LOGIC GOES HERE
        return null;
    }

    public void addItem(Item item) {
        // LOGIC GOES HERE
    }

    public void updateQuantity(Item item, int quantity) {
        // LOGIC GOES HERE
    }

    public void removeItem(Item item) {
        // LOGIC GOES HERE
    }

    public void printInventory() {
        // LOGIC GOES HERE
    }
}