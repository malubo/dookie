package entity;

import input.Keys;

public class Player extends Entity {

	private Keys keys;
	
	public Player(float x, float y, float width, float height, Keys keys) {
		super(x, y, width, height);
		this.keys = keys;
	}

	@Override
	public void update(int delta) {

	}
	

	@Override
	public void render() {
		
	}

}
