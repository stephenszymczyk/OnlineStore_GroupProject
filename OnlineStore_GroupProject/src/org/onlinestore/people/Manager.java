package org.onlinestore.people;

import org.onlinestore.software.OnlineStore;
import org.onlinestore.software.Item;

public class Manager extends Person {

    private OnlineStore store;

    public Manager() {}

    public Manager(String name, String username, String password, OnlineStore store) {
        super(name, username, password);
        this.store = store;
    }

    public OnlineStore getStore() {
        return store;
    }

    public void setStore(OnlineStore store) {
        this.store = store;
    }

    public void addItem(Item item) {
        // LOGIC GOES HERE
        store.getInventory().addItem(item);
    }

    public void editItem(Item item, int quantity) {
        // LOGIC GOES HERE
        store.getInventory().updateQuantity(item, quantity);
    }

    public void removeItem(Item item) {
        // LOGIC GOES HERE
        store.getInventory().removeItem(item);
    }

    public void viewInventory() {
        // LOGIC GOES HERE
        store.getInventory().printInventory();
    }
}