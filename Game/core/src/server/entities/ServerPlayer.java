package server.entities;

import com.badlogic.ashley.core.Entity;

public class ServerPlayer extends Entity{

	public final String NAME;
	
	public ServerPlayer(String name) {
		this.NAME = name;
	}

}
