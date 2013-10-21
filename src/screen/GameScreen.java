package screen;

import org.lwjgl.opengl.GL11;

import level.Level;
import core.*;
import entity.Player;

public class GameScreen implements Screen {

	/**
	 * Screen ID.
	 */
	public static final int ID = 3;

	/**
	 * Refference to the main class. Used for managing screens, input, timing.
	 */
	Main main;

	/**
	 * Player.
	 */
	Player player;

	/**
	 * Camera, watching from above.
	 */
	Camera camera;

	/**
	 * Level.
	 */
	Level level;

	/**
	 * 
	 * 
	 * @param main
	 *            Main class.
	 */
	public GameScreen(Main main) {
		this.main = main;
	}

	@Override
	public void init() {	
		level = new Level("level_two");
		player = new Player(32, 32, 32, 32, main.getKeys(), level);
		camera = new Camera(0, 0, level.mapWidth, level.mapHeight);
	}

	@Override
	public void enter() {
		camera.centerOn(player.getCenter());
	}

	@Override
	public void update(int delta) {

		player.update(delta);

		// exit
		if (main.getKeys().exit.wasDown()) {
			main.enterScreen(MainMenuScreen.ID);
		}
	}

	@Override
	public void render() {
		camera.centerOn(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);
		
		level.renderMap(camera.x, camera.y);
		
		camera.translate();
		player.render();
		camera.untranslate();

		
		
		
		if (Main.DEBUGG) {
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(0.0f, 0.4f, 0.85f);
			
			camera.translate();
			
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
			
			camera.untranslate();
			
			String sn = "game";
			ui.Font.renderText(sn, Main.WIDTH / 2 - ui.Font.getMessageWidth(sn)
					/ 2, 2);
			ui.Font.renderText(Main.VERSION,
					Main.WIDTH / 2 - ui.Font.getMessageWidth(Main.VERSION) / 2,
					Main.HEIGHT - ui.Font.HEIGHT);
			ui.Font.renderText(Integer.toString(level.tilesDisplaying) + " tiles", 1, 10);
		}
	}

	@Override
	public void exit() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public int getID() {
		return GameScreen.ID;
	}

}
