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
        // FIX ME
        return null;
    }
}