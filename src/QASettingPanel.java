
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class QASettingPanel extends JPanel{
	private JLabel title;
	
	
	
	
	public QASettingPanel() {
		
		setLayout(new BorderLayout());
		//Title set
		title = new JLabel("Q&A");
		title.setFont(new Font("Microsoft JhengHei",Font.BOLD,40));
		add(title, BorderLayout.NORTH);
		//
		JTextArea QADisplay = new JTextArea();
		JScrollPane QAScrollPane = new JScrollPane(QADisplay);
		add(QAScrollPane);
		//
		
	}
	
	
}
