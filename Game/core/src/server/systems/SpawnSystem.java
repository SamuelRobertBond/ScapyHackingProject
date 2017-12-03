package server.systems;

import java.util.HashMap;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import client.states.PlayerState;
import server.components.HealthComponent;
import server.components.SpawnID;
import server.entities.ServerPlayer;
import server.entities.Spawn;

public class SpawnSystem extends EntitySystem{
	
	public final int RESPAWN_TIME = 3000;
	
	private HashMap<ServerPlayer, RespawnData> respawnList;
	
	private ImmutableArray<Entity> spawns;
	private ImmutableArray<Entity> players;
	
	public SpawnSystem() {
		respawnList = new HashMap<ServerPlayer, SpawnSystem.RespawnData>();
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		spawns = engine.getEntitiesFor(Family.all(SpawnID.class).get());
		players = engine.getEntitiesFor(Family.all(HealthComponent.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		
		for(Entity e : players){
			
			ServerPlayer p = (ServerPlayer)e;
			
			if(p.hc.state == PlayerState.DEAD){
				if(!respawnList.containsKey(p)){
					respawnList.put(p, new RespawnData());
				}
			}
		}
		
		for(ServerPlayer p : respawnList.keySet()){
			respawnList.get(p).time += deltaTime;
			System.out.println(respawnList.get(p).time);
			if(respawnList.get(p).time > RESPAWN_TIME){
				respawnList.remove(p);
				respawn(p);
			}
		}
		
	}
	
	public void respawn(ServerPlayer player){
		player.respawn(getSpawn());
	}
	
	private Vector2 getSpawn(){
		
		int index = MathUtils.random(spawns.size() - 1);
		Spawn s = (Spawn)spawns.get(index);
		
		if(s.isOccupied()){
			return getSpawn();
		}
		
		return s.getPosition();
	}
	
	class RespawnData{
		public int time = 0;
	}
	
}
