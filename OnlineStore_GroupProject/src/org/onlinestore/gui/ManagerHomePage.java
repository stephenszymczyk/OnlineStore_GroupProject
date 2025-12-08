package org.onlinestore.gui;

import javax.swing.*;
import java.awt.*;
import org.onlinestore.software.Item;
import org.onlinestore.software.OnlineStore;
import org.onlinestore.people.Manager;

public class ManagerHomePage extends JPanel {

    // Reference for main home page
    private MainHomePage parent;

    // Reference for OnlineStore system data
    private OnlineStore store;

    // Reference for the logged-in manager
    private Manager manager;

    // Constructor that initializes the page with the main window, store data, and manager
    public ManagerHomePage(MainHomePage parent, OnlineStore store, Manager manager) {
        this.parent = parent;     // Represents MainHomePage window
        this.store = store;       // Represents instance of OnlineStore
        this.manager = manager;   
        createGUI();               
    }

    // Creates the entire Manager Inventory page GUI
    private void createGUI() {

        // BorderLayout and apply theme background
        setLayout(new BorderLayout());
        setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Frame that contains all manager menu buttons
        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(400, 260));
        box.setBackground(ThemeGUI.PANEL_COLOR);
        box.setBorder(BorderFactory.createLineBorder(ThemeGUI.OUTLINE_COLOR));
        box.setLayout(new GridLayout(4, 1, 15, 15));
        box.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Title displayed at the top of the manager menu box
        JLabel title = new JLabel("Manager Home", SwingConstants.CENTER);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);

        // Creates theme buttons for manager actions
        JButton viewInventoryBtn = HelperGUI.createThemeButton("View Inventory");
        JButton addItemBtn = HelperGUI.createThemeButton("Add Item");
        JButton logoutBtn = HelperGUI.createThemeButton("Logout");

        // Opens manager's inventory perspective when View Inventory button is selected
        viewInventoryBtn.addActionListener(e ->
                parent.showManagerInventoryPage(manager)
        );

        // Opens Add Item page when Add Item button is selected
        addItemBtn.addActionListener(e -> {
            parent.setContentPane(new AddItemPage());
            parent.revalidate();
            parent.repaint();
        });

        // Returns user to main home page when Logout is selected
        logoutBtn.addActionListener(e -> parent.loadHomePage());

        // Adds title and menu buttons to display frame
        box.add(title);
        box.add(viewInventoryBtn);
        box.add(addItemBtn);
        box.add(logoutBtn);

        // Centers the manager menu in the window
        add(HelperGUI.centered(box), BorderLayout.CENTER);
    }


    // Inner class to initialize the Add Item page within the Manager Home Page
    private class AddItemPage extends JPanel {

        // Constructor for the Add Item Page GUI
        public AddItemPage() {

            // BorderLayout and apply theme background
            setLayout(new BorderLayout());
            setBackground(ThemeGUI.BACKGROUND_COLOR);

            // Frame that contains all input fields for adding a new item
            JPanel fieldBox = HelperGUI.createFieldBox(4);

            // Creates theme input fields
            JTextField nameField = HelperGUI.createThemeTextField();
            JTextField priceField = HelperGUI.createThemeTextField();
            JTextField descField = HelperGUI.createThemeTextField();
            JTextField qtyField = HelperGUI.createThemeTextField();

            // Adds each label and matching input field into the form container
            fieldBox.add(HelperGUI.createFieldRow(
                    HelperGUI.createFieldLabel("Name:"), nameField
            ));
            fieldBox.add(HelperGUI.createFieldRow(
                    HelperGUI.createFieldLabel("Price:"), priceField
            ));
            fieldBox.add(HelperGUI.createFieldRow(
                    HelperGUI.createFieldLabel("Description:"), descField
            ));
            fieldBox.add(HelperGUI.createFieldRow(
                    HelperGUI.createFieldLabel("Quantity:"), qtyField
            ));

            // Centers fields and adds title for the Add Item page
            JPanel finalLayout = HelperGUI.centerPanelWithTitle("Add New Item", fieldBox);
            add(finalLayout, BorderLayout.CENTER);

            // Creates theme Add Item and Back buttons
            JButton addBtn = HelperGUI.createThemeButton("Add Item");
            JButton backBtn = HelperGUI.createThemeButton("Back");

            // Row for Add Item and Back buttons
            JPanel buttonRow = HelperGUI.createButtonRow(addBtn, backBtn);
            add(buttonRow, BorderLayout.SOUTH);

            // Allows user to use enter key to move through input fields
            HelperGUI.enterKeyFields(nameField, priceField, descField, qtyField);

            // Allows user to submit new item's attributes using enter once they reach the final field
            HelperGUI.enterKeySubmit(qtyField, addBtn);

            // Logic for when user selects Add Item button
            addBtn.addActionListener(e -> {

                // Gets user input from item attribute fields
                String name = nameField.getText().trim();
                String price = priceField.getText().trim();
                String desc = descField.getText().trim();
                String qty = qtyField.getText().trim();

                // Checks to make sure fields are not empty
                if (HelperGUI.empty(name, price, desc, qty)) {
                    HelperGUI.error(parent, "All fields must be filled.");
                    return;
                }

                // Converts the price from text into a double and checks that the value is not negative
                double p;
                try {
                    p = Double.parseDouble(price);
                    if (p < 0) throw new Exception();
                } catch (Exception ex) {
                    HelperGUI.error(parent, "Invalid entry. Please enter a positive value for price.");
                    return;
                }

                // Checks that user inputs a non-negative number for item quantity
                if (!HelperGUI.positiveIntegerCheck(qty)) {
                    HelperGUI.error(parent, "Invalid entry. Please enter a non-negative value for quantity.");
                    return;
                }

                int q = Integer.parseInt(qty);

                // Creates new item and adds it to inventory
                Item newItem = new Item(p, name, desc, q);
                store.getInventory().addItem(newItem);
                store.saveOnlineStore();

                // Displays confirmation message and returns to manager home page
                HelperGUI.information(parent, "Item added successfully.");
                parent.showManagerHome(manager);
            });

            // Returns user to manager home page when Back is selected
            backBtn.addActionListener(e -> parent.showManagerHome(manager));
        }
    }
}