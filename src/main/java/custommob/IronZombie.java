package custommob;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import net.minecraft.server.v1_9_R2.EnumItemSlot;
import net.minecraft.server.v1_9_R2.GenericAttributes;
import net.minecraft.server.v1_9_R2.ItemStack;
import net.minecraft.server.v1_9_R2.Items;
import util.VerifyUtil;

public class IronZombie extends GeneralZombie implements CustomMob {
	private static String mobkey = "ironzombie";

	public String getKey() {
		return mobkey;
	}

	public void onHit(EntityDamageByEntityEvent event) {
		//Bukkit.broadcastMessage("iron zombie attacked!!");
	}

	public void onGetHit(EntityDamageByEntityEvent event) {
		/*DamageCause cause = event.getCause();
		if (ignore_damagecause.contains(cause)) {
			event.setCancelled(true);
			return;
		}*/
	}

	public void onDamage(EntityDamageEvent event) {
		DamageCause cause = event.getCause();
		if (ignore_damagecause.contains(cause)) {
			event.setCancelled(true);
			return;
		}
	}

	public void onDeath(EntityDeathEvent event) {

	}

	public IronZombie(World world) {
		super(world);
		this.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
	    this.setSlot(EnumItemSlot.HEAD, new ItemStack(Items.IRON_HELMET));
	    this.setSlot(EnumItemSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
	    this.setSlot(EnumItemSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
	    this.setSlot(EnumItemSlot.FEET, new ItemStack(Items.IRON_BOOTS));
	    this.getAttributeInstance(GenericAttributes.g).setValue(8.0D);

	    this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.35D);

	    VerifyUtil.setMobClass((Entity) this.getBukkitEntity(), mobkey);
	}
}
