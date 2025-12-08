package org.onlinestore.software;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import org.onlinestore.people.Customer;
import org.onlinestore.people.Manager;
import org.onlinestore.people.Person;

public class OnlineStore implements Serializable {
    
    //Inventory belongs to OnlineStore
	private static final long serialVersionUID = 1L;
    private Inventory inventory;
    private ArrayList<Person> users;

    public OnlineStore() {
        this.inventory = new Inventory();
        this.users = new ArrayList<>();

        // Default manager values
        Manager defaultManager = new Manager("Karl Schmidt", "manager", "ottertail", this);
        this.users.add(defaultManager);

        createDefaultItems();
    }
    
    //default inventory items for testing methods
    private void createDefaultItems() {
        inventory.addItem(new Item(399.99, "Binocular", "Vortex Diamondback 10x42 Binocular", 60));
        inventory.addItem(new Item(49.95, "Backpack", "Black jansport backpack", 20));
        inventory.addItem(new Item(29.99, "Hydroflask", "40 oz hydroflask water bottle", 75));
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
    
    //system checks if entered username is available
    public boolean usernameAvailable(String username) {
        for (Person p : users) {
            if (p.getUsername().equalsIgnoreCase(username)) {
                return false; 
            }
        }
        return true; 
    }
    
    //system verifies that user log in credentials are correct
    public Person login(String username, String password) {
        for (Person p : users) {
            if (p.getUsername().equals(username) && p.getPassword().equals(password)) {
                return p;
            }
        }
        System.out.println("Incorrect username or password.");
        return null;
    }

    public Customer createAccount(String name, String username, String password, Address address) {

        if (!usernameAvailable(username)) {
            System.out.println("This username is unavailable.");
            return null; 
        }

        Customer c = new Customer(name, username, password, address, 0.09);

        users.add(c);

        System.out.println("Account created successfully.");
        return c;
    }
    
    public void saveOnlineStore() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("OnlineStore.ser"))) {
            out.writeObject(this);
            System.out.println("Saved successfully");
        } 
        catch (Exception e) {
            System.out.println("Save failed");
            e.printStackTrace();
        }
    }
    
    public static OnlineStore loadOnlineStore() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("OnlineStore.ser"))) {
            System.out.println("Loading");
            return (OnlineStore) in.readObject();
        } catch (Exception e) {
            System.out.println("File not found");
            return new OnlineStore();  
        }
    }
}