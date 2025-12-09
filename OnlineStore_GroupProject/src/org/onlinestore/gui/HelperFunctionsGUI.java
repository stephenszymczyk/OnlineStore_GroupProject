package org.onlinestore.gui;

import javax.swing.*;
import java.awt.*;

public class HelperFunctionsGUI {

    // Displays error message
    public static void error(Component popUp, String exceptionMessage) {
        JOptionPane.showMessageDialog(popUp, exceptionMessage, "", JOptionPane.ERROR_MESSAGE);
    }

    // Displays information message
    public static void information(Component popUp, String exceptionMessage) {
        JOptionPane.showMessageDialog(popUp, exceptionMessage, "", JOptionPane.INFORMATION_MESSAGE);
    }

    // Displays warning message
    public static void warning(Component popUp, String exceptionMessage) {
        JOptionPane.showMessageDialog(popUp, exceptionMessage, "", JOptionPane.WARNING_MESSAGE);
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

    // Inner class to create consistent fields for user input
    public static JTextField createThemeTextField() {

        JTextField field = new JTextField(20) {
            private boolean fieldSelected = false;
            private final int fieldCorners = 16;

            {
                setOpaque(false);
                setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                
            	// Keyboard focusing for user input fields
                addFocusListener(new java.awt.event.FocusAdapter() {
                    
                	@Override 
                    public void focusGained(java.awt.event.FocusEvent e) 
                    	{ fieldSelected = true; repaint(); }
                    @Override 
                    public void focusLost(java.awt.event.FocusEvent e) 
                    	{ fieldSelected = false; repaint(); }
                });
            }

            @Override
            protected void paintComponent(Graphics graphic) {
                Graphics2D graphic2D = (Graphics2D) graphic.create();
                graphic2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // edge smoothing
                graphic2D.setColor(ThemeGUI.FIELD_BACKGROUND);
                graphic2D.fillRoundRect(0, 0, getWidth(), getHeight(), fieldCorners, fieldCorners);
                super.paintComponent(graphic2D);
                graphic2D.dispose();
            }

            @Override
            protected void paintBorder(Graphics graphic) {
                Graphics2D graphic2D = (Graphics2D) graphic.create();
                graphic2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphic2D.setColor(fieldSelected ? ThemeGUI.FIELD_BORDER_SELECT : ThemeGUI.FIELD_BORDER);        // border highlight color for fields
                graphic2D.setStroke(new BasicStroke(2f));
                graphic2D.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, fieldCorners, fieldCorners);

                graphic2D.dispose();
            }
        };

        field.setForeground(ThemeGUI.TEXT_MAIN);
        field.setCaretColor(ThemeGUI.TEXT_MAIN);
        field.setFont(ThemeGUI.REGULAR_FONT);

        return field;
    }

    // Themed password field
    public static JPasswordField createThemePasswordField() {

        JPasswordField field = new JPasswordField(20) {

            private boolean fieldSelected = false;
            private final int fieldCorners = 16;

            {
                setOpaque(false);
                setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
                
                // Keyboard focusing for password input
                addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) {
                        fieldSelected = true;
                        repaint();
                    }
                    @Override
                    public void focusLost(java.awt.event.FocusEvent e) {
                        fieldSelected = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics graphic) {
                Graphics2D graphic2D = (Graphics2D) graphic.create();
                graphic2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // smooth edges for password field
                graphic2D.setColor(ThemeGUI.FIELD_BACKGROUND);
                graphic2D.fillRoundRect(0, 0, getWidth(), getHeight(), fieldCorners, fieldCorners);

                super.paintComponent(graphic2D);
                graphic2D.dispose();
            }
            @Override
            protected void paintBorder(Graphics graphic) {
                Graphics2D graphic2D = (Graphics2D) graphic.create();
                graphic2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphic2D.setColor(fieldSelected ? ThemeGUI.FIELD_BORDER_SELECT : ThemeGUI.FIELD_BORDER); // border color for password field
                graphic2D.setStroke(new BasicStroke(2f));
                graphic2D.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, fieldCorners, fieldCorners);

                graphic2D.dispose();
            }
        };
        field.setCaretColor(ThemeGUI.TEXT_MAIN);
        field.setForeground(ThemeGUI.TEXT_MAIN);
        field.setFont(ThemeGUI.REGULAR_FONT);

        return field;
    }

    // Themed lables for fields
    public static JLabel createLabelForField(String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(ThemeGUI.LABEL_FONT);
        label.setForeground(ThemeGUI.TEXT_MAIN);
        return label;
    }

    // Page layout with the label above the field
    public static JPanel fieldLayout(String labelText, JComponent field) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setOpaque(false);
        fieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel fieldLabel = createLabelForField(labelText);
        fieldLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        fieldPanel.add(fieldLabel);
        fieldPanel.add(field);

        return fieldPanel;
    }

    public static JPanel middlePanelWithTitle(String titleText, JPanel fieldBox) {
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(ThemeGUI.BACKGROUND_COLOR);
        
        JLabel panelTitle = new JLabel(titleText, SwingConstants.CENTER);
        panelTitle.setFont(ThemeGUI.SUBTITLE_FONT);
        panelTitle.setForeground(ThemeGUI.TEXT_MAIN);
        panelTitle.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));

        middlePanel.add(panelTitle, BorderLayout.NORTH);
        middlePanel.add(centerThePanel(fieldBox), BorderLayout.CENTER);

        return middlePanel;
    }

    // Puts buttons in a row
    public static JPanel createButtonRow(JButton... buttons) {
        JPanel row = new JPanel(new FlowLayout());
        row.setOpaque(false);

        for (JButton b : buttons)
            row.add(b);

        return row;
    }

    // Centers panel
    public static JPanel centerThePanel(JComponent panel) {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(ThemeGUI.BACKGROUND_COLOR);
        centerPanel.add(panel);
        return centerPanel;
    }

    // Enable moving through fields using enter key
    public static void enterKeyFields(JComponent... inputFields) {
        for (int i = 0; i < inputFields.length - 1; i++) {
            JComponent current = inputFields[i];
            JComponent next = inputFields[i + 1];

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

    // Enable submitting input using enter key when user is on the last field
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

    // Creates a theme button that highlights on hover
    public static JButton createThemeButton(String buttonText) {

        JButton smoothedButton = new JButton(buttonText) {

            private boolean cursorOnButton = false;
            private final int buttonCorners = 16;

            @Override
            protected void paintComponent(Graphics graphic) {
                Graphics2D graphics2D = (Graphics2D) graphic.create();
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics2D.setColor(ThemeGUI.BUTTON_COLOR);
                graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), buttonCorners, buttonCorners);

                if (cursorOnButton) {
                    graphics2D.setColor(ThemeGUI.FIELD_BORDER_SELECT); // buttons use same border as field
                    graphics2D.setStroke(new BasicStroke(2f));
                    graphics2D.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, buttonCorners, buttonCorners);
                }
                super.paintComponent(graphics2D);
                graphics2D.dispose();
            }
            
            
            // Mouse detection when user hovers cursor over button
            @Override
            protected void processMouseEvent(java.awt.event.MouseEvent e) {
                if (e.getID() == java.awt.event.MouseEvent.MOUSE_ENTERED) {
                    cursorOnButton = true;
                    repaint();
                } 
                else if (e.getID() == java.awt.event.MouseEvent.MOUSE_EXITED) {
                    cursorOnButton = false;
                    repaint();
                }
                super.processMouseEvent(e);
            }
        };
        
        // Defeat regular swing settings and implement theme
        smoothedButton.setBorderPainted(false);
        smoothedButton.setContentAreaFilled(false);
        smoothedButton.setFocusPainted(false);
        smoothedButton.setOpaque(false);
        smoothedButton.setFont(ThemeGUI.BUTTON_FONT);
        smoothedButton.setForeground(ThemeGUI.TEXT_MAIN);
        smoothedButton.setBackground(ThemeGUI.BUTTON_COLOR);
        smoothedButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        return smoothedButton;
    }
    
    // Group related input sections on the page
    public static JPanel createSectionBox(String titleText) {
        JPanel sectionBox = new JPanel();
        sectionBox.setLayout(new BoxLayout(sectionBox, BoxLayout.Y_AXIS));
        sectionBox.setOpaque(false);

        JLabel title = new JLabel(titleText);
        title.setFont(ThemeGUI.SUBTITLE_FONT);
        title.setForeground(ThemeGUI.TEXT_MAIN);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        sectionBox.add(title);

        return sectionBox;
    }
    
    // Vertical box for input fields
    public static JPanel createInputSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }
    
    // Default input field settings
    public static void fieldSettings(JComponent field) {
        int defaultFieldWidth = 285;
        int defaultFieldHeight = 35;
        Dimension fieldDimension = new Dimension(defaultFieldWidth, defaultFieldHeight);
        field.setMaximumSize(fieldDimension);
        field.setPreferredSize(fieldDimension);
        field.setMinimumSize(fieldDimension);
    }
}