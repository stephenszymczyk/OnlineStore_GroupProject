package org.onlinestore.software;

import java.util.ArrayList;

public class Cart {

    private ArrayList<Item> items = new ArrayList<>();

    public Cart() {}

    public ArrayList<Item> getItems() { return items; }
    public void setItems(ArrayList<Item> items) { this.items = items; }

    public void addToCart(Item item) {
        // FIX ME
    }

    public void addToCart(Item item, int quantity) {
        // FIX ME
    }

    public void checkOut() {
        // FIX ME
    }

    public void putItemBack(Item item) {
        // FIX ME
    }

    public void cancelTransaction() {
        // FIX ME
    }
}