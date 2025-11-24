package com.gym.admin;

import com.gym.db.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AnnouncementManagementPanel extends JFrame {

    private JTable announcementTable;
    private DefaultTableModel tableModel;
    private JFrame parentDashboard;

    public AnnouncementManagementPanel(JFrame parentDashboard) {
        this.parentDashboard = parentDashboard;
        setTitle("Manage Announcements");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                parentDashboard.setVisible(true);
                dispose();
            }
        });

        tableModel = new DefaultTableModel();
        announcementTable = new JTable(tableModel);
        add(new JScrollPane(announcementTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add Announcement");
        JButton btnEdit = new JButton("Edit Selected");
        JButton btnDelete = new JButton("Delete Selected");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        add(buttonPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addAnnouncement());
        btnEdit.addActionListener(e -> editAnnouncement());
        btnDelete.addActionListener(e -> deleteAnnouncement());

        loadAnnouncements();
    }
    
    private void loadAnnouncements() {
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM announcements ORDER BY post_date DESC")) {
            tableModel.addColumn("ID");
            tableModel.addColumn("TITLE");
            tableModel.addColumn("CONTENT");
            tableModel.addColumn("DATE");
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getInt("announcement_id"), rs.getString("title"), rs.getString("content"), rs.getDate("post_date")});
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void addAnnouncement() {
        String title = JOptionPane.showInputDialog(this, "Enter Title:");
        if (title == null || title.trim().isEmpty()) return;
        String content = JOptionPane.showInputDialog(this, "Enter Content:");
        if (content == null) return;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement("INSERT INTO announcements (title, content, post_date) VALUES (?, ?, ?)")) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));
            pstmt.executeUpdate();
            loadAnnouncements();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void editAnnouncement() {
        int selectedRow = announcementTable.getSelectedRow();
        if (selectedRow < 0) { JOptionPane.showMessageDialog(this, "Please select an announcement to edit."); return; }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String currentTitle = (String) tableModel.getValueAt(selectedRow, 1);
        String currentContent = (String) tableModel.getValueAt(selectedRow, 2);
        String newTitle = JOptionPane.showInputDialog(this, "Enter new title:", currentTitle);
        if (newTitle == null || newTitle.trim().isEmpty()) return;
        String newContent = JOptionPane.showInputDialog(this, "Enter new content:", currentContent);
        if (newContent == null) return;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement("UPDATE announcements SET title = ?, content = ? WHERE announcement_id = ?")) {
            pstmt.setString(1, newTitle);
            pstmt.setString(2, newContent);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            loadAnnouncements();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void deleteAnnouncement() {
        int selectedRow = announcementTable.getSelectedRow();
        if (selectedRow < 0) { JOptionPane.showMessageDialog(this, "Please select an announcement to delete."); return; }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement("DELETE FROM announcements WHERE announcement_id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            loadAnnouncements();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}