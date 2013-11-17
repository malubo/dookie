package level;

import input.Keys;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import core.Camera;
import core.Main;
import entity.Entity.Type;
import entity.Player;
import util.Debug;
import util.Resource;

public class Level {

	static final int BLOCKED_LAYER_INDEX = 0; // blocked immovable tiles
	static final int MOVABLE_LAYER_INDEX = 1; // movable tiles
	static final int SLIDING_LAYER_INDEX = 2; // sliding immovable floor
												// tiles
	static final int ITEM_LAYER_INDEX = 3; // game items, keys, doors,
											// traps
	static final int ENTITY_LAYER_INDEX = 4; // where the player and
												// npc's are at

	public TiledMap map; // tiled map used to load up the layer lists of tiles

	public int tileWidth; // tile width in pixels
	public int tileHeight; // tile height in pixels

	public int mapWidth; // map width in pixels
	public int mapHeight; // map height in pixels

	public int numTilesX; // map width in tiles
	public int numTilesY; // map height in tiles

	private Player player;
	private Keys keys;
	private Camera camera;

	private ArrayList<Tile> blocked = new ArrayList<Tile>();
	private ArrayList<Tile> movable = new ArrayList<Tile>();
	private ArrayList<Tile> sliding = new ArrayList<Tile>();
	private ArrayList<Tile> items = new ArrayList<Tile>();

	/**
	 * Displayed area of the map. In pixels.
	 */
	private Rectangle area;

	/**
	 * Tiles rendered counter.
	 */
	public int tilesDisplaying = 0;

	public Level(String name, Keys keys) {

		map = Resource.getTiledMap("res/world/" + name + ".tmx");
		this.keys = keys;

		tileWidth = map.getTileWidth();
		tileHeight = map.getTileHeight();

		numTilesX = map.getWidth();
		numTilesY = map.getHeight();

		mapWidth = numTilesX * tileWidth;
		mapHeight = numTilesY * tileHeight;

		camera = new Camera(0, 0, mapWidth, mapHeight);

		// fill blocked array
		for (int x = 0; x < numTilesX; x++) {
			for (int y = 0; y < numTilesY; y++) {
				Image tileImage = map.getTileImage(x, y, BLOCKED_LAYER_INDEX);
				if (tileImage != null) {
					Tile t = new Tile(x * tileWidth, y * tileHeight, tileWidth,
							tileHeight, tileImage, this);
					t.setType(Type.blocked);
					blocked.add(t);
				}
			}
		}

		// fill movable array
		for (int x = 0; x < numTilesX; x++) {
			for (int y = 0; y < numTilesY; y++) {
				Image tileImage = map.getTileImage(x, y, MOVABLE_LAYER_INDEX);
				if (tileImage != null) {
					Tile t = new Tile(x * tileWidth, y * tileHeight, tileWidth,
							tileHeight, tileImage, this);
					t.setType(Type.movable);
					movable.add(t);
				}
			}
		}

		// fill sliding array
		for (int x = 0; x < numTilesX; x++) {
			for (int y = 0; y < numTilesY; y++) {
				Image tileImage = map.getTileImage(x, y, SLIDING_LAYER_INDEX);
				if (tileImage != null) {
					Tile t = new Tile(x * tileWidth, y * tileHeight, tileWidth,
							tileHeight, tileImage, this);
					t.setType(Type.sliding);
					sliding.add(t);
				}
			}
		}

		// fill items array
		for (int x = 0; x < numTilesX; x++) {
			for (int y = 0; y < numTilesY; y++) {
				Image tileImage = map.getTileImage(x, y, ITEM_LAYER_INDEX);
				if (tileImage != null) {
					Tile t = new Tile(x * tileWidth, y * tileHeight,
							tileWidth, tileHeight, tileImage, this);
					t.setType(Type.item);
					items.add(t);
				}
			}
		}

		// identify the player position
		player = new Player(160, 192, 32, 32, this.keys, this,
				Player.PLAYER_TYPE_VIKING);
		player.setType(Type.player); 

	}

	/**
	 * Checks if the target grid point is occupied by a blocked tile.
	 * 
	 * @param x
	 *            X coordinate of the point.
	 * @param y
	 *            Y coordinate of the point.
	 * @return
	 */
	public boolean isBlocked(int x, int y) {

		/*
		 * Check if the point is blocked.
		 */
		for (Tile t : blocked) {
			if ((int) t.getX() == x && (int) t.getY() == y) {
				return true;
			}
		}

		/*
		 * Check if the point is movable. If movable check if it can be moved
		 * safely. If it can be moved, mark the movable as moving and return
		 * true.
		 */

		return false;
	}

	public void setDisplayedArea(Rectangle area) {
		this.area = area;
	}

	public Rectangle getDisplayedArea() {
		return area;
	}

	public Tile getBlocked(Point p) {
		for (Tile t : blocked) {
			if ((int) t.getX() == p.getX() && (int) t.getY() == p.getY()) {
				return t;
			}
		}
		return null;
	}

	public Tile getMovable(Point p) {
		for (Tile t : movable) {
			if ((int) t.getX() == p.getX() && (int) t.getY() == p.getY()) {
				return t;
			}

			// Returns the destination of the movable.
			// Prevents collisions and movement errors.
			if (t.getDestination() != null) {
				if ((int) t.getDestination().getX() == p.getX()
						&& (int) t.getDestination().getY() == p.getY()) {
					return t;
				}
			}
		}
		return null;
	}

	public Tile getSliding(Point p) {
		for (Tile t : sliding) {
			if ((int) t.getX() == p.getX() && (int) t.getY() == p.getY()) {
				return t;
			}
		}
		return null;
	}

	public void update(int delta) {
		player.update(delta);

		for (Tile t : movable) {
			t.update(delta);
		}

	}

	public void render() {

		// center on player
		camera.centerOn(player.getCentre());

		// update the rendered area rectangle
		setDisplayedArea(new Rectangle(camera.x, camera.y, Main.WIDTH,
				Main.HEIGHT));

		camera.translate();
		renderLayers(); // render the map and objects
		player.render(); // render the player
		camera.untranslate();

		if (Main.DEBUG) {
			camera.translate();
			Debug.printTileGrid(this);
			camera.untranslate();

			Debug.printTilesDisplayed(tilesDisplaying);
		}

	}

	private void renderLayers() {

		// Reset the tiles on screen counter.
		tilesDisplaying = 0;

		// Render the blocked array.
		for (Tile t : blocked) {
			if (t.isCollidingWidth(getDisplayedArea())) {
				t.render();
				tilesDisplaying++;
			}
		}

		// Render the sliding array.
		for (Tile t : sliding) {
			if (t.isCollidingWidth(getDisplayedArea())) {
				t.render();
				tilesDisplaying++;
			}
		}

		// Render the items array.
		for (Tile t : items) {
			if (t.isCollidingWidth(getDisplayedArea())) {
				t.render();
				tilesDisplaying++;
			}
		}

		// Render the movable array.
		for (Tile t : movable) {
			if (t.isCollidingWidth(getDisplayedArea())) {
				t.render();
				tilesDisplaying++;
			}
		}

	}

}
