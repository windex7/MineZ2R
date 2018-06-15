package command;

import java.io.File;

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
import util.NBTUtil;

public class Dev implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Plugin plugin = MineZ2R.getInstance();
		Player player = (Player)sender;
		String playeruuid = player.getUniqueId().toString();
		File playerconfig = new File(plugin.getDataFolder(), playeruuid + ".yml");
		FileConfiguration playerdata = YamlConfiguration.loadConfiguration(playerconfig);
		ItemStack[] invcontents = player.getInventory().getContents();
		FileConfiguration config = plugin.getConfig();
		File devfile = new File(plugin.getDataFolder(), "devfile.yml");
		FileConfiguration devconfig = YamlConfiguration.loadConfiguration(devfile);
		if (args.length >= 1) {
			switch (args[0]) {
			case "invsave":
				try {
					if (args.length <= 1) return false;
					if (args.length >= 3) {
						// --specify filepath. args[1]:filename, args[2]:path--
						File targetfile = new File(plugin.getDataFolder(), args[1] + ".yml");
						FileConfiguration targetconfig = YamlConfiguration.loadConfiguration(targetfile);
						targetconfig.set(args[2].toString(), Base64Util.itemToStringList(invcontents));
						targetconfig.save(targetfile);
						return true;
					}
					// --save inv to devfile.yml\inv.arg[1]--
					devconfig.set("inv." + args[1], Base64Util.itemToStringList(invcontents));
					devconfig.save(devfile);
					return true;
				} catch (Exception e) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not save to the target file.");
				}
				break;
			case "invload":
				try {
					if (args.length <= 1) return false;
					if (args.length >= 3) {
						// --specify filepath. args[1]:filename, args[2]:path--
						player.getInventory().setContents(Base64Util.itemFromStringList(YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), args[1] + ".yml")).getString(args[2].toString())));
						return true;
					}
					// --load inv from devfile.yml\inv.arg[1]--
					player.getInventory().setContents(Base64Util.itemFromStringList(devconfig.getString("inv." + args[1])));
					return true;
				} catch (Exception e) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not load from the target file.");
				}
				break;
			case "readnbtstring":
				if (args.length <= 1) return false;
				player.sendMessage("key: " + args[1] + " , value: " + NBTUtil.readItemStringTag(player.getInventory().getItemInMainHand(), args[1]));
				return true;
			case "writenbtstring":
				if (args.length <= 2) return false;
				player.getInventory().setItemInMainHand(NBTUtil.writeItemStringTag(player.getInventory().getItemInMainHand(), args[1], args[2]));
				player.sendMessage("successfully wrote nbt to the item in your main hand! key: " + args[1] + " , value: " + args[2]);
				return true;
			case "writenbtint":
				if (args.length <= 2) return false;
				player.getInventory().setItemInMainHand(NBTUtil.writeItemIntTag(player.getInventory().getItemInMainHand(), args[1], Integer.parseInt(args[2])));
				player.sendMessage("successfully wrote nbt to the item in your main hand! key: " + args[1] + " , value: " + args[2]);
				return true;
			default:
				return false;
			}
	}
	return false;
	}
}
