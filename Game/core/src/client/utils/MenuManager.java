package client.utils;

import java.util.HashMap;
import java.util.Set;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MenuManager {

	//Stages
	private Stage stage;
	
	//Tables
	private Table active;
	private HashMap<String, Table> tables;
	
	//Skins
	private Skin skin;
	
	/**
	 * Creates a menu manager to manage a scene 2d menu
	 * @param view - viewport neede for the stage
	 */
	public MenuManager(StretchViewport view) {
		
		stage = new Stage(view);
		skin = new Skin(Constants.SKIN_FILE);
		
		active = new Table();
		stage.addActor(active);
		active.setFillParent(true);
		
		tables = new HashMap<String, Table>();
		tables.put("main", active);
		
	}
	
	/**
	 * Renders the stage containing the table
	 */
	public void render(){
		stage.draw();
		stage.act();
	}
	
	/**
	 * Adds a new table, but does not switch to it.
	 * @param key
	 * @return returns the key of the table
	 */
	public String addTable(String key){
		Table table = tables.put(key, new Table());
		stage.addActor(table);
		table.setFillParent(true);
		table.setVisible(false);
		return key;
	}
	
	/**
	 * Sets this table as the active table, making the other tables invisible
	 * @param table - Table to be set as visible
	 * @return true if successful
	 */
	public boolean setActiveTable(String key){
		
		if(tables.containsKey(key)){
			active.setVisible(false);
			active = tables.get(key);
			active.setVisible(true);
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Adds a label to the active table
	 * @param text - Text to be added to the table
	 * @param size - font size
	 * @return the label added to the table
	 */
	public Label addLabel(String text, TextSize size){
		
		float scale = 2f;
		
		if(size == TextSize.LARGE){
			scale = 1.25f;
		}else if(size == TextSize.SMALL){
			scale = .5f;
		}
		
		Label label = new Label(text, skin);
		label.setFontScale(scale);
		
		active.add(label);
		
		return label;
		
	}
	
	/**
	 * Adds a floating label to the active table
	 * @param text
	 * @param x
	 * @param y
	 * @param size
	 * @return the label added to the table
	 */
	public Label addLabel(String text, float x, float y, TextSize size){
		
		float scale = 1f;
		
		if(size == TextSize.MEDIUM){
			scale = .5f;
		}else if(size == TextSize.SMALL){
			scale = .25f;
		}
		
		Label label = new Label(text, skin);
		label.setFontScale(scale);
		label.setPosition(x, y);
		
		active.add(label);
		
		return label;
		
	}
	
	/**
	 * Adds a TextButton to the active table
	 * @param text
	 * @return
	 */
	public TextButton addTextButton(String text){
		
		TextButton button = new TextButton(text, skin);
		active.add(button);
		return button;
		
	}
	
	/**
	 * Adds a TextButton to the active table
	 * @param text
	 * @return
	 */
	public TextButton addTextButton(String text, float x, float y){
		
		TextButton button = new TextButton(text, skin);
		button.setPosition(x, y);
		active.add(button);
		return button;
		
	}
	
	/**
	 * Adds a text field to the active table
	 * @return the text field
	 */
	public TextField addTextField(String message, float x, float y, float width, float height){
		TextField field = new TextField("", skin);
		field.setMessageText(message);
		active.add(field);
		return field;
	}
	
	/**
	 * Adds a row to the active table
	 */
	public void row(){
		active.row();
	}
	
	/**
	 * Sets this table as the active table, making the other tables invisible
	 * @param table - Table to be set as visible
	 */
	public void setActiveTable(Table table){
		active = table;
	}
	
	/**
	 * Returns all the tables in the menu
	 * @return
	 */
	public Set<String> getTables(){
		return tables.keySet();
	}

	/**
	 * Returns the stage
	 * @return
	 */
	public Stage getStage(){
		return stage;
	}
	
	/**
	 * Disposes resources
	 */
	public void dispose() {
		tables.clear();
		stage.dispose();
		skin.dispose();
	}
	
	
}
