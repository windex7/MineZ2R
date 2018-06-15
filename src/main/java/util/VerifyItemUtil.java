package util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class VerifyItemUtil {
	public static ItemStack[] checkItemStack(ItemStack[] itemstack, String key) {
		ItemStack[] invitems = new ItemStack[itemstack.length];
		int index = 0;
		for (ItemStack items: itemstack) {
			if (items != null && items.getType() != Material.AIR) {
				switch (key) {
				case "temporary":
					if (NBTUtil.readItemStringTag(items, key) != null) {
						invitems[index] = null;
					}
					else {
						invitems[index] = items;
					}
					break;
				case "craftable":
					if (NBTUtil.readItemStringTag(items, key) != null) {
						invitems[index] = NBTUtil.writeItemStringTag(items, key, null);
						//WIP. MUST add crafting exp +(is.getAmount())
						Bukkit.getConsoleSender().sendMessage("processed " + items.getAmount() + " crafted items.");
					}
					else {
						invitems[index] = items;
					}
					break;
				default:
					invitems[index] = items;
					break;
				}
			}
			else {
				invitems[index] = null;
			}
		index++;
		}
		return invitems;
	}
}
