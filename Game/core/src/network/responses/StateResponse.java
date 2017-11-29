package network.responses;

import client.states.PlayerState;

public class StateResponse {

	public String name;
	public PlayerState state;
	
	public StateResponse(String name, PlayerState state) {
		this.name = name;
		this.state = state;
	}
	
	public StateResponse() {
		// TODO Auto-generated constructor stub
	}
	
}
