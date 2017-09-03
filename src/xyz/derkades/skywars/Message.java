package xyz.derkades.skywars;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum Message {
	
	// Countdowns
	
	GAME_STARTING_IN_SECONDS("The game will start in %s seconds."),
	GAME_STARTING_IN_SECOND("The game will start in 1 second."),
	CAGE_OPEN_IN_SECONDS("Cages will open in %s seconds"),
	CAGE_OPEN_IN_SECOND("Cages will open in 1 second."),
	CAGES_OPENED("Cages have been opened."),
	
	MINUTE_LEFT("1 minute left until deathmatch."),
	MINUTES_LEFT("%s minutes left until deathmatch."),
	SECOND_LEFT("1 seconds left until deathmatch."),
	SECONDS_LEFT("%s seconds left until deathmatch."),
	
	// Death messages
	
	KNOCKED_IN_VOID_BY("%s was knocked into the void by %s."),
	FELL_IN_VOID("%s fell into the void."),
	KILLED_BY("%s has been killed by %s."),
	KILLED_USING_MAGIC_BY("%s has been killed by %s using magic."),
	FELL_FROM_HIGH_PLACE("%s fell from a high place."),
	FELL_FROM_HIGH_PLACE_BY("%s was thrown onto lower ground by %s"),
	
	// Kits
	
	KITS_MENU_TITLE("Kits"),
	KIT_SELECTED("You have selected %s"),
	
	JOIN("%s joined (%s/%s)"),
	QUIT("%s left"),
	
	;
	
	private String message;
	
	Message(String message){
		this.message = message;
	}
	
	public String get(Object... placeholders) {
		return String.format(message, placeholders);
	}
	
	public void broadcast(Object... placeholders) {
		Bukkit.broadcastMessage(this.get(placeholders));
	}
	
	public void send(Player player, Object... placeholders) {
		player.sendMessage(this.get(placeholders));
	}

}
