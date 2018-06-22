package listener;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import customrecipe.shapeless.CustomShapelessRecipe;

public class Recipes implements Listener {
	ArrayList<CustomShapelessRecipe> shapeless = new ArrayList<CustomShapelessRecipe>();

	public void addShapeless(CustomShapelessRecipe recipe) {
		for (CustomShapelessRecipe r : shapeless) {
			if (r.match(recipe)) return;
		}
		shapeless.add(recipe);
	}

	public void onDisable() {
		shapeless.clear();
	}

	@EventHandler
	public void onCraft(PrepareItemCraftEvent event) {
		CraftingInventory inv = event.getInventory();
		ItemStack[] items = inv.getContents();
		CustomShapelessRecipe r = new CustomShapelessRecipe(new ItemStack(Material.ANVIL));
		int anvil = 0;
		boolean anvils = false;
		for (ItemStack is : items) {
			if (!is.getType().equals(Material.AIR)) {
				if (is.equals(new ItemStack(Material.ANVIL)) && anvil == 0) {
					anvil = 1;
					continue;
				}
				if (anvil == 1) {
					anvils = true;
				}

				r.addIngredient(is);
			}
		}

		for (CustomShapelessRecipe recipe : shapeless) {
			if (recipe.match(r)) {
				event.getInventory().setResult(recipe.getResult());
				return;
			}
		}

		if (anvils) {
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}
	}
}
