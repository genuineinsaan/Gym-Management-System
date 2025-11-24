package com.gym.admin;

import com.gym.db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class EditMemberDialog extends JDialog {

    private JTextField txtName;
    private JTextField txtPhoneNumber;
    private JTextField txtDob;
    private JComboBox<String> cmbStatus;
    private int memberId;

    public EditMemberDialog(JFrame parent, int memberId) {
        super(parent, "Edit Member", true);
        this.memberId = memberId;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add form components using GridBagLayout...
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2; add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.8; txtName = new JTextField(); add(txtName, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; txtPhoneNumber = new JTextField(); add(txtPhoneNumber, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Date of Birth (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; txtDob = new JTextField(); add(txtDob, gbc);
        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; cmbStatus = new JComboBox<>(new String[]{"Active", "Inactive"}); add(cmbStatus, gbc);

        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Save Changes");
        JButton btnCancel = new JButton("Cancel");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        loadMemberData();
        btnSave.addActionListener(e -> saveMemberChanges());
        btnCancel.addActionListener(e -> dispose());
    }

    private void loadMemberData() {
        String sql = "SELECT name, phone_number, dob, status FROM members WHERE member_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, memberId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                txtName.setText(rs.getString("name"));
                txtPhoneNumber.setText(rs.getString("phone_number"));
                txtDob.setText(rs.getString("dob"));
                cmbStatus.setSelectedItem(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveMemberChanges() {
        String sql = "UPDATE members SET name = ?, phone_number = ?, dob = ?, status = ? WHERE member_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, txtName.getText());
            pstmt.setString(2, txtPhoneNumber.getText());
            pstmt.setString(3, txtDob.getText());
            pstmt.setString(4, (String) cmbStatus.getSelectedItem());
            pstmt.setInt(5, memberId);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Member details updated successfully!");
            dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}