package org.example.view;

import org.example.controller.AccountController;
import org.example.controller.CustomerController;
import org.example.controller.PixKeyController;
import org.example.dto.AccountRequest;
import org.example.dto.AccountResponse;
import org.example.dto.CustomerResponse;
import org.example.enums.AccountType;
import org.example.view.ui.Dependencies;
import org.example.view.ui.MainFrame;
import org.example.view.ui.UiHelper;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CustomerDashboardView extends JPanel {

    public CustomerDashboardView(MainFrame mainFrame,
                                 CustomerController customerController, PixKeyController pixKeyController,
                                 Long customerId) {

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JPanel navPanel = UiHelper.createNavigationPanel(mainFrame, null);
        topPanel.add(navPanel, BorderLayout.NORTH);

        JLabel title = new JLabel("Área do Cliente", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(33, 37, 41));
        topPanel.add(title, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(248, 249, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        CustomerResponse customer = customerController.find(customerId);

        JPanel infoCard = new JPanel(new GridLayout(2, 2, 20, 10));
        infoCard.setBackground(Color.WHITE);
        infoCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        infoCard.add(createInfoLabel("Nome", customer != null ? customer.name() : ""));
        infoCard.add(createInfoLabel("Email", customer != null ? customer.email() : ""));
        infoCard.add(createInfoLabel("CPF", customer != null ? customer.document() : ""));
        infoCard.add(createInfoLabel("Telefone", customer != null ? customer.phone() : ""));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        centerPanel.add(infoCard, gbc);

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        actionsPanel.setOpaque(false);

        JButton manageAccountsBtn = UiHelper.createActionButton("Gerenciar Contas");
        JButton createAccountBtn = UiHelper.createActionButton("Criar Nova Conta");

        manageAccountsBtn.addActionListener(e -> {
            AccountController accountController = Dependencies.accountController();
            AccountManagementView view = new AccountManagementView(mainFrame, accountController, pixKeyController, customerId);
            mainFrame.addScreen(MainFrame.SCREEN_ACCOUNT_MANAGE, view);
            mainFrame.showScreen(MainFrame.SCREEN_ACCOUNT_MANAGE);
        });

        createAccountBtn.addActionListener(e -> {
            String[] types = {"CORRENTE", "POUPANCA"};
            String chosenType = (String) JOptionPane.showInputDialog(
                    this, "Tipo de conta:", "Nova conta",
                    JOptionPane.PLAIN_MESSAGE, null, types, types[0]
            );
            if (chosenType == null) return;

            String saldoStr = JOptionPane.showInputDialog(this, "Saldo inicial:");
            if (saldoStr == null) return;

            try {
                double saldo = Double.parseDouble(saldoStr);

                AccountRequest req;

                if(chosenType.equalsIgnoreCase("corrente")){
                    req = new AccountRequest(saldo, AccountType.CheckingAccount, customerId);
                } else {
                    req = new AccountRequest(saldo, AccountType.SavingsAccount, customerId);
                }

                AccountResponse resp = Dependencies.accountController().create(req);
                if (resp != null) {
                    JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor inválido");
            }
        });

        actionsPanel.add(manageAccountsBtn);
        actionsPanel.add(createAccountBtn);

        gbc.gridy = 1;
        centerPanel.add(actionsPanel, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createInfoLabel(String label, String value) {
        JPanel p = new JPanel(new BorderLayout(5, 0));
        p.setOpaque(false);
        JLabel l = new JLabel(label + ": ");
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(new Color(108, 117, 125));
        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        p.add(l, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);
        return p;
    }
}
