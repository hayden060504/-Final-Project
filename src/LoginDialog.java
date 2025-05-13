import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginDialog extends JDialog {
    private boolean authenticated = false;

    public LoginDialog(Frame parent, String role) {
        super(parent, "Login - " + role, true);
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (authenticate(role, username, password)) {
                authenticated = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        add(userLabel); add(userField);
        add(passLabel); add(passField);
        add(loginButton); add(cancelButton);

        pack();
        setLocationRelativeTo(parent);
    }

    private boolean authenticate(String role, String username, String password) {
        String url = "jdbc:mysql://localhost:3306/your_database";
        String dbUser = "your_db_user";
        String dbPassword = "your_db_password";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
        	String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // 有結果代表驗證成功
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
