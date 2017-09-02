package xyz.derkades.skywars;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Skywars extends JavaPlugin implements Listener {
	
	private static int COUNTDOWN_TIME = 10;
	
	public Skywars() {
		plugin = this;
	}
	
	private static Skywars plugin;
	
	public static Skywars getPlugin() {
		return plugin;
	}
	
	public static Map map;
	public static Mode mode;
	public static World world;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		if (Bukkit.getMaxPlayers() != 1) {
			disablePlugin("Max players in server.properties must be set to 1.");
			return;
		}
		
		String mapString = getConfig().getString("map");
		String modeString = getConfig().getString("mode");
		
		if (mapString == null || modeString == null) {
			disablePlugin("Config is incorrect.");
			return;
		}
		
		try {
			map = Map.valueOf(mapString);
			mode = Mode.valueOf(modeString);
		} catch (IllegalArgumentException e) {
			disablePlugin("Invalid map or mode name");
			return;
		}
		
		world = Bukkit.getWorld(mapString);
		
		if (world == null) {
			disablePlugin("Your world must be called " + mapString + " and set in server.properties");
			return;
		}
		
		getLogger().info("Plugin has started. The game will start automatically when enough players are online.");
		getLogger().info("Map: " + map.getName());
		getLogger().info("Mode: " + mode.toString());
		
		getServer().getPluginManager().registerEvents(this, this);
		
		new StartGameWhenEnoughPlayers().runTaskTimer(plugin, 10*20, 5*20);
	}
	
	private void disablePlugin(String reason) {
		getServer().getPluginManager().disablePlugin(this);
		getLogger().severe(reason);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin(PlayerLoginEvent event) {
	    int maxplayers = map.getRequiredPlayers(mode);
	    if ((Bukkit.getOnlinePlayers().size() < maxplayers)  && (event.getResult() == Result.KICK_FULL)) {
	        event.allow();
	    }
	}
	
	private class StartGameWhenEnoughPlayers extends BukkitRunnable {
		
		@Override
		public void run() {
			if (Bukkit.getOnlinePlayers().size() >= map.getRequiredPlayers(mode)) {
				//Enough players online, start game after countdown
				
				new BukkitRunnable() {
					
					int timeLeft = COUNTDOWN_TIME;
					
					public void run() {
						if (timeLeft == 10 || timeLeft == 5 || timeLeft <= 3) {
							Message.GAME_STARTING_IN.broadcast(timeLeft);
						}
						
						if (timeLeft <= 0) {
							new Game(map, mode).start();
							this.cancel();
							return;
						}
						
						timeLeft--;
					}
				}.runTaskTimer(Skywars.this, 0, 20);
				
			}
		}
		
	}
		

}
