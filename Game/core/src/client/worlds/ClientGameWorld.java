package client.worlds;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import client.ClientManager;
import client.entities.CannonBall;
import client.entities.Player;
import client.inputhandlers.PlayerInputManager;
import client.interfaces.World;
import client.systems.CameraSystem;
import client.systems.MapRenderSystem;
import client.systems.RenderSystem;
import network.responses.DamageResponse;
import network.responses.JoinResponse;
import network.responses.PlayerMovementResponse;
import network.responses.ProjectileMovementResponse;
import network.responses.RotationResponse;
import server.states.ProjectileState;

public class ClientGameWorld implements World{

	private Engine engine;
	private Client client;
	private InputMultiplexer in;
	
	//Clients Player
	private Player player;
	private OrthographicCamera camera;
	private PlayerInputManager playerInput;
	
	//Entity Lists
	private HashMap<String, Player> players;
	private HashMap<Integer, CannonBall> projectiles;
	private Queue<Integer> removalList;
	
	//Systems
	private MapRenderSystem mapRenderSystem;
	private RenderSystem renderSystem;
	
	public ClientGameWorld(ClientManager client, OrthographicCamera camera) {
		
		//World Setup
		engine = new Engine();
		this.camera = camera;
		this.client = client.getClient();
		
		//Init Entity Lists
		players = new HashMap<String, Player>();
		projectiles = new HashMap<Integer, CannonBall>();
		removalList = new LinkedList<Integer>();
		
		//Player input
		in = new InputMultiplexer();
		Gdx.input.setInputProcessor(in);
		
		//Disposable Systems
		mapRenderSystem = new MapRenderSystem(camera);
		renderSystem = new RenderSystem(camera);
		
		//Adding all the systems
		engine.addSystem(mapRenderSystem);
		engine.addSystem(renderSystem);
		
		//Network Setup
		addListeners(client.getClient());
		
	}
	
	private void addListeners(Client client){
		
		//Join Listener
		client.addListener(new Listener(){
			
			@Override
			public void received(Connection connection, Object object) {
				if(object instanceof JoinResponse){
					
					JoinResponse r = (JoinResponse)object;
					Player p = new Player(r.name, r.x, r.y);
					
					if(players.isEmpty()){
						initClientPlayer(p);
					}
					
					players.put(p.name, p);
					engine.addEntity(p);
					
				}
			}
			
		});
		
		//Player Movement Listener
		client.addListener(new Listener(){
			
			@Override
			public void received(Connection connection, Object object) {
				if(object instanceof PlayerMovementResponse){
					PlayerMovementResponse r = (PlayerMovementResponse)object;
					Player player = players.get(r.name);
					if(player != null){
						player.positionComponent.pos.x = r.x;
						player.positionComponent.pos.y = r.y;
					}
				}
			}
			
		});
		
		//Projectile Listener
		client.addListener(new Listener(){
			
			@Override
			public void received(Connection connection, Object object) {
				if(object instanceof ProjectileMovementResponse){
					ProjectileMovementResponse r = (ProjectileMovementResponse)object;
					handleProjectile(r);
				}
			}
			
		});
		
		//Rotation Listener
		client.addListener(new Listener(){
			
			@Override
			public void received(Connection connection, Object object) {
				
				if(object instanceof RotationResponse){
					changeRotation((RotationResponse)object);
				}
				
			}
			
		});
		
		//Damage Listener
		client.addListener(new Listener(){
			
			@Override
			public void received(Connection connection, Object object) {
				if(object instanceof DamageResponse){
					DamageResponse r = (DamageResponse)object;
					if(players.containsKey(r.name)){
						players.get(r.name).animationComponent.health = r.health;
					}
				}
			}
			
		});
		
	}
	
	private void changeRotation(RotationResponse r){
		if(players.containsKey(r.name)){
			players.get(r.name).positionComponent.rotation = r.rotation;
		}
	}
	
	public void queueRemoval(Integer e) {
		removalList.offer(e);
	}
	
	public void handleRemovals(){
		
		while(!removalList.isEmpty()){
			engine.removeEntity(projectiles.remove(removalList.poll()));
		}
		
	}
	
	private void handleProjectile(ProjectileMovementResponse r) {
		
		CannonBall ball = projectiles.get(r.id);
		
		if(ball == null && r.state != ProjectileState.DEAD){
			ball = new CannonBall(r.x, r.y);
			projectiles.put(r.id, ball);
			engine.addEntity(ball);
		
		}else if(r.state == ProjectileState.DEAD){
			queueRemoval(r.id);
		}else{
			ball.pc.pos.x = r.x;
			ball.pc.pos.y = r.y;
		}
		
	}

	private void initClientPlayer(Player p) {
		player = p;
		playerInput = new PlayerInputManager(client, player, camera);
		engine.addSystem(new CameraSystem(player, camera, playerInput));
		in.addProcessor(playerInput);
	}
	
	@Override
	public void render(float delta) {
		engine.update(delta);
		handleRemovals();
	}

	@Override
	public void dispose() {
		mapRenderSystem.dispose();
		
	}

}
