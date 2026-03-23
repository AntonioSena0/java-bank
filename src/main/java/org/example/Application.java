package org.example;

import org.example.view.CustomerAuthView;
import org.example.view.ui.Dependencies;
import org.example.view.ui.MainFrame;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();

            CustomerAuthView authView =
                    new CustomerAuthView(frame, Dependencies.customerController(), Dependencies.pixKeyController());
            frame.addScreen(MainFrame.SCREEN_AUTH, authView);
            frame.showScreen(MainFrame.SCREEN_AUTH);

            frame.setVisible(true);
        });
    }
}
