package org.onlinestore.software;

import java.util.ArrayList;
import org.onlinestore.software.Inventory;
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
    	if(item==null || quantity <=0){
            return;
        }
    }

    public void removeItem(Item item) {
    	if (item == null){
            return;
        }
    }

    public void removeAll() {
     items.clear();
    }
    
    public void cancelTransaction() {
        Items tempItems = items;
        removeAll();
        //place all items back in inventory
    }

    public void putItemBack(Item item){
        items.remove(item)
        //place in inventory
    }

    public void checkOut(){
        //Display error if trying to checkOut with nothing in cart
        if (items.isEmpty()){
            System.out.println("Cart is empty. Add items to proceed to checkout.");
            return;}
            //sum price
        double subtotal = 0.0;
        for(Item item : items){
            subtotal += item.getPrice();
        }
        //apply taxRate (Placeholder taxrate of 9%, can be adjusted)
        double taxrate = 0.09;
        double tax = subtotal * taxRate;
        double total = subtotal + tax;
        
        //Transaction Display
        System.out.println("Checkout Summary:");
        System.out.println("Items in cart: " + items.size());
        System.out.printf("Subtotal: $%.2f%n", subtotal);
        System.out.printf("Tax (%.1f%%): $%.2f%n", taxRate * 100, tax);
        System.out.printf("Total: $%.2f%n", total);

        //Empties cart once user succesfully completes transaction
        items.clear();
    }

}



