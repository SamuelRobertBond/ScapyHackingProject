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
	
	private PhysicsComponent pc;
	private MovementComponent mc;
	public HealthComponent hc;
	
	public ServerPlayer(int id, String name, float x, float y) {
		
		this.ID = id;
		this.NAME = name;
		
		//Creating Components
		Body body = Box2DUtils.createBody(x, y, BodyType.DynamicBody);
		Fixture fixture = Box2DUtils.createCircularFixture(body, PLAYER_RADIUS);
		
		fixture.setUserData(this);
		
		pc = new PhysicsComponent(body, fixture);
		mc = new MovementComponent(PLAYER_SPEED);
		hc = new HealthComponent();
		
		//Adding Components
		add(pc);
		add(mc);
		add(hc);
	}

	public Vector2 getPosition() {
		return pc.body.getPosition();
	}

}
