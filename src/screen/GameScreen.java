package screen;

import core.*;
import entity.Player;

public class GameScreen implements Screen {

	public static final int ID = 3;

	private Main main;

	private Player player;
	private Camera camera;
	
	public GameScreen(Main main) {
		this.main = main;
	}

	@Override
	public void init() {
		player = new Player(0, 0, 32, 32, main.getKeys());
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
			String sn = "game";
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
		return GameScreen.ID;
	}

}
