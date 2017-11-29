package server.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.game.AssetLoader;

import client.states.PlayerState;
import network.responses.MovementResponse;
import network.responses.PlayerMovementResponse;
import network.responses.ProjectileMovementResponse;
import server.Box2DUtils;
import server.components.HealthComponent;
import server.components.MovementComponent;
import server.components.PhysicsComponent;
import server.entities.ServerCannonBall;
import server.entities.ServerPlayer;

public class PhysicsSystem extends EntitySystem{

	private Server server;
	private World world;
	private ContactSystem collisionListener;
	
	private final Vector2 GRAVITY = new Vector2(0, 0);
	
	private ImmutableArray<Entity> entities;
	
	private ComponentMapper<PhysicsComponent> pm = ComponentMapper.getFor(PhysicsComponent.class);
	private ComponentMapper<MovementComponent> mm = ComponentMapper.getFor(MovementComponent.class);
	private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);
	
	public PhysicsSystem(Server server) {
		
		this.server = server;
		
		world = new World(GRAVITY, false);
		
		Box2DUtils.world = world;
		Box2DUtils.spawnTileMapWalls(AssetLoader.MAP, world);
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(PhysicsComponent.class, MovementComponent.class).get());
		collisionListener = new ContactSystem(engine, server, world);
		world.setContactListener(collisionListener);
	}
	
	@Override
	public void update(float timeStep){
		
		//Apply Impulse
		
		for(Entity e : entities){
			PhysicsComponent pc = pm.get(e);
			MovementComponent mc = mm.get(e);
			HealthComponent hc = hm.get(e);
			
			if((mc.xIntensity != 0 || mc.yIntensity !=0) && (hc == null || hc.state != PlayerState.DEAD)){
				pc.body.applyLinearImpulse(new Vector2(mc.MAX_SPEED * mc.xIntensity, mc.MAX_SPEED * mc.yIntensity), pc.body.getWorldCenter(), true);
			}
		}
		
		//World Step
		world.step(timeStep, 6, 6);
		collisionListener.disposeQueued();
		
		
		//Send Responses
		for(Entity e : entities){
			
			PhysicsComponent pc = pm.get(e);
			if(e instanceof ServerPlayer){
				ServerPlayer p = (ServerPlayer)e;
				server.sendToAllUDP(new PlayerMovementResponse(p.NAME, pc.body.getPosition().x, pc.body.getPosition().y));
			}else if(e instanceof ServerCannonBall){
				ServerCannonBall ball = (ServerCannonBall)e;
				server.sendToAllUDP(new ProjectileMovementResponse(ball.idComponent.ENTITY_ID, ball.getPosition().x, ball.getPosition().y));
			}
			

		}
	}
	
	public void dispose(){
		world.dispose();
	}

	public World getWorld() {
		return world;
	}
	
}
