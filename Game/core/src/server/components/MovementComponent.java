package server.components;

import com.badlogic.ashley.core.Component;

public class MovementComponent implements Component{

	public final float MAX_SPEED;
	
	public float xIntensity = 0;
	public float yIntensity = 0;
	
	public MovementComponent(float MAX_SPEED, float xIntensity, float yIntensity) {
		this.MAX_SPEED = MAX_SPEED;
		this.xIntensity = xIntensity;
		this.yIntensity = yIntensity;
	}
	
	public MovementComponent(float MAX_SPEED) {
		this.MAX_SPEED = MAX_SPEED;
	}
	
}
