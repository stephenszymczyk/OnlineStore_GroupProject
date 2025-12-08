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

        // BorderLayout and apply theme background
        setLayout(new BorderLayout());
        setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Frame that contains all input fields for creating an account
        JPanel fieldBox = HelperGUI.createFieldBox(7);

        // Creates text fields using theme
        JTextField nameField = HelperGUI.createThemeTextField();
        JTextField userField = HelperGUI.createThemeTextField();
        JPasswordField passField = HelperGUI.createThemePasswordField();
        JTextField streetField = HelperGUI.createThemeTextField();
        JTextField cityField = HelperGUI.createThemeTextField();
        JTextField stateField = HelperGUI.createThemeTextField();
        JTextField zipField = HelperGUI.createThemeTextField();

        // Adds lables and input fields
        fieldBox.add(HelperGUI.createFieldRow(HelperGUI.createFieldLabel("Name:"), nameField));
        fieldBox.add(HelperGUI.createFieldRow(HelperGUI.createFieldLabel("Username:"), userField));
        fieldBox.add(HelperGUI.createFieldRow(HelperGUI.createFieldLabel("Password:"), passField));
        fieldBox.add(HelperGUI.createFieldRow(HelperGUI.createFieldLabel("Street:"), streetField));
        fieldBox.add(HelperGUI.createFieldRow(HelperGUI.createFieldLabel("City:"), cityField));
        fieldBox.add(HelperGUI.createFieldRow(HelperGUI.createFieldLabel("State:"), stateField));
        fieldBox.add(HelperGUI.createFieldRow(HelperGUI.createFieldLabel("Zip:"), zipField));

        // Centers all fields and adds title
        JPanel finalLayout = HelperGUI.centerPanelWithTitle("Create Account", fieldBox);
        add(finalLayout, BorderLayout.CENTER);

        // Initialize create account button and back button
        JButton createBtn = HelperGUI.createThemeButton("Create Account");
        JButton backBtn = HelperGUI.createThemeButton("Back");

        // Row for create account button and back button
        JPanel buttonRow = HelperGUI.createButtonRow(createBtn, backBtn);
        add(buttonRow, BorderLayout.SOUTH);

        // Allows user to use enter key to move through fields
        HelperGUI.enterKeyFields(
                nameField, userField, passField,
                streetField, cityField, stateField, zipField
        );

        // Allows user to submit account information using enter once they reach the final field
        HelperGUI.enterKeySubmit(zipField, createBtn);

        // Logic for when user selects Create Account button
        createBtn.addActionListener(e -> {

            // Gets user input from fields
            String name = nameField.getText().trim();
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            String street = streetField.getText().trim();
            String city = cityField.getText().trim();
            String state = stateField.getText().trim();
            String zip = zipField.getText().trim();

            // Checks to make sure no fields are empty
            if (HelperGUI.empty(name, user, pass, street, city, state, zip)) {
                HelperGUI.error(parent, "Please fill out all fields.");
                return;
            }

            // Creates an Address object from the entered address information
            Address addr = new Address(street, city, state, zip);

            // Requests OnlineStore to create a new customer account
            Customer newCustomer = store.createAccount(name, user, pass, addr);

            // Error message if the username is unavailable
            if (newCustomer == null) {
                HelperGUI.error(parent, "Username is unavailable.");
                return;
            }

            // Displays account creation confirmation message and returns to home page
            HelperGUI.information(parent, "Account successfully created.");
            parent.loadHomePage();
        });

        // Returns user to the main home page if the back button is selected
        backBtn.addActionListener(e -> parent.loadHomePage());
    }
}