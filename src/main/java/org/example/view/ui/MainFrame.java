package org.example.view.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static final String SCREEN_AUTH = "auth";
    public static final String SCREEN_CUSTOMER_DASH = "customerDash";
    public static final String SCREEN_ACCOUNT_MANAGE = "accountManage";
    public static final String SCREEN_PIX_MANAGE = "pixManage";
    public static final String SCREEN_ADMIN_DASH = "adminDash";

    private CardLayout cardLayout;
    private JPanel rootPanel;

    public MainFrame() {
        setTitle("Java-Bank");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        rootPanel = new JPanel(cardLayout);

        setContentPane(rootPanel);
    }

    public void addScreen(String name, JPanel panel) {
        rootPanel.add(panel, name);
    }

    public void showScreen(String name) {
        cardLayout.show(rootPanel, name);
    }
}