package main;

import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import command.Blockplace;
import command.Dev;
import command.Login;
import command.Logout;
import command.Multigive;
import command.Openinv;
import command.Prof;
import command.Recipe;
import command.Stats;
import command.Tester;
import customrecipe.shapeless.CustomShapelessRecipe;
import customrecipe.shapeless.RepairSword;
import listener.PlayerLogin;
import listener.PlayerTeleport;
import listener.Recipes;
import net.minecraft.server.v1_9_R2.Item;
import util.NBTUtil;
import util.RecipeUtil;

public class MineZ2R extends JavaPlugin implements Listener{
	private static MineZ2R instance;
	public MineZ2R () {
		instance = this;
	}

	public static MineZ2R getInstance() {
		return instance;
	}

	@Override
	public void onDisable() {
		new Recipes().onDisable();
	}

	@Override
	public void onEnable() {

		// --check if datafolder and config.yml exist--
		try {
			if (!getDataFolder().exists()) {
				getLogger().info("datafolder is not found, creating...");
				getDataFolder().mkdirs();
			}
			if (!new File(getDataFolder(), "config.yml").exists()) {
				getLogger().info("config.yml is not found, creating...");
				saveDefaultConfig();
			}
			else {
				getLogger().info("config.yml is found, loading...");
			}
			File devfile = new File(getDataFolder(), "devfile.yml");
			if (!devfile.exists()) {
				getLogger().info("devfile.yml is not found, creating...");
				devfile.createNewFile();
			}
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Could not create config files!");
		}
		// --remove vanilla recipes--
		RecipeUtil.removeVanillaRecipe();

		// --add original recipes--
		ItemStack book = new ItemStack(Material.BOOK, 1);
		book = NBTUtil.writeItemStringTag(book, "craftable", "true");
		// ShapelessRecipe bookfree = new ShapelessRecipe(book);
		ShapedRecipe booktest = new ShapedRecipe(book);
		booktest.shape(" bc","bc ","c b");
		booktest.setIngredient('b', Material.BONE);
		booktest.setIngredient('c', Material.CHEST);
		getServer().addRecipe(booktest);


		try {
			Item enderpearl = Item.d("ender_pearl");
			Item cobweb = Item.d("web");
			Item slimeball = Item.d("slime_ball");
			Item skull = Item.d("skull");
			Field field;
			field = Item.class.getDeclaredField("maxStackSize");
			field.setAccessible(true);
			field.setInt(enderpearl, 1);
			field.setInt(cobweb, 1);
			field.setInt(slimeball, 1);
			field.setInt(skull, 1);
			field.setAccessible(false);
		} catch (IllegalArgumentException | IllegalAccessException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		catch (NoSuchFieldException | SecurityException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		new RepairSword();
		CustomShapelessRecipe rec1 = new CustomShapelessRecipe(new ItemStack(Material.GOLDEN_APPLE, 1, (short)1));
		rec1.addIngredient(new ItemStack(Material.APPLE));
		rec1.addIngredient(new ItemStack(Material.APPLE));
		rec1.register();




		// --register events and commands--
		Bukkit.getPluginManager().registerEvents(new PlayerTeleport(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerLogin(), this);
		Bukkit.getPluginManager().registerEvents(new Recipes(), this);
		getCommand("login").setExecutor(new Login());
		getCommand("logout").setExecutor(new Logout());
		getCommand("recipe").setExecutor(new Recipe());
		getCommand("stats").setExecutor(new Stats());
		getCommand("prof").setExecutor(new Prof());
		getCommand("tester").setExecutor(new Tester());
		getCommand("dev").setExecutor(new Dev());
		getCommand("blockplace").setExecutor(new Blockplace());
		getCommand("multigive").setExecutor(new Multigive());
		getCommand("openinv").setExecutor(new Openinv());

		// --enabled msg--
		getLogger().info("Successfully enabled MZ2R plugin.");
	}
}
