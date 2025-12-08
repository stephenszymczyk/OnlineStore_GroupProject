package org.onlinestore.gui;

import javax.swing.*;
import java.awt.*;
import org.onlinestore.software.OnlineStore;
import org.onlinestore.people.Manager;
import org.onlinestore.people.Customer;
import org.onlinestore.people.Person;

public class LoginPage extends JPanel {

	// Reference for main home page
    private MainHomePage parent;

    // Reference for OnlineStore system data
    private OnlineStore store;

    // Constructor that initializes the page with the main window and OnlineStore system data
    public LoginPage(MainHomePage parent, OnlineStore store) {
        this.parent = parent;   // Represents MainHomePage window
        this.store = store;     // Represents instance of OnlineStore
        createGUI();           
    }

    // Creates the entire login GUI
    private void createGUI() {

        // BorderLayout and apply theme background
        setLayout(new BorderLayout());
        setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Frame that contains all the login fields and buttons
        JPanel box = new JPanel();
        box.setPreferredSize(ThemeGUI.LARGE_BOX);           
        box.setBackground(ThemeGUI.PANEL_COLOR);
        box.setBorder(BorderFactory.createLineBorder(ThemeGUI.OUTLINE_COLOR));
        box.setLayout(new GridBagLayout());               

        // Constraints for arranging everything inside frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;

        // Title located at top of login box
        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        box.add(title, gbc);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Label for username field
        JLabel usernameLabel = HelperGUI.createFieldLabel("Username:");

        // Input box for username field 
        JTextField usernameField = HelperGUI.createThemeTextField();
        usernameField.setPreferredSize(new Dimension(180, 28)); 
        
        // Adds username label
        gbc.gridx = 0;
        gbc.gridy = 1;
        box.add(usernameLabel, gbc);

        // Adds username input field
        gbc.gridx = 1;
        box.add(usernameField, gbc);

        // Label for password field
        JLabel passwordLabel = HelperGUI.createFieldLabel("Password:");

        // Input box for password field
        JPasswordField passwordField = HelperGUI.createThemePasswordField();
        passwordField.setPreferredSize(new Dimension(180, 28)); 
        
        // Adds password label
        gbc.gridx = 0;
        gbc.gridy = 2;
        box.add(passwordLabel, gbc);

        // Adds password field
        gbc.gridx = 1;
        box.add(passwordField, gbc);

        // Creates login and back buttons
        JButton loginBtn = HelperGUI.createThemeButton("Sign In");
        JButton backBtn = HelperGUI.createThemeButton("Back");

        // Row that contains login and back buttons
        JPanel buttonRow = new JPanel(new FlowLayout());
        buttonRow.setOpaque(false);
        buttonRow.add(loginBtn);
        buttonRow.add(backBtn);

        // Adds row for login and back buttons
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        box.add(buttonRow, gbc);

        // Centers the login box in the page
        JPanel centerPanel = HelperGUI.centered(box);
        add(centerPanel, BorderLayout.CENTER);

        // Allows user to tab to password field from username field
        HelperGUI.enterKeyFields(usernameField, passwordField);

        // Allows user to submit password using enter key
        HelperGUI.enterKeySubmit(passwordField, loginBtn);

        // Logic for when user selects sign in button
        loginBtn.addActionListener(e -> {

            // Gets users input
            String u = usernameField.getText().trim();                 // u = entered username
            String p = new String(passwordField.getPassword()).trim(); // p = entered password

            // Checks to make sure fields are not empty
            if (HelperGUI.empty(u, p)) {
                HelperGUI.error(parent, "Please enter your username and password.");
                return;
            }

            // Gets user from account from OnlineStore
            var person = store.login(u, p);   

            // Error message if user enters invalid account credentials
            if (person == null) {
                HelperGUI.error(parent, "Incorrect username or password.");
                return;
            }

            // If credentials are valid checks whether user is a manager or a customer
            if (person instanceof Manager) {
                HelperGUI.information(parent, "Manager Login Successful.");
                parent.showManagerHome((Manager) person);
            } 
            else {
                HelperGUI.information(parent, "Customer Login Successful.");
                parent.showCustomerHome((Customer) person);
            }
        });

        // Returns user to main home page if back button is selected
        backBtn.addActionListener(e -> parent.loadHomePage());
    }
}