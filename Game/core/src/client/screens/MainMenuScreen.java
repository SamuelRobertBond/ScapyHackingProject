package client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import client.utils.Constants;
import client.utils.MenuManager;
import client.utils.TextSize;

public class MainMenuScreen implements Screen{
	
	private Game game;
	private MenuManager menu;
	private OrthographicCamera camera;
	private StretchViewport view;
	private InputMultiplexer in;
	
	public MainMenuScreen(Game game) {
		
		this.game = game;
		camera = new OrthographicCamera(Constants.M_V_WIDTH, Constants.M_V_HEIGHT);
		view = new StretchViewport(Constants.M_V_WIDTH, Constants.M_V_HEIGHT);
		
		menu = new MenuManager(view);
		menu.addLabel("Pirate Cannon Shooter", TextSize.MEDIUM);
		menu.row();
		menu.addTextButton("Button");
		menu.row();
		menu.addTextField("Text:", 100, 100, 200, 50);
		
		in = new InputMultiplexer();
		in.addProcessor(menu.getStage());
		
		Gdx.input.setInputProcessor(in);
	}
	
	@Override
	public void show() {
		
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		menu.render();
		
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
		menu.dispose();
		
	}

}
