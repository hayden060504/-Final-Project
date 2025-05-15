import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class UserPage extends Application {
    public void start(Stage primaryStage) {
        primaryStage.setTitle("User Page");

        //左邊(公告+進度查詢)
        VBox leftPanel = new VBox(15);
        leftPanel.setPadding(new Insets(20));
        leftPanel.setPrefWidth(450);
        leftPanel.setStyle("-fx-background-color: #fdf1dc;");

        //最新公告
        VBox noticeBox = new VBox(10);
        noticeBox.setPadding(new Insets(10));
        noticeBox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 8; -fx-border-width: 1; -fx-background-radius: 8; -fx-background-color: white;");
        Label noticeLabel = new Label("最新公告");
        TextArea noticeArea = new TextArea("公告 1\n公告 2\n公告 3");
        noticeArea.setEditable(false);
        noticeArea.setWrapText(true);
        noticeArea.setPrefHeight(100);
        ScrollPane noticeScroll = new ScrollPane(noticeArea);
        noticeScroll.setFitToWidth(true);
        noticeScroll.setPrefHeight(120);
        noticeBox.getChildren().addAll(noticeLabel, noticeScroll);

        //熱門維修進度
        VBox progressBox = new VBox(10);
        progressBox.setPadding(new Insets(10));
        progressBox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 8; -fx-border-width: 1; -fx-background-radius: 8; -fx-background-color: white;");
        Label progressLabel = new Label("熱門維修進度");
        TextArea progressArea = new TextArea("1. 維修 A\n2. 維修 B\n3. 維修 C");
        progressArea.setEditable(false);
        progressArea.setWrapText(true);
        progressArea.setPrefHeight(100);
        ScrollPane progressScroll = new ScrollPane(progressArea);
        progressScroll.setFitToWidth(true);
        progressScroll.setPrefHeight(120);
        progressBox.getChildren().addAll(progressLabel, progressScroll);

        leftPanel.getChildren().addAll(noticeBox, progressBox);

        //右邊(按鈕們)
        VBox rightPanel = new VBox(15);
        rightPanel.setPadding(new Insets(20));
        rightPanel.setAlignment(Pos.TOP_CENTER);
        rightPanel.setPrefWidth(150);
        rightPanel.setStyle("-fx-background-color: #fde8c8;");
        
        //按鈕們
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
                "-fx-font-weight: bold;"
            );
        }
        
        reportBtn.setOnAction(e -> new ReportPage());
        checkBtn.setOnAction(e -> new CheckPage());
        mapBtn.setOnAction(e -> new MapPage());
        QABtn.setOnAction(e -> new QAPage());


        rightPanel.getChildren().addAll(reportBtn, checkBtn, mapBtn, QABtn);

        //左右邊合起來
        HBox content = new HBox(leftPanel, rightPanel);
        VBox root = new VBox(content);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
