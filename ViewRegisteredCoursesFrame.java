package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import db.DBConnection;

public class ViewRegisteredCoursesFrame extends JFrame {

    private String username;
    private boolean onlyRegistered; // true = show registered courses, false = show all courses
    private JTable table;

    public ViewRegisteredCoursesFrame(String username, boolean onlyRegistered) {
        this.username = username;
        this.onlyRegistered = onlyRegistered;

        setTitle(onlyRegistered ? "Your Registered Courses" : "All Courses");
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 255, 240));
        add(panel);

        JLabel title = new JLabel(onlyRegistered ? "REGISTERED COURSES" : "AVAILABLE COURSES", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(30, 60, 30));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadCourses();

        setVisible(true);
    }

    private void loadCourses() {
        try {
            Connection conn = DBConnection.getConnection();
            String query;

            if (onlyRegistered) {
                query = "SELECT c.course_name, c.total_seats, c.available_seats " +
                        "FROM courses c JOIN registrations r ON c.id = r.course_id " +
                        "WHERE r.username=?";
            } else {
                query = "SELECT course_name, total_seats, available_seats FROM courses";
            }

            PreparedStatement stmt = conn.prepareStatement(query);

            if (onlyRegistered) stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new String[]{"Course Name", "Total Seats", "Available Seats"}, 0);

            while (rs.next()) {
                String courseName = rs.getString("course_name");
                int totalSeats = rs.getInt("total_seats");
                int availableSeats = rs.getInt("available_seats");
                model.addRow(new Object[]{courseName, totalSeats, availableSeats});
            }

            table.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!");
        }
    }
}
