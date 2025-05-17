import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MaintenancePage extends JFrame{
	public MaintenancePage() {
		
		 SwingUtilities.invokeLater(() -> {
	           
	            JFrame frame = new JFrame("Maintenance");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setSize(400, 300);
	            frame.setLayout(new BorderLayout());

	            
	            JLabel titleLabel = new JLabel("Maintenance", SwingConstants.CENTER);
	            titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
	            titleLabel.setOpaque(true);
	            titleLabel.setBackground(new Color(255, 204, 204));
	            frame.add(titleLabel, BorderLayout.NORTH);

	            
	            JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));

	            JButton repairButton = new JButton("<html>待<br>維<br>修</html>");
	            JButton systemButton = new JButton("<html>操作<br>系統</html>");
	            JButton qaButton = new JButton("Q&A");

	            //改這裡，button點擊後到各個頁面
	            repairButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "你點了請維修"));
	            systemButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "你點了操作系統"));
	            qaButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "你點了 Q&A"));

	            
	            panel.add(repairButton);
	            panel.add(systemButton);
	            panel.add(qaButton);

	            frame.add(panel, BorderLayout.CENTER);

	            
	            frame.setVisible(true);
	        });
	    }
	}
