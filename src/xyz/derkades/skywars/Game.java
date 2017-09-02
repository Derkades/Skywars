package xyz.derkades.skywars;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.derkades.skywars.loot.LootChest;

public class Game {
	
	private Map map;
	private Mode mode;
	
	Game(Map map, Mode mode){
		this.map = map;
		this.mode = mode;
	}
	
	public void start() {
		for (LootChest chest : map.getLoot()) {
			chest.fill();
		}
		
		List<Location> islands = map.getIslandLocations();
		
		if (mode == Mode.SOLO) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				Location island = islands.remove(0);
				player.teleport(island);
			}
		} else if (mode == Mode.TEAMS) {
			Location island = islands.remove(0);
			int i = 0;
			for (Player player : Bukkit.getOnlinePlayers()) {
				i++;
				
				player.teleport(island);
				
				if (i % 2 == 0) { //Get new island every 2 players
					island = islands.remove(0);
				}
			}
		} else {
			throw new AssertionError();
		}
	}

}
