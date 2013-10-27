package level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import entity.Entity;

public class Tile extends Entity {

	boolean blocked = false;

	boolean movable = false;

	boolean sliding = false;

	/**
	 * Animation of the tile.
	 */
	Animation animation = new Animation();

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
	
	public boolean isBlocked() {
		return blocked;
	}

	public boolean isMovable() {
		return movable;
	}

	public boolean isSliding() {
		return sliding;
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
