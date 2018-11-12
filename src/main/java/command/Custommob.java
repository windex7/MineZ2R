package command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import main.MineZ2R;

public class Custommob implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		Plugin plugin = MineZ2R.getInstance();
		Player player = (Player)sender;
		if (args.length == 0) return false;
		if (args.length == 1) {

			return true;
		}
		return false;
	}

}
