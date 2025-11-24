package com.gym.admin;

import com.gym.db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddPlanDialog extends JDialog {

    private JTextField txtPlanName;
    private JTextField txtPrice;
    private JTextField txtDuration;

    public AddPlanDialog(JFrame parent) {
        super(parent, "Add New Plan", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2; add(new JLabel("Plan Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.8; txtPlanName = new JTextField(); add(txtPlanName, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Price:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; txtPrice = new JTextField(); add(txtPrice, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Duration (in days):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; txtDuration = new JTextField(); add(txtDuration, gbc);

        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        btnSave.addActionListener(e -> savePlan());
        btnCancel.addActionListener(e -> dispose());
    }

    private void savePlan() {
        String planName = txtPlanName.getText();
        String priceStr = txtPrice.getText().trim();
        String durationStr = txtDuration.getText().trim();

        if (planName.isEmpty() || priceStr.isEmpty() || durationStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int duration = Integer.parseInt(durationStr);
            String sql = "INSERT INTO plans (plan_name, price, duration_days) VALUES (?, ?, ?)";
            try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, planName);
                pstmt.setDouble(2, price);
                pstmt.setInt(3, duration);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Plan added successfully!");
                dispose();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for Price and Duration.");
        }
    }
}