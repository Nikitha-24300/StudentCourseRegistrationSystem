package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.DBConnection;

public class AddCourseFrame extends JFrame {

    JTextField courseNameField, seatsField;

    public AddCourseFrame() {

        setTitle("Add Course");
        setSize(450, 300);
        setLocationRelativeTo(null);

        // Gradient Background
        JPanel bg = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0,
                        new Color(220, 230, 255),
                        0, getHeight(),
                        new Color(240, 240, 255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bg.setLayout(new GridBagLayout());
        add(bg);

        // Card Panel
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(350, 220));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 230), 2, true),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("ADD NEW COURSE", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(70, 70, 120));

        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(title, gbc);

        // Course Name
        JLabel lbl1 = new JLabel("Course Name:");
        lbl1.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        card.add(lbl1, gbc);

        courseNameField = new JTextField();
        courseNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        card.add(courseNameField, gbc);

        // Seats
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lbl2 = new JLabel("Total Seats:");
        lbl2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        card.add(lbl2, gbc);

        seatsField = new JTextField();
        seatsField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        card.add(seatsField, gbc);

        // Add Button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;

        JButton addBtn = new JButton("➕ Add Course");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addBtn.setBackground(new Color(60, 140, 200));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        addBtn.setFocusPainted(false);

        card.add(addBtn, gbc);
        bg.add(card);

        addBtn.addActionListener(e -> saveCourse());

        setVisible(true);
    }

    private void saveCourse() {
        String name = courseNameField.getText();
        String seats = seatsField.getText();

        if (name.isEmpty() || seats.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try {
            int total = Integer.parseInt(seats);

            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO courses(course_name, total_seats, available_seats) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, total);
            stmt.setInt(3, total);

            int result = stmt.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Course Added Successfully!");
                courseNameField.setText("");
                seatsField.setText("");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Seats must be a valid number!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error!");
            ex.printStackTrace();
        }
    }
}
