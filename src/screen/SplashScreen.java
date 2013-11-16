package screen;

import org.newdawn.slick.Image;

import core.Main;
import util.Debug;
import util.Resource;

public class SplashScreen implements Screen {

	/**
	 * Screen ID.
	 */
	public static final int ID = 1;

	/**
	 * Reference to the main class. Used for managing screens, input, timing.
	 */
	private Main main;

	/**
	 * Background image.
	 */
	private Image background;

	/**
	 * Starting alpha of the background.
	 */
	private float fadeAlpha = -0.3f;
	
	/**
	 * Speed at which the background image alpha changes.
	 */
	private float fadeSpeed = 0.00125f;
	
	/**
	 * Final alpha value before it goes back down.
	 */
	private final float MAX_ALPHA = 1f;

	/**
	 * Constructor.
	 * 
	 * @param main
	 *            Main class.
	 */
	public SplashScreen(Main main) {
		this.main = main;
	}

	@Override
	public void init() {
		//background = Resource.getImage("res/image/bck.png");
		background = Resource.getImage("res/image/splash_big.png").getScaledCopy(Main.WIDTH, Main.HEIGHT); 
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

		if (fadeAlpha < 0f && fadeSpeed < 0f) {
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

		if (Main.DEBUG) {
			Debug.printActiveScreenName("splash"); 
			Debug.printVersionNumber();
		}

	}

	@Override
	public void exit() {

	}

	@Override
	public void destroy() {
		background = null;
	}

	public int getID() {
		return SplashScreen.ID;
	}

}
