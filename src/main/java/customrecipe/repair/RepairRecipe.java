package customrecipe.repair;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import util.StatsUtil;
import util.StatsUtil.Stat;

public abstract class RepairRecipe {
	abstract ItemStack onRepair(ItemStack[] is, Player player);

	abstract int getIngredientAmount();

	public static double getRepairMultiplier(Player player) {
		//double defpercent = 0.98;
		double[] percent = {0.98, 1.0, 1.02, 1.04, 1.06, 1.08, 1.1};
		int proflv = StatsUtil.getProf(player, Stat.repair);

		return percent[proflv];
	}
}