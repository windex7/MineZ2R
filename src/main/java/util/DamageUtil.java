package util;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

public class DamageUtil {
	public static void entityDamage(LivingEntity damager, double damage, LivingEntity defender, boolean truevalue) {
		if (truevalue) {
			// defender.damage(damage, damager);
			double currenthealth = defender.getHealth();
			if (currenthealth > damage) {
				defender.damage(0, damager);
				defender.setHealth(currenthealth - damage);
			} else {
				defender.damage(0, damager);
				defender.setHealth(0);
			}
			return;
		} else {
			// ((net.minecraft.server.v1_9_R2.EntityLiving)defender).damageEntity((DamageSource)damager, (float)damage);
			defender.damage(damage, damager);
			return;
		}
	}

	public static void removeDamageTick(LivingEntity entity) {
		//entity.setMaximumNoDamageTicks(0);
		setMaxDamageTick(entity, 0);
		return;
	}

	public static void removeDamageTickAllEntity() {
		for (World world : Bukkit.getServer().getWorlds()) {
			for (LivingEntity entity : world.getLivingEntities()) {
				removeDamageTick(entity);
			}
		}
	}

	public static void setMaxDamageTick(LivingEntity entity, int tick) {
		entity.setMaximumNoDamageTicks(tick);
	}

	public static void setMaxDamageTickAllEntity(int tick) {
		for (World world : Bukkit.getServer().getWorlds()) {
			for (LivingEntity entity : world.getLivingEntities()) {
				setMaxDamageTick(entity, tick);
			}
		}
	}
}
