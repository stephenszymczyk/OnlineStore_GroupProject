package org.onlinestore.software;

import java.util.ArrayList;
import org.onlinestore.people.Customer;
import org.onlinestore.people.Manager;
import org.onlinestore.people.Person;

//Controller class
public class OnlineStore {
    
	//Inventory belongs to OnlineStore
    private Inventory inventory;
    private ArrayList<Person> users;
    
    

    public OnlineStore() {
        this.inventory = new Inventory();
        this.users = new ArrayList<>();

        // Default manager values
        Manager defaultManager = new Manager("Lou Sassle", "manager", "ottertail", this);
        this.users.add(defaultManager);

        createDefaultItems();
    }
    
    //default inventory items for testing methods
    private void createDefaultItems() {
        inventory.addItem(new Item(9.99, "Mousepad", "Black felt mousepad", 60));
        inventory.addItem(new Item(24.99, "Lamp", "Stainless steel 120V lamp", 20));
        inventory.addItem(new Item(15.00, "Hat", "Brown baseball cap", 75));
    }

    public Inventory getInventory() {
        return inventory;
    }

    public ArrayList<Person> getUsers() {
        return users;
    }

    public ArrayList<Manager> getManagers() {
        ArrayList<Manager> managers = new ArrayList<>();
        for (Person p : users) {
            if (p instanceof Manager) {
                managers.add((Manager) p);
            }
        }
        return managers;
    }

    public void addUser(Person person) {
        users.add(person);
    }


    public boolean usernameAvailable(String username) {
        for (Person p : users) {
            if (p.getUsername().equalsIgnoreCase(username)) {
                return false; 
            }
        }
        return true; 
    }

    public Person login(String username, String password) {
        for (Person p : users) {
            if (p.getUsername().equals(username) && p.getPassword().equals(password)) {
                return p;
            }
        }
        System.out.println("Incorrect username or password.");
        return null;
    }

    public Customer createAccount(String name, String username, String password) {
        if (!usernameAvailable(username)) {
            System.out.println("This username is unavailable.");
            return null; 
        }

        Customer c = new Customer(name, username, password, null, 0.0);
        users.add(c);

        System.out.println("Account created successfully.");
        return c;
    }
}