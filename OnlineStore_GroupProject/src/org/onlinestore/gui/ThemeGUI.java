package org.onlinestore.gui;

import java.awt.*;

public class ThemeGUI {

    // Color settings for text, panels, and backgrounds
    public static final Color BACKGROUND_COLOR = new Color(30, 30, 30);            // Main window background color used for all pages
    public static final Color PANEL_COLOR = new Color(45, 45, 45);      		   // Lighter color used for inside panels
    public static final Color HEADER_COLOR = new Color(55, 55, 55);      		   // Color used for headers
    public static final Color OUTLINE_COLOR = new Color(80, 80, 80);               // Outline color for boxes

    public static final Color BUTTON_COLOR = new Color(60, 60, 60);                // Button color when mouse isn't hovering
    public static final Color BUTTON_HOVER = new Color(85, 85, 85);                // Button color when mouse hovers
    public static final Color BUTTON_OUTLINE = new Color(110, 110, 110);           // Button outline color

    public static final Color TEXT_MAIN = new Color(230, 230, 230);                // Main text color used for text

    // Customizable title fonts but currently defaulted to Segoe for all text 
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);     // Font used for large screen titles or main page headings
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);  // Font for smaller headers or section labels inside pages
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);    // Font used for button text to maintain readability
    public static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 13);  // Default font for general text such as form labels and descriptions

    // Box dimensions 
    public static final Dimension SMALL_BOX = new Dimension(360, 220);             // Regular size box used for most pages
    public static final Dimension LARGE_BOX = new Dimension(400, 260);             // Larger box used when page needs more space
} 