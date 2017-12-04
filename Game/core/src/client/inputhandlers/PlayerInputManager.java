package client.inputhandlers;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.esotericsoftware.kryonet.Client;

import client.entities.Player;
import client.states.PlayerState;
import network.requests.MovementRequest;
import network.requests.RotationRequest;
import network.requests.ShootRequest;

public class PlayerInputManager implements InputProcessor{

	private Client client;
	
	private float mouse_x;
	private float mouse_y;
	
	private boolean canShoot;
	
	private Player player;
	private OrthographicCamera camera;
	
	public PlayerInputManager(Client client, Player player, OrthographicCamera camera) {
		
		this.client = client;
		
		this.player = player;
		this.camera = camera;
		
		canShoot = true;
		
		mouse_x = 0;
		mouse_y = 0;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		if(keycode == Keys.W){
			client.sendUDP(new MovementRequest(MovementState.UP, 1f));
		}else if(keycode == Keys.S){
			client.sendUDP(new MovementRequest(MovementState.DOWN, -1f));
		}
		
		if(keycode == Keys.A){
			client.sendUDP(new MovementRequest(MovementState.LEFT, -1f));
		}else if(keycode == Keys.D){
			client.sendUDP(new MovementRequest(MovementState.RIGHT, 1f));
		}
		
		
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		if(keycode == Keys.W){
			client.sendUDP(new MovementRequest(MovementState.UP, -1f));
		}else if(keycode == Keys.S){
			client.sendUDP(new MovementRequest(MovementState.DOWN, 1f));
		}
		
		if(keycode == Keys.A){
			client.sendUDP(new MovementRequest(MovementState.LEFT, 1f));
		}else if(keycode == Keys.D){
			client.sendUDP(new MovementRequest(MovementState.RIGHT, -1f));
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
		
		if(canShoot && player != null && player.animationComponent.health > 1){
			
			Vector2 center = new Vector2(player.positionComponent.pos.x, player.positionComponent.pos.y);
			Vector3 point = camera.unproject(new Vector3(getMousePosition(), camera.zoom));
			
			Vector2 angle = new Vector2(point.x - center.x, point.y - center.y).nor();
			client.sendUDP(new ShootRequest(angle.x, angle.y));
			
			canShoot = false;
			setReload();
			
		}
		
		return false;
	}

	//This is a good way to start determining reload times, but needs to also be validated by the server
	private void setReload() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			
			@Override
			public void run() {
				reload();
			}
			
		}, 2000);
		
	}
	
	private void reload(){
		canShoot = true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		mouse_x = screenX;
		mouse_y = screenY;
		
		handleRotation();
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouse_x = screenX;
		mouse_y = screenY;
		
		handleRotation();
		
		return false;
	}

	private void handleRotation(){
		
		
		if(player != null && player.animationComponent.health > 1){
			
			Vector2 center = new Vector2(player.positionComponent.pos.x, player.positionComponent.pos.y);
			Vector3 point = camera.unproject(new Vector3(getMousePosition(), camera.zoom));
			
			Vector2 angle = (new Vector2(point.x - center.x, point.y - center.y).nor());
			
			player.positionComponent.rotation = angle.angle();
			
			client.sendUDP(new RotationRequest(angle.angle()));
			
		}
		
	}
	
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Vector2 getMousePosition(){
		return new Vector2(mouse_x, mouse_y);
	}

}
