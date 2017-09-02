package xyz.derkades.skywars.loot;

import org.bukkit.Location;
import org.bukkit.block.Chest;

import xyz.derkades.skywars.Map;

public class Loot {
		
	public static boolean isLoot(Map map, Chest chest) {
		Location loc = chest.getLocation();
		for (LootChest loot : map.getLoot()) {
			Location lootLoc = loot.getLocation();
			if (lootLoc.getX() == loc.getBlockX() && lootLoc.getY() == loc.getBlockY() && lootLoc.getZ() == loc.getBlockZ()) {
				return true;
			}
		}
		return false;
	}

}
