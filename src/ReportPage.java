
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.io.File;

public class ReportPage extends JPanel{
	private JPanel leftPanel, rightPanel;
	private JComboBox<String> bigType; //報修類型的大範圍
	private JComboBox<String> type; //報修類型
	private JTextArea describe; //描述輸入
	private JTextField place; //地點輸入
	private JButton summit; //送出按鈕
	
	public ReportPage() {
		
		setLayout(new BorderLayout());
		
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("上傳照片");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "圖片檔案 (JPG, PNG)", "jpg", "jpeg", "png"));
		
		bigType = new JComboBox<String>();
		bigType.addItem("請選擇");
		bigType.addItem("山下校園");
		bigType.addItem("山上校園");
		bigType.addItem("山上宿舍");
		bigType.addItem("山下宿舍");
		leftPanel.add(bigType);
		
		type = new JComboBox<String>();
		type.addItem("請選擇");
		type.addItem("座椅損壞");
		type.addItem("水溝蓋鬆動或遺失");
		type.addItem("水龍頭損壞");
		type.addItem("插座/電燈不通電");
		type.addItem("門損壞");
		type.addItem("窗戶破裂");
		type.addItem("電梯異常");
		type.addItem("感應門異常、損壞");
		type.addItem("飲水機異常");
		type.addItem("桌椅損壞");
		type.addItem("網路中斷");
		type.addItem("垃圾桶破損");
		type.addItem("冷氣/電風扇故障");
		type.addItem("洗衣機/烘衣機無法運作");
		type.addItem("燈具不亮");
		leftPanel.add(type);
		
		add(leftPanel,BorderLayout.WEST);
	}
	
}
