package org.onlinestore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.onlinestore.people.Customer;
import org.onlinestore.people.Person;
import org.onlinestore.software.Address;
import org.onlinestore.software.Cart;
import org.onlinestore.software.Inventory;
import org.onlinestore.software.Item;
import org.onlinestore.software.OnlineStore;

public class OnlineStoreGUI extends JFrame {

    // model
    private OnlineStore store;
    private Person currentUser;
    private Customer currentCustomer;
    private Cart currentCart;

    private JMenuBar menuBar;
    private JMenu accountMenu;
    private JMenu storeMenu;
    private JMenu cartMenu;

   
    private JMenuItem accountLoginItem;
    private JMenuItem accountCreateItem;
    private JMenuItem accountLogoutItem;

    private JMenuItem storeListItemsItem;
    private JMenuItem storeAddToCartItem;

    private JMenuItem cartViewItem;

    
    private JLabel infoLabel;

    // store item list
    private JList<String> itemList;
    private DefaultListModel<String> itemListModel;
    private List<Item> itemObjects;

    public OnlineStoreGUI(String windowTitle) {
        super(windowTitle);

        store = new OnlineStore();

        setSize(600, 300);
        setLayout(new BorderLayout());

    
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.RED);

        infoLabel = new JLabel("Welcome to the Wildcat Online Store");
        
        infoLabel.setForeground(Color.BLUE);

        JLabel SubMenu = new JLabel("Use the menus above to log in, view items, and manage your cart.");
        SubMenu.setForeground(Color.WHITE);

        headerPanel.add(infoLabel);
        headerPanel.add(SubMenu);
        add(headerPanel, BorderLayout.NORTH);

    
        itemListModel = new DefaultListModel<>();
        itemList = new JList<>(itemListModel);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setBackground(Color.WHITE);
        itemList.setForeground(Color.BLACK);
        
        itemObjects = new ArrayList<>();

        itemList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	//double click to add to cart
                if (e.getClickCount() == 2) {
                    addSelectedItemToCartFromList();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(itemList);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.BLUE);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildGUI();
        setVisible(true);
    }

    private void buildGUI() {
        menuBar = new JMenuBar();

        accountMenu = new JMenu("Account");
        storeMenu = new JMenu("Store");
        cartMenu = new JMenu("Cart");

        // account menu items
        accountLoginItem = new JMenuItem("Log In");
        accountCreateItem = new JMenuItem("Create Account");
        accountLogoutItem = new JMenuItem("Log Out");

        // store menu items
        storeListItemsItem = new JMenuItem("List Items");
        storeAddToCartItem = new JMenuItem("Add Selected Item to Cart");

        // cart menu items
        cartViewItem = new JMenuItem("View Cart");

        MenuListener listener = new MenuListener();

        accountLoginItem.addActionListener(listener);
        accountCreateItem.addActionListener(listener);
        accountLogoutItem.addActionListener(listener);

        storeListItemsItem.addActionListener(listener);
        storeAddToCartItem.addActionListener(listener);

        cartViewItem.addActionListener(listener);

        accountMenu.add(accountLoginItem);
        accountMenu.add(accountCreateItem);
        accountMenu.add(accountLogoutItem);

        storeMenu.add(storeListItemsItem);
        storeMenu.add(storeAddToCartItem);

        cartMenu.add(cartViewItem);

        menuBar.add(accountMenu);
        menuBar.add(storeMenu);
        menuBar.add(cartMenu);

        menuBar.setBackground(Color.RED);
        menuBar.setForeground(Color.WHITE);

        setJMenuBar(menuBar);
    }

    private class MenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JMenuItem source = (JMenuItem) e.getSource();

            if (source.equals(accountLoginItem)) {
                handleLogin();
            } 
            else if (source.equals(accountCreateItem)) {
                handleCreateAccount();
            }
            else if (source.equals(accountLogoutItem)) {
                handleLogout();
            }
            else if (source.equals(storeListItemsItem)) {
                handleListItems();
            } 
            else if (source.equals(storeAddToCartItem)) {
                addSelectedItemToCartFromList();
            } 
            else if (source.equals(cartViewItem)) {
                handleViewCart();
            }
        }
    }

    private void handleLogin() {
        String username = JOptionPane.showInputDialog(this, "Username:", "Log In", JOptionPane.PLAIN_MESSAGE);
        if (username == null) return;

        String password = JOptionPane.showInputDialog(this, "Password:", "Log In", JOptionPane.PLAIN_MESSAGE);
        if (password == null) return;

        Person user = store.login(username, password);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Login failed. Check username and password.", "Login", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        currentUser = user;

        if (user instanceof Customer) {
            currentCustomer = (Customer) user;
            currentCart = currentCustomer.getCart();
            infoLabel.setText("Logged in as customer: " + currentCustomer.getName());
            loadInventoryIntoList();
        } 
         else {
            JOptionPane.showMessageDialog(this, "Unknown user type.", "Login", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void handleCreateAccount() {
        String fullName = JOptionPane.showInputDialog(this, "Full name:", "Create Account", JOptionPane.PLAIN_MESSAGE);
        if (fullName == null || fullName.trim().equals("")) return;

        String username = JOptionPane.showInputDialog(this, "Choose a username:", "Create Account", JOptionPane.PLAIN_MESSAGE);
        if (username == null || username.trim().equals("")) return;

        if (!store.usernameAvailable(username)) {
            JOptionPane.showMessageDialog(this, "That username is already in use.", "Create Account", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        String password = JOptionPane.showInputDialog(this, "Choose a password:", "Create Account", JOptionPane.PLAIN_MESSAGE);
        if (password == null || password.trim().equals("")) return;

        String street = JOptionPane.showInputDialog(this, "Street:", "Create Account", JOptionPane.PLAIN_MESSAGE);
        if (street == null) return;
        String city = JOptionPane.showInputDialog(this, "City:", "Create Account", JOptionPane.PLAIN_MESSAGE);
        if (city == null) return;
        String state = JOptionPane.showInputDialog(this, "State:", "Create Account", JOptionPane.PLAIN_MESSAGE);
        if (state == null) return;
        String zip = JOptionPane.showInputDialog(this, "ZIP:", "Create Account", JOptionPane.PLAIN_MESSAGE);
        if (zip == null) return;

        Address addr = new Address(street, city, state, zip);

        Customer c = store.createAccount(fullName, username, password, addr);
        if (c == null) {
            JOptionPane.showMessageDialog(this, "Account could not be created.", "Create Account", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Account created. You can now log in.", "Create Account", JOptionPane.PLAIN_MESSAGE);
    }

    private void handleLogout() {
        currentUser = null;
        currentCustomer = null;
        currentCart = null;
        infoLabel.setText("Welcome to the Wildcat Online Store");
        itemListModel.clear();
        itemObjects.clear();
    }

    // load items 
    private void loadInventoryIntoList() {
        Inventory inv = store.getInventory();
        List<Item> items = inv.getItems();

        itemListModel.clear();
        itemObjects.clear();

        if (items == null) return;

        for (Item it : items) {
            String line = it.getName() + " - $" + it.getPrice() + " (In stock: " + it.getQuantity() + ")";
            itemListModel.addElement(line);
            itemObjects.add(it);
        }
    }

    private void handleListItems() {
        loadInventoryIntoList();
        JOptionPane.showMessageDialog(this, "Double-click an item to add it to your cart.", "Items", JOptionPane.PLAIN_MESSAGE);
    }

    
    private void addSelectedItemToCartFromList() {
        if (currentCustomer == null) {
            JOptionPane.showMessageDialog(this, "You must be logged in as a customer to add items.", "Add to Cart", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        int index = itemList.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Select an item in the list first.", "Add to Cart", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        Item item = itemObjects.get(index);
        if (item.getQuantity() <= 0) {
            JOptionPane.showMessageDialog(this, "That item is out of stock.", "Add to Cart", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        currentCart = currentCustomer.getCart();
        currentCart.addItem(item);

        JOptionPane.showMessageDialog(this, "Added \"" + item.getName() + "\" to your cart.", "Add to Cart", JOptionPane.PLAIN_MESSAGE);
        loadInventoryIntoList();
    }

    private void handleViewCart() {
        if (currentCustomer == null || currentCart == null) {
            JOptionPane.showMessageDialog(this, "You must be logged in as a customer to view the cart.", "Cart", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        
        List<Item> items = currentCart.getItems();
        if (items == null || items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty.", "Cart", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        
        StringBuilder sb = new StringBuilder();
        double subtotal = 0.0;
        sb.append("Items in your cart:\n\n");

        
        for (Item it : items) {
            sb.append(it.getName()).append(" - $").append(it.getPrice()).append("\n");
            subtotal += it.getPrice();
        }

        double taxRate = currentCustomer.getTaxRate();
        double tax = subtotal * taxRate;
        double total = subtotal + tax;

        sb.append("\nSubtotal: $").append(String.format("%.2f", subtotal));
        sb.append("\nTax: $").append(String.format("%.2f", tax));
        sb.append("\nTotal: $").append(String.format("%.2f", total));
        JOptionPane.showMessageDialog(this, sb.toString(), "Cart", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        new OnlineStoreGUI("Wildcat Online Store");
    }
}
