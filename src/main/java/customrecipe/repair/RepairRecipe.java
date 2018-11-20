package customrecipe.repair;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import util.StatsUtil;
import util.StatsUtil.Stat;

public class RepairRecipe {
	Material result;
	Material ingredient;
	double durability; // ratio
	boolean scrappable;
	int maxscrapnum;

	RepairRecipe(Material resultm, Material ingredm, Double dura, boolean isscrappable, int maxscrap) {
		this.result = resultm;
		this.ingredient = ingredm;
		this.durability = dura;
		this.scrappable = isscrappable;
		this.maxscrapnum = maxscrap;
	}

	private static List<RepairRecipe> repairables = new ArrayList<RepairRecipe>() {
		{
			// -- weapoms & tools --
			add(new RepairRecipe(Material.WOOD_SWORD, Material.WOOD, 0.6, true, 1));
			add(new RepairRecipe(Material.WOOD_AXE, Material.WOOD, 0.4, true, 2));
			add(new RepairRecipe(Material.WOOD_SPADE, Material.WOOD, 0.9, false, 0));
			add(new RepairRecipe(Material.WOOD_HOE, Material.WOOD, 0.7, true, 1));

			add(new RepairRecipe(Material.STONE_SWORD, Material.STONE, 0.6, true, 1));
			add(new RepairRecipe(Material.STONE_AXE, Material.STONE, 0.4, true, 2));
			add(new RepairRecipe(Material.STONE_SPADE, Material.STONE, 0.9, false, 0));
			add(new RepairRecipe(Material.STONE_HOE, Material.STONE, 0.7, true, 1));

			add(new RepairRecipe(Material.IRON_SWORD, Material.IRON_INGOT, 0.5, true, 1));
			add(new RepairRecipe(Material.IRON_AXE, Material.IRON_INGOT, 0.33, true, 2));
			add(new RepairRecipe(Material.IRON_SPADE, Material.IRON_INGOT, 0.7, false, 0));
			add(new RepairRecipe(Material.IRON_HOE, Material.IRON_INGOT, 0.6, true, 1));

			add(new RepairRecipe(Material.DIAMOND_SWORD, Material.DIAMOND, 0.4, true, 1));
			add(new RepairRecipe(Material.DIAMOND_AXE, Material.DIAMOND, 0.3, true, 2));
			add(new RepairRecipe(Material.DIAMOND_SPADE, Material.DIAMOND, 0.6, false, 0));
			add(new RepairRecipe(Material.DIAMOND_HOE, Material.DIAMOND, 0.5, true, 1));

			add(new RepairRecipe(Material.GOLD_SWORD, Material.GOLD_INGOT, 0.8, true, 1));
			add(new RepairRecipe(Material.GOLD_AXE, Material.GOLD_INGOT, 0.6, true, 2));
			add(new RepairRecipe(Material.GOLD_SPADE, Material.GOLD_INGOT, 0.95, false, 0));
			add(new RepairRecipe(Material.GOLD_HOE, Material.GOLD_INGOT, 0.9, true, 1));

			add(new RepairRecipe(Material.BOW, Material.STRING, 0.4, true, 2));
			add(new RepairRecipe(Material.FISHING_ROD, Material.STRING, 0.4, false, 0));
			add(new RepairRecipe(Material.SHEARS, Material.IRON_INGOT, 0.6, false, 0));

			// -- armors --
			add(new RepairRecipe(Material.LEATHER_HELMET, Material.LEATHER, 0.3, true, 4));
			add(new RepairRecipe(Material.LEATHER_CHESTPLATE, Material.LEATHER, 0.16, true, 7));
			add(new RepairRecipe(Material.LEATHER_LEGGINGS, Material.LEATHER, 0.2, true, 6));
			add(new RepairRecipe(Material.LEATHER_BOOTS, Material.LEATHER, 0.4, true, 3));

			add(new RepairRecipe(Material.CHAINMAIL_HELMET, Material.IRON_FENCE, 0.25, true, 4));
			add(new RepairRecipe(Material.CHAINMAIL_CHESTPLATE, Material.IRON_FENCE, 0.14, true, 7));
			add(new RepairRecipe(Material.CHAINMAIL_LEGGINGS, Material.IRON_FENCE, 0.17, true, 6));
			add(new RepairRecipe(Material.CHAINMAIL_BOOTS, Material.IRON_FENCE, 0.35, true, 3));

			add(new RepairRecipe(Material.IRON_HELMET, Material.IRON_INGOT, 0.5, true, 4));
			add(new RepairRecipe(Material.IRON_CHESTPLATE, Material.IRON_INGOT, 0.33, true, 7));
			add(new RepairRecipe(Material.IRON_LEGGINGS, Material.IRON_INGOT, 0.7, true, 6));
			add(new RepairRecipe(Material.IRON_BOOTS, Material.IRON_INGOT, 0.6, true, 3));

			add(new RepairRecipe(Material.GOLD_HELMET, Material.GOLD_INGOT, 0.5, true, 4));
			add(new RepairRecipe(Material.GOLD_CHESTPLATE, Material.GOLD_INGOT, 0.33, true, 7));
			add(new RepairRecipe(Material.GOLD_LEGGINGS, Material.GOLD_INGOT, 0.7, true, 6));
			add(new RepairRecipe(Material.GOLD_BOOTS, Material.GOLD_INGOT, 0.6, true, 3));
		}
	};

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