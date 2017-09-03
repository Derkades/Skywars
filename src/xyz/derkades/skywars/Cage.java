package xyz.derkades.skywars;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public enum Cage {
	
	PINK("Pink", Material.STAINED_GLASS, 6, 10),
	
	;
	
	private String name;
	private Material type;
	private int data;
	private int cost;
	private String permission;
	
	Cage(String name, Material type, int data, int cost){
		this.name = name;
		this.type = type;
		this.data = data;
		this.cost = cost;
	}
	
	Cage(String name, Material type, int data, String permission){
		this.name = name;
		this.type = type;
		this.data = data;
		this.permission = permission;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCost() {
		return cost;
	}

	public String getPermission() {
		return permission;
	}
	
	public void spawn(final Location location) {
		final Location outer1 = location.add(-2, -1, -2);
		final Location outer2 = location.add(2, 4, 2);
		fillArea(outer1.getBlockX(), outer1.getBlockY(), outer1.getBlockZ(), 
				outer2.getBlockX(), outer2.getBlockY(), outer2.getBlockZ(), 
				this.type, this.data);
		
		final Location inner1 = location.add(-1, 0, -1);
		final Location inner2 = location.add(1, 2, 1);
		fillArea(inner1.getBlockX(), inner1.getBlockY(), inner1.getBlockZ(), 
				inner2.getBlockX(), inner2.getBlockY(), inner2.getBlockZ(),
				Material.AIR, 0);
	}
	
	public static void remove(final Location location) {
		final Location outer1 = location.add(-2, -1, -2);
		final Location outer2 = location.add(2, 4, 2);
		fillArea(outer1.getBlockX(), outer1.getBlockY(), outer1.getBlockZ(), 
				outer2.getBlockX(), outer2.getBlockY(), outer2.getBlockZ(), 
				Material.AIR, 0);
	}
	
	@SuppressWarnings("deprecation")
	private static void fillArea(int x1, int y1, int z1, int x2, int y2, int z2, Material material, int data) {
		final int minX = Math.min(x1, x2);
		final int maxX = Math.max(x1, x2);
		final int minY = Math.min(y1, y2);
		final int maxY = Math.max(y1, y2);
		final int minZ = Math.min(z1, z2);
		final int maxZ = Math.max(z1, z2);
		
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = minZ; z <= maxZ; z++) {
					Block block = new Location(Skywars.world, x, y, z).getBlock();
					block.setType(material);
					block.setData((byte) data);
				}
			}
		}
	}

}
