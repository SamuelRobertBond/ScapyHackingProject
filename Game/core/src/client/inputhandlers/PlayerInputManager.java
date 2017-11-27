package client.inputhandlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.esotericsoftware.kryonet.Client;

import network.requests.MovementRequest;

public class PlayerInputManager implements InputProcessor{

	private Client client;
	
	public PlayerInputManager(Client client) {
		this.client = client;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		if(keycode == Keys.W){
			client.sendUDP(new MovementRequest(MovementState.UP, 1f));
		}else if(keycode == Keys.S){
			client.sendUDP(new MovementRequest(MovementState.DOWN, -1f));
		}
		
		if(keycode == Keys.A){
			client.sendUDP(new MovementRequest(MovementState.LEFT, 1f));
		}else if(keycode == Keys.D){
			client.sendUDP(new MovementRequest(MovementState.RIGHT, 1f));
		}	
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		if(keycode == Keys.W){
			client.sendUDP(new MovementRequest(MovementState.UP, 0f));
		}else if(keycode == Keys.S){
			client.sendUDP(new MovementRequest(MovementState.DOWN, 0f));
		}
		
		if(keycode == Keys.A){
			client.sendUDP(new MovementRequest(MovementState.LEFT, 0f));
		}else if(keycode == Keys.D){
			client.sendUDP(new MovementRequest(MovementState.RIGHT, 0f));
		}	
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	
	//Shooting is done here
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
