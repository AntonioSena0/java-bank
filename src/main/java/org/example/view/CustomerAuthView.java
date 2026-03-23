package org.example.view;

import org.example.controller.CustomerController;
import org.example.controller.PixKeyController;
import org.example.dto.CustomerLoginRequest;
import org.example.dto.CustomerRequest;
import org.example.dto.CustomerResponse;
import org.example.enums.UserType;
import org.example.view.ui.MainFrame;
import org.example.view.ui.UiHelper;

import javax.swing.*;
import java.awt.*;

public class CustomerAuthView extends JPanel {

    public static String formatarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static String formatarTelefone(String tel) {
        tel = tel.replaceAll("[^0-9]", "");
        if (tel.length() == 11) {
            return tel.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        } else if (tel.length() == 10) {
            return tel.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        }
        return tel;
    }

    public CustomerAuthView(MainFrame mainFrame, CustomerController customerController, PixKeyController pixKeyController) {

        customerController.createAdminIfNotExists();

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("Java-Bank", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(33, 150, 243));
        add(title, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel loginPanel = createLoginPanel(mainFrame, customerController, pixKeyController);
        JPanel registerPanel = createRegisterPanel(mainFrame, customerController, pixKeyController);

        tabs.addTab("Login", loginPanel);
        tabs.addTab("Registrar", registerPanel);

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel createLoginPanel(MainFrame mainFrame, CustomerController customerController, PixKeyController pixKeyController) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginBtn = UiHelper.createActionButton("Entrar");

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String senha = new String(passwordField.getPassword());
            Long customerId = customerController.login(new CustomerLoginRequest(email, senha));
            
            if (customerId != null) {
                CustomerResponse customer = customerController.find(customerId);
                if (customer != null) {
                    if (customer.role() == UserType.ADMIN) {
                        AdminDashboardView adminDash = new AdminDashboardView(mainFrame);
                        mainFrame.addScreen(MainFrame.SCREEN_ADMIN_DASH, adminDash);
                        mainFrame.showScreen(MainFrame.SCREEN_ADMIN_DASH);
                    } else {
                        CustomerDashboardView dash = new CustomerDashboardView(mainFrame, customerController, pixKeyController, customerId);
                        mainFrame.addScreen(MainFrame.SCREEN_CUSTOMER_DASH, dash);
                        mainFrame.showScreen(MainFrame.SCREEN_CUSTOMER_DASH);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login inválido");
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(loginBtn, gbc);

        return panel;
    }

    private JPanel createRegisterPanel(MainFrame mainFrame, CustomerController customerController, PixKeyController pixKeyController) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField cpfField = new JTextField(20);
        JTextField phoneField = new JTextField(20);

        JButton registerBtn = UiHelper.createActionButton("Registrar Cliente");

        registerBtn.addActionListener(e -> {
            CustomerRequest req = new CustomerRequest(
                    nameField.getText(),
                    emailField.getText(),
                    new String(passwordField.getPassword()),
                    formatarCPF(cpfField.getText()),
                    formatarTelefone(phoneField.getText()),
                    UserType.CLIENT
            );
            if (customerController.create(req) != null) {
                JOptionPane.showMessageDialog(this, "Cliente criado com sucesso");

                Long customerId = customerController.login(new CustomerLoginRequest(req.email(), req.password()));

                CustomerDashboardView dash = new CustomerDashboardView(mainFrame, customerController, pixKeyController, customerId);
                mainFrame.addScreen(MainFrame.SCREEN_CUSTOMER_DASH, dash);
                mainFrame.showScreen(MainFrame.SCREEN_CUSTOMER_DASH);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao criar cliente");
            }
        });

        String[] labels = {"Nome:", "Email:", "Senha:", "CPF:", "Telefone:"};
        JComponent[] fields = {nameField, emailField, passwordField, cpfField, phoneField};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.gridwidth = 1;
            panel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            panel.add(fields[i], gbc);
        }

        gbc.gridx = 0; gbc.gridy = labels.length; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(registerBtn, gbc);

        return panel;
    }
}
