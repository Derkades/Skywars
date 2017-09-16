package xyz.derkades.skywars;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Skywars extends JavaPlugin implements Listener {
	
	private static final int COUNTDOWN_TIME = 5;
	public static final int GAME_TIME = 15 * 60;
	public static final int CAGE_OPEN_TIME = 10;
	
	public static Skywars plugin;	
	public static Map map;
	public static Mode mode;
	public static World world;
	public static FileConfiguration data;
	
	public static List<UUID> alive;
	public static java.util.Map<UUID, Integer> kills;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		alive = new ArrayList<>();
		kills = new HashMap<>();
		
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
		
		if (getConfig().getBoolean("enabled", false)) {
			new StartGameWhenEnoughPlayers().runTaskTimer(plugin, 2*20, 2*20);
		} else {
			getLogger().warning("Automatic game starting is disabled. Set 'enabled: true' in config.yml. You can start this game manually by right-clicking a beetroot.");
		}
		
		String dataFilePath = getConfig().getString("data-file");
		if (dataFilePath == null || dataFilePath.isEmpty()) {
			disablePlugin("Invalid data file path");
		}
		
		File dataFile = new File(dataFilePath);
		
		if (dataFile.exists()) {
			data = YamlConfiguration.loadConfiguration(dataFile);
		} else {
			disablePlugin("Data file does not exist at " + dataFile.getAbsolutePath() + ". Is this the right path? Did you create the file?");
		}
		
		getLogger().warning("This world will not be saved. When you reboot the server all changes will be reset. If you wish to make changes, save manually using /save-all");
		
		world.setAutoSave(false);
		
		world.setGameRuleValue("announceAdvancements", "false");
		world.setGameRuleValue("doFireTick", "false");
		world.setGameRuleValue("doMobLoot", "false");
		world.setGameRuleValue("doMobSpawning", "false");
		world.setGameRuleValue("announceAdvancements", "false");
		world.setGameRuleValue("spawnRadius", "0");
		world.setGameRuleValue("mobGriefing", "true");
		
		world.setGameRuleValue("doDaylightCycle", "false");
		world.setTime(6000);
		
		getServer().getScheduler().runTaskTimer(this, () -> world.setStorm(false), 20, 20);
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
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		event.setJoinMessage(Message.JOIN.get(player.getName(), Bukkit.getOnlinePlayers().size(), map.getRequiredPlayers(mode)));
		
		player.setGameMode(GameMode.ADVENTURE); //So players can't break blocks in the lobby
		player.teleport(new Location(world, 0, 176, 0));
		
		player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		player.setFoodLevel(20);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		event.setQuitMessage(Message.QUIT.get(player.getName()));
		
		alive.remove(player.getUniqueId());
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDeath(PlayerDeathEvent event) {
		DamageCause cause = event.getEntity().getLastDamageCause().getCause();
		
		Player player = event.getEntity();
		LivingEntity killer = player.getKiller();
		
		if (killer != null && cause == DamageCause.VOID) {
			event.setDeathMessage(Message.KNOCKED_IN_VOID_BY.get(player.getName(), killer.getName()));
			addKill(killer);
		} else if (killer == null && cause == DamageCause.VOID) {
			event.setDeathMessage(Message.FELL_IN_VOID.get(player.getName()));
		} else if (killer != null && (cause == DamageCause.ENTITY_ATTACK || cause == DamageCause.ENTITY_SWEEP_ATTACK)) {
			event.setDeathMessage(Message.KILLED_BY.get(player.getName(), killer.getName()));
			addKill(killer);
		} else if (killer != null && cause == DamageCause.MAGIC) {
			event.setDeathMessage(Message.KILLED_USING_MAGIC_BY.get(player.getName(), killer.getName()));
			addKill(killer);
		} else if (killer == null && cause == DamageCause.FALL) {
			event.setDeathMessage(Message.FELL_FROM_HIGH_PLACE.get(player.getName()));
		} else if (killer != null && cause == DamageCause.FALL) {
			event.setDeathMessage(Message.FELL_FROM_HIGH_PLACE_BY.get(player.getName(), killer.getName()));
			addKill(killer);
		} else {
			event.setDeathMessage("");
			getLogger().warning("Unsupported death cause!");
			getLogger().warning("Cause: " + cause);
			getLogger().warning("Killed by player: " + (killer != null));
		}
		
		player.setGameMode(GameMode.SPECTATOR);
		alive.remove(player.getUniqueId());
		SkywarsPlayer skywarsPlayer = new SkywarsPlayer(player);
		skywarsPlayer.addDeath();
	}
	
	private void addKill(LivingEntity killer) {
		if (kills.containsKey(killer.getUniqueId())) {
			kills.put(killer.getUniqueId(), kills.get(killer.getUniqueId()) + 1);
		} else {
			kills.put(killer.getUniqueId(), 1);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND && event.getItem() != null && event.getItem().getType() == Material.BEETROOT) {
			Player player = event.getPlayer();
			PlayerInventory inv = player.getInventory();
			inv.setItem(inv.getHeldItemSlot(), new ItemStack(Material.AIR));
			player.sendMessage("game will start when enough players are online");
			new StartGameWhenEnoughPlayers().runTaskTimer(plugin, 2*20, 2*20);
		}
	}
	
	public static void debug(String message) {
		Skywars.plugin.getLogger().info("[debug] " + message);
	}
	
	public static void debug(String message, Object object) {
		debug(message + ": " + object);
	}
	
	private class StartGameWhenEnoughPlayers extends BukkitRunnable {
		
		@Override
		public void run() {
			int online = Bukkit.getOnlinePlayers().size();
			int required = map.getRequiredPlayers(mode);
			if (online >= required) {
				//Enough players online, start game after countdown
				
				this.cancel(); //Prevent from running again
				
				new BukkitRunnable() {
					
					int timeLeft = COUNTDOWN_TIME;
					
					public void run() {
						debug("Time left: " + timeLeft);
						
						if (timeLeft == 5 || timeLeft == 3 || timeLeft == 2) {
							Message.GAME_STARTING_IN_SECONDS.broadcast(timeLeft);
						}
						
						if (timeLeft == 1) {
							Message.GAME_STARTING_IN_SECOND.broadcast();
						}
						
						if (timeLeft <= 0) {
							this.cancel();
							new Game(map, mode).start();
							return;
						}
						
						timeLeft--;
					}
				}.runTaskTimer(Skywars.this, 0, 20);
			} else {
				getLogger().info(String.format("Not enough players (%s/%s)", online, required));
			}
		}
		
	}

}
