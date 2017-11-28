package server.components;

import com.badlogic.ashley.core.Component;

public class ProjectileIdComponent implements Component{

	public final int ENTITY_ID;
	
	public ProjectileIdComponent(int ENTITY_ID) {
		this.ENTITY_ID = ENTITY_ID;
	}
	
	
}
