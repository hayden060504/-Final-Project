package Main.StartPage;

import javax.swing.*;

import Main.UserPage.UserPage;
import Main.MaintenancePage.MaintenancePage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.util.Arrays;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class StartPage extends JFrame {
	public StartPage() {

		// System.out.println(new Main.UserPage.UserPage()); // 測試用

		setTitle("報修系統");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 設定成絕對定位
		setLayout(new GridBagLayout());

		getContentPane().setBackground(new Color(250, 240, 230));

		GridBagConstraints gbc = new GridBagConstraints();
		// 設定上下左右間距
		gbc.insets = new Insets(10, 10, 10, 10);
		// 讓按鈕填滿可用空間
		gbc.fill = GridBagConstraints.BOTH;
		// 讓按鈕隨視窗變動(x軸變大變小比例1:1)
		gbc.weightx = 1.0;
		// 讓按鈕隨視窗變動(y軸變大變小比例1:1)
		gbc.weighty = 1.0;


		JButton userButton = new JButton("使用者");

		styleMilkTeaButton(userButton);
		gbc.gridx = 0; // 第一個
		gbc.gridy = 0;
		add(userButton, gbc);
		userButton.addActionListener(e -> openLogin("user"));


		JButton maintenanceButton = new JButton("維修人員");
		styleMilkTeaButton(maintenanceButton);
		gbc.gridx = 1; // 第一個
		gbc.gridy = 0;
		add(maintenanceButton, gbc);
		maintenanceButton.addActionListener(e -> openLogin("maintenance"));

		userButton.addActionListener(e -> {
			new UserPage();
			dispose();
		});

		maintenanceButton.addActionListener(e -> {
			LoginDialog login = new LoginDialog(this, "maintenance");
			login.setVisible(true);
			if (login.isAuthenticated()) {
				dispose();
				new MaintenancePage(login.getUserId());
			}
		});

		setLocationRelativeTo(null);
		setVisible(true);
		
		//將兩個btn放進List中 一起調大小
		List<JButton> allButtons = Arrays.asList(userButton, maintenanceButton);
	    	addComponentListener(new ComponentAdapter() {
	    		public void componentResized(ComponentEvent e) {
	                int width = getWidth();
	                int fontSize = Math.max(14, width / 40); // 依照視窗寬度決定字體大小
	                Font dynamicFont = new Font("Noto Sans TC", Font.BOLD, fontSize);
	                for (JButton btn : allButtons) {
	                    btn.setFont(dynamicFont);
	                }
	            }
	        });

	}

	private static void openLogin(String role) {
		LoginDialog login = new LoginDialog(null, role);

		if (login.isAuthenticated()) {
			if (role.equals("user")) {
				new UserPage(); // 開啟使用者頁面
			} else if (role.equals("maintenance")) {
				int id = login.getUserId();
				new MaintenancePage(id); // 假設你有維修人員頁面
			}
		}
	}

	private void styleMilkTeaButton(JButton button) {
		button.setFont(new Font("Noto Sans TC", Font.BOLD, 16));
		button.setBackground(new Color(210, 180, 140));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createLineBorder(new Color(190, 140, 100), 2));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public static JPanel getMainPanel() {
		// TODO Auto-generated method stub
		return getMainPanel();
	}

	

}
