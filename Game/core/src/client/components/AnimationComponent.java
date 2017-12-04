package client.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.AssetLoader;

public class AnimationComponent implements Component{

	public Sprite cannonSprite;
	public int health = 3;
	
	public final float CANNON_WIDTH = 30f;
	public final float CANNON_HEIGHT = 20f;
	
	private Animation<TextureRegion> deadAnim;
	private TextureRegion alive[];
	private TextureRegion dead[];
	
	public AnimationComponent() {
		
		//Setting animation frames
		//Dont normally do this manually 
		
		//Dead
		dead = new TextureRegion[AssetLoader.CANNONHEAD_BREAKING_TEXTURES.length * AssetLoader.CANNONHEAD_BREAKING_TEXTURES[0].length];
		dead[0] = AssetLoader.CANNONHEAD_BREAKING_TEXTURES[0][0];
		dead[1] = AssetLoader.CANNONHEAD_BREAKING_TEXTURES[0][1];
		dead[2] = AssetLoader.CANNONHEAD_BREAKING_TEXTURES[0][2];
		dead[3] = AssetLoader.CANNONHEAD_BREAKING_TEXTURES[0][3];
		dead[4] = AssetLoader.CANNONHEAD_BREAKING_TEXTURES[0][4];
		
		deadAnim = new Animation<TextureRegion>(.025f, dead);
		
		//Living
		alive = new TextureRegion[AssetLoader.CANNONHEAD_TEXTURES.length * AssetLoader.CANNONHEAD_TEXTURES[0].length];
		alive[0] = AssetLoader.CANNONHEAD_TEXTURES[0][0];
		alive[1] = AssetLoader.CANNONHEAD_TEXTURES[0][1];
		alive[2] = AssetLoader.CANNONHEAD_TEXTURES[0][2];
		
		cannonSprite = new Sprite(alive[0]);
		cannonSprite.setOrigin(CANNON_WIDTH/4, CANNON_HEIGHT/2);
		cannonSprite.setSize(CANNON_WIDTH, CANNON_HEIGHT);
		
	}
	
	public boolean setLivingTexture(){
		
		if(health > 0){
			cannonSprite.setRegion(alive[3 - health]);
			return true;
		}
		return false;
		
	}
	
	public Animation<TextureRegion> getDead(){
		return deadAnim;
	}
	
}
