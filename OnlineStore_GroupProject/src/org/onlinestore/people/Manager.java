package org.onlinestore.people;

import org.onlinestore.software.OnlineStore;
import org.onlinestore.software.Item;

public class Manager extends Person {
	
    private OnlineStore store;

    public Manager(String name, String username, String password, OnlineStore store) {
        super(name, username, password);
        this.store = store;
    }
    
    // Managers do not directly modify the inventory since Inventory does not belong to them.
    // Inventory belongs to OnlineStore. Managers call methods for modifying inventory through the OnlineStore class.
    // All logic for modifying inventory is contained in the Inventory class.
    public void addItem(Item item) {
        store.getInventory().addItem(item);
    }

    public void editItem(Item item, int quantity) {
        store.getInventory().updateQuantity(item, quantity);
    }

    public void removeItem(Item item) {
        store.getInventory().removeItem(item);
    }

    public void viewInventory() {
        store.getInventory().printInventory();
    }
}