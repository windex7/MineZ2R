package customrecipe.repair;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import util.StatsUtil;
import util.StatsUtil.Stat;

public class RepairRecipe {
	/*
	private static Multimap<Material, Material, Integer> repairables = HashMultimap.create() {
		{
			put(Material.WOOD_SWORD, Material.WOOD);
			put(Material.WOOD_AXE, Material.WOOD);
			put(Material.WOOD_SPADE, Material.WOOD);
			put(Material.WOOD_HOE, Material.WOOD);

			put(Material.STONE_SWORD, Material.STONE);
		}
	};
	*/

	public static ItemStack onRepair(ItemStack[] is, Player player) {
		return is[0];
	}


	public static double getRepairMultiplier(Player player) {
		//double defpercent = 0.98;
		/*
		double[] percent = {0.98, 1.0, 1.02, 1.04, 1.06, 1.08, 1.1};
		int proflv = StatsUtil.getProf(player, Stat.repair);

		return percent[proflv];
		*/

		return StatsUtil.getProfCorrectionValue(player, Stat.repair);
	}
}