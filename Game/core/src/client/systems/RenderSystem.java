package client.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import client.components.PositionComponent;
import client.components.SpriteComponent;

public class RenderSystem extends EntitySystem{

	private SpriteBatch batch;
	
	private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	
	private ImmutableArray<Entity> entities;
	
	public RenderSystem(OrthographicCamera cam) {
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(SpriteComponent.class, PositionComponent.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		
		batch.begin();
		
		//Draw Each Entity
		for(Entity e : entities){
			SpriteComponent sc = sm.get(e);
			PositionComponent pc = pm.get(e);
			sc.sprite.setPosition(pc.pos.x, pc.pos.y);
			sc.sprite.draw(batch);
		}
		
		batch.end();
		
	}
	
}
