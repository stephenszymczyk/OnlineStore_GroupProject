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
        this.parent = parent;    
        this.store = store;      
        this.manager = manager;  
        createGUI();              
    }

    // Creates the entire Manager Home Page GUI
    private void createGUI() {

        setLayout(new BorderLayout());
        setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Title at the top
        JLabel title = new JLabel("Manager Home Page", SwingConstants.CENTER);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        // Button panel
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));

        JButton viewInventoryBtn = HelperFunctionsGUI.createThemeButton("View Inventory");
        JButton addItemBtn = HelperFunctionsGUI.createThemeButton("Add Item");
        JButton viewOrdersBtn = HelperFunctionsGUI.createThemeButton("View Orders");
        JButton logoutBtn = HelperFunctionsGUI.createThemeButton("Logout");

        int width = 230;
        Dimension btnDimension = new Dimension(width, 35);

        viewInventoryBtn.setMaximumSize(btnDimension);
        addItemBtn.setMaximumSize(btnDimension);
        viewOrdersBtn.setMaximumSize(btnDimension);
        logoutBtn.setMaximumSize(btnDimension);
        viewInventoryBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addItemBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewOrdersBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnPanel.add(viewInventoryBtn);
        btnPanel.add(Box.createVerticalStrut(15));
        btnPanel.add(addItemBtn);
        btnPanel.add(Box.createVerticalStrut(15));
        btnPanel.add(viewOrdersBtn);
        btnPanel.add(Box.createVerticalStrut(15));
        btnPanel.add(logoutBtn);

        add(HelperFunctionsGUI.centerThePanel(btnPanel), BorderLayout.CENTER);

        // Button actions
        viewInventoryBtn.addActionListener(e -> parent.showManagerInventoryPage(manager));

        addItemBtn.addActionListener(e -> {
            parent.setContentPane(new AddItemPage());
            parent.revalidate();
            parent.repaint();
        });

        viewOrdersBtn.addActionListener(e -> {
            parent.setContentPane(new ManagerOrderHistoryPage(parent, store, manager));
            parent.revalidate();
            parent.repaint();
        });

        logoutBtn.addActionListener(e -> parent.loadHomePage());
    }

    // Inner class to initialize the Add Item page within the Manager Home Page
    private class AddItemPage extends JPanel {

        // Constructor for the Add Item Page GUI
        public AddItemPage() {

            setLayout(new BorderLayout());
            setBackground(ThemeGUI.BACKGROUND_COLOR);

            // Main container for input fields
            JPanel container = new JPanel();
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            container.setOpaque(false);

            JLabel title = new JLabel("Add New Item", SwingConstants.LEFT);
            title.setFont(ThemeGUI.SUBTITLE_FONT);
            title.setForeground(ThemeGUI.TEXT_MAIN);
            title.setAlignmentX(Component.LEFT_ALIGNMENT);
            title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

            container.add(title);

            JPanel input = new JPanel();
            input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
            input.setOpaque(false);

            int fieldWidth = 280;
            Dimension fieldSize = new Dimension(fieldWidth, 34);
            JTextField nameField = HelperFunctionsGUI.createThemeTextField();
            JTextField priceField = HelperFunctionsGUI.createThemeTextField();
            JTextField descField = HelperFunctionsGUI.createThemeTextField();
            JTextField qtyField = HelperFunctionsGUI.createThemeTextField();

            nameField.setMaximumSize(fieldSize);
            priceField.setMaximumSize(fieldSize);
            descField.setMaximumSize(fieldSize);
            qtyField.setMaximumSize(fieldSize);

            input.add(HelperFunctionsGUI.fieldLayout("Name", nameField));
            input.add(Box.createVerticalStrut(12));
            input.add(HelperFunctionsGUI.fieldLayout("Price", priceField));
            input.add(Box.createVerticalStrut(12));
            input.add(HelperFunctionsGUI.fieldLayout("Description", descField));
            input.add(Box.createVerticalStrut(12));
            input.add(HelperFunctionsGUI.fieldLayout("Quantity", qtyField));
            input.add(Box.createVerticalStrut(20));
            container.add(input);

            // Scrollable container panel
            JPanel containerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            containerPanel.setOpaque(false);
            containerPanel.add(container);

            JScrollPane scroll = new JScrollPane(containerPanel);
            scroll.setBorder(null);
            scroll.setOpaque(false);
            scroll.getViewport().setOpaque(false);
            scroll.getVerticalScrollBar().setUnitIncrement(22);

            add(scroll, BorderLayout.CENTER);

            // Creates themed buttons
            JButton addBtn = HelperFunctionsGUI.createThemeButton("Add Item");
            JButton backBtn = HelperFunctionsGUI.createThemeButton("Back");
            JPanel buttonRow = HelperFunctionsGUI.createButtonRow(addBtn, backBtn);
            add(buttonRow, BorderLayout.SOUTH);

            // Lets user move through fields using enter key and submit using enter key once on the last field
            HelperFunctionsGUI.enterKeyFields(nameField, priceField, descField, qtyField);
            HelperFunctionsGUI.enterKeySubmit(qtyField, addBtn);

            // Logic for adding items 
            addBtn.addActionListener(e -> {

                String name = nameField.getText().trim();
                String price = priceField.getText().trim();
                String desc = descField.getText().trim();
                String qty = qtyField.getText().trim();

                if (HelperFunctionsGUI.empty(name, price, desc, qty)) {
                    HelperFunctionsGUI.error(parent, "All fields must be filled.");
                    return;
                }

                double p;
                try {
                    p = Double.parseDouble(price);
                    if (p < 0) throw new Exception();
                } catch (Exception exceptionCall) {
                    HelperFunctionsGUI.error(parent, "Invalid entry. Please enter a positive value for price.");
                    return;
                }

                if (!HelperFunctionsGUI.positiveIntegerCheck(qty)) {
                    HelperFunctionsGUI.error(parent, "Invalid entry. Please enter a non-negative value for quantity.");
                    return;
                }

                int q = Integer.parseInt(qty);
                Item newItem = new Item(p, name, desc, q);
                store.getInventory().addItem(newItem);
                store.saveOnlineStore();

                HelperFunctionsGUI.information(parent, "Item added successfully.");
                parent.showManagerHome(manager);
            });

            backBtn.addActionListener(e -> parent.showManagerHome(manager));
        }
    }
}