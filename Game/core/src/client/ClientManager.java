package client;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;

public class ClientManager {

	private Client client;
	
	public ClientManager() {
		
		client = new Client();
		
		try {
			client.connect(5000, "localhost", 25565, 25566);
		} catch (IOException e) {
			Gdx.app.log("ClientManager", "Client Failed to connect to host");
		}
		
		client.start();
	}
	
}
