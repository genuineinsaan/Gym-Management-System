package com.gym.admin;

import com.gym.db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Gym Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton btnManageMembers = new JButton("Manage Members");
        JButton btnManagePlans = new JButton("Manage Plans");
        JButton btnManageTrainers = new JButton("Manage Trainers");
        JButton btnManageAnnouncements = new JButton("Manage Announcements");
        
        Font btnFont = new Font("Arial", Font.PLAIN, 16);
        btnManageMembers.setFont(btnFont);
        btnManagePlans.setFont(btnFont);
        btnManageTrainers.setFont(btnFont);
        btnManageAnnouncements.setFont(btnFont);
        
        buttonPanel.add(btnManageMembers);
        buttonPanel.add(btnManagePlans);
        buttonPanel.add(btnManageTrainers);
        buttonPanel.add(btnManageAnnouncements);
        add(buttonPanel, BorderLayout.CENTER);
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Live Statistics"));
        JLabel lblTotalMembers = new JLabel("Total Members: 0", SwingConstants.CENTER);
        JLabel lblPaymentsDue = new JLabel("Payments Due: 0", SwingConstants.CENTER);
        JLabel lblTotalTrainers = new JLabel("Total Trainers: 0", SwingConstants.CENTER);
        Font statsFont = new Font("Arial", Font.BOLD, 18);
        lblTotalMembers.setFont(statsFont);
        lblPaymentsDue.setFont(statsFont);
        lblTotalTrainers.setFont(statsFont);
        statsPanel.add(lblTotalMembers);
        statsPanel.add(lblPaymentsDue);
        statsPanel.add(lblTotalTrainers);
        add(statsPanel, BorderLayout.SOUTH);

        try {
            lblTotalMembers.setText("Total Members: " + getTotalCount("SELECT COUNT(*) FROM members WHERE status = 'Active'"));
            lblPaymentsDue.setText("Payments Due: " + getTotalCount("SELECT COUNT(*) FROM members WHERE next_payment_due BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)"));
            lblTotalTrainers.setText("Total Trainers: " + getTotalCount("SELECT COUNT(*) FROM trainers"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnManageMembers.addActionListener(e -> { new MemberManagementPanel(this).setVisible(true); this.setVisible(false); });
        btnManagePlans.addActionListener(e -> { new PlanManagementPanel(this).setVisible(true); this.setVisible(false); });
        btnManageTrainers.addActionListener(e -> { new TrainerManagementPanel(this).setVisible(true); this.setVisible(false); });
        btnManageAnnouncements.addActionListener(e -> { new AnnouncementManagementPanel(this).setVisible(true); this.setVisible(false); });
    }

    private int getTotalCount(String query) throws SQLException {
        int count = 0;
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        }
        return count;
    }
}