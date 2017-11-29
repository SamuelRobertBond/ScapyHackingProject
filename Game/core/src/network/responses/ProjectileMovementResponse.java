package network.responses;

import server.states.ProjectileState;

public class ProjectileMovementResponse{

	public int id;
	public float x;
	public float y;
	
	public ProjectileState state;
	
	public ProjectileMovementResponse(int id, float x, float y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.state = ProjectileState.ALIVE;
	}
	
	public ProjectileMovementResponse() {
		// TODO Auto-generated constructor stub
	}

}
