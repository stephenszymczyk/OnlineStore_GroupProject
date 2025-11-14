package org.onlinestore.software;

import java.util.ArrayList;
import org.onlinestore.people.Customer;
import org.onlinestore.people.Manager;
import org.onlinestore.people.Person;

public class OnlineStore {

    private Inventory inventory;
    private ArrayList<Person> users;
    private ArrayList<Manager> managers;

    public OnlineStore() {
        this.inventory = new Inventory();
        this.users = new ArrayList<Person>();
        this.managers = new ArrayList<Manager>();

        Manager defaultManager = new Manager("Store Manager", "manager", "ottertail", this);

        this.users.add(defaultManager);
        this.managers.add(defaultManager);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public ArrayList<Person> getUsers() {
        return users;
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

    public void addUser(Person person) {
        // LOGIC GOES HERE
    }

    public boolean usernameExists(String username) {
        // LOGIC GOES HERE
        return false;
    }

    public Person login(String username, String password) {
        // LOGIC GOES HERE
        return null;
    }

    public Customer createAccount(String name, String username, String password) {
        // LOGIC GOES HERE
        return null;
    }
}