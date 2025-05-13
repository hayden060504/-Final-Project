
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;

public class ReportPage extends JPanel{
	private JPanel leftPanel, rightPanel;
	private JComboBox<String> bigPlace; //報修類型的大範圍
	private JComboBox<String> type; //報修類型
	private JTextArea describe; //描述輸入
	private JTextField place; //地點輸入
	private JButton summitBtn; //送出按鈕
	
	public ReportPage() {
		
		setLayout(new BorderLayout());
		//圖片上傳
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("上傳照片");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "圖片檔案 (JPG, PNG)", "jpg", "jpeg", "png"));
        //左邊面板
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(1,4,0,0));
		//大區域決定
		bigPlace = new JComboBox<String>();
		bigPlace.addItem("請選擇");
		bigPlace.addItem("山下校園");
		bigPlace.addItem("山上校園");
		bigPlace.addItem("山上宿舍");
		bigPlace.addItem("山下宿舍");
		leftPanel.add(bigPlace);
		//報修類型
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
		//地點描述
		place = new JTextField();
		
		
		//具體狀況描述
		describe = new JTextArea();
		
		//送出按鈕
		summitBtn = new JButton("送出");
		add(leftPanel,BorderLayout.WEST);
	}
	
}
