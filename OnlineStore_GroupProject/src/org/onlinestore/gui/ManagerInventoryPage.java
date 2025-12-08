package org.onlinestore.gui;

import javax.swing.*;
import java.awt.*;
import org.onlinestore.software.Item;
import org.onlinestore.software.OnlineStore;
import org.onlinestore.people.Manager;

public class ManagerInventoryPage extends JPanel {

    // Reference for main home page
    private MainHomePage parent;

    // Reference for OnlineStore system data
    private OnlineStore store;

    // Reference for the logged-in manager
    private Manager manager;

    // Constructor that initializes the page with the main window, store data, and manager
    public ManagerInventoryPage(MainHomePage parent, OnlineStore store, Manager manager) {
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

        // Title displayed at the top of the inventory page
        JLabel title = new JLabel("Inventory", SwingConstants.CENTER);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(title, BorderLayout.NORTH);

        // Panel that contains the display for inventory items and their attributes
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

        // Back button that returns manager to the Manager Home page
        JButton backBtn = HelperGUI.createThemeButton("Back");
        backBtn.addActionListener(e -> parent.showManagerHome(manager));

        // Panel used to center the Back button at the bottom of the page
        JPanel backPanel = new JPanel();
        backPanel.setBackground(ThemeGUI.BACKGROUND_COLOR);
        backPanel.add(backBtn);

        add(backPanel, BorderLayout.SOUTH);
    }

    // Creates a displayed version of each inventory item that shows all of its attributes
    private JPanel createItemDisplay(Item item) {

        // Item display panel that contains theme text grouping
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
                "Quantity: " + item.getQuantity()
        );
        itemAttributes.setEditable(false);
        itemAttributes.setBackground(ThemeGUI.PANEL_COLOR);
        itemAttributes.setForeground(ThemeGUI.TEXT_MAIN);
        itemAttributes.setFont(ThemeGUI.REGULAR_FONT);

        itemDisplay.add(itemAttributes, BorderLayout.CENTER);

        // Button row for editing or removing the item
        JPanel buttonRow = new JPanel(new FlowLayout());
        buttonRow.setOpaque(false);

        JButton editNameBtn = HelperGUI.createThemeButton("Edit Name");
        JButton editPriceBtn = HelperGUI.createThemeButton("Edit Price");
        JButton editDescBtn = HelperGUI.createThemeButton("Edit Description");
        JButton editQtyBtn = HelperGUI.createThemeButton("Edit Quantity");
        JButton removeBtn = HelperGUI.createThemeButton("Remove Item");

        // Pop-up boxes for editing item attributes
        editNameBtn.addActionListener(e -> editNamePopUp(item));
        editPriceBtn.addActionListener(e -> editPricePopUp(item));
        editDescBtn.addActionListener(e -> editDescriptionPopUp(item));
        editQtyBtn.addActionListener(e -> openEditQuantityDialog(item));

        // Confirmation display and removes item after manager confirms they want to delete it
        removeBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    parent,
                    "Do you want to remove '" + item.getName() + "' from inventory?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                store.getInventory().removeItem(item);
                store.saveOnlineStore();
                refreshPage();
            }
        });

        // Adds buttons for editing item attributes
        buttonRow.add(editNameBtn);
        buttonRow.add(editPriceBtn);
        buttonRow.add(editDescBtn);
        buttonRow.add(editQtyBtn);
        buttonRow.add(removeBtn);

        itemDisplay.add(buttonRow, BorderLayout.SOUTH);

        return itemDisplay;
    }

    // Pop-up box for editing item name
    private void editNamePopUp(Item item) {
        JTextField field = new JTextField(item.getName());
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Enter new name:"));
        panel.add(field);

        int userSelection = JOptionPane.showConfirmDialog(parent, panel, "Edit Name", JOptionPane.OK_CANCEL_OPTION);

        if (userSelection == JOptionPane.OK_OPTION) {
            String text = field.getText().trim();
            if (HelperGUI.empty(text)) {
                HelperGUI.error(parent, "Field cannot be empty.");
                return;
            }
            item.setName(text);
            store.saveOnlineStore();
            refreshPage();
        }
    }

    // Pop-up box for editing item price
    private void editPricePopUp(Item item) {
        JTextField field = new JTextField(String.valueOf(item.getPrice()));
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Enter new price:"));
        panel.add(field);

        int userSelection = JOptionPane.showConfirmDialog(parent, panel, "Edit Price", JOptionPane.OK_CANCEL_OPTION);

        if (userSelection == JOptionPane.OK_OPTION) {
            try {
                double price = Double.parseDouble(field.getText().trim());
                if (price < 0) throw new Exception();
                item.setPrice(price);
                store.saveOnlineStore();
                refreshPage();
            } catch (Exception e) {
                HelperGUI.error(parent, "Invalid entry. Please enter a positive value for price.");
            }
        }
    }

    // Pop-up box for editing item description
    private void editDescriptionPopUp(Item item) {
        JTextField field = new JTextField(item.getDescription());
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Enter new description:"));
        panel.add(field);

        int userSelection = JOptionPane.showConfirmDialog(parent, panel, "Edit Description", JOptionPane.OK_CANCEL_OPTION);

        if (userSelection == JOptionPane.OK_OPTION) {
            String text = field.getText().trim();
            if (HelperGUI.empty(text)) {
                HelperGUI.error(parent, "Field cannot be empty.");
                return;
            }
            item.setDescription(text);
            store.saveOnlineStore();
            refreshPage();
        }
    }

    // Pop-up box for editing item quantity
    private void openEditQuantityDialog(Item item) {
        JTextField field = new JTextField(String.valueOf(item.getQuantity()));
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Enter new quantity:"));
        panel.add(field);

        int userSelection = JOptionPane.showConfirmDialog(parent, panel, "Edit Quantity", JOptionPane.OK_CANCEL_OPTION);

        if (userSelection == JOptionPane.OK_OPTION) {
            String text = field.getText().trim();
            if (!HelperGUI.positiveIntegerCheck(text)) {
                HelperGUI.error(parent, "Invalid entry. Please enter a non-negative value for quantity.");
                return;
            }
            item.setQuantity(Integer.parseInt(text));
            store.saveOnlineStore();
            refreshPage();
        }
    }

    // Refreshes the inventory page so changes are displayed as they occur 
    private void refreshPage() {
        parent.showManagerInventoryPage(manager);
    }
}