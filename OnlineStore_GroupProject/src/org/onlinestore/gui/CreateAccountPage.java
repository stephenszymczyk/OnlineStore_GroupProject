package org.onlinestore.gui;

import javax.swing.*;
import java.awt.*;
import org.onlinestore.software.OnlineStore;
import org.onlinestore.software.Address;
import org.onlinestore.people.Customer;

public class CreateAccountPage extends JPanel {

    // Reference for main home page
    private MainHomePage parent;

    // Reference for OnlineStore system data
    private OnlineStore store;

    // Constructor that initializes the page with the main window and OnlineStore system data
    public CreateAccountPage(MainHomePage parent, OnlineStore store) {
        this.parent = parent;   // Represents MainHomePage window
        this.store = store;     // Represents instance of OnlineStore
        createGUI();            
    }

    // Creates the entire Create Account page GUI
    private void createGUI() {

        setLayout(new BorderLayout());
        setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Main panel with title
        JPanel mainPanel = HelperFunctionsGUI.createSectionBox("Create Account");

        // Input panel that holds all labeled fields
        JPanel inputFieldPanel = HelperFunctionsGUI.createInputSection();
        JTextField nameField = HelperFunctionsGUI.createThemeTextField();
        JTextField userField = HelperFunctionsGUI.createThemeTextField();
        JPasswordField passField = HelperFunctionsGUI.createThemePasswordField();
        JTextField streetField = HelperFunctionsGUI.createThemeTextField();
        JTextField cityField = HelperFunctionsGUI.createThemeTextField();
        JTextField stateField = HelperFunctionsGUI.createThemeTextField();
        JTextField zipField = HelperFunctionsGUI.createThemeTextField();

        // Uses all default field settings
        HelperFunctionsGUI.fieldSettings(nameField);
        HelperFunctionsGUI.fieldSettings(userField);
        HelperFunctionsGUI.fieldSettings(passField);
        HelperFunctionsGUI.fieldSettings(streetField);
        HelperFunctionsGUI.fieldSettings(cityField);
        HelperFunctionsGUI.fieldSettings(stateField);
        HelperFunctionsGUI.fieldSettings(zipField);

        // Add labeled fields to panel
        inputFieldPanel.add(HelperFunctionsGUI.fieldLayout("Name", nameField));
        inputFieldPanel.add(Box.createVerticalStrut(10));
        inputFieldPanel.add(HelperFunctionsGUI.fieldLayout("Username", userField));
        inputFieldPanel.add(Box.createVerticalStrut(10));
        inputFieldPanel.add(HelperFunctionsGUI.fieldLayout("Password", passField));
        inputFieldPanel.add(Box.createVerticalStrut(10));
        inputFieldPanel.add(HelperFunctionsGUI.fieldLayout("Street", streetField));
        inputFieldPanel.add(Box.createVerticalStrut(10));
        inputFieldPanel.add(HelperFunctionsGUI.fieldLayout("City", cityField));
        inputFieldPanel.add(Box.createVerticalStrut(10));
        inputFieldPanel.add(HelperFunctionsGUI.fieldLayout("State", stateField));
        inputFieldPanel.add(Box.createVerticalStrut(10));
        inputFieldPanel.add(HelperFunctionsGUI.fieldLayout("Zip", zipField));
        inputFieldPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(inputFieldPanel);

        // Center fields on panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(mainPanel);

        JScrollPane scroll = new JScrollPane(centerPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(35);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        add(scroll, BorderLayout.CENTER);

        // Buttons for create account and go back
        JButton createBtn = HelperFunctionsGUI.createThemeButton("Create Account");
        JButton backBtn = HelperFunctionsGUI.createThemeButton("Back");
        JPanel buttonRow = HelperFunctionsGUI.createButtonRow(createBtn, backBtn);
        add(buttonRow, BorderLayout.SOUTH);

        // Enables using enter key to move through fields
        HelperFunctionsGUI.enterKeyFields(
                nameField, userField, passField,
                streetField, cityField, stateField, zipField
        );

        // Enables using enter key to submit account information when final field is reached 
        HelperFunctionsGUI.enterKeySubmit(zipField, createBtn);

        // Logic used when Create Account button is selected
        createBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String street = streetField.getText().trim();
            String city = cityField.getText().trim();
            String state = stateField.getText().trim();
            String zip = zipField.getText().trim();

            // Checks to make sure no fields are empty
            if (HelperFunctionsGUI.empty(name, user, pass, street, city, state, zip)) {
                HelperFunctionsGUI.error(parent, "Please fill out all fields.");
                return;
            }

            // Creates new address object using user's input
            Address addr = new Address(street, city, state, zip);

            // OnlineStore creates new customer account
            Customer newCustomer = store.createAccount(name, user, pass, addr);

            // Error message if the username is unavailable
            if (newCustomer == null) {
                HelperFunctionsGUI.error(parent, "Username is unavailable.");
                return;
            }

            // Displays account creation confirmation message and returns to home page
            HelperFunctionsGUI.information(parent, "Account successfully created.");
            parent.loadHomePage();
        });

        // Returns user to the main home page if the back button is selected
        backBtn.addActionListener(e -> parent.loadHomePage());
    }
}