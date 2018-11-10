package util;

import org.bukkit.entity.Player;

public class StatsUtil {
	public static Object getStats(Player player, String type) {
		Object stats = ConfigUtil.getPlayerConfig(player, type);
		return stats;
	}

	public static void setStats(Player player, String type, Object value) {
		ConfigUtil.setPlayerConfig(player, type, value);
		return;
	}
}
