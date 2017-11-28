package client.screens;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Constants;

import client.ClientManager;
import client.entities.Player;
import client.worlds.ClientGameWorld;
import network.requests.JoinRequest;
import network.responses.JoinResponse;
import server.ServerManager;

public class GameScreen implements Screen{

	private Game game;
	private ClientGameWorld world;
	private OrthographicCamera cam;
	
	//Client Server
	private ServerManager server;
	private ClientManager client;
	
	
	public GameScreen(Game game, boolean isServer) {
		
		this.game = game;
		cam = new OrthographicCamera(Constants.V_WIDTH / Constants.PPM, Constants.V_HEIGHT / Constants.PPM);
		
		if(isServer){
			server = new ServerManager();
			server.enableDebug(cam);
		}
		
		client = new ClientManager();
		world = new ClientGameWorld(client, cam);
		
		client.getClient().sendTCP(new JoinRequest());
		
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
		if(server != null){
			server.debugRenderer(delta);
		}
		
		debugCamera(delta);
		
		
	}
	
	private void debugCamera(float delta){
		
		if(Gdx.input.isKeyPressed(Keys.Z)){
			cam.zoom += 2;
		}else if(Gdx.input.isKeyPressed(Keys.X)){
			cam.zoom -= 2;
		}
		
	}
	

	@Override
	public void resize(int width, int height) {
		
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
