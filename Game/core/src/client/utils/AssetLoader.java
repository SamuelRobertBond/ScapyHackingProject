package client.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class AssetLoader {

	public static Texture CANNON_TEXTURE;
	public static Texture CANNONBALL_TEXTURE;
	
	public static TiledMap MAP;
	
	public static void load(){
		//CANNON_TEXTURE = new Texture(Gdx.files.internal(""));
		//CANNONBALL_TEXTURE = new Texture(Gdx.files.internal(""));
		MAP = new TmxMapLoader().load("map.tmx");
	}
	
}
