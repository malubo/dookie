package level;

import entity.Entity;

public class Tile extends Entity {
	
	boolean blocked = false;
	
	boolean movable = false;
	
	public Tile(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	@Override
	public void update(int delta) {
		// update animation
	}

	@Override
	public void render() {
		
	}
}
