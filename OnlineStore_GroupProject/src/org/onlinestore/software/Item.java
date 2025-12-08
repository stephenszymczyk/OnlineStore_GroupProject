package org.onlinestore.software;

import java.io.Serializable;

public class Item implements Serializable {

    private double price;
    private String name;
    private String description;
    private int quantity;  

    public Item() {}

    public Item(double price, String name, String description, int quantity) {
        this.price = price;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    public double getPrice() {
        return price; 
    }
    
    public void setPrice(double price) {
        this.price = price; 
    }

    public String getName() {
        return name; 
    }
    
    public void setName(String name) {
        this.name = name; 
    }

    public String getDescription() {
        return description; 
    }
    
    public void setDescription(String description) {
        this.description = description; 
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}