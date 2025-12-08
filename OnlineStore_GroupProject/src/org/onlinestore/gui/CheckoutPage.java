package org.onlinestore.gui;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import org.onlinestore.people.Customer;
import org.onlinestore.software.Cart;
import org.onlinestore.software.Item;
import org.onlinestore.software.OnlineStore;

public class CheckoutPage extends JPanel {

    // Reference to the main home page window
    private MainHomePage parent;

    // Reference to the OnlineStore system data
    private OnlineStore store;

    // Reference to the logged-in customer
    private Customer customer;

    // Constructor that initializes the page with the main window, store data, and customer
    public CheckoutPage(MainHomePage parent, OnlineStore store, Customer customer) {
        this.parent = parent;     // Represents MainHomePage window
        this.store = store;       // Represents instance of OnlineStore
        this.customer = customer; 
        createGUI();              
    }

    // Creates the entire Checkout page GUI
    private void createGUI() {

        // BorderLayout and theme background
        setLayout(new BorderLayout());
        setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Title displayed at the top of the checkout page
        JLabel title = new JLabel("Checkout", SwingConstants.CENTER);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // Saves the customers cart
        Cart cart = customer.getCart();

        // Main panel that contains the order summary
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Box for displaying items that were in customer's cart
        JPanel itemsBox = HelperGUI.createFieldBox(5);

        JLabel itemsTitle = HelperGUI.createFieldLabel("Items in Your Cart:");
        itemsBox.add(itemsTitle);

        // Groups items by quantity since cart stores duplicates
        Map<Item, Integer> quantityInCart = new LinkedHashMap<>();
        for (Item cartItem : cart.getItems()) {
            quantityInCart.put(cartItem, quantityInCart.getOrDefault(cartItem, 0) + 1);
        }

        // Adds labels for all items in cart and their quantities
        for (Map.Entry<Item, Integer> itemEntry : quantityInCart.entrySet()) {
            Item cartItem = itemEntry.getKey();
            int qty = itemEntry.getValue();

            JLabel label = HelperGUI.createFieldLabel(
                cartItem.getName() + " (x" + qty + ")  -  $" +
                String.format("%.2f", cartItem.getPrice()) + " each"
            );
            itemsBox.add(label);
        }

        mainPanel.add(itemsBox);

        // Box that displays order summary
        JPanel orderSummary = HelperGUI.createFieldBox(5);

        JLabel subtotalLabel = HelperGUI.createFieldLabel(
            "Subtotal: $" + String.format("%.2f", cart.getSubtotal())
        );
        JLabel taxLabel = HelperGUI.createFieldLabel(
            "Tax: $" + String.format("%.2f", cart.getTax())
        );
        JLabel totalLabel = HelperGUI.createFieldLabel(
            "Total: $" + String.format("%.2f", cart.getTotal())
        );
        JLabel addressLabel = HelperGUI.createFieldLabel(
            "Shipping To: " +
            customer.getAddress().getStreetAddress() + ", " +
            customer.getAddress().getCity() + " " +
            customer.getAddress().getState() + " " +
            customer.getAddress().getZipCode()
        );

        orderSummary.add(subtotalLabel);
        orderSummary.add(taxLabel);
        orderSummary.add(totalLabel);
        orderSummary.add(addressLabel);

        // Button that takes customer to the address update page
        JButton changeAddressButton = HelperGUI.createThemeButton("Change Address");
        changeAddressButton.addActionListener(e -> {
            parent.setContentPane(new AddressUpdatePage(parent, store, customer));
            parent.revalidate();
            parent.repaint();
        });
        orderSummary.add(changeAddressButton);

        // Adds spacing and summary info to display
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(orderSummary);

        add(HelperGUI.centered(mainPanel), BorderLayout.CENTER);

        // Buttons for confirming payment of canceling checkout
        JButton paymentBtn = HelperGUI.createThemeButton("Confirm Payment");
        JButton cancelBtn = HelperGUI.createThemeButton("Cancel");

        // Processes transaction if payment button is selected
        paymentBtn.addActionListener(e -> processTransaction());

        // Cancel button to return back to customer home page
        cancelBtn.addActionListener(e -> parent.showCustomerHome(customer));
        
        // Cancel and payment button location
        JPanel bottom = HelperGUI.createButtonRow(paymentBtn, cancelBtn);
        add(bottom, BorderLayout.SOUTH);
    }

    // Gets user payment information and processes transaction
    private void processTransaction() {

        JTextField creditCardNumber = new JTextField();
        JTextField expirationDate = new JTextField();
        JTextField cvvNumber = new JTextField();

        // Pop-up credit card information input fields
        JPanel box = new JPanel(new GridLayout(3, 2, 12, 12));
        box.add(new JLabel("16 Digit Credit Card Number:"));
        box.add(creditCardNumber);
        box.add(new JLabel("Expiration Date (MM/YY):"));
        box.add(expirationDate);
        box.add(new JLabel("3 Digit CVV:"));
        box.add(cvvNumber);

        // Customer selects OK to submit payment or cancel to go back to menu
        int selection = JOptionPane.showConfirmDialog(
            parent,
            box,
            "Enter Payment Information",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (selection != JOptionPane.OK_OPTION)
            return;

        // Checks that credit card has correct number of digits.
        if (!creditCardNumber.getText().matches("\\d{16}")) {
            HelperGUI.error(parent, "Invalid credit card number.");
            return;
        }

        // Checks that expiration date is in correct format
        if (!expirationDate.getText().matches("\\d{2}/\\d{2}")) {
            HelperGUI.error(parent, "Invalid expiration date.");
            return;
        }

        // Checks that cvv has correct number of digits
        if (!cvvNumber.getText().matches("\\d{3}")) {
            HelperGUI.error(parent, "Invalid CVV.");
            return;
        }

        // Displays confirmation message if payment is approved
        HelperGUI.information(parent, "Your payment has been confirmed. Thank you for your order.");

        // Clears cart once payments has been completed
        customer.getCart().clearCart();

        // Returns to customer home page
        parent.showCustomerHome(customer);
    }
}