package xyz.derkades.skywars;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum Message {
	
	GAME_STARTING_IN("The game will start in %s seconds."),
	
	KNOCKED_IN_VOID_BY("%s was knocked into the void by %s."),
	FELL_IN_VOID("%s fell into the void."),
	KILLED_BY("%s has been killed by %s."),
	KILLED_USING_MAGIC_BY("%s has been killed by %s using magic."),
	FELL_FROM_HIGH_PLACE("%s fell from a high place."),
	FELL_FROM_HIGH_PLACE_BY("%s was thrown onto lower ground by %s")
	
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
