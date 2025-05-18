package Main.MaintenancePage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MaintenanceAcceptPage extends JPanel {
	//連接資料庫
	String server = "jdbc:mysql://140.119.19.73:3315/";
	String database = "TG09";
	String username = "TG09";
	String password = "hGykqi";
	String url = server + database + "?useSSL=false&serverTimezone=UTC";
    
	private JPanel reportListPanel;
	private int maintenanceId;
	
	public MaintenanceAcceptPage(int maintenanceId) {
    	this.maintenanceId = maintenanceId;
		
		setLayout(new BorderLayout());
    	
    	//設定標題(最上面)
    	JLabel title = new JLabel("接案系統", SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.BOLD, 20));
		add(title, BorderLayout.NORTH);

		reportListPanel = new JPanel();
        reportListPanel.setLayout(new BoxLayout(reportListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(reportListPanel);
        add(scrollPane, BorderLayout.CENTER);
        
        //載入未接受的案件
        loadUnacceptedReports(); 
    }

	//載入尚未被接受的報修案件
    private void loadUnacceptedReports() {
        reportListPanel.removeAll();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            //排除已經在accept cases裡面的
        	String sql = """
                SELECT r.id, r.description_place, r.description_situation, 
                       l.name AS location_name, c.name AS category_name
                FROM reports r
                JOIN location l ON r.location_id = l.id
                JOIN categories c ON r.category_id = c.id
                WHERE r.id NOT IN (SELECT report_id FROM accepted_cases)
                ORDER BY r.id DESC
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int reportId = rs.getInt("id");
                String place = rs.getString("description_place");
                String situation = rs.getString("description_situation");
                String location = rs.getString("location_name");
                String category = rs.getString("category_name");

                //每一個未接受的案件都建一條
                JPanel reportPanel = new JPanel(new BorderLayout());
                reportPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                JTextArea content = new JTextArea(
                    "案件 ID: " + reportId + "\n" +
                    "地點類別: " + location + "\n" +
                    "報修類型: " + category + "\n" +
                    "地點描述: " + place + "\n" +
                    "狀況描述: " + situation
                );
                content.setEditable(false);
				content.setLineWrap(true);
				content.setWrapStyleWord(true);
				reportPanel.add(content, BorderLayout.CENTER);

                JButton acceptButton = new JButton("接受");
                acceptButton.addActionListener(e -> acceptReport(reportId));
                reportPanel.add(acceptButton, BorderLayout.EAST);

                reportListPanel.add(reportPanel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //按接受之後刷新葉面
        revalidate();
        repaint();
    }

    //實際執行寫入 accepted_cases 資料表
    private void acceptReport(int reportId) {
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "INSERT INTO accepted_cases (report_id, maintenance_id, accepted_at) VALUES (?, ?, NOW())";
			PreparedStatement ps = conn.prepareStatement(sql);
			//利用maintenanceid記錄在資料表
			ps.setInt(1, reportId);
			ps.setInt(2, maintenanceId);
			ps.executeUpdate();

			JOptionPane.showMessageDialog(this, "成功接案！");
			//接案後刷新列表
			loadUnacceptedReports(); 

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "接案失敗：" + e.getMessage());
		}
	}
}
