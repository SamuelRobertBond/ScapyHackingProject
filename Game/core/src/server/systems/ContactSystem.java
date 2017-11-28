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

import server.entities.ServerCannonBall;
import server.entities.ServerPlayer;

public class ContactSystem implements ContactListener{
	
	private Engine engine;
	private World world;
	
	private Queue<Fixture> destoryFixtures;
	
	public ContactSystem(Engine engine, World world) {
		this.engine = engine;
		this.world = world;
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
				
				//Hurt Server Player
				
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
				engine.removeEntity((Entity)a.getUserData());
				world.destroyBody(a.getBody());
			}
			
		}
		
	}

}
