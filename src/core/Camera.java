package core;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.geom.Point;

public class Camera {

	public float x;
	public float y;
	
	private  int mapWidth;
	private  int mapHeight;

	public Camera(float x, float y, int mapWidth, int mapHeight) {
		this.x = x;
		this.y = y;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
	}

	public void centerOn(float x, float y) {
		this.x = x - Main.WIDTH / 2;
		this.y = y - Main.HEIGHT / 2;

		if (this.x < 0) {
			this.x = 0;
		}

		if (this.x + Main.WIDTH > mapWidth) {
			this.x = mapWidth - Main.WIDTH;
		}

		if (this.y < 0) {
			this.y = 0;
		}

		if (this.y + Main.HEIGHT > mapHeight) {
			this.y = mapHeight - Main.HEIGHT;
		}
	}
	
	public void centerOn(Point point) {
		centerOn(point.getX(), point.getY()); 
	}
	/*
	public void centerOn(Entity entity) {
		
	}
	*/
	public void translate() {
		GL11.glTranslatef(-x, -y, 0);
	}

	public void untranslate() {
		GL11.glTranslatef(x, y, 0);
	}

}
