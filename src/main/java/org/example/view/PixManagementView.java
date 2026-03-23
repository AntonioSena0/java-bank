package org.example.view;

import org.example.controller.PixKeyController;
import org.example.dto.PixKeyRequest;
import org.example.dto.PixKeyResponse;
import org.example.enums.PixKeyType;
import org.example.view.ui.MainFrame;
import org.example.view.ui.UiHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PixManagementView extends JPanel {

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

    public PixManagementView(MainFrame mainFrame,
                             PixKeyController pixController,
                             Long accountId) {

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        
        JPanel navPanel = UiHelper.createNavigationPanel(mainFrame, MainFrame.SCREEN_ACCOUNT_MANAGE);
        topPanel.add(navPanel, BorderLayout.NORTH);

        JLabel title = new JLabel("Chaves PIX - Conta " + accountId, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(33, 37, 41));
        topPanel.add(title, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);

        DefaultListModel<PixKeyResponse> model = new DefaultListModel<>();
        List<PixKeyResponse> pixList = pixController.findByAccountId(accountId);
        for (PixKeyResponse p : pixList) model.addElement(p);

        JList<PixKeyResponse> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer((jList, value, index, isSelected, cellHasFocus) -> {
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            panel.setBackground(isSelected ? new Color(231, 241, 251) : Color.WHITE);
            
            JLabel keyLabel = new JLabel(value.keyValue());
            keyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            
            JLabel typeLabel = new JLabel(value.type().toString());
            typeLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            typeLabel.setForeground(new Color(108, 117, 125));

            panel.add(keyLabel, BorderLayout.WEST);
            panel.add(typeLabel, BorderLayout.EAST);
            
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

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        btnPanel.setOpaque(false);

        JButton addBtn = UiHelper.createActionButton("Criar Chave");
        JButton deleteBtn = UiHelper.createDangerButton("Excluir Chave");

        addBtn.addActionListener(e -> {
            String keyValue = JOptionPane.showInputDialog(this, "Valor da chave:");
            if (keyValue == null || keyValue.isEmpty()) return;

            PixKeyType type = (PixKeyType) JOptionPane.showInputDialog(
                    this, "Tipo:", "Tipo de chave",
                    JOptionPane.PLAIN_MESSAGE, null, PixKeyType.values(), PixKeyType.CPF
            );
            if(type == PixKeyType.CPF){
                keyValue = formatarCPF(keyValue);
            }
            if(type == PixKeyType.PHONE){
                keyValue = formatarTelefone(keyValue);
            }

            if (type == null) return;

            PixKeyRequest req = new PixKeyRequest(keyValue, type, accountId);
            PixKeyResponse created = pixController.createPixKey(req);
            if (created != null) {
                model.addElement(created);
            }
        });

        deleteBtn.addActionListener(e -> {
            PixKeyResponse selected = list.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma chave");
                return;
            }
            pixController.deletePixKey(selected.id());
            model.removeElement(selected);
        });

        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);

        add(btnPanel, BorderLayout.SOUTH);
    }
}
