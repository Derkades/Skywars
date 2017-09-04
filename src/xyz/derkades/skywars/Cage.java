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
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();

		fillArea(x - 2, y - 1, z - 2, x + 2, y + 3, z + 2, this.type, this.data); //Fill solid
		fillArea(x - 1, y, z - 1, x + 1, y + 2, z + 1, this.type, this.data); //Remove inner part
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
		Skywars.debug("x1", x1);
		Skywars.debug("x2", x2);
		Skywars.debug("y1", y1);
		Skywars.debug("y2", y2);
		final int minX = Math.min(x1, x2); Skywars.debug("minx", minX);
		final int maxX = Math.max(x1, x2); Skywars.debug("maxx", maxX);
		final int minY = Math.min(y1, y2); Skywars.debug("miny", minY);
		final int maxY = Math.max(y1, y2); Skywars.debug("maxy", maxY);
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
