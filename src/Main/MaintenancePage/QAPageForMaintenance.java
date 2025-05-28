package Main.MaintenancePage;

import java.sql.*;

import java.awt.BorderLayout;
import java.util.ArrayList;

import Main.Style;

import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;

public class QAPageForMaintenance extends JPanel implements Style{
	private JFXPanel jfxPanel;
	private Accordion accordion;
	private ArrayList<String> questions;
	private ArrayList<String> answers;
	private String server = "jdbc:mysql://140.119.19.73:3315/";
	private String database = "TG09"; // change to your own database
	private String url = server + database + "?useSSL=false";
	private String username = "TG09"; // change to your own username
	private String password = "hGykqi"; // change to your own password

	public QAPageForMaintenance() {

		setLayout(new BorderLayout());

		// 初始化 JFXPanel
		jfxPanel = new JFXPanel();
		add(jfxPanel, BorderLayout.CENTER);

		// 啟動 JavaFX UI
		Platform.runLater(() -> initFX(jfxPanel));

	}

	private void initFX(JFXPanel fxPanel) {
		VBox contentBox = new VBox(10);
		contentBox.setStyle("-fx-padding: 20; -fx-background-color: #f9f9f9;");

		// [1] 標題列 + 新增按鈕
		HBox headerBox = new HBox();
		headerBox.setSpacing(10);
		headerBox.setAlignment(Pos.CENTER_LEFT);

		// 建立標題 Label
		Label titleLabel = new Label("Q&A");
		titleLabel.setFont(Font.font("Microsoft JhengHei", FontWeight.BOLD, 40));

		// 加上彈性空間（把右邊的按鈕推到右邊）
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		// 新增按鈕
		Button addNewBtn = new Button("新增問題");
		
		//重新載入按鈕
		Button reloadBtn = new Button("重載");

		// 加到 HBox（順序很重要）
		headerBox.getChildren().addAll(titleLabel, spacer, addNewBtn, reloadBtn);

		contentBox.getChildren().add(headerBox);

		accordion = new Accordion();

		// 示範用
		/*
		 * for (int i = 1; i <= 2; i++) { TitledPane pane = createQuestionPane("問題" + i,
		 * "答案" + i); accordion.getPanes().add(pane); }
		 */

		contentBox.getChildren().add(accordion);

		// [3] 放進 ScrollPane
		ScrollPane scrollPane = new ScrollPane(contentBox);
		scrollPane.setFitToWidth(true);
		scrollPane.setStyle("-fx-background: #ffffff;");

		Scene scene = new Scene(scrollPane, 500, 400);
		fxPanel.setScene(scene);

		// 底部欄位
		HBox bottomBox = new HBox();
		bottomBox.setSpacing(10);
		bottomBox.setAlignment(Pos.CENTER_LEFT);

		// 返回按鈕
		Button returnBtn = new Button("返回");

		bottomBox.getChildren().add(returnBtn);

		contentBox.getChildren().add(bottomBox);

		// 載入資料庫
		load();

		// 新增按鈕的反應
		addNewBtn.setOnAction(e -> {
			Dialog<Pair<String, String>> dialog = new Dialog<>();
			dialog.setTitle("新增 Q&A");
			dialog.setHeaderText("請輸入問題與答案");

			ButtonType okButtonType = new ButtonType("確定", ButtonBar.ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

			// 問題與答案輸入框
			TextField questionField = new TextField("問題");
			TextArea answerField = new TextArea("答案");

			// 創建排版
			VBox dialogContent = new VBox(10);
			dialogContent.setPadding(new Insets(10));
			dialogContent.getChildren().addAll(new Label("問題："), questionField, new Label("答案："), answerField);

			dialog.getDialogPane().setContent(dialogContent);

			dialog.showAndWait();

			TitledPane newPane = createQuestionPane(questionField.getText(), answerField.getText());
			accordion.getPanes().add(newPane);

			// 上傳至資料庫
			update(questionField.getText(), answerField.getText());
		});

		// 返回按鈕的反應
		returnBtn.setOnAction(e -> {
			MaintenancePage.getCardLayout().show(MaintenancePage.getMainPanel(), "MaintenancePage");
		});
		
		//
		reloadBtn.setOnAction(e->{
			load();
		});
	}

	// 新增問題和答案以及修改按鈕
	private TitledPane createQuestionPane(String question, String answer) {
		// 問題與答案的 Label
		Label questionLabel = new Label(question);
		Label answerLabel = new Label(answer);
		answerLabel.setWrapText(true);
		answerLabel.setMaxWidth(400);

		// 修改按鈕（放在標題右側）
		Button editBtn = new Button("修改");
		editBtn.setStyle("-fx-font-size: 10; -fx-padding: 2 6 2 6;");

		// 修改按鈕的反應
		editBtn.setOnAction(e -> {
			// 兩個輸入框對話視窗
			Dialog<Pair<String, String>> dialog = new Dialog<>();
			dialog.setTitle("修改 Q&A");
			dialog.setHeaderText("請修改問題與答案");
			
			String originalQuestion = questionLabel.getText();

			// 使用預設的 OK/Cancel 按鈕
			ButtonType okButtonType = new ButtonType("確定", ButtonBar.ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

			// 問題與答案輸入框
			TextField questionField = new TextField(questionLabel.getText());
			TextArea answerField = new TextArea(answerLabel.getText());

			VBox dialogContent = new VBox(10);
			dialogContent.setPadding(new Insets(10));
			dialogContent.getChildren().addAll(new Label("問題："), questionField, new Label("答案："), answerField);

			dialog.getDialogPane().setContent(dialogContent);

			// 結果處理
			dialog.setResultConverter(dialogButton -> {
				if (dialogButton == okButtonType) {
					return new Pair<>(questionField.getText(), answerField.getText());
				}
				return null;
			});

			dialog.showAndWait().ifPresent(result -> {
				questionLabel.setText(result.getKey());
				answerLabel.setText(result.getValue());
			});

			// 上傳至資料庫
			edit(questionField.getText(), answerField.getText(), originalQuestion);
		});

		// 刪除按鈕
		Button deleteBtn = new Button("X");
		deleteBtn.setStyle("-fx-font-size: 10; -fx-padding: 2 6 2 6;");

		// 標題區域（問題 + 修改鈕 + 刪除紐）
		HBox titleBox = new HBox(10, questionLabel, editBtn, deleteBtn);
		titleBox.setStyle("-fx-alignment: center-left;");
		titleBox.setPadding(new Insets(5));

		// 答案區域
		VBox contentBox = new VBox(answerLabel);
		contentBox.setPadding(new Insets(5));

		// TitledPane
		TitledPane pane = new TitledPane();
		pane.setGraphic(titleBox);
		pane.setContent(contentBox);

		deleteBtn.setOnAction(e -> {

			// 從 Accordion 中移除這個 pane
			accordion.getPanes().remove(pane);

			// 從標題中取得問題文字（你是用 Label）
			Label deleteLabel = (Label) ((HBox) pane.getGraphic()).getChildren().get(0);
			String questionText = deleteLabel.getText();

			// 刪除資料庫該筆資料
			delete(questionText);
		});

		return pane;
	}

	//上傳資料
	private void update(String question, String ans) {

		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			System.out.println("DB Connected");

			Statement stat = conn.createStatement();
			String query = String.format("INSERT INTO `Q&A` (Question,Answer) VALUES ('%s','%s')", question, ans);

			stat.execute(query);

			stat.close();

			System.out.println("Data updated.");
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
	
	//修改資料
	private void edit(String question, String ans, String originalQuestion) {

		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			System.out.println("DB Connected");

			Statement stat = conn.createStatement();
			String query = String.format("UPDATE `Q&A` SET Question = '%s', Answer = '%s' WHERE Question = '%s'", question, ans, originalQuestion);

			stat.execute(query);

			stat.close();

			System.out.println("Data edited.");
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	//讀取資料
	private void load() {
		accordion.getPanes().clear();
		
		String sql = "SELECT Question, Answer FROM `Q&A`"; // 請把 your_table_name 換成你的資料表名稱

		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			System.out.println("DB Connected.");

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {
				String question = rs.getString("Question");
				String answer = rs.getString("Answer");

				// 創造面板
				TitledPane pane = createQuestionPane(question, answer);

				// 加入至面板
				accordion.getPanes().add(pane);
			}

			stat.close();

			System.out.println("Data Loaded.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void delete(String question) {

		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			System.out.println("DB Connected.");

			Statement stat = conn.createStatement();

			String query = "DELETE FROM `Q&A` WHERE Question='" + question + "'";

			stat.execute(query);

			stat.close();

			System.out.println("Delete Succeed");
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
}
