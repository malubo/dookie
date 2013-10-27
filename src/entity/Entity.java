package entity;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * Abstract class designed to be the ancestor for all rectangular game objects.
 * 
 * @author Luboš
 * 
 */
public abstract class Entity {

	/**
	 * X-axis coordinate in pixels.
	 */
	private float x;

	/**
	 * Y-axis coordinate in pixels.
	 */
	private float y;

	/**
	 * Width of the entity in pixels.
	 */
	private float width;

	/**
	 * Height in pixels.
	 */
	private float height;

	/**
	 * Indicator of visibility. If visible, will be rendered.
	 */
	private boolean visible = true;

	/**
	 * Indicator of movement.
	 */
	private boolean moving = false;

	/**
	 * Constructor.
	 * 
	 * @param x
	 *            X-axis coordinate in pixels.
	 * @param y
	 *            Y-axis coordinate in pixels.
	 */
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructor.
	 * 
	 * @param x
	 *            X-axis coordinate in pixels
	 * @param y
	 *            Y-axis coordinate in pixels
	 * @param width
	 *            Width in pixels
	 * @param height
	 *            Height in pixels
	 */
	public Entity(float x, float y, float width, float height) {
		this(x, y);
		this.width = width;
		this.height = height;
	}

	/**
	 * Sets the x-axis coordinate.
	 * 
	 * @param x
	 *            X-axis coordinate.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Sets the y-axis coordinate.
	 * 
	 * @param y
	 *            Y-axis coordinate.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Sets the width in pixels.
	 * 
	 * @param width
	 *            Width in pixels.
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * Sets the height in pixels.
	 * 
	 * @param height
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * Sets the visibility indicator.
	 * 
	 * @param visible
	 *            Visibility indicator.<br>
	 *            If set to false, will not be rendered.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Sets the movement indicator.
	 * 
	 * @param moving
	 *            Movement indicator.
	 */
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	/**
	 * Returns the x-axis coordinate.
	 * 
	 * @return X-axis coordinate.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Returns the y-axis coordinate.
	 * 
	 * @return Y-axis coordinate.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Returns the width.
	 * 
	 * @return Width.
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Returns the height.
	 * 
	 * @return Height.
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Returns the visibility indicator status.
	 * 
	 * @return True if visible.<br>
	 *         False if invisible.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Returns the movement indicator status.
	 * 
	 * @return True if moving.<br>
	 *         False if not moving.
	 */
	public boolean isMoving() {
		return moving;
	}

	/**
	 * Returns the bounds of the entity.
	 * 
	 * @return Rectangular bounds of the entity.
	 */
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	/**
	 * Returns the centre of the entity.
	 * 
	 * @return Centre point of the entity
	 */
	public Point getCentre() {
		return new Point(x + (width / 2), y + (height / 2));
	}

	/**
	 * Changes the location of the entity. Moves the entity by the given amount
	 * of pixels.
	 * 
	 * @param x
	 * @param y
	 */
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * Changes the location of the entity. Moves the entity to a new location.
	 * 
	 * @param x
	 *            X-axis coordinate in pixels
	 * @param y
	 *            Y-axis coordinate in pixels
	 */
	public void moveTo(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Determines if another shape collides with the entity.
	 * 
	 * @param shape
	 * @return True if the shape collides with the entity.
	 */
	public boolean isCollingWidth(Shape shape) {
		return getBounds().intersects(shape) || getBounds().contains(shape);
	}

	/**
	 * Updates the state, actions and animations of the entity.
	 * 
	 * @param delta
	 *            Delay between frames in milliseconds.
	 */
	public abstract void update(int delta);

	/**
	 * Renders the entity.
	 */
	public abstract void render();

}
