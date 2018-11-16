package customrecipe.repair;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import util.StatsUtil;
import util.StatsUtil.Stat;

public abstract class RepairRecipe {
	abstract ItemStack onRepair(ItemStack[] is, Player player);

	public static double getRepairOffset(Player player) {
		//double defpercent = 0.98;
		double percent = StatsUtil.getProf(player, Stat.repair);
		return percent;
	}
}
