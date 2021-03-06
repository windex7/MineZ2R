package main;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import command.Blockplace;
import command.Custommob;
import command.Dev;
import command.Login;
import command.Logout;
import command.Multigive;
import command.Openinv;
import command.Prof;
import command.Recipe;
import command.Stats;
import command.Tester;
import customitem.ImpactGrenade;
import custommob.BabyPigman;
import custommob.Forsaken;
import custommob.GeneralZombie;
import custommob.Giant;
import custommob.IronZombie;
import custommob.LegChopper;
import custommob.MineZHorse;
import custommob.Pigman;
import custommob.Revenant;
import custommob.RevenantReborn;
import custommob.RevenantServant;
import custommob.ShinyToe;
import customrecipe.shapeless.CustomShapelessRecipe;
import customrecipe.shapeless.RepairSword;
import listener.EntityDamage;
import listener.PlayerInteract;
import listener.PlayerLogin;
import listener.PlayerTeleport;
import listener.ProjectileHit;
import listener.Recipes;
import listener.SwapOffhand;
import util.ConfigUtil;
import util.DamageUtil;
import util.NBTUtil;
import util.OffhandUtil;
import util.RecipeUtil;
import util.ReflectionUtil;
import util.StacksizeUtil;
import util.TimingUtil;

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
		/*try {
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
		}*/
		ConfigUtil.initConfig();

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


		/*try {
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
		}*/
		StacksizeUtil.initialStackSize();

		new RepairSword();
		CustomShapelessRecipe rec1 = new CustomShapelessRecipe(new ItemStack(Material.GOLDEN_APPLE, 1, (short)1));
		rec1.addIngredient(new ItemStack(Material.APPLE));
		rec1.addIngredient(new ItemStack(Material.APPLE));
		rec1.register();




		// --register events and commands--
		/*
		Bukkit.getPluginManager().registerEvents(new EntityDamageByEntity(), this);
		Bukkit.getPluginManager().registerEvents(new ProjectileHit(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerTeleport(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerLogin(), this);
		Bukkit.getPluginManager().registerEvents(new Recipes(), this);
		Bukkit.getPluginManager().registerEvents(new SwapOffhand(), this);
		*/

		registerEvents(
				new EntityDamage(),
				new ProjectileHit(),
				new PlayerInteract(),
				new PlayerTeleport(),
				new PlayerLogin(),
				new Recipes(),
				new SwapOffhand()
				);

		/*
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
		*/

		Map<String, CommandExecutor> commandlist = new HashMap<String, CommandExecutor>() {
			{
				put("login", new Login());
				put("logout", new Logout());
				put("recipe", new Recipe());
				put("stats", new Stats());
				put("prof", new Prof());
				put("tester", new Tester());
				put("dev", new Dev());
				put("blockplace", new Blockplace());
				put("multigive", new Multigive());
				put("openinv", new Openinv());
				put("custommob", new Custommob());
			}
		};

		//registerItemClass(ImpactGrenade.getKey(), ImpactGrenade.class);

		registerItemClasses(
				ImpactGrenade.class
				);

		/*
		registerMobClass(GeneralZombie.getKey(), GeneralZombie.class);
		registerMobClass(ShinyToe.getKey(), ShinyToe.class);
		registerMobClass(IronZombie.getKey(), IronZombie.class);
		registerMobClass(Forsaken.getKey(), Forsaken.class);
		registerMobClass(Pigman.getKey(), Pigman.class);
		*/

		registerMobClasses(
				GeneralZombie.class,
				ShinyToe.class,
				IronZombie.class,
				Revenant.class,
				RevenantReborn.class,
				RevenantServant.class,
				Forsaken.class,
				Pigman.class,
				BabyPigman.class,
				LegChopper.class,
				MineZHorse.class,
				Giant.class
				);


		registerCommand(commandlist);

		final int nodamagetick = 10;

		TimingUtil.resetTick();
		new BukkitRunnable() {
			@Override
			public void run() {
				TimingUtil.tick();
			}
		}.runTaskTimer(instance, 1, 1);

		new BukkitRunnable() {
			@Override
			public void run() {
				//DamageUtil.removeDamageTickAllEntity();
				DamageUtil.setMaxDamageTickAllEntity(nodamagetick);
				OffhandUtil.checkAllOffhand();
			}
		}.runTaskTimer(instance, 1, 20); // period was 1 but can be longer, set to 20

		// --enabled msg--
		getLogger().info("Successfully enabled MZ2R plugin.");
	}

	public void registerEvents(Listener... listeners) {
		for (Listener listener : listeners) {
			Bukkit.getPluginManager().registerEvents(listener, this);
		}
	}

	public void registerCommand(Map<String, CommandExecutor> commandlist) {
		for (Entry<String, CommandExecutor> entry : commandlist.entrySet()) {
			getCommand(entry.getKey()).setExecutor(entry.getValue());
		}
	}

	private void registerItemClass(String itemname, Class<?> clazz) {
		ReflectionUtil.registerItemClass(itemname, clazz);
	}

	private void registerItemClass(Class<?> clazz) {
		try {
			registerItemClass((String)clazz.getMethod("getKey").invoke(null), clazz);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	private void registerItemClasses(Class<?>... clazzes) {
		for (Class<?> clazz : clazzes) {
			registerItemClass(clazz);
		}
	}

	private void registerMobClass(String mobname, Class<?> clazz) {
		ReflectionUtil.registerMobClass(mobname, clazz);
	}

	private void registerMobClass(Class<?> clazz) {
		try {
			registerMobClass((String)clazz.getMethod("getKey").invoke(null), clazz);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	private void registerMobClasses(Class<?>... clazzes) {
		for (Class<?> clazz : clazzes) {
			registerMobClass(clazz);
		}
	}
}
