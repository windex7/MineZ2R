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
		if (locblock.getY() <= 0) return false;
		if (isAir(locabove) && (isAir(locabove2) || isAir(locblock))) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isInMidair(Location loc) {
		Block locblock = loc.getBlock();
		Block locbelow = locblock.getRelative(0, -1, 0);
		if (locblock.getY() <= 0) return false;
		if (isAir(locblock) && isAir(locbelow)) {
			return true;
		}
		return false;
	}

	public static boolean isAir(Block block) {
		if (block != null) {
			if (block.getType() == Material.AIR) return true;
			else return false;
		} else return true;
	}

	public static boolean isOutOfWorld(Location loc) {
		if (loc.getBlockY() <= 0 || loc.getBlockY() > 256) {
			return true;
		} else {
			return false;
		}
	}

	public static Location toSurface(Location loc) {
		/*
		if (isStuck(loc)) {
			return toSurface(loc.clone().add(0, 1, 0));
		} else if (isInMidair(loc)) {
			return toSurface(loc.clone().add(0, -1, 0));
		} else if (isOutOfWorld(loc)) {
			Location locclone = loc.clone();
			locclone.setY(256);
			return toSurface(locclone);
		} else {
			return loc;
		}
		*/
		return toSurface(loc, 0);
	}

	private static int retrylimit = 256;

	public static Location toSurface(Location loc, int retrynum) {
		if (retrynum > retrylimit) return loc;
		if (isStuck(loc)) {
			return toSurface(loc.clone().add(0, 1, 0), retrynum++);
		} else if (isInMidair(loc)) {
			return toSurface(loc.clone().add(0, -1, 0), retrynum++);
		} else if (isOutOfWorld(loc)) {
			Location locclone = loc.clone();
			locclone.setY(256);
			return toSurface(locclone, retrynum++);
		} else {
			return loc;
		}
	}
}
