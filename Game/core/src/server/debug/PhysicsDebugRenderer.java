package server.debug;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsDebugRenderer extends EntitySystem{

	private Box2DDebugRenderer renderer;
	private World world;
	private OrthographicCamera camera;
	
	public PhysicsDebugRenderer(World world, OrthographicCamera cam){
		
		renderer = new Box2DDebugRenderer();
		
		camera = cam;
		this.world = world;
	}
	
	@Override
	public void update(float deltaTime){
		renderer.render(world, camera.combined);
	}
	
	
	
}
