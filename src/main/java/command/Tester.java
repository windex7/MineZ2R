package command;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import main.MineZ2R;

public class Tester implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Plugin plugin = MineZ2R.getInstance();
		Player player = (Player)sender;
		String playeruuid = player.getUniqueId().toString();
		File playerconfig = new File(plugin.getDataFolder(), playeruuid + ".yml");
		FileConfiguration playerdata = YamlConfiguration.loadConfiguration(playerconfig);
		player.setAllowFlight(true);
		player.setFlying(true);
		player.setFlySpeed(0.02F);
		//for (int i = 0; i < 20 * 10; i++) {
		//	Bukkit.getScheduler().scheduleSyncDelayedTask(mz3, new Runnable() {
		//		@Override
		//		public void run() {
		//			player.setSprinting(false);
		//		}
		//	}, i);
		//}
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				player.setFlying(false);
				player.setFlySpeed(0.2F);
				player.setAllowFlight(false);
			}
		}, 20 * 10);
		return true;
	}
}
