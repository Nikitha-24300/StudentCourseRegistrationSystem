package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import db.DBConnection;

public class SignUpFrame extends JFrame {

    JTextField usernameField, fullNameField;
    JPasswordField passwordField;
    JComboBox<String> roleBox;

    public SignUpFrame() {

        setTitle("Student Course Registration - Sign Up");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // MAIN BACKGROUND PANEL
        JPanel bgPanel = new JPanel();
        bgPanel.setLayout(new BorderLayout());
        bgPanel.setBackground(new Color(230, 240, 255));
        add(bgPanel);

        // FORM PANEL
        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(380, 300));
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 240), 2, true));
        bgPanel.add(formPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("CREATE ACCOUNT", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(40, 40, 90));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(title, gbc);
        gbc.gridwidth = 1;

        // Full Name
        JLabel nameLbl = new JLabel("Full Name:");
        nameLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(nameLbl, gbc);

        fullNameField = new JTextField();
        fullNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        formPanel.add(fullNameField, gbc);

        // Username
        JLabel userLbl = new JLabel("Username:");
        userLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(userLbl, gbc);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        // Password
        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(passLbl, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Role
        JLabel roleLbl = new JLabel("Role:");
        roleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(roleLbl, gbc);

        String[] roles = {"Student", "Admin"};
        roleBox = new JComboBox<>(roles);
        roleBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        formPanel.add(roleBox, gbc);

        // Buttons Panel
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);

        JButton signUpBtn = new JButton("Create Account");
        signUpBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signUpBtn.setBackground(new Color(50, 170, 100));
        signUpBtn.setForeground(Color.WHITE);
        signUpBtn.setFocusPainted(false);
        signUpBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        JButton loginBtn = new JButton("Back to Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        btnPanel.add(signUpBtn);
        btnPanel.add(loginBtn);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        formPanel.add(btnPanel, gbc);

        // Button actions
        signUpBtn.addActionListener(e -> handleSignUp());

        loginBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }

    // SIGNUP LOGIC
    private void handleSignUp() {
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String role = roleBox.getSelectedItem().toString();

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();

            // FIXED SQL COLUMN NAME → full_name
            String query = role.equals("Student")
                    ? "INSERT INTO students(full_name, username, password) VALUES(?, ?, ?)"
                    : "INSERT INTO admins(full_name, username, password) VALUES(?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, fullName);
            stmt.setString(2, username);
            stmt.setString(3, password);

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Account Created Successfully!");

            new LoginFrame();
            dispose();

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "Username already exists!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!");
        }
    }

    public static void main(String[] args) {
        new SignUpFrame();
    }
}
