package screen;

import util.Debugg;
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
		level = new Level("level1");
		level.player = new Player(32, 32, 32, 32, main.getKeys(), level, Player.PLAYER_TYPE_GENIE);
		camera = new Camera(0, 0, level.mapWidth, level.mapHeight);
	}

	@Override
	public void enter() {
		//camera.centerOn(player.getCenter());
		camera.centerOn(level.player.getCenter());
	}

	@Override
	public void update(int delta) {

		level.player.update(delta);

		// exit
		if (main.getKeys().exit.wasDown()) {
			main.enterScreen(MainMenuScreen.ID);
		}
	}

	@Override
	public void render() {
		camera.centerOn(level.player.getX() + level.player.getWidth() / 2, level.player.getY() + level.player.getHeight() / 2);
		level.renderMap(camera.x, camera.y);
		
		camera.translate();
		level.player.render();
		camera.untranslate();

		if (Main.DEBUGG) {
			camera.translate();
			Debugg.printTileGrid(level); 
			camera.untranslate();
			
			Debugg.printTilesDisplayed(level.tilesDisplaying);
			Debugg.printActiveScreenName("game");
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
