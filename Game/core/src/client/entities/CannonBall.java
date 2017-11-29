package client.entities;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.AssetLoader;

import client.components.PositionComponent;
import client.components.SpriteComponent;

public class CannonBall extends Entity{
	
	public SpriteComponent sc;
	public PositionComponent pc;
	
	public final float BALL_SIZE = 10;
	
	public CannonBall(float x, float y) {
		
		sc = new SpriteComponent(AssetLoader.CANNONBALL_TEXTURE, BALL_SIZE, BALL_SIZE, 0);
		pc = new PositionComponent(x, y);
		
		add(sc);
		add(pc);
	}
	
}
