package org.example.view;

import org.example.controller.TransactionController;
import org.example.dto.AccountResponse;
import org.example.dto.TransactionResponse;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AccountAnalysisView {

    private final TransactionController transactionController;
    private final AccountResponse account;

    public AccountAnalysisView(TransactionController transactionController, AccountResponse account) {
        this.transactionController = transactionController;
        this.account = account;
    }

    public void showDialog(Component parent) {
        JDialog dialog = new JDialog(
                SwingUtilities.getWindowAncestor(parent),
                "Análise da Conta " + account.id(),
                Dialog.ModalityType.APPLICATION_MODAL
        );

        JTextArea area = new JTextArea();
        area.setEditable(false);

        area.append("Conta: " + account.id() + "\n");
        area.append("Cliente: " + account.customer().id() + "\n");
        area.append("Saldo: " + account.balance() + "\n");
        area.append("Tipo: " + account.type() + "\n");
        area.append("\nTransações da conta:\n");

        List<TransactionResponse> txs = transactionController.listTransactionsByAccountId(account.id());
        if (txs.isEmpty()) {
            area.append("Nenhuma transação encontrada.\n");
        } else {
            for (TransactionResponse t : txs) {
                area.append("Id: " + t.id() + "\n");
                area.append("Tipo: " + t.type() + "\n");
                area.append("Valor: " + t.amount() + "\n");
                area.append("Data: " + t.createdAt() + "\n");
                if (t.from_account() != null) {
                    area.append("Conta origem: " + t.from_account().id() + "\n");
                }
                if (t.to_account() != null) {
                    area.append("Conta destino: " + t.to_account().id() + "\n");
                }
                area.append("------------------------\n");
            }
        }

        dialog.add(new JScrollPane(area));
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
}
