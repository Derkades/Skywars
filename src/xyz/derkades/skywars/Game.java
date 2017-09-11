package xyz.derkades.skywars;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.coloredcarrot.api.sidebar.Sidebar;
import com.coloredcarrot.api.sidebar.SidebarString;

import xyz.derkades.derkutils.ListUtils;
import xyz.derkades.derkutils.bukkit.Colors;
import xyz.derkades.skywars.loot.LootChest;

public class Game {
	
	private Map map;
	private Mode mode;
	
	private Sidebar sidebar;

	Game(Map map, Mode mode){
		this.map = map;
		this.mode = mode;
	}
	
	public void start() {
		//Fill loot chests
		for (LootChest chest : map.getLoot()) {
			chest.fill();
		}
		
		//Spawn cages and teleport players
		teleportPlayers();
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			new KitsMenu(player).open();
		}
		
		final FileConfiguration config = Skywars.plugin.getConfig();
		final String title = Colors.parseColors(config.getString("scoreboard-title"));
		final List<String> content = Colors.parseColors(config.getStringList("scoreboard"));
		
		sidebar = new Sidebar(title, Skywars.plugin, new SidebarString(":D"));
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			sidebar.showTo(player);
		}
		
		new BukkitRunnable() {
			
			int secondsLeft = Skywars.GAME_TIME + Skywars.CAGE_OPEN_TIME;
			
			@Override
			public void run() {
				updateSidebar(title, content, getClockString(secondsLeft));
				
				int secondsBeforeCageOpen = secondsLeft - Skywars.GAME_TIME;
				
				if (secondsBeforeCageOpen > 1 && secondsBeforeCageOpen <= 5) {
					Message.CAGE_OPEN_IN_SECONDS.broadcast(secondsBeforeCageOpen);
				} else if (secondsBeforeCageOpen == 1) {
					Message.CAGE_OPEN_IN_SECOND.broadcast();
				} else if (secondsBeforeCageOpen == 0) {
					Message.CAGES_OPENED.broadcast();
					
					for (Player player : Bukkit.getOnlinePlayers()) {
						player.setGameMode(GameMode.SURVIVAL);
					}
					
					openCages();
				}
				
				if (secondsLeft == 10*60) {
					Message.MINUTES_LEFT.broadcast(5);
				} else if (secondsLeft == 5*60) {
					Message.MINUTES_LEFT.broadcast(5);
				} else if (secondsLeft == 60) {
					Message.MINUTE_LEFT.broadcast();
				} else if (secondsLeft == 30 || secondsLeft == 10 || (secondsLeft < 5 && secondsLeft > 1)) {
					Message.SECONDS_LEFT.broadcast(secondsLeft);
				} else if (secondsLeft == 1) {
					Message.SECOND_LEFT.broadcast();
				}
				
				if (secondsLeft == 0) {
					deathMatch();
					this.cancel();
					return;
				}
				
				secondsLeft--;
			}
			
		}.runTaskTimer(Skywars.plugin, 0, 20);
	}
	
	private void teleportPlayers() {
		List<Location> islands = map.getIslandLocations();
		
		if (mode == Mode.SOLO) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				Location island = islands.remove(0);
				island.add(0.5, 0, 0.5);
				new SkywarsPlayer(player).getCage().spawn(island);
				player.teleport(island);
			}
		} else if (mode == Mode.TEAMS) {
			Location island = null;
			int i = 0;
			for (Player player : Bukkit.getOnlinePlayers()) {				
				if (i % 2 == 0) { //Get new island every 2 players
					island = islands.remove(0);
					island.add(0.5, 0, 0.5);
				}
				
				//2 times the same location for two players -> last player cage will override.
				new SkywarsPlayer(player).getCage().spawn(island);
				
				player.teleport(island);
				
				i++;
			}
		} else {
			throw new AssertionError();
		}
	}
	
	private void openCages() {
		for (Location location : map.getIslandLocations()) {
			Cage.remove(location);
		}
	}
	
	private String getClockString(int secondsLeft) {
		if (secondsLeft < 60) {
			if (String.valueOf(secondsLeft).length() == 1) {
				return "00:0" + secondsLeft;
			}
			
			return "00:" + secondsLeft;
		} else {
			String minutes = String.valueOf(secondsLeft / 60);
			String seconds = String.valueOf(secondsLeft % 60);
			
			if (seconds.length() == 1)
				seconds = "0" + seconds;
			
			if (minutes.length() == 1)
				minutes = "0" + minutes;
			
			
			return minutes + ":" + seconds;
		}
	}
	
	private void updateSidebar(String title, List<String> content, String time) {
		content = ListUtils.replaceInStringList(content, new String[] {"{map}", "{mode}", "{time}"}, new String[] {map.getName(), mode.toString(), time});
		
		List<SidebarString> sidebarStrings = new ArrayList<>();
		for (String string : content) {
			sidebarStrings.add(new SidebarString(string));
		}
		
		sidebar.setEntries(sidebarStrings);
		sidebar.update();
	}
	
	private void deathMatch() {
		//Hide sidebar
		for (Player player : Bukkit.getOnlinePlayers()) {
			sidebar.hideFrom(player);
		}
	}

}
