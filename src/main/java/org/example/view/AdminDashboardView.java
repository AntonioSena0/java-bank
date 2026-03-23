package org.example.view;

import org.example.controller.AccountController;
import org.example.controller.TransactionController;
import org.example.dto.AccountResponse;
import org.example.dto.TransactionResponse;
import org.example.view.ui.Dependencies;
import org.example.view.ui.MainFrame;
import org.example.view.ui.UiHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminDashboardView extends JPanel {

    public AdminDashboardView(MainFrame mainFrame) {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JPanel navPanel = UiHelper.createNavigationPanel(mainFrame, null);
        topPanel.add(navPanel, BorderLayout.NORTH);

        JLabel title = new JLabel("Painel Administrativo", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(33, 37, 41));
        topPanel.add(title, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setOpaque(false);

        AccountController accountController = Dependencies.accountController();
        TransactionController txController = Dependencies.transactionController();

        JTextArea accountsArea = createTextArea("Contas no Sistema:\n");
        List<AccountResponse> accounts = accountController.findAll();
        for (AccountResponse acc : accounts) {
            accountsArea.append("Conta " + acc.id() + " - Cliente: " + acc.customer().id() + " - Saldo: " + acc.balance() + "\n");
        }

        JTextArea txArea = createTextArea("Transações no Sistema:\n");
        List<TransactionResponse> txs = txController.findAll();
        for (TransactionResponse t : txs) {
            txArea.append("Tx " + t.id() + " - " + t.type() + " - " + t.amount() + "\n");
        }

        centerPanel.add(new JScrollPane(accountsArea));
        centerPanel.add(new JScrollPane(txArea));

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        bottomPanel.setOpaque(false);

        JButton analyzeAccountBtn = UiHelper.createActionButton("Analisar Conta");
        JButton analyzeTxBtn = UiHelper.createActionButton("Analisar Transação");

        analyzeAccountBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID da conta:");
            if (idStr == null) return;
            try {
                Long accId = Long.parseLong(idStr);
                AccountResponse acc = accountController.find(accId);
                if (acc == null) {
                    JOptionPane.showMessageDialog(this, "Conta não encontrada");
                    return;
                }
                AccountAnalysisView v = new AccountAnalysisView(txController, acc);
                v.showDialog(this);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID inválido");
            }
        });

        analyzeTxBtn.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID da transação:");
            if (idStr == null) return;
            try {
                Long txId = Long.parseLong(idStr);
                TransactionResponse t = txController.findTransaction(txId);
                if (t == null) {
                    JOptionPane.showMessageDialog(this, "Transação não encontrada");
                    return;
                }
                TransactionAnalysisView v = new TransactionAnalysisView(t);
                v.showDialog(this);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID inválido");
            }
        });

        bottomPanel.add(analyzeAccountBtn);
        bottomPanel.add(analyzeTxBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JTextArea createTextArea(String initialText) {
        JTextArea area = new JTextArea(initialText);
        area.setFont(new Font("Consolas", Font.PLAIN, 12));
        area.setEditable(false);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return area;
    }
}
