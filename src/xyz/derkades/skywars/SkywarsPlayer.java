package xyz.derkades.skywars;

import org.bukkit.OfflinePlayer;

public class SkywarsPlayer {
	
	@SuppressWarnings("unused")
	private OfflinePlayer player;
	
	public SkywarsPlayer(OfflinePlayer player) {
		this.player = player;
	}
	
	public void addKills(int kill) {
		
	}
	
	public void addDeath() {
		
	}
	
	public int getKills() {
		return 0;
	}
	
	public int getDeaths() {
		return 0;
	}
	
	public Cage getCage() {
		return Cage.PINK;
	}

}
