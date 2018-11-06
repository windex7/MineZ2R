package util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {
	public static boolean removeIS(Player player, ItemStack is, int qty) {
		for (int i = 0; i < qty; i++) {
			// boolean result = removeOneIS(player, searchInv(player, is));
			boolean result = removeOneIS(player, is);
			if (!result) return false;
		}
		return true;
	}

	private static boolean removeOneIS(Player player, ItemStack is) {
		if (is == null) return false;
		if (is.getAmount() == 1) {
			player.getInventory().removeItem(is);
		} else {
			is.setAmount(is.getAmount() - 1);
		}
		player.updateInventory();
		return true;
	}

	public static ItemStack searchInv(Player player, ItemStack is) {
		Inventory inv = player.getInventory();
		for (ItemStack invis : inv.getContents()) {
			if (invis.getType() == is.getType()) {
				if (invis.getData() == is.getData()) {
					return invis;
				}
			}
		}
		return null;
	}
}
