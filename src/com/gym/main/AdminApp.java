package com.gym.main;

import com.gym.admin.AdminDashboard;
import javax.swing.SwingUtilities;

public class AdminApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminDashboard().setVisible(true);
        });
    }
}