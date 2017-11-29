package server.worlds;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import client.inputhandlers.MovementState;
import client.interfaces.World;
import client.states.PlayerState;
import network.requests.JoinRequest;
import network.requests.MovementRequest;
import network.requests.ShootRequest;
import network.responses.JoinResponse;
import server.components.MovementComponent;
import server.components.PhysicsComponent;
import server.components.ProjectileIdComponent;
import server.debug.PhysicsDebugRenderer;
import server.entities.ServerCannonBall;
import server.entities.ServerPlayer;
import server.systems.PhysicsSystem;

public class ServerGameWorld implements World{

	private int entityCount;
	
	private Engine engine;
	private HashMap<Integer, ServerPlayer> players; 
	
	private Queue<ShootRequest> shootrequests;
	
	private PhysicsSystem physicsSystem;
	private PhysicsDebugRenderer renderer;
	private Server server;
	
	public ServerGameWorld(Server server) {
		
		this.server = server;
		
		engine = new Engine();
		entityCount = 0;
		
		shootrequests = new LinkedList<ShootRequest>();
		
		physicsSystem = new PhysicsSystem(server);
		engine.addSystem(physicsSystem);
		
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
						
						JoinRequest r = (JoinRequest)object; //Used to retrieve name
						
						//Adding the player to the server and alerting all the clients
						ServerPlayer player = new ServerPlayer(connection.getID(), connection.getID() + "", 200, 200); //Change x and y here (50, 50)
						
						players.put(connection.getID(), player);
						addPlayer(player);
						
						PhysicsComponent pc =  player.getComponent(PhysicsComponent.class);
						sendJoinResponse(connection, player.NAME, pc.body.getPosition().x, pc.body.getPosition().y);
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
		
		//Shooting Requests
		server.addListener(new Listener(){
			
			@Override
			public void received(Connection connection, Object object) {
				
				if(object instanceof ShootRequest){
					ShootRequest r = (ShootRequest)object;
					r.id = connection.getID();
					if(players.get(r.id).hc.state != PlayerState.DEAD){
						shootrequests.offer(r);
					}
				}
				
			}
			
			
		});
		
	}

	private void fireProjectile(ShootRequest r) {
		++entityCount;
		if(entityCount > 500000){
			entityCount = 0;
		}
		engine.addEntity(new ServerCannonBall(players.get(r.id), entityCount, new Vector2(r.x, r.y)));
	}

	private void addPlayer(ServerPlayer player) {
		engine.addEntity(player);
	}
	
	private void sendJoinResponse(Connection connection, String name, float x, float y){
		
		//New Player
		server.sendToAllTCP(new JoinResponse(name, x, y));
		
		//Other Players
		for(Entity e : engine.getEntitiesFor(Family.exclude(ProjectileIdComponent.class).get())){
			
			ServerPlayer p = (ServerPlayer)e;
			PhysicsComponent pc =  p.getComponent(PhysicsComponent.class);
			
			if(p.NAME != name){
				server.sendToTCP(connection.getID(), new JoinResponse(p.NAME, pc.body.getPosition().x, pc.body.getPosition().y));
			}
		}
		
	}
	
	public void enableDebugRenderer(OrthographicCamera cam) {
		renderer = new PhysicsDebugRenderer(physicsSystem.getWorld(), cam);
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render(float delta) {
		
		//ShootRequests
		while(!shootrequests.isEmpty()){
			fireProjectile(shootrequests.remove());
		}

		engine.update(delta);
	}

	public void debugRender(float delta) {
		if(renderer != null){
			renderer.update(delta);
		}
		
	}
	
}
