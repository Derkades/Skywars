package xyz.derkades.skywars;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import xyz.derkades.derkutils.bukkit.Colors;
import xyz.derkades.derkutils.bukkit.IconMenu;
import xyz.derkades.derkutils.bukkit.ItemBuilder;

public class KitsMenu extends IconMenu {

	public KitsMenu(Player player) {
		super(Skywars.plugin, Message.KITS_MENU_TITLE.get(), 9, player);
		
		setSize(Skywars.plugin.getConfig().getInt("kits-rows") * 9);
		
		for (String key : Skywars.plugin.getConfig().getConfigurationSection("kits").getKeys(false)) {
			ConfigurationSection section = Skywars.plugin.getConfig().getConfigurationSection("kits." + key);
			
			int slot = Integer.parseInt(key);
			
			Material material = Material.valueOf(section.getString("item"));
			int data = section.getInt("data");
			String name = section.getString("name");
			List<String> lore = section.getStringList("lore");
			
			if (material == null) {
				material = Material.STONE;
			}
			
			items.put(slot, new ItemBuilder(material).data(data).coloredName(name).coloredLore(lore).create());
		}
	}

	@Override
	public boolean onOptionClick(OptionClickEvent event) {
		Player player = event.getPlayer();
		int position = event.getPosition();
		
		List<String> commands = Skywars.plugin.getConfig().getStringList("kits." + position + ".commands");
		String name = Skywars.plugin.getConfig().getString("kits." + position + ".name");
		
		for (String command : commands) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", player.getName()));
		}
		
		Message.KIT_SELECTED.get(Colors.parseColors(name));
		return true;
	}

}
