package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.DBConnection;

public class DropCourseFrame extends JFrame {

    private String username;
    private JComboBox<String> courseBox;

    public DropCourseFrame(String username) {
        this.username = username;

        setTitle("Drop Course");
        setSize(450, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 240, 230));
        add(panel);

        JLabel title = new JLabel("DROP COURSE", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(90, 40, 40));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 15, 15));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        form.setBackground(new Color(255, 240, 230));

        form.add(new JLabel("Select Course to Drop:", SwingConstants.RIGHT));
        courseBox = new JComboBox<>();
        form.add(courseBox);

        JButton dropBtn = new JButton("Drop");
        dropBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        dropBtn.setBackground(new Color(200, 60, 60));
        dropBtn.setForeground(Color.WHITE);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(255, 240, 230));
        btnPanel.add(dropBtn);

        panel.add(form, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        loadRegisteredCourses();

        dropBtn.addActionListener(e -> dropCourse());

        setVisible(true);
    }

    private void loadRegisteredCourses() {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT c.course_name FROM courses c " +
                           "JOIN registrations r ON c.id = r.course_id " +
                           "WHERE r.username=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courseBox.addItem(rs.getString("course_name"));
            }

            if (courseBox.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "You have no registered courses!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!");
        }
    }

    private void dropCourse() {
        String selectedCourse = (String) courseBox.getSelectedItem();

        if (selectedCourse == null) {
            JOptionPane.showMessageDialog(this, "No course selected!");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();

            // Get course id
            String query = "SELECT id FROM courses WHERE course_name=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, selectedCourse);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int courseId = rs.getInt("id");

                // Delete registration
                query = "DELETE FROM registrations WHERE username=? AND course_id=?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setInt(2, courseId);
                stmt.executeUpdate();

                // Update available seats
                query = "UPDATE courses SET available_seats = available_seats + 1 WHERE id=?";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, courseId);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Course dropped successfully!");
                dispose();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!");
        }
    }
}
