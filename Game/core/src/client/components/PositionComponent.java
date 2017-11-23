package client.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component{
	
	public Vector2 pos;
	
	public PositionComponent(float x, float y) {
		pos = new Vector2(x, y);
	}
	
	public void setPosition(float x, float y){
		pos.set(x, y);
	}
	
}
