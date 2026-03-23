package org.example.view;

import org.example.controller.TransactionController;
import org.example.dto.TransactionResponse;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AccountTransactionView {

    private final TransactionController controller;
    private final Long accountId;

    public AccountTransactionView(TransactionController controller, Long accountId) {
        this.controller = controller;
        this.accountId = accountId;
    }

    public void showDialog(Component parent) {
        JDialog dialog = new JDialog(
                SwingUtilities.getWindowAncestor(parent),
                "Transações da Conta " + accountId,
                Dialog.ModalityType.APPLICATION_MODAL
        );

        JTextArea area = new JTextArea();
        area.setEditable(false);

        List<TransactionResponse> txs = controller.listTransactionsByAccountId(accountId);
        if (txs.isEmpty()) {
            area.append("Nenhuma transação encontrada para esta conta.\n");
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