package network.responses;

public class PlayerMovementResponse{

	public String name;
	public float x;
	public float y;
	
	public PlayerMovementResponse(String name, float x, float y) {
		this.x = x;
		this.y = y;
		this.name = name;
	}
	
	public PlayerMovementResponse() {
		// TODO Auto-generated constructor stub
	}

}
