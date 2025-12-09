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

        setTitle("Campus Store");                        // Main window title
        setSize(600, 500);                               // Main window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close window
        setLocationRelativeTo(null);                     // Centers window on users screen
        setBackground(ThemeGUI.BACKGROUND_COLOR);                     
        getContentPane().setBackground(ThemeGUI.BACKGROUND_COLOR);   
        getRootPane().setBackground(ThemeGUI.BACKGROUND_COLOR);       
        getLayeredPane().setBackground(ThemeGUI.BACKGROUND_COLOR);    

        ((JPanel) getContentPane()).setOpaque(true);                

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

        // Black main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(ThemeGUI.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Title at top of page
        JLabel textBanner = new JLabel("Welcome to the Campus Store");
        textBanner.setFont(ThemeGUI.TITLE_FONT);
        textBanner.setForeground(ThemeGUI.TEXT_MAIN);
        textBanner.setAlignmentX(Component.CENTER_ALIGNMENT);
        textBanner.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Online store icon
        ImageIcon bannerIcon = new ImageIcon(getClass().getResource("/UAlogo.png"));
        Image imageSizing = bannerIcon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
        bannerIcon = new ImageIcon(imageSizing);

        JLabel mainImage = new JLabel(bannerIcon);
        mainImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainImage.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Button frame for main panel options
        JPanel box = new JPanel();
        box.setLayout(new GridLayout(2, 1, 12, 12));
        box.setPreferredSize(new Dimension(250, 120));
        box.setOpaque(false); 

        JButton loginBtn = HelperFunctionsGUI.createThemeButton("Login");
        JButton createAccountBtn = HelperFunctionsGUI.createThemeButton("Create Account");

        box.add(loginBtn);
        box.add(createAccountBtn);

        // Center the button frame
        JPanel buttonCenter = new JPanel();
        buttonCenter.setOpaque(false);
        buttonCenter.add(box);
        buttonCenter.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adds everything to main panel
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(textBanner);
        mainPanel.add(mainImage);
        mainPanel.add(buttonCenter);
        mainPanel.add(Box.createVerticalGlue());

        setContentPane(mainPanel);
        revalidate();
        repaint();

        // Button actions
        loginBtn.addActionListener(e -> openLoginPage());
        createAccountBtn.addActionListener(e -> openCreateAccountPage());
    }


    // Open login page
    private void openLoginPage() {
        setContentPane(new LoginPage(this, store));
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