import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.File;

public class ReportPage extends JPanel {
	//我用成自己的DATABASE了，之後改
	String server = "jdbc:mysql://140.119.19.73:3315/";
	String database = "113306020";
	String username = "113306020";
	String password = "pzwgt";
	String url = server + database + "?useSSL=false&serverTimezone=UTC";

	private JPanel leftPanel, rightPanel;
	private JComboBox<String> locationCombo; // 學校地點
	private JComboBox<String> categoryCombo; // 報修類型
	private JTextArea situation_description; // 描述輸入
	private JTextArea place_description; // 地點輸入
	private JButton submitBtn; // 送出按鈕

	public ReportPage() {

		setLayout(new BorderLayout());
		// 圖片上傳
		//之後想改成傳不需要轉整個檔案的
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("上傳照片");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.addChoosableFileFilter(
				new javax.swing.filechooser.FileNameExtensionFilter("圖片檔案 (JPG, PNG)", "jpg", "jpeg", "png"));
		// 左邊下拉選單區
		leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(5, 1, 5, 5));
		// 報修類型 跟 地點 表單
		categoryCombo = new JComboBox<>();
		locationCombo = new JComboBox<>();

		leftPanel.add(new JLabel("報修類別:"));
		leftPanel.add(categoryCombo);
		leftPanel.add(new JLabel("故障地點:"));
		leftPanel.add(locationCombo);

		// 右邊
		JPanel rightPanel = new JPanel(new BorderLayout());
		situation_description = new JTextArea(3, 20);
		rightPanel.add(new JLabel("問題描述："), BorderLayout.NORTH);
		rightPanel.add(new JScrollPane(situation_description), BorderLayout.CENTER);

		place_description = new JTextArea(3, 20);
		rightPanel.add(new JLabel("地點描述："), BorderLayout.NORTH);
		rightPanel.add(new JScrollPane(place_description), BorderLayout.CENTER);

		submitBtn = new JButton("送出");
		submitBtn.addActionListener(e -> insertReport());
		rightPanel.add(submitBtn, BorderLayout.SOUTH);

		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);

		// 大區域決定
		locationCombo.addItem("請選擇");
		locationCombo.addItem("山下校園");
		locationCombo.addItem("山上校園");
		locationCombo.addItem("山上宿舍");
		locationCombo.addItem("山下宿舍");
		leftPanel.add(locationCombo);
		// 報修類型
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

	}

	private void loadComboBox(JComboBox<ComboItem> comboBox, String table) {
		try (Connection conn = DriverManager.getConnection(url, username, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT id, name FROM " + table)) {
			while (rs.next()) {
				comboBox.addItem(new ComboItem(rs.getInt("id"), rs.getString("name")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void insertReport() {
		ComboItem selectedCategory = (ComboItem) categoryCombo.getSelectedItem();
		ComboItem selectedLocation = (ComboItem) locationCombo.getSelectedItem();
		String situation_description_upload = situation_description.getText();
		String place_description_upload = place_description.getText();

		if (selectedCategory == null || selectedLocation == null || situation_description_upload.isBlank()
				|| place_description_upload.isBlank()) {
			JOptionPane.showMessageDialog(this, "請完整填寫所有欄位");
			return;
		}

		String sql = "INSERT INTO reports (description_place, description_situation, category_id, location_id) VALUES(?, ?, ?, ?)";

		try(Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, place_description.getText());
			ps.setString(2, situation_description.getText());
			ps.setInt(3, selectedCategory.getId());
			ps.setInt(4, selectedLocation.getId());

			ps.executeUpdate();
			JOptionPane.showMessageDialog(this, "送出成功");

			place_description.setText("");
			situation_description.setText("");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "送出失敗" + e.getMessage());
		}

	}

	static class ComboItem {
		private int id;
		private String name;

		public ComboItem(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}

}
