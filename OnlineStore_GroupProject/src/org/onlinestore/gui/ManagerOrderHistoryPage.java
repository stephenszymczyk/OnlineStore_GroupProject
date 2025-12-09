package org.onlinestore.gui;

import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.*;
import org.onlinestore.software.OnlineStore;
import org.onlinestore.software.Order;
import org.onlinestore.people.Manager;

public class ManagerOrderHistoryPage extends JPanel {

    // Reference to MainHomePage window
    private MainHomePage parent;

    // Reference to OnlineStore system data
    private OnlineStore store;

    // Reference to logged-in manager
    private Manager manager;

    // Constructor that initializes the page
    public ManagerOrderHistoryPage(MainHomePage parent, OnlineStore store, Manager manager) {
        this.parent = parent;     // Represents MainHomePage window
        this.store = store;       // Represents instance of OnlineStore
        this.manager = manager;
        createGUI();
    }

    // Creates the entire View Order History page GUI
    private void createGUI() {

        // BorderLayout and theme background
        setLayout(new BorderLayout());
        setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Title displayed at the top of the page
        JLabel title = new JLabel("Order History", SwingConstants.CENTER);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // Panel that displays all orders
        JPanel ordersPanel = new JPanel();
        ordersPanel.setLayout(new BoxLayout(ordersPanel, BoxLayout.Y_AXIS));
        ordersPanel.setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Displays message if there are no orders
        if (store.getOrderHistory().getAllOrders().isEmpty()) {

            JLabel emptyLabel = new JLabel("No orders have been placed yet.", SwingConstants.CENTER);
            emptyLabel.setForeground(ThemeGUI.TEXT_MAIN);
            emptyLabel.setFont(ThemeGUI.REGULAR_FONT);

            JPanel centered = new JPanel(new GridBagLayout());
            centered.setBackground(ThemeGUI.BACKGROUND_COLOR);
            centered.add(emptyLabel);

            add(centered, BorderLayout.CENTER);
        }
        else {

            // Adds a display panel for each order
            for (Order order : store.getOrderHistory().getAllOrders()) {
                ordersPanel.add(createOrderDisplay(order));
                ordersPanel.add(Box.createVerticalStrut(15));
            }

            JScrollPane scroll = new JScrollPane(ordersPanel);
            scroll.setBorder(null);
            scroll.getViewport().setBackground(ThemeGUI.BACKGROUND_COLOR);
            scroll.getVerticalScrollBar().setUnitIncrement(40);

            add(scroll, BorderLayout.CENTER);
        }

        // Back button returns manager to Manager Home Page
        JButton backBtn = HelperGUI.createThemeButton("Back");
        backBtn.addActionListener(e -> parent.showManagerHome(manager));

        JPanel bottom = new JPanel();
        bottom.setBackground(ThemeGUI.BACKGROUND_COLOR);
        bottom.add(backBtn);

        add(bottom, BorderLayout.SOUTH);
    }

    // Creates a panel displaying order information
 // Creates a panel displaying order information
    private JPanel createOrderDisplay(Order order) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ThemeGUI.PANEL_COLOR);
        panel.setBorder(BorderFactory.createLineBorder(ThemeGUI.OUTLINE_COLOR));

        // Formats date and time of order
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        StringBuilder stringBuilder = new StringBuilder();

        // Adds order number
        stringBuilder.append("Order Number: ").append(order.getConfirmationNumber()).append("\n");

        // Adds customer's name
        stringBuilder.append("Customer: ").append(order.getCustomer().getName()).append("\n");

        // Adds date and time that the order was placed
        stringBuilder.append("Date: ").append(order.getOrderDate().format(dateTimeFormat)).append("\n");

        // Adds total cost of the order
        stringBuilder.append("Total Cost: $")
                     .append(String.format("%.2f", order.getTotalCost())).append("\n");

        // Adds the saved shipping address
        stringBuilder.append("Shipping Address:\n");
        stringBuilder.append("   ").append(order.getStreetAddress()).append("\n");
        stringBuilder.append("   ").append(order.getCity())
                     .append(", ").append(order.getState())
                     .append(" ").append(order.getZipCode()).append("\n");

        stringBuilder.append("\nItems Ordered:\n");

        // Lists each item and quantity purchased
        order.getItemsPurchased().forEach((item, qty) -> {
            stringBuilder.append(" - ")
              .append(item.getName())
              .append(" (x").append(qty).append(")\n");
        });

        JTextArea area = new JTextArea(stringBuilder.toString());
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setBackground(ThemeGUI.PANEL_COLOR);
        area.setForeground(ThemeGUI.TEXT_MAIN);
        area.setFont(ThemeGUI.SCROLL_FONT);

        panel.add(area, BorderLayout.CENTER);

        return panel;
    }
}