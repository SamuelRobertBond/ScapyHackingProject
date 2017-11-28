package client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Constants;

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
		
		TextButton b = menu.addTextButton("Client");
		b.pad(3f, 6f, 3f, 6f);
		b.addListener(new ChangeListener(){
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				startGame(false);
			}
			
		});
		menu.getActiveTable().getCell(b).pad(10f, 0, 1f, 0);
		menu.row();
		
		b = menu.addTextButton("Server");
		b.addListener(new ChangeListener(){
		
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				startGame(true);
			}
			
		});
		
		b.pad(3f, 6f, 3f, 6f);
		menu.getActiveTable().getCell(b).pad(1f, 0, 1f, 0);
		
		in = new InputMultiplexer();
		in.addProcessor(menu.getStage());
		
		Gdx.input.setInputProcessor(in);
	}
	
	
	private void startGame(boolean isServer){
		this.dispose();
		game.setScreen(new GameScreen(game, isServer));
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
		view.update(width, height);
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
