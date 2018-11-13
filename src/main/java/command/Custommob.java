package command;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import main.MineZ2R;
import util.SpawnMobUtil;

public class Custommob implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		Plugin plugin = MineZ2R.getInstance();
		Player player = (Player)sender;
		if (args.length == 0) return false;
		if (args.length == 1) {
			/*if (ReflectionUtil.getMobSet().contains(args[0])) {
				try {
					EntityRegistry.spawnEntity((Entity)(ReflectionUtil.getMobConstructor(args[0], World.class).newInstance(player.getWorld())), player.getLocation());
					return true;
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
					return false;
				}
			}*/
			 return SpawnMobUtil.spawnCustomMob(args[0], player.getLocation());
		}
		if (args.length == 4) {
			World world = null;
			if (sender instanceof BlockCommandSender) world = ((BlockCommandSender) sender).getBlock().getWorld();
			if (sender instanceof Player) world = ((Player) sender).getWorld();
			if (world == null) return false;
			return SpawnMobUtil.spawnCustomMob(args[0], new Location(world ,Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3])));
		}
		return false;
	}
}