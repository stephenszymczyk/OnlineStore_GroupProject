package org.onlinestore;

import org.onlinestore.people.*;
import org.onlinestore.software.*;
import java.util.Scanner;

public class Driver1 {

    public static void main(String[] args) {

        //Initialize OnlineStore
        OnlineStore store = new OnlineStore();

        /////////////////////////////////////////////
        // unit tests for Person.java
        /////////////////////////////////////////////
        System.out.println("\nTesting methods to set user information.");

        //create person
        Person p = new Person("Tell Hicks", "Crotalus", "willardi");

        //print current person info
        System.out.println("Name: " + p.getName());
        System.out.println("Username: " + p.getUsername());
        System.out.println("Password: " + p.getPassword());

        //update person fields
        p.setName("Laurence Klauber");
        p.setUsername("Atrox");
        p.setPassword("getula");

        //print updated person info
        System.out.println("\nUpdated user information:");
        System.out.println("Name: " + p.getName());
        System.out.println("Username: " + p.getUsername());
        System.out.println("Password: " + p.getPassword());


        /////////////////////////////////////////////
        //unit tests for Address.java
        /////////////////////////////////////////////
        System.out.println("\nTesting methods to set user address.");

        //create address
        Address address = new Address("1200 E University Blvd", "Tucson", "AZ", "85721");

        //print current address
        System.out.println("Street: " + address.getStreetAddress());
        System.out.println("City: " + address.getCity());
        System.out.println("State: " + address.getState());
        System.out.println("Zip: " + address.getZipCode());

        //update address fields
        address.setStreetAddress("26 Jim Bradford Trail");
        address.setCity("Mimbres");
        address.setState("NM");
        address.setZipCode("88049");

        //print updated address
        System.out.println("\nUpdated address:");
        System.out.println("Street: " + address.getStreetAddress());
        System.out.println("City: " + address.getCity());
        System.out.println("State: " + address.getState());
        System.out.println("Zip: " + address.getZipCode());


        /////////////////////////////////////////////
        //unit tests for Item.java
        /////////////////////////////////////////////
        System.out.println("\nTesting methods to set item attributes.");

        //create item
        Item item1 = new Item(29.99, "Snake Hook",
                "36 inch stainless steel snake hook with wood handle", 20);

        //print current item
        System.out.println("Name: " + item1.getName());
        System.out.println("Description: " + item1.getDescription());
        System.out.println("Price: $" + item1.getPrice());
        System.out.println("Quantity: " + item1.getQuantity());

        //update all item attributes
        item1.setName("Telescoping Snake Hook");
        item1.setDescription("36 inch stainless steel telescoping snake hook with wood handle.");
        item1.setPrice(24.99);
        item1.setQuantity(10);

        //print updated item attributes
        System.out.println("\nUpdated item attributes:");
        System.out.println("Updated Name: " + item1.getName());
        System.out.println("Updated Description: " + item1.getDescription());
        System.out.println("Updated Price: $" + item1.getPrice());
        System.out.println("Updated Quantity: " + item1.getQuantity());


        /////////////////////////////////////////////
        //unit tests for Inventory.java
        /////////////////////////////////////////////
        System.out.println("\nTesting methods to modify inventory.");

        Inventory inventory = new Inventory();

        //create and add items
        Item firstItem = item1;
        Item secondItem = new Item(259.99, "Hex Gloves",
                "Black full length hex armor gloves", 20);
        Item thirdItem = new Item(39.99, "Headlamp",
                "200 lumen LED headlamp", 60);

        inventory.addItem(firstItem);
        inventory.addItem(secondItem);
        inventory.addItem(thirdItem);

        System.out.println("\nCurrent Inventory:");
        inventory.printInventory();

        //find item
        Item foundGloves = inventory.findItem("Hex Gloves");
        System.out.println("Found item: " + foundGloves.getName());

        //update quantity
        inventory.updateQuantity(foundGloves, 10);

        System.out.println("\nUpdated Inventory:");
        inventory.printInventory();

        //remove item
        inventory.removeItem(foundGloves);

        System.out.println("Inventory after removing 'Hex Gloves':");
        inventory.printInventory();


        /////////////////////////////////////////////
        //unit tests for OnlineStore.java
        /////////////////////////////////////////////
        System.out.println("\nTesting methods for OnlineStore.");

        System.out.println("\nDefault Inventory:");
        store.getInventory().printInventory();

        System.out.println("Checking if username 'solifugid' is available:");
        System.out.println("Available: " + store.usernameAvailable("solifugid"));

        //create new customer account
        Customer customer = store.createAccount(
                "Jim Rorabaugh",
                "solifugid",
                "senticolis",
                new Address("3220 W Westridge Ave", "Tucson", "AZ", "85745")
        );

        //print new customer info
        System.out.println("\nNew account created:");
        System.out.println("Name: " + customer.getName());
        System.out.println("Username: " + customer.getUsername());
        System.out.println("Password: " + customer.getPassword());
        System.out.println("Address: "
        		+ customer.getAddress().getStreetAddress() + ", "
                + customer.getAddress().getCity() + ", "
                + customer.getAddress().getState() + " "
                + customer.getAddress().getZipCode());

        //test login method using valid credentials
        System.out.println("\nAttempting to login in with valid credentials:");
        Person validLogin = store.login("solifugid", "senticolis");
        //checks that login method correctly returns a Person
        if (validLogin != null) {
            System.out.println("Login returned user: " + validLogin.getName());
        }
        else {
            System.out.println("UNIT TESTING FAILURE");  //only prints of method fails unit testing
        }

        //test login method using an invalid username/password
        System.out.println("\nAttempting to login in with invalid credentials:");
        //prints "Incorrect username or password." since account does not exist
        Person invalidLogin = store.login("BozoTheClown", "blueberry");
        //checks that login method returns null when credentials are wrong
        if (invalidLogin == null) {
            System.out.println("Login returned user: null");
        }
        else {
            System.out.println("UNIT TESTING FAILURE" + invalidLogin.getName()); //only prints of method fails unit testing
        }

        //test getUsers() method
        System.out.println("\nCurrent number of users:");
        //includes default manager and any accounts that were created after
        System.out.println("Total users: " + store.getUsers().size());

        //test addUser() method
        store.addUser(new Person("Brian Hinds", "fundad", "boa"));
        //print to confirm that number of users has increased
        System.out.println("Number of users after adding new user: " + store.getUsers().size());

        //test getManagers() method
        System.out.println("\nNumber of managers: " + store.getManagers().size());


        /////////////////////////////////////////////
        //unit tests for Manager.java
        /////////////////////////////////////////////
        System.out.println("\nTesting methods for managers.");

        //test to get default manager information
        Manager manager = store.getManagers().get(0);

        //create new items to test manager methods
        Item managerItem1 = new Item(14.99, "Lifestraw", "Lifestraw water filter with drawstring pouch", 90);
        Item managerItem2 = new Item(6.99, "Fishwife Smoked Mackerel", "Tinned smoke mackerel with chili flakes", 125);

        //test manager method to add item to inventory
        manager.addItem(managerItem1);
        manager.addItem(managerItem2);
        System.out.println("Updated inventory after manager "
        		+ "adds 'Lifestraw' and 'Fishwife Smoked Mackerel:");
        store.getInventory().printInventory();

        //test manager method to edit item quantity
        manager.editQuantity(managerItem1, 200);
        System.out.println("Updated Lifestraw Quantity: "
        		+ store.getInventory().findItem("Lifestraw").getQuantity());

        //test manager method to view inventory
        System.out.println("\nUpdated inventory after changing 'Lifestraw' quantity: ");
        manager.viewInventory();

        //test manager method to remove inventory
        manager.removeItem(managerItem2);
        System.out.println("Updated Inventory after removing 'Fishwife Smoked Mackerel:");
        store.getInventory().printInventory();


        /////////////////////////////////////////////
        //unit tests for Customer.java
        /////////////////////////////////////////////
        System.out.println("\nTesting methods for customers.");

        //print customer information
        System.out.println("Customer name: " + customer.getName());
        System.out.println("Username: " + customer.getUsername());
        System.out.println("Password: " + customer.getPassword());
        System.out.println("Tax rate: " + customer.getTaxRate());
        System.out.println("Shipping address: "
        		+ customer.getAddress().getStreetAddress() + ", "
        		+ customer.getAddress().getCity() + ", "
                + customer.getAddress().getState() + " "
                + customer.getAddress().getZipCode());

        //test method to change tax rate
        customer.setTaxRate(0.07);
        System.out.println("\nUpdated customer tax rate.");
        System.out.println("New tax rate: " + customer.getTaxRate());

        //test method to update address
        customer.setAddress(new Address("27297 Lindell Rd", "Lake Elsinore", "CA", "92532"));
        System.out.println("\nUpdated shipping address.");
        System.out.println("Street: " + customer.getAddress().getStreetAddress());
        System.out.println("City: " + customer.getAddress().getCity());
        System.out.println("State: " + customer.getAddress().getState());
        System.out.println("Zip: " + customer.getAddress().getZipCode());

        //test method to get customer's cart
        System.out.println("\nTesting customer get cart method.");
        System.out.println("Customer cart returned sucessfully. " + customer.getCart()); // should return memory reference to cart


        /////////////////////////////////////////////
        //unit tests for Cart.java
        /////////////////////////////////////////////
        System.out.println("\nTesting methods for shopping cart.");

        //get customer's shopping cart
        Cart cart = customer.getCart();

        //test adding items to customer's shopping cart
        System.out.println("\nAdding 2 Headlamps and 3 Lifestraws to shopping cart.");
        cart.addItems(thirdItem, 2);        // adds 2 Headlamps to cart
        cart.addItems(managerItem1, 3);     // adds 3 Lifestraws to cart

        //print cart contents after adding items
        System.out.println("Shopping cart contents after adding items:");
        for (Item i : cart.getItems()) {
        	System.out.println(i.getName() + " - $" + i.getPrice());
        }

        //test putting items back into inventory
        System.out.println("\nPutting 1 Headlamp back into inventory.");
        cart.removeItem(thirdItem, inventory);   // removeItem restores stock now

        //verify cart contents after putting item back
        System.out.println("Updated Shopping Cart:");
        for (Item i : cart.getItems()) {
        	System.out.println(i.getName());
        }

        //test removing one item from cart
        System.out.println("\nTesting remove item from cart method.");
        if (!cart.getItems().isEmpty()) {
        	cart.removeItem(cart.getItems().get(0), inventory);
        }
        System.out.println("Updated shopping cart after removing item: " + cart.getItems().size());

        //test cancelTransaction()
        System.out.println("\nTesting cancel transaction method.");
        cart.cancelTransaction(inventory);
        System.out.println("Shopping cart size after cancelling transaction: " + cart.getItems().size());
        
        System.out.println("\nSimulating checkout procedure. Adding two headlamps to empty cart.");

        //add 2 Headlamps for checkout test
        cart.addItems(thirdItem, 2);

        //simulation inputs
        String simulateInput =
        		"1\n" +                     //confirms address
                "1234123412341234\n" +      //credit card number input
                "05/27\n" +                 //expiration date input
                "123\n" +                   //CVV input
                "1\n";                      //confirms transaction

        Scanner simulateScan = new Scanner(simulateInput);

        //performs simulation
        cart.checkOut(simulateScan, inventory);

        //check that cart is empty after checkout
        System.out.println("Shopping cart size after checkout: " + cart.getItems().size());
    }
}