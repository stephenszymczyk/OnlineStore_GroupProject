package org.onlinestore.gui;

import javax.swing.*;
import java.awt.*;
import org.onlinestore.software.OnlineStore;
import org.onlinestore.people.Manager;   // Manager user type
import org.onlinestore.people.Customer;  // Customer user type

public class MainHomePage extends JFrame {

    // OnlineStore object, contains inventory and users
    private OnlineStore store;

    // Constructor to create window and load the online store
    public MainHomePage(OnlineStore store) {
        this.store = store;

        setTitle("Online Store");                        // Main window title
        setSize(600, 500);                               // Main window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close window
        setLocationRelativeTo(null);                     // Centers window on users screen

        // Automatically saves data when user exits window
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                store.saveOnlineStore();                 // Saves data for entire system
            }
        });

        createGUI();   
        setVisible(true); 
    }


    // Creates main menu for Online Store GUI
    private void createGUI() {

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Welcome banner at top of main menu
        JLabel banner = new JLabel("Welcome to the Online Store", SwingConstants.CENTER);
        banner.setFont(ThemeGUI.TITLE_FONT);
        banner.setForeground(ThemeGUI.TEXT_MAIN);
        banner.setOpaque(true);
        banner.setBackground(ThemeGUI.HEADER_COLOR);
        banner.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Centers frame for login and create account buttons
        JPanel box = new JPanel();
        box.setPreferredSize(ThemeGUI.SMALL_BOX);
        box.setBackground(ThemeGUI.PANEL_COLOR);
        box.setBorder(BorderFactory.createLineBorder(ThemeGUI.OUTLINE_COLOR));
        box.setLayout(new GridLayout(2, 1, 12, 12));
        box.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Login button
        JButton loginBtn = HelperGUI.createThemeButton("Login");

        // Create account button
        JButton createAccountBtn = HelperGUI.createThemeButton("Create Account");

        // Goes to login page when login button is clicked
        loginBtn.addActionListener(e -> openLoginPage());

        // Goes to create account page when login button is clicked
        createAccountBtn.addActionListener(e -> openCreateAccountPage());

        // Adds login and create account buttons to center frame
        box.add(loginBtn);
        box.add(createAccountBtn);

        // Centers frame in window
        JPanel center = HelperGUI.centered(box);

        // Adds banner and main menu box to frame
        mainPanel.add(banner, BorderLayout.NORTH);
        mainPanel.add(center, BorderLayout.CENTER);

        // Set the window's display to the main panel
        setContentPane(mainPanel);
        revalidate();
        repaint();
    }


    // Open login page
    private void openLoginPage() {
        setContentPane(new LoginPage(this, store)); // this = MainHomePage frame
        revalidate();
        repaint();
    }

    // Open create account page
    private void openCreateAccountPage() {
        setContentPane(new CreateAccountPage(this, store));
        revalidate();
        repaint();
    }
    
    // Reload main home page
    public void loadHomePage() {
        createGUI();
        revalidate();
        repaint();
    }
    
    // Displays manager home page
    public void showManagerHome(Manager manager) {
        setContentPane(new ManagerHomePage(this, store, manager));
        revalidate();
        repaint();
    }
    
    // Displays inventory page
    public void showManagerInventoryPage(Manager manager) {
        setContentPane(new ManagerInventoryPage(this, store, manager));
        revalidate();
        repaint();
    }

    // Displays customer home page
    public void showCustomerHome(Customer customer) {
        setContentPane(new CustomerHomePage(this, store, customer));
        revalidate();
        repaint();
    }
    
    //Displays customer's inventory page
    public void showCustomerInventoryPage(Customer customer) {
        setContentPane(new CustomerInventoryPage(this, store, customer));
        revalidate();
        repaint();
    }
    
    // Displays checkout page
    public void showCheckoutPage(Customer customer) {
        setContentPane(new CheckoutPage(this, store, customer));
        revalidate();
        repaint();
    }
    
    // Displays address update page
    public void showAddressUpdatePage(Customer customer) {
        setContentPane(new AddressUpdatePage(this, store, customer));
        revalidate();
        repaint();
    } 
}