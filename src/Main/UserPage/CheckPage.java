package Main.UserPage;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CheckPage extends JPanel {

    private JTextField idInput; // 用戶輸入報修ID的欄位
    private JTextArea resultArea;  // 顯示查詢結果的區域

    // 資料庫連線參數
    private static final String DB_URL = "jdbc:mysql://140.119.19.73:3315/TG09"; 
    private static final String DB_USER = "TG09"; 
    private static final String DB_PASSWORD = "hGykqi"; 
    //設計整個查詢頁面的 UI
    public CheckPage() {
    	
        setLayout(new BorderLayout());
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null); // 自訂 layout
        add(layeredPane);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBounds(0,0,getWidth(),getHeight());
        
        // 標題文字設定
        JLabel titleLabel = new JLabel("Check Maintenance Progress");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        // 中間輸入區域
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel idLabel = new JLabel("Enter Report ID:");
        idInput = new JTextField(15);
        JButton checkButton = new JButton("Check");
        // 將元件加到輸入區塊
        inputPanel.add(idLabel);
        inputPanel.add(idInput);
        inputPanel.add(checkButton);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        // 建立顯示結果的文字區域（不可編輯）
        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);// 自動換行
        resultArea.setWrapStyleWord(true);// 單詞換行
        JScrollPane scrollPane = new JScrollPane(resultArea);// 可捲動
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
          
        layeredPane.add(mainPanel,JLayeredPane.DEFAULT_LAYER);
        
        JButton returnBtn = new JButton("返回");
        returnBtn.setSize(80, 30);
        layeredPane.add(returnBtn, JLayeredPane.MODAL_LAYER);
        returnBtn.setLocation(10, 360); 
        
        // 查詢按鈕被點擊時執行的動作
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reportId = idInput.getText().trim();
                String result = getReportStatusFromDB(reportId);
                resultArea.setText(result);
            }
        });
        
        returnBtn.addActionListener(e-> UserPage.getCardLayout().show(UserPage.getMainPanel(), "UserPage"));
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // 更新返回按鈕位置
                int x = 10;
                int y = 10;
                returnBtn.setLocation(x, y);
                
                mainPanel.setBounds(0,0,getWidth(),getHeight());
            }
        });
    }
    

    // 從資料庫查詢報修狀態
    private String getReportStatusFromDB(String reportId) {
        String result = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        	 // 建立 SQL 查詢指令
            String query = "SELECT * FROM reports WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, reportId); // 將輸入的報修 ID 傳入查詢條件
            // 執行查詢
            ResultSet rs = stmt.executeQuery();
            // 若有查到結果
            if (rs.next()) {
                String status = rs.getString("status");
                String updated = rs.getString("last_updated");
                String location = rs.getString("location");
                String tech = rs.getString("technician");
               
                result = "Report ID: " + reportId + "\n" +
                         "Status: " + status + "\n" +
                         "Last Updated: " + updated + "\n" +
                         "Location: " + location + "\n" +
                         "Technician: " + tech;
            } else {
            	// 沒查到任何資料
                result = "No report found for ID: " + reportId;
            }

        } catch (SQLException e) {
        	// 錯誤處理：顯示例外訊息
            result = "Error: " + e.getMessage();
        }

        return result;
    }
}
