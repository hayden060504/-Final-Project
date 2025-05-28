package Main;

import java.awt.Color;
import java.awt.Font;

public interface Style {

	//這是interface，使用的時候implements之後直接用
	
	Color inputBackColor = new Color(255, 235, 200); // 主背景（左邊）
	Color backColor = new Color(253, 241, 220); // 輸入框背景
	Color btnBackColor = new Color(253, 225, 191); // 按鈕背景
	Color inputTextColor = Color.DARK_GRAY; // 輸入框文字

	Font contentFont = new Font("Microsoft JhengHei", Font.PLAIN, 20); // 文字大小和按鈕字體大小
	Font titleFont = new Font("Microsoft JhengHei", Font.BOLD, 40); // 標題大小
}
