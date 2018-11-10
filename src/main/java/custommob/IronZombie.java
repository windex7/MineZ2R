package custommob;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.minecraft.server.v1_9_R2.EnumItemSlot;
import net.minecraft.server.v1_9_R2.GenericAttributes;
import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.Items;

public class IronZombie extends GeneralZombie{
	private static String mobkey = "ironzombie";

	public static String getKey() {
		return mobkey;
	}

	public static void onHit(EntityDamageByEntityEvent event) {
		Bukkit.broadcastMessage("iron zombie attacked!!");
	}

	public static void onGetHit(EntityDamageByEntityEvent event) {
		/*DamageCause cause = event.getCause();
		if (ignore_damagecause.contains(cause)) {
			event.setCancelled(true);
			return;
		}*/
	}

	public static void onDamage(EntityDamageEvent event) {
		DamageCause cause = event.getCause();
		if (ignore_damagecause.contains(cause)) {
			event.setCancelled(true);
			return;
		}
	}

	public IronZombie(World world) {
		super(world);
		this.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
	    this.setSlot(EnumItemSlot.HEAD, new ItemStack(Items.IRON_HELMET));
	    this.setSlot(EnumItemSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
	    this.setSlot(EnumItemSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
	    this.setSlot(EnumItemSlot.FEET, new ItemStack(Items.IRON_BOOTS));
	    this.getAttributeInstance(GenericAttributes.g).setValue(8.0D);
	}
}
