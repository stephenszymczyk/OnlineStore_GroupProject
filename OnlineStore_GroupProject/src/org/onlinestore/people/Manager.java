package org.onlinestore.people;

import org.onlinestore.software.Inventory;
import org.onlinestore.software.Item;

public class Manager extends Person {

    private Inventory inventory;

    public Manager() {}

    public Manager(String name, String username, String password, Inventory inventory) {
        super(name, username, password);
        this.inventory = inventory;
    }

    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }

    public void addItem(Item item) {
        // FIX ME
    }

    public void editItem(Item item, int quantity) {
        // FIX ME
    }

    public void removeItem(Item item) {
        // FIX ME
    }

    public void viewInventory() {
        // FIX ME
    }
}