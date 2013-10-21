package level;

import java.util.ArrayList;

import org.newdawn.slick.Image;
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
	
	ArrayList<Tile> blocked;
	ArrayList<Tile> boxes;
	ArrayList<Tile> items;
	
	public int tilesDisplaying = 0;
	
	public Level(String name) {
		map = Resource.getTiledMap("res/level/" + name + ".tmx");

		tileWidth = map.getTileWidth();
		tileHeight = map.getTileHeight();

		numTilesX = map.getWidth();
		numTilesY = map.getHeight();

		mapWidth = numTilesX * tileWidth;
		mapHeight = numTilesY * tileHeight;
		
		// identify player position
		// fill blocked array
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
		//renderLayer(BLOCKED_LAYER_INDEX, x, y, minTileX, maxTileX, minTileY, maxTileY);
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
	
}
