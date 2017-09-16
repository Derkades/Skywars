package xyz.derkades.skywars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;

import xyz.derkades.skywars.loot.LootChest;
import xyz.derkades.skywars.loot.LootLevel;

public enum Map {
	
	ESSENCIA("Essência", null, null, Mode.SOLO),
	FLORESTA("Floresta", null, null, Mode.TEAMS),
	
	INFERNO("Inferno", 
			
			new Point[] {
					//new Point(0, 110, -87, 0, 0),
					new Point(54, 110, -52, 25, 0),
			}, 
			
			new LootChest[] {
					new LootChest(LootLevel.ONE, 63, 104, 54), new LootChest(LootLevel.ONE, 64, 105, 55), new LootChest(LootLevel.ONE, 65, 107, 61), 
					new LootChest(LootLevel.ONE, 64, 107, 63), new LootChest(LootLevel.ONE, 53, 104, 64), new LootChest(LootLevel.ONE, 52, 103, 63),
					
					new LootChest(LootLevel.ONE, 7, 104, 96), new LootChest(LootLevel.ONE, 6, 107, 101), new LootChest(LootLevel.ONE, -2, 106, 101), 
					new LootChest(LootLevel.ONE, -4, 105, 99),new LootChest(LootLevel.ONE, -7, 104, 95), new LootChest(LootLevel.ONE, -8, 103, 94),
					
					new LootChest(LootLevel.ONE, -55, 103, 62), new LootChest(LootLevel.ONE, -56, 105, 64), new LootChest(LootLevel.ONE, -62, 105, 63), 
					new LootChest(LootLevel.ONE, -63, 105, 62), new LootChest(LootLevel.ONE, -64, 105, 54), new LootChest(LootLevel.ONE, -52, 103, 56),
					
					new LootChest(LootLevel.ONE, -96, 104, 7), new LootChest(LootLevel.ONE, -98, 105, 6), new LootChest(LootLevel.ONE, -102, 107, 1), 
					new LootChest(LootLevel.ONE, -101, 106, -2), new LootChest(LootLevel.ONE, -96, 104, -6), new LootChest(LootLevel.ONE, -94, 103, -8),
					
					new LootChest(LootLevel.ONE, -63, 104, -55), new LootChest(LootLevel.ONE, -64, 105, -56), new LootChest(LootLevel.ONE, -64, 105, -59), 
					new LootChest(LootLevel.ONE, -53, 104, -64), new LootChest(LootLevel.ONE, -63, 105, -62), new LootChest(LootLevel.ONE, -54, 105, -64),
					
					new LootChest(LootLevel.ONE, 8, 103, -94), new LootChest(LootLevel.ONE, 2, 106, -101), new LootChest(LootLevel.ONE, 6, 103, -95), 
					new LootChest(LootLevel.ONE, -1, 106, -101), new LootChest(LootLevel.ONE, -6, 105, -98), new LootChest(LootLevel.ONE, -7, 104, -96),
					
					new LootChest(LootLevel.ONE, 54, 104, -63), new LootChest(LootLevel.ONE, 55, 105, -64), new LootChest(LootLevel.ONE, 60, 106, -65),
					new LootChest(LootLevel.ONE, 64, 104, -53), new LootChest(LootLevel.ONE, 64, 106, -62), new LootChest(LootLevel.ONE, 60, 100, -46),
					
					new LootChest(LootLevel.ONE, 94, 103, -7), new LootChest(LootLevel.ONE, 94, 103, 8), new LootChest(LootLevel.ONE, 97, 105, -7), 
					new LootChest(LootLevel.ONE, 102, 108, -5), new LootChest(LootLevel.ONE, 102, 107, -2), new LootChest(LootLevel.ONE, 98, 107, 7),
					
						
					new LootChest(LootLevel.TWO, -4, 102, -61), new LootChest(LootLevel.TWO, -3, 102, -62),
					new LootChest(LootLevel.TWO, 3, 102, -62), new LootChest(LootLevel.TWO, 4, 102, -61),
					new LootChest(LootLevel.TWO, 4, 102, -55), new LootChest(LootLevel.TWO, 3, 102, -54),
					new LootChest(LootLevel.TWO, -2, 102, -54), new LootChest(LootLevel.TWO, -4, 102, -55),
		
					new LootChest(LootLevel.TWO, -33, 102, -40), new LootChest(LootLevel.TWO, -32, 102, -39),
					new LootChest(LootLevel.TWO, -32, 102, -33), new LootChest(LootLevel.TWO, -33, 102, -32),
					new LootChest(LootLevel.TWO, -39, 102, -32), new LootChest(LootLevel.TWO, -40, 102, -33),
					new LootChest(LootLevel.TWO, -40, 102, -39), new LootChest(LootLevel.TWO, -39, 102, -40),
		
					new LootChest(LootLevel.TWO, -54, 102, 3), new LootChest(LootLevel.TWO, -55, 102, 4),
					new LootChest(LootLevel.TWO, -61, 102, 4), new LootChest(LootLevel.TWO, -62, 102, 3),
					new LootChest(LootLevel.TWO, -62, 102, -3), new LootChest(LootLevel.TWO, -61, 102, -4),
					new LootChest(LootLevel.TWO, -55, 102, -4), new LootChest(LootLevel.TWO, -54, 102, -3),
		
					new LootChest(LootLevel.TWO, -40, 102, 33), new LootChest(LootLevel.TWO, -39, 102, 32),
					new LootChest(LootLevel.TWO, -33, 102, 32), new LootChest(LootLevel.TWO, -32, 102, 33),
					new LootChest(LootLevel.TWO, -32, 102, 39),  new LootChest(LootLevel.TWO, -33, 102, 40),
					new LootChest(LootLevel.TWO, -39, 102, 40), new LootChest(LootLevel.TWO, -40, 102, 39),
		
					new LootChest(LootLevel.TWO, -4, 102, 55), new LootChest(LootLevel.TWO, -3, 102, 54),
					new LootChest(LootLevel.TWO, 3, 102, 54), new LootChest(LootLevel.TWO, 4, 102, 55),
					new LootChest(LootLevel.TWO, 4, 102, 61), new LootChest(LootLevel.TWO, 3, 102, 62),
					new LootChest(LootLevel.TWO, -3, 102, 62), new LootChest(LootLevel.TWO, -4, 102, 61),
		
					new LootChest(LootLevel.TWO, 32, 102, 33), new LootChest(LootLevel.TWO, 33, 102, 32),
					new LootChest(LootLevel.TWO, 39, 102, 32), new LootChest(LootLevel.TWO, 40, 102, 33),
					new LootChest(LootLevel.TWO, 40, 102, 39), new LootChest(LootLevel.TWO, 39, 102, 40),
					new LootChest(LootLevel.TWO, 33, 102, 40), new LootChest(LootLevel.TWO, 32, 102, 39),
		
					new LootChest(LootLevel.TWO, 62, 102, 3), new LootChest(LootLevel.TWO, 61, 102, 4),
					new LootChest(LootLevel.TWO, 55, 102, 4), new LootChest(LootLevel.TWO, 54, 102, 3),
					new LootChest(LootLevel.TWO, 54, 102, -3), new LootChest(LootLevel.TWO, 55, 102, -4),
					new LootChest(LootLevel.TWO, 61, 102, -4), new LootChest(LootLevel.TWO, 62, 102, -3),
		
					new LootChest(LootLevel.TWO, 32, 102, -32), new LootChest(LootLevel.TWO, 32, 102, -33),
					new LootChest(LootLevel.TWO, 32, 102, -39), new LootChest(LootLevel.TWO, 33, 102, -40),
					new LootChest(LootLevel.TWO, 39, 102, -40), new LootChest(LootLevel.TWO, 40, 102, -39),
					new LootChest(LootLevel.TWO, 40, 102, -33), new LootChest(LootLevel.TWO, 39, 102, -32),
					
					
					new LootChest(LootLevel.THREE, 16, 101, -10), new LootChest(LootLevel.THREE, 10, 101, 16),
					new LootChest(LootLevel.THREE, 16, 101, 10), new LootChest(LootLevel.THREE, -10, 101, 16),
					
					new LootChest(LootLevel.THREE, -16, 101, 10), new LootChest(LootLevel.THREE, -10, 101, -16),
					new LootChest(LootLevel.THREE, -16, 101, -10), new LootChest(LootLevel.THREE, 10, 101, -16),
					
					new LootChest(LootLevel.THREE, 5, 100, 5), new LootChest(LootLevel.THREE, -5, 100, -5),
					new LootChest(LootLevel.THREE, -5, 100, 5), new LootChest(LootLevel.THREE, 5, 100, -5),
					
					new LootChest(LootLevel.THREE, 1, 100, 0), new LootChest(LootLevel.THREE, 0, 100, -1),
					new LootChest(LootLevel.THREE, -1, 100, 0), new LootChest(LootLevel.THREE, 0, 100, 1),
			}, Mode.SOLO, Mode.TEAMS);
	
	private String name;
	private Point[] points;
	private LootChest[] loot;
	private List<Mode> supportedModes;
	
	Map(String name, Point[] islands, LootChest[] loot, Mode... supportedModes){
		this.name = name;
		this.points = islands;
		this.loot = loot;
		this.supportedModes = Arrays.asList(supportedModes);
	}
	
	public String getName() {
		return name;
	}
	
	public List<Location> getIslandLocations() {		
		if (points == null) {
			return new ArrayList<>();
		}
		
		List<Location> locations = new ArrayList<>();
		for (Point point : points) {
			locations.add(new Location(Skywars.world, point.x, point.y, point.z, point.yaw, point.pitch));
		}
		
		return locations;
	}
	
	public LootChest[] getLoot() {
		if (loot == null) {
			return new LootChest[] {};
		}
		
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
			return points.length;
		} else if (mode == Mode.TEAMS){
			return points.length * 2;
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
		
		Point(int x, int y, int z, float yaw, float pitch){
			this.x = x;
			this.y = y;
			this.z = z;
			this.yaw = yaw;
			this.pitch = pitch;
		}

	}

}
