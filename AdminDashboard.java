package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {

    private String username; // Store logged-in admin username

    // Modified constructor to accept username
    public AdminDashboard(String username) {
        this.username = username;
        initUI();
    }

    // Original no-argument constructor calls the new one with a default username
    public AdminDashboard() {
        this("Admin"); // default
        initUI();
    }

    private void initUI() {
        setTitle("Admin Dashboard - " + username);
        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 240, 255));
        add(mainPanel);

        // HEADER
        JLabel title = new JLabel("ADMIN DASHBOARD", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(40, 40, 90));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        // Optional: Show logged-in admin
        JLabel loggedInLbl = new JLabel("Logged in as: " + username, SwingConstants.CENTER);
        loggedInLbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loggedInLbl.setForeground(new Color(60, 60, 60));
        loggedInLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(loggedInLbl, BorderLayout.SOUTH);

        // CENTER PANEL FOR BUTTONS
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(5, 1, 15, 15));
        btnPanel.setBackground(new Color(230, 240, 255));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        JButton addCourseBtn = createButton("Add Course");
        JButton viewCourseBtn = createButton("View Courses");
        JButton viewStudentCountBtn = createButton("View Total Students Registered");
        JButton viewStudentCourseBtn = createButton("View Courses Registered by Student");
        JButton logoutBtn = createButton("Logout");

        btnPanel.add(addCourseBtn);
        btnPanel.add(viewCourseBtn);
        btnPanel.add(viewStudentCountBtn);
        btnPanel.add(viewStudentCourseBtn);
        btnPanel.add(logoutBtn);

        mainPanel.add(btnPanel, BorderLayout.CENTER);

        // ACTIONS
        addCourseBtn.addActionListener(e -> new AddCourseFrame());
        viewCourseBtn.addActionListener(e -> new ViewCoursesFrame());
        viewStudentCountBtn.addActionListener(e -> new ViewStudentCountFrame());
        viewStudentCourseBtn.addActionListener(e -> new ViewStudentRegistrationsFrame());

        logoutBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
