package core;

import static org.lwjgl.opengl.GL11.*;
import input.Keys;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import screen.GameScreen;
import screen.MainMenuScreen;
import screen.OptionsScreen;
import screen.Screen;
import screen.SplashScreen;

public class Main {

	/**
	 * Game title.
	 */
	public static final String TITLE = "Dookie";

	/**
	 * Version.
	 */
	public static final String VERSION = "0.0.1";

	/**
	 * Width of the game canvas.
	 */
	public static final int WIDTH = 512;

	/**
	 * Height of the game canvas.
	 */
	public static final int HEIGHT = 384;

	/**
	 * Fullscreen mode.
	 */
	public static final boolean FULLSCREEN = true;

	/**
	 * Vertical sync.
	 */
	public static final boolean VSYNC = true;

	/**
	 * Frames per second cap.
	 */
	public static final int FPS_CAP = 120;

	/**
	 * Debugg mode.
	 */
	public static final boolean DEBUGG = true;

	/**
	 * Frames per second meter. Contains the current FPS.
	 */
	private int fps;

	/**
	 * Counts frames per second.
	 */
	private int fpsCounter;

	/**
	 * Time of the last FPS counter reset.
	 */
	private long lastFPS;

	/**
	 * Time of the last update.
	 */
	private long lastFrame;

	/**
	 * Active part of the game. (MenuScreen, GameScreen etc.)
	 */
	private Screen activeScreen;

	/**
	 * List of available game screens.
	 */
	private ArrayList<Screen> screens;

	/**
	 * Keyboard input.
	 */
	private Keys keys;

	/**
	 * Constructor.
	 */
	public Main() {
		init();
		initDisplay();
		initMouse();
		initOpenGL();
		initScreens();
	}

	/**
	 * Initial variables set up.
	 */
	private void init() {
		fps = 0;
		fpsCounter = 0;
		lastFPS = getTime();
		lastFrame = lastFPS;
		keys = new Keys();
	}

	/**
	 * Initial display and resolution set up.
	 */
	private void initDisplay() {
		try {
			if (FULLSCREEN) {
				Display.setDisplayMode(Display.getDesktopDisplayMode());
				Display.setFullscreen(true);
			} else {
				Display.setDisplayMode(new DisplayMode(800, 600));
			}

			// VSync.
			Display.setVSyncEnabled(VSYNC);

			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initial mouse set up.
	 */
	private void initMouse() {
		try {
			Mouse.create();
			if (FULLSCREEN) {
				Mouse.setGrabbed(true);
			}
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initial OpenGL set up.
	 */
	private void initOpenGL() {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_DEPTH_TEST);
		glClearColor(0, 0, 0, 0);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	/**
	 * Game screens creation. Screens are different game stages.
	 */
	private void initScreens() {
		screens = new ArrayList<Screen>();
		screens.add(new SplashScreen(this));
		screens.add(new MainMenuScreen(this));
		screens.add(new GameScreen(this));
		screens.add(new OptionsScreen(this));
	}

	/**
	 * Game cycle. Update + Render the current screen.
	 */
	public void loop() {
		
		// set intro screen as active screen
		enterScreen(SplashScreen.ID); 
		enterScreen(GameScreen.ID);
		
		while (!Display.isCloseRequested()) {

			// calculate delta
			int delta = getDelta();
			
			// update the FPS counter
			updateFPS();

			// clear the screen with black color
			glClear(GL11.GL_COLOR_BUFFER_BIT);

			// get input
			keys.updateKeys();

			// update
			activeScreen.update(delta);

			// render
			activeScreen.render();

			// debugg section
			if (DEBUGG) {
				
				// draw fps meter values
				ui.Font.renderText(Integer.toString(fps), 1, 1);
				
				// draw delta values
				ui.Font.renderText(Integer.toString(delta), 1, 10);
			}

			// yield
			Thread.yield();

			// update display & sync fps
			Display.update();
			Display.sync(FPS_CAP);
		}
		exit();
	}

	/**
	 * Returns time in milliseconds.
	 * 
	 * @return System time in milliseconds.
	 */
	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Returns delta. Delta is time elapsed since the last game cycle. Used for
	 * adjusting the game speed for low/high frame rates.
	 * 
	 * @return Delta in milliseconds
	 */
	private int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = currentTime;
		return delta;
	}

	/**
	 * Updates the FPS counter. Resets every second.
	 */
	private void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			fps = fpsCounter;
			fpsCounter = 0;
			lastFPS += 1000;
		}
		fpsCounter++;
	}

	/**
	 * Returns current FPS.
	 * 
	 * @return Current FPS.
	 */
	public int getFPS() {
		return fps;
	}

	/**
	 * Returns the keyboard keys.
	 * 
	 * @return Keys.
	 */
	public Keys getKeys() {
		return keys;
	}

	/**
	 * Enters a new game screen, while closing the active one.
	 * 
	 * @param screenID
	 *            ID of the screen
	 */
	public void enterScreen(int screenID) {

		// reset input keys
		keys.resetKeys();

		// exit and destroy the active screen
		if (activeScreen != null) {
			activeScreen.exit();
			activeScreen.destroy();
		}

		// find and enter the screen
		for (Screen screen : screens) {
			if (screen.getID() == screenID) {
				activeScreen = screen;
				screen.init();
				screen.enter();
			}
		}
	}

	/**
	 * Cleans up when exiting the application.
	 */
	public void exit() {
		Mouse.destroy();
		Display.destroy();
		Keyboard.destroy();
		System.out.println("Clean up done!\nExiting ...");
		System.exit(0);
	}

	/**
	 * Entry point into the program.
	 * 
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.loop();
	}
}
