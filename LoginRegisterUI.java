
import java.sql.*;
import javax.swing.*;

public class LoginRegisterUI extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public LoginRegisterUI() {
        setTitle("Login / Register");
        setSize(300, 200);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 20, 80, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 150, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 60, 80, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 60, 150, 25);
        add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(30, 110, 100, 25);
        add(loginBtn);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(150, 110, 100, 25);
        add(registerBtn);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> register());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    void login() {
        try {
            Connection con = DBConnection.connect();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?");

            ps.setString(1, usernameField.getText());
            ps.setString(2, new String(passwordField.getPassword()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                new DashboardUI(rs.getInt("user_id"));
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void register() {
        try {
            Connection con = DBConnection.connect();

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users(username,password) VALUES(?,?)");

            ps.setString(1, usernameField.getText());
            ps.setString(2, new String(passwordField.getPassword()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registered!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
