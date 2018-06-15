package command;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
import util.VerifyItemUtil;

public class Login extends YamlConfiguration implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Plugin plugin = MineZ2R.getInstance();
		Player player = (Player)sender;
		String playeruuid = player.getUniqueId().toString();
		File playerconfig = new File(plugin.getDataFolder(), playeruuid + ".yml");
		FileConfiguration playerdata = YamlConfiguration.loadConfiguration(playerconfig);
		FileConfiguration config = plugin.getConfig();
		if (!playerconfig.exists()) {
			// --the player joins mz2 for the first time--
			try {
				playerconfig.getParentFile().mkdirs();
				playerconfig.createNewFile();
				playerdata.createSection("spawnkit");
				playerdata.set("spawnkit", plugin.getConfig().getString("inv.defaultspawnkit"));
				playerdata.save(playerconfig);
				player.getInventory().clear();
				player.updateInventory();
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not save " + playeruuid + ".yml !");
			}
		}
		if (playerdata.getString("inventory") != null) {
			ItemStack[] items = Base64Util.itemFromStringList(playerdata.getString("inventory"));
			ItemStack[] permitems = VerifyItemUtil.checkItemStack(items, "temporary");
			ItemStack[] invitems = VerifyItemUtil.checkItemStack(permitems, "craftable");
			player.getInventory().setContents(invitems);
		}
		else {
			// --spawn with "spawnkit"--
			// WIP: "package" system for spawnkit
			player.getInventory().setContents(Base64Util.itemFromStringList(playerdata.getString("spawnkit")));
		}
		if (playerdata.getString("coord") != null) {
			// --tp player to the place logged out the last time--
			player.teleport((Location)playerdata.get("coord"));
		}
		else {
			// --tp player to spawn(if he/she is premium, open selection gui)--
			player.teleport((Location)config.get("coord"));
		}
		return true;
	}
}
