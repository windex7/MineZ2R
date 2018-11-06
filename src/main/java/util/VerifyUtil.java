package util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class VerifyUtil {
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

	private static String istypekey = "customitem";

	public static ItemStack setItemClass(ItemStack is, String tag) {
		ItemStack result = NBTUtil.writeItemStringTag(is, istypekey, tag);
		return result;
	}

	public static <T> Class<T> getItemClass(ItemStack is) {
		String tag = NBTUtil.readItemStringTag(is, istypekey);
		return getClassFromStr(tag);
	}

	public static void setEntityClass(Entity en, String tag) {
		NBTUtil.writeEntityStringTag(en, istypekey, tag);
		return;
	}

	public static <T> Class<T> getEntityClass(Entity en) {
		String tag = NBTUtil.readEntityStringTag(en, istypekey);
		return getClassFromStr(tag);
	}

	public static <T> Class<T> getClassFromStr(String tag) {
		if (tag != null && !tag.isEmpty()) {
			try {
				return ReflectionUtil.getItemClass(tag);
			} catch (ClassNotFoundException e) {
				return null;
			}
		} else {
			return null;
		}
	}
}
