package xyz.derkades.skywars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;

import xyz.derkades.skywars.loot.LootChest;

public enum Map {
	
	ESSENCIA("Essência", null, null, Mode.SOLO),
	FLORESTA("Floresta", null, null, Mode.TEAMS),
	
	INFERNO("Inferno", 
			
			new Point[] {
					//new Point(0.5, 110, -87.5, 0, 0),
					new Point(54.5, 110, -52.5, 25, 0),
			}, 
			
			null, Mode.SOLO, Mode.TEAMS);
	
	private String name;
	private List<Location> islands;
	private LootChest[] loot;
	private List<Mode> supportedModes;
	
	Map(String name, Point[] islands, LootChest[] loot, Mode... supportedModes){
		this.name = name;
		this.islands = convertToBukkit(islands);
		this.loot = loot;
		if (loot == null) loot = new LootChest[] {};
		this.supportedModes = Arrays.asList(supportedModes);
	}
	
	public String getName() {
		return name;
	}
	
	public List<Location> getIslandLocations() {		
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
	
	private static class Point {
		
		private double x;
		private double y;
		private double z;
		private float yaw;
		private float pitch;
		
		Point(double x, double y, double z, float yaw, float pitch){
			this.x = x;
			this.y = y;
			this.z = z;
			this.yaw = yaw;
			this.pitch = pitch;
		}

	}
	
	private static List<Location> convertToBukkit(Point[] points){
		if (points == null) {
			return new ArrayList<>();
		}
		
		List<Location> locations = new ArrayList<>();
		for (Point point : points) {
			locations.add(new Location(Skywars.world, point.x, point.y, point.z, point.yaw, point.pitch));
		}
		return locations;
	}

}
