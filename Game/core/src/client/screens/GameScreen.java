package client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import client.ClientManager;
import client.utils.Constants;
import client.worlds.GameWorld;
import server.ServerManager;

public class GameScreen implements Screen{

	private Game game;
	private GameWorld world;
	private OrthographicCamera cam;
	
	//Client Server
	private ServerManager server;
	private ClientManager client;
	
	public GameScreen(Game game, ClientManager client, ServerManager server, String name) {
		this.game = game;
		cam = new OrthographicCamera(Constants.V_WIDTH, Constants.V_HEIGHT);
		world = new GameWorld(name, cam);
		world.enableDebuging(server);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		
		world.render(delta);
		server.debugRenderer(delta);
		
		debugCamera(delta);
		
		
	}
	
	private void debugCamera(float delta){
		
		if(Gdx.input.isKeyPressed(Keys.W)){
			cam.translate(0, 30 * delta);
		}else if(Gdx.input.isKeyPressed(Keys.S)){
			cam.translate(0, -30 * delta);
		}
		
		if(Gdx.input.isKeyPressed(Keys.A)){
			cam.translate(-30 * delta, 0);
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			cam.translate(30 * delta, 0);
		}
		
		if(Gdx.input.isKeyPressed(Keys.Z)){
			cam.zoom += .2;
		}else if(Gdx.input.isKeyPressed(Keys.X)){
			cam.zoom -= .2;
		}
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		world.dispose();
	}

}
