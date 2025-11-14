package org.onlinestore.software;

import java.util.ArrayList;

public class Cart {

    private ArrayList<Item> items = new ArrayList<>();

    public Cart() {}

    public ArrayList<Item> getItems() {
    	return items; 
    }
    
    public void setItems(ArrayList<Item> items) {
    	this.items = items; 
    }

    public void addToCart(Item item) {
        // LOGIC GOES HERE
    }

    public void addToCart(Item item, int quantity) {
    	// LOGIC GOES HERE
    }

    public void checkOut() {
    	// LOGIC GOES HERE
    }

    public void putItemBack(Item item) {
    	// LOGIC GOES HERE
    }

    public void cancelTransaction() {
    	// LOGIC GOES HERE
    }
}