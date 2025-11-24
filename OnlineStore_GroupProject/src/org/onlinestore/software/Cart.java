package org.onlinestore.software;

import java.util.ArrayList;
import org.onlinestore.people.Customer;
//items and transaction stuff
public class Cart {

    private ArrayList<Item> items = new ArrayList<Item>();
    Customer customer;

    public Cart(Customer customer) {
        this.customer = customer;
    }

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
    	if (item != null){
            items.remove(item);
        }
    }

    public void removeAll() {
        items.clear();
    }
    
    public void cancelTransaction(Inventory inventory) {
        for (Item item : items) {
            inventory.addItem(item);
            removeItem(item);
        }
    }

    public void putItemBack(Item item, Inventory inventory){
        items.remove(item);
        inventory.addItem(item);
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
        double taxrate = customer.getTaxRate();
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





