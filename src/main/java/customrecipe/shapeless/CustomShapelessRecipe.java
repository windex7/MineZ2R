package customrecipe.shapeless;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

import listener.Recipes;
import main.MineZ2R;

public class CustomShapelessRecipe {
	Map<ItemStack, Integer> items = new HashMap<ItemStack, Integer>();
	ShapelessRecipe registerer = new ShapelessRecipe(new ItemStack(Material.ANVIL));
	ItemStack result;

	public CustomShapelessRecipe(ItemStack itemstack) {
		result = itemstack;
	}

	public void setResult(ItemStack itemstack) {
		result = itemstack;
	}

	public void setDurability(short durability) {
		result.setDurability(durability);
	}

	public ArrayList<String> getId() {
		ArrayList<String> array = new ArrayList<String>();
		for (Entry<ItemStack, Integer> entry : items.entrySet()) {
			array.add(entry.getKey().getType().toString() + entry.getKey().getItemMeta().getDisplayName() + entry.getValue());
		}
		return array;
	}

	public ArrayList<ItemStack> getIs() {
		ArrayList<ItemStack> array = new ArrayList<ItemStack>();
		for (Entry<ItemStack, Integer> entry : items.entrySet()) {
			array.add(entry.getKey());
		}
		return array;
	}

	public void addIngredient(ItemStack itemstack) {
		for (Entry<ItemStack, Integer> entry : items.entrySet()) {
			if (entry.getKey().equals(itemstack)) {
				registerer.addIngredient(itemstack.getData());
				entry.setValue(entry.getValue() + 1);
				return;
			}
		}
		registerer.addIngredient(itemstack.getData());
		items.put(itemstack, 1);
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
		/*
		Set<String> set1 = new HashSet<String>();
		set1.addAll(recipe.getId());
		Set<String> set2 = new HashSet<String>();
		set2.addAll(getId());
		return set1.equals(set2);
		*/
		return this.getIs().equals(recipe.getIs());
	}
}
