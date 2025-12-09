package org.onlinestore.gui;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import org.onlinestore.people.Customer;
import org.onlinestore.software.Cart;
import org.onlinestore.software.Item;
import org.onlinestore.software.OnlineStore;
import org.onlinestore.software.Transaction;
import org.onlinestore.software.Order;

public class CheckoutPage extends JPanel {

    // Reference to the main home page window
    private MainHomePage parent;

    // Reference to the OnlineStore system data
    private OnlineStore store;

    // Reference to the logged-in customer
    private Customer customer;

    // Constructor
    public CheckoutPage(MainHomePage parent, OnlineStore store, Customer customer) {
        this.parent = parent;
        this.store = store;
        this.customer = customer;
        createGUI();
    }

    // Creates the entire Checkout page GUI
    private void createGUI() {

        setLayout(new BorderLayout());
        setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Title displayed at the top of page
        JLabel title = new JLabel("Checkout", SwingConstants.CENTER);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        Cart cart = customer.getCart();

        // Main panel for contents of scroll panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        // Frame for item lists
        JPanel itemsBox = new JPanel();
        itemsBox.setLayout(new BoxLayout(itemsBox, BoxLayout.Y_AXIS));
        itemsBox.setBackground(ThemeGUI.PANEL_COLOR);
        itemsBox.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ThemeGUI.OUTLINE_COLOR, 1, true),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        itemsBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cartContentTitle = HelperFunctionsGUI.createLabelForField("Items in Your Cart:");
        cartContentTitle.setFont(ThemeGUI.SUBTITLE_FONT);
        cartContentTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        cartContentTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        itemsBox.add(cartContentTitle);

        // Combines duplicate cart items using map so that they appear using a single quantity
        Map<Item, Integer> quantityInCart = new LinkedHashMap<>();
        for (Item cartItem : cart.getItems()) {
            quantityInCart.put(cartItem, quantityInCart.getOrDefault(cartItem, 0) + 1);
        }
        for (Map.Entry<Item, Integer> listedItem : quantityInCart.entrySet()) {
            Item currentItem = listedItem.getKey();
            int qty = listedItem.getValue();

            JLabel label = HelperFunctionsGUI.createLabelForField(
                currentItem.getName() + " (x" + qty + ") — $" + String.format("%.2f", currentItem.getPrice())
            );
            label.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            itemsBox.add(label);
        }

        mainPanel.add(itemsBox);
        mainPanel.add(Box.createVerticalStrut(20));

        // Panel for order details
        JPanel orderDetailPanel = new JPanel();
        orderDetailPanel.setLayout(new BoxLayout(orderDetailPanel, BoxLayout.Y_AXIS));
        orderDetailPanel.setBackground(ThemeGUI.PANEL_COLOR);
        orderDetailPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ThemeGUI.OUTLINE_COLOR, 1, true),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        orderDetailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtotalLabel = HelperFunctionsGUI.createLabelForField(
            "Subtotal: $" + String.format("%.2f", cart.getSubtotal())
        );
        subtotalLabel.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        JLabel taxLabel = HelperFunctionsGUI.createLabelForField(
            "Tax: $" + String.format("%.2f", cart.getTax())
        );
        taxLabel.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));

        JLabel totalLabel = HelperFunctionsGUI.createLabelForField(
            "Total: $" + String.format("%.2f", cart.getTotal())
        );
        totalLabel.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));

        JLabel addressLabel = HelperFunctionsGUI.createLabelForField("<html>"
            + "Shipping Address:<br>"
            + customer.getAddress().getStreetAddress() + "<br>"
            + customer.getAddress().getCity() + ", "
            + customer.getAddress().getState() + " "
            + customer.getAddress().getZipCode()
            + "</html>"
        );
        addressLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));

        orderDetailPanel.add(subtotalLabel);
        orderDetailPanel.add(taxLabel);
        orderDetailPanel.add(totalLabel);
        orderDetailPanel.add(addressLabel);

        JButton changeAddressButton = HelperFunctionsGUI.createThemeButton("Change Address");
        changeAddressButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        changeAddressButton.addActionListener(e -> {
            parent.setContentPane(new AddressUpdatePage(parent, store, customer));
            parent.revalidate();
            parent.repaint();
        });

        orderDetailPanel.add(changeAddressButton);

        mainPanel.add(orderDetailPanel);

        // Scrolling panel
        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        add(scroll, BorderLayout.CENTER);

        JButton paymentButton = HelperFunctionsGUI.createThemeButton("Confirm Payment");
        JButton cancelPaymentButton = HelperFunctionsGUI.createThemeButton("Cancel");

        paymentButton.addActionListener(e -> processTransaction());
        cancelPaymentButton.addActionListener(e -> parent.showCustomerHome(customer));

        JPanel bottom = HelperFunctionsGUI.createButtonRow(paymentButton, cancelPaymentButton);
        add(bottom, BorderLayout.SOUTH);
    }

    // Gets user payment information and processes transaction
    private void processTransaction() {

        JTextField creditCardNumber = new JTextField();
        JTextField expirationDate = new JTextField();
        JTextField cvvNumber = new JTextField();

        JPanel box = new JPanel(new GridLayout(3, 2, 12, 12));
        box.add(new JLabel("16 Digit Credit Card Number:"));
        box.add(creditCardNumber);
        box.add(new JLabel("Expiration Date (MM/YY):"));
        box.add(expirationDate);
        box.add(new JLabel("3 Digit CVV:"));
        box.add(cvvNumber);

        int selection = JOptionPane.showConfirmDialog(parent, box, "Enter Payment Information", 
        		JOptionPane.OK_CANCEL_OPTION
        );

        if (selection != JOptionPane.OK_OPTION)
            return;

        Transaction transaction = new Transaction(customer, store);

        Order order = transaction.processPayment(
            creditCardNumber.getText(),
            expirationDate.getText(),
            cvvNumber.getText()
        );

        if (order == null) {
            HelperFunctionsGUI.error(parent, "Invalid payment information.");
            return;
        }

        HelperFunctionsGUI.information(parent,
            "Your payment has been confirmed.\n"
            + "Confirmation Number: " + order.getConfirmationNumber()
        );

        parent.showCustomerHome(customer);
    }
}