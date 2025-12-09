package org.onlinestore.people;

import java.io.Serializable;
import org.onlinestore.software.OnlineStore;
import org.onlinestore.software.Item;

public class Manager extends Person implements Serializable {
	
    private OnlineStore store;

    public Manager(String name, String username, String password, OnlineStore store) {
        super(name, username, password);
        this.store = store;
    }
}