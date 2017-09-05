package xyz.derkades.skywars.loot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import xyz.derkades.derkutils.Random;
import xyz.derkades.derkutils.bukkit.ItemBuilder;
import xyz.derkades.skywars.Skywars;

public class LootChest {
	
	private LootLevel level;
	private Location location;

	public LootChest(LootLevel level, int x, int y, int z) {
		this.level = level;
		this.location = new Location(Skywars.world, x, y, z);
	}

	public LootLevel getLevel() {
		return level;
	}

	public Location getLocation() {
		return location;
	}

	public void fill() {
		Block block = location.getBlock();
		
		if (block.getType() != Material.CHEST) {
			Skywars.plugin.getLogger().warning(String.format(
					"Block at %s, %s, %s should be a chest but is not. Has the block been destroyed? Are coordinates wrong?",
					location.getX(), location.getY(), location.getZ()));
			return;
		}
				
		Chest chest = (Chest) block.getState();
		
		LootItem[] loot = level.items;
		List<ItemStack> items = new ArrayList<ItemStack>();
		for (LootItem item : loot)
			items.add(new ItemBuilder(item.getMaterial()).data(item.getData()).amount(item.getRandomAmount()).create());
		List<ItemStack> contents = Arrays.asList(chest.getInventory().getContents());

		for (ItemStack item : items) {
			int slot = Random.getRandomInteger(0, 27);
			contents.set(slot, item);
		}

		chest.getInventory().setContents((ItemStack[]) contents.toArray());
	}

}
