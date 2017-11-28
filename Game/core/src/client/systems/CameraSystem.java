package client.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import client.entities.Player;
import client.inputhandlers.PlayerInputManager;

public class CameraSystem extends EntitySystem{

	private OrthographicCamera cam;
	private Player player;
	private PlayerInputManager input;
	private final float MAX_VIEW_RANGE = 800;
	
	public CameraSystem(Player player, OrthographicCamera cam, PlayerInputManager input) {
		
		this.player = player;
		this.cam = cam;
		this.input = input;
		
		cam.zoom = 35f;
	}
	
	@Override
	public void update(float deltaTime) {
		
		cam.position.x = player.positionComponent.pos.x;
		cam.position.y = player.positionComponent.pos.y;
		
		Vector2 center = new Vector2(player.positionComponent.pos.x, player.positionComponent.pos.y);
		Vector3 point = cam.unproject(new Vector3(input.getMousePosition(), cam.zoom));
		
		Vector2 angle = (new Vector2(point.x - center.x, point.y - center.y).nor());
		float dst = Math.min(MAX_VIEW_RANGE, new Vector2(point.x, point.y).dst(center) / 5);
		
		Vector2 offset = new Vector2(dst * angle.x, dst * angle.y);
		
		cam.position.x += offset.x;
		cam.position.y += offset.y;
		
		cam.update();
		
		super.update(deltaTime);
	}
	
	
}
