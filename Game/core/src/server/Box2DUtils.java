package server;

import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import server.entities.Spawn;


public class Box2DUtils {
	
	public static World world;

	public static Body createBody(float x, float y, BodyType type){
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = type;
		bodyDef.position.set(new Vector2(x, y));
		bodyDef.fixedRotation = true;
		bodyDef.linearDamping = 100;
		
		return world.createBody(bodyDef);
	}
	
	
	public static Fixture createRectangularFixture(Body body, float width, float height, boolean isSensor){
		
		FixtureDef def = new FixtureDef();
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		def.isSensor = isSensor;
		def.shape = shape;
		def.density = .2f;
		def.restitution = 0;
		def.friction = .1f;
		
		Fixture fixture = body.createFixture(def);
		shape.dispose();
		
		return fixture;
	}
	
	public static void generateSpawn(){
		
	}
	
	
	public static Fixture createCircularFixture(Body body, float radius){
		
		FixtureDef def = new FixtureDef();
		
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		
		def.shape = shape;
		def.density = .2f;
		def.restitution = 0;
		def.friction = .1f;
		
		Fixture fixture = body.createFixture(def);
		shape.dispose();
		
		return fixture;
		
	}
	
	
	public static Spawn[] spawnTileMapWalls(TiledMap map, World world){
		
		MapLayers layers = map.getLayers();
		MapLayer layer = layers.get("walls");
		
		MapObjects walls = layer.getObjects();
		
		for(MapObject object : walls){
			
			MapProperties p = object.getProperties();
			
			float x = (Float)p.get("x");
			float y = (Float)p.get("y");
			float width = (Float)p.get("width");
			float height = (Float)p.get("height");
			
			width /= 2;
			height /= 2;
			
			x += width;
			y += height;
			
			Body body = Box2DUtils.createBody(x, y, BodyType.StaticBody);
			Box2DUtils.createRectangularFixture(body, width, height, false);
			
		}
		
		layer = layers.get("spawns");
		MapObjects spawndata = layer.getObjects();
		Spawn[] spawns = new Spawn[spawndata.getCount()];
		
		for(int i = 0; i < spawndata.getCount(); ++i){
			
			MapObject object = spawndata.get(i);
			MapProperties p = object.getProperties();
			
			float x = (Float)p.get("x");
			float y = (Float)p.get("y");
			float width = (Float)p.get("width");
			float height = (Float)p.get("height");
			
			width /= 2;
			height /= 2;
			
			x += width;
			y += height;
			
			Body body = Box2DUtils.createBody(x, y, BodyType.StaticBody);
			Fixture fixture = Box2DUtils.createRectangularFixture(body, width, height, true);
			
			spawns[i] = new Spawn(body.getPosition());
			fixture.setUserData(spawns[i]);
		}
		
		return spawns;
		
	}
	
	public static void removeBody(Body body){
		world.destroyBody(body);
	}
	
}