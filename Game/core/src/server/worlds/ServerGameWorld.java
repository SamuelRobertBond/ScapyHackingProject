package server.worlds;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.OrthographicCamera;

import client.interfaces.World;
import server.debug.PhysicsDebugRenderer;
import server.systems.PhysicsSystem;

public class ServerGameWorld implements World{

	private Engine engine;
	
	private PhysicsSystem physicsSystem;
	private PhysicsDebugRenderer renderer;
	
	public ServerGameWorld() {
		engine = new Engine();
		physicsSystem = new PhysicsSystem();
		engine.addSystem(physicsSystem);
	}
	
	public void enableDebugRenderer(OrthographicCamera cam) {
		renderer = new PhysicsDebugRenderer(physicsSystem.getWorld(), cam);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		engine.update(delta);
	}

	public void debugRender(float delta) {
		if(renderer != null){
			renderer.update(delta);
		}
		
	}
	
}
