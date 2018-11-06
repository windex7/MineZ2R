package customitem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import main.MineZ2R;
import util.Base64Util;
import util.NBTUtil;

public class OldSPItem implements Listener {
	public static List<Integer> grenades = new ArrayList<>();
	public static List<Integer> fraggrenades = new ArrayList<>();
	public static HashMap<String, Integer> fragdelay = new HashMap<>();
	public static HashMap<String, Location> c4location = new HashMap<>();

	@EventHandler
	public void onInteractBlock(PlayerInteractEvent event) {
		Plugin mz3 = MineZ2R.getInstance();
		Player player = event.getPlayer();
		String playeruuid = player.getUniqueId().toString();
		File playerconfig = new File(mz3.getDataFolder(), playeruuid + ".yml");
		FileConfiguration playerdata = YamlConfiguration.loadConfiguration(playerconfig);
		Block block = event.getClickedBlock();
		Action action = event.getAction();
		ItemStack item = event.getItem();
		//BlockFace blockface = event.getBlockFace();
		// --prior block-clicking event.--
		if (block != null) {
			switch (block.getType().toString()) {
			case "SKULL":
				//player.sendMessage("it's skull yeah");
				break;
			case "ENDER_CHEST":
				// --ENDER CHEST--
				if (action.equals(Action.LEFT_CLICK_BLOCK))
					break;
				event.setCancelled(true);
				ItemStack unavailableglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 2);
				unavailableglass = NBTUtil.writeItemStringTag(unavailableglass, "unavailable", "true");
				// --if more rows become available in the future, implement here (make inv with reading config)--
				if (playerdata.getString("enderchest") != null) {
					Inventory inv = Bukkit.createInventory(player, 9, "Ender Chest");
					inv.setContents(Base64Util.itemFromStringList(playerdata.getString("enderchest")));
					for (int i = 5; i < 9; i++) {
						inv.setItem(i, unavailableglass);
					}
					player.openInventory(inv);
					return;
				}
				// --for players new to enderchest--
				Inventory inv = Bukkit.createInventory(player, 9, "Ender Chest");
				for (int i = 5; i < 9; i++) {
					inv.setItem(i, unavailableglass);
				}
				player.openInventory(inv);
				return;
			case "WORKBENCH":
				// --CRAFTING TABLE--
				//event.setCancelled(true);
				return;
			default:
				break;
			}
			onClickItem(event, player, item, action, block);
			return;
		} else {
			onClickItem(event, player, item, action, null);
			return;
		}
	}

	@SuppressWarnings("deprecation")
	public static void onClickItem(PlayerInteractEvent event, Player player, ItemStack item, Action action,
			Block block) {

		Plugin mz3 = MineZ2R.getInstance();

		final int fragfuse = 80;

		if (item != null) {
			if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
				switch (item.getType().toString()) {
				case "SLIME_BALL":
					if (item.getItemMeta().getDisplayName() != null) {
						if (item.getItemMeta().getDisplayName().equals("Frag Grenade")) {
							event.setCancelled(true);
							if (item.getItemMeta().getLore() != null) {
								return;
							}
							ItemMeta itemmeta = item.getItemMeta();
							itemmeta.setDisplayName("[pin removed]");
							itemmeta.setLore(Arrays.asList(String.valueOf(
									new SimpleDateFormat("ddHHmmssSSS").format(Calendar.getInstance().getTime()))));
							item.setItemMeta(itemmeta);
							item.addUnsafeEnchantment(Enchantment.LUCK, 4);
							player.updateInventory();
							for (int i = 0; i < fragfuse; i++) {
								final int l = i;
								Bukkit.getScheduler().scheduleSyncDelayedTask(mz3, new Runnable() {
									@Override
									public void run() {
										if (player.getInventory().contains(item)) {
											fragdelay.put(item.getItemMeta().getLore().toString(), l);
											player.setExp((1F * (float) l / (float) fragfuse));
										} else {
											player.setExp(0);
										}
									}
								}, i);
							}
							Bukkit.getScheduler().scheduleSyncDelayedTask(mz3, new Runnable() {
								@Override
								public void run() {
									if (player.getInventory().contains(item)) {
										player.getWorld().createExplosion(player.getLocation(), 4F);
										player.getInventory().remove(item);
										player.setExp(0);
										fragdelay.remove(item.getItemMeta().getLore().toString());
									}
								}
							}, fragfuse);
						}
					}
					break;
				default:
					return;
				}
			}
			if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
				switch (item.getType().toString()) {
				case "LEVER":
					if (item.getItemMeta().getDisplayName() != null) {
						if (item.getItemMeta().getDisplayName().equals("Detonator")) {
							event.setCancelled(true);
							String key = item.getItemMeta().getLore().toString();
							if (!(c4location.containsKey(key)))
								return;
							Location c4loc = c4location.get(key);
							if (c4loc.getBlock().getType() != Material.AIR) {
								player.getWorld().createExplosion(c4loc, 4F);
							}
							c4location.remove(item.getItemMeta().getLore().toString());
							c4loc.getBlock().setType(Material.AIR);
							player.getInventory().removeItem(item);
						}
					}
					break;
				case "SLIME_BALL":
					if (item.getItemMeta().getDisplayName() != null) {
						//if (item.getItemMeta().getDisplayName().equals("Frag Grenade")) event.setCancelled(true);
						if (item.getItemMeta().getDisplayName().equals("[pin removed]")) {
							//event.setCancelled(true);
							Item grenade = player.getWorld().dropItem(player.getEyeLocation(),
									new ItemStack(Material.SLIME_BALL));
							NBTUtil.writeItemEntityIntTag(grenade, "PickupDelay", 32767);
							grenade.setVelocity(player.getLocation().getDirection().multiply(0.9D));
							Bukkit.getScheduler().scheduleSyncDelayedTask(mz3, new Runnable() {
								@Override
								public void run() {
									grenade.getWorld().createExplosion(grenade.getLocation(), 4F);
								}
							}, fragfuse - fragdelay.get(item.getItemMeta().getLore().toString()));
							fragdelay.remove(item.getItemMeta().getLore().toString());
							//EnderPearl fraggrenade = player.launchProjectile(EnderPearl.class);
							//fraggrenade.setShooter(null);
							//fraggrenade.setVelocity(fraggrenade.getVelocity().multiply(0.4));
							//fraggrenades.add(fraggrenade.getEntityId());
							if (item.getAmount() == 1) {
								player.getInventory().removeItem(item);
							} else {
								item.setAmount(item.getAmount() - 1);
							}
							player.updateInventory();
						}
					}
					break;
				case "ENDER_PEARL":
					if (item.getItemMeta().getDisplayName() != null) {
						if (item.getItemMeta().getDisplayName().equals("Impact Grenade")) {
							event.setCancelled(true);
							EnderPearl grenade = player.launchProjectile(EnderPearl.class);
							grenade.setShooter(null);
							grenades.add(grenade.getEntityId());
							if (item.getAmount() == 1) {
								player.getInventory().removeItem(item);
							} else {
								item.setAmount(item.getAmount() - 1);
							}
							player.updateInventory();
						}
					}
					break;
				case "GOLD_AXE":
					if (item.getItemMeta().getDisplayName() != null) {
						if (item.getItemMeta().getDisplayName().equals("Mjolnir")) {
							player.getWorld()
									.strikeLightning(player.getTargetBlock((HashSet<Byte>) null, 20).getLocation());
							item.setDurability((short) (item.getDurability() + 2));
							if (item.getDurability() >= 32) {
								player.getInventory().remove(item);
								player.updateInventory();
							}
						}
					}
					break;
				case "BLAZE_ROD":
					if (item.getItemMeta().getDisplayName() != null) {
						if (item.getItemMeta().getDisplayName().equals("Cornucopia")) {
							for (int i = 0; i < 3; i++) {
								int maxchoice = 2;
								int randomchoice = (int) (Math.random() * maxchoice);
								switch (randomchoice) {
								case 0:
									double bottlespeed = 0.3;
									ThrownExpBottle expbottle = player.launchProjectile(ThrownExpBottle.class);
									expbottle.setVelocity(expbottle.getVelocity().multiply(bottlespeed));
									break;
								case 1:
									Firework firework = (Firework) player.getWorld().spawnEntity(
											player.getTargetBlock((HashSet<Byte>) null, 100).getLocation(),
											EntityType.FIREWORK);
									FireworkMeta fireworkmeta = firework.getFireworkMeta();
									int maxtype = 5;
									int randomtype = (int) (Math.random() * maxtype);
									Type type = convertNumberToType(randomtype);
									int randomcolor1 = (int) (Math.random() * 17);
									int randomcolor2 = (int) (Math.random() * 17);
									Color color1 = convertNumberToColor(randomcolor1);
									Color color2 = convertNumberToColor(randomcolor2);
									int randompower = (int) ((Math.random() * 3) + 1);
									Random random = new Random();
									FireworkEffect effect = FireworkEffect.builder().flicker(random.nextBoolean())
											.withColor(color1).withFade(color2).with(type).trail(random.nextBoolean())
											.build();
									fireworkmeta.addEffect(effect);
									fireworkmeta.setPower(randompower);
									firework.setFireworkMeta(fireworkmeta);
									break;
								}
							}
						}
					}
					break;
				default:
					return;
				}
			}
		}
	}

	public static Type convertNumberToType(int type) {
		switch (type) {
		case 0:
			return Type.BALL;
		case 1:
			return Type.BALL_LARGE;
		case 2:
			return Type.BURST;
		case 3:
			return Type.CREEPER;
		case 4:
			return Type.STAR;
		default:
			return Type.CREEPER;
		}
	}

	public static Color convertNumberToColor(int colornumber) {
		switch (colornumber) {
		case 0:
			return Color.AQUA;
		case 1:
			return Color.BLACK;
		case 2:
			return Color.BLUE;
		case 3:
			return Color.FUCHSIA;
		case 4:
			return Color.GRAY;
		case 5:
			return Color.GREEN;
		case 6:
			return Color.LIME;
		case 7:
			return Color.MAROON;
		case 8:
			return Color.NAVY;
		case 9:
			return Color.OLIVE;
		case 10:
			return Color.ORANGE;
		case 11:
			return Color.PURPLE;
		case 12:
			return Color.RED;
		case 13:
			return Color.SILVER;
		case 14:
			return Color.TEAL;
		case 15:
			return Color.WHITE;
		case 16:
			return Color.YELLOW;
		default:
			return Color.LIME;
		}
	}

}
