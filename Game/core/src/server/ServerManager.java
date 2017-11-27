package server;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.esotericsoftware.kryonet.Server;

import network.NetworkUtils;
import server.worlds.ServerGameWorld;

public class ServerManager {

	private Server server;
	private ServerGameWorld gameWorld;
	private Timer timer;
	
	private final long TIMESTEP = 1000/60;
	
	public ServerManager() {
		
		server = new Server();
		NetworkUtils.setSerializationClasses(server.getKryo());
		
		//Binding the port
		try {
			server.bind(25565, 25566);
		} catch (IOException e) {
			Gdx.app.log("ServerManager", "Unable to bind port");
		}
		
		server.start();
		
		gameWorld = new ServerGameWorld(server);
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask(){
			
			@Override
			public void run() {
				updateWorld();
				
			}
			
		}, 0, TIMESTEP);
		
		Gdx.app.log("Server Created", "The server has been initialized");
		
	}
	
	private void updateWorld(){
		gameWorld.render(TIMESTEP);
	}
	
	public void debugRenderer(float delta){
		gameWorld.debugRender(delta);
	}
	
	public void enableDebug(OrthographicCamera cam){
		gameWorld.enableDebugRenderer(cam);
	}
	
}
