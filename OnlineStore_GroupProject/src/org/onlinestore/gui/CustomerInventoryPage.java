package org.onlinestore.gui;

import javax.swing.*;
import java.awt.*;
import org.onlinestore.software.OnlineStore;
import org.onlinestore.software.Item;
import org.onlinestore.people.Customer;

public class CustomerInventoryPage extends JPanel {

    // Reference to the main home page window
    private MainHomePage parent;

    // Reference to the OnlineStore system data
    private OnlineStore store;

    // Reference to the logged-in customer
    private Customer customer;

    // Constructor that initializes the page with the main window, store data, and customer
    public CustomerInventoryPage(MainHomePage parent, OnlineStore store, Customer customer) {
        this.parent = parent;       // Represents MainHomePage window
        this.store = store;         // Represents instance of OnlineStore
        this.customer = customer;   
        createGUI();                 
    }

    // Creates the entire Customer Inventory page GUI
    private void createGUI() {

        // BorderLayout and theme background
        setLayout(new BorderLayout());
        setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Title displayed at the top of the inventory page
        JLabel title = new JLabel("Store Inventory", SwingConstants.CENTER);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Panel that displays all available store items
        JPanel itemListPanel = new JPanel();
        itemListPanel.setBackground(ThemeGUI.BACKGROUND_COLOR);
        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));

        // Adds each inventory item to the displayed list
        for (Item item : store.getInventory().getItems()) {
            itemListPanel.add(createItemDisplay(item));
            itemListPanel.add(Box.createVerticalStrut(15));
        }

        // Allows user to scroll through inventory panel
        JScrollPane scroll = new JScrollPane(itemListPanel);
        scroll.getViewport().setBackground(ThemeGUI.BACKGROUND_COLOR);
        scroll.setBorder(null);

        add(scroll, BorderLayout.CENTER);

        // Back button returns customer to the Customer Home Page
        JButton backBtn = HelperGUI.createThemeButton("Back");
        backBtn.addActionListener(e -> parent.showCustomerHome(customer));
        
        // Panel used to center the Back button at the bottom of the page
        JPanel backPanel = new JPanel();
        backPanel.setBackground(ThemeGUI.BACKGROUND_COLOR);
        backPanel.add(backBtn);

        add(backPanel, BorderLayout.SOUTH);
    }

    // Creates a displayed version of each inventory item that shows all of its attributes
    private JPanel createItemDisplay(Item item) {

        JPanel itemDisplay = new JPanel();
        itemDisplay.setBackground(ThemeGUI.PANEL_COLOR);
        itemDisplay.setBorder(BorderFactory.createLineBorder(ThemeGUI.OUTLINE_COLOR));
        itemDisplay.setLayout(new BorderLayout());
        itemDisplay.setPreferredSize(new Dimension(500, 160));

        // Text for all of item's attributes
        JTextArea itemAttributes = new JTextArea(
                "Name: " + item.getName() + "\n" +
                "Price: $" + item.getPrice() + "\n" +
                "Description: " + item.getDescription() + "\n" +
                "Quantity in Stock: " + item.getQuantity()
        );
        itemAttributes.setEditable(false);
        itemAttributes.setBackground(ThemeGUI.PANEL_COLOR);
        itemAttributes.setForeground(ThemeGUI.TEXT_MAIN);
        itemAttributes.setFont(ThemeGUI.REGULAR_FONT);

        itemDisplay.add(itemAttributes, BorderLayout.CENTER);

        // Button that allows customer to add item to their cart
        JPanel buttonRow = new JPanel(new FlowLayout());
        buttonRow.setOpaque(false);

        JButton addToCartBtn = HelperGUI.createThemeButton("Add to Cart");
        addToCartBtn.addActionListener(e -> itemQuantityPopUp(item));

        buttonRow.add(addToCartBtn);
        itemDisplay.add(buttonRow, BorderLayout.SOUTH);

        return itemDisplay;
    }

    // Pop-up box for customer to select quantity of item they want to add to their cart
    private void itemQuantityPopUp(Item item) {

        JTextField qtyField = new JTextField("1");

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setOpaque(false);
        panel.add(new JLabel("Enter quantity to add to cart:"));
        panel.add(qtyField);

        // Customer selects OK to confirm new quantity or cancel to go back
        int userSelection = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Add to Cart",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (userSelection == JOptionPane.OK_OPTION) {

            String text = qtyField.getText().trim();

            // Checks for valid quantity input
            if (!HelperGUI.positiveIntegerCheck(text)) {
                HelperGUI.error(parent, "Invalid entry. Please enter a non-negative value for quantity.");
                return;
            }

            int qty = Integer.parseInt(text);

            // Checks that quantity has a value of at least one.
            if (qty <= 0) {
                HelperGUI.error(parent, "Invalid entry. Quantity cannot be zero.");
                return;
            }

            // Checks if desired quantity is available and displays message if not
            if (qty > item.getQuantity()) {
                HelperGUI.error(parent, "We're sorry. There is not enough quantity in inventory.");
                return;
            }

            // Adds entered quantity of item to customer's cart
            customer.getCart().addItems(item, qty);

            HelperGUI.information(parent, "Added to cart!");

            // Refreshes the page so updated stock displays immediately
            parent.setContentPane(new CustomerInventoryPage(parent, store, customer));
            parent.revalidate();
            parent.repaint();
        }
    }
}