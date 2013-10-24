package util;

import level.Level;

import org.lwjgl.opengl.GL11;

import core.Main;

public class Debugg {

	public static void printFPS(int fps) {
		ui.Font.renderText(Integer.toString(fps) + "fps", 1, 1);
	}

	public static void printTilesDisplayed(int tiles) {
		String sn = "game";
		ui.Font.renderText(sn,
				Main.WIDTH / 2 - ui.Font.getMessageWidth(sn) / 2, 2);
		ui.Font.renderText(Integer.toString(tiles) + "tiles", 1, 10);
	}
	
	public static void printTileGrid(Level level) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.0f, 0.4f, 0.85f);
		
		// horisontal grid
		for(int i = 0; i < level.mapHeight; i++){
			GL11.glBegin(GL11.GL_LINES);
		    GL11.glLineWidth(1.0f);
		    GL11.glVertex2f(i*32, 0);
		    GL11.glVertex2f(i*32, level.mapHeight);
		    GL11.glEnd();
		}
		
		// vertical grid
		for(int i = 0; i < level.mapWidth; i++){
			GL11.glBegin(GL11.GL_LINES);
		    GL11.glLineWidth(1.0f);
		    GL11.glVertex2f(0, i*32);
		    GL11.glVertex2f(level.mapWidth, i*32);
		    GL11.glEnd();
		}
	}

	public static void printVersionNumber() {
		ui.Font.renderText(Main.VERSION,
				Main.WIDTH / 2 - ui.Font.getMessageWidth(Main.VERSION) / 2,
				Main.HEIGHT - ui.Font.HEIGHT);
	}

	public static void printActiveScreenName(String name) {
		ui.Font.renderText(name, Main.WIDTH / 2 - ui.Font.getMessageWidth(name)
				/ 2, 2);
	}

	public static void printPlayerInfo(int playerX, int playerY, int targetX,
			int targetY, String state, String direction) {
	}

}
