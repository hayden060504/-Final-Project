import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class userPage extends JFrame {
	public userPage() {
		//基本設定跟user page 一樣
    	setTitle("User Page");
    	setSize(400, 300);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLayout(new GridBagLayout());
    	
    	GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        JButton checkButton = new JButton("進度查詢");
    	checkButton.setPreferredSize(new Dimension(150, 50));
    	checkButton.setFont(new Font("Arial", Font.BOLD, 15));
    	gbc.gridx = 0; //左上
        gbc.gridy = 0;
        add(checkButton, gbc);
        
        JButton reportButton = new JButton("回報");
    	reportButton.setPreferredSize(new Dimension(150, 50));
    	reportButton.setFont(new Font("Arial", Font.BOLD, 15));
    	gbc.gridx = 1; //右上
        gbc.gridy = 0;
        add(reportButton, gbc);
        
        JButton mapButton = new JButton("地圖");
    	mapButton.setPreferredSize(new Dimension(150, 50));
    	mapButton.setFont(new Font("Arial", Font.BOLD, 15));
    	gbc.gridx = 0; //左下
        gbc.gridy = -1;
        add(mapButton, gbc);
        
        JButton qaButton = new JButton("Q&A");
    	qaButton.setPreferredSize(new Dimension(150, 50));
    	qaButton.setFont(new Font("Arial", Font.BOLD, 15));
    	gbc.gridx = 1; //右下
        gbc.gridy = -1;
        add(qaButton, gbc);
        
        
        //按下按鈕後，跳到新的page
        checkButton.addActionListener(e -> new checkPage());
        reportButton.addActionListener(e -> new reportPage());
        mapButton.addActionListener(e -> new mapPage());
        qaButton.addActionListener(e -> new qaPage());
        
        setLocationRelativeTo(null);
    	setVisible(true);
	}
}