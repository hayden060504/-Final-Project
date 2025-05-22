package Main.StartPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import Main.UserPage.UserPage;
import Main.MaintenancePage.MaintenanceAcceptPage;

public class LoginDialog extends JDialog {
    private boolean authenticated = false;
    private int maintenanceId = -1;

    public LoginDialog(Frame parent, String role) {
        super(parent, "Login - " + role, true);
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("使用者名稱:");
        JTextField userField = new JTextField(12);
        JLabel passLabel = new JLabel("密碼:");
        JPasswordField passField = new JPasswordField(12);

        JButton loginButton = new JButton("登入");
        JButton cancelButton = new JButton("取消");

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
        
        LoginDialog login = new LoginDialog(null, "user"); // 或 "admin"
        login.setVisible(true);

        if (login.isAuthenticated()) {
            new UserPage(); // 🔥 登入成功後跳轉主頁
        }

    }

    private boolean authenticate(String role, String username, String password) {
        String url = "jdbc:mysql://140.119.19.73:3315/TG09";
        String dbUser = "TG09";
        String dbPassword = "hGykqi";

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
        	String sql = "SELECT * FROM login WHERE username = ? AND password = ? AND role = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            ResultSet rs = stmt.executeQuery();
            //return rs.next(); // 有結果代表驗證成功
            if (rs.next()) {
                maintenanceId = rs.getInt("id"); 
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
    
    public int getUserId() {
        return maintenanceId;
    }
}
