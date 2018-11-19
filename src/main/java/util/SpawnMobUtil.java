package util;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.World;

import net.minecraft.server.v1_9_R2.Entity;

public class SpawnMobUtil {
	public static boolean spawnCustomMob(String mobName, Location loc) {
		if (ReflectionUtil.getMobSet().contains(mobName)) {
			try {
				EntityRegistry.spawnEntity((Entity)(ReflectionUtil.getMobConstructor(mobName, World.class).newInstance(loc.getWorld())), loc);
				return true;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean spawnCustomInvulMob(String mobName, Location loc, int invultick) {
		if (ReflectionUtil.getMobSet().contains(mobName)) {
			try {
				EntityRegistry.spawnInvulEntity((Entity)(ReflectionUtil.getMobConstructor(mobName, World.class).newInstance(loc.getWorld())), loc, invultick);
				return true;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}
}
