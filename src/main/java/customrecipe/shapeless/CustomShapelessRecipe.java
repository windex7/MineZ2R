package customrecipe.shapeless;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

import listener.Recipes;
import main.MineZ2R;

public class CustomShapelessRecipe {
	ArrayList<Pair<ItemStack, Integer>> items = new ArrayList<Pair<ItemStack, Integer>>();
	ShapelessRecipe registerer = new ShapelessRecipe(new ItemStack(Material.ANVIL));
	ItemStack result;

	public CustomShapelessRecipe(ItemStack itemstack) {
		result = itemstack;
	}

	public ArrayList<String> getId() {
		ArrayList<String> array = new ArrayList<String>();
		for (Pair<ItemStack, Integer> pair : items) {
			array.add(pair.getLeft().getType().toString() + pair.getLeft().getItemMeta().getDisplayName() + pair.getRight());
		}
		return array;
	}

	public void addIngredient(ItemStack itemstack) {
		boolean contains = false;
		int pairs = -1;

		for (Pair<ItemStack, Integer> pair : items) {
			if (pair.getLeft().equals(itemstack)) {
				contains = true;
				pairs = items.indexOf(pair);
			}
		}

		if (contains) {
			registerer.addIngredient(itemstack.getData());
			items.get(pairs).setValue(items.get(pairs).getRight() + 1);
		} else {
			registerer.addIngredient(itemstack.getData());
			items.add(Pair.of(itemstack, 1));
		}
	}

	public void addIngredient(ItemStack itemstack, int i) {
		for (int n = 0; n < i; n++) {
			addIngredient(itemstack);
		}
	}

	public void register() {
		Plugin plugin = MineZ2R.getInstance();
		plugin.getServer().addRecipe(registerer);
		new Recipes().addShapeless(this);
	}

	public ItemStack getResult() {
		return this.result;
	}

	public boolean match(CustomShapelessRecipe recipe) {
		Set<String> set1 = new HashSet<String>();
		set1.addAll(recipe.getId());
		Set<String> set2 = new HashSet<String>();
		set2.addAll(getId());
		return set1.equals(set2);
	}
}
