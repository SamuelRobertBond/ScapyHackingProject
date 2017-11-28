package server.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Contains the body and fixture for the physics object
 * @author sam
 */
public class PhysicsComponent implements Component{

	public Body body;
	public Fixture fixture;
	
	public PhysicsComponent(Body body, Fixture fixture) {
		this.body = body;
		this.fixture = fixture;
	}
	
}
