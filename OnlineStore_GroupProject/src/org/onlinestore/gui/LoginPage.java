package org.onlinestore.gui;

import javax.swing.*;
import java.awt.*;
import org.onlinestore.software.OnlineStore;
import org.onlinestore.people.Manager;
import org.onlinestore.people.Customer;

public class LoginPage extends JPanel {

    // Reference for main home page
    private MainHomePage parent;

    // Reference for OnlineStore system data
    private OnlineStore store;

    // Constructor that initializes the page with the main window and OnlineStore system data
    public LoginPage(MainHomePage parent, OnlineStore store) {
        this.parent = parent;   
        this.store = store;     
        createGUI();           
    }

    // Creates the entire login GUI
    private void createGUI() {

        setLayout(new BorderLayout());
        setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Main panel for login page
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        // Login page header
        JLabel title = new JLabel("Login", SwingConstants.CENTER);
        title.setFont(ThemeGUI.TITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(title);

        // Panel for login fields
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setOpaque(false);
        fieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        int fieldWidth = 260;
        Dimension fieldSize = new Dimension(fieldWidth, 34);

        // Username field
        JTextField usernameField = HelperFunctionsGUI.createThemeTextField();
        usernameField.setMaximumSize(fieldSize);

        fieldPanel.add(HelperFunctionsGUI.fieldLayout("Username", usernameField));
        fieldPanel.add(Box.createVerticalStrut(15));

        // Password field
        JPasswordField passwordField = HelperFunctionsGUI.createThemePasswordField();
        passwordField.setMaximumSize(fieldSize);

        fieldPanel.add(HelperFunctionsGUI.fieldLayout("Password", passwordField));
        fieldPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(fieldPanel);

        // Center panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(mainPanel);
        add(centerPanel, BorderLayout.CENTER);

        // Login and back buttons
        JButton loginBtn = HelperFunctionsGUI.createThemeButton("Sign In");
        JButton backBtn = HelperFunctionsGUI.createThemeButton("Back");
        JPanel buttonRow = HelperFunctionsGUI.createButtonRow(loginBtn, backBtn);
        add(buttonRow, BorderLayout.SOUTH);

        // Allows user to tab from username to password
        HelperFunctionsGUI.enterKeyFields(usernameField, passwordField);

        // Allows user to submit password with enter
        HelperFunctionsGUI.enterKeySubmit(passwordField, loginBtn);

        // Logic for when user selects sign in button
        loginBtn.addActionListener(e -> {

            String uName = usernameField.getText().trim();                 
            String uPass = new String(passwordField.getPassword()).trim(); 

            if (HelperFunctionsGUI.empty(uName, uPass)) {
                HelperFunctionsGUI.error(parent, "Please enter your username and password.");
                return;
            }

            var person = store.login(uName, uPass);   
            if (person == null) {
                HelperFunctionsGUI.error(parent, "Incorrect username or password.");
                return;
            }

            if (person instanceof Manager) {
                HelperFunctionsGUI.information(parent, "Manager Login Successful.");
                parent.showManagerHome((Manager) person);
            } 
            else {
                HelperFunctionsGUI.information(parent, "Customer Login Successful.");
                parent.showCustomerHome((Customer) person);
            }
        });

        // Back button
        backBtn.addActionListener(e -> parent.loadHomePage());
    }
}