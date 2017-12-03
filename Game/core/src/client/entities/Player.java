package client.entities;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.AssetLoader;
import com.mygdx.game.Constants;

import client.components.AnimationComponent;
import client.components.PositionComponent;
import client.components.SpriteComponent;
import client.components.StateComponent;
import client.states.PlayerState;

public class Player extends Entity{

	public final String name;
	
	public final float PLAYER_WIDTH = 22;
	public final float PLAYER_HEIGHT = 22;
	
	public AnimationComponent animationComponent;
	public SpriteComponent spriteComponent;
	public StateComponent stateComponent;
	public PositionComponent positionComponent;
	
	public Player(String name, float x, float y) {
		
		this.name = name;
		add(new StateComponent());
		
		spriteComponent = new SpriteComponent(AssetLoader.CANNON_BASE_TEXTURE, PLAYER_WIDTH, PLAYER_HEIGHT, 0); //Base
		positionComponent = new PositionComponent(x / Constants.PPM, y / Constants.PPM);
		animationComponent = new AnimationComponent();
		
		add(spriteComponent);
		add(positionComponent);
		add(animationComponent);
		
	}
	
	public void kill(){
		stateComponent.state = PlayerState.DEAD;
	}
	
}
