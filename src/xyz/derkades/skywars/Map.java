package xyz.derkades.skywars;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;

public enum Map {
	
	ESSENCIA("Essência", null, Mode.SOLO),
	FLORESTA("Floresta", null, Mode.TEAMS),
	INFERNO("Inferno", null, Mode.SOLO, Mode.TEAMS);
	
	private String name;
	private List<Location> islands;
	private List<Mode> supportedModes;
	
	Map(String name, Location[] islands, Mode... supportedModes){
		this.name = name;
		this.islands = Arrays.asList(islands);
		this.supportedModes = Arrays.asList(supportedModes);
	}
	
	public String getName() {
		return name;
	}
	
	public List<Location> getIslandLocations() {
		return islands;
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
