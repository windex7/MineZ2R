package custommob;

import java.util.Set;

import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import net.minecraft.server.v1_9_R2.EntityHuman;
import net.minecraft.server.v1_9_R2.EntityPigZombie;
import net.minecraft.server.v1_9_R2.GenericAttributes;
import net.minecraft.server.v1_9_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_9_R2.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_9_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_9_R2.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_9_R2.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_9_R2.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_9_R2.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_9_R2.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_9_R2.PathfinderGoalSelector;
import util.PrivateField;
import util.VerifyUtil;

public class BabyPigman extends EntityPigZombie {
	private static String mobkey = "babypig";
	private static float power = 4;

	public static String getKey() {
		return mobkey;
	}

	public static void onHit(EntityDamageByEntityEvent event) {

	}

	public static void onGetHit(EntityDamageByEntityEvent event) {
		/*
		DamageCause cause = event.getCause();
		if (ignore_damagecause.contains(cause)) {
			event.setCancelled(true);
			return;
		}
		*/
	}

	public static void onDamage(EntityDamageEvent event) {
		Entity pig = event.getEntity();
		//DamageCause cause = event.getCause();
		/*
		if (ignore_damagecause.contains(cause)) {
			event.setCancelled(true);
			return;
		}
		*/
		if (!pig.isDead())
			((LivingEntity) pig).setHealth(0);
		pig.getWorld().createExplosion(pig.getLocation(), power);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BabyPigman(org.bukkit.World world) {
		super(((CraftWorld) world).getHandle());

		Set goalB = (Set) PrivateField.getPrivateField("b", PathfinderGoalSelector.class, this.goalSelector);
		goalB.clear();
		Set goalC = (Set) PrivateField.getPrivateField("c", PathfinderGoalSelector.class, this.goalSelector);
		goalC.clear();
		Set targetB = (Set) PrivateField.getPrivateField("b", PathfinderGoalSelector.class, this.targetSelector);
		targetB.clear();
		Set targetC = (Set) PrivateField.getPrivateField("c", PathfinderGoalSelector.class, this.targetSelector);
		targetC.clear();

		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
		this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, true));
		this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 0.3D));
		this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));

		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.4D);
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(64.0D);

		((PigZombie) this.getBukkitEntity()).setBaby(true);

		VerifyUtil.setMobClass((Entity) this.getBukkitEntity(), getKey());
	}
}
