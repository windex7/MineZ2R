package command;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import main.MineZ2R;
import util.Base64Util;

public class Logout implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Plugin plugin = MineZ2R.getInstance();
		Player player = (Player)sender;
		String playeruuid = player.getUniqueId().toString();
		File playerconfig = new File(plugin.getDataFolder(), playeruuid + ".yml");
		FileConfiguration playerdata = YamlConfiguration.loadConfiguration(playerconfig);
		ItemStack[] invcontents = player.getInventory().getContents();
		playerdata.set("inventory", Base64Util.itemToStringList(invcontents));
		playerdata.set("coord", player.getLocation());
		try {
			playerdata.save(playerconfig);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not save " + playerconfig.getName() + " !");
		}
		player.getInventory().clear();
		player.updateInventory();
		return true;
	}
}
