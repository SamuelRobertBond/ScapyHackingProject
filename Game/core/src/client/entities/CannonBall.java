package client.entities;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.AssetLoader;

import client.components.PositionComponent;
import client.components.SpriteComponent;

public class CannonBall extends Entity{
	
	public CannonBall(float x, float y, float width, float height) {
		add(new SpriteComponent(AssetLoader.CANNONBALL_TEXTURE, width, height, 0));
		add(new PositionComponent(x, y));
	}
	
}
