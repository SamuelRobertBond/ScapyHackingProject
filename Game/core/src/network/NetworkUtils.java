package network;

import com.esotericsoftware.kryo.Kryo;

import client.inputhandlers.MovementState;
import client.states.PlayerState;
import network.requests.JoinRequest;
import network.requests.MovementRequest;
import network.requests.RotationRequest;
import network.requests.ShootRequest;
import network.responses.JoinResponse;
import network.responses.MovementResponse;
import network.responses.PlayerMovementResponse;
import network.responses.ProjectileMovementResponse;
import network.responses.RotationResponse;
import network.responses.ShootResponse;
import network.responses.StateResponse;
import server.states.ProjectileState;

public class NetworkUtils {

	
	public static void setSerializationClasses(Kryo kryo){
		
		kryo.register(PlayerState.class);
		kryo.register(MovementState.class);
		kryo.register(ProjectileState.class);
		
		kryo.register(JoinRequest.class);
		kryo.register(MovementRequest.class);
		kryo.register(ShootRequest.class);
		kryo.register(RotationRequest.class);
		
		kryo.register(JoinResponse.class);
		kryo.register(ShootResponse.class);
		kryo.register(MovementResponse.class);
		kryo.register(PlayerMovementResponse.class);
		kryo.register(ProjectileMovementResponse.class);
		kryo.register(StateResponse.class);
		kryo.register(RotationResponse.class);
		
	}
	
}
