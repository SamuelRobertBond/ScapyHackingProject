package client.components;

import com.badlogic.ashley.core.Component;

import client.states.PlayerState;

public class StateComponent implements Component{

	public PlayerState state = PlayerState.WAITING;

}
