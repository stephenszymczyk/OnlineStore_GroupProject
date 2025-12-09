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
    private OrderHistory orderHistory = new OrderHistory();

    public OnlineStore() {
        this.inventory = new Inventory();
        this.users = new ArrayList<>();

        // Default manager values
        Manager defaultManager = new Manager("Tommy Loyd", "manager", "ottertail", this);
        this.users.add(defaultManager);
        
        // Default customer 
        Customer defaultCustomer = new Customer("Stephen Szymczyk", "stephen", "stephen",
        	new Address("3220 W Westridge Ave", "Tucson", "AZ", "85745"),
            0.09);

        // Add to system users
        this.users.add(defaultCustomer);

        createDefaultItems();
    }
    
    //default inventory items for testing methods
    private void createDefaultItems() {

        inventory.addItem(new Item(
            110.00,
            "Arizona Basketball Jersey",
            "Nike Arizona Wildcats Basketball Authentic 2024 Jersey",
            30
        ));

        inventory.addItem(new Item(
            85.00,
            "Navy Basketball Shorts",
            "Nike Arizona Wildcats Basketball Authentic 2024 Shorts (Navy)",
            20
        ));

        inventory.addItem(new Item(
            65.00,
            "Red Basketball Shorts",
            "Nike Arizona Wildcats Replica Basketball Shorts (Red)",
            50
        ));

        inventory.addItem(new Item(
            60.00,
            "Retro Basketball Shorts",
            "Nike Arizona Wildcats Retro Basketball Shorts (White/Red)",
            75
        ));

        inventory.addItem(new Item(
            85.00,
            "Women's Nike Half-Zip",
            "Nike Womens Arizona Block A Navy Half-Zip Pullover",
            120
        ));

        inventory.addItem(new Item(
            75.00,
            "Women's Crewneck",
            "Nike Womens Arizona 3D Oversized Fleece Navy Crewneck",
            25
        ));


        inventory.addItem(new Item(
            33.74,
            "Arizona Basketball",
            "Nike Arizona Wildcats Team Replica Basketball",
            40
        ));

        inventory.addItem(new Item(
            44.99,
            "Zona Basketball T-Shirt",
            "Men's Nike Navy Arizona Wildcats 2025 Courtside Basketball Legend Dri-FIT T-Shirt",
            60
        ));

        inventory.addItem(new Item(
            54.99,
            "Women's Wildcats Pullover",
            "Women's Gameday Couture Gray Arizona Wildcats Basketball Premium Fleece Drop Pullover Sweatshirt",
            35
        ));
    }
    
    public OrderHistory getOrderHistory() {
        return orderHistory;
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