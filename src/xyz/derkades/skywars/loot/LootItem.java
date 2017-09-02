package xyz.derkades.skywars.loot;

import org.bukkit.Material;

import xyz.derkades.derkutils.Random;

public class LootItem {
	
	private Material material;
	private int min;
	private int max;

	LootItem(Material material, int min, int max) {
		this.material = material;
		this.min = min;
		this.max = max;
	}

	Material getMaterial() {
		return material;
	}

	int getRandomAmount() {
		return Random.getRandomInteger(min, max);
	}

}
