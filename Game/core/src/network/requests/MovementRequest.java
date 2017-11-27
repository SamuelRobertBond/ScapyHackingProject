package network.requests;

import client.inputhandlers.MovementState;

public class MovementRequest {

	public MovementState move;
	public float intensity;
	
	public MovementRequest(MovementState move, float intensity) {
		this.move = move;
		this.intensity = intensity;
	}
	
	public MovementRequest() {
		// TODO Auto-generated constructor stub
	}

}
