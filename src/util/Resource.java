package util;

import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.ResourceLoader;

public class Resource {

	public static SpriteSheet playerSprites = getSpriteSheet("res/image/player.png", 32, 32);
	
	public static Image getImage(String path) {
		Image image = null;
		try {
			image = new Image(path, false, Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return image;
	}

	public static SpriteSheet getSpriteSheet(String path, int tileWidth, int tileHeight) {
		SpriteSheet spriteSheet = null;
		Image image = null;
		try {
			image = new Image(path, false, Image.FILTER_NEAREST);
			spriteSheet = new SpriteSheet(image, tileWidth, tileHeight);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return spriteSheet;
	}

	public static Audio getAudio(String path) {
		Audio audio = null;
		String extention = path.substring(path.lastIndexOf('.') + 1).toUpperCase();
		try {
			audio = AudioLoader.getAudio(extention, ResourceLoader.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return audio;
	}
	
	public static TiledMap getTiledMap(String path){
		TiledMap map = null;
		try {
			map = new TiledMap(path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return map;
	}

}
