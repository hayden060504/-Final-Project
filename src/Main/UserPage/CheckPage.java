package Main.UserPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CheckPage extends JPanel {

    private JTextField idInput;  // 用戶輸入報修 ID 的欄位
    private JTextArea resultArea;  // 顯示查詢結果的區域

    // 資料庫連線參數
    private static final String DB_URL = "jdbc:mysql://140.119.19.73:3315/TG09";
    private static final String DB_USER = "TG09";
    private static final String DB_PASSWORD = "hGykqi";

    public CheckPage() {
        setLayout(new BorderLayout());

        // 建立主面板，使用 BoxLayout 讓元件垂直排列
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30)); // 整體上下左右留白

        // ===== 標題 =====
        JLabel titleLabel = new JLabel("查詢歷史紀錄");
        titleLabel.setFont(new Font("Microsoft JhengHei", Font.BOLD, 20));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT); // 置中
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 標題下方留白

        // ===== 中間輸入區塊 =====
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel idLabel = new JLabel("輸入回報編號:");
        idLabel.setAlignmentX(CENTER_ALIGNMENT);

        idInput = new JTextField(15);
        idInput.setMaximumSize(new Dimension(200, 25)); // 設定最大寬度
        idInput.setAlignmentX(CENTER_ALIGNMENT);

        JButton checkButton = new JButton("查詢");
        checkButton.setAlignmentX(CENTER_ALIGNMENT);

        // 將元件與間距加進 inputPanel
        inputPanel.add(idLabel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(idInput);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(checkButton);

        mainPanel.add(inputPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 輸入區下方留白

        // ===== 顯示結果區域 =====
        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 結果區下方留白

        // ===== 返回按鈕區域（靠左）=====
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton returnBtn = new JButton("返回");
        bottomPanel.add(returnBtn);
        mainPanel.add(bottomPanel);

        // 加入主面板到 CheckPage
        add(mainPanel, BorderLayout.CENTER);

        // ===== 按鈕事件處理 =====
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reportId = idInput.getText().trim();
                String result = getReportStatusFromDB(reportId);
                resultArea.setText(result);
            }
        });
        
        returnBtn.addActionListener(e-> UserPage.getCardLayout().show(UserPage.getMainPanel(), "使用者頁面"));
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int x = 10;
                int y = 10;
                //維持按鈕的位置
                returnBtn.setLocation(x, y);
                
                //持續根據視窗大小改變大小
                mainPanel.setBounds(0,0,getWidth(),getHeight());
            }
        });
    }

    // 外部可呼叫查詢功能
    public void performQuery(String reportId) {
        idInput.setText(reportId);
        String result = getReportStatusFromDB(reportId);
        resultArea.setText(result);
    }

    // 資料庫查詢報修進度
    private String getReportStatusFromDB(String reportId) {
        String result = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 建立 SQL 查詢
            String query = "SELECT * FROM reports WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, reportId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                String description_situation = rs.getString("description_situation");
                String description_place = rs.getString("description_place");
                String created_at = rs.getString("created_at");

                // 更新查詢次數
                PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE reports SET query_count = query_count + 1 WHERE id = ?");
                updateStmt.setString(1, reportId);
                updateStmt.executeUpdate();

                result = "Report ID: " + reportId + "\n" +
                        "ID: " + id + "\n" +
                        "Description_Situation: " + description_situation + "\n" +
                        "Description_Place: " + description_place + "\n" +
                        "Created_At: " + created_at + "\n";

                // 更新熱門查詢
                UserPage.updateTopQueriedReports();
            } else {
                result = "No report found for ID: " + reportId;
            }

        } catch (SQLException e) {
            result = "Error: " + e.getMessage();
        }

        return result;
    }
}
