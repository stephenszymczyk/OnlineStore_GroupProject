package org.onlinestore.gui;

import javax.swing.*;
import java.awt.*;

public class HelperGUI {

    // Displays error message
    public static void error(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "", JOptionPane.ERROR_MESSAGE);
    }

    // Displays information message
    public static void information(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "", JOptionPane.INFORMATION_MESSAGE);
    }

    // Displays warning message
    public static void warning(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "", JOptionPane.WARNING_MESSAGE);
    }

    // Checks for empty input boxes
    public static boolean empty(String... fields) {
        for (String f : fields) {
            if (f == null || f.trim().isEmpty()) return true;
        }
        return false;
    }

    // Checks if input is integer
    public static boolean integerCheck(String s) {
        try { Integer.parseInt(s.trim()); return true; }
        catch (Exception e) { return false; }
    }

    // Checks if input is positive integer
    public static boolean positiveIntegerCheck(String s) {
        return integerCheck(s) && Integer.parseInt(s.trim()) >= 0;
    }

    // Creates a theme text field
    public static JTextField createThemeTextField() {
        JTextField field = new JTextField(20);
        field.setBackground(ThemeGUI.PANEL_COLOR);
        field.setForeground(ThemeGUI.TEXT_MAIN);
        field.setCaretColor(ThemeGUI.TEXT_MAIN); // Sets color of blinking cursor inside text fields
        field.setBorder(BorderFactory.createLineBorder(ThemeGUI.BUTTON_OUTLINE));
        field.setFont(ThemeGUI.REGULAR_FONT);

        Dimension d = field.getPreferredSize();
        d.width = 200;
        field.setPreferredSize(d);

        return field;
    }

    // Creates a theme password field
    public static JPasswordField createThemePasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setBackground(ThemeGUI.PANEL_COLOR);
        field.setForeground(ThemeGUI.TEXT_MAIN);
        field.setCaretColor(ThemeGUI.TEXT_MAIN); // Sets color of blinking cursor inside password field
        field.setBorder(BorderFactory.createLineBorder(ThemeGUI.BUTTON_OUTLINE));
        field.setFont(ThemeGUI.REGULAR_FONT);

        Dimension d = field.getPreferredSize();
        d.width = 200;
        field.setPreferredSize(d);

        return field;
    }

    // Creates a theme label for fields
    public static JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(ThemeGUI.REGULAR_FONT);
        label.setForeground(ThemeGUI.TEXT_MAIN);
        return label;
    }

    // Creates a row that has label and field
    public static JPanel createFieldRow(JComponent label, JComponent field) {
        JPanel row = new JPanel(new GridLayout(1, 2, 10, 0));
        row.setOpaque(false);
        row.add(label);
        row.add(field);
        return row;
    }

    // Creates a theme box to frame labels and fields
    public static JPanel createFieldBox(int rows) {
        JPanel box = new JPanel();
        box.setBackground(ThemeGUI.PANEL_COLOR);
        box.setBorder(BorderFactory.createLineBorder(ThemeGUI.OUTLINE_COLOR));
        box.setLayout(new GridLayout(rows, 1, 12, 12));
        box.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return box;
    }

    // Creates a center panel with title
    public static JPanel centerPanelWithTitle(String titleText, JPanel formBox) {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(ThemeGUI.BACKGROUND_COLOR);

        JLabel title = new JLabel(titleText, SwingConstants.CENTER);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

        centerPanel.add(title, BorderLayout.NORTH);
        centerPanel.add(centered(formBox), BorderLayout.CENTER);

        return centerPanel;
    }

    // Creates a row of buttons
    public static JPanel createButtonRow(JButton... buttons) {
        JPanel row = new JPanel(new FlowLayout());
        row.setOpaque(false);

        for (JButton b : buttons)
            row.add(b);

        return row;
    }

    // Centers panel using GridBagLayout
    public static JPanel centered(JComponent panel) {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(ThemeGUI.BACKGROUND_COLOR);
        centerPanel.add(panel);
        return centerPanel;
    }

    // Allows user to move through fields using enter key
    public static void enterKeyFields(JComponent... fields) {
        for (int i = 0; i < fields.length - 1; i++) {
            JComponent current = fields[i];
            JComponent next = fields[i + 1];

            current.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent e) {
                    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                        next.requestFocusInWindow();
                    }
                }
            });
        }
    }

    // Allows user to submit input using enter key
    public static void enterKeySubmit(JComponent lastField, JButton button) {
        lastField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    button.doClick();
                }
            }
        });
    }

    // Creates a theme button that changes hue when user hovers cursor over it
    public static JButton createThemeButton(String text) {
        JButton button = new JButton(text);

        button.setFont(ThemeGUI.BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBackground(ThemeGUI.BUTTON_COLOR);
        button.setForeground(ThemeGUI.TEXT_MAIN);
        button.setBorder(BorderFactory.createLineBorder(ThemeGUI.BUTTON_OUTLINE));
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            // Mouse hovering
        	public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ThemeGUI.BUTTON_HOVER);
            }
        	// Mouse removed
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(ThemeGUI.BUTTON_COLOR);
            }
        });

        return button;
    }
    
    
}