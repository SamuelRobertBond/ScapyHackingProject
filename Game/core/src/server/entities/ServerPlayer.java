package server.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;

import server.Box2DUtils;
import server.components.HealthComponent;
import server.components.MovementComponent;
import server.components.PhysicsComponent;

public class ServerPlayer extends Entity{

	public final int ID;
	public final String NAME;
	
	public final float PLAYER_RADIUS = 10;
	public final float PLAYER_SPEED = 460;
	
	public PhysicsComponent pc;
	private MovementComponent mc;
	public HealthComponent hc;
	
	public ServerPlayer(int id, String name) {
		
		this.ID = id;
		this.NAME = name;
		
		mc = new MovementComponent(PLAYER_SPEED);
		add(mc);
	}
	
	public void respawn(Vector2 spawn){
	
		if(pc != null){
			Box2DUtils.removeBody(pc.body);
		}
		
		remove(PhysicsComponent.class);
		remove(HealthComponent.class);
		
		//Creating Components
		Body body = Box2DUtils.createBody(spawn.x, spawn.y, BodyType.DynamicBody);
		Fixture fixture = Box2DUtils.createCircularFixture(body, PLAYER_RADIUS);
		
		fixture.setUserData(this);
		
		pc = new PhysicsComponent(body, fixture);
		hc = new HealthComponent();
		
		add(hc);
		add(pc);
		
	}

	public Vector2 getPosition() {
		return pc.body.getPosition();
	}



}
