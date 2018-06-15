package command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipe implements CommandExecutor{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) return false;

		Inventory recipeInv = Bukkit.createInventory(null, 54, ChatColor.DARK_PURPLE + "Recipe Book");
		//ItemStack leftskull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		//SkullMeta leftskullMeta = (SkullMeta) leftskull.getItemMeta();
		//leftskullMeta.setOwner("MHF_ArrowLeft");
		//leftskullMeta.setDisplayName("Previous");
		//leftskull.setItemMeta(leftskullMeta);
		ItemStack prebook = new ItemStack(Material.BOOK, 1);
		ItemMeta prebookMeta = prebook.getItemMeta();
		prebookMeta.setDisplayName(ChatColor.GOLD + "Previous Recipe");
		prebook.setItemMeta(prebookMeta);
		ItemStack nexbook = new ItemStack(Material.BOOK, 1);
		ItemMeta nexbookMeta = prebook.getItemMeta();
		nexbookMeta.setDisplayName(ChatColor.GOLD + "Next Recipe");
		nexbook.setItemMeta(nexbookMeta);
		recipeInv.setItem(45, prebook);
		recipeInv.setItem(53, nexbook);
		recipeInv.setItem(10, new ItemStack(Material.DIAMOND, 1));
		recipeInv.setItem(11, new ItemStack(Material.DIAMOND, 1));
		recipeInv.setItem(12, new ItemStack(Material.DIAMOND, 1));
		recipeInv.setItem(20, new ItemStack(Material.STICK, 1));
		recipeInv.setItem(29, new ItemStack(Material.STICK, 1));
		recipeInv.setItem(22, new ItemStack(Material.WORKBENCH, 1));
		recipeInv.setItem(23, new ItemStack(Material.DIAMOND_AXE, 1));
		Player player = (Player)sender;
		player.openInventory(recipeInv);
		return true;
	}
}
