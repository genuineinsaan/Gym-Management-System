package com.gym.admin;

import com.gym.db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EditTrainerDialog extends JDialog {

    private JTextField txtName;
    private JTextField txtContactInfo;
    private int trainerId;

    public EditTrainerDialog(JFrame parent, int trainerId) {
        super(parent, "Edit Trainer", true);
        this.trainerId = trainerId;
        
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2; add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.8; txtName = new JTextField(); add(txtName, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Contact Info:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; txtContactInfo = new JTextField(); add(txtContactInfo, gbc);

        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Save Changes");
        JButton btnCancel = new JButton("Cancel");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        loadTrainerData();
        btnSave.addActionListener(e -> saveTrainerChanges());
        btnCancel.addActionListener(e -> dispose());
    }

    private void loadTrainerData() {
        String sql = "SELECT name, contact_info FROM trainers WHERE trainer_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trainerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                txtName.setText(rs.getString("name"));
                txtContactInfo.setText(rs.getString("contact_info"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveTrainerChanges() {
        String sql = "UPDATE trainers SET name = ?, contact_info = ? WHERE trainer_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, txtName.getText());
            pstmt.setString(2, txtContactInfo.getText());
            pstmt.setInt(3, trainerId);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Trainer details updated!");
            dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}