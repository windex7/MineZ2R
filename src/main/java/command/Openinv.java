package command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import main.MineZ2R;

public class Openinv implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		Plugin plugin = MineZ2R.getInstance();
		Player player = (Player)sender;
		if (args.length == 0) return false;
		if (args.length == 1) {
			Player invplayer = plugin.getServer().getPlayer(args[0]);
			Inventory inv = invplayer.getInventory();
			player.openInventory(inv);
			return true;
		}
		return false;
	}
}
