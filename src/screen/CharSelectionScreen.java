package screen;

import ui.CharSelectionMenu;
import util.Debug;
import util.Settings;
import core.Main;

public class CharSelectionScreen implements Screen {

	public static final int ID = 5;

	private Main main;
	
	private CharSelectionMenu menu;

	public CharSelectionScreen(Main main) {
		this.main = main;
	}

	@Override
	public void init() {
		menu = new CharSelectionMenu();
	}

	@Override
	public void enter() {

	}

	@Override
	public void update(int delta) {
		menu.update(delta);

		// down pressed
		if (main.getKeys().left.isDown()) {
			menu.previous();
		}

		// up pressed
		if (main.getKeys().right.isDown()) {
			menu.next();
		}

		// confirm pressed
		if (main.getKeys().confirm.isDown()) {
			Settings.playerType = menu.getIndex();
			main.enterScreen(GameScreen.ID);
		}

		// exit was pressed
		if (main.getKeys().exit.wasDown()) {
			main.enterScreen(MainMenuScreen.ID);
		}
	}

	@Override
	public void render() {

		menu.render();

		if (Main.DEBUG) {
			Debug.printActiveScreenName("character selection");
			Debug.printVersionNumber();
		}
	}

	@Override
	public void exit() {
	}

	@Override
	public void destroy() {
		menu = null;
	}

	@Override
	public int getID() {
		return CharSelectionScreen.ID;
	}

}
