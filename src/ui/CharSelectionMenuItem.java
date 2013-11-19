package ui;

import org.newdawn.slick.Animation;

import util.Resource;

import entity.Entity;

public class CharSelectionMenuItem extends Entity {

	public static final int WIDTH = 42;
	public static final int HEIGHT = 42;

	public static final String[] NAMES = { "ghost", "skeleton", "goblin",
			"imp", "genie", "princess", "reaper", "viking" };

	/**
	 * Type of character.
	 */
	private int type;

	/**
	 * Indicator of active selection.
	 */
	private boolean selected;

	/**
	 * Animation of the entity when not selected.
	 */
	private Animation unselectedAnimation;

	/**
	 * Animation of the entity when selected.
	 */
	private Animation selectedAnimation;

	public CharSelectionMenuItem(float x, float y, int type) {
		super(x, y);
		this.type = type;
		setWidth(WIDTH);
		setHeight(HEIGHT);
		initAnimations();
	}

	private void initAnimations() {
		selectedAnimation = new Animation();
		unselectedAnimation = new Animation();

		unselectedAnimation.addFrame(Resource.playerSprites.getSprite(1, type)
				.getScaledCopy(WIDTH, HEIGHT), 100);
		selectedAnimation.addFrame(Resource.playerSprites.getSprite(0, type)
				.getScaledCopy(WIDTH, HEIGHT), 200);
		selectedAnimation.addFrame(Resource.playerSprites.getSprite(2, type)
				.getScaledCopy(WIDTH, HEIGHT), 200);
	}

	public void select() {
		selected = true;
		move(0, 5);
		setX(getX() - 2.5f);
		setWidth(getWidth() + 5);
		setHeight(getHeight() + 5);
	}

	public void deSelect() {
		selected = false;
		move(0, - 5);
		setX(getX() + 2.5f);
		setWidth(getWidth() - 5);
		setHeight(getHeight() - 5);
	}

	@Override
	public void update(int delta) {
		selectedAnimation.update(delta);
		unselectedAnimation.update(delta);
	}

	@Override
	public void render() {
		if (selected) {
			selectedAnimation.getCurrentFrame().draw(getX(), getY(), getWidth(), getHeight());
			Font.scale = 1.4f;
			Font.renderText(NAMES[type],
					getCentre().getMinX() - Font.getMessageWidth(NAMES[type])
							/ 2, getY() + HEIGHT + 10);
			Font.scale = 1.0f;
		} else {
			unselectedAnimation.draw(getX(), getY());
		}
	}
}
