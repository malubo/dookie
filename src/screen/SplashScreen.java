package screen;

import org.newdawn.slick.Image;

import core.Main;
import util.Resource;

public class SplashScreen implements Screen {

	/**
	 * Screen ID.
	 */
	public static final int ID = 1;

	private Main main;

	private Image background;

	private float fadeAlpha = -1.5f;
	private float fadeSpeed = 0f;
	//private final float MAX_ALPHA = 1.6f;
	private final float MAX_ALPHA = 1f;

	
	public SplashScreen(Main main) {
		this.main = main;
	}

	@Override
	public void init() {
		background = Resource.getImage("res/image/bck1.png");
		background.setAlpha(0f);
	}

	@Override
	public void enter() {
		fadeSpeed = 0.0004f;
	}

	@Override
	public void update(int delta) {
		if (fadeAlpha >= MAX_ALPHA) {
			fadeSpeed = -fadeSpeed;
		}
		
		if(fadeAlpha < 0f && fadeSpeed < 0f) {
			// end of splash screen
			main.enterScreen(MainMenuScreen.ID); 
		}

		fadeAlpha += fadeSpeed * delta;

		if (fadeAlpha > 0f) {
			background.setAlpha(fadeAlpha);
		}
	}

	@Override
	public void render() {
		background.draw();
		
		if(Main.DEBUGG) {
			String sn = "splash";
			ui.Font.renderText(sn, Main.WIDTH / 2 - ui.Font.getMessageWidth(sn) / 2, 2);
			ui.Font.renderText(Main.VERSION, Main.WIDTH / 2 - ui.Font.getMessageWidth(Main.VERSION) / 2, Main.HEIGHT - ui.Font.HEIGHT);
		}

	}

	@Override
	public void exit() {

	}

	@Override
	public void destroy() {

	}

	public int getID() {
		return SplashScreen.ID;
	}

}