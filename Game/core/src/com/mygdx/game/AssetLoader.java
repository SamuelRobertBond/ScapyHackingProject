package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class AssetLoader {

	public static Texture CANNON_TEXTURE;
	public static Texture CANNON_BASE_TEXTURE;
	public static Texture CANNONBALL_TEXTURE;
	public static TextureRegion CANNONHEAD_TEXTURES[][];
	public static TextureRegion CANNONHEAD_BREAKING_TEXTURES[][];
	
	public static TiledMap MAP;
	
	public static void load(){
		
		CANNON_BASE_TEXTURE = new Texture(Gdx.files.internal("CannonBase.png"));
		CANNONBALL_TEXTURE = new Texture(Gdx.files.internal("cannonball.png"));
		
		CANNONHEAD_TEXTURES = new TextureRegion(new Texture(Gdx.files.internal("CannonHead-living.png"))).split(32, 32);
		CANNONHEAD_BREAKING_TEXTURES = new TextureRegion(new Texture(Gdx.files.internal("CannonHead-breaking.png"))).split(32, 32);
		
		MAP = new TmxMapLoader().load("map.tmx");
	}
	
}
