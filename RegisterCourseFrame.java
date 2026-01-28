package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.DBConnection;

public class RegisterCourseFrame extends JFrame {

    private String username;
    private JComboBox<String> courseBox;

    public RegisterCourseFrame(String username) {
        this.username = username;

        setTitle("Register for Course");
        setSize(450, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(235, 245, 255));
        add(panel);

        JLabel title = new JLabel("REGISTER FOR COURSE", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(40, 40, 90));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 15, 15));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        form.setBackground(new Color(235, 245, 255));

        form.add(new JLabel("Select Course:", SwingConstants.RIGHT));
        courseBox = new JComboBox<>();
        form.add(courseBox);

        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerBtn.setBackground(new Color(60, 140, 200));
        registerBtn.setForeground(Color.WHITE);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(235, 245, 255));
        btnPanel.add(registerBtn);

        panel.add(form, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        loadCourses();

        registerBtn.addActionListener(e -> registerCourse());

        setVisible(true);
    }

    private void loadCourses() {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT course_name FROM courses WHERE available_seats > 0";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courseBox.addItem(rs.getString("course_name"));
            }

            if (courseBox.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "No courses available!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!");
        }
    }

    private void registerCourse() {
        String selectedCourse = (String) courseBox.getSelectedItem();

        if (selectedCourse == null) {
            JOptionPane.showMessageDialog(this, "No course selected!");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();

            // Get course id and available seats
            String query = "SELECT id, available_seats FROM courses WHERE course_name=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, selectedCourse);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int courseId = rs.getInt("id");
                int seats = rs.getInt("available_seats");

                // Check if student already registered
                query = "SELECT * FROM registrations WHERE username=? AND course_id=?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setInt(2, courseId);
                ResultSet check = stmt.executeQuery();

                if (check.next()) {
                    JOptionPane.showMessageDialog(this, "Already registered for this course!");
                    return;
                }

                if (seats <= 0) {
                    JOptionPane.showMessageDialog(this, "No seats available!");
                    return;
                }

                // Insert registration
                query = "INSERT INTO registrations(username, course_id) VALUES(?, ?)";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setInt(2, courseId);
                stmt.executeUpdate();

                // Update available seats
                query = "UPDATE courses SET available_seats = available_seats - 1 WHERE id=?";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, courseId);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Course registered successfully!");
                dispose();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!");
        }
    }
}
