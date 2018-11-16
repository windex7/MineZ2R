package util;

import org.bukkit.entity.Player;

public class StatsUtil {
	private static String pathToStat(Stat stat) {
		return "stat." + stat.name();
	}

	private static String pathToProf(Stat stat) {
		return "prof." + stat.name();
	}

	public static Object getStat(Player player, Stat type) {
		Object stats = ConfigUtil.getPlayerConfig(player, pathToStat(type));
		return stats;
	}

	public static void setStat(Player player, Stat type, Object value) {
		ConfigUtil.setPlayerConfig(player, pathToStat(type), value);
		return;
	}

	public static double getProf(Player player, Stat type) {
		Object prof = ConfigUtil.getPlayerConfig(player, pathToProf(type));
		return (double) prof;
	}

	public static void setProf(Player player, Stat type, double value) {
		ConfigUtil.setPlayerConfig(player, pathToProf(type), value);
		return;
	}

	private static double getProfLevel(double stats, Stat stattype) {
		return 0;
	}

	public static enum Stat {
		wooden_sword,
		stone_sword,
		iron_sword,
		diamond_sword,
		wooden_axe,
		stone_axe,
		iron_axe,
		diamond_axe,
		wooden_spade,
		stone_spade,
		iron_spade,
		diamond_spade,
		shear,
		water,
		weapon,
		armor,
		repair,
		general_zombie,
		giant
	};

	/*
	private static boolean isValidStat(String name) {
		for (Stat stat : Stat.values()) {
			if (stat.name().equals(name)) return true;
		}
		return false;
	}
	*/
}