package util;

import java.util.Iterator;

import org.bukkit.inventory.Recipe;
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

	public static boolean registerRecipe(Recipe recipe) {
		Plugin plugin = MineZ2R.getInstance();
		return plugin.getServer().addRecipe(recipe);
	}
}
