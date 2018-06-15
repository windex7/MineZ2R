package util;

import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

import main.MineZ2R;

public class RecipeUtil {
	public static void removeVanillaRecipe() {
		Plugin plugin = MineZ2R.getInstance();
		Iterator<Recipe> iterator = plugin.getServer().recipeIterator();
		Recipe recipe;
		while(iterator.hasNext()) {
			recipe = iterator.next();
			if (recipe != null && !(NBTUtil.readItemStringTag(recipe.getResult(), "craftable") != null)) {
				iterator.remove();
			}
		}
	}

	public static void registerShapedRecipe(ItemStack resultitem, ItemStack[] ingredients) {
		if (!(resultitem != null && resultitem.getType() != Material.AIR && ingredients.length >= 9)) return;
		ShapedRecipe recipe = new ShapedRecipe(resultitem);
		for (int i = 0; i <= 9; i++) {

		}

	}
}
