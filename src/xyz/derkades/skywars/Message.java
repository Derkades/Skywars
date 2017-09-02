package xyz.derkades.skywars;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum Message {
	
	GAME_STARTING_IN("The game will start in %s seconds");
	
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
