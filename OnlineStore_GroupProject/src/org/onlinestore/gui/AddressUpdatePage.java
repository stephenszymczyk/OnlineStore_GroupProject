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
        this.parent = parent;       
        this.store = store;         
        this.customer = customer;   
        createGUI();                 
    }

    // Creates the entire Address Update Page GUI
    private void createGUI() {

        setLayout(new BorderLayout());
        setBackground(ThemeGUI.BACKGROUND_COLOR);

        // Title displayed at the top of the address update page
        JLabel title = new JLabel("Update Shipping Address", SwingConstants.CENTER);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Main panel layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Address field dimensions
        int fieldWidth = 285;
        Dimension fieldSize = new Dimension(fieldWidth, 34);

        // Themed address fields 
        JTextField street = HelperFunctionsGUI.createThemeTextField();
        JTextField city = HelperFunctionsGUI.createThemeTextField();
        JTextField state = HelperFunctionsGUI.createThemeTextField();
        JTextField zip = HelperFunctionsGUI.createThemeTextField();

        street.setText(customer.getAddress().getStreetAddress());
        city.setText(customer.getAddress().getCity());
        state.setText(customer.getAddress().getState());
        zip.setText(customer.getAddress().getZipCode());
        street.setMaximumSize(fieldSize);
        city.setMaximumSize(fieldSize);
        state.setMaximumSize(fieldSize);
        zip.setMaximumSize(fieldSize);

        // Panel for all address fields
        JPanel verticalFieldPanel = new JPanel();
        verticalFieldPanel.setLayout(new BoxLayout(verticalFieldPanel, BoxLayout.Y_AXIS));
        verticalFieldPanel.setOpaque(false);
        verticalFieldPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        verticalFieldPanel.add(HelperFunctionsGUI.fieldLayout("Street", street));
        verticalFieldPanel.add(Box.createVerticalStrut(10));
        verticalFieldPanel.add(HelperFunctionsGUI.fieldLayout("City", city));
        verticalFieldPanel.add(Box.createVerticalStrut(10));
        verticalFieldPanel.add(HelperFunctionsGUI.fieldLayout("State", state));
        verticalFieldPanel.add(Box.createVerticalStrut(10));
        verticalFieldPanel.add(HelperFunctionsGUI.fieldLayout("ZIP Code", zip));
        verticalFieldPanel.add(Box.createVerticalStrut(15));

        mainPanel.add(verticalFieldPanel);

        // Center fields on panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(mainPanel);

        // Enable panel scrolling
        JScrollPane scroll = new JScrollPane(centerPanel);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(34);

        add(scroll, BorderLayout.CENTER);

        // Buttons for saving updated address or canceling and returning to menu
        JButton saveBtn = HelperFunctionsGUI.createThemeButton("Save");
        JButton cancelBtn = HelperFunctionsGUI.createThemeButton("Cancel");

        // Save new address or display error message
        saveBtn.addActionListener(e -> {
            if (HelperFunctionsGUI.empty(street.getText(), city.getText(), state.getText(), zip.getText())) {
                HelperFunctionsGUI.error(parent, "Fields cannot be empty.");
                return;
            }

            customer.getAddress().setStreetAddress(street.getText().trim());
            customer.getAddress().setCity(city.getText().trim());
            customer.getAddress().setState(state.getText().trim());
            customer.getAddress().setZipCode(zip.getText().trim());

            HelperFunctionsGUI.information(parent, "Address updated successfully.");
            parent.showCheckoutPage(customer);
        });

        // Cancel returns user to checkout page
        cancelBtn.addActionListener(e -> parent.showCheckoutPage(customer));

        // Places buttons at bottom
        JPanel buttonRow = HelperFunctionsGUI.createButtonRow(saveBtn, cancelBtn);
        add(buttonRow, BorderLayout.SOUTH);
    }
}