package entity;

import java.util.HashMap;

import level.Level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import core.Main;
import ui.Font;
import util.Resource;
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

	/**
	 * The direction the player is facing.
	 */
	private Direction direction;

	public enum Direction {
		north, east, south, west
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

	int playerType;

	public static final int PLAYER_TYPE_GHOST = 0;
	public static final int PLAYER_TYPE_SKELETON = 1;
	public static final int PLAYER_TYPE_GOBLIN = 2;
	public static final int PLAYER_TYPE_IMP = 3;
	public static final int PLAYER_TYPE_GENIE = 4;
	public static final int PLAYER_TYPE_PRINCESS = 5;
	public static final int PLAYER_TYPE_REAPER = 6;
	public static final int PLAYER_TYPE_VIKING = 7;

	/**
	 * Walking speed.
	 */
	static final float WALKING_SPEED = 0.0992f;

	/**
	 * Level associated with the player.
	 */
	Level level;

	/**
	 * Indicator of player's movement.
	 */
	private boolean moving = false;

	/**
	 * Destination coords when the player is moving.
	 */
	private Point destination = null;

	/**
	 * Time at which was performed last action.
	 */
	private long lastAction;

	/**
	 * Time in miliseconds between any action is available. Triggered when
	 * performing any action.
	 */
	private static final long ACTION_COOLDOWN = 295;

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
			Level level, int playerType) {
		super(x, y, width, height);
		this.keys = keys;
		this.level = level;
		this.playerType = playerType;
		lastAction = Main.getTime();
		init();
	}

	private void init() {
		state = State.standing;
		direction = Direction.south;

		SpriteSheet player = Resource.getSpriteSheet("res/image/player.png",
				32, 32);

		// STANDING NORTH
		Animation standingNorth = new Animation();
		standingNorth.addFrame(player.getSprite(7, playerType), 100);
		animations.put(ANIMATION_STANDING_NORTH, standingNorth);

		// STANDING EAST
		Animation standingEast = new Animation();
		standingEast.addFrame(player.getSprite(4, playerType), 100);
		animations.put(ANIMATION_STANDING_EAST, standingEast);

		// STANDING SOUTH
		Animation standingSouth = new Animation();
		standingSouth.addFrame(player.getSprite(1, playerType), 100);
		animations.put(ANIMATION_STANDING_SOUTH, standingSouth);

		// STANDING WEST
		Animation standingWest = new Animation();
		standingWest.addFrame(player.getSprite(10, playerType), 100);
		animations.put(ANIMATION_STANDING_WEST, standingWest);

		// WALKING NORTH
		Animation walkingNorth = new Animation();
		walkingNorth.addFrame(player.getSprite(6, playerType), 100);
		walkingNorth.addFrame(player.getSprite(8, playerType), 100);
		animations.put(ANIMATION_WALKING_NORTH, walkingNorth);

		// WALKING EAST
		Animation walkingEast = new Animation();
		walkingEast.addFrame(player.getSprite(3, playerType), 100);
		walkingEast.addFrame(player.getSprite(5, playerType), 100);
		animations.put(ANIMATION_WALKING_EAST, walkingEast);

		// WALKING SOUTH
		Animation walkingSouth = new Animation();
		walkingSouth.addFrame(player.getSprite(0, playerType), 100);
		walkingSouth.addFrame(player.getSprite(2, playerType), 100);
		animations.put(ANIMATION_WALKING_SOUTH, walkingSouth);

		// WALKING WEST
		Animation walkingWest = new Animation();
		walkingWest.addFrame(player.getSprite(9, playerType), 100);
		walkingWest.addFrame(player.getSprite(11, playerType), 100);
		animations.put(ANIMATION_WALKING_WEST, walkingWest);

	}

	public Direction getDirection() {
		return direction;
	}

	public State getState() {
		return state;
	}

	private void setDirection(Direction direction) {
		this.direction = direction;
	}

	private void setState(State state) {
		this.state = state;
	}

	@Override
	public void update(int delta) {

		// get current game time
		long time = Main.getTime();

		// get keyboard input
		handleInput(delta, time);

		// reset state
		if (!moving) {
			setState(State.standing);
		}

		/*
		 * Player is moving. Destination point is not set.
		 */
		if (moving && destination == null) {

			int targetX = (int) getX();
			int targetY = (int) getY();

			switch (direction) {
			case north:
				targetX = (int) getX();
				targetY = ((int) getY()) - 32;
				break;
			case east:
				targetX = ((int) getX()) + 32;
				targetY = (int) getY();
				break;
			case south:
				targetX = (int) getX();
				targetY = ((int) getY()) + 32;
				break;
			case west:
				targetX = ((int) getX()) - 32;
				targetY = (int) getY();
				break;
			}

			this.destination = new Point(targetX, targetY);
		}
		
		/*
		 * Cancel destination if path is blocked.
		 */
		if(destination != null && level.isBlocked((int)destination.getX(), (int)destination.getY())) {
			destination = null;
			moving = false;
			state = State.standing;
		}
		
		/*
		 * Player is moving. Destination point is set. Move towards the
		 * destination point.
		 */
		if (moving && destination != null) {

			state = State.walking;

			float newX = getX();
			float newY = getY();

			switch (direction) {
			case north:
				newY -= WALKING_SPEED * delta;
				if (newY < destination.getY()) {
					newY = destination.getY();
					moving = false;
					destination = null;
				}
				break;
			case east:
				newX += WALKING_SPEED * delta;
				if (newX > destination.getX()) {
					newX = destination.getX();
					moving = false;
					destination = null;
				}
				break;
			case south:
				newY += WALKING_SPEED * delta;
				if (newY > destination.getY()) {
					newY = destination.getY();
					moving = false;
					destination = null;
				}
				break;
			case west:
				newX -= WALKING_SPEED * delta;
				if (newX < destination.getX()) {
					newX = destination.getX();
					moving = false;
					destination = null;
				}
				break;
			}

			this.moveTo(newX, newY);
		}

		// update animations
		updateAnimations(delta);
	}

	private void handleInput(long delta, long time) {

		if (moving) {
			return; // bugger off if moving in progress
		}

		if (lastAction + ACTION_COOLDOWN > time) {
			return; // bugger off if action cooldown hasn't finished
		}

		// UP PRESSED
		if (keys.up.isDown() && !keys.down.isDown()) {
			if (direction == Direction.north) {
				setState(State.walking);
				moving = true;
			} else {
				setDirection(Direction.north);
			}
			lastAction = time;
		}

		// RIGHT PRESSED
		if (keys.right.isDown() && !keys.left.isDown()) {
			if (direction == Direction.east) {
				setState(State.walking);
				moving = true;
			} else {
				setDirection(Direction.east);
			}
			lastAction = time;
		}

		// DOWN PRESSED
		if (keys.down.isDown() && !keys.up.isDown()) {
			if (direction == Direction.south) {
				setState(State.walking);
				moving = true;
			} else {
				setDirection(Direction.south);
			}
			lastAction = time;
		}

		// LEFT PRESSED
		if (keys.left.isDown() && !keys.right.isDown()) {
			if (direction == Direction.west) {
				setState(State.walking);
				moving = true;
			} else {
				setDirection(Direction.west);
			}
			lastAction = time;
		}
	}

	/**
	 * Updates animations.
	 * 
	 * @param delta
	 */
	private void updateAnimations(long delta) {
		for (Integer an : animations.keySet()) {
			Animation animation = animations.get(an);
			animation.update(delta);
		}
	}

	@Override
	public void render() {

		if (Main.DEBUGG) {
			renderDebuggInfo();
		}

		if (state == State.standing) {
			switch (direction) {
			case north:
				animations.get(ANIMATION_STANDING_NORTH).getCurrentFrame()
						.draw(getX(), getY());
				break;
			case east:
				animations.get(ANIMATION_STANDING_EAST).getCurrentFrame()
						.draw(getX(), getY());
				break;
			case south:
				animations.get(ANIMATION_STANDING_SOUTH).getCurrentFrame()
						.draw(getX(), getY());
				break;
			case west:
				animations.get(ANIMATION_STANDING_WEST).getCurrentFrame()
						.draw(getX(), getY());
				break;
			}
		}

		if (state == State.walking) {
			switch (direction) {
			case north:
				animations.get(ANIMATION_WALKING_NORTH).getCurrentFrame()
						.draw(getX(), getY());
				break;
			case east:
				animations.get(ANIMATION_WALKING_EAST).getCurrentFrame()
						.draw(getX(), getY());
				break;
			case south:
				animations.get(ANIMATION_WALKING_SOUTH).getCurrentFrame()
						.draw(getX(), getY());
				break;
			case west:
				animations.get(ANIMATION_WALKING_WEST).getCurrentFrame()
						.draw(getX(), getY());
				break;
			}
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

		/*
		 * 
		 * // DESTINATION INFO if (destination != null) { String targetCoords =
		 * Integer .toString((int) destination.getX() / 32) + "-" +
		 * Integer.toString((int) destination.getY() / 32);
		 * 
		 * Font.renderText( targetCoords, getX() + WIDTH / 2 -
		 * Font.getMessageWidth(targetCoords) / 2, getY() + 58);
		 * 
		 * } else { String targetCoords = "no destination"; Font.renderText(
		 * targetCoords, getX() + WIDTH / 2 - Font.getMessageWidth(targetCoords)
		 * / 2, getY() + 58); }
		 */
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
