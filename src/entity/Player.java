package entity;

import input.Keys;

public class Player extends Entity {

	private int state;

	public static final int STANDING = 100;
	public static final int WALKING = 101;
	public static final int PUSHING = 102;

	private int direction;

	public static final int NORTH = 200;
	public static final int EAST = 201;
	public static final int SOUTH = 202;
	public static final int WEST = 203;

	
	
	
	
	/**
	 * Keys associated with the player.
	 */
	private Keys keys;

	/**
	 * Constructor.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param keys
	 */
	public Player(float x, float y, float width, float height, Keys keys) {
		super(x, y, width, height);
		this.keys = keys;
		init();
	}
	
	private void init() {
		state = Player.STANDING;
		direction = Player.SOUTH;
	}
	
	
	public int getDirection() {
		return direction;
	}
	
	public int getState() {
		return state;
	}
	
	private void setDirection(int direction) {
		this.direction = direction;
	}
	
	private void setState(int state) {
		this.state = state;
	}
	
	
	
	
	
	
	
	
	

	@Override
	public void update(int delta) {

	}

	@Override
	public void render() {

	}

}
