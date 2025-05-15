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
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        JPanel MaintenancePage = new JPanel();
        MaintenancePage.setLayout(new GridBagLayout());
    	
    	GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        JButton checkButton = new JButton("排程排序");
    	checkButton.setPreferredSize(new Dimension(150, 50));
    	checkButton.setFont(new Font("Microsoft JhengHei", Font.BOLD, 15));
    	gbc.gridx = 0;
        gbc.gridy = 0;
        MaintenancePage.add(checkButton, gbc);
        
        JButton reportButton = new JButton("");
    	reportButton.setPreferredSize(new Dimension(150, 50));
    	reportButton.setFont(new Font("Microsoft JhengHei", Font.BOLD, 15));
    	gbc.gridx = 1;
        gbc.gridy = 0;
        MaintenancePage.add(reportButton, gbc);
        
        JButton mapButton = new JButton("");
    	mapButton.setPreferredSize(new Dimension(150, 50));
    	mapButton.setFont(new Font("Microsoft JhengHei", Font.BOLD, 15));
    	gbc.gridx = 0;
        gbc.gridy = -1;
        MaintenancePage.add(mapButton, gbc);
        
        JButton qaButton = new JButton("Q&A");
    	qaButton.setPreferredSize(new Dimension(150, 50));
    	qaButton.setFont(new Font("Microsoft JhengHei", Font.BOLD, 15));
    	gbc.gridx = 1;
        gbc.gridy = -1;
        MaintenancePage.add(qaButton, gbc);
        
   
        mainPanel.add(MaintenancePage,"MaintenancePage");
        mainPanel.add(new QASettingPanel(), "QASettingPanel"); 
        mainPanel.add(new SchedulePage(), "SchedulePage");
        mainPanel.add(new MaintenanceAcceptPage(),"MaintenanceAcceptPage" );
        add(mainPanel);
        
        checkButton.addActionListener(e -> cardLayout.show(mainPanel, "SchedulePage"));
        reportButton.addActionListener(e -> cardLayout.show(mainPanel, "MaintenanceAcceptPage"));
        //mapButton.addActionListener(e -> new PriorityDetermine()); 這個應該用不到吧？ by蔡昕恩
        qaButton.addActionListener(e -> cardLayout.show(mainPanel, "QASettingPanel"));
        
        cardLayout.show(mainPanel,"MaintenancePage");
        setLocationRelativeTo(null);
    	setVisible(true);

	}

}

	}

}
