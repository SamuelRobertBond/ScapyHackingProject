package server.worlds;

import java.util.HashMap;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import client.inputhandlers.MovementState;
import client.interfaces.World;
import network.requests.JoinRequest;
import network.requests.MovementRequest;
import network.responses.JoinResponse;
import server.components.MovementComponent;
import server.debug.PhysicsDebugRenderer;
import server.entities.ServerPlayer;
import server.systems.PhysicsSystem;

public class ServerGameWorld implements World{

	private Engine engine;
	private HashMap<Integer, ServerPlayer> players; 
	
	private PhysicsSystem physicsSystem;
	private PhysicsDebugRenderer renderer;
	private Server server;
	
	public ServerGameWorld(Server server) {
		
		engine = new Engine();
		physicsSystem = new PhysicsSystem();
		engine.addSystem(physicsSystem);
		
		this.server = server;
		players = new HashMap<Integer, ServerPlayer>();
		
		setListeners();
		
	}
	
	private void setListeners() {
		
		//Join Listener / Response
		server.addListener(new Listener(){
			
			@Override
			public void received(Connection connection, Object object) {
				if(object instanceof JoinRequest){
					if(!players.containsKey(connection.getID())){
						
						JoinRequest r = (JoinRequest)object;
						
						//Adding the player to the server and alerting all the clients
						ServerPlayer player = new ServerPlayer(r.name);
						players.put(connection.getID(), player);
						addPlayer(player);
					}
				}
			}
			
		});
		
		//Movement Listener
		server.addListener(new Listener(){
			@Override
			public void received(Connection connection, Object object) {
				if(object instanceof MovementRequest){
					MovementRequest r = (MovementRequest)object;
					if(r.move == MovementState.UP || r.move == MovementState.DOWN){
						players.get(connection.getID()).getComponent(MovementComponent.class).yIntensity += r.intensity;
					}else if(r.move == MovementState.LEFT || r.move == MovementState.RIGHT){
						players.get(connection.getID()).getComponent(MovementComponent.class).xIntensity += r.intensity;
					}
				}
			}
			
		});
	
	}

	private void addPlayer(ServerPlayer player) {
		server.sendToAllTCP(new JoinResponse(player.NAME));
	}
	
	public void enableDebugRenderer(OrthographicCamera cam) {
		renderer = new PhysicsDebugRenderer(physicsSystem.getWorld(), cam);
	}

	@Override
	public void dispose() {
		
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
