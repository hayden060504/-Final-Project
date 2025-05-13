
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
	String server = "jdbc:mysql://140.119.19.73:3315/";
	String database = "113306020";
	String username = "113306020";
	String password = "pzwgt";
	String url = server + database + "?useSSL=false";
	
	private JPanel leftPanel, rightPanel;
	private JComboBox<String> locationCombo; //報修類型的大範圍
	private JComboBox<String> categoryCombo; //報修類型
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
        locationCombo = new JComboBox<String>();
        locationCombo.addItem("請選擇");
        locationCombo.addItem("山下校園");
        locationCombo.addItem("山上校園");
        locationCombo.addItem("山上宿舍");
		locationCombo.addItem("山下宿舍");
		leftPanel.add(locationCombo);
		//報修類型
		categoryCombo = new JComboBox<String>();
		categoryCombo.addItem("請選擇");
		categoryCombo.addItem("座椅損壞");
		categoryCombo.addItem("水溝蓋鬆動或遺失");
		categoryCombo.addItem("水龍頭損壞");
		categoryCombo.addItem("插座/電燈不通電");
		categoryCombo.addItem("門損壞");
		categoryCombo.addItem("窗戶破裂");
		categoryCombo.addItem("電梯異常");
		categoryCombo.addItem("感應門異常、損壞");
		categoryCombo.addItem("飲水機異常");
		categoryCombo.addItem("桌椅損壞");
		categoryCombo.addItem("網路中斷");
		categoryCombo.addItem("垃圾桶破損");
		categoryCombo.addItem("冷氣/電風扇故障");
		categoryCombo.addItem("洗衣機/烘衣機無法運作");
		categoryCombo.addItem("燈具不亮");
		leftPanel.add(categoryCombo);
		//地點描述
		place = new JTextField();
		
		
		//具體狀況描述
		describe = new JTextArea();
		
		//送出按鈕
		summitBtn = new JButton("送出");
		add(leftPanel,BorderLayout.WEST);
	}
	
	public void ReportForm() {
		setTitle("報修系統");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        categoryCombo = new JComboBox<>();
        locationCombo = new JComboBox<>();
        descriptionArea = new JTextArea(5, 20);
        submitButton = new JButton("送出報修");

        // 抓資料填進下拉選單
        loadComboBox(categoryCombo, "categories");
        loadComboBox(locationCombo, "locations");

	}
}
