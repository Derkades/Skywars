package xyz.derkades.skywars.loot;

import org.bukkit.Material;

import xyz.derkades.derkutils.Random;

public class LootItem {
	
	private Material material;
	private int data;
	private int min;
	private int max;
	private float chance;

	LootItem(Material material, int data, int min, int max, float chance) {
		this.material = material;
		this.data = data;
		this.min = min;
		this.max = max;
		this.chance = chance;
	}
	
	LootItem(Material material, int min, int max, float chance) {
		this.material = material;
		this.min = min;
		this.max = max;
		this.chance = chance;
	}

	Material getMaterial() {
		return material;
	}
	
	int getData() {
		return data;
	}

	int getRandomAmount() {
		if (Random.getRandomDouble() <= chance) {
			return Random.getRandomInteger(min, max);
		} else {
			return 0;
		}
	}

}
