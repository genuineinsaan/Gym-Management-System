package com.gym.admin;

import com.gym.db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EditPlanDialog extends JDialog {

    private JTextField txtPlanName;
    private JTextField txtPrice;
    private JTextField txtDuration;
    private int planId;

    public EditPlanDialog(JFrame parent, int planId) {
        super(parent, "Edit Plan", true);
        this.planId = planId;

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
        JButton btnSave = new JButton("Save Changes");
        JButton btnCancel = new JButton("Cancel");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        loadPlanData();
        btnSave.addActionListener(e -> savePlanChanges());
        btnCancel.addActionListener(e -> dispose());
    }

    private void loadPlanData() {
        String sql = "SELECT plan_name, price, duration_days FROM plans WHERE plan_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, planId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                txtPlanName.setText(rs.getString("plan_name"));
                txtPrice.setText(String.valueOf(rs.getDouble("price")));
                txtDuration.setText(String.valueOf(rs.getInt("duration_days")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void savePlanChanges() {
        try {
            String sql = "UPDATE plans SET plan_name = ?, price = ?, duration_days = ? WHERE plan_id = ?";
            try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, txtPlanName.getText());
                pstmt.setDouble(2, Double.parseDouble(txtPrice.getText()));
                pstmt.setInt(3, Integer.parseInt(txtDuration.getText()));
                pstmt.setInt(4, planId);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Plan updated successfully!");
                dispose();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Price and Duration.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}