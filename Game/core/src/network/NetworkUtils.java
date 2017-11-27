package network;

import com.esotericsoftware.kryo.Kryo;

import client.inputhandlers.MovementState;
import network.requests.JoinRequest;
import network.requests.MovementRequest;
import network.requests.ShootRequest;
import network.responses.JoinResponse;
import network.responses.MovementResponse;
import network.responses.ShootResponse;

public class NetworkUtils {

	
	public static void setSerializationClasses(Kryo kryo){
		
		kryo.register(MovementState.class);
		
		kryo.register(JoinRequest.class);
		kryo.register(MovementRequest.class);
		kryo.register(ShootRequest.class);
		
		kryo.register(JoinResponse.class);
		kryo.register(ShootResponse.class);
		kryo.register(MovementResponse.class);
		
	}
	
}
