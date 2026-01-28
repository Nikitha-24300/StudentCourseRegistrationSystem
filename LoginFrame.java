package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import db.DBConnection;

public class LoginFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;
    JComboBox<String> roleBox;

    public LoginFrame() {

        setTitle("Student Course Registration - Login");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // MAIN BACKGROUND PANEL
        JPanel bgPanel = new JPanel();
        bgPanel.setLayout(new BorderLayout());
        bgPanel.setBackground(new Color(230, 240, 255));
        add(bgPanel);

        // CENTER FORM PANEL
        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(350, 250));
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 240), 2, true));

        bgPanel.add(formPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("LOGIN", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(40, 40, 90));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(title, gbc);

        gbc.gridwidth = 1;

        // Username
        JLabel userLbl = new JLabel("Username:");
        userLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(userLbl, gbc);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        // Password
        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(passLbl, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Role
        JLabel roleLbl = new JLabel("Role:");
        roleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(roleLbl, gbc);

        String[] roles = {"Student", "Admin"};
        roleBox = new JComboBox<>(roles);
        roleBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        formPanel.add(roleBox, gbc);

        // Buttons Panel
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        JButton signUpBtn = new JButton("Sign Up");
        signUpBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signUpBtn.setBackground(new Color(50, 170, 100));
        signUpBtn.setForeground(Color.WHITE);
        signUpBtn.setFocusPainted(false);
        signUpBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        btnPanel.add(loginBtn);
        btnPanel.add(signUpBtn);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        // Click actions
        loginBtn.addActionListener(e -> handleLogin());
        signUpBtn.addActionListener(e -> {
            new SignUpFrame();
            dispose();
        });

        setVisible(true);
    }

    // LOGIN LOGIC
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();
        String role = roleBox.getSelectedItem().toString();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();

            // Correct query
            String query = role.equals("Student")
                    ? "SELECT * FROM students WHERE username=? AND password=?"
                    : "SELECT * FROM admins WHERE username=? AND password=?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
    // Open respective dashboard
    if (role.equals("Student")) {
        new StudentDashboard(username);
    } else {
        new AdminDashboard(username); // Assuming you have AdminDashboard
    }
    dispose(); // Close the login window
} else {
    JOptionPane.showMessageDialog(this, "Invalid credentials!");
}


        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!");
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
