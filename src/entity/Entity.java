package entity;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class Entity {

	private float x;
	private float y;

	private float width;
	private float height;

	private boolean visible;
	private boolean finished;

	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Entity(float x, float y, float width, float height) {
		this(x, y);
		this.width = width;
		this.height = height;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isFinished() {
		return finished;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public Point getCenter() {
		return new Point((x + width) / 2, (y + height) / 2);
	}

	public void move(float x, float y) {
		this.x += x;
		this.y += y;
	}

	public void moveTo(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public boolean isCollingWidth(Shape shape) {
		return getBounds().intersects(shape) || getBounds().contains(shape);
	}

	public abstract void update(int delta);

	public abstract void render();

}
