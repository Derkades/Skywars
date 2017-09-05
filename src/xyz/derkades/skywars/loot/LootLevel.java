package xyz.derkades.skywars.loot;

import org.bukkit.Material;

public enum LootLevel {
	
	ONE(
			new LootItem(Material.STONE, 5, 16, 1.0f),
			new LootItem(Material.WEB, 1, 3, 0.3f),
			new LootItem(Material.TNT, 1, 3, 0.15f),
			new LootItem(Material.LAVA_BUCKET, 1, 1, 0.1f),
			new LootItem(Material.WATER_BUCKET, 1, 1, 0.3f),
			new LootItem(Material.SNOW_BALL, 4, 24, 0.3f),
			new LootItem(Material.WOOD, 16, 35, 1.0f),
			new LootItem(Material.BREAD, 2, 5, 0.6f),
			new LootItem(Material.GRILLED_PORK, 1, 4, 0.2f),
			new LootItem(Material.CAKE, 1, 1, 0.2f),
			new LootItem(Material.FISHING_ROD, 1, 1, 0.4f),
			new LootItem(Material.BOW, 1, 1, 0.2f),
			new LootItem(Material.ARROW, 2, 8, 0.6f),
			new LootItem(Material.EGG, 4, 20, 0.4f),
			new LootItem(Material.POTION, 16387, 1, 1, 0.2f),
			new LootItem(Material.POTION, 16387, 1, 1, 0.2f),
			new LootItem(Material.POTION, 16386, 1, 1, 0.2f),
			new LootItem(Material.POTION, 16386, 1, 1, 0.2f),
			new LootItem(Material.EXP_BOTTLE, 6, 20, 0.3f)
		), 
	
	TWO(
			new LootItem(Material.IRON_HELMET, 1, 1, 1.0f),
			new LootItem(Material.IRON_CHESTPLATE, 1, 1, 1.0f),
			new LootItem(Material.IRON_LEGGINGS, 1, 1, 1.0f),
			new LootItem(Material.IRON_BOOTS, 1, 1, 1.0f),
			new LootItem(Material.IRON_SWORD, 1, 1, 1.0f),
			new LootItem(Material.IRON_AXE, 1, 1, 1.0f),
			new LootItem(Material.IRON_PICKAXE, 1, 1, 1.0f),
			new LootItem(Material.DIAMOND_HELMET, 1, 1, 0.3f),
			new LootItem(Material.DIAMOND_BOOTS, 1, 1, 0.3f),
			new LootItem(Material.DIAMOND_SWORD, 1, 1, 0.3f)
		), 
	
	THREE();

	LootItem[] items;
	
	LootLevel(LootItem... items) {
		this.items = items;
	}

}
