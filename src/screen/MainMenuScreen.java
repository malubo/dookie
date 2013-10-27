package screen;

import ui.CenteredButtonMenu;
import core.Main;

public class MainMenuScreen implements Screen {

	/**
	 * Screen ID.
	 */
	public static final int ID = 2;

	/**
	 * Refference to the main class.
	 * Used for managing screens, input, timing.
	 */
	private Main main;

	private CenteredButtonMenu menu;

	/**
	 * Constructor.
	 * 
	 * @param main
	 */
	public MainMenuScreen(Main main) {
		this.main = main;
	}

	@Override
	public void init() {
		menu = new CenteredButtonMenu("start", "options", "exit");
	}

	@Override
	public void enter() {

	}

	@Override
	public void update(int delta) {

		// down pressed
		if (main.getKeys().down.isDown()) {
			menu.next();
		}

		// up pressed
		if (main.getKeys().up.isDown()) {
			menu.previous();
		}

		// confirm pressed
		if (main.getKeys().confirm.isDown()) {
			if (menu.getSelectedItem().equals("start")) {
				main.enterScreen(GameScreen.ID);
			} else if (menu.getSelectedItem().equals("options")) {
				main.enterScreen(OptionsScreen.ID);
			} else if (menu.getSelectedItem().equals("exit")) {
				main.exit();
			}
		}

		// exit was pressed
		if (main.getKeys().exit.wasDown()) {
			main.exit();
		}
	}

	@Override
	public void render() {
		menu.render();
		if (Main.DEBUG) {
			String sn = "main menu";
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
		return MainMenuScreen.ID;
	}

}
