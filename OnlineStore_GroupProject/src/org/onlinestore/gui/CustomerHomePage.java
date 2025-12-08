package org.onlinestore.gui;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.onlinestore.software.Item;
import org.onlinestore.software.OnlineStore;
import org.onlinestore.people.Customer;

public class CustomerHomePage extends JPanel {

    // Reference to the main home page window
    private MainHomePage parent;

    // Reference to the OnlineStore system data
    private OnlineStore store;

    // Reference to the logged-in customer
    private Customer customer;

    // Constructor that initializes the page with the main window, store data, and customer
    public CustomerHomePage(MainHomePage parent, OnlineStore store, Customer customer) {
        this.parent = parent;       // Represents MainHomePage window
        this.store = store;         // Represents instance of OnlineStore
        this.customer = customer;   
        createGUI();                 
    }

    // Create the entire Customer Home page GUI
    private void createGUI() {

        // BorderLayout and theme background
        setLayout(new BorderLayout());
        setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Frame that contains all customer menu buttons
        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(400, 300));
        box.setBackground(ThemeGUI.PANEL_COLOR);
        box.setBorder(BorderFactory.createLineBorder(ThemeGUI.OUTLINE_COLOR));
        box.setLayout(new GridLayout(4, 1, 15, 15));
        box.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Title displayed at the top of the customer menu box
        JLabel title = new JLabel("Customer Home", SwingConstants.CENTER);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);

        // Creates theme buttons for customer actions
        JButton viewInventoryBtn = HelperGUI.createThemeButton("View Inventory");
        JButton viewCartBtn = HelperGUI.createThemeButton("View Cart");
        JButton logoutBtn = HelperGUI.createThemeButton("Logout");

        // Opens customer inventory page when View Inventory is selected
        viewInventoryBtn.addActionListener(e ->
                parent.showCustomerInventoryPage(customer)
        );

        // Opens the cart display when View Cart is selected
        viewCartBtn.addActionListener(e -> {
            parent.setContentPane(createCartPage());
            parent.revalidate();
            parent.repaint();
        });

        // Returns user to main home page when Logout is selected
        logoutBtn.addActionListener(e ->
                parent.loadHomePage()
        );

        // Adds title and menu buttons to display frame
        box.add(title);
        box.add(viewInventoryBtn);
        box.add(viewCartBtn);
        box.add(logoutBtn);

        // Centers the customer menu in the window
        add(HelperGUI.centered(box), BorderLayout.CENTER);
    }

    // Creates customer's Cart page
    private JPanel createCartPage() {
    	
    	// BorderLayout and apply theme background
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Title displayed at top of cart page
        JLabel title = new JLabel("Your Cart", SwingConstants.CENTER);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        // Panel that contains the display for the cart items
        JPanel cartItemDisplay = new JPanel();
        cartItemDisplay.setLayout(new BoxLayout(cartItemDisplay, BoxLayout.Y_AXIS));
        cartItemDisplay.setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Displays message if customer's cart is empty
        if (customer.getCart().getItems().isEmpty()) {

            JLabel emptyCartLabel = new JLabel("Your cart is currently empty.", SwingConstants.CENTER);
            emptyCartLabel.setForeground(ThemeGUI.TEXT_MAIN);
            emptyCartLabel.setFont(ThemeGUI.REGULAR_FONT);

            JPanel centered = new JPanel(new GridBagLayout());
            centered.setBackground(ThemeGUI.BACKGROUND_COLOR);
            centered.add(emptyCartLabel);

            panel.add(centered, BorderLayout.CENTER);

        } 
        else {

            // Hash to save items so they do not display as duplicates in the customer's cart
            Set<Item> preventItemDuplicate = new LinkedHashSet<>(customer.getCart().getItems());

            // Adds a display for each item type
            for (Item item : preventItemDuplicate) {
                cartItemDisplay.add(createCartItemDisplay(item));
                cartItemDisplay.add(Box.createVerticalStrut(10));
            }

            // Allows user to scroll through cart if it contains lots of items
            JScrollPane scroll = new JScrollPane(cartItemDisplay);
            scroll.setBorder(null);
            scroll.getViewport().setBackground(ThemeGUI.BACKGROUND_COLOR);
            panel.add(scroll, BorderLayout.CENTER);
        }

        // Back button for returning to customer home page and checkout button
        JButton backBtn = HelperGUI.createThemeButton("Back");
        JButton checkoutBtn = HelperGUI.createThemeButton("Checkout");

        // Returns user to customer home page
        backBtn.addActionListener(e -> {
            parent.showCustomerHome(customer);
            parent.revalidate();
            parent.repaint();
        });

        // Takes user to checkout page
        checkoutBtn.addActionListener(e -> {
            parent.setContentPane(new CheckoutPage(parent, store, customer));
            parent.revalidate();
            parent.repaint();
        });

        JPanel bottom = new JPanel();
        bottom.setBackground(ThemeGUI.BACKGROUND_COLOR);
        bottom.add(backBtn);
        bottom.add(checkoutBtn);

        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // Creates a displayed version of each item inside the customer's cart
    private JPanel createCartItemDisplay(Item item) {

        JPanel cartItemDisplay = new JPanel(new BorderLayout());
        cartItemDisplay.setBorder(BorderFactory.createLineBorder(ThemeGUI.OUTLINE_COLOR));
        cartItemDisplay.setBackground(ThemeGUI.PANEL_COLOR);
        cartItemDisplay.setPreferredSize(new Dimension(500, 140));

        // Keeps track of the quantity of a particular item in the cart
        long quantityInCart = customer.getCart().getItems().stream()
                .filter(i -> i == item)
                .count();

        // Text for all of the item's attributes
        JTextArea itemAttributes = new JTextArea(
                "Name: " + item.getName() + "\n" +
                "Price: $" + item.getPrice() + "\n" +
                "Description: " + item.getDescription() + "\n" +
                "Quantity in Cart: " + quantityInCart
        );
        itemAttributes.setEditable(false);
        itemAttributes.setBackground(ThemeGUI.PANEL_COLOR);
        itemAttributes.setForeground(ThemeGUI.TEXT_MAIN);
        itemAttributes.setFont(ThemeGUI.REGULAR_FONT);

        cartItemDisplay.add(itemAttributes, BorderLayout.CENTER);

        // Buttons for removing the item or editing its quantity in the cart
        JButton removeBtn = HelperGUI.createThemeButton("Remove");
        JButton editQtyBtn = HelperGUI.createThemeButton("Edit Quantity");

        // Removes this item from the cart
        removeBtn.addActionListener(e -> {
            customer.getCart().removeItem(item, store.getInventory());
            HelperGUI.information(parent, "Item removed from cart.");
            refreshCartPage();
        });

        // Pop-up box for editing item quantity in cart
        editQtyBtn.addActionListener(e -> editQuantityPopUp(item, quantityInCart));

        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.add(removeBtn);
        btnPanel.add(editQtyBtn);

        cartItemDisplay.add(btnPanel, BorderLayout.SOUTH);

        return cartItemDisplay;
    }

    // Pop-up box for editing item quantity
    private void editQuantityPopUp(Item item, long currentQty) {

        JTextField field = new JTextField();
        field.setText("" + currentQty);

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setOpaque(false);
        panel.add(new JLabel("Enter new quantity:"));
        panel.add(field);

        // Customer selects OK to confirm new quantity or cancel to go back to cart
        int userSelection = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Edit Quantity",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (userSelection != JOptionPane.OK_OPTION) return;

        String text = field.getText().trim();

        // Checks for valid quantity input
        if (!HelperGUI.positiveIntegerCheck(text)) {
            HelperGUI.error(parent, "Invalid entry. Please enter a non-negative value for quantity.");
            return;
        }

        int newQty = Integer.parseInt(text);

        // If user enters same quantity then no changes are made
        if (newQty == currentQty) return;

        // Checks the difference between the old quantity and the new quantity
        int quantityDifference = newQty - (int) currentQty;

        // Adds more of the item to cart and displays message if not enough inventory is available
        if (quantityDifference > 0) {
            if (quantityDifference > item.getQuantity()) {
                HelperGUI.error(parent, "We're sorry. There is not enough quantity in inventory.");
                return;
            }
            customer.getCart().addItems(item, quantityDifference);
        }
        // Removes items from cart using appropriate quantity
        else {
            int quantityRemoved = -quantityDifference;
            for (int i = 0; i < quantityRemoved; i++)
                customer.getCart().removeItem(item, store.getInventory());
        }

        HelperGUI.information(parent, "Quantity updated.");
        refreshCartPage();
    }

    // Refreshes the Cart Page after changes
    private void refreshCartPage() {
        parent.setContentPane(createCartPage());
        parent.revalidate();
        parent.repaint();
    }
}