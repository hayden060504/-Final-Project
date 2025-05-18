package Main.UserPage;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserPage {
	private static CardLayout cardLayout;
	private static JPanel mainPanel;

    public UserPage() {
        	JFrame frame = new JFrame("User Page");
        	frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            //建立 JFXPanel(就是你讓我用很久!!!)
            //笑死
            JFXPanel fxPanel = new JFXPanel();
            
            cardLayout = new CardLayout();
            mainPanel = new JPanel(cardLayout);
            
            mainPanel.add(fxPanel,"UserPage");
            frame.add(mainPanel);
            
            //新增畫面
            mainPanel.add(new ReportPage(),"ReportPage");
            mainPanel.add(new CheckPage(),"CheckPage");
            mainPanel.add(new MapPage(),"MapPage");
            mainPanel.add(new QAPageForUser(),"QAPageForUser");
            
            //啟動 JavaFX UI
            Platform.runLater(() -> fxPanel.setScene(createFXScene()));
            
            //設定初始畫面
            cardLayout.show(mainPanel, "UserPage");
            
            frame.setVisible(true);
        };

    //JavaFX Scene
    private static Scene createFXScene() {
        //左邊(公告+熱門維修進度查詢)
        VBox leftPanel = new VBox(15);
        leftPanel.setPadding(new Insets(20));
        leftPanel.setPrefWidth(300);
        leftPanel.setStyle("-fx-background-color: #fdf1dc;");

        //最新公告
        VBox noticeBox = new VBox(10);
        noticeBox.setPadding(new Insets(10));
        noticeBox.setStyle(
                "-fx-border-color: #cccccc; " +
                "-fx-border-radius: 8; " +
                "-fx-border-width: 1; " +
                "-fx-background-radius: 8; " +
                "-fx-background-color: white;");
        Label noticeLabel = new Label("最新公告");
        TextArea noticeArea = new TextArea("公告 1\n公告 2\n公告 3");
        noticeArea.setEditable(false);
        noticeArea.setWrapText(true);
        noticeArea.setPrefHeight(100);
        ScrollPane noticeScroll = new ScrollPane(noticeArea);
        noticeScroll.setFitToWidth(true);
        noticeScroll.setPrefHeight(120);
        noticeBox.getChildren().addAll(noticeLabel, noticeScroll);

        //熱門維修進度查詢
        VBox progressBox = new VBox(10);
        progressBox.setPadding(new Insets(10));
        progressBox.setStyle(
                "-fx-border-color: #cccccc; " +
                "-fx-border-radius: 8; " +
                "-fx-border-width: 1; " +
                "-fx-background-radius: 8; " +
                "-fx-background-color: white;");
        Label progressLabel = new Label("熱門維修進度查詢");
        TextArea progressArea = new TextArea("1. case A\n2. case B\n3. case C");
        progressArea.setEditable(false);
        progressArea.setWrapText(true);
        progressArea.setPrefHeight(100);
        ScrollPane progressScroll = new ScrollPane(progressArea);
        progressScroll.setFitToWidth(true);
        progressScroll.setPrefHeight(120);
        progressBox.getChildren().addAll(progressLabel, progressScroll);

        leftPanel.getChildren().addAll(noticeBox, progressBox);

        //右邊的按鈕們
        VBox rightPanel = new VBox(20);
        rightPanel.setPadding(new Insets(10));
        rightPanel.setAlignment(Pos.TOP_CENTER);
        rightPanel.setPrefWidth(100);
        rightPanel.setStyle("-fx-background-color: #fde8c8;");

        Button reportBtn = new Button("我要報修");
        Button checkBtn = new Button("進度查詢");
        Button mapBtn = new Button("地圖");
        Button QABtn = new Button("Q&A");

        for (Button btn : new Button[]{reportBtn, checkBtn, mapBtn, QABtn}) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle(
                    "-fx-background-color: #ffcc99;" +
                    "-fx-border-color: #d38f65;" +
                    "-fx-border-radius: 8;" +
                    "-fx-background-radius: 8;" +
                    "-fx-font-weight: bold;");
        }

        
        
        //按按鈕換到新Page
        reportBtn.setOnAction(e -> cardLayout.show(mainPanel, "ReportPage"));
        checkBtn.setOnAction(e -> cardLayout.show(mainPanel, "CheckPage"));
        mapBtn.setOnAction(e -> cardLayout.show(mainPanel, "MapPage"));
        QABtn.setOnAction(e -> cardLayout.show(mainPanel, "QAPageForUser"));

        rightPanel.getChildren().addAll(reportBtn, checkBtn, mapBtn, QABtn);

        //左右邊合起來
        HBox content = new HBox(leftPanel, rightPanel);
        VBox root = new VBox(content);

        return new Scene(root);
    }
    
  //獲得CardLayout，別的畫面的跳回按鍵用這兩個
  	/*e.g:
  	 *UserPage.getCardLayout().show(UserPage.getMainPanel(),"你想要的畫面");
  	 *根據上方cardLayout建立時後面的String去打在你想要的畫面
  	 */
  	public static CardLayout getCardLayout() {
  		return cardLayout;
  	}
  	public static JPanel getMainPanel() {
  		return mainPanel;
  	}
}
