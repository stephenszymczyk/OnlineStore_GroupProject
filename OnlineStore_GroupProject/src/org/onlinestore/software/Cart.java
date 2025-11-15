package org.onlinestore.software;

import java.util.ArrayList;
//items and transaction stuff
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
        addItems(item, 1);
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
    
    public void cancelTrasaction() {
        Items tempItems = items;
        removeAll();
        //place all items back in inventory
    }

    public void putItemBack(Item item){
        //search for item
        //remove from cart
        //place in inventory
    }

    public void checkOut(){
        //for each item in Cart
            //sum price
        //apply taxRate
        //create transaction record
    }

}

