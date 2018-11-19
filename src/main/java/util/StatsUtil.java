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

	public static void setStat(Player player, Stat type, double value) {
		ConfigUtil.setPlayerConfig(player, pathToStat(type), value);
		int proflv = getProfLevel(value, type);
		setProf(player, type, proflv);
		return;
	}

	public static int getProf(Player player, Stat type) {
		Object prof = ConfigUtil.getPlayerConfig(player, pathToProf(type));
		return (Integer) prof;
	}

	private static void setProf(Player player, Stat type, int level) {
		ConfigUtil.setPlayerConfig(player, pathToProf(type), level);
		return;
	}

	private static int getProfLevel(double stats, Stat stattype) {
		double lv1 = 10; // +0.0
		double lv2 = 60;
		double lv3 = 260;
		double lv4 = 1260;
		double lv5 = 5260;
		double lv6 = 25260; // +1.0
		if (stats < lv1) return 0;
		else if (stats < lv2) return 1;
		else if (stats < lv3) return 2;
		else if (stats < lv4) return 3;
		else if (stats < lv5) return 4;
		else if (stats < lv6) return 5;
		else return 6;
	}

	public static enum Stat {
		wooden_sword, // tools
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
		shear, // heal
		water,
		weapon, // crafting tools
		armor,
		repair,
		general_zombie, // mobs
		shinytoe,
		ironzombie,
		revenant,
		revenant_servant,
		forsaken,
		legchopper,
		pigman,
		babypigman,
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