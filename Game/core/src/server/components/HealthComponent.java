package server.components;

import com.badlogic.ashley.core.Component;

import client.states.PlayerState;

public class HealthComponent implements Component{

	public int health;
	public PlayerState state;
	
	public HealthComponent() {
		this.health = 3;
		this.state = PlayerState.ALIVE;
	}
	
}
