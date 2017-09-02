package xyz.derkades.skywars;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Skywars extends JavaPlugin implements Listener {
	
	private static final int COUNTDOWN_TIME = 10;
	public static final int GAME_TIME = 15 * 60;
	
	public static Skywars plugin;	
	public static Map map;
	public static Mode mode;
	public static World world;
	
	@Override
	public void onEnable() {
		plugin = this;
		
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
		
		Bukkit.setDefaultGameMode(GameMode.SURVIVAL);
		
		world.setGameRuleValue("announceAdvancements", "false");
		world.setGameRuleValue("doFireTick", "false");
		world.setGameRuleValue("doMobLoot", "false");
		world.setGameRuleValue("doMobSpawning", "false");
		world.setGameRuleValue("announceAdvancements", "false");
		world.setGameRuleValue("spawnRadius", "0");
		world.setGameRuleValue("mobGriefing", "true");
		
		world.setGameRuleValue("doDaylightCycle", "false");
		world.setTime(6000);
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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDeath(PlayerDeathEvent event) {
		DamageCause cause = event.getEntity().getLastDamageCause().getCause();
		
		Player player = event.getEntity();
		LivingEntity killer = player.getKiller();
		
		if (killer != null && cause == DamageCause.VOID) {
			event.setDeathMessage(Message.KNOCKED_IN_VOID_BY.get(player.getName(), killer.getName()));
		} else if (killer == null && cause == DamageCause.VOID) {
			event.setDeathMessage(Message.FELL_IN_VOID.get(player.getName()));
		} else if (killer != null && (cause == DamageCause.ENTITY_ATTACK || cause == DamageCause.ENTITY_SWEEP_ATTACK)) {
			event.setDeathMessage(Message.KILLED_BY.get(player.getName(), killer.getName()));
		} else if (killer != null && cause == DamageCause.MAGIC) {
			event.setDeathMessage(Message.KILLED_USING_MAGIC_BY.get(player.getName(), killer.getName()));
		} else if (killer == null && cause == DamageCause.FALL) {
			event.setDeathMessage(Message.FELL_FROM_HIGH_PLACE.get(player.getName()));
		} else if (killer != null && cause == DamageCause.FALL) {
			event.setDeathMessage(Message.FELL_FROM_HIGH_PLACE_BY.get(player.getName(), killer.getName()));
		} else {
			event.setDeathMessage("");
			getLogger().warning("Unsupported death cause!");
			getLogger().warning("Cause: " + cause);
			getLogger().warning("Killed by player? " + (killer != null));
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
