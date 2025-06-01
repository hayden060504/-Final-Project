package Main.UserPage;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Main.JavaFxFontTransfer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

public class UserPage{
	private static CardLayout cardLayout;
	private static JPanel mainPanel;
	private static VBox progressBoxContent;
	private static TextArea noticeArea;
	
	static String url = "jdbc:mysql://140.119.19.73:3315/TG09";
	static String dbUser = "TG09";
	static String dbPassword = "hGykqi";
	
	static Font titleFont = new Font("Microsoft JhengHei", Font.BOLD, 40);
    static Font contentFont = new Font("Microsoft JhengHei", Font.PLAIN, 20);
    static Color backColor = new Color(253, 241, 220); 
    static Color btnBackColor = new Color(253, 225, 191);
    static Color inputBackColor = new Color(255, 235, 200); 

    public UserPage() {
        	JFrame frame = new JFrame("User Page");
        	frame.setSize(800, 600);
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
        VBox leftPanel = new VBox(30);
        leftPanel.setPadding(new Insets(40));
        leftPanel.setPrefWidth(600);
        leftPanel.setStyle("-fx-background-color: #fdf1dc;");

        //最新公告
        VBox noticeBox = new VBox(20);
        noticeBox.setPadding(new Insets(20));
        leftPanel.setStyle("-fx-background-color: #" +
                JavaFxFontTransfer.toHex(JavaFxFontTransfer.convertAwtColorToJavaFX(inputBackColor)) + ";");
        Label noticeLabel = new Label("<最新公告>");
        noticeLabel.setFont(JavaFxFontTransfer.convertAwtFontToJavaFX(titleFont));
        
        noticeArea = new TextArea();
        noticeArea.setEditable(false);
        noticeArea.setWrapText(true);
        noticeArea.setPrefHeight(200);
        noticeArea.setFont(JavaFxFontTransfer.convertAwtFontToJavaFX(contentFont));
        noticeBox.setStyle(
        	    "-fx-border-color: #cccccc; " +
        	    "-fx-border-radius: 8; " +
        	    "-fx-border-width: 1; " +
        	    "-fx-background-radius: 8; " +
        	    "-fx-background-color: #" +
        	    JavaFxFontTransfer.toHex(JavaFxFontTransfer.convertAwtColorToJavaFX(backColor)) + ";");
        ScrollPane noticeScroll = new ScrollPane(noticeArea);
        noticeScroll.setFitToWidth(true);
        noticeScroll.setPrefHeight(240);
        noticeBox.getChildren().addAll(noticeLabel, noticeScroll);
        
        //載入公告
        updateLatestAnnouncements();

        //熱門維修進度查詢
        VBox progressBox = new VBox(20);
        progressBox.setPadding(new Insets(20));
        progressBox.setStyle(
        	    "-fx-border-color: #cccccc; " +
        	    "-fx-border-radius: 8; " +
        	    "-fx-border-width: 1; " +
        	    "-fx-background-radius: 8; " +
        	    "-fx-background-color: #" +
        	    JavaFxFontTransfer.toHex(JavaFxFontTransfer.convertAwtColorToJavaFX(backColor)) + ";");
        Label progressLabel = new Label("<熱門維修進度查詢>");
        progressLabel.setFont(JavaFxFontTransfer.convertAwtFontToJavaFX(titleFont));
        
        progressBoxContent = new VBox(10); 
        
        ScrollPane progressScroll = new ScrollPane(progressBoxContent);
        progressScroll.setFitToWidth(true);
        progressScroll.setPrefHeight(240);
        progressBox.getChildren().addAll(progressLabel, progressScroll);

        leftPanel.getChildren().addAll(noticeBox, progressBox);
        
        //載入熱門查詢
        updateTopQueriedReports();

        //右邊的按鈕們
        VBox rightPanel = new VBox(40);
        rightPanel.setPadding(new Insets(20));
        rightPanel.setAlignment(Pos.TOP_CENTER);
        rightPanel.setPrefWidth(200);
        rightPanel.setStyle("-fx-background-color: #" +
                JavaFxFontTransfer.toHex(JavaFxFontTransfer.convertAwtColorToJavaFX(btnBackColor)) + ";");

        Button reportBtn = new Button("我要報修");
        Button checkBtn = new Button("進度查詢");
        Button QABtn = new Button("Q&A");

        for (Button btn : new Button[]{reportBtn, checkBtn, QABtn}) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setFont(JavaFxFontTransfer.convertAwtFontToJavaFX(contentFont));
            btn.setStyle(
                "-fx-background-color: #" +
                JavaFxFontTransfer.toHex(JavaFxFontTransfer.convertAwtColorToJavaFX(btnBackColor)) + ";" +
                "-fx-border-color: #d38f65;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;");
        }

        //按按鈕換到新Page
        reportBtn.setOnAction(e -> cardLayout.show(mainPanel, "ReportPage"));
        checkBtn.setOnAction(e -> cardLayout.show(mainPanel, "CheckPage"));
        QABtn.setOnAction(e -> cardLayout.show(mainPanel, "QAPageForUser"));

        rightPanel.getChildren().addAll(reportBtn, checkBtn, QABtn);

        //左右邊合起來
        HBox content = new HBox(leftPanel, rightPanel);
        VBox root = new VBox(content);

        return new Scene(root);
    }
    
    
    public static void updateLatestAnnouncements() {
        Platform.runLater(() -> {
            StringBuilder announcements = new StringBuilder();
            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
            	//從資料庫Announce表中抓前10筆
                String query = "SELECT Title, Content FROM Announce ORDER BY ID DESC LIMIT 10"; // 最新10筆
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String title = rs.getString("Title");
                    String content = rs.getString("Content");
                    announcements.append("【").append(title).append("】\n")	//標題
                                 .append(content).append("\n\n");	//內容(想說既然有內容就順便放上來好了)
                }
                noticeArea.setText(announcements.toString()); //放到最新公告的TextArea，就結束了!!!
            } catch (SQLException e) {
                noticeArea.setText("公告載入失敗: " + e.getMessage());
            }
        });
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
  	
  	 public static void updateTopQueriedReports() {
  		Platform.runLater(() -> {	//原本跑error但加這個就好了，跟上次有點像但不完全一樣
             progressBoxContent.getChildren().clear();	//把舊排名清掉
             try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
                 String query = "SELECT id, query_count FROM reports ORDER BY query_count DESC LIMIT 10";	//選查詢次數最多的前10筆
                 PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery();

                 int rank = 1;
                 while (rs.next()) {
                     String id = rs.getString("id");
                     int count = rs.getInt("query_count");
                     String buttonText = rank + ". Report ID: " + id;
                     //用link
                     Hyperlink reportLink = new Hyperlink(buttonText);
                     reportLink.setOnAction(e -> {
                         cardLayout.show(mainPanel, "CheckPage");
                         CheckPage cp = (CheckPage) getComponentByName("CheckPage");
                         if (cp != null) {
                             cp.performQuery(id);
                         }
                     });
                     reportLink.setFont(JavaFxFontTransfer.convertAwtFontToJavaFX(contentFont));
                     progressBoxContent.getChildren().add(reportLink);
                     rank++;
                 }
             } catch (SQLException e) {
                 Button errorBtn = new Button("Error fetching top queries: " + e.getMessage());
                 errorBtn.setFont(JavaFxFontTransfer.convertAwtFontToJavaFX(contentFont));
                 progressBoxContent.getChildren().add(errorBtn);
             }
  		});
  		
     }

  	 //從userpage跳去checkpage (讓cardLayout切換)
     private static JPanel getComponentByName(String name) {
         for (int i = 0; i < mainPanel.getComponentCount(); i++) {
             if (mainPanel.getComponent(i).getClass().getSimpleName().equals(name)) {
                 return (JPanel) mainPanel.getComponent(i);
             }
         }
         return null;
     }
 }
