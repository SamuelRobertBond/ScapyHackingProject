package client.entities;

import com.badlogic.ashley.core.Entity;

import client.components.PositionComponent;
import client.components.SpriteComponent;
import client.components.StateComponent;
import client.states.PlayerState;
import client.utils.AssetLoader;

public class Player extends Entity{

	public final String name;
	
	private SpriteComponent spriteComponent;
	private StateComponent stateComponent;
	
	public Player(String name) {
		this.name = name;
		add(new StateComponent());
	}
	
	public void addPlayer(float x, float y, float width, float height, float angle){
		spriteComponent = new SpriteComponent(AssetLoader.CANNON_TEXTURE, width, height, angle);
		add(spriteComponent);
		add(new PositionComponent(x, y));
	}
	
	public void killPlayer(){
		stateComponent.state = PlayerState.DEAD;
	}
	
}
