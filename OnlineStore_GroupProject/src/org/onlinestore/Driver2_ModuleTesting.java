package org.onlinestore;

import org.onlinestore.software.*;
import org.onlinestore.people.*;
import java.util.Scanner;

///////////////////////////////////////////////////////////////////////////////////////////////
//Module test to confirm that Item, Inventory, Cart, and Customer interaction execute properly.
///////////////////////////////////////////////////////////////////////////////////////////////

public class Driver2_ModuleTesting {

    public static void main(String[] args) {

        System.out.println("Module Testing of Inventory Increment/Decrement.");

        //Initialize OnlineStore and get Inventory.
        OnlineStore store = new OnlineStore();
        Inventory inventory = store.getInventory();

        //create customer
        Customer customer = new Customer(
                "Bufo Alvarius",
                "alvarius",
                "meo",
                new Address("1200 University Blvd", "Tucson", "AZ", "85721"),
                0.09
        );

        Cart cart = customer.getCart();

        Item hydroFlask = inventory.findItem("Hydroflask");

        System.out.println("\nInitial quantity of '" + hydroFlask.getName() + "': "
                + hydroFlask.getQuantity());
        
        //add items to cart 
        System.out.println("\nAdding 3 '" + hydroFlask.getName() + "' to shopping cart.");

        int startingQuantity = hydroFlask.getQuantity();
        cart.addItems(hydroFlask, 3);
        
        //check that inventory decremented correctly
        System.out.println("Shopping cart size after adding Hydroflasks: " + cart.getItems().size());
        System.out.println("Quantity expected in inventory: " + (startingQuantity - 3));
        System.out.println("Quantity found in inventory:   " + hydroFlask.getQuantity());

        
        System.out.println("\nRemoving 1 item from shopping cart");

        int endingQuantity = hydroFlask.getQuantity();

        if (!cart.getItems().isEmpty()) {
            cart.removeItem(cart.getItems().get(0), inventory);
        }
        
        //check that item gets restored to inventory correctly after being removed from cart
        System.out.println("Shopping cart size after removing: " + cart.getItems().size());
        System.out.println("Quantity expected in inventory: " + (endingQuantity + 1));
        System.out.println("Quantity found in inventory:   " + hydroFlask.getQuantity());

        
        System.out.println("\nCanceling transaction and returning all items to inventory.");

        int beginQuantity = hydroFlask.getQuantity();

        cart.cancelTransaction(inventory);
        
        //check that all items were restored to inventory after canceling transaction
        System.out.println("Shopping cart size after canceling transaction: " + cart.getItems().size());
        System.out.println("Quantity expected in inventory: " + (beginQuantity + 2)); // since 2 were left in cart
        System.out.println("Quantity found in inventory:   " + hydroFlask.getQuantity());
        
        System.out.println("\nSimulating successful checkout (items should not be returned to inventory).");

        //reset shopping cart
        cart.addItems(hydroFlask, 2);  // add 2 hydroflasks to test checkout

        int beforeCheckout = hydroFlask.getQuantity();

        String simulateCheckout =
                "1\n" +                 // confirm address
                "1234123412341234\n" +  // credit card number
                "05/27\n" +             // expiration date
                "123\n" +               // CVV
                "1\n";                  // confirm transaction

        Scanner simulateScanner = new Scanner(simulateCheckout);

        cart.checkOut(simulateScanner, inventory);
        
        //check shopping cart is empty after completed checkout and that items were not returned to inventory
        System.out.println("Shopping cart size after successful checkout: " + cart.getItems().size());
        System.out.println("Quantity expected in inventory: " + beforeCheckout);
        System.out.println("Quantity found in inventory: " + hydroFlask.getQuantity());

    }
}