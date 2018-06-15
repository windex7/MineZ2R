package util;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_9_R2.EntityArrow;
import net.minecraft.server.v1_9_R2.EntityItem;
import net.minecraft.server.v1_9_R2.NBTTagCompound;

public class NBTUtil {
	public static String readItemStringTag(ItemStack item, String key) {
		if (!(item != null) || item.getType() == Material.AIR) return null;
		net.minecraft.server.v1_9_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound nbttag = nmsItem.getTag();
		if (nbttag != null) {
			if (nbttag.hasKey(key)) {
			String value = nbttag.getString(key);
			return value;
			}
			return null;
		}
		return null;
	}

	public static ItemStack writeItemStringTag(ItemStack item, String key, String value) {
		if (!(item != null) || item.getType() == Material.AIR) return item;
		net.minecraft.server.v1_9_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound nbttag = nmsItem.getTag();
		if (value != null && !(value.equals("null"))) {
			if (nbttag != null) {
				nbttag.setString(key, value);
				nmsItem.setTag(nbttag);
			}
			else {
				NBTTagCompound newnbttag = new NBTTagCompound();
				newnbttag.setString(key, value);
				nmsItem.setTag(newnbttag);
			}
			ItemStack nbtitem = CraftItemStack.asBukkitCopy(nmsItem);
			return nbtitem;
		}
		else {
			// called when value is null
			return removeItemStringTag(item, key);
		}
	}

	public static ItemStack removeItemStringTag(ItemStack item, String key) {
		if (!(item != null) || item.getType() == Material.AIR) return item;
		net.minecraft.server.v1_9_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound nbttag = nmsItem.getTag();
		if (nbttag != null) {
			nbttag.remove(key);
			nmsItem.setTag(nbttag);
		}
		ItemStack nbtitem = CraftItemStack.asBukkitCopy(nmsItem);
		return nbtitem;
	}

	public static ItemStack writeItemIntTag(ItemStack item, String key, int value) {
		if (!(item != null) || item.getType() == Material.AIR) return item;
		net.minecraft.server.v1_9_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound nbttag = nmsItem.getTag();
		if (value > 0) {
			if (nbttag != null) {
				nbttag.setInt(key, value);
				nmsItem.setTag(nbttag);
			}
			else {
				NBTTagCompound newnbttag = new NBTTagCompound();
				newnbttag.setInt(key, value);
				nmsItem.setTag(newnbttag);
			}
			ItemStack nbtitem = CraftItemStack.asBukkitCopy(nmsItem);
			return nbtitem;
		}
		else {
			// called when value is minus
			//return removeItemStringTag(item, key);
			return item;
		}
	}

	public static void writeArrowIntTag(Entity entity, String key, int value) {
		net.minecraft.server.v1_9_R2.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		NBTTagCompound nbttag = new NBTTagCompound();
		nmsEntity.c(nbttag);
		nbttag.setInt(key, value);
		EntityArrow nbtentity = (EntityArrow) nmsEntity;
		nbtentity.a(nbttag);
	}

	public static void writeItemEntityIntTag(Entity entity, String key, int value) {
		net.minecraft.server.v1_9_R2.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		NBTTagCompound nbttag = new NBTTagCompound();
		nmsEntity.c(nbttag);
		nbttag.setInt(key, value);
		EntityItem nbtentity = (EntityItem) nmsEntity;
		nbtentity.a(nbttag);
	}
}
