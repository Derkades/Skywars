package xyz.derkades.skywars;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Skywars extends JavaPlugin {
	
	private static int COUNTDOWN_TIME = 10;
	
	public Skywars() {
		plugin = this;
	}
	
	private static Skywars plugin;
	
	public static Skywars getPlugin() {
		return plugin;
	}
	
	public static Map MAP;
	public static Mode MODE;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		if (Bukkit.getMaxPlayers() != 1) {
			disablePlugin("Max players in server.properties must be set to 1.");
		}
		
		MAP = Map.valueOf(getConfig().getString("map"));
		MODE = Mode.valueOf(getConfig().getString("mode"));
		
		if (MAP == null || MODE == null) {
			disablePlugin("Config is incorrect.");
			return;
		}
		
		getLogger().info("Plugin has started. The game will start automatically when enough players are online.");
		getLogger().info("Map: " + MAP.getName());
		getLogger().info("Mode: " + MODE.toString());
		
		new StartGameWhenEnoughPlayers().runTaskTimer(plugin, 10*20, 5*20);
	}
	
	private void disablePlugin(String reason) {
		getServer().getPluginManager().disablePlugin(this);
		getLogger().severe(reason);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin(PlayerLoginEvent event) {
	    int maxplayers = MAP.getRequiredPlayers(MODE);
	    if ((Bukkit.getOnlinePlayers().size() < maxplayers)  && (event.getResult() == Result.KICK_FULL)) {
	        event.allow();
	    }
	}
	
	private class StartGameWhenEnoughPlayers extends BukkitRunnable {
		
		@Override
		public void run() {
			if (Bukkit.getOnlinePlayers().size() >= MAP.getRequiredPlayers(MODE)) {
				//Enough players online, start game after countdown
				
				new BukkitRunnable() {
					
					int timeLeft = COUNTDOWN_TIME;
					
					public void run() {
						if (timeLeft == 10 || timeLeft == 5 || timeLeft <= 3) {
							Message.GAME_STARTING_IN.broadcast(timeLeft);
						}
						
						if (timeLeft <= 0) {
							new Game(MAP, MODE).start();
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
