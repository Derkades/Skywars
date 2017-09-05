package xyz.derkades.skywars;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

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
		Block block = location.getBlock();

		Block outer1 = block.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST, 2).getRelative(BlockFace.NORTH, 2); 
		Block outer2 = block.getRelative(BlockFace.UP, 3).getRelative(BlockFace.WEST, 2).getRelative(BlockFace.SOUTH, 2);
		
		fillArea(outer1, outer2, this.type, this.data); //Fill solid
		
		Block inner1 = block.getRelative(BlockFace.EAST).getRelative(BlockFace.NORTH);
		Block inner2 = block.getRelative(BlockFace.UP, 2).getRelative(BlockFace.WEST).getRelative(BlockFace.SOUTH);
		
		fillArea(inner1, inner2, Material.AIR, 0); //Remove inner part
	}
	
	public static void remove(final Location location) {
		Block block = location.getBlock();
		Block outer1 = block.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST, 2).getRelative(BlockFace.NORTH, 2); 
		Block outer2 = block.getRelative(BlockFace.UP, 3).getRelative(BlockFace.WEST, 2).getRelative(BlockFace.SOUTH, 2);
		
		fillArea(outer1, outer2, Material.AIR, 0);
	}
	
	@SuppressWarnings("deprecation")
	private static void fillArea(Block point1, Block point2, Material material, int data) {
		final int minX = Math.min(point1.getX(), point2.getX());
		final int maxX = Math.max(point1.getX(), point2.getX());
		final int minY = Math.min(point1.getY(), point2.getY());
		final int maxY = Math.max(point1.getY(), point2.getY());
		final int minZ = Math.min(point1.getZ(), point2.getZ()); 
		final int maxZ = Math.max(point1.getZ(), point2.getZ());
		
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				for (int z = minZ; z <= maxZ; z++) {
					Block block = new Location(Skywars.world, x, y, z).getBlock();
					block.setType(material);
					if (data > 0) block.setData((byte) data);
				}
			}
		}
	}

}
