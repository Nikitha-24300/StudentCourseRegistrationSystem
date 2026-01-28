package ui;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    private String username;

    // Parameterized constructor to accept logged-in username
    public StudentDashboard(String username) {
        this.username = username;
        initUI();
    }

    // Optional: default constructor
    public StudentDashboard() {
        this("Student"); // default username
        initUI();
    }

    private void initUI() {
        setTitle("Student Dashboard - " + username);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 245, 255));
        add(mainPanel);

        // HEADER
        JLabel title = new JLabel("WELCOME, " + username.toUpperCase(), SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(40, 40, 90));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        // BUTTON PANEL
        JPanel btnPanel = new JPanel(new GridLayout(5, 1, 15, 15));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));
        btnPanel.setBackground(new Color(230, 245, 255));

        JButton viewAvailableBtn = createButton("View Available Courses");
        JButton registerBtn = createButton("Register for Course");
        JButton dropBtn = createButton("Drop Course");
        JButton viewRegisteredBtn = createButton("View Registered Courses");
        JButton logoutBtn = createButton("Logout");

        btnPanel.add(viewAvailableBtn);
        btnPanel.add(registerBtn);
        btnPanel.add(dropBtn);
        btnPanel.add(viewRegisteredBtn);
        btnPanel.add(logoutBtn);

        mainPanel.add(btnPanel, BorderLayout.CENTER);

        // ACTIONS
        viewAvailableBtn.addActionListener(e -> new ViewRegisteredCoursesFrame(username, false)); // view all courses
        registerBtn.addActionListener(e -> new RegisterCourseFrame(username));
        dropBtn.addActionListener(e -> new DropCourseFrame(username));
        viewRegisteredBtn.addActionListener(e -> new ViewRegisteredCoursesFrame(username, true)); // view only registered courses
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
