package com.mygdx.game;

import com.badlogic.gdx.Game;

import client.screens.GameScreen;
import client.screens.MainMenuScreen;

public class GdxGame extends Game{

	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new MainMenuScreen(this));
	}
	
}
