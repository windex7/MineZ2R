package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

public class StatsUtil {
	public static enum Stat {
		tools, // for proflv method - always 0
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
		mobs, // for proflv method - always 0
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

	private static Map<Stat, Double[]> multiplier = new HashMap<Stat, Double[]>() {
		{
			put(Stat.tools, new Double[] {-0.2, 0.0, 0.2, 0.4, 0.6, 0.8, 1.0});
			put(Stat.shear, new Double[] {-50d, 0d, 50d, 100d, 150d, 200d, 250d}); // tick
			put(Stat.water, new Double[] {1.2, 1.0, 0.8, 0.6, 0.4, 0.2, 0.0}); // possibility to be infected
			put(Stat.weapon, new Double[] {-0.2, 0.0, 0.2, 0.4, 0.6, 0.8, 1.0});
			put(Stat.armor, new Double[] {-0.2, 0.0, 0.2, 0.4, 0.6, 0.8, 1.0}); // armor point
			put(Stat.repair, new Double[] {0.9, 1.0, 1.1, 1.2, 1.3, 1.4, 1.5});
			put(Stat.mobs, new Double[] {-0.2, 0.0, 0.2, 0.4, 0.6, 0.8, 1.0});
		}
	};

	private static List<Stat> tools = new ArrayList<Stat>() {
		{
			Stat[] allstattool = new Stat[] {
					Stat.wooden_sword,
					Stat.stone_sword,
					Stat.iron_sword,
					Stat.diamond_sword,
					Stat.wooden_axe,
					Stat.stone_axe,
					Stat.iron_axe,
					Stat.diamond_axe,
					Stat.wooden_spade,
					Stat.stone_spade,
					Stat.iron_spade,
					Stat.diamond_spade};
			for (Stat stat : allstattool) {
				add(stat);
			}
		}
	};

	private static List<Stat> mobs = new ArrayList<Stat>() {
		{
			Stat[] allstatmob = new Stat[] {
					Stat.general_zombie, // mobs
					Stat.shinytoe,
					Stat.ironzombie,
					Stat.revenant,
					Stat.revenant_servant,
					Stat.forsaken,
					Stat.legchopper,
					Stat.pigman,
					Stat.babypigman,
					Stat.giant
			};
			for (Stat stat : allstatmob) {
				add(stat);
			}
		}
	};

	private static String pathToStat(Stat stat) {
		return "stat." + stat.name();
	}

	private static String pathToProf(Stat stat) {
		return "prof." + stat.name();
	}

	public static Object getStat(Player player, Stat type) {
		Object stats = ConfigUtil.getPlayerConfig(player, pathToStat(type));
		if (stats != null) return stats;
		return 0;
	}

	public static void setStat(Player player, Stat type, double value) {
		ConfigUtil.setPlayerConfig(player, pathToStat(type), value);
		int proflv = getProfLevel(value, type);
		setProf(player, type, proflv);
		return;
	}

	public static int getProf(Player player, Stat type) {
		Object prof = ConfigUtil.getPlayerConfig(player, pathToProf(type));
		if (prof != null) return (Integer) prof;
		return 0;
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
		if (stats < lv1) return 0; // untrained
		else if (stats < lv2) return 1; // novice
		else if (stats < lv3) return 2; // competent
		else if (stats < lv4) return 3; // advanced
		else if (stats < lv5) return 4; // proficient
		else if (stats < lv6) return 5; // legendary
		else return 6;
	}

	public static double getProfMultiplier(Stat stattype, int proflv) {
		if (multiplier.containsKey(stattype)) {
			return multiplier.get(stattype)[proflv];
		} else if (tools.contains(stattype)) {
			return multiplier.get(Stat.tools)[proflv];
		} else if (mobs.contains(stattype)) {
			return multiplier.get(Stat.mobs)[proflv];
		} else {
			return 0;
		}
	}

	public static double getProfCorrectionValue(Player player, Stat stattype) {
		int proflv = getProf(player, stattype);
		return getProfMultiplier(stattype, proflv);
	}

	/*
	private static boolean isValidStat(String name) {
		for (Stat stat : Stat.values()) {
			if (stat.name().equals(name)) return true;
		}
		return false;
	}
	*/
}