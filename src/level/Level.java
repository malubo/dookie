package level;

import org.newdawn.slick.tiled.TiledMap;

import util.Resource;

public class Level {
	public TiledMap map;

	public int tileWidth; // tile width in pixels
	public int tileHeight; // tile height in pixels

	public int mapWidth; // map width in pixels
	public int mapHeight; // map height in pixels

	public int numTilesX; // map width in tiles
	public int numTilesY; // map height in tiles
	
	public Level(String name) {
		map = Resource.getTiledMap("res/level/" + name + ".tmx");

		tileWidth = map.getTileWidth();
		tileHeight = map.getTileHeight();

		numTilesX = map.getWidth();
		numTilesY = map.getHeight();

		mapWidth = numTilesX * tileWidth;
		mapHeight = numTilesY * tileHeight;
	}
}
