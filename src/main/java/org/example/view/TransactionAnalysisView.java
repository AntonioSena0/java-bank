package org.example.view;

import org.example.dto.TransactionResponse;

import javax.swing.*;
import java.awt.*;

public class TransactionAnalysisView {

    private final TransactionResponse transaction;

    public TransactionAnalysisView(TransactionResponse transaction) {
        this.transaction = transaction;
    }

    public void showDialog(Component parent) {
        JDialog dialog = new JDialog(
                SwingUtilities.getWindowAncestor(parent),
                "Transação " + transaction.id(),
                Dialog.ModalityType.APPLICATION_MODAL
        );

        JTextArea area = new JTextArea();
        area.setEditable(false);

        area.append("Id: " + transaction.id() + "\n");
        area.append("Tipo: " + transaction.type() + "\n");
        area.append("Valor: " + transaction.amount() + "\n");
        area.append("Data: " + transaction.createdAt() + "\n");
        if (transaction.from_account() != null) {
            area.append("Conta origem: " + transaction.from_account().id() + "\n");
        }
        if (transaction.to_account() != null) {
            area.append("Conta destino: " + transaction.to_account().id() + "\n");
        }

        dialog.add(new JScrollPane(area));
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
}