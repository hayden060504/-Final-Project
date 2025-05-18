package Main.UserPage;

import java.awt.BorderLayout;
import java.util.ArrayList;

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

public class QAPageForUser extends JPanel {
	private JFXPanel jfxPanel;
	private Button addNew;
	private ArrayList<String> questions;
	private ArrayList<String> answers;

	public QAPageForUser() {

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

	    

	    

	    
	    
	    // 加到 HBox（順序很重要）
	    headerBox.getChildren().addAll(titleLabel);

	    contentBox.getChildren().add(headerBox);
	    
	    
	    Accordion accordion = new Accordion();

	    contentBox.getChildren().add(accordion);

	    // [3] 放進 ScrollPane
	    ScrollPane scrollPane = new ScrollPane(contentBox);
	    scrollPane.setFitToWidth(true);
	    scrollPane.setStyle("-fx-background: #ffffff;");

	    Scene scene = new Scene(scrollPane, 500, 400);
	    fxPanel.setScene(scene);
	    
	  //返回按鈕
	    HBox bottomBox = new HBox();
	    bottomBox.setSpacing(10);
	    bottomBox.setAlignment(Pos.CENTER_LEFT);
	    
	    Button returnBtn = new Button("返回");
	    
	    bottomBox.getChildren().add(returnBtn);
	    
	    contentBox.getChildren().add(bottomBox);	    
	    
	    returnBtn.setOnAction(e ->{
	    	UserPage.getCardLayout().show(UserPage.getMainPanel(), "UserPage");
	    });
	}
}
