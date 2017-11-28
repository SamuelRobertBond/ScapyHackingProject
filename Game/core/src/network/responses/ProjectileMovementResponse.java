package network.responses;

public class ProjectileMovementResponse extends MovementResponse{

	public int id;
	
	public ProjectileMovementResponse(int id, float x, float y) {
		super(x, y);
		this.id = id;
	}

}
