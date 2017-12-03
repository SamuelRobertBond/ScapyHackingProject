package client.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteComponent implements Component{

	public Sprite sprite;
	
	/**
	 * Sprite Component for objects that require a sprite
	 * @param tex
	 * @param width
	 * @param height
	 * @param angle - in degrees
	 */
	public SpriteComponent(Texture tex, float width, float height, float angle) {
		
		sprite = new Sprite(tex);
		sprite.setSize(width, height);
		sprite.rotate(angle);
		
	}
	
}
