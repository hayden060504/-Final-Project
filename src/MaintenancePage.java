import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MaintenancePage extends JFrame{
	public MaintenancePage() {
		
		setTitle("Maintenance Page");
    	setSize(400, 300);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLayout(new GridBagLayout());
    	
    	GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        JButton checkButton = new JButton("排程排序");
    	checkButton.setPreferredSize(new Dimension(150, 50));
    	checkButton.setFont(new Font("Microsoft JhengHei", Font.BOLD, 15));
    	gbc.gridx = 0; //左上
        gbc.gridy = 0;
        add(checkButton, gbc);
        
        JButton reportButton = new JButton("");
    	reportButton.setPreferredSize(new Dimension(150, 50));
    	reportButton.setFont(new Font("Microsoft JhengHei", Font.BOLD, 15));
    	gbc.gridx = 1; //右上
        gbc.gridy = 0;
        add(reportButton, gbc);
        
        JButton mapButton = new JButton("Q&A");
    	mapButton.setPreferredSize(new Dimension(150, 50));
    	mapButton.setFont(new Font("Microsoft JhengHei", Font.BOLD, 15));
    	gbc.gridx = 0; //左下
        gbc.gridy = -1;
        add(mapButton, gbc);
        
        JButton qaButton = new JButton("維修排序");
    	qaButton.setPreferredSize(new Dimension(150, 50));
    	qaButton.setFont(new Font("Microsoft JhengHei", Font.BOLD, 15));
    	gbc.gridx = 1; //右下
        gbc.gridy = -1;
        add(qaButton, gbc);
        
        //設定維修員各介面的切換
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        mainPanel.add(new QASettingPanel(), "QASettingPanel"); //新增QA面板
        mainPanel.add(new SchedulePage(), "SchedulePage"); //新增待維修面板
        mainPanel.add(new MaintenanceAcceptPage(),"MaintenanceAcceptPage" );
        
        checkButton.addActionListener(e -> cardLayout.show(mainPanel, "SchedulePage"));
        reportButton.addActionListener(e -> cardLayout.show(mainPanel, "MaintenanceAcceptPage"));
        //mapButton.addActionListener(e -> new PriorityDetermine()); 這個應該用不到吧？ by蔡昕恩
        qaButton.addActionListener(e -> cardLayout.show(mainPanel, "QASettingPanel"));
        
        setLocationRelativeTo(null);
    	setVisible(true);

	}

}
