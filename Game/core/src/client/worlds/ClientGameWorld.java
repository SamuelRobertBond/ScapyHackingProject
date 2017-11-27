package client.worlds;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;

import client.ClientManager;
import client.inputhandlers.PlayerInputManager;
import client.interfaces.World;
import client.systems.MapRenderSystem;

public class ClientGameWorld implements World{

	private Engine engine;
	private InputMultiplexer in;
	
	//Systems
	private MapRenderSystem mapRenderSystem;
	
	public ClientGameWorld(ClientManager client, OrthographicCamera camera) {
		
		engine = new Engine();
		in = new InputMultiplexer();
		
		PlayerInputManager playerInput = new PlayerInputManager(client.getClient());
		in.addProcessor(playerInput);
		
		Gdx.input.setInputProcessor(in);
		
		//Disposable Systems
		mapRenderSystem = new MapRenderSystem(camera);
		
		//Adding all the systems
		engine.addSystem(mapRenderSystem);
		
	}
	
	@Override
	public void render(float delta) {
		engine.update(delta);
	}

	@Override
	public void dispose() {
		mapRenderSystem.dispose();
		
	}

}
