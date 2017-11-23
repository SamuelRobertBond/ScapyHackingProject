package client.entities;

import com.badlogic.ashley.core.Entity;

import client.components.PositionComponent;
import client.components.SpriteComponent;
import client.utils.AssetLoader;

public class CannonBall extends Entity{
	
	public CannonBall(float x, float y, float width, float height) {
		add(new SpriteComponent(AssetLoader.CANNONBALL_TEXTURE, width, height, 0));
		add(new PositionComponent(x, y));
	}
	
}
