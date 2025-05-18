package Main.MaintenancePage;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MaintenancePage extends JFrame {
	private static CardLayout cardLayout;
	private static JPanel mainPanel;
	
	public MaintenancePage() {

			//視窗的設定
			setTitle("Maintenance");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(400, 300);
			setLocationRelativeTo(null);
			//主要面板，用於切換畫面用(不是創建新視窗!!!!!!!!!!!!!!!!!!!)
			cardLayout = new CardLayout();
			mainPanel = new JPanel(cardLayout);
			//創建維修員Panel
			JPanel maintenancePanel = new JPanel();
			maintenancePanel.setLayout(new BorderLayout());
			//上方的title
			JLabel titleLabel = new JLabel("Maintenance", SwingConstants.CENTER);
			titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
			titleLabel.setOpaque(true);
			titleLabel.setBackground(new Color(255, 204, 204));
			maintenancePanel.add(titleLabel, BorderLayout.NORTH);
			//下方的三個按鈕和乘載的Panel
			JPanel downPanel = new JPanel(new GridLayout(1, 3, 10, 10));

			JButton repairButton = new JButton("<html>待<br>維<br>修</html>");
			JButton systemButton = new JButton("<html>接案<br>系統</html>");
			JButton qaButton = new JButton("Q&A");

			downPanel.add(repairButton);
			downPanel.add(systemButton);
			downPanel.add(qaButton);
			
			maintenancePanel.add(downPanel, BorderLayout.CENTER);

			// 在同一個視窗內，改畫面，不要再跳一個新視窗了
			// 不要再用new那個class了
			//創建每個畫面，並加入mainPanel
			mainPanel.add(maintenancePanel,"MaintenancePage");
			mainPanel.add(new SchedulePage(),"SchedulePage");
			mainPanel.add(new MaintenanceAcceptPage(),"MaintenanceAcceptPage");
			mainPanel.add(new QAPageForMaintenance(),"QAPageForMaintenance");
			
			//設定切換畫面
			repairButton.addActionListener(e -> {cardLayout.show(mainPanel, "SchedulePage");
												 setTitle("Q&A");}); //設置title
			systemButton.addActionListener(e -> cardLayout.show(mainPanel, "MaintenanceAcceptPage"));
			qaButton.addActionListener(e -> cardLayout.show(mainPanel, "QAPageForMaintenance"));
			
			//加到JFrame
			add(mainPanel);
			
			//設定初始畫面
			cardLayout.show(mainPanel, "MaintenancePage");
			
			setVisible(true);
	}
	//獲得CardLayout，別的畫面的跳回按鍵用這兩個
	/*e.g:
	 *MaintenancePage.getCardLayout().show(MaintenancePage.getMainPanel(),"你想要的畫面");
	 *根據上方cardLayout建立時後面的String去打在你想要的畫面
	 */
	public static CardLayout getCardLayout() {
		return cardLayout;
	}
	public static JPanel getMainPanel() {
		return mainPanel;
	}
}
