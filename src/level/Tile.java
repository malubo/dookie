package level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import entity.Entity;

public class Tile extends Entity {

	/**
	 * Animation of the tile.
	 */
	private Animation animation = new Animation();

	public Tile(float x, float y, float width, float height, Image image, Level level) {
		super(x, y, width, height, level);
		animation.addFrame(image, 100);
		setVisible(true);
	}
	
	@Override
	public void update(int delta) {
		animation.update(delta);
		
		if(isMoving()) {
			move(delta);
		}
	}

	@Override
	public void render() {
		if (isVisible()) {
			animation.getCurrentFrame().draw(getX(), getY());
		}
	}
}
