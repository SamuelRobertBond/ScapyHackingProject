package server.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

import server.components.SpawnID;

public class Spawn extends Entity{

	private Vector2 pos;
	private boolean occupied;
	
	public Spawn(Vector2 pos) {
		occupied = false;
		this.pos = pos;
		add(new SpawnID());
	}
	
	public void setOccupied(boolean occupied){
		this.occupied = occupied;
	}
	
	public boolean isOccupied(){
		return occupied;
	}
	
	public Vector2 getPosition(){
		return pos;
	}
	
}
