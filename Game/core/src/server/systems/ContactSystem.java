package server.systems;

import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.esotericsoftware.kryonet.Server;

import client.states.PlayerState;
import network.responses.ProjectileMovementResponse;
import network.responses.StateResponse;
import server.entities.ServerCannonBall;
import server.entities.ServerPlayer;
import server.states.ProjectileState;

public class ContactSystem implements ContactListener{
	
	private Engine engine;
	private World world;
	private Server server;
	
	private Queue<Fixture> destoryFixtures;
	
	public ContactSystem(Engine engine, Server server, World world) {
		this.engine = engine;
		this.world = world;
		this.server = server;
		this.destoryFixtures = new LinkedList<Fixture>();
	}
	
	@Override
	public void beginContact(Contact contact) {
		
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		//Checks whether a or b has a cannonball, if so processes the collision
		if(!checkCannonBallCollision(a, b)){
			checkCannonBallCollision(b, a);
		}
		
	}
	
	private boolean checkCannonBallCollision(Fixture a, Fixture b){
		
		if(a.getUserData() instanceof ServerCannonBall){
			
			if(b.getUserData() instanceof ServerPlayer){
				ServerPlayer p = (ServerPlayer)b.getUserData();
				--p.hc.health;
				if(p.hc.state != PlayerState.DEAD && p.hc.health < 1){
					p.hc.state = PlayerState.DEAD;
					server.sendToAllTCP(new StateResponse(p.NAME, p.hc.state));
				}
			}
			
			destoryFixtures.offer(a);
			
			return true;
		}
		
		return false;
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

	public void disposeQueued() {
		
		while(!destoryFixtures.isEmpty()){
			
			Fixture a = destoryFixtures.remove();
			
			if(a.getUserData() != null && a != null){
				
				ServerCannonBall ball = (ServerCannonBall)a.getUserData(); 
				
				ProjectileMovementResponse r = new ProjectileMovementResponse(ball.idComponent.ENTITY_ID, a.getBody().getPosition().x, a.getBody().getPosition().y);
				r.state = ProjectileState.DEAD;
				server.sendToAllTCP(r);
				
				engine.removeEntity((Entity)a.getUserData());
				world.destroyBody(a.getBody());
			}
			
		}
		
	}

}
