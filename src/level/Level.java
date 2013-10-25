package level;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import core.Main;
import util.Resource;

public class Level {
	public TiledMap map;

	public int tileWidth; // tile width in pixels
	public int tileHeight; // tile height in pixels

	public int mapWidth; // map width in pixels
	public int mapHeight; // map height in pixels

	public int numTilesX; // map width in tiles
	public int numTilesY; // map height in tiles

	ArrayList<Tile> blocked = new ArrayList<Tile>();
	ArrayList<Tile> boxes = new ArrayList<Tile>();
	ArrayList<Tile> items = new ArrayList<Tile>();

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
					//System.out.println(x + ":" + y);
					blocked.add(new Tile(x * 32, y * 32, 32, 32));
					System.out.println("added " + x * 32 + ":" + y * 32);
				}
			}
		}
		
		
		
		// fill box array
		// fill items array

	}

	public void renderMap(float x, float y) {
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
		System.out.println("checking " + x + ":" + y);
		
		for(Tile t : blocked) {
			if((int)t.getX() == x && (int)t.getY() == y) {
				return true;
			}
		}
		
		return false;
	}
}
