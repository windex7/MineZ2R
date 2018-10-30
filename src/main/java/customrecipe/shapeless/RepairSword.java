package customrecipe.shapeless;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RepairSword {
	public RepairSword() {
		short maxdura = Material.WOOD_SWORD.getMaxDurability();
		short repairdura = 10;
		for (short i = 0; i < maxdura; i++) {
			ItemStack ingredient = new ItemStack(Material.WOOD_SWORD);
			ingredient.setDurability(i);
			short newdura = 0;
			if (i > repairdura) newdura = (short)(i - repairdura);
			ItemStack result = new ItemStack(Material.WOOD_SWORD);
			result.setDurability(newdura);
			CustomShapelessRecipe r = new CustomShapelessRecipe(result);
			r.addIngredient(new ItemStack(Material.WOOD));
			r.addIngredient(ingredient);
			r.register();
		}
	}
}
