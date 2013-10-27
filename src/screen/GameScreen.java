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
	 * Level.
	 */
	Level level;

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
		//camera.centerOn(level.player.getX() + level.player.getWidth() / 2, level.player.getY() + level.player.getHeight() / 2);
		level.render();
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
