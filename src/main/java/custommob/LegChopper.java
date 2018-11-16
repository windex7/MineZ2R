package custommob;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.minecraft.server.v1_9_R2.GenericAttributes;
import util.VerifyUtil;

public class LegChopper extends GeneralZombie implements CustomMob {
	private static String mobkey = "legchopper";

	public String getKey() {
		return mobkey;
	}

	public void onHit(EntityDamageByEntityEvent event) {
		Entity victim = event.getEntity();
		if (victim.isDead()) return;
		((LivingEntity) victim).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 2), true);
		event.setDamage(0);
		event.setCancelled(true);
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

	public LegChopper(World world) {
		super(world);
	    this.getAttributeInstance(GenericAttributes.g).setValue(8.0D);

	    //this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(-10);
	    this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.45D);
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(64.0D);

		((Zombie) this.getBukkitEntity()).setBaby(true);

	    VerifyUtil.setMobClass((Entity) this.getBukkitEntity(), mobkey);
	}
}