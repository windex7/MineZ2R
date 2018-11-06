package util;

import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NMSUtil {
	public static net.minecraft.server.v1_9_R2.ItemStack convIStoNMS(ItemStack is) {
		return CraftItemStack.asNMSCopy(is);
	}

	public static ItemStack convNMStoIS(net.minecraft.server.v1_9_R2.ItemStack is) {
		return CraftItemStack.asCraftMirror(is);
	}
}
