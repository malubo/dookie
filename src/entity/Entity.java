package entity;

import level.Level;
import level.Tile;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * Abstract class designed to be the ancestor for all rectangular game objects.
 * 
 * @author Luboš Malý
 * 
 */
public abstract class Entity {

	/**
	 * Speed constant.
	 */
	public static final float SPEED = 0.105f;

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
	 * Level that the entity exists in.
	 */
	private Level level;

	/**
	 * Indicator of visibility. If visible, will be rendered.
	 */
	private boolean visible = true;

	/**
	 * Indicator of movement.
	 */
	private boolean moving = false;

	/**
	 * Direction enumerate.
	 */
	public enum Direction {
		north, east, south, west
	};

	/**
	 * Direction which the entity is facing.
	 */
	private Direction direction;

	/**
	 * Type enumerate.
	 */
	public enum Type {
		player, blocked, movable, sliding, item
	};

	/**
	 * Entity type.
	 */
	private Type type;

	/**
	 * Destination point on the grid.
	 */
	private Point destination;

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
	 *            X-axis coordinate in pixels.
	 * @param y
	 *            Y-axis coordinate in pixels.
	 * @param width
	 *            Width in pixels.
	 * @param height
	 *            Height in pixels.
	 */
	public Entity(float x, float y, float width, float height, Level level) {
		this(x, y);
		this.width = width;
		this.height = height;
		this.level = level;
	}

	/**
	 * Starts movement. Determines the destination point. Turns on the movement
	 * indicator. <br>
	 * Direction is specified because only player has accurate direction.
	 * 
	 * @param direction
	 *            Direction of the movement. Player's direction in most cases.
	 */
	public void startMovement(Direction direction) {

		// Set the movement direction.
		setDirection(direction);

		// Set a new destination point.
		setDestination(getNewDestinationPoint((int) getX(), (int) getY()));

		// If the destination is different from current position start the
		// movement.
		if (getDestination().getX() != getX()
				| getDestination().getY() != getY()) {
			setMoving(true);
		} else {
			stopMovement();
		}
	}

	/**
	 * Calculates the destination point. Takes into account sliding tiles.
	 * 
	 * @param x
	 *            X coordinate of current position.
	 * @param y
	 *            Y coordinate of current position.
	 * @return Definitive destination point.<br>
	 *         Returns null if the path is blocked.
	 */
	private Point getNewDestinationPoint(int x, int y) {

		Point currentPosition;
		Point newDestination;
		Point behindNewDestination;

		// New destination X, Y coordinates.
		int destinationX = x;
		int destinationY = y;

		// Behind the destination X, Y coordinates.
		int behindDestinationX = x;
		int behindDestinationY = y;

		// Find out the coordinates of the destination.
		switch (getDirection()) {
		case north:
			destinationY -= getHeight();
			behindDestinationY -= getHeight() * 2;
			break;
		case east:
			destinationX += getWidth();
			behindDestinationX += getWidth() * 2;
			break;
		case south:
			destinationY += getHeight();
			behindDestinationY += getHeight() * 2;
			break;
		case west:
			destinationX -= getWidth();
			behindDestinationX -= getWidth() * 2;
			break;
		}

		currentPosition = new Point(x, y);
		
		newDestination = new Point(destinationX, destinationY);

		behindNewDestination = new Point(behindDestinationX,
				behindDestinationY);

		// Destination point is blocked.
		if (level.getBlocked(newDestination) != null) {
			return currentPosition;
		}

		// Standing on a sliding tile.
		if (level.getSliding(currentPosition) != null) {

			// Can't push a movable from sliding tile.
			if (level.getMovable(newDestination) != null) {
				return currentPosition;
			}
		}

		// Destination point is movable.
		if (level.getMovable(newDestination) != null) {

			// One space behind destination is blocked.
			if (level.getBlocked(behindNewDestination) != null) {
				return new Point(x, y);
			}

			// One space behind destination is movable.
			if (level.getMovable(behindNewDestination)  != null) {
				return new Point(x, y);
			}

			// Get the movable to move.
			Tile movable = level.getMovable(newDestination);
			if (movable != null) {
				movable.startMovement(getDirection());
			}
			
		}

		// Destination is sliding.
		else if (level.getSliding(newDestination) != null) {

			if (level.getMovable(behindNewDestination) == null) {
				return getNewDestinationPoint(destinationX, destinationY);
			}
			
		}

		return newDestination;
	}

	/**
	 * Stops the movement.
	 */
	public void stopMovement() {
		setDestination(null);
		setMoving(false);
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
	 * Sets the direction.
	 * 
	 * @param direction
	 *            Direction.
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            Type.
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Sets the destination point.
	 * 
	 * @param destination
	 *            Destination point.
	 */
	public void setDestination(Point destination) {
		this.destination = destination;
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
	 * Returns the direction.
	 * 
	 * @return Direction.
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Returns type.
	 * 
	 * @return Type.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Returns the destination point.
	 * 
	 * @return Destination point.
	 */
	public Point getDestination() {
		return destination;
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
	 * Returns the centre point.
	 * 
	 * @return Centre point of the entity.
	 */
	public Point getCentre() {
		return new Point(x + (width / 2), y + (height / 2));
	}

	/**
	 * Moves the entity in it's direction according to it's speed and delta.<br>
	 * Takes into account movement indicator, destination point and stops when
	 * the entity reaches the destination.
	 * 
	 * @param delta
	 */
	public void move(int delta) {

		// New X, Y coordinates.
		float newX = getX();
		float newY = getY();

		/*
		 * Move the entity in proper direction.
		 */
		switch (getDirection()) {
		case north:
			newY -= SPEED * delta;
			if (newY < getDestination().getY()) {
				newY = getDestination().getY();
				stopMovement();
			}
			break;
		case east:
			newX += SPEED * delta;
			if (newX > getDestination().getX()) {
				newX = getDestination().getX();
				stopMovement();
			}
			break;
		case south:
			newY += SPEED * delta;
			if (newY > getDestination().getY()) {
				newY = getDestination().getY();
				stopMovement();
			}
			break;
		case west:
			newX -= SPEED * delta;
			if (newX < getDestination().getX()) {
				newX = getDestination().getX();
				stopMovement();
			}
			break;
		}
		this.moveTo(newX, newY);
	}

	/**
	 * Changes the location of the entity. Moves the entity by the given amount
	 * of pixels.
	 * 
	 * @param x
	 *            X-axis coordinate.
	 * @param y
	 *            Y-axis coordinate.
	 */
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * Changes the location of the entity. Moves the entity to a new location.
	 * 
	 * @param x
	 *            X-axis coordinate.
	 * @param y
	 *            Y-axis coordinate.
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
	public boolean isCollidingWidth(Shape shape) {
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
