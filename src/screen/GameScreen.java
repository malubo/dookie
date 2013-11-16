package screen;

import util.Debug;
import level.Level;
import core.*;

public class GameScreen implements Screen {

	/**
	 * Screen ID.
	 */
	public static final int ID = 3;

	/**
	 * Reference to the main class. Used for managing screens, input, timing.
	 */
	private Main main;
	
	public static final int MIN_LEVEL_NUMBER = 0;
	public static final int MAX_LEVEL_NUMBER = 99;

	/**
	 * Number of the active level.
	 */
	int activeLevelNumber = MIN_LEVEL_NUMBER;

	/**
	 * Level.
	 */
	private Level level;
	
	
	/**
	 * @param main Main
	 */
	public GameScreen(Main main) {
		this.main = main;
	}

	@Override
	public void init() {	
		level = new Level("level1", main.getKeys());
	}

	@Override
	public void enter() {
	}

	@Override
	public void update(int delta) {

		level.update(delta);

		// exit
		if (main.getKeys().exit.wasDown()) {
			main.enterScreen(MainMenuScreen.ID);
		}
	}

	@Override
	public void render() {
		level.render();
		if(Main.DEBUG) {
			Debug.printActiveScreenName("game");
			Debug.printVersionNumber();
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
