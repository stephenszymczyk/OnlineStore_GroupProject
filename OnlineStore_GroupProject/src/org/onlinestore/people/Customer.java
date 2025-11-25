package org.onlinestore.people;

import org.onlinestore.software.Address;
import org.onlinestore.software.Cart;

public class Customer extends Person {

    private Address address;     
    private double taxRate = 0.09;
    private Cart cart;

    public Customer() {
        this.cart = new Cart(this);
    }

    public Customer(String name, String username, String password, 
                    Address address, double taxRate) {

        super(name, username, password);

        this.address = address;

        this.taxRate = taxRate;
        this.cart = new Cart(this);
    }

    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }

    public double getTaxRate() {
        return taxRate;
    }
    
    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public Cart getCart() {
        return cart;
    }
    
    public void setCart(Cart cart) {
        this.cart = cart;
    }
}


