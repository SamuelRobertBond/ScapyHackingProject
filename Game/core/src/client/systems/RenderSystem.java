package client.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import client.components.AnimationComponent;
import client.components.PositionComponent;
import client.components.SpriteComponent;
import client.entities.Player;

public class RenderSystem extends EntitySystem{

	private Engine engine;
	
	private SpriteBatch batch;
	private OrthographicCamera cam;
	
	private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);
	
	private ImmutableArray<Entity> entities;
	private ImmutableArray<Entity> playerEntities;
	
	private float stateTime;
	
	public RenderSystem(OrthographicCamera cam) {
		batch = new SpriteBatch();
		stateTime = 0f;
		this.cam = cam;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(SpriteComponent.class, PositionComponent.class).exclude(AnimationComponent.class).get());
		playerEntities = engine.getEntitiesFor(Family.all(AnimationComponent.class, PositionComponent.class, SpriteComponent.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		
		//Draw Each Entity
		for(Entity e : entities){
			
			SpriteComponent sc = sm.get(e);
			PositionComponent pc = pm.get(e);
			
			sc.sprite.setPosition(pc.pos.x - sc.sprite.getWidth()/2, pc.pos.y - sc.sprite.getHeight()/2);
			sc.sprite.draw(batch);
		}
		
		for(Entity e : playerEntities){
			
			Player p = (Player)e;
			
			SpriteComponent sc = sm.get(e);
			PositionComponent pc = pm.get(e);
			AnimationComponent ac = am.get(e);
			
			sc.sprite.setPosition(pc.pos.x - sc.sprite.getWidth()/2, pc.pos.y - sc.sprite.getHeight()/2);
			sc.sprite.draw(batch);
			
			if(p.animationComponent.setLivingTexture()){
				ac.cannonSprite.setPosition(pc.pos.x - ac.cannonSprite.getWidth()/2 + ac.CANNON_WIDTH/4, pc.pos.y - ac.cannonSprite.getHeight()/2);
				ac.cannonSprite.setRotation(pc.rotation);
				ac.cannonSprite.draw(batch);
			}else{
				stateTime += deltaTime;
				batch.draw(ac.getDead().getKeyFrame(stateTime), pc.pos.x - ac.cannonSprite.getWidth()/2 + ac.CANNON_WIDTH/4, pc.pos.y - ac.CANNON_HEIGHT/2, ac.CANNON_WIDTH/4, ac.CANNON_HEIGHT/2, ac.CANNON_WIDTH, ac.CANNON_HEIGHT, 1, 1, p.positionComponent.rotation);
			}
			
		}
		
		batch.end();
		
	}
	
}
