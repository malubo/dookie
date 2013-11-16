package screen;

import util.Debug;
import core.Main;

public class CharacterSelectionScreen implements Screen {

	public static final int ID = 5;

	private Main main;

	public CharacterSelectionScreen(Main main) {
		this.main = main;
	}

	@Override
	public void init() {

	}

	@Override
	public void enter() {

	}

	@Override
	public void update(int delta) {

	}

	@Override
	public void render() {

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
	}

	@Override
	public int getID() {
		return CharacterSelectionScreen.ID;
	}

}
