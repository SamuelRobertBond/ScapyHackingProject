package server.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import server.Box2DUtils;
import server.components.MovementComponent;
import server.components.PhysicsComponent;
import server.components.ProjectileIdComponent;

public class ServerCannonBall extends Entity{

	public final float SPEED = 500f;
	public final float RADIUS = 5f;
	public final float OFFSET = 17f;
	
	public ProjectileIdComponent idComponent;
	private PhysicsComponent physicsComponent;
	private MovementComponent movementComponent;
	
	public ServerCannonBall(ServerPlayer player, int id, Vector2 angle) {
		
		idComponent = new ProjectileIdComponent(id);
		angle = angle.nor();
		
		Body body = Box2DUtils.createBody(player.getPosition().x + (OFFSET * angle.x), player.getPosition().y + (OFFSET * angle.y), BodyType.DynamicBody);
		Fixture fixture = Box2DUtils.createCircularFixture(body, RADIUS);
		
		fixture.setSensor(true);
		fixture.setUserData(this);
		
		physicsComponent = new PhysicsComponent(body, fixture);
		movementComponent = new MovementComponent(SPEED, angle.x, angle.y);
		
		add(physicsComponent);
		add(movementComponent);
		add(idComponent);
		
	}

	public Vector2 getPosition() {
		return physicsComponent.body.getPosition();
	}
	
	
	
}
