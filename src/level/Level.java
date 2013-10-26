package level;

import input.Keys;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import core.Camera;
import core.Main;
import entity.Player;
import util.Debugg;
import util.Resource;

public class Level {

	private static final int BLOCKED_LAYER_INDEX = 0;
	private static final int MOVABLE_LAYER_INDEX = 1;
	private static final int SLIDING_LAYER_INDEX = 2;
	private static final int ENTITY_LAYER_INDEX = 10;

	public TiledMap map;

	public int tileWidth; // tile width in pixels
	public int tileHeight; // tile height in pixels

	public int mapWidth; // map width in pixels
	public int mapHeight; // map height in pixels

	public int numTilesX; // map width in tiles
	public int numTilesY; // map height in tiles

	private Player player;
	private Keys keys;
	private Camera camera;

	ArrayList<Tile> blocked = new ArrayList<Tile>();
	ArrayList<Tile> movable = new ArrayList<Tile>();
	ArrayList<Tile> sliding = new ArrayList<Tile>();

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
		player = new Player(32, 32, 32, 32, keys, this,
				Player.PLAYER_TYPE_GENIE);

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

	public void setDisplayedArea(Rectangle area) {
		this.area = area;
	}

	public Rectangle getDisplayedArea() {
		return area;
	}

	public void update(int delta) {
		player.update(delta);
	}

	public void render() {

		// center on player
		camera.centerOn(player.getCenter());

		// update the rendered area rectangle
		setDisplayedArea(new Rectangle(camera.x, camera.y, Main.WIDTH,
				Main.HEIGHT));

		camera.translate();
		renderLayers(); // render the map and objects
		player.render(); // render the player
		camera.untranslate();

		if (Main.DEBUGG) {
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
