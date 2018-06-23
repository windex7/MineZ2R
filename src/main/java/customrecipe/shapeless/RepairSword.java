package customrecipe.shapeless;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RepairSword {
	public RepairSword() {
		CustomShapelessRecipe r = new CustomShapelessRecipe(new ItemStack(Material.WOOD_SWORD));
		r.addIngredient(new ItemStack(Material.WOOD));
		r.addIngredient(new ItemStack(Material.WOOD_SWORD));
		r.register();
	}
}
