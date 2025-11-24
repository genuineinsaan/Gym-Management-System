package com.gym.user;

import com.gym.db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginScreen extends JFrame {

    private JTextField txtPhoneNumber;
    private JPasswordField txtDob;
    private JButton btnLogin;

    public UserLoginScreen() {
        setTitle("Member Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Phone Number:"));
        txtPhoneNumber = new JTextField();
        add(txtPhoneNumber);

        add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        txtDob = new JPasswordField();
        add(txtDob);

        add(new JLabel("")); 
        btnLogin = new JButton("Login");
        add(btnLogin);

        btnLogin.addActionListener(e -> loginUser());
    }

    private void loginUser() {
        String phone = txtPhoneNumber.getText();
        String dob = new String(txtDob.getPassword());

        if (phone.isEmpty() || dob.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both phone number and date of birth.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "SELECT member_id, name FROM members WHERE phone_number = ? AND dob = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, phone);
            pstmt.setString(2, dob);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int memberId = rs.getInt("member_id");
                new UserDashboard(memberId).setVisible(true);
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error during login.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}