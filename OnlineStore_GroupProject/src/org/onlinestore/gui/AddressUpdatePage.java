package org.onlinestore.gui;

import javax.swing.*;
import java.awt.*;
import org.onlinestore.people.Customer;
import org.onlinestore.software.OnlineStore;

public class AddressUpdatePage extends JPanel {

    // Reference to the main home page window
    private MainHomePage parent;

    // Reference to the OnlineStore system data
    private OnlineStore store;

    // Reference to the logged-in customer
    private Customer customer;

    // Constructor that initializes the page with the main window, store data, and customer
    public AddressUpdatePage(MainHomePage parent, OnlineStore store, Customer customer) {
        this.parent = parent;       // Represents MainHomePage window
        this.store = store;         // Represents instance of OnlineStore
        this.customer = customer;   
        createGUI();                 
    }

    // Creates the entire Address Update Page GUI
    private void createGUI() {

        // BorderLayout and theme background
        setLayout(new BorderLayout());
        setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Title displayed at the top of the address update page
        JLabel title = new JLabel("Update Shipping Address", SwingConstants.CENTER);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Text fields used to display and edit all parts of the shipping address
        JTextField street = HelperGUI.createThemeTextField();
        JTextField city = HelperGUI.createThemeTextField();
        JTextField state = HelperGUI.createThemeTextField();
        JTextField zip = HelperGUI.createThemeTextField();

        // Fields are filled with customer's existing address information
        street.setText(customer.getAddress().getStreetAddress());
        city.setText(customer.getAddress().getCity());
        state.setText(customer.getAddress().getState());
        zip.setText(customer.getAddress().getZipCode());

        // Box that contains all address fields
        JPanel addressFieldPanel = HelperGUI.createFieldBox(4);
        addressFieldPanel.add(HelperGUI.createFieldRow(HelperGUI.createFieldLabel("Street:"), street));
        addressFieldPanel.add(HelperGUI.createFieldRow(HelperGUI.createFieldLabel("City:"),   city));
        addressFieldPanel.add(HelperGUI.createFieldRow(HelperGUI.createFieldLabel("State:"),  state));
        addressFieldPanel.add(HelperGUI.createFieldRow(HelperGUI.createFieldLabel("ZIP Code:"), zip));

        // Centers address fields in window
        add(HelperGUI.centered(addressFieldPanel), BorderLayout.CENTER);

        // Buttons for saving updated address or canceling and returning to menu
        JButton saveBtn = HelperGUI.createThemeButton("Save");
        JButton cancelBtn = HelperGUI.createThemeButton("Cancel");

        // Checks that inputs are valid and saves updated customer address
        saveBtn.addActionListener(e -> {

            // Checks that all address fields have been filled out
            if (HelperGUI.empty(street.getText(), city.getText(), state.getText(), zip.getText())) {
                HelperGUI.error(parent, "Fields cannot be empty.");
                return;
            }

            // Updates address information in customer's account
            customer.getAddress().setStreetAddress(street.getText().trim());
            customer.getAddress().setCity(city.getText().trim());
            customer.getAddress().setState(state.getText().trim());
            customer.getAddress().setZipCode(zip.getText().trim());

            HelperGUI.information(parent, "Address updated successfully.");

            // Takes user back to checkout page after saving changes
            parent.showCheckoutPage(customer);
        });

        // Cancel button takes user back to checkout page without saving changes
        cancelBtn.addActionListener(e ->
            parent.showCheckoutPage(customer)
        );

        // Positions save and cancel buttons at bottom of window
        JPanel buttonRow = HelperGUI.createButtonRow(saveBtn, cancelBtn);
        add(buttonRow, BorderLayout.SOUTH);
    }
}