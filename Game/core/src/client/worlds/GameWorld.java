package client.worlds;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.OrthographicCamera;

import client.interfaces.World;
import client.systems.MapRenderSystem;
import server.ServerManager;

public class GameWorld implements World{

	private Engine engine;
	
	private OrthographicCamera camera;
	
	//Systems
	private MapRenderSystem mapRenderSystem;
	
	public GameWorld(String name, OrthographicCamera camera) {
		
		engine = new Engine();
		this.camera = camera;
		
		//Disposable Systems
		mapRenderSystem = new MapRenderSystem(camera);
		
		//Adding all the systems
		engine.addSystem(mapRenderSystem);
		
	}
	
	@Override
	public void render(float delta) {
		engine.update(delta);
	}
	
	public void enableDebuging(ServerManager server){
		server.enableDebug(camera);
	}

	@Override
	public void dispose() {
		mapRenderSystem.dispose();
		
	}

}
