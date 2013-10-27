package level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

import entity.Entity;
import entity.Player;

public class Tile extends Entity {

	boolean blocked = false;

	boolean movable = false;

	boolean sliding = false;

	/**
	 * Animation of the tile.
	 */
	Animation animation = new Animation();

	/**
	 * Destination coords when the tile is moving.
	 */
	private Point destination = null;

	/**
	 * Indicator of movement.
	 */
	private boolean moving = false;

	public Tile(float x, float y, float width, float height, Image image) {
		super(x, y, width, height);
		animation.addFrame(image, 100);
		setVisible(true);
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	public void setSliding(boolean sliding) {
		this.sliding = sliding;
	}
	
	public void setDestination(Point destination) {
		this.destination = destination;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public boolean isMovable() {
		return movable;
	}

	public boolean isSliding() {
		return sliding;
	}
	
	public Point getDestination() {
		return destination;
	}
	
	public boolean isMoving() {
		return moving;
	}

	@Override
	public void update(int delta) {
		animation.update(delta);
	}

	@Override
	public void render() {
		if (isVisible()) {
			animation.getCurrentFrame().draw(getX(), getY());
		}
	}
}
