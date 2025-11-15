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

    public void addItem(Item item) {
        // LOGIC GOES HERE
    }

    public void addItems(Item item, int quantity) {
    	// LOGIC GOES HERE
    }

    public void removeItem(Item item) {
    	// LOGIC GOES HERE
    }

    public void removeAll() {
    	// LOGIC GOES HERE
    }

}
