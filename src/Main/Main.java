package Main;

import javafx.application.Platform;
import javax.swing.SwingUtilities;
import Main.StartPage.StartPage;

public class Main {
    public static void main(String[] args) {
        // 初始化 JavaFX Toolkit，只執行一次就好
        Platform.startup(() -> {
            // 再用 SwingUtilities 啟動你的 Swing UI
            SwingUtilities.invokeLater(() -> new StartPage());
        });
    }
}


