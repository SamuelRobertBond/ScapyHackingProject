package client.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.AssetLoader;

public class MapRenderSystem extends EntitySystem{

	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	public MapRenderSystem(OrthographicCamera camera) {
		this.camera = camera;
		renderer = new OrthogonalTiledMapRenderer(AssetLoader.MAP);
	}
	
	@Override
	public void update(float deltaTime) {
		renderer.setView(camera);
		renderer.render();
	}
	
	public void dispose(){
		renderer.dispose();
	}
	
}
