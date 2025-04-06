import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class startPage extends JFrame{
    public startPage() {
    	setTitle("Start Page");
    	setSize(400, 300);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	//設定成絕對定位
    	setLayout(new GridBagLayout()); 
    	
    	GridBagConstraints gbc = new GridBagConstraints();
    	//設定上下左右間距
        gbc.insets = new Insets(10, 10, 10, 10);
        //讓按鈕填滿可用空間
        gbc.fill = GridBagConstraints.BOTH;
        //讓按鈕隨視窗變動(x軸變大變小比例1:1)
        gbc.weightx = 1.0;
        //讓按鈕隨視窗變動(y軸變大變小比例1:1)
        gbc.weighty = 1.0;
    	
    	JButton userButton = new JButton("User Page");
    	//設定初始大小和字體
    	userButton.setPreferredSize(new Dimension(150, 50));
    	userButton.setFont(new Font("Arial", Font.BOLD, 15));
    	gbc.gridx = 0; //第一個
        gbc.gridy = 0;
        add(userButton, gbc);
        
    	JButton maintenanceButton = new JButton("Maintenance Page");
    	//設定初始大小和字體
    	maintenanceButton.setPreferredSize(new Dimension(150, 50));
    	maintenanceButton.setFont(new Font("Arial", Font.BOLD, 15));
    	gbc.gridx = 1; //第一個
        gbc.gridy = 0;
        add(maintenanceButton, gbc);
           	
    	userButton.addActionListener(e -> new userPage());
    	maintenanceButton.addActionListener(e -> new maintenancePage());  	
    	
    	setLocationRelativeTo(null);
    	setVisible(true);
    	
    	
    }
    
}


