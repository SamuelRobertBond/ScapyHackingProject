package server.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import client.utils.AssetLoader;
import server.Box2DUtils;

public class PhysicsSystem extends EntitySystem{

	private World world;
	private final Vector2 GRAVITY = new Vector2(0, 0);
	
	public PhysicsSystem() {
		this.world = new World(GRAVITY, false);
		Box2DUtils.spawnTileMapWalls(AssetLoader.MAP, world);
	}
	
	@Override
	public void update(float timeStep){
		world.step(timeStep, 6, 6);
	}
	
	public void dispose(){
		world.dispose();
	}

	public World getWorld() {
		return world;
	}
	
}
