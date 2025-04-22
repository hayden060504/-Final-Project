import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;

public class maintenancePage extends JFrame{
	public maintenancePage() {
		
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
    	checkButton.setFont(new Font("Arial", Font.BOLD, 15));
    	gbc.gridx = 0; //左上
        gbc.gridy = 0;
        add(checkButton, gbc);
        
        JButton reportButton = new JButton("");
    	reportButton.setPreferredSize(new Dimension(150, 50));
    	reportButton.setFont(new Font("Arial", Font.BOLD, 15));
    	gbc.gridx = 1; //右上
        gbc.gridy = 0;
        add(reportButton, gbc);
        
        JButton mapButton = new JButton("Q&A");
    	mapButton.setPreferredSize(new Dimension(150, 50));
    	mapButton.setFont(new Font("Arial", Font.BOLD, 15));
    	gbc.gridx = 0; //左下
        gbc.gridy = -1;
        add(mapButton, gbc);
        
        JButton qaButton = new JButton("維修排序");
    	qaButton.setPreferredSize(new Dimension(150, 50));
    	qaButton.setFont(new Font("Arial", Font.BOLD, 15));
    	gbc.gridx = 1; //右下
        gbc.gridy = -1;
        add(qaButton, gbc);
        
        checkButton.addActionListener(e -> new scheduleDisplay());
        reportButton.addActionListener(e -> new maintenanceAccept());
        mapButton.addActionListener(e -> new priorityDetermine());
        qaButton.addActionListener(e -> new QA());
        
        setLocationRelativeTo(null);
    	setVisible(true);

	}

}
