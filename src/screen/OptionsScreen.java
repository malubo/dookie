package screen;

import core.Main;

public class OptionsScreen implements Screen {

	public static final int ID = 4;

	private Main main;

	public OptionsScreen(Main main) {
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
		// exit
		if (main.getKeys().exit.wasDown()) {
			main.enterScreen(MainMenuScreen.ID);
		}
	}

	@Override
	public void render() {
		if (Main.DEBUGG) {
			String sn = "options";
			ui.Font.renderText(sn, Main.WIDTH / 2 - ui.Font.getMessageWidth(sn)
					/ 2, 2);
			ui.Font.renderText(Main.VERSION,
					Main.WIDTH / 2 - ui.Font.getMessageWidth(Main.VERSION) / 2,
					Main.HEIGHT - ui.Font.HEIGHT);
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
		return OptionsScreen.ID;
	}

}
