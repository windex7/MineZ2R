package util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class StuckUtil {
	public static boolean isStuck(Location loc) {
		Block locblock = loc.getBlock();
		Block locabove = locblock.getRelative(0, 1, 0);
		Block locabove2 = locblock.getRelative(0, 2, 0);
		//Block locbelow = locblock.getRelative(0, -1, 0);
		if (isAir(locabove) && (isAir(locabove2) || isAir(locblock))) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isAir(Block block) {
		if (block != null) {
			if (block.getType() == Material.AIR) return true;
			else return false;
		} else return true;
	}
}
