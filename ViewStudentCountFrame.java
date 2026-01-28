package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.DBConnection;

public class ViewStudentCountFrame extends JFrame {

    public ViewStudentCountFrame() {

        setTitle("Total Students Registered");
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 250, 255));
        add(panel);

        JLabel title = new JLabel("TOTAL STUDENTS REGISTERED", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(50, 50, 120));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        JLabel countLabel = new JLabel("", SwingConstants.CENTER);
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        countLabel.setForeground(new Color(20, 100, 160));
        panel.add(countLabel, BorderLayout.CENTER);

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT COUNT(*) AS total FROM students";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                countLabel.setText(rs.getInt("total") + " Students");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            countLabel.setText("Error!");
        }

        setVisible(true);
    }
}
