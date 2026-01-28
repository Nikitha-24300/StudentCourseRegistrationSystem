package ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import db.DBConnection;

public class ViewCoursesFrame extends JFrame {

    JTable table;

    public ViewCoursesFrame() {

        setTitle("View Courses");
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Background Gradient
        JPanel bg = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0,
                        new Color(225, 235, 255),
                        0, getHeight(),
                        new Color(240, 245, 255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bg.setLayout(new BorderLayout());
        add(bg);

        // Card panel for table
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 230), 2, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel title = new JLabel("AVAILABLE COURSES", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(60, 60, 120));
        card.add(title, BorderLayout.NORTH);

        // Table
        String[] cols = {"ID", "Course Name", "Total Seats", "Available Seats"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(160, 190, 240));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(180, 200, 240));

        JScrollPane scroll = new JScrollPane(table);
        card.add(scroll, BorderLayout.CENTER);

        bg.add(card, BorderLayout.CENTER);

        loadCourses(model);

        setVisible(true);
    }

    private void loadCourses(DefaultTableModel model) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM courses";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("course_name"),
                        rs.getInt("total_seats"),
                        rs.getInt("available_seats")
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error!");
            ex.printStackTrace();
        }
    }
}
