
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class QASettingPanel extends JPanel {
	private JLabel title;
	private JFXPanel jfxPanel;
	private Button addNew;
	private ArrayList<String> questions;
	private ArrayList<String> answers;

	public QASettingPanel() {

		setLayout(new BorderLayout());
		// Title set
		title = new JLabel("Q&A");
		title.setFont(new Font("Microsoft JhengHei", Font.BOLD, 40));

		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // 置中對齊
		titlePanel.add(title);
		add(titlePanel, BorderLayout.NORTH);
		// 初始化 JFXPanel
		jfxPanel = new JFXPanel();
		add(jfxPanel, BorderLayout.CENTER);

		// 啟動 JavaFX UI
		Platform.runLater(() -> initFX(jfxPanel));

	}

	private void initFX(JFXPanel fxPanel) {
	    Accordion accordion = new Accordion();
	    VBox contentBox = new VBox(10);
	    contentBox.setStyle("-fx-padding: 20; -fx-background-color: #f9f9f9;");

	    // [1] 標題列 + 新增按鈕
	    HBox headerBox = new HBox();
	    headerBox.setSpacing(10);
	    headerBox.setStyle("-fx-alignment: center-right;");

	    Button addNewBtn = new Button("➕ 新增問題");
	    addNewBtn.setOnAction(e -> {
	        // 模擬新增問題
	        int num = accordion.getPanes().size() + 1;
	        TitledPane newPane = createQuestionPane("❓ New Question " + num, "This is a new answer.");
	        accordion.getPanes().add(newPane);
	    });

	    headerBox.getChildren().add(addNewBtn);
	    contentBox.getChildren().add(headerBox);


	    for (int i = 1; i <= 2; i++) {
	        TitledPane pane = createQuestionPane("問題" + i, "答案" + i);
	        accordion.getPanes().add(pane);
	    }

	    contentBox.getChildren().add(accordion);

	    // [3] 放進 ScrollPane
	    ScrollPane scrollPane = new ScrollPane(contentBox);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setStyle("-fx-background: #ffffff;");

	    Scene scene = new Scene(scrollPane, 500, 400);
	    fxPanel.setScene(scene);
	}
	
	private TitledPane createQuestionPane(String question, String answer) {
	    // 問題與答案的 Label
	    Label questionLabel = new Label(question);
	    Label answerLabel = new Label(answer);
	    answerLabel.setWrapText(true);
	    answerLabel.setMaxWidth(400);

	    // 修改按鈕（放在標題右側）
	    Button editBtn = new Button("✏");
	    editBtn.setStyle("-fx-font-size: 10; -fx-padding: 2 6 2 6;");
	    editBtn.setOnAction(e -> {
	        // 兩個輸入框對話視窗
	        Dialog<Pair<String, String>> dialog = new Dialog<>();
	        dialog.setTitle("修改 Q&A");
	        dialog.setHeaderText("請修改問題與答案");

	        // 使用預設的 OK/Cancel 按鈕
	        ButtonType okButtonType = new ButtonType("確定", ButtonBar.ButtonData.OK_DONE);
	        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

	        // 問題與答案輸入框
	        TextField questionField = new TextField(questionLabel.getText());
	        TextArea answerField = new TextArea(answerLabel.getText());

	        VBox dialogContent = new VBox(10);
	        dialogContent.setPadding(new Insets(10));
	        dialogContent.getChildren().addAll(
	            new Label("問題："), questionField,
	            new Label("答案："), answerField
	        );

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
	    });

	    // 標題區域（問題 + 修改鈕）
	    HBox titleBox = new HBox(10, questionLabel, editBtn);
	    titleBox.setStyle("-fx-alignment: center-left;");
	    titleBox.setPadding(new Insets(5));

	    // 答案區域
	    VBox contentBox = new VBox(answerLabel);
	    contentBox.setPadding(new Insets(5));

	    // TitledPane
	    TitledPane pane = new TitledPane();
	    pane.setGraphic(titleBox);
	    pane.setContent(contentBox);

	    return pane;
	}


}

