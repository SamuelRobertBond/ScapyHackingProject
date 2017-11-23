package client.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteComponent implements Component{

	public Sprite sprite;
	
	/**
	 * Sprite Component for objects that require a sprite
	 * @param texture
	 * @param width
	 * @param height
	 * @param angle - in degrees
	 */
	public SpriteComponent(Texture texture, float width, float height, float angle) {
		sprite = new Sprite(texture);
		sprite.setSize(width, height);
		sprite.rotate(angle);
	}
	
}
