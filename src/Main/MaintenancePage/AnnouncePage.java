package Main.MaintenancePage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Main.Style;

public class AnnouncePage extends JPanel implements Style{

	private JTextField titleField;
	private JTextArea contentArea;
	private JButton submitBtn;

	public AnnouncePage() {
		
		setLayout(new BorderLayout());

		JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        add(layeredPane);
		
		// 建立主體容器，垂直BoxLayout置中
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); // 上下左右邊距
		mainPanel.setBackground(backColor);

		// 標題Label與輸入框
		JLabel titleLabel = new JLabel("標題：");
		titleLabel.setFont(contentFont);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleField = new JTextField(20);
		titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // 限高
		mainPanel.add(titleLabel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 5))); // 間隔
		mainPanel.add(titleField);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); // 間隔

		// 內文Label與輸入框
		JLabel contentLabel = new JLabel("內文：");
		contentLabel.setFont(contentFont);
		contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentArea = new JTextArea(5, 20);
		contentArea.setLineWrap(true);
		contentArea.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(contentArea);
		scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
		mainPanel.add(contentLabel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 5))); // 間隔
		mainPanel.add(scrollPane);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 間隔

		// 提交按鈕
		submitBtn = new JButton("發布");
		submitBtn.setFont(contentFont);
		submitBtn.setBackground(btnBackColor);
		submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(submitBtn);

		// 將mainPanel加到中間
		layeredPane.add(mainPanel, BorderLayout.CENTER, JLayeredPane.DEFAULT_LAYER);
				
		//
		JButton returnBtn = new JButton("返回");
		returnBtn.setFont(contentFont);
		returnBtn.setBackground(btnBackColor);
		returnBtn.setSize(80, 30);
        layeredPane.add(returnBtn, JLayeredPane.MODAL_LAYER);
        returnBtn.setLocation(10, 360); 
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //維持按鈕的位置
                returnBtn.setLocation(10, getHeight()-40);
                
                //持續根據視窗大小改變大小
                mainPanel.setBounds(0,0,getWidth(),getHeight());
            }
        });
        
        returnBtn.addActionListener(e-> MaintenancePage.getCardLayout().show(MaintenancePage.getMainPanel(), "MaintenancePage"));
		
		submitBtn.addActionListener(e-> submit(titleField.getText(),contentArea.getText()));
	}

	private void submit(String title, String content) {
		String url = "jdbc:mysql://140.119.19.73:3315/TG09?useSSL=false";
		String username = "TG09"; // database username
		String password = "hGykqi"; // database password

		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			System.out.println("DB Connected");

			Statement stat = conn.createStatement();
			String query = String.format("INSERT INTO `Announce` (Title,Content) VALUES ('%s','%s')", title, content);

			stat.execute(query);

			stat.close();

			System.out.println("Data submited successfully.");
			
			// 呼叫這段代碼即可
			JOptionPane.showMessageDialog(null, "您的問題已成功發布！", "發布成功", JOptionPane.INFORMATION_MESSAGE);
			MaintenancePage.getCardLayout().show(MaintenancePage.getMainPanel(), "MaintenancePage");
			
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
}
