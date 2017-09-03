package xyz.derkades.skywars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;

import xyz.derkades.skywars.loot.LootChest;

public enum Map {
	
	ESSENCIA("Essência", null, null, Mode.SOLO),
	FLORESTA("Floresta", null, null, Mode.TEAMS),
	INFERNO("Inferno", null, null, Mode.SOLO, Mode.TEAMS);
	
	private String name;
	private List<Location> islands;
	private LootChest[] loot;
	private List<Mode> supportedModes;
	
	Map(String name, Location[] islands, LootChest[] loot, Mode... supportedModes){
		this.name = name;
		if (islands != null) this.islands = Arrays.asList(islands);
		this.loot = loot;
		if (loot == null) loot = new LootChest[] {};
		this.supportedModes = Arrays.asList(supportedModes);
	}
	
	public String getName() {
		return name;
	}
	
	public List<Location> getIslandLocations() {
		if (islands == null) {
			return new ArrayList<>();
		}
		
		return islands;
	}
	
	public LootChest[] getLoot() {
		return loot;
	}
	
	public List<Mode> getSupportedModes() {
		return supportedModes;
	}
	
	public int getRequiredPlayers(Mode mode) {
		if (!this.getSupportedModes().contains(mode)) {
			throw new UnsupportedOperationException("This map doesn't support the given mode.");
		}
		
		if (mode == Mode.SOLO) {
			return islands.size();
		} else if (mode == Mode.TEAMS){
			return islands.size() * 2;
		} else {
			throw new AssertionError();
		}
	}
	
	@Override
	public String toString() {
		return name;
	}

}
