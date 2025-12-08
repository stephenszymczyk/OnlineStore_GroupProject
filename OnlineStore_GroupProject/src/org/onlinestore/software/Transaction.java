package org.onlinestore.software;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Transaction implements java.io.Serializable {
	private ArrayList<Item> purchasedItems;
	private final double totalPrice;
	private final double subtotalPrice;
	private final int transactionID;
	
	private static final AtomicInteger nextId = new AtomicInteger();
	
	public Transaction(ArrayList<Item> items, double subtotal, double total) {
		this.purchasedItems = items;
		this.transactionID = nextId.incrementAndGet();
		this.subtotalPrice = subtotal;
		this.totalPrice = total;
	}
	
	//getters
	public ArrayList<Item> getPurchasedItems() {
		return purchasedItems;
	}
	public int getTransactionID() {
		return transactionID;
	}
	//
	//no setters as transaction info should not be changed once created
	
}
