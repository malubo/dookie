package level;

import input.Keys;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import core.Camera;
import core.Main;
import entity.Player;
import util.Debugg;
import util.Resource;

public class Level {

	private static final int BLOCKED_LAYER_INDEX = 0; // blocked immovable tiles
	private static final int MOVABLE_LAYER_INDEX = 1; // movable tiles
	private static final int SLIDING_LAYER_INDEX = 2; // sliding immovable floor
														// tiles
	private static final int ITEM_LAYER_INDEX = 3; // game items, keys, doors,
													// traps
	private static final int ENTITY_LAYER_INDEX = 4; // where the player and
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
					blocked.add(new Tile(x * tileWidth, y * tileHeight,
							tileWidth, tileHeight, tileImage));
				}
			}
		}

		// fill movable array
		for (int x = 0; x < numTilesX; x++) {
			for (int y = 0; y < numTilesY; y++) {
				Image tileImage = map.getTileImage(x, y, MOVABLE_LAYER_INDEX);
				if (tileImage != null) {
					movable.add(new Tile(x * tileWidth, y * tileHeight,
							tileWidth, tileHeight, tileImage));
				}
			}
		}

		// fill sliding array
		for (int x = 0; x < numTilesX; x++) {
			for (int y = 0; y < numTilesY; y++) {
				Image tileImage = map.getTileImage(x, y, SLIDING_LAYER_INDEX);
				if (tileImage != null) {
					sliding.add(new Tile(x * tileWidth, y * tileHeight,
							tileWidth, tileHeight, tileImage));
				}
			}
		}

		// identify the player position
		player = new Player(32, 32, 32, 32, this.keys, this,
				Player.PLAYER_TYPE_PRINCESS);

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
	
	public boolean isBlocked(Point p) {
		return isBlocked((int)p.getX(), (int)p.getY());
	}

	/**
	 * Checks if the target grid point is occupied by a movable tile.
	 * 
	 * @param x
	 *            X coordinate of the point.
	 * @param y
	 *            Y coordinate of the point.
	 * @return True if the point is occupied by a movable. False otherwise.
	 */
	public boolean isMovable(int x, int y) {
		for (Tile t : movable) {
			if ((int) t.getX() == x && (int) t.getY() == y) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isMovable(Point p) {
		return isMovable((int)p.getX(), (int)p.getY()); 
	}

	public void setDisplayedArea(Rectangle area) {
		this.area = area;
	}

	public Rectangle getDisplayedArea() {
		return area;
	}

	public void update(int delta) {
		player.update(delta);

		/**
		 * Update tile movements.
		 */
		for (Tile t : movable) {

			float px = player.getX();
			float py = player.getY();
			float pw = player.getWidth();
			float ph = player.getHeight();
			Point pd = player.getDestination();

			if (t.isCollingWidth(player.getBounds())) {

				float tx = t.getX();
				float ty = t.getY();
				float tw = t.getWidth();
				float th = t.getHeight();

				// horizontal pushing
				if(py == ty){
					if(tx > px) {
						// pushing from left
						t.moveTo(px + Player.WIDTH, ty);
					}
					if(tx < px) {
						// pushing from right
						t.moveTo(px - Player.WIDTH, ty);
					}
				}
				
				// vertical pushing
				if(px == tx) {
					if(ty > py) {
						// pushing from top
						t.moveTo(tx, py + Player.HEIGHT);
					}
					if(ty < py) {
						// pushing from bottom
						t.moveTo(tx, py - Player.HEIGHT);
					}
				}
			}
		}

		/**
		 * Update tile animations.
		 */
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
			Debugg.printTileGrid(this);
			camera.untranslate();

			Debugg.printTilesDisplayed(tilesDisplaying);
			Debugg.printActiveScreenName("game");
		}

	}

	private void renderLayers() {

		// Reset the tiles on screen counter.
		tilesDisplaying = 0;

		// Render the blocked layer.
		for (Tile t : blocked) {
			if (t.isCollingWidth(getDisplayedArea())) {
				t.render();
				tilesDisplaying++;
			}
		}

		// Render the movable layer.
		for (Tile t : movable) {
			if (t.isCollingWidth(getDisplayedArea())) {
				t.render();
				tilesDisplaying++;
			}
		}

		// Render the sliding layer.
		for (Tile t : sliding) {
			if (t.isCollingWidth(getDisplayedArea())) {
				t.render();
				tilesDisplaying++;
			}
		}

	}

}
