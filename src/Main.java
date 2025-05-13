import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
        // 建議在 Event Dispatch Thread 中啟動 Swing 介面
        SwingUtilities.invokeLater(() -> new StartPage());
    }
}

