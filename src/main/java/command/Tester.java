package command;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import custommob.Forsaken;
import custommob.Pigman;
import custommob.ShinyToe;
import main.MineZ2R;
import util.EntityRegistry;

public class Tester implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;
		Plugin plugin = MineZ2R.getInstance();
		Player player = (Player)sender;
		String playeruuid = player.getUniqueId().toString();
		File playerconfig = new File(plugin.getDataFolder(), playeruuid + ".yml");
		FileConfiguration playerdata = YamlConfiguration.loadConfiguration(playerconfig);
		// EntityRegistry.spawnEntity(new GeneralZombie(player.getWorld()), player.getLocation());
		EntityRegistry.spawnEntity(new ShinyToe(player.getWorld()), player.getLocation());
		EntityRegistry.spawnEntity(new Pigman(player.getWorld()), player.getLocation());
		EntityRegistry.spawnEntity(new Forsaken(player.getWorld()), player.getLocation());
		return true;
	}
}
