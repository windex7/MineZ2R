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
	private static String mobtypekey = "custommob";

	private static final String itemtype = "item";
	private static final String mobtype = "mob";

	public static ItemStack setItemClass(ItemStack is, String tag) {
		ItemStack result = NBTUtil.writeItemStringTag(is, istypekey, tag);
		return result;
	}

	public static <T> Class<T> getItemClass(ItemStack is) {
		String tag = NBTUtil.readItemStringTag(is, istypekey);
		return getClassFromStr(tag, itemtype);
	}

	public static void setEntityClass(Entity en, String tag) {
		MetadataUtil.setMetadata(en, istypekey, tag);
		return;
	}

	public static <T> Class<T> getEntityClass(Entity en) {
		String tag = (String) MetadataUtil.getMetadata(en, istypekey);
		return getClassFromStr(tag, itemtype);
	}

	public static void setMobClass(Entity en, String type) {
		MetadataUtil.setMetadata(en, mobtypekey, type);
		//Bukkit.broadcastMessage("set mob type: " + type);
		return;
	}

	public static <T> Class<T> getMobClass(Entity en) {
		String type = (String) MetadataUtil.getMetadata(en, mobtypekey);
		//Bukkit.broadcastMessage("get mob type: " + type);
		return getClassFromStr(type, mobtype);
	}

	public static <T> Class<T> getClassFromStr(String tag, String type) {
		if (tag != null && !tag.isEmpty()) {
			try {
				switch (type) {
				case itemtype:
					return ReflectionUtil.getItemClass(tag);
				case mobtype:
					return ReflectionUtil.getMobClass(tag);
				default:
					return null;
				}

			} catch (ClassNotFoundException e) {
				return null;
			}
		} else {
			return null;
		}
	}
}
