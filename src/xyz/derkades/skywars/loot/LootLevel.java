package xyz.derkades.skywars.loot;

import org.bukkit.Material;

public enum LootLevel {
	
	ONE(new LootItem(Material.ROTTEN_FLESH, 1, 20)), 
	
	TWO(), 
	
	THREE();

	LootItem[] items;
	
	LootLevel(LootItem... items) {
		this.items = items;
	}

}
