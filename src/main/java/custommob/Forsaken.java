package custommob;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_9_R2.EnumItemSlot;
import net.minecraft.server.v1_9_R2.GenericAttributes;
import util.NMSUtil;


public class Forsaken extends GeneralZombie {
	private static String mobkey = "forsaken";
	private static double unenchhp = 10;
	private static Enchantment ench = Enchantment.PROTECTION_ENVIRONMENTAL;

	private static List<DamageCause> ignore_whenEnch = new ArrayList<DamageCause>() {
		{
			add(DamageCause.PROJECTILE);
		}
	};
	private static List<DamageCause> ignore_whenUnench = new ArrayList<DamageCause>() {
		{
			add(DamageCause.ENTITY_ATTACK);
		}
	};

	public static String getKey() {
		return mobkey;
	}

	public static void onHit(EntityDamageByEntityEvent event) {
		Bukkit.broadcastMessage("let's go Chinaaaaa");
	}

	public static void onGetHit(EntityDamageByEntityEvent event) {
		DamageCause cause = event.getCause();
		if (ignore_damagecause.contains(cause)) {
			event.setCancelled(true);
			return;
		}

		Entity forsaken = event.getEntity();
		if (forsaken.isDead()) return;
		Entity damager = event.getDamager();
		boolean isplayerdamaged = false;
		if (damager instanceof Player) isplayerdamaged = true;
		LivingEntity lforsaken = (LivingEntity) forsaken;
		EntityEquipment eq = lforsaken.getEquipment();
		if (eq.getHelmet().containsEnchantment(ench)) { // enchanted
			if (ignore_whenEnch.contains(cause)) {
				event.setCancelled(true);
				if (isplayerdamaged) damager.sendMessage("nope");
				return;
			}
			double hp = lforsaken.getHealth();
			if (hp <= unenchhp) {
				lforsaken.setHealth(unenchhp);
				ItemStack[] armors = eq.getArmorContents();
				for (ItemStack armor : armors) {
					armor.removeEnchantment(ench);
				}
			}
		} else { // unenchanted
			if (ignore_whenUnench.contains(cause)) {
				event.setCancelled(true);
				if (isplayerdamaged) damager.sendMessage("nope nope");
				return;
			}
		}
	}

	public Forsaken(World world) {
		super(world);
		ItemStack mainhandis = new ItemStack(Material.IRON_SWORD);
		ItemStack headis = new ItemStack(Material.IRON_HELMET);
		ItemStack chestis = new ItemStack(Material.IRON_CHESTPLATE);
		ItemStack legsis = new ItemStack(Material.IRON_LEGGINGS);
		ItemStack feetis = new ItemStack(Material.IRON_BOOTS);

		mainhandis.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		headis.addEnchantment(ench, 1);
		chestis.addEnchantment(ench, 1);
		legsis.addEnchantment(ench, 1);
		feetis.addEnchantment(ench, 1);

		this.setSlot(EnumItemSlot.MAINHAND, NMSUtil.convIStoNMS(mainhandis));
	    this.setSlot(EnumItemSlot.HEAD, NMSUtil.convIStoNMS(headis));
	    this.setSlot(EnumItemSlot.CHEST, NMSUtil.convIStoNMS(chestis));
	    this.setSlot(EnumItemSlot.LEGS, NMSUtil.convIStoNMS(legsis));
	    this.setSlot(EnumItemSlot.FEET, NMSUtil.convIStoNMS(feetis));
	    this.getAttributeInstance(GenericAttributes.g).setValue(8.0D);
	}
}
