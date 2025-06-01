package Main;

import java.awt.Font;
import java.awt.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class JavaFxFontTransfer {

	public static javafx.scene.text.Font convertAwtFontToJavaFX(Font awtFont) {
		String family = awtFont.getName();
		int size = awtFont.getSize();

		// 判斷字重
		FontWeight weight = FontWeight.NORMAL;
		if ((awtFont.getStyle() & Font.BOLD) != 0) {
			weight = FontWeight.BOLD;
		}

		// 判斷斜體
		FontPosture posture = FontPosture.REGULAR;
		if ((awtFont.getStyle() & Font.ITALIC) != 0) {
			posture = FontPosture.ITALIC;
		}

		return javafx.scene.text.Font.font(family, weight, posture, size);
	}

	public static javafx.scene.paint.Color convertAwtColorToJavaFX(Color awtColor) {
		return javafx.scene.paint.Color.rgb(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(),
				awtColor.getAlpha() / 255.0);
	}
}
