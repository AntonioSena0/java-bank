package org.example.view;

import org.example.controller.AccountController;
import org.example.controller.PixKeyController;
import org.example.controller.TransactionController;
import org.example.dto.AccountResponse;
import org.example.enums.AccountType;
import org.example.view.ui.Dependencies;
import org.example.view.ui.MainFrame;
import org.example.view.ui.UiHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AccountManagementView extends JPanel {

    public AccountManagementView(MainFrame mainFrame,
                                 AccountController accountController, PixKeyController pixKeyController,
                                 Long customerId) {

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JPanel navPanel = UiHelper.createNavigationPanel(mainFrame, MainFrame.SCREEN_CUSTOMER_DASH);
        topPanel.add(navPanel, BorderLayout.NORTH);

        JLabel title = new JLabel("Minhas Contas", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(33, 37, 41));
        topPanel.add(title, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);

        List<AccountResponse> accounts = accountController.findByCustomerId(customerId);
        DefaultListModel<AccountResponse> model = new DefaultListModel<>();
        for (AccountResponse acc : accounts) {
            model.addElement(acc);
        }

        JList<AccountResponse> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer((jList, value, index, isSelected, cellHasFocus) -> {
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            panel.setBackground(isSelected ? new Color(231, 241, 251) : Color.WHITE);
            
            JLabel idLabel = new JLabel("ID: " + value.id() + " | " + (value.type() == AccountType.CheckingAccount ? "Conta Corrente" : "Conta Poupança"));
            idLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            
            JLabel balanceLabel = new JLabel(String.format("Saldo: R$ %.2f", value.balance()));
            balanceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            balanceLabel.setForeground(new Color(40, 167, 69));

            panel.add(idLabel, BorderLayout.WEST);
            panel.add(balanceLabel, BorderLayout.EAST);
            
            if (isSelected) {
                panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(33, 150, 243), 1),
                    BorderFactory.createEmptyBorder(9, 14, 9, 14)
                ));
            } else {
                panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                    BorderFactory.createEmptyBorder(9, 14, 9, 14)
                ));
            }
            return panel;
        });

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(248, 249, 250));
        add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.setOpaque(false);

        JButton depositBtn = UiHelper.createActionButton("Depositar");
        JButton withdrawBtn = UiHelper.createActionButton("Sacar");
        JButton transferBtn = UiHelper.createActionButton("Transferir");
        JButton pixBtn = UiHelper.createActionButton("PIX");
        JButton txBtn = UiHelper.createSecondaryButton("Extrato");

        TransactionController txController = Dependencies.transactionController();

        depositBtn.addActionListener(e -> {
            AccountResponse selected = list.getSelectedValue();
            if (selected == null) { JOptionPane.showMessageDialog(this, "Selecione uma conta"); return; }
            String val = JOptionPane.showInputDialog(this, "Valor do depósito:");
            if (val != null) {
                try {
                    txController.deposit(selected.id(), Double.parseDouble(val));
                    refreshList(accountController, customerId, model);
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
            }
        });

        withdrawBtn.addActionListener(e -> {
            AccountResponse selected = list.getSelectedValue();
            if (selected == null) { JOptionPane.showMessageDialog(this, "Selecione uma conta"); return; }
            String val = JOptionPane.showInputDialog(this, "Valor do saque:");
            if (val != null) {
                try {
                    txController.withdraw(selected.id(), Double.parseDouble(val));
                    refreshList(accountController, customerId, model);
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
            }
        });

        transferBtn.addActionListener(e -> {
            AccountResponse selected = list.getSelectedValue();
            if (selected == null) { JOptionPane.showMessageDialog(this, "Selecione uma conta"); return; }
            String key = JOptionPane.showInputDialog(this, "Chave da conta destino (Digite exatamente igual):");
            String val = JOptionPane.showInputDialog(this, "Valor da transferência:");
            if (key != null && val != null) {
                try {
                    txController.transfer(selected.id(), pixKeyController.findByPixKey(key).id(), Double.parseDouble(val));
                    refreshList(accountController, customerId, model);
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Chave inválida"); }
            }
        });

        pixBtn.addActionListener(e -> {
            AccountResponse selected = list.getSelectedValue();
            if (selected == null) { JOptionPane.showMessageDialog(this, "Selecione uma conta"); return; }
            PixKeyController pixController = Dependencies.pixKeyController();
            PixManagementView pixView = new PixManagementView(mainFrame, pixController, selected.id());
            mainFrame.addScreen(MainFrame.SCREEN_PIX_MANAGE, pixView);
            mainFrame.showScreen(MainFrame.SCREEN_PIX_MANAGE);
        });

        txBtn.addActionListener(e -> {
            AccountResponse selected = list.getSelectedValue();
            if (selected == null) { JOptionPane.showMessageDialog(this, "Selecione uma conta"); return; }
            AccountTransactionView v = new AccountTransactionView(txController, selected.id());
            v.showDialog(this);
        });

        btnPanel.add(depositBtn);
        btnPanel.add(withdrawBtn);
        btnPanel.add(transferBtn);
        btnPanel.add(pixBtn);
        btnPanel.add(txBtn);

        add(btnPanel, BorderLayout.SOUTH);
    }

    private void refreshList(AccountController controller, Long customerId, DefaultListModel<AccountResponse> model) {
        model.clear();
        List<AccountResponse> accounts = controller.findByCustomerId(customerId);
        for (AccountResponse acc : accounts) {
            model.addElement(acc);
        }
    }
}
