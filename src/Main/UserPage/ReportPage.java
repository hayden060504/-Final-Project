package Main.UserPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import Main.Style;

public class ReportPage extends JPanel implements Style{
	String server = "jdbc:mysql://140.119.19.73:3315/";
	String database = "TG09";
	String username = "TG09";
	String password = "hGykqi";
	String url = server + database + "?useSSL=false&serverTimezone=UTC";
	private static final long serialVersionUID = 1L;

	private JPanel mainPanel, leftPanel, rightPanel;
	private JLabel title, placeLabel, situationLabel;
	private JLabel reportStyle, placeStyle;
	private JComboBox<ComboItem> locationCombo; // 學校地點
	private JComboBox<ComboItem> categoryCombo; // 報修類型
	private JTextArea situation_description; // 描述輸入
	private JTextField place_description; // 地點輸入
	private JButton submitBtn, chooseFileBtn; //按鈕
	private JButton returnBtn;
	private JLabel selectedFileLabel; // 顯示選擇的檔案
	private File selectedFile;

	private Color inputBackColor = new Color(255, 235, 200); // 主背景（左邊）
	private Color backColor = new Color(253, 241, 220); // 輸入框背景（右邊）
	private Color btnBackColor = new Color(253, 225, 191); // 按鈕背景
	private Color inputTextColor = Color.DARK_GRAY; // 輸入框文字

	private Font insideWordFont = new Font("Microsoft JhengHei", Font.PLAIN, 20); // 文字大小
	private Font titleFont = new Font("Microsoft JhengHei", Font.BOLD, 30); // 標題大小

	public ReportPage() {

		// 主頁面設計
		setLayout(new BorderLayout());
		setBackground(backColor);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// 初始化mainPanel
		mainPanel = new JPanel(new BorderLayout());

		// 左邊下拉選單區
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.setBackground(backColor);

		// 圖片上傳
		// 之後想改成傳不需要轉整個檔案的
		chooseFileBtn = new JButton("選擇圖片");
		chooseFileBtn.setBackground(btnBackColor);

		chooseFileBtn.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("上傳照片");
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.addChoosableFileFilter(
					new javax.swing.filechooser.FileNameExtensionFilter("圖片檔案 (JPG, PNG)", "jpg", "jpeg", "png"));

			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				selectedFile = fileChooser.getSelectedFile();
				selectedFileLabel.setText("已選擇：" + selectedFile.getName());
			}
		});
		selectedFileLabel = new JLabel("未選擇檔案");
		JPanel filePanel = new JPanel(new FlowLayout());
		filePanel.setBackground(backColor); // 保持背景一致
		filePanel.add(chooseFileBtn, BorderLayout.NORTH);
		filePanel.add(selectedFileLabel, BorderLayout.SOUTH);

		leftPanel.add(filePanel, BorderLayout.CENTER);

		// 報修類型 跟 地點 表單
		categoryCombo = new JComboBox<>();
		locationCombo = new JComboBox<>();
		categoryCombo.setBackground(btnBackColor);
		locationCombo.setBackground(btnBackColor);

		// 左半邊的上半
		JPanel upperPanel = new JPanel(new GridLayout(2, 2));

		upperPanel.setBackground(backColor); // 背景與主背景一致
		reportStyle = new JLabel("報修類別:");
		upperPanel.add(reportStyle);
		upperPanel.add(categoryCombo);
		placeStyle = new JLabel("故障地點:");
		upperPanel.add(placeStyle);
		upperPanel.add(locationCombo);

		leftPanel.add(upperPanel, BorderLayout.NORTH);

		//
		returnBtn = new JButton("返回");
		returnBtn.setBackground(btnBackColor);
		returnBtn.addActionListener(e -> UserPage.getCardLayout().show(UserPage.getMainPanel(), "UserPage"));
		leftPanel.add(returnBtn, BorderLayout.SOUTH);

		// 這個就可以得到圖片的路徑
		if (selectedFile != null) {
			String filePath = selectedFile.getAbsolutePath();
			// 這裡可以上傳圖片或儲存路徑到資料庫
			System.out.println("選擇的檔案路徑：" + filePath);
		}
		// 右邊
		rightPanel = new JPanel(new BorderLayout());
		rightPanel.setBackground(backColor);

		JPanel centerPanel = new JPanel(new BorderLayout(2, 1));

		centerPanel.setBackground(backColor);
		situation_description = new JTextArea(3, 20);
		situation_description.setBackground(inputBackColor);
		situation_description.setForeground(inputTextColor);
		placeLabel = new JLabel("問題描述：");
		centerPanel.add(placeLabel, BorderLayout.NORTH);
		centerPanel.add(new JScrollPane(situation_description), BorderLayout.CENTER);
		rightPanel.add(centerPanel, BorderLayout.CENTER);

		JPanel northPanel = new JPanel(new GridLayout(1, 2));
		northPanel.setBackground(backColor);
		place_description = new JTextField(15);
		place_description.setBackground(inputBackColor);
		situationLabel = new JLabel("地點描述：");
		northPanel.add(situationLabel, BorderLayout.NORTH);
		northPanel.add(place_description);
		rightPanel.add(northPanel, BorderLayout.NORTH);

		submitBtn = new JButton("送出");
		submitBtn.addActionListener(e -> insertReport());
		submitBtn.setBackground(btnBackColor);
		rightPanel.add(submitBtn, BorderLayout.SOUTH);

		leftPanel.setMinimumSize(new Dimension(200, 0));
		rightPanel.setMinimumSize(new Dimension(200, 0));

		// 改成只用 splitPane
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setContinuousLayout(true); // 即時更新
		mainPanel.add(splitPane, BorderLayout.CENTER);

		title = new JLabel("回報");
		title.setBackground(backColor);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setOpaque(true); // 讓 JLabel 的背景色生效
		title.setBorder(new EmptyBorder(0, 0, 10, 0));

		mainPanel.add(title, BorderLayout.NORTH);

		add(mainPanel);

		locationCombo.addItem(new ComboItem(-1, "請選擇地點"));
		categoryCombo.addItem(new ComboItem(-1, "請選擇故障類型"));

		loadComboBox(locationCombo, "location");
		loadComboBox(categoryCombo, "categories");
		// 設定字體大小
		setFont();

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
		//之後沒用到可刪
		String situation_description_upload = situation_description.getText();
		String place_description_upload = place_description.getText();
		String imagePath = null;

		if (selectedFile != null) {
	        try {
	            //建立 uploads 資料夾 (相對專案根目錄)
	            File uploadDir = new File("uploads");
	            if (!uploadDir.exists()) {
	                uploadDir.mkdirs();
	            }

	            //重新命名檔案，避免重複，例：timestamp + 原檔名
	            String newFileName = System.currentTimeMillis() + "_" + selectedFile.getName();
	            File target = new File(uploadDir, newFileName);

	            // 複製檔案
	            Files.copy(selectedFile.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);

	            //存相對路徑
	            imagePath = "uploads/" + newFileName;
	        } catch (Exception e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, "圖片上傳失敗：" + e.getMessage());
	            return;  //失敗就不要繼續送出
	        }
	    }

		String sql = "INSERT INTO reports (description_place, description_situation, category_id, location_id, image_path) VALUES(?, ?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(url, username, password);
			PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, place_description.getText());
			ps.setString(2, situation_description.getText());
			ps.setInt(3, selectedCategory.getId());
			ps.setInt(4, selectedLocation.getId());
			ps.setString(5, imagePath);

			ps.executeUpdate();
			JOptionPane.showMessageDialog(this, "送出成功");
			
			place_description.setText("");
			situation_description.setText("");
			selectedFile = null;
			selectedFileLabel.setText("未選擇檔案");
			
			
			UserPage.getCardLayout().show(UserPage.getMainPanel(), "UserPage");
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "送出失敗" + e.getMessage());
		}

	}

	private void setFont() {
		title.setFont(titleFont);
		chooseFileBtn.setFont(insideWordFont);
		placeLabel.setFont(insideWordFont);
		situationLabel.setFont(insideWordFont);
		selectedFileLabel.setFont(insideWordFont);
		returnBtn.setFont(insideWordFont);
		reportStyle.setFont(insideWordFont);
		placeStyle.setFont(insideWordFont);
		submitBtn.setFont(insideWordFont);
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
		
		public String toString() {
			return name;
		}
	}

}
