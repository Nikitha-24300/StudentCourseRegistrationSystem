package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.DBConnection;

public class ViewStudentRegistrationsFrame extends JFrame {

    JTextField usernameField;

    public ViewStudentRegistrationsFrame() {

        setTitle("View Student Registrations");
        setSize(450, 350);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(235, 245, 255));
        add(mainPanel);

        JLabel title = new JLabel("CHECK STUDENT COURSE REGISTRATIONS", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(40, 40, 110));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 15, 15));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        form.setBackground(new Color(235, 245, 255));

        form.add(new JLabel("Enter Student Username:", SwingConstants.RIGHT));
        usernameField = new JTextField();
        form.add(usernameField);

        JButton checkBtn = new JButton("Check Courses");
        checkBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        checkBtn.setBackground(new Color(60, 140, 200));
        checkBtn.setForeground(Color.WHITE);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(235, 245, 255));
        btnPanel.add(checkBtn);

        mainPanel.add(form, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        checkBtn.addActionListener(e -> showCourses());

        setVisible(true);
    }

    private void showCourses() {

        String username = usernameField.getText().trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a username!");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();

            String query =
                    "SELECT c.course_name " +
                    "FROM registrations r " +
                    "JOIN courses c ON r.course_id = c.id " +
                    "WHERE r.username=?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            DefaultListModel<String> model = new DefaultListModel<>();

            while (rs.next()) {
                model.addElement(rs.getString("course_name"));
            }

            if (model.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No registrations found!");
            } else {
                JList<String> list = new JList<>(model);
                list.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                JScrollPane scroll = new JScrollPane(list);
                scroll.setPreferredSize(new Dimension(300, 200));

                JOptionPane.showMessageDialog(this, scroll,
                        "Courses Registered by " + username, JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!");
        }
    }
}
