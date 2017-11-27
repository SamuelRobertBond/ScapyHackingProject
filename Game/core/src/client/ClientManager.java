package client;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;

import network.NetworkUtils;

public class ClientManager {

	private Client client;
	
	public ClientManager() {
		
		client = new Client();
		NetworkUtils.setSerializationClasses(client.getKryo());
		
		client.start();
		
		try {
			client.connect(5000, client.discoverHost(25566, 5000).getHostAddress(), 25565, 25566);
		} catch (IOException e) {
			Gdx.app.log("ClientManager", "Client Failed to connect to host");
		}
		
	}
	
	public Client getClient(){
		return client;
	}
	
}
