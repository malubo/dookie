package level;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import core.Main;
import entity.Player;
import util.Resource;

public class Level {
	
	private static final int BLOCKED_LAYER_INDEX = 0;
	private static final int ITEM_LAYER_INDEX = 1;
	private static final int ENTITY_LAYER_INDEX = 2;
	
	public TiledMap map;

	public int tileWidth; // tile width in pixels
	public int tileHeight; // tile height in pixels

	public int mapWidth; // map width in pixels
	public int mapHeight; // map height in pixels

	public int numTilesX; // map width in tiles
	public int numTilesY; // map height in tiles

	public Player player;
	ArrayList<Tile> blocked = new ArrayList<Tile>();
	ArrayList<Tile> boxes = new ArrayList<Tile>();
	ArrayList<Tile> items = new ArrayList<Tile>();

	private Rectangle area;
	
	/**
	 * Tiles rendered counter.
	 */
	public int tilesDisplaying = 0;

	public Level(String name) {

		map = Resource.getTiledMap("res/world/" + name + ".tmx");

		tileWidth = map.getTileWidth();
		tileHeight = map.getTileHeight();

		numTilesX = map.getWidth();
		numTilesY = map.getHeight();

		mapWidth = numTilesX * tileWidth;
		mapHeight = numTilesY * tileHeight;

		// identify player position

		// fill blocked array
		for(int x = 0; x < numTilesX; x++){
			for(int y = 0; y < numTilesY; y++) {
				if(map.getTileImage(x, y, 0) != null) {
					blocked.add(new Tile(x * tileWidth, y * tileHeight, tileWidth, tileHeight));
				}
			}
		}
		
		// fill box array
		// fill items array

	}

	public void renderMap(float x, float y) {
		
		setDisplayedArea(new Rectangle(x, y, Main.WIDTH, Main.HEIGHT));
		
		if (x < 0) {
			x = 0;
		}

		if (y < 0) {
			y = 0;
		}

		// horizontal coordinates of the least and the most visible tiles
		int minTileX = (int) (x - (x % tileWidth)) / tileWidth;
		int maxTileX = minTileX + Main.WIDTH / tileWidth + 2;

		// vertical coordinates of the least and the most visible tiles
		int minTileY = (int) (y - (y % tileHeight)) / tileHeight;
		int maxTileY = minTileY + Main.HEIGHT / tileHeight + 2;

		if (maxTileX > numTilesX) {
			maxTileX = numTilesX;
		}

		if (maxTileY > numTilesY) {
			maxTileY = numTilesY;
		}

		tilesDisplaying = 0;

		renderLayer(0, x, y, minTileX, maxTileX, minTileY, maxTileY);	
	}

	public void renderLayer(int layerIndex, float x, float y, int minX,
			int maxX, int minY, int maxY) {
		for (int xAxis = minX; xAxis < maxX; xAxis++) {
			for (int yAxis = minY; yAxis < maxY; yAxis++) {
				Image tileImg = map.getTileImage(xAxis, yAxis, layerIndex);
				if (tileImg != null) {
					tileImg.clampTexture();
					tileImg.draw(-x + xAxis * tileWidth, -y + yAxis
							* tileHeight);
					tilesDisplaying++;
				}
			}
		}
	}

	public boolean isBlocked(int x, int y) {
		for(Tile t : blocked) {
			if((int)t.getX() == x && (int)t.getY() == y) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param destination Point at which the player is trying to move.
	 * @return True if the point is occupied by a movable. False otherwise.
	 */
	public boolean isPushingMovable(Point destination) {
		return false;
	}
	
	
	public void setDisplayedArea(Rectangle area) {
		this.area = area;
	}
	
	public Rectangle getDisplayedArea() {
		return area;
	}
	
}
