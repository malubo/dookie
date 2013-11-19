package entity;

import java.util.HashMap;

import level.Level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import core.Main;
import ui.Font;
import util.Resource;
import util.Settings;
import input.Keys;

public class Player extends Entity {

	/**
	 * Player width in pixels.
	 */
	public static final int WIDTH = 32;

	/**
	 * Player height in pixels.
	 */
	public static final int HEIGHT = 32;

	/**
	 * State of the player. Used for animations.
	 */
	private State state;

	public enum State {
		standing, walking, sliding
	};

	private HashMap<Integer, Animation> animations = new HashMap<Integer, Animation>();

	static final int ANIMATION_STANDING_NORTH = 100;
	static final int ANIMATION_STANDING_EAST = 101;
	static final int ANIMATION_STANDING_SOUTH = 102;
	static final int ANIMATION_STANDING_WEST = 103;

	static final int ANIMATION_WALKING_NORTH = 200;
	static final int ANIMATION_WALKING_EAST = 201;
	static final int ANIMATION_WALKING_SOUTH = 202;
	static final int ANIMATION_WALKING_WEST = 203;

	static final int ANIMATION_SLIDING_NORTH = 300;
	static final int ANIMATION_SLIDING_EAST = 301;
	static final int ANIMATION_SLIDING_SOUTH = 302;
	static final int ANIMATION_SLIDING_WEST = 303;

	/**
	 * Walking speed.
	 */
	public static final float SPEED = 0.105f;

	/**
	 * Level associated with the player.
	 */
	Level level;

	/**
	 * Time at which was performed last action.
	 */
	private long lastAction;

	/**
	 * Time in milliseconds between any action is available. Triggered when
	 * performing any action.
	 */
	private static final long ACTION_COOLDOWN = 260;

	/**
	 * Keys associated with the player.
	 */
	Keys keys;

	/**
	 * Constructor.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param keys
	 */
	public Player(float x, float y, float width, float height, Keys keys,
			Level level) {
		super(x, y, width, height, level);
		this.keys = keys;
		this.level = level;
		
		state = State.standing;
		setDirection(Direction.south);

		loadAnimations();
		setVisible(true);

		stopMovement();
		activateCooldown();
	}

	private void loadAnimations() {
		SpriteSheet player = Resource.getSpriteSheet("res/image/player.png",
				32, 32);

		// STANDING NORTH
		Animation standingNorth = new Animation();
		standingNorth.addFrame(player.getSprite(7, Settings.playerType), 100);
		animations.put(ANIMATION_STANDING_NORTH, standingNorth);

		// STANDING EAST
		Animation standingEast = new Animation();
		standingEast.addFrame(player.getSprite(4, Settings.playerType), 100);
		animations.put(ANIMATION_STANDING_EAST, standingEast);

		// STANDING SOUTH
		Animation standingSouth = new Animation();
		standingSouth.addFrame(player.getSprite(1, Settings.playerType), 100);
		animations.put(ANIMATION_STANDING_SOUTH, standingSouth);

		// STANDING WEST
		Animation standingWest = new Animation();
		standingWest.addFrame(player.getSprite(10, Settings.playerType), 100);
		animations.put(ANIMATION_STANDING_WEST, standingWest);

		// WALKING NORTH
		Animation walkingNorth = new Animation();
		walkingNorth.addFrame(player.getSprite(6, Settings.playerType), 100);
		walkingNorth.addFrame(player.getSprite(8, Settings.playerType), 100);
		animations.put(ANIMATION_WALKING_NORTH, walkingNorth);

		// WALKING EAST
		Animation walkingEast = new Animation();
		walkingEast.addFrame(player.getSprite(3, Settings.playerType), 100);
		walkingEast.addFrame(player.getSprite(5, Settings.playerType), 100);
		animations.put(ANIMATION_WALKING_EAST, walkingEast);

		// WALKING SOUTH
		Animation walkingSouth = new Animation();
		walkingSouth.addFrame(player.getSprite(0, Settings.playerType), 100);
		walkingSouth.addFrame(player.getSprite(2, Settings.playerType), 100);
		animations.put(ANIMATION_WALKING_SOUTH, walkingSouth);

		// WALKING WEST
		Animation walkingWest = new Animation();
		walkingWest.addFrame(player.getSprite(9, Settings.playerType), 100);
		walkingWest.addFrame(player.getSprite(11, Settings.playerType), 100);
		animations.put(ANIMATION_WALKING_WEST, walkingWest);
	}

	public State getState() {
		return state;
	}

	private void setState(State state) {
		this.state = state;
	}

	@Override
	public void update(int delta) {

		// Handle input.
		handleInput(delta);

		if (!isMoving()) {
			// If the player is not moving reset the state to standing.
			setState(State.standing); 
		} else {
			move(delta);
		}

		// Update animations.
		updateAnimations(delta);
	}

	private void handleInput(long delta) {

		if (isMoving()) {
			return; // Bugger off if moving is in progress.
		}

		if (isCooldownReady()) {
			return; // Bugger off if action cool down hasn't finished.
		}

		// UP PRESSED
		if (keys.up.isDown() && !keys.down.isDown()) {
			if (getDirection() == Direction.north) {
				if (state == State.standing || state == State.walking) {
					setState(State.walking);
					startMovement(getDirection());
					activateCooldown();
				}
			}

			// Changing direction cool down.
			if (getDirection() != Direction.north && state == State.standing) {
				setDirection(Direction.north);
				// activateCooldown();
			}
		}

		// RIGHT PRESSED
		if (keys.right.isDown() && !keys.left.isDown()) {
			if (getDirection() == Direction.east) {
				if (state == State.standing || state == State.walking) {
					setState(State.walking);
					startMovement(getDirection());
					activateCooldown();
				}
			}
			
			// Changing direction cool down.
			if (getDirection() != Direction.east && state == State.standing) {
				setDirection(Direction.east);
				// activateCooldown();
			}
		}

		// DOWN PRESSED
		if (keys.down.isDown() && !keys.up.isDown()) {
			if (getDirection() == Direction.south) {
				if (state == State.standing || state == State.walking) {
					setState(State.walking);
					startMovement(getDirection());
					activateCooldown();
				}
			}
			
			// Changing direction cool down.
			if (getDirection() != Direction.south && state == State.standing) {
				setDirection(Direction.south);
				// activateCooldown();
			}
		}

		// LEFT PRESSED
		if (keys.left.isDown() && !keys.right.isDown()) {
			if (getDirection() == Direction.west) {
				if (state == State.standing || state == State.walking) {
					setState(State.walking);
					startMovement(getDirection());
					activateCooldown();
				}
			}

			// Changing direction cool down.
			if (getDirection() != Direction.west && state == State.standing) {
				setDirection(Direction.west);
				// activateCooldown();
			}
		}
	}

	private boolean isCooldownReady() {
		return (lastAction + ACTION_COOLDOWN > Main.getTime());
	}

	private void activateCooldown() {
		lastAction = Main.getTime();
	}

	/**
	 * Updates animations.
	 * 
	 * @param delta
	 *            Delta
	 */
	private void updateAnimations(long delta) {
		for (Integer an : animations.keySet()) {
			Animation animation = animations.get(an);
			animation.update(delta);
		}
	}

	@Override
	public void render() {

		if (!isVisible()) {
			return;
		}

		if (Main.DEBUG) {
			renderDebuggInfo();
		}

		switch (getDirection()) {
		case north:
			if (state == State.standing) {
				animations.get(ANIMATION_STANDING_NORTH).getCurrentFrame()
						.draw(getX(), getY());
			} else if (state == State.walking) {
				animations.get(ANIMATION_WALKING_NORTH).getCurrentFrame()
						.draw(getX(), getY());
			}
			break;
		case east:
			if (state == State.standing) {
				animations.get(ANIMATION_STANDING_EAST).getCurrentFrame()
						.draw(getX(), getY());
			} else if (state == State.walking) {
				animations.get(ANIMATION_WALKING_EAST).getCurrentFrame()
						.draw(getX(), getY());
			}
			break;
		case south:
			if (state == State.standing) {
				animations.get(ANIMATION_STANDING_SOUTH).getCurrentFrame()
						.draw(getX(), getY());
			} else if (state == State.walking) {
				animations.get(ANIMATION_WALKING_SOUTH).getCurrentFrame()
						.draw(getX(), getY());
			}
			break;
		case west:
			if (state == State.standing) {
				animations.get(ANIMATION_STANDING_WEST).getCurrentFrame()
						.draw(getX(), getY());
			} else if (state == State.walking) {
				animations.get(ANIMATION_WALKING_WEST).getCurrentFrame()
						.draw(getX(), getY());
			}
			break;
		}
	}

	private void renderDebuggInfo() {
		// PLAYER COORDS
		String simpleCoords = Integer.toString((int) this.getX() / 32) + "-"
				+ Integer.toString((int) this.getY() / 32);
		String coords = Integer.toString((int) this.getX()) + "-"
				+ Integer.toString((int) this.getY());

		Font.renderText(coords,
				getX() + WIDTH / 2 - Font.getMessageWidth(coords) / 2,
				getY() + 49);
		Font.renderText(simpleCoords,
				getX() + WIDTH / 2 - Font.getMessageWidth(simpleCoords) / 2,
				getY() + 40);

		// PLAYER STATE
		String stateString = "";
		switch (state) {
		case standing:
			stateString = "standing";
			break;
		case walking:
			stateString = "walking";
			break;
		case sliding:
			stateString = "sliding";
			break;
		}

		Font.renderText(stateString,
				getX() + WIDTH / 2 - Font.getMessageWidth(stateString) / 2,
				getY() + -10);

	}
}
