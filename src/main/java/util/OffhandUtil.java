package util;

import java.util.Objects;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import main.MineZ2R;

public class OffhandUtil {
	static String offhandtag = "offhand";

	public static boolean isAllowedOffhand(ItemStack item) {
		if (Objects.equals(NBTUtil.readItemStringTag(item, offhandtag), "true")) {
			return true;
		} else {
			return false;
		}
	}

	public static void checkAllOffhand() {
		for (World world : MineZ2R.getInstance().getServer().getWorlds()) {
			for (Player player : world.getPlayers()) {
				checkOffhand(player);
			}
		}
	}

	public static void checkOffhand(Player player) {
		if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
		if (player.getInventory().getItemInOffHand().getType() != Material.AIR) {
			ItemStack offhanditem = player.getInventory().getItemInOffHand();
			if (!isAllowedOffhand(offhanditem)) {
				EntityUtil.dropItem(player, offhanditem);
				player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
			}
		}
	}
}
