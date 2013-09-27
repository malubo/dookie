package ui;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Font {

	private static String letters = "abcdefghijklmnopqrstuvwxyz    "
			+ "0123456789-.!?/%$\\=*+,;:()&#\"'";

	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;

	private static SpriteSheet font = getFont();

	public static float getMessageWidth(String msg) {
		return msg.length() * WIDTH;
	}

	public static void renderText(String msg, float x, float y) {
		msg = msg.toLowerCase();
		int offset = 0;

		for (char c : msg.toCharArray()) {
			int yCoord = letters.indexOf(c) / 30;
			int xCoord = letters.indexOf(c) - yCoord * 30;
			Image letter = font.getSprite(xCoord, yCoord);
			letter.clampTexture();
			letter.draw(x + offset, y);
			offset += WIDTH;
		}
	}
	
	private static SpriteSheet getFont() {
		try {
			return new SpriteSheet("res/image/font.png", WIDTH, HEIGHT);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return null;
	}
}
